package com.bancomext.fiducia.views.presenter;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.bancomext.fiducia.model.service.envio.EnvioPatrimonios;
import com.bancomext.fiducia.model.service.envio.EnvioReporte;
import com.bancomext.fiducia.model.service.envio.EnvioValora;
import com.bancomext.fiducia.model.service.recibo.ReciboPatrimonios;
import com.bancomext.fiducia.model.service.recibo.ReciboReporte;
import com.bancomext.fiducia.model.service.recibo.ReciboValora;
import com.bancomext.fiducia.retrofit.Api;
import com.bancomext.fiducia.retrofit.Retrofit;
import com.bancomext.fiducia.views.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatrimoniosPresenter {

    private PatrimoniosCallback mPatrimoniosCallback;
    private Activity mActivity;
    private Context mContext;

    public interface PatrimoniosCallback{
        void onSucces(String message);
        void onError();
    }

    public PatrimoniosPresenter(PatrimoniosCallback patrimoniosCallback, Activity activity, Context context) {
        mPatrimoniosCallback = patrimoniosCallback;
        mActivity = activity;
        mContext = context;
    }

    public void enviaPatrimonio(){
        if (isNetDisponible()&&isConnectedWifi() || isConnectedMobile()&& isNetDisponible()){
            Retrofit service = Api.getApiJSON().create(Retrofit.class);
            Call<ReciboReporte> call = service.reporteEnviaPatrimonio(new EnvioReporte(3,Singleton.getInstance().getFideicomisoContrato(),Singleton.getInstance().getNombreUser()));
            call.enqueue(new Callback<ReciboReporte>() {
                @Override
                public void onResponse(Call<ReciboReporte> call, Response<ReciboReporte> response) {
                    if (response.code() == 200){
                        mPatrimoniosCallback.onSucces("patrimonios");
                    }else {
                        mPatrimoniosCallback.onError();
                    }
                }

                @Override
                public void onFailure(Call<ReciboReporte> call, Throwable t) {
                    mPatrimoniosCallback.onError();
                }
            });
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
