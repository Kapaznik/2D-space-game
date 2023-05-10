package com.example.a2dspacegame.spaceGame.Fragments;
import com.example.a2dspacegame.spaceGame.Activities.GameActivity;
import com.example.a2dspacegame.spaceGame.Models.RecordList;
import com.example.a2dspacegame.spaceGame.utilities.MySPv3;
import com.example.a2dspacegame.spaceGame.utilities.SignalGenerator;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.GoogleMap;
import com.example.a2dspacegame.spaceGame.CallBacks.MapCallBack;
import com.example.a2dspacegame.spaceGame.Models.Record;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.example.a2dspacegame.R;
import com.google.gson.Gson;import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.example.a2dspacegame.spaceGame.CallBacks.MapCallBack;


import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.CameraPosition;

import com.google.android.gms.maps.model.Marker;


import java.util.ArrayList;

public class MapFragment extends SupportMapFragment implements OnMapReadyCallback {
    private GoogleMap mMap;
    private ArrayList<Marker> markers;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Set the map's camera position to a specific coordinate
        LatLng coordinate = new LatLng(37.7749, -122.4194); // San Francisco
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(coordinate, 12);
        mMap.moveCamera(cameraUpdate);
    }
    public void zoomOnRecord(int rank, double x, double y) {

        LatLng point = new LatLng(x, y);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(point).zoom(12).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        markers.get(rank-1).showInfoWindow();

    }
}
