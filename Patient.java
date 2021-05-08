/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dental;

import java.time.LocalDate;
import java.sql.Date;

/**
 *
 * @author doagu
 */
public class Patient {
    int id;
    String name,number,branch,email,Address,doctorname,uhid,age,gender,rid;
    Date dob;

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getRid() {
        return rid;
    }


    public Patient(int id, String name, String number, String branch, String email, String Address, String doctorname,String uhid,String age,String gender,Date dob,String rid) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.branch = branch;
        this.email = email;
        this.Address = Address;
        this.doctorname = doctorname;
        this.uhid = uhid;
        this.age=age;
        this.gender=gender;
        this.dob=dob;
        this.rid=rid;
    }
    
    
   
    public String getDoctorname() {
        return doctorname;
    }

    public void setDoctorname(String doctorname) {
        this.doctorname = doctorname;
    }

    public Patient(int id, String name, String number, String branch, String email, String Address,String uhid,String age,String gender,Date dob,String rid) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.branch = branch;
        this.email = email;
        this.Address = Address;
        this.uhid = uhid;
        this.age=age;
        this.gender=gender;
        this.dob=dob;
        this.rid=rid;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Date getDob() {
        return dob;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public void setUhid(String uhid) {
        this.uhid = uhid;
    }

    public String getUhid() {
        return uhid;
    }

    public String getBranch() {
        return branch;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return Address;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }
    
}
