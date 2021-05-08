/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dental;

/**
 *
 * @author doagu
 */
public class Doctor {
    int id;
    String name,number,branch,email,Address,type,shift;

    public Doctor(int id, String name, String number, String branch, String email, String Address, String type, String shift) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.branch = branch;
        this.email = email;
        this.Address = Address;
        this.type = type;
        this.shift = shift;
    }

    public Doctor(int id, String name, String number, String shift) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.shift = shift;
    }

    public Doctor(String name, String number, String shift) {
        this.name = name;
        this.number = number;
        this.shift = shift;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public Doctor(int id,String name, String number) {
        this.name = name;
        this.number = number;
        this.id=id;
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

    public void setType(String type) {
        this.type = type;
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

    public String getBranch() {
        return branch;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return Address;
    }

    public String getType() {
        return type;
    }
    
    public Doctor(int id, String name, String number, String branch, String email, String Address, String type) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.branch = branch;
        this.email = email;
        this.Address = Address;
        this.type = type;
    }
//    public Doctor(String name,String number){
//        this.name = name;
//        this.number = number;
//    }
}
