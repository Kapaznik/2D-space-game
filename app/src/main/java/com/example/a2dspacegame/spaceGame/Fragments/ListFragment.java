package com.example.a2dspacegame.spaceGame.Fragments;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2dspacegame.spaceGame.Adapter.ScoreAdapter;
import com.example.a2dspacegame.spaceGame.CallBacks.ListCallBack;
import com.example.a2dspacegame.spaceGame.Models.Record;
import com.example.a2dspacegame.spaceGame.Models.RecordList;
import com.example.a2dspacegame.spaceGame.Utilities.MySPv3;
import com.example.a2dspacegame.R;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Objects;


public class ListFragment extends Fragment implements ListCallBack{
    private RecyclerView scoreboard_LST_scores;
    private ListCallBack listCallBack;


    public void setCallBack_sendClick(ListCallBack listCallBack) {
        this.listCallBack = listCallBack;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_score, container, false);
        findViews(view);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        String fromJSON = MySPv3.getInstance().getString("MY_DB", "");
        RecordList dataManager = new Gson().fromJson(fromJSON,RecordList.class);
        ArrayList<Record> scores = dataManager.getRecords();
        if (scores != null && !scores.isEmpty()) {
            ScoreAdapter scoreAdapter = new ScoreAdapter(scores);
            scoreAdapter.setCallBack_sendClick(listCallBack);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
            scoreboard_LST_scores.setAdapter(scoreAdapter);
            scoreboard_LST_scores.setLayoutManager(linearLayoutManager);
            scoreboard_LST_scores.setVisibility(View.VISIBLE);
        } else {
            scoreboard_LST_scores.setVisibility(View.GONE);
        }
    }

    private void findViews(View view) {
        scoreboard_LST_scores = view.findViewById(R.id.scoreboard_LST_scores);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ListCallBack) {
            ((SupportMapFragment) Objects.requireNonNull(getChildFragmentManager().findFragmentById(R.id.score_FRAME_map))).getMapAsync(googleMap -> {
            });
        }
    }

    @Override
    public void rowSelected(String name, double lat, double lon) {

    }
}
