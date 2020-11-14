package com.bancomext.fiducia.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bancomext.fiducia.bancomextfiducia.R;
import com.bancomext.fiducia.model.ModelMovimientos;
import com.bancomext.fiducia.tools.Constants;
import com.bancomext.fiducia.tools.UtilsFiducia;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.text.DecimalFormat;
import java.util.List;

public class DetalleMovimientosActivity extends AppCompatActivity implements View.OnClickListener{

    ImageButton regresar;
    TextView tituloFideicomiso;
    private ImageView imgTer;
    private String mflag;
    private FirebaseAnalytics mFirebaseAnalytics;
    
    TextView tvFecha, tvFolio, tvValorar, tvMoneda, tvImporte, tvConcepto, tvTercero,mTVTercero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_movimientos);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.logEvent(Constants.detalleMovimiento, null);

        setView();
    }

    private void setView() {
        regresar = findViewById(R.id.backMov);
        regresar.setOnClickListener(this);

        tituloFideicomiso = findViewById(R.id.tituloFideicomiso);
        tituloFideicomiso.setText(Singleton.getInstance().getNombreFideicomiso());

        tvFecha = findViewById(R.id.tvFecha);
        tvFecha.setText(getIntent().getStringExtra("fecha"));
        tvFolio = findViewById(R.id.tvFolio);
        tvFolio.setText(getIntent().getStringExtra("folio"));
        tvValorar = findViewById(R.id.tvValorar);
        tvValorar.setText(getIntent().getStringExtra("valorar"));
        tvMoneda = findViewById(R.id.tvMoneda);
        tvMoneda.setText(getIntent().getStringExtra("moneda"));
        tvImporte = findViewById(R.id.tvImporte);
        tvImporte.setText(getIntent().getStringExtra("importe"));
        tvConcepto = findViewById(R.id.tvConcepto);
        tvConcepto.setText(getIntent().getStringExtra("concepto"));
        tvTercero = findViewById(R.id.tvTercero);
        mTVTercero = findViewById(R.id.tvxTercero);
        imgTer = findViewById(R.id.img1);

        try {
            if (getIntent().getStringExtra("tercero").length()> 0){
                mTVTercero.setVisibility(View.VISIBLE);
                tvTercero.setText(getIntent().getStringExtra("tercero"));
                imgTer.setVisibility(View.VISIBLE);
            }else{
                tvTercero.setVisibility(View.GONE);
                mTVTercero.setVisibility(View.GONE);
                imgTer.setVisibility(View.GONE);
            }
        }catch (Exception e){
            e.getMessage();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == regresar.getId()){

            mflag =getIntent().getStringExtra("flag");
            Intent intent = null;
            if (mflag.equals("1")){
                 intent = new Intent(this, MenuActivity.class);
            }else {
                intent = new Intent(this, ResultadosBusquedaActivity.class);
            }

            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {

    }
}
