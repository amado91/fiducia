package com.bancomext.fiducia.tools;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.bancomext.fiducia.views.fragmentView.MiCuentaFragment;
import com.bancomext.fiducia.views.fragmentView.MovimientosFragment;
import com.bancomext.fiducia.views.fragmentView.NotificacionesFragment;
import com.bancomext.fiducia.views.fragmentView.PatrimoniosFragment;
import com.bancomext.fiducia.views.fragmentView.SaldosFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {


    int mNoOptions;
    public PagerAdapter(FragmentManager fm, int noOptions){
        super(fm);
        this.mNoOptions= noOptions;
    }


    @Override
    public Fragment getItem(int i) {

        switch (i){
            case 0:
                MovimientosFragment movimientosFragment = new MovimientosFragment();
                return movimientosFragment;
            case 1:
                SaldosFragment saldosFragment = new SaldosFragment();
                return saldosFragment;
            case 2:
                PatrimoniosFragment patrimoniosFragment = new PatrimoniosFragment();
                return patrimoniosFragment;
            case 3:
                MiCuentaFragment miCuentaFragment = new MiCuentaFragment();
                return miCuentaFragment;
            case 4:
                NotificacionesFragment notificacionesFragment = new NotificacionesFragment();
                return notificacionesFragment;

            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return mNoOptions;
    }
}
