package com.example.collisiongame2.Views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.collisiongame2.Adapters.PlayerAdapter;
import com.example.collisiongame2.Interfaces.CallBack_playerScoreClicked;
import com.example.collisiongame2.Model.Player;
import com.example.collisiongame2.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;


public class ListFragment extends Fragment {
    private MaterialTextView list_LBL_title;
    private MaterialButton List_BTN_send;

    private CallBack_playerScoreClicked callBackPlayerScoreClicked;

//////changing now
    private RecyclerView ListFragment_RCV_PLAYERBOARD;

    private ArrayList<Player> playerList;


    public ListFragment(ArrayList<Player> playerList) {
        this.playerList = playerList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);
        findViews(view);
        initViews();

        List_BTN_send.setOnClickListener(v -> playerClicked(32.11, 32.112));
        return view;
    }



    private void playerClicked(double lat, double lon ) {
        if(callBackPlayerScoreClicked != null)
            callBackPlayerScoreClicked.playerScoreClicked(lat, lon);

    }
    private void initViews() {
        PlayerAdapter playerAdapter = new PlayerAdapter(getActivity().getApplicationContext(),playerList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        ListFragment_RCV_PLAYERBOARD.setLayoutManager(linearLayoutManager);
        ListFragment_RCV_PLAYERBOARD.setAdapter(playerAdapter);
    }
    private void findViews(View view){
        list_LBL_title = view.findViewById(R.id.list_LBL_title);
        List_BTN_send = view.findViewById(R.id.List_BTN_send);
        ListFragment_RCV_PLAYERBOARD = view.findViewById(R.id.ListFragment_RCV_PLAYERBOARD);
    }

    public void setCallBackPlayerScoreClicked(CallBack_playerScoreClicked callBackPlayerScoreClicked) {
        this.callBackPlayerScoreClicked = callBackPlayerScoreClicked;
    }

    public void setPlayerList(ArrayList<Player> playerList) {
        this.playerList = playerList;


    }
}