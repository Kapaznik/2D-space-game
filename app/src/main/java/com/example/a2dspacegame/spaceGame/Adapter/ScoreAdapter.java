package com.example.a2dspacegame.spaceGame.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2dspacegame.R;
import com.example.a2dspacegame.spaceGame.CallBacks.ListCallBack;
import com.example.a2dspacegame.spaceGame.Models.Record;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;



public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder> {
    private Context context;
    private ArrayList<Record> records;
    public ListCallBack listCallBack_;

    public void setCallBack_sendClick(ListCallBack listCallBack_) {
        this.listCallBack_ = listCallBack_;
    }

    public ScoreAdapter(ArrayList<Record> records) {
        this.context = context;
        this.records = records;
    }


    @NonNull
    @Override
    public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.score, parent, false);
        ScoreViewHolder scoreViewHolder = new ScoreViewHolder(view);
        return scoreViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreViewHolder holder, int position) {
        Record score = getItem(position);
        holder.score_LBL_rank.setText(score.getRank()+"");
        holder.score_LBL_name.setText(score.getName()+"");
        holder.score_LBL_score.setText("Score : "+score.getScore());
    }


    private Record getItem(int position) {
        return this.records.get(position);
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public class ScoreViewHolder extends RecyclerView.ViewHolder {
        private MaterialTextView score_LBL_rank;

        private MaterialTextView score_LBL_name;

        private MaterialTextView score_LBL_score;


        public ScoreViewHolder(@NonNull View itemView) {
            super(itemView);
            score_LBL_name = itemView.findViewById(R.id.score_LBL_name);
            score_LBL_rank = itemView.findViewById(R.id.score_LBL_rank);
            score_LBL_score = itemView.findViewById(R.id.score_LBL_score);
            itemView.setOnClickListener(v -> {
                if (listCallBack_ != null)
                    listCallBack_.rowSelected(
                            getItem(getAdapterPosition()).getRank(),
                            getItem(getAdapterPosition()).getLat(),
                            getItem(getAdapterPosition()).getLon());
            });
        }
    }
}
