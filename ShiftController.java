/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dental;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTimePicker;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.util.StringConverter;
import javafx.util.converter.LocalTimeStringConverter;

/**
 * FXML Controller class
 *
 * @author doagu
 */
public class ShiftController implements Initializable {

    @FXML
    private JFXComboBox doctor;

    @FXML
    private JFXTimePicker shift1s;

    @FXML
    private JFXTimePicker shift1e;

    @FXML
    private CheckBox attendence;

    @FXML
    private JFXTimePicker shift2s;

    @FXML
    private JFXTimePicker shift2e;

    @FXML
    private JFXTimePicker shift3s;

    @FXML
    private JFXTimePicker shift3e;

    String t1, t12, t2, t22, t3, t32;

    @FXML
    void timeStart(ActionEvent event) {
        t1 = (shift1s.getValue() != null ? shift1s.getValue().toString() : "");
//        System.out.println(t1);
    }

    @FXML
    void timeEnd(ActionEvent event) {
        t12 = (shift1e.getValue() != null ? shift1e.getValue().toString() : "");
    }

    @FXML
    void timeStart2(ActionEvent event) {
        t2 = (shift2s.getValue() != null ? shift2s.getValue().toString() : "");
//       shift1e.setValue(t);
    }

    @FXML
    void timeEnd2(ActionEvent event) {
        t22 = (shift2e.getValue() != null ? shift2e.getValue().toString() : "");
    }

    @FXML
    void timeStart3(ActionEvent event) {
        t3 = (shift3s.getValue() != null ? shift3s.getValue().toString() : "");
//       shift1e.setValue(t);
    }

    @FXML
    void timeEnd3(ActionEvent event) {
        t32 = (shift3e.getValue() != null ? shift3e.getValue().toString() : "");
    }

    @FXML
    void sumit(ActionEvent event) {
        Connection conn = mysqlconnect.connectDb();
        String t, t21, t31;
        t = t1 + " to " + t12;
        t21 = t2 + " to " + t22;
        t31 = t3 + " to " + t32;
        String sql = String.format("select * from doctor where name='%s'", doctor.getValue());
//        System.out.println(sql);
        PreparedStatement pst;
        try {
            pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
//                System.out.println(rs.getInt("doctorid"));
                int i = rs.getInt("doctorid");
                String j = rs.getString("doctorId");
                PreparedStatement pst2 = conn.prepareStatement("select * from shift where doctorid=" + i);
                rs = pst2.executeQuery();

                if (rs.next()) {
//                    System.out.println(t1 + " " + t21 + " " + t31);
                    sql = String.format("update shift set shift1='%s',shift2='%s',shift3='%s', s1='%s' ,s12='%s' ,s2='%s' ,s22='%s', s3='%s', s32='%s' where doctorid=%d", t, t21, t31,t1,t12,t2,t22,t3,t32,i);
//                    System.out.println(sql);
//                    System.out.println("doctor is found");
                    pst2 = conn.prepareStatement(sql);
                    boolean b = pst2.execute();
//                    System.out.println(b);
                } else {
                    pst2 = conn.prepareStatement("insert into shift(shift1,shift2,shift3,s1,s12,s2,s22,s3,s32,doctorid) values (?,?,?,?,?,?,?,?,?,?)");
                    pst2.setString(1, t);
                    pst2.setString(2, t21);
                    pst2.setString(3, t31);
                    pst2.setString(4, t1);
                    pst2.setString(5, t12);
                    pst2.setString(6, t2);
                    pst2.setString(7, t22);
                    pst2.setString(8, t3);
                    pst2.setString(9, t32);
                    pst2.setString(10, j);
                    boolean b = pst2.execute();
//                    System.out.println(b);
                }
            }
        } catch (SQLException ex) {
//            System.out.println(ex);
        }

//        System.out.println(t1 + " " + t12 + " " + t2 + " " + t22 + " " + t3 + " " + t32);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> data = FXCollections.observableArrayList();
        Connection conn = mysqlconnect.connectDb();
        if (LoginController.t.equals("superadmin")) {
            String sql = "select * from doctor";
//            System.out.println(sql);
            PreparedStatement pst;
            try {
                pst = conn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();
//            System.out.println(rs.next());
                while (rs.next()) {
//                    System.out.println(rs.getString("name"));
                    data.add(rs.getString("name"));
                }
                doctor.setItems(data);
            } catch (SQLException ex) {
//                System.out.println(ex);
            }
        }else{
//            String sql = "select * from doctor where branch='"+LoginController.+"'";
//            System.out.println(sql);
//            PreparedStatement pst;
//            try {
//                pst = conn.prepareStatement(sql);
//                ResultSet rs = pst.executeQuery();
////            System.out.println(rs.next());
//                while (rs.next()) {
//                    System.out.println(rs.getString("name"));
//                    data.add(rs.getString("name"));
//                }
//                doctor.setItems(data);
//            } catch (SQLException ex) {
//                System.out.println(ex);
//            }
            
        }
        shift1s.setIs24HourView(true);
        shift1e.setIs24HourView(true);
        shift2s.setIs24HourView(true);
        shift2e.setIs24HourView(true);
        shift3s.setIs24HourView(true);
        shift3e.setIs24HourView(true);
    }

}
