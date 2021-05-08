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
public class medicinelist {
   private String medicine;
   private String dosages;
   private String frequency;
   private String duration;
   private String notes;

    public String getMedicine() {
        return medicine;
    }

    public String getDosages() {
        return dosages;
    }

    public String getFrequency() {
        return frequency;
    }

    public String getDuration() {
        return duration;
    }

    public String getNotes() {
        return notes;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public void setDosages(String dosages) {
        this.dosages = dosages;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public medicinelist(String medicine, String dosages, String frequency, String duration, String notes) {
        this.medicine = medicine;
        this.dosages = dosages;
        this.frequency = frequency;
        this.duration = duration;
        this.notes = notes;
    }
    
    
}
