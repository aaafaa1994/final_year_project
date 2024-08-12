package com.example.mctapp.Models;

public class Drug {
    int drug_id;
    String name;
    String dose;

    String time;

    int doctor_id;
    int mother_id;


    public Drug(int drug_id, String name, String dose, String time, int doctor_id, int mother_id) {
        this.drug_id = drug_id;
        this.name = name;
        this.dose = dose;
        this.time = time;
        this.doctor_id = doctor_id;
        this.mother_id = mother_id;
    }

    public int getDrug_id() {
        return drug_id;
    }

    public String getName() {
        return name;
    }

    public String getDose() {
        return dose;
    }

    public String getTime() {
        return time;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public int getMother_id() {
        return mother_id;
    }
}
