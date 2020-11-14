package com.bancomext.fiducia.views;

import android.app.AlertDialog;
import android.arch.lifecycle.SingleGeneratedAdapterObserver;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bancomext.fiducia.bancomextfiducia.R;
import com.bancomext.fiducia.model.ModelMovimientos;
import com.bancomext.fiducia.views.RecyclerView.RecyclerViewBusqueda;
import com.bancomext.fiducia.views.presenter.BuscarFechaPresenter;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ResultadosBusquedaActivity extends AppCompatActivity implements View.OnClickListener, BuscarFechaPresenter.FiltradoFechasCallback{

    List<ModelMovimientos> lstmovimientos ;
    RecyclerView rcv;
    ImageButton regresar;
    TextView titulo;
    private ImageView mReportFiltro;
    private Date fechaIni, fechaFin;
    private BuscarFechaPresenter mBuscarFechaPresenter;
    private TextView avisoFiltro;

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados_busqueda);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mBuscarFechaPresenter = new BuscarFechaPresenter(this, this, this);
        fechaIni = Singleton.getInstance().getFechaIni();
        fechaFin = Singleton.getInstance().getFechaFin();
        setView();
    }

    private void setView() {

        regresar = findViewById(R.id.ibRegresar);
        regresar.setOnClickListener(this);
        mReportFiltro = findViewById(R.id.movFiltro);
        mReportFiltro.setOnClickListener(this);
        titulo = findViewById(R.id.tituloFideicomiso);
        avisoFiltro =  findViewById(R.id.avisoFiltroNo);
        titulo.setText(Singleton.getInstance().getNombreFideicomiso());

        lstmovimientos = new ArrayList<>();

        rcv= findViewById(R.id.resultados_busqueda);
        lstmovimientos = Singleton.getInstance().getFechasEncontradas();
        if (lstmovimientos.size() > 0){
            RecyclerViewBusqueda myAdapter = new RecyclerViewBusqueda(this,lstmovimientos);
            rcv.setLayoutManager(new GridLayoutManager(this,1));
            rcv.setAdapter(myAdapter);
        }else {
            avisoFiltro.setVisibility(View.VISIBLE);
            mReportFiltro.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == regresar.getId()){
            Intent intent = new Intent(ResultadosBusquedaActivity.this, BuscarFechaActivity.class);
            startActivity(intent);
        }

        if (v ==  mReportFiltro){
            mBuscarFechaPresenter.getReportFiltrado(1,fechaIni,String.valueOf(fechaFin));
        }
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void succes(String nombre) {
        AlertDialog.Builder alerta;
        alerta = new AlertDialog.Builder(ResultadosBusquedaActivity.this);
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
    public void error() {
        AlertDialog.Builder alerta;
        alerta = new AlertDialog.Builder(ResultadosBusquedaActivity.this);
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
