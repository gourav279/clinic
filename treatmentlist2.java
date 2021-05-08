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
public class treatmentlist2 {
   String treatmenttype,price,discount,total;

    public String getTreatmenttype() {
        return treatmenttype;
    }

    public void setTreatmenttype(String treatmenttype) {
        this.treatmenttype = treatmenttype;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public treatmentlist2(String treatmenttype, String price, String discount, String total) {
        this.treatmenttype = treatmenttype;
        this.price = price;
        this.discount = discount;
        this.total = total;
    }
}
