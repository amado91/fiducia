package com.bancomext.fiducia.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Toast;

import com.bancomext.fiducia.bancomextfiducia.R;
import com.bancomext.fiducia.tools.UtilsFiducia;
import com.bancomext.fiducia.tools.Constants;
import com.bancomext.fiducia.views.presenter.ValorarPresenter;
import com.google.firebase.analytics.FirebaseAnalytics;

public class ValorarActivity extends AppCompatActivity implements View.OnClickListener, ValorarPresenter.ValorarRegisterCallback {

    ImageButton regresar;
    EditText comentarios;
    Button enviar;
    RatingBar valorar;
    ValorarPresenter mValorarPresenter;
    private int puntuacion = 0;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valorar);

        mValorarPresenter = new ValorarPresenter(this,this,this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.logEvent(Constants.valorar, null);

        setView();
    }

    private void setView() {
        regresar = findViewById(R.id.ibRegresar);
        regresar.setOnClickListener(this);
        enviar = findViewById(R.id.btnEnviar);
        enviar.setOnClickListener(this);
        comentarios = findViewById(R.id.etComentarios);

        valorar = findViewById(R.id.rbValorar);
        valorar.setStepSize(1);
        valorar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                puntuacion = Math.round(rating);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == regresar.getId()){
            onBackPressed();
        }
        if (v.getId() == enviar.getId()){
            if (puntuacion > 0 ){
                long fecha = UtilsFiducia.unixtime();
                UtilsFiducia.progress(this);
                mValorarPresenter.valorarRequest(puntuacion,comentarios.getText().toString(),fecha);
            }else {
                AlertDialog.Builder alerta;
                alerta = new AlertDialog.Builder(this);
                alerta.setTitle("Alerta");
                alerta.setMessage("Apóyanos a calificar la aplicación").setCancelable(false).
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
    }

    @Override
    public void valorarSucces() {
        UtilsFiducia.stopProgress();
        finish();
    }

    @Override
    public void valorarError() {
        Toast.makeText(this,"No Tienes WiFi/internet",Toast.LENGTH_SHORT).show();
    }
}
