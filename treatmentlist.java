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
public class treatmentlist {

  String treatmenttype;
  double cost,discount,tcost;

    public treatmentlist(String treatmenttype, double cost, double discount, double tcost) {
        this.treatmenttype = treatmenttype;
        this.cost = cost;
        this.discount = discount;
        this.tcost = tcost;
    }

//    treatmentlist(String treatmenttype, double cost, double discount, double tcost) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

    public String getTreatmenttype() {
        return treatmenttype;
    }

    public double getCost() {
        return cost;
    }

    public double getDiscount() {
        return discount;
    }

    public double getTcost() {
        return tcost;
    }

    public void setTreatmenttype(String treatmenttype) {
        this.treatmenttype = treatmenttype;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public void setTcost(double tcost) {
        this.tcost = tcost;
    }

  
    
}
