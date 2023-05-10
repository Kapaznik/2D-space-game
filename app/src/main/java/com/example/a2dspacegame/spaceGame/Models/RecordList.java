package com.example.a2dspacegame.spaceGame.Models;

import java.util.ArrayList;
import java.util.Collections;

public class RecordList {
    private ArrayList<Record> records = new ArrayList<>();

    public ArrayList<Record> getRecords() {
        return records;
    }

    public RecordList setRecords(ArrayList<Record> records){
        this.records = records;
        return this;
    }

    public void sortRecords(){
        Collections.sort(records);
    }
    public void clearRecords() {
        records.clear();
    }
}
