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
public class Debit {
    private String durdebitid ,distributername,number,totalamount,paidamount,dueamount;
    private String date,time,pamount,debitid;

    public String getDebitid() {
        return debitid;
    }

    public void setDebitid(String debitid) {
        this.debitid = debitid;
    }

    public Debit( String distributername, String number, String date, String time, String pamount) {
        this.debitid = debitid;
        this.distributername = distributername;
        this.number = number;
        this.date = date;
        this.time = time;
        this.pamount = pamount;
    }

    public Debit(String durdebitid, String distributername, String number, String paidamount, String dueamount, String totalamount) {
        this.durdebitid = durdebitid;
        this.distributername = distributername;
        this.number = number;
        this.totalamount = totalamount;
        this.paidamount = paidamount;
        this.dueamount = dueamount;
    }

    public String getDurdebitid() {
        return durdebitid;
    }

    public void setDurdebitid(String durdebitid) {
        this.durdebitid = durdebitid;
    }

    public String getDistributername() {
        return distributername;
    }

    public void setDistributername(String distributername) {
        this.distributername = distributername;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(String totalamount) {
        this.totalamount = totalamount;
    }

    public String getPaidamount() {
        return paidamount;
    }

    public void setPaidamount(String paidamount) {
        this.paidamount = paidamount;
    }

    public String getDueamount() {
        return dueamount;
    }

    public void setDueamount(String dueamount) {
        this.dueamount = dueamount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPamount() {
        return pamount;
    }

    public void setPamount(String pamount) {
        this.pamount = pamount;
    }
    
}
