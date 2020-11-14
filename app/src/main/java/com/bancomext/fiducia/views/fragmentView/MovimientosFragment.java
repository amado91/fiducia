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

import com.bancomext.fiducia.bancomextfiducia.R;
import com.bancomext.fiducia.model.ModelMovimientos;
import com.bancomext.fiducia.tools.Constants;
import com.bancomext.fiducia.tools.UtilsFiducia;
import com.bancomext.fiducia.views.RecyclerView.RecyclerViewMovimientos;
import com.bancomext.fiducia.views.Singleton;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MovimientosFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FirebaseAnalytics mFirebaseAnalytics;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MovimientosFragment() {
        // Required empty public constructor
    }

    public static MovimientosFragment newInstance(String param1, String param2) {
        MovimientosFragment fragment = new MovimientosFragment();
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

    public List<ModelMovimientos> lstmovimientos ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movimientos, container, false);

        lstmovimientos = new ArrayList<>();
        lstmovimientos = Singleton.getInstance().getMovimientos();

        List<ModelMovimientos> lstmovimientosFiltered = new ArrayList<>();
        Date firstDayOfMonth = UtilsFiducia.getFirstDayMonth();
        for (ModelMovimientos movto: lstmovimientos) {
            if (movto.getMovtoFecha().after(firstDayOfMonth)) {
                lstmovimientosFiltered.add(movto);
            }
        }

        RecyclerView myrv = view.findViewById(R.id.recyclerView_movimientos);
        RecyclerViewMovimientos myAdapter = new RecyclerViewMovimientos(this,lstmovimientosFiltered);
        myrv.setLayoutManager(new GridLayoutManager(this.getContext(),1));
        myrv.setAdapter(myAdapter);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity().getApplicationContext());
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, Constants.movimientos);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        return view;
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
}
