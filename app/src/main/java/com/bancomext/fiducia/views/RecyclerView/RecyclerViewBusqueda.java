package com.bancomext.fiducia.views.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bancomext.fiducia.bancomextfiducia.R;
import com.bancomext.fiducia.model.ModelMovimientos;
import com.bancomext.fiducia.tools.UtilsFiducia;
import com.bancomext.fiducia.views.DetalleMovimientosActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewBusqueda extends RecyclerView.Adapter<RecyclerViewBusqueda.MyViewHolder>{

    private Activity mActivity ;
    private List<ModelMovimientos> mData;

    public RecyclerViewBusqueda(Activity activity, List<ModelMovimientos> mData) {
        this.mActivity = activity;
        this.mData = mData;
        android.util.Log.e("Singleto",""+mData.size());
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mActivity.getApplicationContext());
        view = mInflater.inflate(R.layout.cardview_resultados_busqueda,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {

        Double importe = Double.valueOf(mData.get(i).getMovtoImporte());
        DecimalFormat formato = new DecimalFormat("$#,###.00");
        final String valorFormateado = formato.format(importe);
        myViewHolder.getMovimiento_title().setText(mData.get(i).getMovtoConcepto());
        myViewHolder.getMovimientos_descripcion().setText(mData.get(i).getMovtoTipo());
        myViewHolder.getMovimientos_saldo().setText(valorFormateado);
        myViewHolder.getCardviewMoviientos().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity.getApplicationContext(),DetalleMovimientosActivity.class);
                intent.putExtra("fecha", UtilsFiducia.fechaFormat(mData.get(i).getMovtoFecha()).replace(".",""));
                intent.putExtra("folio",String.valueOf(mData.get(i).getMovtoFolio()));
                intent.putExtra("valorar",mData.get(i).getMovtoTipo());
                intent.putExtra("moneda",mData.get(i).getMovtoMoneda());
                intent.putExtra("importe",valorFormateado);
                intent.putExtra("concepto",mData.get(i).getMovtoConcepto());
                intent.putExtra("tercero",mData.get(i).getMovtoTercero());
                intent.putExtra("flag","0");
                mActivity.startActivity(intent);
            }
        });

        List<Integer> indexList = new ArrayList<>();
        String current = "";
        for (int j = 0; j < mData.size(); j++) {
            if (!mData.get(j).getMovtoFecha().toString().equals(current)) {
                current = mData.get(j).getMovtoFecha().toString();
                indexList.add(j);
            }
        }
        if (indexList.contains(i)) {
            visibleFecha(myViewHolder, i);
        }else {
            invisible(myViewHolder);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView movimiento_title;
        TextView movimientos_descripcion;
        TextView movimientos_saldo;
        ImageButton siguienteMovimientos;
        ConstraintLayout cardviewMoviientos;
        private TextView fechas;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            movimiento_title = itemView.findViewById(R.id.tituloMovimientos_busqueda);
            movimientos_descripcion = itemView.findViewById(R.id.descripcionMovimientos_busqueda);
            movimientos_saldo = itemView.findViewById(R.id.saldoMovimientos_busqueda);
            siguienteMovimientos = itemView.findViewById(R.id.movimientosSiguiente_busqueda);
            cardviewMoviientos = itemView.findViewById(R.id.cardview_movimientos_busqueda);
            fechas = itemView.findViewById(R.id.fechaBusqueda);
        }

        public TextView getFechas() {
            return fechas;
        }

        public TextView getMovimiento_title() {
            return movimiento_title;
        }

        public TextView getMovimientos_descripcion() {
            return movimientos_descripcion;
        }

        public TextView getMovimientos_saldo() {
            return movimientos_saldo;
        }

        public ImageButton getSiguienteMovimientos() {
            return siguienteMovimientos;
        }

        public ConstraintLayout getCardviewMoviientos() {
            return cardviewMoviientos;
        }
    }


    public void visibleFecha(MyViewHolder myViewHolder, int i) {
        myViewHolder.getFechas().setVisibility(View.VISIBLE);
        String fecha = UtilsFiducia.fechaFormat(mData.get(i).getMovtoFecha());
        myViewHolder.getFechas().setText(fecha.replace(".",""));

    }

    public void invisible(MyViewHolder myViewHolder) {
        myViewHolder.getFechas().setVisibility(View.GONE);
    }
}
