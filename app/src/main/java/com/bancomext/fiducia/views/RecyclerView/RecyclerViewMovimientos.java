package com.bancomext.fiducia.views.RecyclerView;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.bancomext.fiducia.bancomextfiducia.R;
import com.bancomext.fiducia.model.ModelMovimientos;
import com.bancomext.fiducia.tools.UtilsFiducia;
import com.bancomext.fiducia.views.DetalleMovimientosActivity;
import com.bancomext.fiducia.views.Singleton;
import com.bancomext.fiducia.views.fragmentView.MovimientosFragment;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewMovimientos extends RecyclerView.Adapter<RecyclerViewMovimientos.MyViewHolder> {

    private MovimientosFragment mContext ;
    public List<ModelMovimientos> movData ;

    private String fecha;

    public RecyclerViewMovimientos(MovimientosFragment mContext, List<ModelMovimientos> mData) {
        this.mContext = mContext;
        this.movData = mData;
        this.fecha = UtilsFiducia.fechaFormat(Singleton.getInstance().getMovimientos().get(0).getMovtoFecha());
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext.getContext());
        view = mInflater.inflate(R.layout.cardview_movimientos,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

        fecha.replace(".","");

        List<Integer> indexList = new ArrayList<>();
        String current = "";

        for (int j = 0; j < movData.size(); j++) {
            if (!movData.get(j).getMovtoFecha().toString().equals(current)) {
                current = movData.get(j).getMovtoFecha().toString();
                indexList.add(j);
            }
        }
        if (indexList.contains(i)) {
            visibleFecha(myViewHolder, i);
        }else {
            invisible(myViewHolder);
        }

        Double importe = Double.valueOf(movData.get(i).getMovtoImporte());
        DecimalFormat formato = new DecimalFormat("$#,###.00");
        final String valorFormateado = formato.format(importe);
        String numcontrato = String.valueOf(movData.get(i).getMovtoContratoNumero());
        myViewHolder.movimiento_title.setText(movData.get(i).getMovtoConcepto());
        myViewHolder.movimientos_descripcion.setText(movData.get(i).getMovtoTipo());
        myViewHolder.movimientos_saldo.setText(valorFormateado);
        myViewHolder.cardviewMoviientos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext.getContext(),DetalleMovimientosActivity.class);
                intent.putExtra("fecha", UtilsFiducia.fechaFormat(movData.get(i).getMovtoFecha()).replace(".",""));
                intent.putExtra("folio",String.valueOf(movData.get(i).getMovtoFolio()));
                intent.putExtra("valorar",movData.get(i).getMovtoTipo());
                intent.putExtra("moneda",movData.get(i).getMovtoMoneda());
                intent.putExtra("importe",valorFormateado);
                intent.putExtra("concepto",movData.get(i).getMovtoConcepto());
                intent.putExtra("tercero",movData.get(i).getMovtoTercero());
                intent.putExtra("flag","1");
                mContext.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return movData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView movimiento_title;
        TextView movimientos_descripcion;
        TextView movimientos_saldo;
        ImageView siguienteMovimientos;
        ConstraintLayout cardviewMoviientos;
        TextView fechaMovimientos;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            movimiento_title = itemView.findViewById(R.id.tituloMovimientos);
            movimientos_descripcion = itemView.findViewById(R.id.descripcionMovimientos);
            movimientos_saldo = itemView.findViewById(R.id.saldoMovimientos);
            siguienteMovimientos = itemView.findViewById(R.id.movimientosSiguiente);
            cardviewMoviientos = itemView.findViewById(R.id.cardview_movimientos);
            fechaMovimientos = itemView.findViewById(R.id.tvFechaMovimientos);
        }

        public TextView getFechaMovimientos() {
            return fechaMovimientos;
        }

    }

    public int compareTo(ModelMovimientos d) {
        return (d.getMovtoFecha()).compareTo(d.getMovtoFecha());
    }

    public void visibleFecha(MyViewHolder myViewHolder, int i) {
        myViewHolder.getFechaMovimientos().setVisibility(View.VISIBLE);
        myViewHolder.getFechaMovimientos().setText(UtilsFiducia.fechaFormat(movData.get(i).getMovtoFecha()).replace(".", ""));

    }

    public void invisible(MyViewHolder myViewHolder) {
        myViewHolder.getFechaMovimientos().setVisibility(View.GONE);

    }

}
