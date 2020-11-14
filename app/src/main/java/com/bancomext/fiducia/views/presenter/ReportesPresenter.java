package com.bancomext.fiducia.views.presenter;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.bancomext.fiducia.model.service.envio.EnvioReportMovFiltrado;
import com.bancomext.fiducia.model.service.envio.EnvioReporte;
import com.bancomext.fiducia.model.service.envio.EnvioValora;
import com.bancomext.fiducia.model.service.recibo.ReciboReportMovFiltrado;
import com.bancomext.fiducia.model.service.recibo.ReciboReporte;
import com.bancomext.fiducia.model.service.recibo.ReciboValora;
import com.bancomext.fiducia.retrofit.Api;
import com.bancomext.fiducia.retrofit.Retrofit;
import com.bancomext.fiducia.tools.UtilsFiducia;
import com.bancomext.fiducia.views.MenuActivity;
import com.bancomext.fiducia.views.Singleton;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportesPresenter {

    private ReportesCallback mReportesCallback;
    private Context mContext;
    private Activity mActivity;


    public interface ReportesCallback{
        void reportesSucces(String nombre);
        void reportError();
    }

    public ReportesPresenter(ReportesCallback valorarRegisterCallback, Context context, Activity activity) {
        this.mReportesCallback = valorarRegisterCallback;
        this.mContext = context;
        this.mActivity = activity;
    }

    public void reportRequest(int typeReport, final String nombre){
        if (isNetDisponible()&&isConnectedWifi() || isConnectedMobile()&& isNetDisponible()){
            Retrofit service = Api.getApiJSON().create(Retrofit.class);

            if (typeReport == 1) {
                Call<ReciboReportMovFiltrado> call = service.reportFiltrado(new EnvioReportMovFiltrado(typeReport, Singleton.getInstance().getFideicomisoContrato(),
                        Singleton.getInstance().getNombreUser(),
                        UtilsFiducia.getFirstDayOfMonth(),UtilsFiducia.convertDate(String.valueOf(UtilsFiducia.getMonthCurrent()))));
                call.enqueue(new Callback<ReciboReportMovFiltrado>() {
                    @Override
                    public void onResponse(Call<ReciboReportMovFiltrado> call, Response<ReciboReportMovFiltrado> response) {
                        if (response.code() == 200){
                            mReportesCallback.reportesSucces(nombre);
                        }else {
                            mReportesCallback.reportError();
                        }
                    }

                    @Override
                    public void onFailure(Call<ReciboReportMovFiltrado> call, Throwable t) {
                        mReportesCallback.reportError();
                    }
                });
            } else {
                Call<ReciboReporte>  call = service.reporteEnviaSaldo(new EnvioReporte(typeReport,Singleton.getInstance().getFideicomisoContrato(),Singleton.getInstance().getNombreUser()));
                call.enqueue(new Callback<ReciboReporte>() {
                    @Override
                    public void onResponse(Call<ReciboReporte> call, Response<ReciboReporte> response) {
                        if (response.code() == 200){
                            mReportesCallback.reportesSucces(nombre);
                        }else {
                            mReportesCallback.reportError();
                        }
                    }

                    @Override
                    public void onFailure(Call<ReciboReporte> call, Throwable t) {
                        mReportesCallback.reportError();
                    }
                });
            }
        }
        else {
            Toast.makeText(mContext,"No Tienes WiFi/internet",Toast.LENGTH_SHORT).show();
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
