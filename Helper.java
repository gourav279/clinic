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
public class Helper {
    int id;
    String name,number,branch,email,Address,shift;

    public Helper(int id, String name, String number, String branch, String email, String Address, String shift) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.branch = branch;
        this.email = email;
        this.Address = Address;
        this.shift = shift;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String name) {
        this.name = name;
    }

    public void setPassword(String branch) {
        this.branch = branch;
    }

    public void setEmail(String branch) {
        this.branch = branch;
    }

    public void setType(String email) {
        this.email = email;
    }

    public void setAddress(String Address) {
        this.Address = Address;
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

    public Helper(int id, String name, String number, String branch, String email, String Address) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.branch = branch;
        this. email = email;
        this.Address = Address;
    }
}
