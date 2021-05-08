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
public class Branch {
    int id;
    String name,number,email,Address;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setEmail(String email) {
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

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return Address;
    }
    
    public Branch(int id, String name, String number, String email, String Address) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.email = email;
        this.Address = Address;
    }
    
}
