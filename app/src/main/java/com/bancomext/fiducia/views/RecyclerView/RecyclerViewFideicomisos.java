package com.bancomext.fiducia.views.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bancomext.fiducia.bancomextfiducia.BuildConfig;
import com.bancomext.fiducia.bancomextfiducia.R;
import com.bancomext.fiducia.model.ModelFideicomiso;
import com.bancomext.fiducia.model.ModelMovimientos;
import com.bancomext.fiducia.model.ModelSaldos;
import com.bancomext.fiducia.model.service.envio.EnvioMovimientos;
import com.bancomext.fiducia.model.service.envio.EnvioSaldos;
import com.bancomext.fiducia.model.service.recibo.ReciboAtencion;
import com.bancomext.fiducia.model.service.recibo.ReciboMovimientos;
import com.bancomext.fiducia.model.service.recibo.ReciboSaldos;
import com.bancomext.fiducia.retrofit.Api;
import com.bancomext.fiducia.retrofit.Retrofit;
import com.bancomext.fiducia.tools.UtilsFiducia;
import com.bancomext.fiducia.views.Singleton;
import com.bancomext.fiducia.views.preMenuActivity;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerViewFideicomisos extends RecyclerView.Adapter<RecyclerViewFideicomisos.MyViewHolder> {

    private Context mContext;
    private Activity mActivity;
    private List<ModelFideicomiso> mData;
    List<ModelMovimientos> lstmovimientos;
    List<ModelSaldos> lstSaldos;
    private int fideicomiso_referencia;
    private String nombre;
    int idIndexFideicomiso;

    public static final boolean SIMULATE = BuildConfig.simulate;
    public static final boolean TEST = BuildConfig.pruebas;
    public static final boolean PRODUCTION = BuildConfig.prodution;

    public RecyclerViewFideicomisos(Context mContext, List<ModelFideicomiso> mData, Activity activity) {
        this.mContext = mContext;
        this.mData = mData;
        this.mActivity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        idIndexFideicomiso = i;
        if (TEST) {
            myViewHolder.fideicomiso_referencia.setText(mData.get(idIndexFideicomiso).getTitle());
            myViewHolder.nombre.setText(mData.get(idIndexFideicomiso).getNombre());
            myViewHolder.cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mData.get(idIndexFideicomiso).getSaldos().size()>0) {
                        Intent intent = new Intent(mContext, preMenuActivity.class);
                        intent.putExtra("titulo_Fideicomiso", mData.get(idIndexFideicomiso).getTitle());
                        intent.putExtra("nombre", mData.get(idIndexFideicomiso).getNombre());
                        mContext.startActivity(intent);
                    }
                }
            });
        }
        if (PRODUCTION) {

            myViewHolder.fideicomiso_referencia.setText(mData.get(idIndexFideicomiso).getTitle());
            myViewHolder.nombre.setText(mData.get(idIndexFideicomiso).getNombre());
            myViewHolder.cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    idIndexFideicomiso = i;
                    if (mData.get(idIndexFideicomiso).getSaldos().size()>0){
                        goToPreMenu();
                    }
                    else {
                        UtilsFiducia.progress(mActivity);
                        ConsumirWebServicesProductionMovimientos(i);
                        ConsumirWebServicesProductionSaldos(i);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView fideicomiso_referencia;
        TextView fideicomiso;
        TextView nombre;
        ImageView siguiente;
        CardView cardview;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            fideicomiso_referencia = itemView.findViewById(R.id.fideicomiso);
            fideicomiso = itemView.findViewById(R.id.textView19);
            nombre = itemView.findViewById(R.id.nombre);
            siguiente = itemView.findViewById(R.id.siguiente);
            cardview = itemView.findViewById(R.id.cardview_id);
        }
    }

    public String loadJSONFromAsset(String flName) {
        String json;
        try {
            InputStream is = mContext.getAssets().open("json/" + flName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            Log.v("RegistroNumActivity", "Load json ok");
        } catch (IOException ex) {
            ex.printStackTrace();
            Log.v("RegistroNumActivity", "Error: " + ex.getMessage());
            return null;
        }
        return json;
    }


    private void ConsumirWebServicesProductionMovimientos(int i){
        int id = Integer.valueOf(mData.get(i).getTitle());

        Retrofit service = Api.getApiJSON().create(Retrofit.class);
        final Call<List<ReciboMovimientos>> datosMovimientos = service.obtenerProdution(new EnvioMovimientos(id));
        datosMovimientos.enqueue(new Callback<List<ReciboMovimientos>>() {
            @Override
            public void onResponse(Call<List<ReciboMovimientos>> call, Response<List<ReciboMovimientos>> response) {
                android.util.Log.e("peticion movimientos", "" + response.code());
                List<ReciboMovimientos> datosMovimientos = response.body();
                if (datosMovimientos != null){
                    List<ModelMovimientos> movimientosList = new ArrayList<ModelMovimientos>();
                    ModelMovimientos modelo = new ModelMovimientos();
                    for (int i=0; i<datosMovimientos.size(); i++){
                        ReciboMovimientos reciboMovimientos = datosMovimientos.get(i);
                        int numReferencia = reciboMovimientos.getMovtoContratoNumero();
                        int folio = reciboMovimientos.getMovtoFolio();
                        String tipo = reciboMovimientos.getMovtoTipo();
                        Date fecha = UtilsFiducia.substring(reciboMovimientos.getMovtoFecha());
                        String moneda = reciboMovimientos.getMovtoMoneda();
                        String importe = String.valueOf(reciboMovimientos.getMovtoImporte());
                        String concepto = reciboMovimientos.getMovtoConcepto();
                        String tercero = reciboMovimientos.getMovtoTercero();
                        Date fechaReg = convertDate(String.valueOf(reciboMovimientos.getMovtoFecha()));

                        modelo = new ModelMovimientos(numReferencia,folio,tipo,fecha,moneda,importe,concepto,tercero,fechaReg);
                        movimientosList.add(modelo);
                        lstmovimientos = movimientosList;
                    }
                }
                Singleton.getInstance().setFlagMovimientos(false);
                if (lstmovimientos != null){
                    Singleton.getInstance().setMovimientos(lstmovimientos);

                    List<ModelMovimientos> lstmovimientosFiltered = new ArrayList<>();
                    Date firstDayOfMonth = UtilsFiducia.getFirstDayMonth();
                    for (ModelMovimientos movto: lstmovimientos) {
                        if (movto.getMovtoFecha().after(firstDayOfMonth)) {
                            lstmovimientosFiltered.add(movto);
                        }
                    }
                    if (lstmovimientosFiltered.size() <= 0) {
                        Singleton.getInstance().setFlagMovimientos(true);
                    }
                }else {
                    Singleton.getInstance().setFlagMovimientos(true);
                }
            }

            @Override
            public void onFailure(Call<List<ReciboMovimientos>> call, Throwable t) {
                t.getMessage();
                Toast toast1 = Toast.makeText(mContext, ""+t.getMessage(), Toast.LENGTH_SHORT);
                toast1.show();
            }
        });
    }

    private void ConsumirWebServicesProductionSaldos(int i){
        int id = Integer.valueOf(mData.get(i).getTitle());
        Singleton.getInstance().setFideicomisoContrato(Integer.valueOf(mData.get(i).getTitle()));
        Retrofit service = Api.getApiJSON().create(Retrofit.class);
        final Call<List<ReciboSaldos>> datosSaldos = service.obtenerProdution(new EnvioSaldos(id));
        datosSaldos.enqueue(new Callback<List<ReciboSaldos>>() {
            @Override
            public void onResponse(Call<List<ReciboSaldos>> call, Response<List<ReciboSaldos>> response) {
                List<ReciboSaldos> datosSaldos = response.body();
                if (datosSaldos != null){
                    List<ModelSaldos> saldosList = new ArrayList<ModelSaldos>();
                    for (int i=0; i<datosSaldos.size(); i++){
                        ReciboSaldos reciboSaldos = datosSaldos.get(i);
                        int contratoNumero = reciboSaldos.getSaldoContratoNumero();
                        Date fecha = UtilsFiducia.substring(reciboSaldos.getSaldoFecha());
                        int atencionContratoNumero = reciboSaldos.getAtencionContratoNumero();
                        String ctoInvNombre = reciboSaldos.getSaldoCtoInvNombre();
                        String ctoInvMoneda =reciboSaldos.getSaldoCtoInvMoneda();
                        int ctoInvSaldo = reciboSaldos.getSaldoCtoInvSaldo();
                        String saldoFechaReg = String.valueOf(reciboSaldos.getSaldoFecha());

                        ModelSaldos modelo = new ModelSaldos(contratoNumero,fecha,atencionContratoNumero,ctoInvNombre,ctoInvMoneda,ctoInvSaldo,saldoFechaReg);
                        saldosList.add(modelo);
                        lstSaldos = saldosList;
                    }
                }
                if (lstSaldos != null){
                    Singleton.getInstance().setSaldos(lstSaldos);
                    Singleton.getInstance().setFlagSaldos(false);
                    goToPreMenu();
                }else {
                    Singleton.getInstance().setFlagSaldos(true);
                    goToPreMenu();
                }
            }

            @Override
            public void onFailure(Call<List<ReciboSaldos>> call, Throwable t) {
                t.getMessage();
                Toast toast1 = Toast.makeText(mContext, ""+t.getMessage(), Toast.LENGTH_SHORT);
                toast1.show();
            }
        });
    }


    private Date convertDate(String fecha) {
        DateFormat fmt1 = new SimpleDateFormat("yyyyDDD");
        Date date = null;
        try {
            date = fmt1.parse(fecha);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat fmt2 = new SimpleDateFormat("MM/dd/yyyy");
        String outputFecha = fmt2.format(date);
        Date dateFinal = new Date();
        try {
            dateFinal =fmt2.parse(outputFecha);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateFinal;
    }

    public void goToPreMenu(){
        UtilsFiducia.stopProgress();
        Intent intent = new Intent(mActivity, preMenuActivity.class);
        intent.putExtra("titulo_Fideicomiso", mData.get(idIndexFideicomiso).getTitle());
        intent.putExtra("nombre", mData.get(idIndexFideicomiso).getNombre());
        mActivity.startActivity(intent);
    }


}
