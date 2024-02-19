package com.example.collisiongame2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collisiongame2.Model.Player;
import com.example.collisiongame2.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> {

    private Context context;
    private ArrayList<Player> allPlayersList;

    //callback

    public PlayerAdapter(Context context, ArrayList<Player> allPlayersList) {
        this.context = context;
        this.allPlayersList = allPlayersList;
    }

    //setter to callback


    @NonNull
    @Override
    public PlayerAdapter.PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row_player_score, parent,false);
        return new PlayerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerAdapter.PlayerViewHolder holder, int position) {
        Player player = getItem(position);
        holder.player_LBL_rank.setText((String.valueOf(position+1)));
        holder.player_LBL_name.setText(player.getName());
        holder.player_LBL_score.setText(String.valueOf(player.getScore()));


    }

    @Override
    public int getItemCount() {
        return allPlayersList == null ? 0 : allPlayersList.size();

    }
private Player getItem(int position){

        return allPlayersList.get(position);
}

    public class PlayerViewHolder extends RecyclerView.ViewHolder{

       private  MaterialTextView player_LBL_rank;
       private  MaterialTextView player_LBL_name;
       private MaterialTextView player_LBL_score;
       private  MaterialButton player_BUTTON_LOCATION;


        public PlayerViewHolder(@NonNull View itemView) {
            super(itemView);

            player_LBL_rank = itemView.findViewById(R.id.player_LBL_rank);
            player_LBL_name = itemView.findViewById(R.id.player_LBL_name);
            player_LBL_score = itemView.findViewById(R.id.player_LBL_score);
            player_BUTTON_LOCATION = itemView.findViewById(R.id.player_BUTTON_LOCATION);
        }
    }
}
