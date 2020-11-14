package com.bancomext.fiducia.views.presenter;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.bancomext.fiducia.model.service.envio.EnvioReportMovFiltrado;
import com.bancomext.fiducia.model.service.recibo.ReciboReportMovFiltrado;
import com.bancomext.fiducia.retrofit.Api;
import com.bancomext.fiducia.retrofit.Retrofit;
import com.bancomext.fiducia.tools.UtilsFiducia;
import com.bancomext.fiducia.views.Singleton;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuscarFechaPresenter {

    private FiltradoFechasCallback mFiltradoFechasCallback;
    private Context mContext;
    private Activity mActivity;

    public interface FiltradoFechasCallback{
        void succes(String nombre);
        void error();
    }

    public BuscarFechaPresenter(FiltradoFechasCallback filtradoFechasCallback, Context context, Activity activity) {
        mFiltradoFechasCallback = filtradoFechasCallback;
        mContext = context;
        mActivity = activity;
    }

    public void getReportFiltrado(int typeReport, Date fechaInicial, String fechaFinal){
        if (isNetDisponible()&&isConnectedWifi() || isConnectedMobile()&& isNetDisponible()){
            Retrofit service = Api.getApiJSON().create(Retrofit.class);
            Call<ReciboReportMovFiltrado> call =
                    service.reporteEnviaMovimiento(
                            new EnvioReportMovFiltrado(
                                    typeReport, Singleton.getInstance().getFideicomisoContrato(),
                                    Singleton.getInstance().getNombreUser(), UtilsFiducia.fechaFormatFiltro(fechaInicial),UtilsFiducia.convertDate(fechaFinal)));

            call.enqueue(new Callback<ReciboReportMovFiltrado>() {
                @Override
                public void onResponse(Call<ReciboReportMovFiltrado> call, Response<ReciboReportMovFiltrado> response) {
                    if (response.code() == 200){
                        mFiltradoFechasCallback.succes("Movimientos filtrados");
                    }else {
                        mFiltradoFechasCallback.error();
                    }
                }

                @Override
                public void onFailure(Call<ReciboReportMovFiltrado> call, Throwable t) {
                    mFiltradoFechasCallback.error();
                }
            });

        }

    }

    public  boolean isNetDisponible() {
        ConnectivityManager connectivityManager = (ConnectivityManager) mActivity.getSystemService(mContext.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected() ;
    }

    public  boolean isConnectedWifi() {
        ConnectivityManager connectivityManager = (ConnectivityManager) mActivity.getSystemService(mContext.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    public  boolean isConnectedMobile() {
        ConnectivityManager connectivityManager = (ConnectivityManager) mActivity.getSystemService(mContext.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
    }
}
