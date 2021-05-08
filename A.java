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
public class A {
    private String id,name,date,ptye,time;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPtye() {
        return ptye;
    }

    public void setPtye(String ptye) {
        this.ptye = ptye;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public A(String id, String name, String date, String ptye, String time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.ptye = ptye;
        this.time = time;
    }

   
}
