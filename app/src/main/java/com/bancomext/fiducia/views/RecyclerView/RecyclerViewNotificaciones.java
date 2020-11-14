package com.bancomext.fiducia.views.RecyclerView;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bancomext.fiducia.bancomextfiducia.R;
import com.bancomext.fiducia.model.ModelNotificaciones;
import com.bancomext.fiducia.views.MenuActivity;
import com.bancomext.fiducia.views.fragmentView.NotificacionesFragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RecyclerViewNotificaciones extends RecyclerView.Adapter<RecyclerViewNotificaciones.MyViewHolder>{

    private NotificacionesFragment nContext;
    private List<ModelNotificaciones> nData;

   public RecyclerViewNotificaciones(NotificacionesFragment nContext, List<ModelNotificaciones> nData){
       this.nContext = nContext;
       this.nData = nData;
   }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view ;
        LayoutInflater mInflater = LayoutInflater.from(nContext.getContext());
        view = mInflater.inflate(R.layout.cardview_notificaciones,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        myViewHolder.notificaciones_title.setText(nData.get(i).getTitulo());
        myViewHolder.notificaciones_descripcion.setText(nData.get(i).getDescripcion());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        //String convertedDate = dateFormat.format(nData.get(i).getFecha());
        String convertedDate = dateFormat.format(new Date());
        myViewHolder.notificaciones_fecha.setText(convertedDate);
        myViewHolder.cardviewNotificaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(nContext.getContext(), MenuActivity.class);
                nContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return nData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView notificaciones_title;
        TextView notificaciones_descripcion;
        TextView notificaciones_fecha;
        CardView cardviewNotificaciones;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            notificaciones_title = itemView.findViewById(R.id.notificacionesTitulo);
            notificaciones_descripcion = itemView.findViewById(R.id.notificacionesDescripcion);
            notificaciones_fecha = itemView.findViewById(R.id.notificacionesFecha);
            cardviewNotificaciones = itemView.findViewById(R.id.cardview_notificaciones);
        }
    }
}
