package com.example.a2dspacegame.spaceGame.Fragments;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2dspacegame.spaceGame.Adapter.ScoreAdapter;
import com.example.a2dspacegame.spaceGame.CallBacks.ListCallBack;
import com.example.a2dspacegame.spaceGame.CallBacks.MapCallBack;
import com.example.a2dspacegame.spaceGame.Models.Record;
import com.example.a2dspacegame.spaceGame.Models.RecordList;
import com.example.a2dspacegame.spaceGame.utilities.MySPv3;
import com.example.a2dspacegame.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.util.ArrayList;


public class ListFragment extends Fragment {
    private RecyclerView scoreboard_LST_scores;
    private ListCallBack listCallBack;
    private GoogleMap mMap;

    public void setCallBack_sendClick(ListCallBack listCallBack_){
        this.listCallBack = listCallBack_;
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
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
            scoreboard_LST_scores.setAdapter(scoreAdapter);
            scoreboard_LST_scores.setLayoutManager(linearLayoutManager);
            scoreAdapter.setCallBack_sendClick(new ListCallBack() {
                @Override
                public void rowSelected(int rank, double lat, double lon) {

                }
            });
            scoreboard_LST_scores.setVisibility(View.VISIBLE);
        } else {
            scoreboard_LST_scores.setVisibility(View.GONE);
        }

        Record recordAtIndexOne = scores != null && scores.size() > 1 ? scores.get(1) : null;
        if (recordAtIndexOne != null) {
            Log.d("TAG", "Name: " + recordAtIndexOne.getName());
            Log.d("TAG", "Score: " + recordAtIndexOne.getScore());
            Log.d("TAG", "Location: " + recordAtIndexOne.getLon());
            Log.d("TAG", "Location: " + recordAtIndexOne.getLat());
        }
    }

    private void findViews(View view) {
        scoreboard_LST_scores = view.findViewById(R.id.scoreboard_LST_scores);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MapCallBack) {
            ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.score_FRAME_map)).getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    mMap = googleMap;
                }
            });
        }
    }
}
