package com.example.mctapp.Models;

public class Child {
    int child_id;
    int mother_id;
    String name;
    int age;

    public Child(int child_id, int mother_id, String name, int age) {
        this.child_id = child_id;
        this.mother_id = mother_id;
        this.name = name;
        this.age = age;
    }

    public int getChild_id() {
        return child_id;
    }

    public int getMother_id() {
        return mother_id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
