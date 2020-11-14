package com.bancomext.fiducia.views.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.bancomext.fiducia.bancomextfiducia.R;
import com.bancomext.fiducia.model.service.envio.EnvioLogin;
import com.bancomext.fiducia.model.service.envio.EnvioValora;
import com.bancomext.fiducia.model.service.recibo.ReciboLogin;
import com.bancomext.fiducia.model.service.recibo.ReciboValora;
import com.bancomext.fiducia.retrofit.Api;
import com.bancomext.fiducia.retrofit.Retrofit;
import com.bancomext.fiducia.tools.UtilsFiducia;
import com.bancomext.fiducia.views.FideicomisosActivity;
import com.bancomext.fiducia.views.LoginActivity;
import com.bancomext.fiducia.views.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ValorarPresenter {

    private ValorarRegisterCallback mValorarRegisterCallback;
    private Context mContext;
    private Activity mActivity;

    public interface ValorarRegisterCallback{
        void valorarSucces();
        void valorarError();
    }

    public ValorarPresenter(ValorarRegisterCallback valorarRegisterCallback, Context context, Activity activity) {
        this.mValorarRegisterCallback = valorarRegisterCallback;
        this.mContext = context;
        this.mActivity = activity;
    }

    public void valorarRequest(int puntiacion, String comentario, long fecha ){

        if (isNetDisponible()&&isConnectedWifi() || isConnectedMobile()&& isNetDisponible()){
            Retrofit service = Api.getApiJSON().create(Retrofit.class);
            Call<ReciboValora> call = service.valoraRequest(new EnvioValora(puntiacion,comentario,fecha,Singleton.getInstance().getFideicomisoContrato(),Singleton.getInstance().getNombreUser()));
            call.enqueue(new Callback<ReciboValora>() {
                @Override
                public void onResponse(Call<ReciboValora> call, Response<ReciboValora> response) {
                    if (response.code() == 200){
                        mValorarRegisterCallback.valorarSucces();
                    }else {
                        mValorarRegisterCallback.valorarError();
                    }
                }

                @Override
                public void onFailure(Call<ReciboValora> call, Throwable t) {
                    mValorarRegisterCallback.valorarError();
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
