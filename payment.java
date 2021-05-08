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
public class payment {
    
    String ID,pname,pnumber,pdate,ptamount,pstatus;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPnumber() {
        return pnumber;
    }

    public void setPnumber(String pnumber) {
        this.pnumber = pnumber;
    }

    public String getPdate() {
        return pdate;
    }

    public void setPdate(String pdate) {
        this.pdate = pdate;
    }

    public String getPtamount() {
        return ptamount;
    }

    public void setPtamount(String ptamount) {
        this.ptamount = ptamount;
    }

    public String getPstatus() {
        return pstatus;
    }

    public void setPstatus(String pstatus) {
        this.pstatus = pstatus;
    }

    public payment(String ID, String pname, String pnumber, String pdate, String ptamount, String pstatus) {
        this.ID = ID;
        this.pname = pname;
        this.pnumber = pnumber;
        this.pdate = pdate;
        this.ptamount = ptamount;
        this.pstatus = pstatus;
    }

   
    
}
