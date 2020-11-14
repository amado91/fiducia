package com.bancomext.fiducia.views.fragmentView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bancomext.fiducia.bancomextfiducia.R;
import com.bancomext.fiducia.tools.Constants;
import com.bancomext.fiducia.views.LoginActivity;
import com.bancomext.fiducia.views.ValorarActivity;
import com.google.firebase.analytics.FirebaseAnalytics;

public class MiCuentaFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FirebaseAnalytics mFirebaseAnalytics;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MiCuentaFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MiCuentaFragment newInstance(String param1, String param2) {
        MiCuentaFragment fragment = new MiCuentaFragment();
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
    }

    TextView valorar;
    TextView cerrarSesion;
    ImageView ibValorar, ibCerrarSesion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mi_cuenta, container, false);
        valorar = view.findViewById(R.id.TituloMenu);
        valorar.setOnClickListener(this);
        cerrarSesion = view.findViewById(R.id.cerrarsesion);
        cerrarSesion.setOnClickListener(this);
        ibValorar = view.findViewById(R.id.ibValorar);
        ibValorar.setOnClickListener(this);
        ibCerrarSesion = view.findViewById(R.id.ibCerrarSesion);
        ibCerrarSesion.setOnClickListener(this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity().getApplicationContext());
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, Constants.cuenta);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == valorar.getId()){
            goToValorarFragment();
        }
        if (v.getId() == ibValorar.getId()){
            goToValorarFragment();
        }
        if (v.getId() == cerrarSesion.getId()){
            dialogCloseSession();
        }
        if (v.getId() == ibCerrarSesion.getId()){
            dialogCloseSession();
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
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

    public void dialogCloseSession(){
        AlertDialog.Builder alerta;
        alerta = new AlertDialog.Builder(this.getContext());
        alerta.setMessage("¿Deseas cerrar tu sesión?").setCancelable(false).
                setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //getActivity().finish();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        alerta.create();
        alerta.show();
    }

    public void goToValorarFragment(){
        Intent intent = new Intent(this.getContext(), ValorarActivity.class);
        startActivity(intent);
    }
}
