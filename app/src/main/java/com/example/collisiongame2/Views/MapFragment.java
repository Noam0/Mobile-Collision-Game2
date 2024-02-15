package com.example.collisiongame2.Views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.collisiongame2.R;
import com.google.android.material.textview.MaterialTextView;


public class MapFragment extends Fragment {

    private MaterialTextView map_LBL_title;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        findViews(view);
        return view;
    }

   public void zoom(double lat , double lon){
     map_LBL_title.setText(lat + "\n" + lon);
    }

    private void findViews(View view){
        map_LBL_title =  view.findViewById(R.id.map_LBL_title);
    }
}