package com.xd.drivesafe.Driver.Fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xd.drivesafe.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DProfileFragment extends Fragment {


    public DProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dprofile, container, false);
    }

}