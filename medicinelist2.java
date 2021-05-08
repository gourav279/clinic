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
public class medicinelist2 {
    String mname,mdosages,mfrequency,mduration,mnote;

    public String getMname() {
        return mname;
    }

    public String getMdosages() {
        return mdosages;
    }

    public String getMfrequency() {
        return mfrequency;
    }

    public String getMduration() {
        return mduration;
    }

    public String getMnote() {
        return mnote;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public void setMdosages(String mdosages) {
        this.mdosages = mdosages;
    }

    public void setMfrequency(String mfrequency) {
        this.mfrequency = mfrequency;
    }

    public void setMduration(String mduration) {
        this.mduration = mduration;
    }

    public void setMnote(String mnote) {
        this.mnote = mnote;
    }

    public medicinelist2(String mname, String mdosages, String mfrequency, String mduration, String mnote) {
        this.mname = mname;
        this.mdosages = mdosages;
        this.mfrequency = mfrequency;
        this.mduration = mduration;
        this.mnote = mnote;
    }
}
