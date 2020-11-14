package com.bancomext.fiducia.tools;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.util.Log;

import com.bancomext.fiducia.bancomextfiducia.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UtilsFiducia {

    Context mContext;

    public static void setBadge(final Context context, final int count) {
        setBadgeSamsung(context, count);
    }

    public static void clearBadge(final Context context) {
        setBadgeSamsung(context, 0);
    }


    private static void setBadgeSamsung(final Context context, final int count) {
        String launcherClassName = getLauncherClassName(context);
        if (launcherClassName == null) {
            return;
        }
        Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
        intent.putExtra("badge_count", count);
        intent.putExtra("badge_count_package_name", context.getPackageName());
        intent.putExtra("badge_count_class_name", launcherClassName);
        context.sendBroadcast(intent);
    }

    public static String convertDate(String fecha){
        String fecha1= "";
        Date datef = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        try {
            datef = simpleDateFormat.parse(fecha);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        fecha1 = simpleDateFormat.format(datef);

        return fecha1;

    }

    public static ProgressDialog sProgressDialog;

    public static void progress(Activity activity) {
        stopProgress();
        if (!(activity).isFinishing()) {
            sProgressDialog =  new ProgressDialog(activity, R.style.ProgressTheme);
            sProgressDialog.setCancelable(false);
            sProgressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small_Inverse);
            //sProgressDialog = ProgressDialog.show(activity, null, "", true, false);
            sProgressDialog.show();
        }
    }

    public static void stopProgress() {
        try {
            if (sProgressDialog != null) {
                sProgressDialog.cancel();
                sProgressDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static long unixtime(){

        Date currentDate = new Date();
        long unixtime = currentDate.getTime();

        return unixtime;

    }

    public static Date substring(long fecha){
        long unixSeconds = fecha/1000;
        return new java.util.Date(unixSeconds*1000L);
    }

    public static String fechaFormat(Date fecha){
        return new SimpleDateFormat("dd/MMM/YY").format(fecha);

    }

    public static Date fechaPicker(String fecha) {
        {
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            Date fechaDate = null;
            try {
                fechaDate = formato.parse(fecha);
            } catch (ParseException ex) {
                System.out.println(ex);
            }
            return fechaDate;
        }
    }

    public static boolean isAppOnForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        final String packageName = context.getPackageName();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }

    public static String getLauncherClassName(final Context context) {
        PackageManager pm = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resolveInfos) {
            String pkgName = resolveInfo.activityInfo.applicationInfo.packageName;
            if (pkgName.equalsIgnoreCase(context.getPackageName())) {
                String className = resolveInfo.activityInfo.name;
                return className;
            }
        }
        return null;
    }

    public static String getTopClassName(final Context context) {
        String className = "";
        ActivityManager mgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (mgr != null) {
            List<ActivityManager.AppTask> tasks = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                tasks = mgr.getAppTasks();
            }
            if (tasks != null && !tasks.isEmpty()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    className = tasks.get(0).getTaskInfo().topActivity.getClassName();
                }
            }
        }
        return  className;
    }

    public static String getMoneda(String moneda){
        String tipomoneda = "";

        switch (moneda){
            case "MONEDA NACIONAL":
                tipomoneda = "MXN";
                break;
            case "DOLAR AMERICANO":
                tipomoneda = "USD";
                break;
        }
        return tipomoneda;
    }

    public static void dialog(Context context, String message){

        AlertDialog.Builder alerta;
        alerta = new AlertDialog.Builder(context);
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

    public static String mesNombre(int mes){
        String mesName[] = {"ene","feb","mar","abr"," may","jun","jul","ago","sep","oct","nov","dic"};

        String name = mesName[mes];

        return name;
    }

    public static Date getFirstDayMonth(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Log.e("inicial:       ","" + cal.getTime());
        return cal.getTime();
    }

    public static String getFirstDayOfMonth(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Log.e("inicial:       ","" + cal.getTime());
        return new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(cal.getTime());
    }

    public static Date getMonthCurrent(){
        Calendar cal = Calendar.getInstance();
        return cal.getTime();
    }

    public static String fechaFormatFiltro(Date fecha){
        SimpleDateFormat formato = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        return formato.format(fecha);
    }
}
