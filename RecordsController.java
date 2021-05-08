/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dental;

import com.jfoenix.controls.JFXDatePicker;
import static dental.ReceptionistDeshController.a;
import static dental.ReceptionistDeshController.t;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * FXML Controller class
 *
 * @author doagu
 */
public class RecordsController implements Initializable {

    @FXML
    private Button getrecords;

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    @FXML
    private JFXDatePicker to;
    @FXML
    private JFXDatePicker selectdate;
    @FXML
    private JFXDatePicker from;
    @FXML
    private Button getrecords1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO  
        getrecords1.setOnAction(e -> {
            if (from.getValue() != null && to.getValue() != null) {
                try {
                    LocalDate value1 = from.getValue();
                    LocalDate value2 = to.getValue();
                    String sql2 = "SELECT * from patient as p join transactionhistory as t where p.patientid=t.patientid and date between '" + value1 + "' and '" + value2 + "'";
                    Connection conna = mysqlconnect.connectDb();
                    PreparedStatement psta = conna.prepareStatement(sql2);
                    ResultSet rsa = psta.executeQuery();
                    Connection connx = null;
                    String sqlx = "select * from voucher where date between '" + value1 + "' and '" + value2 + "'";
                    PreparedStatement pstx = null;
                    connx = mysqlconnect.connectDb();
                    pstx = connx.prepareStatement(sqlx);
                    ResultSet rsx = pstx.executeQuery();
                    HSSFWorkbook wb = new HSSFWorkbook();
                    HSSFSheet sheet = wb.createSheet("Dental House");
                    HSSFRow header = sheet.createRow(0);
                    header.createCell(0).setCellValue("ID");
                    header.createCell(1).setCellValue("Name");
                    header.createCell(2).setCellValue("Time");
                    header.createCell(3).setCellValue("Amount");
                    header.createCell(4).setCellValue("Paid To");
                    header.createCell(5).setCellValue("Amount");
                    int index1 = 1;
                    while (rsa.next() && rsx.next()) {
                        HSSFRow row = sheet.createRow(index1);
                        row.createCell(0).setCellValue(rsa.getString("transactionId"));
                        row.createCell(1).setCellValue(rsa.getString("name"));
                        row.createCell(2).setCellValue(rsa.getString("time"));
                        row.createCell(3).setCellValue(rsa.getString("totalAmount"));
                        row.createCell(4).setCellValue(rsx.getString("paidto"));
                        row.createCell(5).setCellValue(rsx.getString("amount"));
                        index1++;
                    }
                    try {
                        int sum = 0;
                        int sumx = 0;
                        Connection connq = mysqlconnect.connectDb();
                        String sqlq = "Select sum(totalAmount) from transactionhistory where date between '" + value1 + "' and '" + value2 + "'";
                        String sqly = "Select sum(amount) from voucher where date between '" + value1 + "' and '" + value2 + "'";
                        PreparedStatement psr = connq.prepareStatement(sqlq);
                        PreparedStatement psy = connq.prepareStatement(sqly);
                        ResultSet rsw = psr.executeQuery();
                        ResultSet rsy = psy.executeQuery();
                        while (rsw.next() && rsy.next()) {
                            int c = rsw.getInt(1);
                            int d = rsy.getInt(1);
                            sum = sum + c;
                            sumx = sumx + d;
                            HSSFRow row = sheet.createRow(index1);
                            row.createCell(3).setCellValue(sum);
                            row.createCell(5).setCellValue(sumx);
                        }
                    } catch (Exception d) {
                        System.out.println(d);
                    }
//                int sumx= 0;
//                Connection conny = mysqlconnect.connectDb();
//                String sqly = "Select sum(amount) from voucher where date='" + value + "'";
//                PreparedStatement psy = conny.prepareStatement(sqly);
//                ResultSet rsy = psy.executeQuery();
//                while (rsy.next()) {
//                    int c = rsy.getInt(1);
//                    sumx = sumx + c;
//                    HSSFRow row3 = sheet.createRow(index1);
//                    row3.createCell(5).setCellValue(sumx);
//                }
                    String path = "C:\\Sheets\\Different Dates\\" + value1 + " to " + value2 + ".xls";
                    FileOutputStream fileout = new FileOutputStream(path);
                    wb.write(fileout);
                    fileout.close();
                    System.out.println("Sheet Created Successfully");
                    psta.close();
                    rsa.close();

                } catch (SQLException ex) {
                    Logger.getLogger(TreatmentController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(TreatmentController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(TreatmentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                Alert a = new Alert(Alert.AlertType.NONE);
                a.setAlertType(Alert.AlertType.ERROR);
                a.setContentText("Enter Value");
                a.show();
            }
        });

        getrecords.setOnAction(e -> {
            if (selectdate.getValue() != null) {
                try {
                    LocalDate value = selectdate.getValue();
                    String sql = "Select * from transactionhistory as t join patient as p where t.patientid=p.patientid and date='" + value + "'";
                    conn = mysqlconnect.connectDb();
                    PreparedStatement pst1 = conn.prepareStatement(sql);
                    ResultSet rs1 = pst1.executeQuery();
                    Connection connx = null;
                    String sqlx = "select * from voucher where date='" + value + "'";
                    PreparedStatement pstx = null;
                    connx = mysqlconnect.connectDb();
                    pstx = connx.prepareStatement(sqlx);
                    ResultSet rsx = pstx.executeQuery();
                    HSSFWorkbook wb = new HSSFWorkbook();
                    HSSFSheet sheet = wb.createSheet("Dental House");
                    HSSFRow header = sheet.createRow(0);
                    header.createCell(0).setCellValue("ID");
                    header.createCell(1).setCellValue("Name");
                    header.createCell(2).setCellValue("Time");
                    header.createCell(3).setCellValue("Amount");
                    header.createCell(4).setCellValue("Paid To");
                    header.createCell(5).setCellValue("Amount");
                    int index1 = 1;
                    while (rs1.next() && rsx.next()) {
                        HSSFRow row = sheet.createRow(index1);
                        row.createCell(0).setCellValue(rs1.getString("transactionId"));
                        row.createCell(1).setCellValue(rs1.getString("name"));
                        row.createCell(2).setCellValue(rs1.getString("time"));
                        row.createCell(3).setCellValue(rs1.getString("totalAmount"));
                        row.createCell(4).setCellValue(rsx.getString("paidto"));
                        row.createCell(5).setCellValue(rsx.getString("amount"));
                        index1++;
                    }
                    try {
                        int sum = 0;
                        int sumx = 0;
                        Connection connq = mysqlconnect.connectDb();
                        String sqlq = "Select sum(totalAmount) from transactionhistory where date='" + value + "'";
                        String sqly = "Select sum(amount) from voucher where date='" + value + "'";
                        PreparedStatement psr = connq.prepareStatement(sqlq);
                        PreparedStatement psy = connq.prepareStatement(sqly);
                        ResultSet rsw = psr.executeQuery();
                        ResultSet rsy = psy.executeQuery();
                        while (rsw.next() && rsy.next()) {
                            int c = rsw.getInt(1);
                            int d = rsy.getInt(1);
                            sum = sum + c;
                            sumx = sumx + d;
                            HSSFRow row = sheet.createRow(index1);
                            row.createCell(3).setCellValue(sum);
                            row.createCell(5).setCellValue(sumx);
                        }
                    } catch (Exception d) {
                        System.out.println(d);
                    }
//                int sumx= 0;
//                Connection conny = mysqlconnect.connectDb();
//                String sqly = "Select sum(amount) from voucher where date='" + value + "'";
//                PreparedStatement psy = conny.prepareStatement(sqly);
//                ResultSet rsy = psy.executeQuery();
//                while (rsy.next()) {
//                    int c = rsy.getInt(1);
//                    sumx = sumx + c;
//                    HSSFRow row3 = sheet.createRow(index1);
//                    row3.createCell(5).setCellValue(sumx);
//                }
                    String path = "C:\\Sheets\\Fixed Dates\\" + value + ".xls";
                    FileOutputStream fileout = new FileOutputStream(path);
                    wb.write(fileout);
                    fileout.close();
                    System.out.println("Sheet Created Successfully");
                    pst1.close();
                    rs1.close();

                } catch (SQLException ex) {
                    Logger.getLogger(TreatmentController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(TreatmentController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(TreatmentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                Alert a = new Alert(Alert.AlertType.NONE);
                a.setAlertType(Alert.AlertType.ERROR);
                a.setContentText("Enter Value");
                a.show();
            }
        });
//        getrecords.setOnAction(e -> {
//            try {
//                String sql = "Select * from transactionhistory";
//                conn = mysqlconnect.connectDb();
//                pst = conn.prepareStatement(sql);
//                rs = pst.executeQuery();
//                HSSFWorkbook wb = new HSSFWorkbook();
//                HSSFSheet sheet = wb.createSheet("January");
//                HSSFRow header = sheet.createRow(0);
//                header.createCell(0).setCellValue("ID");
//                header.createCell(1).setCellValue("Name");
//                header.createCell(2).setCellValue("Number");
//                int index1 = 1;
//                while (rs.next()) {
//                    HSSFRow row = sheet.createRow(index1);
//                    row.createCell(0).setCellValue(rs.getString("transactionId"));
//                    row.createCell(1).setCellValue(rs.getString("patientId"));
//                    row.createCell(2).setCellValue(rs.getString("totalAmount"));
//                    index1++;
//                }
//                System.out.println("hello");
//                int sum = 0;
//                conn = mysqlconnect.connectDb();
////             String sql1 ="select * from transactionhistory as t join patient as p where t.patientid=p.patientid";
//                String sql1 = "Select sum(totalAmount) from transactionhistory";
//                PreparedStatement ps = conn.prepareStatement(sql1);
//                ResultSet rs = ps.executeQuery();
//                while (rs.next()) {
//                    int c = rs.getInt(1);
//                    sum = sum + c;
//                    HSSFRow row = sheet.createRow(index1);
//                    row.createCell(2).setCellValue(sum);
//                }
//                LocalDate date = LocalDate.now();
//                String path = "C:\\Users\\doagu\\OneDrive\\Desktop\\java\\" + date + ".xls";
//                FileOutputStream fileout = new FileOutputStream(path);
//                wb.write(fileout);
//                fileout.close();
//                System.out.println("Sheet Created Successfully");
//                pst.close();
//                rs.close();
//
//            } catch (SQLException ex) {
//                Logger.getLogger(TreatmentController.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (FileNotFoundException ex) {
//                Logger.getLogger(TreatmentController.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (IOException ex) {
//                Logger.getLogger(TreatmentController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        });
    }

}
