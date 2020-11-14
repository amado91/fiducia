package com.bancomext.fiducia.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bancomext.fiducia.bancomextfiducia.BuildConfig;
import com.bancomext.fiducia.bancomextfiducia.R;
import com.bancomext.fiducia.model.ModelFideicomiso;
import com.bancomext.fiducia.model.ModelMovimientos;
import com.bancomext.fiducia.model.ModelSaldos;
import com.bancomext.fiducia.model.service.envio.EnvioAtencion;
import com.bancomext.fiducia.model.service.recibo.ReciboAtencion;
import com.bancomext.fiducia.retrofit.Api;
import com.bancomext.fiducia.retrofit.Retrofit;
import com.bancomext.fiducia.tools.Constants;
import com.bancomext.fiducia.tools.UtilsFiducia;
import com.bancomext.fiducia.views.RecyclerView.RecyclerViewFideicomisos;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FideicomisosActivity extends AppCompatActivity implements View.OnClickListener{

    List<ModelFideicomiso> lstfideicomiso ;
    TextView cerrarSesion;
    String userName;
    private FirebaseAnalytics mFirebaseAnalytics;
    private AppCompatActivity mActivity = new AppCompatActivity();
    String nombre;

    public static final boolean SIMULATE = BuildConfig.simulate;
    public static final boolean TEST = BuildConfig.pruebas;
    public static final boolean PRODUCTION = BuildConfig.prodution;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fideicomisos);
        nombre = Singleton.getInstance().getNombreUser();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.logEvent(Constants.fideicomisos,  null);

        try {
            if (Singleton.getInstance().getFideicomisos() != null) {
                lstfideicomiso = Singleton.getInstance().getFideicomisos();
                presentarListaFideicomisos();
            } else {
                if (TEST) {
                   // ConsumirWebServicesTESTFideicomisos();
                }
                if (PRODUCTION) {
                    ConsumirWebServicesProductionFideicomisos();
                }
            }
        } catch (Exception e) {
            e.getMessage();
        }

        setView();
    }

    private void setView(){
        cerrarSesion = findViewById(R.id.imbCerrarSesion);
        cerrarSesion.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == cerrarSesion.getId()){
            AlertDialog.Builder alerta;
            alerta = new AlertDialog.Builder(this);
            alerta.setMessage("¿Deseas cerrar tu sesión?").setCancelable(false).
                    setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Singleton.getInstance().setFideicomisos(null);
                    Intent intent = new Intent(FideicomisosActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            alerta.create();
            alerta.show();
        }
    }

    @Override
    public void onBackPressed() {

    }

    private void presentarListaFideicomisos() {
        RecyclerView myrv = findViewById(R.id.recyclerView_id);
        RecyclerViewFideicomisos myAdapter = new RecyclerViewFideicomisos(getApplicationContext(),lstfideicomiso, FideicomisosActivity.this);
        myrv.setLayoutManager(new GridLayoutManager(getApplicationContext(),1));
        myrv.setAdapter(myAdapter);
    }

    private void ConsumirWebServicesProductionFideicomisos() {

        UtilsFiducia.progress(this);
        String nameUser = Singleton.getInstance().getNombreUser();
        String pass = Singleton.getInstance().getPassword();

        Retrofit service = Api.getApiLogin(nameUser,pass).create(Retrofit.class);
        final Call<List<ReciboAtencion>> datosFideicomiso = service.obtenerProdution(new EnvioAtencion(nombre));
        datosFideicomiso.enqueue(new Callback<List<ReciboAtencion>>() {
            @Override
            public void onResponse(Call<List<ReciboAtencion>> call, Response<List<ReciboAtencion>> response) {
                if (response.code() == 200){
                    List<ReciboAtencion> datosrecibidos = response.body();
                    if (datosrecibidos != null) {
                        List<ModelFideicomiso> fideicomisoList = new ArrayList<ModelFideicomiso>();
                        for (int i= 0; i<datosrecibidos.size(); i++){
                            ReciboAtencion fideicomiso_referencia = datosrecibidos.get(i);
                            String numReferencia = String.valueOf(fideicomiso_referencia.getAtencionContratoNumero());
                            ModelFideicomiso modelo = new ModelFideicomiso(numReferencia, fideicomiso_referencia.getAtencionContratoNombre(),new ArrayList<ModelMovimientos>(), new ArrayList<ModelSaldos>());
                            fideicomisoList.add(modelo);
                        }
                        lstfideicomiso = fideicomisoList;
                        Singleton.getInstance().setFideicomisos(lstfideicomiso);
                        UtilsFiducia.stopProgress();
                        presentarListaFideicomisos();
                    }
                }else {
                    dialogGenerica();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<List<ReciboAtencion>> call, Throwable t) {
                dialogGenerica();
            }
        });
    }

    public void dialogGenerica(){
        UtilsFiducia.stopProgress();
        AlertDialog.Builder alerta;
        alerta = new AlertDialog.Builder(FideicomisosActivity.this);
        alerta.setTitle("Alerta");
        alerta.setMessage("Por el momento no podemos obtener los fideicomisos. Favor de intentar mas tarde:").setCancelable(false).
                setNegativeButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(FideicomisosActivity.this,LoginActivity.class);
                        startActivity(intent);
                        dialog.cancel();
                    }
                });
        alerta.create();
        alerta.show();
    }


    //Checar disponibilidad de la red e internet----------------------------------------------------
    private boolean isNetDisponible() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected() ;
    }

    public boolean isConnectedWifi() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    public boolean isConnectedMobile() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
    }
}
