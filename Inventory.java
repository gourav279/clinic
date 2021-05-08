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
public class Inventory {
    int id;
    String name,brand,price,distributer,qty;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public String getPrice() {
        return price;
    }

    public String getDistributer() {
        return distributer;
    }

    public String getQty() {
        return qty;
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

    public void setPrice(String price) {
        this.price = price;
    }

    public void setDistributer(String distributer) {
        this.distributer = distributer;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public Inventory(int id, String name, String brand, String price, String distributer, String qty) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.distributer = distributer;
        this.qty = qty;
    }
    
}
