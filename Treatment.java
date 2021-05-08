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
public class Treatment {
    int id;
    String name,price,category,branch;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
    
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public String getBranch() {
        return branch;
    }
    
    public Treatment(int id, String name, String price, String category, String branch) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.branch = branch;
    }
    
}
