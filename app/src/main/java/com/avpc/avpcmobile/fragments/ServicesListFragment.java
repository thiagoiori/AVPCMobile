package com.avpc.avpcmobile.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avpc.avpcmobile.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServicesListFragment extends Fragment {


    public ServicesListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_services_list, container, false);
    }

}
