package com.example.mctapp.Tools;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.mctapp.Models.Child;
import com.example.mctapp.Models.Doctor;
import com.example.mctapp.Models.Drug;
import com.example.mctapp.Models.Instruction;
import com.example.mctapp.Models.Mother;


public class SharedPreferenceManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;


    final String PREF_NAME = "PREFERENCE";
    int PRIVATE_MODE = 0;

    public SharedPreferenceManager(Context _context) {
        this.context = _context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void store_user(Mother mother) {
        editor.putInt("mother_id", mother.getMother_id());
        editor.putInt("mobile_number", mother.getMobile_number());
        editor.putString("user_f_name", mother.getFull_name());
        editor.putString("user_u_name", mother.getUser_name());
        editor.putString("user_email", mother.getEmail());
        editor.putString("user_password", mother.getPassword());
        editor.apply();
    }

    public int get_user_id() {
        return pref.getInt("mother_id", -1);
    }
    public int get_mobile_number() {
        return pref.getInt("mobile_number", -1);
    }

    public String get_user_f() {
        return pref.getString("user_f_name", "");
    }


    public String get_user_email() {
        return pref.getString("user_email", "");
    }

    public String get_user_password() {
        return pref.getString("user_password", "");
    }

    public String get_user_u_name() {
        return pref.getString("user_u_name", "");
    }


    public void store_doctor(Doctor mother) {
        editor.putInt("doctor_id", mother.getDoctor_id());
        editor.putInt("doctor_mobile_number", mother.getMobile_number());
        editor.putInt("doctor_job_id", mother.getJob_id());
        editor.putString("doctor_user_f_name", mother.getFull_name());
        editor.putString("doctor_user_email", mother.getEmail());
        editor.putString("doctor_user_password", mother.getPassword());
        editor.apply();
    }

    public int get_doctor_id() {
        return pref.getInt("doctor_id", -1);
    }
    public int get_doctor_job_id() {
        return pref.getInt("doctor_job_id", -1);
    }
    public int get_doctor_mobile_number() {
        return pref.getInt("doctor_mobile_number", -1);
    }

    public String get_doctor_user_u_name() {
        return pref.getString("doctor_user_f_name", "");
    }
    public String get_doctor_user_email() {
        return pref.getString("doctor_user_email", "");
    }

    public String get_doctor_user_password() {
        return pref.getString("doctor_user_password", "");
    }

    public void store_drug(Drug mother) {
        editor.putInt("drug_mother_id", mother.getMother_id());
        editor.putInt("drug_id", mother.getDrug_id());
        editor.putInt("drug_doctor_id", mother.getDoctor_id());
        editor.putString("drug_name", mother.getName());
        editor.putString("drug_does", mother.getDose());
        editor.putString("drug_time", mother.getTime());
        editor.apply();
    }
    public int get_drug_mother_id() {
        return pref.getInt("drug_mother_id", -1);
    }
    public int get_drug_id() {
        return pref.getInt("drug_id", -1);
    }
    public int get_drug_doctor_id() {
        return pref.getInt("drug_doctor_id", -1);
    }
    public String get_drug_name() {
        return pref.getString("drug_name", "");
    }
    public String get_drug_does() {
        return pref.getString("drug_does", "");
    }
    public String get_drug_time() {
        return pref.getString("drug_time", "");
    }


    public void store_instruction(Instruction mother) {
        editor.putInt("Instruction_id", mother.getId());
        editor.putInt("Instruction_doctor_id", mother.getDoctor_id());
        editor.putString("Instruction_age", mother.getAge());
        editor.putString("Instruction_desc", mother.getDescription());
        editor.apply();
    }
    public int get_Instruction_id() {
        return pref.getInt("Instruction_id", -1);
    }
    public int get_Instruction_doctor_id() {
        return pref.getInt("Instruction_doctor_id", -1);
    }
    public String get_Instruction_age() {
        return pref.getString("Instruction_age", "");
    }
    public String get_Instruction_desc() {
        return pref.getString("Instruction_desc", "");
    }

    public void store_child(Child mother) {
        editor.putInt("Child_id", mother.getChild_id());
        editor.putInt("Child_mother_id", mother.getMother_id());
        editor.putInt("Child_age", mother.getAge());
        editor.putString("Child_name", mother.getName());
        editor.apply();
    }
    public int get_Child_id() {
        return pref.getInt("Child_id", -1);
    }
    public int get_Child_age() {
        return pref.getInt("Child_age", -1);
    }
    public int get_Child_mother_id() {
        return pref.getInt("Child_mother_id", -1);
    }

    public String get_Child_name() {
        return pref.getString("Child_name", "");
    }
}
