package com.bancomext.fiducia.views;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bancomext.fiducia.bancomextfiducia.R;
import com.bancomext.fiducia.model.ModelMovimientos;
import com.bancomext.fiducia.tools.UtilsFiducia;
import com.bancomext.fiducia.tools.Constants;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BuscarFechaActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText fechaInicial;
    private EditText fechaFinal;
    private Button consultar;
    private int dia, mes, anio;
    private TextView tituloFideicomiso;
    private TextView regresar;
    private FirebaseAnalytics mFirebaseAnalytics;
    private Date fechaDatePickerInicial;
    private Date fechaDatePickerFinal;
    private List<ModelMovimientos> newList = new ArrayList<>();
    private ArrayList<ModelMovimientos> fechasEncontradas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_fecha);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, Constants.buscarFecha);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        setView();
    }

    private void setView() {

        tituloFideicomiso = findViewById(R.id.tituloFideicomiso);
        tituloFideicomiso.setText(Singleton.getInstance().getNombreFideicomiso());

        fechaInicial = findViewById(R.id.etInicial);
        fechaInicial.setOnClickListener(this);
        fechaInicial.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()){
                    if (!fechaFinal.getText().toString().isEmpty()){
                        consultar.setBackgroundResource(R.drawable.borde_redondo_verde);
                        consultar.setEnabled(true);
                    }
                    if (fechaFinal.getText().toString().isEmpty()){
                        consultar.setBackgroundResource(R.drawable.borde_redondo_gris);
                        consultar.setEnabled(false);
                    }
                }
            }
        });

        fechaFinal = findViewById(R.id.etFinal);
        fechaFinal.setOnClickListener(this);
        fechaFinal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!fechaInicial.getText().toString().isEmpty()){
                    consultar.setBackgroundResource(R.drawable.borde_redondo_verde);
                    consultar.setEnabled(true);
                    if (fechaDatePickerInicial.after(fechaDatePickerFinal)){
                        showInfo();
                    }
                }
                if (fechaInicial.getText().toString().isEmpty() || fechaInicial.getText().toString().equals("")){
                    consultar.setBackgroundResource(R.drawable.borde_redondo_gris);
                    consultar.setEnabled(false);
                }


            }
        });

        consultar = findViewById(R.id.btnConsultar);
        consultar.setOnClickListener(this);
        regresar = findViewById(R.id.cancelarBtn);
        regresar.setOnClickListener(this);
        newList = Singleton.getInstance().getMovimientos();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == fechaInicial.getId()){
            final Calendar calendar= Calendar.getInstance();
            dia = calendar.get(Calendar.DAY_OF_MONTH);
            mes = calendar.get(Calendar.MONTH);
            anio = calendar.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,R.style.DatePicker ,new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    String fecha = dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
                    String fechaInicio = dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
                    String fechaConformato = dayOfMonth+"/"+UtilsFiducia.mesNombre((monthOfYear))+"/"+year;

                    fechaInicial.setText(fechaConformato);
                    fechaDatePickerInicial = UtilsFiducia.fechaPicker(fecha);

                }
            }
                    ,anio,mes,dia);
            Calendar c = Calendar.getInstance();
            c.add(Calendar.MONTH, -12); // subtract 2 years from now
            datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
            c.add(Calendar.MONTH, 12);
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog.show();

        }
        if (v.getId() == fechaFinal.getId()){
            final Calendar calendar= Calendar.getInstance();
            dia = calendar.get(Calendar.DAY_OF_MONTH);
            mes = calendar.get(Calendar.MONTH);
            anio = calendar.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,R.style.DatePicker ,new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    String fechaFinalp = dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
                    String fechaconFormato = dayOfMonth+"/"+UtilsFiducia.mesNombre(monthOfYear)+"/"+year;
                    fechaDatePickerFinal = UtilsFiducia.fechaPicker(fechaFinalp);
                    fechaFinal.setText(fechaconFormato);
                }
            }
                    ,anio,mes,dia);
            calendar.add(Calendar.MONTH, -12); // subtract 2 years from now
            datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
            calendar.add(Calendar.MONTH, 12);
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog.show();
        }
        if (v.getId() == consultar.getId()){
            fechasEncontradas.clear();
            if (fechaDatePickerInicial.after(fechaDatePickerFinal)){
                showInfo();
            }else{
                Singleton.getInstance().setFechaIni(fechaDatePickerInicial);
                Singleton.getInstance().setFechaFin(fechaDatePickerFinal);
                for (int i = 0;  i<newList.size(); i++){
                    if (fechaDatePickerInicial.before(newList.get(i).getMovtoFecha()) && fechaDatePickerFinal.after(newList.get(i).getMovtoFecha())){
                        fechasEncontradas.add(newList.get(i));
                    }
                }

                Singleton.getInstance().setFechasEncontradas(fechasEncontradas);
                Intent intent = new Intent(BuscarFechaActivity.this, ResultadosBusquedaActivity.class);
                startActivity(intent);
            }

        }
        if (v.getId() == regresar.getId()){
            Intent intent = new Intent(BuscarFechaActivity.this, MenuActivity.class);
            startActivity(intent);
        }
    }


    public void showInfo(){
        AlertDialog.Builder alerta;
        alerta = new AlertDialog.Builder(BuscarFechaActivity.this);
        alerta.setTitle("Aviso");
        alerta.setMessage("La fecha inicial debe ser menor o igual a la fecha final.").setCancelable(false).
                setNegativeButton("Entendido", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alerta.create();
        alerta.show();
    }

    @Override
    public void onBackPressed() {

    }
}
