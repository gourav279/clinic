/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dental;

/**
 *
 * @author doaguru
 */
public class Emi {
    private String name,number,email,totalAmount, pamount, dueamount,id,id2;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPamount() {
        return pamount;
    }

    public void setPamount(String pamount) {
        this.pamount = pamount;
    }

    public String getDueamount() {
        return dueamount;
    }

    public void setDueamount(String dueamount) {
        this.dueamount = dueamount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId2() {
        return id2;
    }

    public void setId2(String id2) {
        this.id2 = id2;
    }

    public Emi(String name, String number, String email, String totalAmount, String pamount, String dueamount, String id, String id2) {
        this.name = name;
        this.number = number;
        this.email = email;
        this.totalAmount = totalAmount;
        this.pamount = pamount;
        this.dueamount = dueamount;
        this.id = id;
        this.id2 = id2;
    }

}
