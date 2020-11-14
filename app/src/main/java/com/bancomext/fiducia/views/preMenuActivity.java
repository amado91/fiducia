package com.bancomext.fiducia.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bancomext.fiducia.bancomextfiducia.R;
import com.bancomext.fiducia.model.ModelFideicomiso;
import com.bancomext.fiducia.model.ModelMovimientos;
import com.bancomext.fiducia.tools.Constants;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.List;

public class preMenuActivity extends AppCompatActivity implements View.OnClickListener{

    ImageButton movimientos;
    ImageButton saldos;
    ImageButton patrimonios;
    TextView movimientosTxt;
    TextView saldosTxt;
    TextView patrimoniosTxt;
    int id = 0;
    private FirebaseAnalytics mFirebaseAnalytics;
    private ModelFideicomiso mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_menu);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.logEvent(Constants.menu, null);

        setView();
    }

    private void setView() {
        movimientos = findViewById(R.id.ibtMovimientos);
        movimientos.setOnClickListener(this);
        movimientosTxt =  findViewById(R.id.textView2);
        movimientosTxt.setOnClickListener(this);
        saldos = findViewById(R.id.ibtSaldos);
        saldos.setOnClickListener(this);
        saldosTxt = findViewById(R.id.saldosTxt);
        saldosTxt.setOnClickListener(this);
        patrimonios = findViewById(R.id.ibtPatrimonios);
        patrimonios.setOnClickListener(this);
        patrimoniosTxt = findViewById(R.id.textView17);
        patrimoniosTxt.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        String tituloFideicomiso = getIntent().getStringExtra("titulo_Fideicomiso");
        String nomFideicomiso = getIntent().getStringExtra("nombre");
        id = getIntent().getIntExtra("contrato_nombre",10594);
        String nombreFideicomiso = "Fideicomiso "+ tituloFideicomiso+" "+nomFideicomiso;
        Singleton.getInstance().setNombreFideicomiso(nombreFideicomiso);


        if (Singleton.getInstance().isFlagSaldos() == true){
            saldos.setVisibility(View.GONE);
            saldosTxt.setVisibility(View.GONE);
        }

        if (Singleton.getInstance().isFlagMovimientos() == true){
            movimientos.setVisibility(View.GONE);
            movimientosTxt.setVisibility(View.GONE);
        }

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == movimientos.getId() || v == movimientosTxt){
            Intent intent = new Intent(preMenuActivity.this, MenuActivity.class);
            intent.putExtra("menu",0);
            intent.putExtra("idContrato",id);
            startActivity(intent);
            finish();
        }
        if (v.getId() == saldos.getId() || v == saldosTxt){
            Intent intent = new Intent(preMenuActivity.this, MenuActivity.class);
            intent.putExtra("menu",1);
            startActivity(intent);
            finish();
        }
        if (v.getId() == patrimonios.getId() || v == patrimoniosTxt){
            Intent intent = new Intent(preMenuActivity.this, MenuActivity.class);
            intent.putExtra("menu",2);
            startActivity(intent);
            finish();
        }

    }

    @Override
    public void onBackPressed() {

    }
}
