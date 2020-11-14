package com.bancomext.fiducia.views.fragmentView;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bancomext.fiducia.bancomextfiducia.R;
import com.bancomext.fiducia.model.ModelNotificaciones;
import com.bancomext.fiducia.tools.Constants;
import com.bancomext.fiducia.tools.ManageFile;
import com.bancomext.fiducia.views.RecyclerView.RecyclerViewNotificaciones;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class NotificacionesFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FirebaseAnalytics mFirebaseAnalytics;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    List<ModelNotificaciones> lstnotificaciones;
    private Context context;
    private TextView aviso;

    public NotificacionesFragment() {
    }

    public static NotificacionesFragment newInstance(String param1, String param2) {
        NotificacionesFragment fragment = new NotificacionesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        context = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notificaciones, container, false);

        final ManageFile m = new ManageFile(context);
        final File f = context.getFilesDir();
        final Object data = m.readFile(f, "PUSH_NOTIFICATION_SAVED");
        final List<ModelNotificaciones> pushList = new ArrayList<ModelNotificaciones>();
        lstnotificaciones = (List<ModelNotificaciones>) data;
        try {
            if (lstnotificaciones.size()>0){
                RecyclerView myrv = view.findViewById(R.id.recyclerView_notificaciones);
                RecyclerViewNotificaciones myAdapter = new RecyclerViewNotificaciones(this,lstnotificaciones);
                myrv.setLayoutManager(new GridLayoutManager(this.getContext(),1));
                myrv.setAdapter(myAdapter);
            }else {
                aviso = view.findViewById(R.id.avisoNotificaciones);
                aviso.setVisibility(View.VISIBLE);
            }
        }catch (Exception e){
            e.getMessage();
            aviso = view.findViewById(R.id.avisoNotificaciones);
            aviso.setVisibility(View.VISIBLE);
        }
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity().getApplicationContext());
        mFirebaseAnalytics.logEvent(Constants.notificaciones, null);
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
