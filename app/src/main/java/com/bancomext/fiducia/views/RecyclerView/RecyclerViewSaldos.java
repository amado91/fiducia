package com.bancomext.fiducia.views.RecyclerView;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bancomext.fiducia.bancomextfiducia.R;
import com.bancomext.fiducia.model.ModelSaldos;
import com.bancomext.fiducia.tools.UtilsFiducia;
import com.bancomext.fiducia.views.DetalleSaldosActivity;
import com.bancomext.fiducia.views.Singleton;
import com.bancomext.fiducia.views.fragmentView.SaldosFragment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewSaldos extends RecyclerView.Adapter<RecyclerViewSaldos.MyViewHolder>{

    private SaldosFragment mContext ;
    private List<ModelSaldos> msalData ;
    private String fecha;

    public RecyclerViewSaldos(SaldosFragment mContext, List<ModelSaldos> mData) {
        this.mContext = mContext;
        this.msalData = Singleton.getInstance().getSaldos();
        fecha = UtilsFiducia.fechaFormat(msalData.get(0).getFecha());
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext.getContext());
        view = mInflater.inflate(R.layout.cardview_saldos,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder,final int i) {

        Double importe = Double.valueOf(msalData.get(i).getCtoInvSaldo());
        DecimalFormat formato = new DecimalFormat("$#,###.00");
        final String valorFormateado = formato.format(importe);

        List<Integer> indexList = new ArrayList<>();
        String current = "";
        for (int j = 0; j < msalData.size(); j++) {
            if (!msalData.get(j).getFecha().toString().equals(current)) {
                current = msalData.get(j).getFecha().toString();
                indexList.add(j);
            }
        }
        if (indexList.contains(i)) {
            visibleFecha(myViewHolder, i);
        }else {
            invisible(myViewHolder);
        }

        myViewHolder.saldos_title.setText(msalData.get(i).getCtoInvNombre());
        myViewHolder.saldos_moneda.setText(UtilsFiducia.getMoneda(msalData.get(i).getCtoInvMoneda()));
        DecimalFormat formatter = new DecimalFormat("$#,###.00");

            myViewHolder.saldos_saldo.setText(formatter.format((msalData.get(i).getCtoInvSaldo())));
            myViewHolder.cardviewSaldos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext.getContext(), DetalleSaldosActivity.class);
                    intent.putExtra("fechaSaldo", UtilsFiducia.fechaFormat(msalData.get(i).getFecha()).replace(".",""));
                    intent.putExtra("nombre",msalData.get(i).getCtoInvNombre());
                    intent.putExtra("contrato",String.valueOf(msalData.get(i).getAtencionContratoNumero()));
                    intent.putExtra("importe",valorFormateado);
                    intent.putExtra("moneda",msalData.get(i).getCtoInvMoneda());
                    mContext.startActivity(intent);
                }
            });
    }

    @Override
    public int getItemCount() {
        return msalData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView saldos_title;
        TextView saldos_moneda;
        TextView saldos_saldo;
        ImageView siguienteMovimientos;
        ConstraintLayout cardviewSaldos;
        TextView fechaSaldos;
        CardView cardsaldos;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            saldos_title = itemView.findViewById(R.id.tituloSaldo);
            saldos_moneda = itemView.findViewById(R.id.monedaSaldo);
            saldos_saldo = itemView.findViewById(R.id.saldoSaldo);
            siguienteMovimientos = itemView.findViewById(R.id.saldosSiguiente);
            cardviewSaldos = itemView.findViewById(R.id.cardview_saldos);
            fechaSaldos = itemView.findViewById(R.id.tvFechaSaldos);
            cardsaldos = itemView.findViewById(R.id.cardSaldos);
        }

        public TextView getFechaSaldos() {
            return fechaSaldos;
        }
    }

    public void visibleFecha(RecyclerViewSaldos.MyViewHolder myViewHolder, int i) {
        myViewHolder.getFechaSaldos().setVisibility(View.VISIBLE);
        myViewHolder.getFechaSaldos().setText(UtilsFiducia.fechaFormat(msalData.get(i).getFecha()).replace(".", ""));

    }

    public void invisible(RecyclerViewSaldos.MyViewHolder myViewHolder) {
        myViewHolder.getFechaSaldos().setVisibility(View.GONE);

    }
}
