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
public class Medicine {
    int id;
    String name,brand,mg,distributer;

    public void setDistributer(String distributer) {
        this.distributer = distributer;
    }

    public String getDistributer() {
        return distributer;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setMg(String mg) {
        this.mg = mg;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public String getMg() {
        return mg;
    }

    public Medicine(int id, String name, String brand, String mg, String distributer) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.mg = mg;
        this.distributer= distributer;
    }
    
}
