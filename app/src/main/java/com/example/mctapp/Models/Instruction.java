package com.example.mctapp.Models;

public class Instruction {
    int id;
    String age;
    String description;
    int doctor_id;

    public Instruction(int id, String age, String description, int doctor_id) {
        this.id = id;
        this.age = age;
        this.description = description;
        this.doctor_id = doctor_id;
    }

    public int getId() {
        return id;
    }

    public String getAge() {
        return age;
    }

    public String getDescription() {
        return description;
    }

    public int getDoctor_id() {
        return doctor_id;
    }
}
