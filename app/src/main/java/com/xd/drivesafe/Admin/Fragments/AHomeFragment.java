package com.xd.drivesafe.Admin.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xd.drivesafe.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AHomeFragment extends Fragment {


    public AHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ahome, container, false);
    }

}
