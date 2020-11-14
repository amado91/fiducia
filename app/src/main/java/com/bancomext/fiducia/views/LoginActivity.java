package com.bancomext.fiducia.views;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.VoiceInteractor;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bancomext.fiducia.bancomextfiducia.BuildConfig;
import com.bancomext.fiducia.bancomextfiducia.R;
import com.bancomext.fiducia.model.service.envio.EnvioLogin;
import com.bancomext.fiducia.model.service.recibo.ReciboLogin;
import com.bancomext.fiducia.retrofit.Api;
import com.bancomext.fiducia.retrofit.Retrofit;
import com.bancomext.fiducia.tools.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.io.IOException;
import java.io.InputStream;

import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText email;
    private EditText password;
    private TextInputLayout emailTxt, passwordTxt;
    private Button login;
    private TextView avisoPrivacidad;
    private FirebaseAnalytics mFirebaseAnalytics;
    private static final String TAG = "Login";
    String tokenEnvio;
    private int dispositivo = 1;
    private ProgressDialog progressDialog;
    private TextView version;

    public static final boolean SIMULATE = BuildConfig.simulate;
    public static final boolean TEST = BuildConfig.pruebas;
    public static final boolean PRODUCTION = BuildConfig.prodution;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.logEvent(Constants.login, null);
        getToken();
        setView();
    }

    private void setView() {
        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPass);
        emailTxt = findViewById(R.id.tilEmail);
        passwordTxt = findViewById(R.id.tilPass);
        login = findViewById(R.id.btnLogin);
        login.setOnClickListener(this);
        version = findViewById(R.id.version);
        version.setText("v "+BuildConfig.VERSION_NAME);

        avisoPrivacidad = findViewById(R.id.btnAviso);
        avisoPrivacidad.setOnClickListener(this);


        progressDialog = new ProgressDialog(this, R.style.ProgressTheme);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small_Inverse);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == login.getId()){
            if (SIMULATE){
                progressDialog.show();
                if (loadJSONFromAsset("Login.json") != null) {
                    Intent intent = new Intent(LoginActivity.this, FideicomisosActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            if (TEST) {
                progressDialog.show();
                ConsumirWebServicesTEST();
            }

            if (PRODUCTION){
                if (!email.getText().toString().isEmpty() && !password.getText().toString().isEmpty()){
                    progressDialog.show();
                    ConsumirWebServicesProduction();
                }else if (email.getText().toString().isEmpty() && password.getText().toString().isEmpty()){
                    dialog("Favor de ingresar usuario y contraseña.");
                }
                else if (email.getText().toString().isEmpty()){
                    dialog("Favor de ingresar usuario y contraseña.");
                }
                else if (password.getText().toString().isEmpty()){
                    dialog("Favor de ingresar usuario y contraseña.");
                }

            }
        }

        if (v.getId() == avisoPrivacidad.getId()){
            Intent intent = new Intent(LoginActivity.this, AvisosPrivacidadActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void ConsumirWebServicesTEST() {
        final String usu = email.getText().toString();
        final String pass = password.getText().toString();
        tokenEnvio = Singleton.getInstance().getToken();


        if(isNetDisponible()&&isConnectedWifi() || isConnectedMobile()&& isNetDisponible()){
            Retrofit service= Api.getApiJSON().create(Retrofit.class);

            //WebService Login
            final retrofit2.Call<ReciboLogin> datosLogin = service.obtenerProdution(new EnvioLogin(tokenEnvio,dispositivo));
            datosLogin.enqueue(new Callback<ReciboLogin>() {
                @Override
                public void onResponse(retrofit2.Call<ReciboLogin> call, Response<ReciboLogin> response) {
                    if (response.code() == 200){
                        ReciboLogin datosrecibidos = response.body();
                        if (datosrecibidos != null){
                            if (datosrecibidos.getCodigo() !=null){
                                if (datosrecibidos.getCodigo().equals("1")){
                                    Intent intent = new Intent(LoginActivity.this, FideicomisosActivity.class);
                                    intent.putExtra("Usuario",datosrecibidos.getUsuario().getNombre());
                                    Singleton.getInstance().setNombreUser(datosrecibidos.getUsuario().getNombre());
                                    startActivity(intent);
                                    finish();
                                }
                                else {
                                    progressDialog.dismiss();
                                    dialog(getString(R.string.Wrong_user_password));
                                }
                            }
                            else{
                                progressDialog.dismiss();
                                dialog(getString(R.string.Wrong_user_password));
                            }
                        }
                        else{
                            progressDialog.dismiss();
                            dialog(getString(R.string.Wrong_user_password));
                        }
                    }
                    else if (response.code() == 204){
                        progressDialog.dismiss();
                        dialog("No se recibieron datos del servidor.");
                    }
                }

                @Override
                public void onFailure(retrofit2.Call<ReciboLogin> call, Throwable t) {
                    progressDialog.dismiss();
                    t.getMessage();
                    dialog("Servicio temporalmente no disponible.");

                }
            });
        }
        else{
            dialog("Servicio temporalmente no disponible.");
        }

    }

    private void ConsumirWebServicesProduction() {
        final String usu = email.getText().toString();
        final String pass = password.getText().toString();
        tokenEnvio = Singleton.getInstance().getToken();


        if(isNetDisponible()&&isConnectedWifi() || isConnectedMobile()&& isNetDisponible()){
            Retrofit service= Api.getApiLogin(usu,pass).create(Retrofit.class);

            //WebService Login
            final retrofit2.Call<ReciboLogin> datosLogin = service.obtenerProdution(new EnvioLogin(tokenEnvio,dispositivo));
            datosLogin.enqueue(new Callback<ReciboLogin>() {
                @Override
                public void onResponse(retrofit2.Call<ReciboLogin> call, Response<ReciboLogin> response) {
                    if (response.code() == 200){
                        ReciboLogin datosrecibidos = response.body();
                        android.util.Log.e("Code",""+response.code());
                        if (datosrecibidos != null){
                            if (datosrecibidos.getCodigo() !=null){
                                if (datosrecibidos.getCodigo().equals("0")){
                                    Intent intent = new Intent(LoginActivity.this, FideicomisosActivity.class);
                                    //intent.putExtra("usuarioName",datosrecibidos.getUsuario().getNombre());
                                    Singleton.getInstance().setNombreUser(datosrecibidos.getUsuario().getNombre());
                                    Singleton.getInstance().setPassword(pass);
                                    startActivity(intent);
                                    finish();
                                }
                                else {
                                    dialog(getString(R.string.Wrong_user_password));
                                }
                            }
                            else{
                                dialog(getString(R.string.Wrong_user_password));
                            }
                        }
                        else{
                            dialog(getString(R.string.Wrong_user_password));
                        }
                    }
                    else if (response.code() == 204){
                        dialog("No se recibieron datos del servidor.");
                    }
                    else if (response.code() == 401){
                        dialog(getString(R.string.Wrong_user_password));
                    }
                }

                @Override
                public void onFailure(retrofit2.Call<ReciboLogin> call, Throwable t) {
                    dialog("Servicio temporalmente no disponible.");
                }
            });
        }
        else{
            dialog("Servicio temporalmente no disponible.");
        }
    }

    public void getToken(){
        // Get token
        // [START retrieve_current_token]
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String tokenmsg = task.getResult().getToken();
                        String token1 = tokenmsg.replace("InstanceID Token: ","");
                        // Log and toast

                        //String token = getString(R.string.msg_token_fmt, /*tokenmsg*/token1);
                        Singleton.getInstance().setToken(token1);
                        Log.d(TAG, token1);
                        //Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
        // [END retrieve_current_token]
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public String loadJSONFromAsset(String flName) {
        String json;
        try {
            InputStream is = this.getAssets().open("json/" + flName);
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


    //Checar disponibilidad de la red e internet----------------------------------------------------
    private boolean isNetDisponible() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected() ;
    }

    public boolean isConnectedWifi() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    public boolean isConnectedMobile() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
    }

    public void dialog(String message){
        progressDialog.dismiss();
        email.setText(null);
        password.setText(null);
        passwordTxt.setHintEnabled(false);
        emailTxt.setHintEnabled(false);
        AlertDialog.Builder alerta;
        alerta = new AlertDialog.Builder(LoginActivity.this);
        alerta.setTitle("Alerta");
        alerta.setMessage(message).setCancelable(false).
                setNegativeButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                });
        alerta.create();
        alerta.show();
    }
}
