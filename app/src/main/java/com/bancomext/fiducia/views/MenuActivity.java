package com.bancomext.fiducia.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.bottomnavigation.LabelVisibilityMode;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bancomext.fiducia.bancomextfiducia.BuildConfig;
import com.bancomext.fiducia.bancomextfiducia.R;
import com.bancomext.fiducia.model.service.envio.EnvioEnvia;
import com.bancomext.fiducia.model.service.recibo.ReciboEnvia;
import com.bancomext.fiducia.retrofit.Api;
import com.bancomext.fiducia.retrofit.Retrofit;
import com.bancomext.fiducia.tools.Constants;
import com.bancomext.fiducia.views.fragmentView.MiCuentaFragment;
import com.bancomext.fiducia.views.fragmentView.MovimientosFragment;
import com.bancomext.fiducia.views.fragmentView.NotificacionesFragment;
import com.bancomext.fiducia.views.fragmentView.PatrimoniosFragment;
import com.bancomext.fiducia.views.fragmentView.SaldosFragment;
import com.bancomext.fiducia.views.presenter.ReportesPresenter;
import com.google.firebase.analytics.FirebaseAnalytics;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuActivity extends AppCompatActivity implements MovimientosFragment.OnFragmentInteractionListener, SaldosFragment.OnFragmentInteractionListener, PatrimoniosFragment.OnFragmentInteractionListener, MiCuentaFragment.OnFragmentInteractionListener, NotificacionesFragment.OnFragmentInteractionListener, View.OnClickListener, ReportesPresenter.ReportesCallback {

    FrameLayout pager;
    TextView opcionMenu;
    TextView tituloFideicomiso;
    TextView buscarFecha;
    ImageButton regresar;
    ImageView estadistica;
    BottomNavigationView menux;
    private ReportesPresenter mReportesPresenter;


    TextView leyendaSaldos;
    ConstraintLayout clTitulo;
    int menu =0;
    String nombre;

    private FirebaseAnalytics mFirebaseAnalytics;

    public static final boolean SIMULATE = BuildConfig.simulate;
    public static final boolean PRODUCTION = BuildConfig.prodution;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        nombre = getIntent().getStringExtra("usuarioName");
        mReportesPresenter =  new ReportesPresenter(this,this,this);

        setViwe();
    }

    private void setViwe() {

        menu = getIntent().getIntExtra("menu",0);

        buscarFecha = findViewById(R.id.ibBuscarFecha);
        buscarFecha.setOnClickListener(this);
        regresar = findViewById(R.id.ibRegresar);
        regresar.setOnClickListener(this);

        leyendaSaldos = findViewById(R.id.tvLeyendaSaldo);
        clTitulo = findViewById(R.id.clTitulo);

        estadistica = findViewById(R.id.ivEstadistica);
        estadistica.setOnClickListener(this);

        opcionMenu = findViewById(R.id.TituloMenu);
        tituloFideicomiso = findViewById(R.id.tituloFideicomiso);
        tituloFideicomiso.setText(Singleton.getInstance().getNombreFideicomiso());

        menux = findViewById(R.id.btnvMenu);
        menux.setOnNavigationItemSelectedListener(navListener);
        menux.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);


        if (Singleton.getInstance().isFlagSaldos() == true){
            menux.getMenu().removeItem(R.id.saldosItem);
        }

        if (Singleton.getInstance().isFlagMovimientos() == true){
            menux.getMenu().removeItem(R.id.movimientosItem);
        }

        pager = findViewById(R.id.pager);

        getSupportFragmentManager().beginTransaction().replace(R.id.pager, new MovimientosFragment()).commit();

        seleccion(menu);
        pager.setForegroundGravity(menu);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = null;

            switch (menuItem.getItemId()){
                case R.id.movimientosItem:
                    selectedFragment = new MovimientosFragment();
                    opcionMenu.setText(R.string.movimientos1);
                    buscarFecha.setEnabled(true);
                    buscarFecha.setVisibility(View.VISIBLE);
                    estadistica.setVisibility(View.VISIBLE);
                    estadistica.setEnabled(true);
                    leyendaSaldos.setVisibility(View.GONE);

                    estadistica.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (SIMULATE) {
                                AlertDialog.Builder alerta;
                                alerta = new AlertDialog.Builder(MenuActivity.this);
                                alerta.setTitle("Solicitud de Reporte de Movimientos");
                                alerta.setMessage("Tu solicitud esta siendo atendida, en breve recibirás tu reporte en tu correo electrónico").setCancelable(false).
                                        setNegativeButton("Entendido", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });
                                alerta.create();
                                alerta.show();
                            }
                            if (PRODUCTION) {
                                ConsumirWebServicesProductionReportes("movimientos");
                            }
                        }
                    });
                    break;
                case R.id.saldosItem:
                    selectedFragment = new SaldosFragment();
                    opcionMenu.setText(R.string.saldos1);
                    buscarFecha.setEnabled(false);
                    buscarFecha.setVisibility(View.INVISIBLE);
                    estadistica.setVisibility(View.VISIBLE);
                    estadistica.setEnabled(true);
                    leyendaSaldos.setVisibility(View.VISIBLE);

                    estadistica.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (SIMULATE) {
                                AlertDialog.Builder alerta;
                                alerta = new AlertDialog.Builder(MenuActivity.this);
                                alerta.setTitle("Solicitud de Reporte de Saldos");
                                alerta.setMessage("Tu solicitud esta siendo atendida, en breve recibirás tu reporte en tu correo electrónico").setCancelable(false).
                                        setNegativeButton("Entendido", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });
                                alerta.create();
                                alerta.show();
                            }
                            if (PRODUCTION) {
                                ConsumirWebServicesProductionReportes("saldos");
                            }
                        }
                    });

                    break;
                case R.id.patrimoniosItem:
                    selectedFragment = new PatrimoniosFragment();
                    opcionMenu.setText(R.string.patrimonios1);
                    buscarFecha.setEnabled(false);
                    buscarFecha.setVisibility(View.INVISIBLE);
                    estadistica.setVisibility(View.INVISIBLE);
                    estadistica.setEnabled(false);
                    leyendaSaldos.setVisibility(View.GONE);
                    break;
                case R.id.cuentaItem:
                    selectedFragment = new MiCuentaFragment();
                    opcionMenu.setText(R.string.mi_cuenta1);
                    buscarFecha.setEnabled(false);
                    buscarFecha.setVisibility(View.INVISIBLE);
                    estadistica.setVisibility(View.INVISIBLE);
                    estadistica.setEnabled(false);
                    leyendaSaldos.setVisibility(View.GONE);
                    break;
                case R.id.notificacionesItem:
                    selectedFragment = new NotificacionesFragment();
                    opcionMenu.setText(R.string.notificaciones1);
                    buscarFecha.setEnabled(false);
                    buscarFecha.setVisibility(View.INVISIBLE);
                    estadistica.setVisibility(View.INVISIBLE);
                    estadistica.setEnabled(false);
                    leyendaSaldos.setVisibility(View.GONE);
                    break;
                    default:
                        selectedFragment = new MovimientosFragment();
                        break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.pager, selectedFragment).commit();

            return true;
        }
    };


    public int seleccion(int tabmenu){

        Fragment selectedFragment = null;

        switch (tabmenu){
            case 0 :
                selectedFragment = new MovimientosFragment();
                opcionMenu.setText(R.string.movimientos1);
                buscarFecha.setEnabled(true);
                buscarFecha.setVisibility(View.VISIBLE);
                menux.setSelectedItemId(R.id.movimientosItem);
                estadistica.setVisibility(View.VISIBLE);
                estadistica.setEnabled(true);
                leyendaSaldos.setVisibility(View.GONE);
                break;
            case 1:
                selectedFragment = new SaldosFragment();
                opcionMenu.setText(R.string.saldos1);
                buscarFecha.setEnabled(false);
                buscarFecha.setVisibility(View.INVISIBLE);
                menux.setSelectedItemId(R.id.saldosItem);
                estadistica.setVisibility(View.VISIBLE);
                estadistica.setEnabled(true);
                leyendaSaldos.setVisibility(View.VISIBLE);
                break;
            case 2:
                selectedFragment = new PatrimoniosFragment();
                opcionMenu.setText(R.string.patrimonios1);
                buscarFecha.setEnabled(false);
                buscarFecha.setVisibility(View.INVISIBLE);
                menux.setSelectedItemId(R.id.patrimoniosItem);
                estadistica.setVisibility(View.GONE);
                estadistica.setEnabled(true);
                leyendaSaldos.setVisibility(View.GONE);
                break;
            default:
                selectedFragment = new MovimientosFragment();
                opcionMenu.setText(R.string.movimientos1);
                buscarFecha.setEnabled(true);
                buscarFecha.setVisibility(View.VISIBLE);
                menux.setSelectedItemId(R.id.movimientosItem);
                estadistica.setVisibility(View.VISIBLE);
                estadistica.setEnabled(true);
                leyendaSaldos.setVisibility(View.GONE);
                break;

        }
        getSupportFragmentManager().beginTransaction().replace(R.id.pager, selectedFragment).commit();
        return tabmenu;
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == buscarFecha.getId()){
            Intent intent = new Intent(MenuActivity.this, BuscarFechaActivity.class);
            startActivity(intent);
        }
        if (v.getId() == regresar.getId()){
            Intent intent = new Intent(MenuActivity.this, FideicomisosActivity.class);
            intent.putExtra("usuarioName",nombre);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {

    }

    private void ConsumirWebServicesProductionReportes(String typeReport){

        Log.e("typeReport", ""+typeReport);
        switch (typeReport){
            case "movimientos":
                requestReportType(1,"Movimientos");
                break;
            case "saldos":
                requestReportType(2,"Saldos");
                break;
        }
    }

    public void requestReportType(int typeReport,String nombre){
        mReportesPresenter.reportRequest(typeReport,nombre);
    }


    @Override
    public void reportesSucces(String nombre) {
        AlertDialog.Builder alerta;
        alerta = new AlertDialog.Builder(MenuActivity.this);
        alerta.setTitle("Solicitud de Reporte de "+ nombre);
        alerta.setMessage("Tu solicitud esta siendo atendida, en breve recibirás tu reporte en tu correo electrónico.").setCancelable(false).
                setNegativeButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alerta.create();
        alerta.show();
    }

    @Override
    public void reportError() {
        AlertDialog.Builder alerta;
        alerta = new AlertDialog.Builder(MenuActivity.this);
        alerta.setTitle("Alerta");
        alerta.setMessage("No fue posible generar tu reporte.\n Intenta más tarde.").setCancelable(false).
                setNegativeButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alerta.create();
        alerta.show();
    }
}
