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
public class Appointment {
    int id;
    String pname,dname;

    public void setId(int id) {
        this.id = id;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public int getId() {
        return id;
    }

    public String getPname() {
        return pname;
    }

    public String getDname() {
        return dname;
    }

    public Appointment(int id, String pname) {
        this.id = id;
        this.pname = pname;
    }
}
