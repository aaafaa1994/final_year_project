package com.example.mctapp.Models;

public class Mother {
    int mother_id;
    String email;
    String full_name;
    String user_name;
    String password;
    int mobile_number;

    public Mother(int mother_id, String email, String full_name, String user_name, String password, int mobile_number) {
        this.mother_id = mother_id;
        this.email = email;
        this.full_name = full_name;
        this.user_name = user_name;
        this.password = password;
        this.mobile_number = mobile_number;
    }

    public int getMother_id() {
        return mother_id;
    }

    public String getEmail() {
        return email;
    }

    public String getFull_name() {
        return full_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getPassword() {
        return password;
    }

    public int getMobile_number() {
        return mobile_number;
    }
}
