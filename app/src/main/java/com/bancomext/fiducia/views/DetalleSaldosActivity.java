package com.bancomext.fiducia.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bancomext.fiducia.bancomextfiducia.R;
import com.bancomext.fiducia.tools.Constants;
import com.bancomext.fiducia.tools.UtilsFiducia;
import com.google.firebase.analytics.FirebaseAnalytics;

public class DetalleSaldosActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton regresar;
    TextView tituloFideicomiso;
    private FirebaseAnalytics mFirebaseAnalytics;

    TextView tvFecha, tvNombre, tvContrato, tvImporte, tvMoneda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_saldos);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.logEvent(Constants.detalleSaldo, null);

        setView();
    }

    private void setView() {
        regresar = findViewById(R.id.backSal);
        regresar.setOnClickListener(this);

        tituloFideicomiso = findViewById(R.id.tituloFideicomiso);
        tituloFideicomiso.setText(Singleton.getInstance().getNombreFideicomiso());


        tvFecha = findViewById(R.id.tFecha);
        tvFecha.setText(getIntent().getStringExtra("fechaSaldo"));
        tvNombre = findViewById(R.id.tnombre);
        tvNombre.setText(getIntent().getStringExtra("nombre"));
        tvMoneda = findViewById(R.id.tMoneda);
        tvMoneda.setText(UtilsFiducia.getMoneda(getIntent().getStringExtra("moneda")));
        tvContrato =  findViewById(R.id.tContrato);
        tvContrato.setText(getIntent().getStringExtra("contrato"));
        tvImporte = findViewById(R.id.tImporte);
        tvImporte.setText(getIntent().getStringExtra("importe"));

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == regresar.getId()){
            finish();
        }
    }

    @Override
    public void onBackPressed() {

    }
}
