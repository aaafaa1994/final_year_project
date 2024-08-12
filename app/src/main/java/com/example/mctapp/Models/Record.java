package com.example.mctapp.Models;

public class Record {
    int record_id;
    int degree;
    String date;
    String time;
    int mother_id;
    String child_name;


    public Record(int record_id, int degree, String date, String time, int mother_id, String child_name) {
        this.record_id = record_id;
        this.degree = degree;
        this.date = date;
        this.time = time;
        this.mother_id = mother_id;
        this.child_name = child_name;
    }

    public String getChild_name() {
        return child_name;
    }

    public int getRecord_id() {
        return record_id;
    }

    public int getDegree() {
        return degree;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public int getMother_id() {
        return mother_id;
    }
}
