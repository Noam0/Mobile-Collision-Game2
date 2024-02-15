package com.example.collisiongame2.Views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.collisiongame2.Interfaces.CallBack_playerScoreClicked;
import com.example.collisiongame2.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;


public class ListFragment extends Fragment {
    private MaterialTextView list_LBL_title;
    private MaterialButton List_BTN_send;

    private CallBack_playerScoreClicked callBackPlayerScoreClicked;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);
        findViews(view);
        List_BTN_send.setOnClickListener(v -> playerClicked(32.11, 32.112));
        return view;
    }

    private void playerClicked(double lat, double lon ) {
        if(callBackPlayerScoreClicked != null)
            callBackPlayerScoreClicked.playerScoreClicked(lat, lon);

    }

    private void findViews(View view){
        list_LBL_title = view.findViewById(R.id.list_LBL_title);
        List_BTN_send = view.findViewById(R.id.List_BTN_send);
    }

    public void setCallBackPlayerScoreClicked(CallBack_playerScoreClicked callBackPlayerScoreClicked) {
        this.callBackPlayerScoreClicked = callBackPlayerScoreClicked;
    }
}