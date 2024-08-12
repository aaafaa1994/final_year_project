package com.example.mctapp.Models;

public class Doctor {
    int doctor_id;
    String email;
    int job_id;
    String password;
    String full_name;
    int mobile_number;

    public Doctor(int doctor_id, String email, int job_id, String password, String full_name, int mobile_number) {
        this.doctor_id = doctor_id;
        this.email = email;
        this.job_id = job_id;
        this.password = password;
        this.full_name = full_name;
        this.mobile_number = mobile_number;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public String getEmail() {
        return email;
    }

    public int getJob_id() {
        return job_id;
    }

    public String getPassword() {
        return password;
    }

    public String getFull_name() {
        return full_name;
    }

    public int getMobile_number() {
        return mobile_number;
    }
}
