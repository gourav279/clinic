/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dental;

import com.jfoenix.controls.JFXDatePicker;
import static dental.DocControllerDash.dt;
import static dental.DocControllerDash.pdf1;
import static dental.mysqlconnect.connectDb;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javax.swing.JOptionPane;
import javafx.util.Callback;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author doagu
 */
public class AccountantDashController implements Initializable {

    @FXML
    private Button log_btn;

    @FXML
    private AnchorPane profilePane, recordpane;

    @FXML
    private Label name;

    @FXML
    private Label moblie;

    @FXML
    private Label email2;

    @FXML
    private Label branch;

    @FXML
    private Label address;

    @FXML
    private TableView<TxHistory> txList;

    @FXML
    private Label lblname;

    @FXML
    private Label lblnumber;

    @FXML
    private Label lblamount;

    @FXML
    private TextField textCheckUp;

    @FXML
    private TextField textpayamount;

    String id, ta, ca = "0";
    int tc;
    @FXML
    private Pane rec;
    @FXML
    private JFXDatePicker selectdate;
    @FXML
    private Button getrecords;
    @FXML
    private JFXDatePicker from;
    @FXML
    private JFXDatePicker to;
    @FXML
    private Button getrecords1;

    @FXML
    void addCUF() {
        ca = textCheckUp.getText();
        tc = Integer.parseInt(ca) + Integer.parseInt(ta);
        lbltamount.setText(String.valueOf(tc));
    }

    @FXML
    private TableView<Debit> dd_list;

    @FXML
    private TableColumn<Debit, String> dd_dn;

    @FXML
    private TableColumn<Debit, String> dd_num;

    @FXML
    private TableColumn<Debit, String> dd_pn;

    @FXML
    private TableColumn<Debit, String> dd_da;

    @FXML
    private TableColumn<Debit, String> dd_ta;

    @FXML
    private TableColumn<Debit, String> ddid;

    @FXML
    private TableColumn colcreditbtn;

    public ObservableList<Debit> getDebit() {
        Connection conn = connectDb();

        ObservableList<Debit> list = FXCollections.observableArrayList();
        try {
            String sql = "select * from duedebit";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Debit(rs.getString("durdebitid"), rs.getString("distributername"), rs.getString("number"), rs.getString("paidamount"), rs.getString("dueamount"), rs.getString("totalamount")));
            }
        } catch (Exception e) {
//            System.out.print("Exception " + e);
        }
        return list;
    }
    ObservableList<Debit> listM2, listM3, listM4;

    public void UpdateTable2() {
        ddid.setCellValueFactory(new PropertyValueFactory<Debit, String>("durdebitid"));
        dd_dn.setCellValueFactory(new PropertyValueFactory<Debit, String>("distributername"));
        dd_num.setCellValueFactory(new PropertyValueFactory<Debit, String>("number"));
        dd_pn.setCellValueFactory(new PropertyValueFactory<Debit, String>("paidamount"));
        dd_da.setCellValueFactory(new PropertyValueFactory<Debit, String>("dueamount"));
        dd_ta.setCellValueFactory(new PropertyValueFactory<Debit, String>("totalamount"));

        listM2 = getDebit();
        dd_list.setItems(listM2);

    }

    //transition History
    @FXML
    private TableView<Debit> th_list;

    @FXML
    private TableColumn<Debit, String> th_dn;

    @FXML
    private TableColumn<Debit, String> th_num;

    @FXML
    private TableColumn<Debit, String> th_dt;

    @FXML
    private TableColumn<Debit, String> th_tt;

    @FXML
    private TableColumn<Debit, String> th_am;

    public ObservableList<Debit> getTransctionHistory() {
        Connection conn = connectDb();

        ObservableList<Debit> list = FXCollections.observableArrayList();
        try {
            String sql = "select * from debithistory";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Debit(rs.getString("distributername"), rs.getString("number"), rs.getString("date"), rs.getString("time"), rs.getString("pamount")));
            }
        } catch (Exception e) {

        }
        return list;
    }

    public void UpdateTable3() {
        th_dn.setCellValueFactory(new PropertyValueFactory<Debit, String>("distributername"));
        th_num.setCellValueFactory(new PropertyValueFactory<Debit, String>("number"));
        th_dt.setCellValueFactory(new PropertyValueFactory<Debit, String>("date"));
        th_tt.setCellValueFactory(new PropertyValueFactory<Debit, String>("time"));
        th_am.setCellValueFactory(new PropertyValueFactory<Debit, String>("pamount"));
        listM3 = getTransctionHistory();
        th_list.setItems(listM3);

    }
    static String pdf;

    @FXML
    void printinvoice() throws SQLException, JRException, FileNotFoundException {
        invoicecc();
        Connection conn = mysqlconnect.connectDb();
        String sql = "insert into transactionhistory (patientid, date, time, totalamount, invoicepdf)values(?,?,?,?,?)";
        try {

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, id);
            java.sql.Date d = java.sql.Date.valueOf(dada);
            pst.setDate(2, d);
            pst.setString(3, titi);
            pst.setString(4, textpayamount.getText());
            File file = new File(pdf);
            FileInputStream input = new FileInputStream(file);
            pst.setBinaryStream(5, input);
            pst.execute();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        int A = Integer.parseInt(textpayamount.getText());
        int B = Integer.parseInt(lbltamount.getText());
        if (B - A > 200) {
            Connection con3 = mysqlconnect.connectDb();
            String sql3 = "update treatmenthistory set paymentstatus='EMI Based' where patientid='" + id + "' and paymentstatus= 'pending'";
            try {

                PreparedStatement pst3 = con3.prepareStatement(sql3);
                pst3.execute();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
        if (B - A < 200) {
            Connection con4 = mysqlconnect.connectDb();
            String sql4 = "update treatmenthistory set paymentstatus='Payment Done' where patientid='" + id + "' and paymentstatus= 'pending'";
            try {

                PreparedStatement pst4 = con4.prepareStatement(sql4);
                pst4.execute();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }

        if (B - A > 200) {
            Connection con2 = mysqlconnect.connectDb();
            String sql2 = "insert into emi (patientid,dueamount,totalamount,paidamount)values(?,?,?,?)";
            try {

                PreparedStatement pst2 = conn.prepareStatement(sql2);
                pst2.setString(1, id);
                pst2.setString(2, String.valueOf(B - A));
                pst2.setString(3, String.valueOf(B));
                pst2.setString(4, String.valueOf(A));
                pst2.execute();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
        updateTable();
        updateTable3();
        clearabc();
    }

    @FXML
    private Label lbltamount;

    @FXML
    private TableColumn<TxHistory, String> col_name;

    @FXML
    private TableColumn<TxHistory, String> col_mob;

    @FXML
    private TableColumn<TxHistory, String> col_mail;

    @FXML
    private TableColumn<TxHistory, String> col_date;

    @FXML
    private TableColumn<TxHistory, String> col_time;

    @FXML
    private TableColumn<TxHistory, String> col_total_amount;

    @FXML
    private TableColumn<TxHistory, String> colididid;

    @FXML
    private TableColumn colinvoice;

    @FXML
    private AnchorPane transctionList;

    @FXML
    private TextField filterField;

    ObservableList<TxHistory> listM;
    ObservableList<product> listproduct;

    public void updateTable() {
        colididid.setCellValueFactory(new PropertyValueFactory("id"));
        col_name.setCellValueFactory(new PropertyValueFactory("name"));
        col_mob.setCellValueFactory(new PropertyValueFactory("number"));
        col_mail.setCellValueFactory(new PropertyValueFactory("email"));
        col_date.setCellValueFactory(new PropertyValueFactory("date"));
        col_time.setCellValueFactory(new PropertyValueFactory("time"));
        col_total_amount.setCellValueFactory(new PropertyValueFactory("dueAmount"));
        listM = getTransction();
        txList.setItems(listM);
    }

    @FXML
    void transctionWindow(ActionEvent event) {
        transctionList.setVisible(true);
        rec.setVisible(false);
        updateTable();
    }

    void search_user() {
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_mob.setCellValueFactory(new PropertyValueFactory<TxHistory, String>("number"));
        col_mail.setCellValueFactory(new PropertyValueFactory<TxHistory, String>("email"));
        col_date.setCellValueFactory(new PropertyValueFactory<TxHistory, String>("date"));
        col_time.setCellValueFactory(new PropertyValueFactory<TxHistory, String>("time"));
        col_total_amount.setCellValueFactory(new PropertyValueFactory<TxHistory, String>("dueAmount"));
        listM = getTransction();
        txList.setItems(listM);
        FilteredList<TxHistory> filteredData = new FilteredList<>(listM, b -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (person.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches username
                } else if (person.getNumber().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches password
                } else if (person.getEmail().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches password
                } else {
                    return false; // Does not match.
                }
            });
        });
        SortedList<TxHistory> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(txList.comparatorProperty());
        txList.setItems(sortedData);
    }

    public static ObservableList<TxHistory> getTransction() {
        Connection conn = mysqlconnect.connectDb();
        ObservableList<TxHistory> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from transactionhistory as t join patient as p where t.patientId=p.Patientid");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                list.add(new TxHistory(rs.getString("transactionid"), rs.getString("name"), rs.getString("number"), rs.getString("email"), rs.getString("date"), rs.getString("time"), rs.getString("totalAmount")));
            }
        } catch (Exception e) {

        }
        return list;
    }

    @FXML
    void closeWindow(ActionEvent event) {
        profilePane.setVisible(false);
        transctionList.setVisible(false);
        emiPane.setVisible(false);
        rec.setVisible(true);
    }

    ObservableList<Emi> listM1;

    @FXML
    private TableView<Emi> emiList;

    @FXML
    private TableColumn<Emi, String> col_name1;

    @FXML
    private TableColumn<Emi, String> col_mob1;

    @FXML
    private TableColumn<Emi, String> col_mail1;

    @FXML
    private TableColumn<Emi, String> col_due_amount1;

    @FXML
    private TableColumn<Emi, String> col_total_amount1;

    @FXML
    private TableColumn<Emi, String> colpamount;

    @FXML
    private TableColumn<Emi, String> coleid;

    @FXML
    private TableColumn<Emi, String> colidid;
    @FXML
    private TableColumn button;

    @FXML
    private AnchorPane emiPane;

    @FXML
    void emiWindow(ActionEvent event) {
        updateTable2();
        emiPane.setVisible(true);
        rec.setVisible(false);
    }

    public void updateTable2() {
        col_name1.setCellValueFactory(new PropertyValueFactory("name"));
        col_mob1.setCellValueFactory(new PropertyValueFactory("number"));
        col_mail1.setCellValueFactory(new PropertyValueFactory("email"));
        col_total_amount1.setCellValueFactory(new PropertyValueFactory("totalAmount"));
        colpamount.setCellValueFactory(new PropertyValueFactory("pamount"));
        col_due_amount1.setCellValueFactory(new PropertyValueFactory("dueamount"));
        coleid.setCellValueFactory(new PropertyValueFactory("id"));
        colidid.setCellValueFactory(new PropertyValueFactory("id2"));
        listM1 = getEmi();
        emiList.setItems(listM1);
    }

    public static ObservableList<Emi> getEmi() {
        Connection conn = mysqlconnect.connectDb();
        ObservableList<Emi> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from emi as e join patient as p where e.patientId=p.Patientid");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
//                System.out.println(rs.getString("name") + " " + rs.getString("number") + " " + rs.getString("dueamount"));
                list.add(new Emi(rs.getString("name"), rs.getString("number"), rs.getString("email"), rs.getString("totalAmount"), rs.getString("paidamount"), rs.getString("dueamount"), rs.getString("patientid"), rs.getString("emiid")));
            }
        } catch (Exception e) {

        }
        return list;
    }
    @FXML
    private AnchorPane paymentpane;

    @FXML
    private AnchorPane duedebitpane;

    @FXML
    void duedebitpaneshow(ActionEvent event) {
        rec.setVisible(false);
        duedebitpane.setVisible(true);
    }

    @FXML
    void backpane2(ActionEvent event) {
        rec.setVisible(true);
        duedebitpane.setVisible(false);
    }

    @FXML
    private AnchorPane debithistorypane;

    @FXML
    void debithistorypaneshow(ActionEvent event) {
        rec.setVisible(false);
        debithistorypane.setVisible(true);
    }

    @FXML
    void backpane3(ActionEvent event) {
        rec.setVisible(true);
        debithistorypane.setVisible(false);
    }

    @FXML
    private AnchorPane bankpane;

    void bankpaneshow(ActionEvent event) {

        bankpane.setVisible(true);
    }

    @FXML
    void backpane4(ActionEvent event) {
        rec.setVisible(true);
        bankpane.setVisible(false);
    }

    @FXML
    private AnchorPane newpurchasepane;

    @FXML
    void newpurchasepaneshow(ActionEvent event) {
        rec.setVisible(false);
        newpurchasepane.setVisible(true);
        disName.clear();
        disNumber.clear();
        lblDTA.setText(null);
        lblCPA.clear();
        tableproduct.setItems(null);
        val = 0;
    }

    @FXML
    void backpane5(ActionEvent event) {
        rec.setVisible(true);
        newpurchasepane.setVisible(false);
    }

    @FXML
    void paymentpaneshow(ActionEvent event) {
        clearabc();
        rec.setVisible(false);
        paymentpane.setVisible(true);
        updateTable();
        search_user();
        updateTable2();
        UpdateTable2();
        UpdateTable3();
        updateTable3();
    }

    @FXML
    void backpane(ActionEvent event) {
        rec.setVisible(true);
        paymentpane.setVisible(false);
    }

    /*End  EMI Session*/
    void handelpatient(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Patient.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("TxHistory window");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {

        }
    }

    @FXML
    private Pane changePane;

    @FXML
    void changeWindow(ActionEvent event) {
        changePane.setVisible(true);
    }

    @FXML
    void ForgetWindow(ActionEvent event) {
        if (p.getText().equals(cp.getText())) {
            Connection conn = mysqlconnect.connectDb();
            String sql = "update accountant set password= ? where AccountantId=" + LoginController.id;
            try {

                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, p.getText());
                pst.execute();
                Window owner = log_btn.getScene().getWindow();
                showAlert(Alert.AlertType.CONFIRMATION, owner, "Sucessfully Changed Password",
                        "Password is Changed");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        } else {
            Window owner = log_btn.getScene().getWindow();
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Password is not same");
            return;
        }

        profilePane.setVisible(false);
        changePane.setVisible(false);
        p.clear();
        cp.clear();
    }

    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();//To change body of generated methods, choose Tools | Templates.
    }
    private TextField p;

    private TextField cp;

    @FXML
    void handleProfile(ActionEvent event) {
        profilePane.setVisible(true);
        Connection conn = mysqlconnect.connectDb();
        String sql = String.format("Select * from %s where name='%s' and password='%s'", LoginController.t, LoginController.u, LoginController.p);

        Statement st;
        try {
            st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                name.setText(rs.getString("name"));
                moblie.setText(rs.getString("number"));
                branch.setText(rs.getString("branch"));
                address.setText(rs.getString("address"));
                email2.setText(rs.getString("Email"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void logOut(ActionEvent event) throws IOException {
        log_btn.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Stage mainStage = new Stage();
        Scene scene = new Scene(root);
        mainStage.setTitle("LogIn Page");
        mainStage.setScene(scene);
        mainStage.show();
    }

    @FXML
    private TableView<payment> tblPayment;

    @FXML
    private TableColumn<payment, String> colPName;

    @FXML
    private TableColumn<payment, String> colMno;

    @FXML
    private TableColumn<payment, String> colTdate;

    @FXML
    private TableColumn<payment, String> colTA;

    @FXML
    private TableColumn<payment, String> colPS;

    @FXML
    private TableColumn<payment, String> colAid;

    @FXML
    private TableColumn colpbtn;

    ObservableList<payment> listPay;

    public void updateTable3() {
        colAid.setCellValueFactory(new PropertyValueFactory<>("ID"));
        colPName.setCellValueFactory(new PropertyValueFactory<>("pname"));
        colMno.setCellValueFactory(new PropertyValueFactory<>("pnumber"));
        colTdate.setCellValueFactory(new PropertyValueFactory<>("pdate"));
        colTA.setCellValueFactory(new PropertyValueFactory<>("ptamount"));
        colPS.setCellValueFactory(new PropertyValueFactory<>("pstatus"));

        listPay = mysqlconnect.getpayment();
        tblPayment.setItems(listPay);
    }

    @FXML
    private ComboBox<String> comboPN;

    int val;

    @FXML
    private TextField textQt;

    @FXML
    private TextField textP;

    @FXML
    private TextField textA;

    @FXML
    private Button btnPlus;

    @FXML
    private TableView<product> tableproduct;

    @FXML
    private TableColumn<product, String> colProName;

    @FXML
    private TableColumn<product, String> colQTY;

    @FXML
    private TableColumn<product, String> colProPrice;

    @FXML
    private TableColumn<product, String> colProAmout;

    @FXML
    private TableColumn colProbtn;

    @FXML
    private TextField disName;

    @FXML
    private TextField disNumber;

    @FXML
    private Label lblDTA;

    @FXML
    private TextField lblCPA;

    public void folder() {
        String dir = "C:/dentalHouse/" + dt + "";
        File file = new File(dir);
        file.mkdirs();

    }

    public void clearabc() {
        lblname.setText(null);
        lblnumber.setText(null);
        lblamount.setText(null);
        textCheckUp.setText(null);
        lbltamount.setText(null);
        textpayamount.clear();
    }

    public void invoicecc() throws SQLException, JRException {
        folder();
        Connection conn = mysqlconnect.connectDb();
        PreparedStatement pst;
        String sql = "select * from prescription";
        pst = conn.prepareStatement(sql);
        JasperDesign j = JRXmlLoader.load("newinvoice.jrxml");
        JRDesignQuery update = new JRDesignQuery();
        update.setText(sql);
        j.setQuery(update);
        HashMap<String, Object> para = new HashMap<>();
//        System.out.println(lblDPNN.getText() + " " + lblPMNN.getText() + " " + txtPPAN.getText());
        para.put("parameter1", lblname.getText());

        para.put("parameter2", lblnumber.getText());
        para.put("parameter3", lblamount.getText());
        para.put("parameter4", textCheckUp.getText());
        para.put("parameter5", lbltamount.getText());
        para.put("parameter6", textpayamount.getText());
        para.put("rid", "RG_" + id);
        para.put("pid", "UH_" + id);
        int ab = Integer.parseInt(lbltamount.getText()) - Integer.parseInt(textpayamount.getText());
        para.put("parameter7", String.valueOf(ab));
        JasperReport jr = JasperCompileManager.compileReport(j);
        JasperPrint jp = JasperFillManager.fillReport(jr, para, conn);
        String exportPath = "C:/dentalhouse/report.pdf";
        JasperExportManager.exportReportToPdfFile(jp, exportPath);
        pdf = exportPath;
        JasperViewer.viewReport(jp, false);

    }

    public void emiinvoice() throws SQLException, JRException {
        folder();
//        String sourceFile;
//        sourceFile = "C:\\Users\\doagu\\Documents\\NetBeansProjects\\dental\\src\\dental\\invoiceemi.jrxml";
        Connection conn = mysqlconnect.connectDb();
        PreparedStatement pst;
        String sql = "select * from  transactionhistory as t join patient as p where t.patientId= p.patientId";
        pst = conn.prepareStatement(sql);
        JasperDesign j = JRXmlLoader.load("reportInvoice.jrxml");
        JRDesignQuery update = new JRDesignQuery();
        update.setText(sql);
        j.setQuery(update);
        HashMap<String, Object> para = new HashMap<>();
//        System.out.println(lblDPNN.getText() + " " + lblPMNN.getText() + " " + txtPPAN.getText());
        para.put("pname", lblDPNN.getText());
        para.put("parameter2", lblPMNN.getText());
        para.put("parameter3", txtPPAN.getText());
        para.put("parameter4", lblPDAN.getText());
        int aa = Integer.parseInt(lblPDAN.getText()) - Integer.parseInt(txtPPAN.getText());
        para.put("parameter5", String.valueOf(aa));
        para.put("rid", "RG_" + ceid);
        para.put("pid", "UH_" + ceid);
        JasperReport jr = JasperCompileManager.compileReport(j);
        JasperPrint jp = JasperFillManager.fillReport(jr, para, conn);
        String exportPath = "C:/dentalhouse/report.pdf";
        JasperExportManager.exportReportToPdfFile(jp, exportPath);
        pdf = exportPath;
        JasperViewer.viewReport(jp, false);

    }

    @FXML
    void submitaction(ActionEvent event) {
        Connection conn = mysqlconnect.connectDb();
        String sql = "insert into debithistory (distributername,number, date, time, pamount)values(?,?,?,?,?)";
//        System.out.println(sql);
        try {

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, disName.getText());
            pst.setString(2, disNumber.getText());
            java.sql.Date d = java.sql.Date.valueOf(dada);
            pst.setDate(3, d);
            pst.setString(4, titi);
            pst.setString(5, lblCPA.getText());
            pst.execute();
            UpdateTable2();
            UpdateTable3();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        int A, B, C;

        A = Integer.parseInt(lblDTA.getText());
        B = Integer.parseInt(lblCPA.getText());
        C = A - B;
//        System.out.println(C);
        if (A - B > 200) {
            Connection con = mysqlconnect.connectDb();
            String sql2 = "insert into duedebit (distributername,number,totalamount,paidamount,dueamount)values(?,?,?,?,?)";
//            System.out.println(sql);
            try {
                PreparedStatement pst2 = conn.prepareStatement(sql2);
                pst2.setString(1, disName.getText());
                pst2.setString(2, disNumber.getText());
                pst2.setInt(3, A);
                pst2.setInt(4, B);
                pst2.setInt(5, C);
                pst2.execute();
                UpdateTable2();
                UpdateTable3();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
        UpdateTable2();
        UpdateTable3();
        disName.clear();
        disNumber.clear();
        lblDTA.setText(null);
        lblCPA.clear();
        tableproduct.setItems(null);
    }

    static String dada, titi;

    @FXML
    private AnchorPane paneDDT;

    @FXML
    private TextField textDPA;

    @FXML
    private Button btnDS, recordback;

    @FXML
    private Button btnDB;

    @FXML
    private Label lblDN;

    @FXML
    private Label lblDMN;

    @FXML
    private Label lblDDA;

    @FXML
    private AnchorPane adjustE;

    @FXML
    private TextField txtPPAN;

    @FXML
    private Label lblPDAN;

    @FXML
    private Label lblDPNN;

    @FXML
    private Label lblPMNN;

    @FXML
    private Label lblPEN;

    @FXML
    private Button btnSC;

    @FXML
    private Button btnBC, vocherbtn, printvocherbtn;

    static String deid, ceid, ceid2;
    int DAA, DBB, DCC;
    int CAA, CBB, CCC;

    @FXML
    private AnchorPane vocherpane;

    @FXML
    private TextField paidto;

    @FXML
    private TextField paidfor;

    @FXML
    private DatePicker vocdate;

    @FXML
    private TextField vocamount;

    @FXML
    private AnchorPane vocherlistpane;

    public void vou() throws JRException {

        String sourceFile;
        sourceFile = "voucher.jrxml";
        try {
            JasperReport jr = JasperCompileManager.compileReport(sourceFile);
            HashMap<String, Object> para = new HashMap<>();
            para.put("parameter1", paidto.getText());
            para.put("parameter2", paidfor.getText());
            para.put("parameter3", vocamount.getText());

            JasperPrint jp = JasperFillManager.fillReport(jr, para);
            JasperViewer.viewReport(jp, false);
        } catch (JRException ex) {
            Logger.getLogger(DocControllerDash.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private TableView<vocher> vochertable;

    @FXML
    private TableColumn<vocher, String> tpaidto;

    @FXML
    private TableColumn<vocher, String> tpaidfor;

    @FXML
    private TableColumn<vocher, String> tdate;

    @FXML
    private TableColumn<vocher, String> tamount;

    @FXML
    private Button backvochertable, backvocher, vocherlistbtn;

    @FXML
    private void handelrecords(ActionEvent event) {
        recordpane.setVisible(true);
    }

    public class vocher {

        private String paidto, paidfor, date, amount;

        public vocher(String paidto, String paidfor, String date, String amount) {
            this.paidto = paidto;
            this.paidfor = paidfor;
            this.date = date;
            this.amount = amount;
        }

        public String getPaidto() {
            return paidto;
        }

        public void setPaidto(String paidto) {
            this.paidto = paidto;
        }

        public String getPaidfor() {
            return paidfor;
        }

        public void setPaidfor(String paidfor) {
            this.paidfor = paidfor;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }
    }

    public ObservableList<vocher> getvocher() {
        Connection conn = mysqlconnect.connectDb();
        ObservableList<vocher> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from voucher");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
//                System.out.println(rs.getString("name") + " " + rs.getString("number") + " " + rs.getString("dueamount"));
                list.add(new vocher(rs.getString("paidto"), rs.getString("paidfor"), rs.getString("date"), rs.getString("amount")));
            }
        } catch (Exception e) {

        }
        return list;
    }
    ObservableList<vocher> listT;

    public void u() {
        tpaidto.setCellValueFactory(new PropertyValueFactory("paidto"));
        tpaidfor.setCellValueFactory(new PropertyValueFactory("paidfor"));
        tdate.setCellValueFactory(new PropertyValueFactory("date"));
        tamount.setCellValueFactory(new PropertyValueFactory("amount"));
        listT = getvocher();
        vochertable.setItems(listT);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        recordback.setOnAction(e -> {
            recordpane.setVisible(false);
//            vocherpane.setVisible(true);
        });
        vocherbtn.setOnAction(e -> {
            rec.setVisible(false);
            vocherpane.setVisible(true);
        });
        backvocher.setOnAction(e -> {
            rec.setVisible(true);
            vocherpane.setVisible(false);
        });
        vocherlistbtn.setOnAction(e -> {
            u();
            rec.setVisible(false);
            vocherlistpane.setVisible(true);
        });
        backvochertable.setOnAction(e -> {
            rec.setVisible(true);
            vocherlistpane.setVisible(false);
        });
        printvocherbtn.setOnAction(e -> {
            try {
                vou();
            } catch (JRException ex) {
                Logger.getLogger(AccountantDashController.class.getName()).log(Level.SEVERE, null, ex);
            }
            Connection con = mysqlconnect.connectDb();
            try {
                PreparedStatement pst = con.prepareStatement("insert into voucher(paidto,paidfor,date,amount) values(?,?,?,?)");
                pst.setString(1, paidto.getText());
                pst.setString(2, paidfor.getText());
                java.sql.Date d = java.sql.Date.valueOf(vocdate.getValue().toString());
                pst.setDate(3, d);
                pst.setString(4, vocamount.getText());
                pst.execute();
            } catch (SQLException ex) {
                Logger.getLogger(AccountantDashController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        updateTable();
        search_user();
        updateTable2();
        UpdateTable2();
        UpdateTable3();
        updateTable3();

        colProAmout.setCellValueFactory(new PropertyValueFactory("amount"));
        colProPrice.setCellValueFactory(new PropertyValueFactory("price"));
        colProName.setCellValueFactory(new PropertyValueFactory("name"));
        colQTY.setCellValueFactory(new PropertyValueFactory("Qty"));
        tableproduct.setItems(listproduct);
        listproduct = FXCollections.observableArrayList();

        Calendar cal = Calendar.getInstance();

        SimpleDateFormat f = new SimpleDateFormat("YYYY-MM-dd");
        SimpleDateFormat sf = new SimpleDateFormat("hh:mm");
        dada = f.format(cal.getTime());
        titi = sf.format(cal.getTime());
//        System.out.println(dada);
//        System.out.println(titi);

        Callback<TableColumn<Emi, String>, TableCell<Emi, String>> cellco = (params) -> {

            final TableCell<Emi, String> cell = new TableCell<Emi, String>() {
                //override updatesItem method
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //ensure that cell is creates only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button button3 = new Button("Get Payment");
                        button3.setStyle("-fx-background-color:red");
                        button3.setStyle("-fx-background-radius:50 50 50 50");
                        button3.setStyle("-fx-text-fill:white");
                        button3.centerShapeProperty();
                        button3.setOnAction(event -> {
                            Emi m = getTableView().getItems().get(getIndex());
                            ceid = m.getId();
                            ceid2 = m.getId2();
                            CAA = Integer.parseInt(m.getDueamount());
                            CBB = Integer.parseInt(m.getPamount());
                            lblPDAN.setText(m.getDueamount());
                            lblDPNN.setText(m.getName());
                            lblPMNN.setText(m.getNumber());
                            lblPEN.setText(m.getEmail());
                            txtPPAN.clear();
                            adjustE.setVisible(true);
                        });
                        setGraphic(button3);
                        setText(null);
                    }
                }
            };

            return cell;
        };
        ObservableList<String> data = FXCollections.observableArrayList();
        String sql = "select * from inventory";
        Connection conn = mysqlconnect.connectDb();
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
//            System.out.println(rs.next());
            while (rs.next()) {
//                System.out.println(rs.getString("name"));
                data.add(rs.getString("name"));
            }
            comboPN.setItems(data);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        button.setCellFactory(cellco);
        Callback<TableColumn<payment, String>, TableCell<payment, String>> cellFactory3 = (params) -> {

            final TableCell<payment, String> cell = new TableCell<payment, String>() {
                //override updatesItem method
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //ensure that cell is creates only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button button3 = new Button("Get Payment");
                        button3.setStyle("-fx-background-color:red");
                        button3.setStyle("-fx-background-radius:50 50 50 50");
                        button3.setStyle("-fx-text-fill:white");
                        button3.centerShapeProperty();
                        button3.setOnAction(event -> {
                            payment m = getTableView().getItems().get(getIndex());
                            lbltamount.setText(m.getPtamount());
                            lblamount.setText(m.getPtamount());
                            lblnumber.setText(m.getPnumber());
                            lblname.setText(m.getPname());
                            ta = m.getPtamount();
                            id = m.getID();

                        });

                        setGraphic(button3);
                        setText(null);

                    }
                }
            };

            return cell;
        };
        colpbtn.setCellFactory(cellFactory3);

        Callback<TableColumn<product, String>, TableCell<product, String>> cellFactory4 = (params) -> {
            final TableCell<product, String> cell = new TableCell<product, String>() {
                //override updatesItem method
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //ensure that cell is creates only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button button3 = new Button("X");
                        button3.setStyle("-fx-background-color:red");
                        button3.setStyle("-fx-background-radius:50 50 50 50");
                        button3.setStyle("-fx-text-fill:white");
                        button3.centerShapeProperty();
                        button3.setOnAction(event -> {
                            product m = getTableView().getItems().get(getIndex());
                            tableproduct.getItems().remove(m);
                            int AA = Integer.parseInt(m.getAmount());
                            val = val - AA;
                            lblDTA.setText(String.valueOf(val));
                        });

                        setGraphic(button3);
                        setText(null);

                    }
                }
            };

            return cell;
        };
        colProbtn.setCellFactory(cellFactory4);

        Callback<TableColumn<TxHistory, String>, TableCell<TxHistory, String>> cellFactory10 = (params) -> {
            final TableCell<TxHistory, String> cell = new TableCell<TxHistory, String>() {
                //override updatesItem method
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //ensure that cell is creates only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button button3 = new Button("View Invoice");
                        button3.setStyle("-fx-background-color:red");
                        button3.setStyle("-fx-background-radius:50 50 50 50");
                        button3.setStyle("-fx-text-fill:white");
                        button3.centerShapeProperty();
                        button3.setOnAction(event -> {
                            TxHistory m = getTableView().getItems().get(getIndex());
                            String sss = m.getId();
//                            System.out.println(sss);
                            String sql = String.format("select * from transactionhistory where transactionid=" + sss + "");

                            try {
                                Connection con = mysqlconnect.connectDb();
                                PreparedStatement pst = con.prepareStatement(sql);
                                ResultSet rs = pst.executeQuery();

                                if (rs.next()) {
//                 FileOutputStream fs = new FileOutputStream(rs.getString("image"));
                                    String Abcd = "C:\\dentalHouse\\test.pdf";

                                    File file = new File(Abcd);
                                    FileOutputStream output = new FileOutputStream(file);
//            while (rs.next()) {
                                    InputStream input = rs.getBinaryStream("invoicepdf");
                                    byte[] buffer = new byte[1024];
                                    while (input.read(buffer) > 0) {
//                 input.read(buffer);
                                        output.write(buffer);

//                }
                                    }

                                    output.close();
                                    Desktop.getDesktop().open(new java.io.File(Abcd));

//                 file.canRead();
                                }
//     
//        byte requestBytes[] = Content.getBytes();
//        ByteArrayInputStream bis = new ByteArrayInputStream(requestBytes);

                            } //            JOptionPane.showMessageDialog(null, e);
                            catch (FileNotFoundException ex) {
                                Logger.getLogger(DocControllerDash.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                                Logger.getLogger(DocControllerDash.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (SQLException ex) {
                                Logger.getLogger(AccountantDashController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });

                        setGraphic(button3);
                        setText(null);

                    }
                }
            };

            return cell;
        };

        colinvoice.setCellFactory(cellFactory10);

        Callback<TableColumn<Debit, String>, TableCell<Debit, String>> cellFactory5 = (params) -> {

            final TableCell<Debit, String> cell = new TableCell<Debit, String>() {
                //override updatesItem method
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //ensure that cell is creates only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button button3 = new Button("Adjust");
                        button3.setStyle("-fx-background-color:red");
                        button3.setStyle("-fx-background-radius:50 50 50 50");
                        button3.setStyle("-fx-text-fill:white");
                        button3.centerShapeProperty();
                        button3.setOnAction(event -> {
                            Debit m = getTableView().getItems().get(getIndex());
//                            System.out.println(m.getDurdebitid());
                            deid = m.getDurdebitid();
                            DAA = Integer.parseInt(m.getPaidamount());
                            DBB = Integer.parseInt(m.getDueamount());

                            lblDDA.setText(m.getDueamount());
                            lblDMN.setText(m.getNumber());
                            lblDN.setText(m.getDistributername());
//                            System.out.println(deid);
                            textDPA.clear();
                            paneDDT.setVisible(true);
                        });

                        setGraphic(button3);
                        setText(null);

                    }
                }
            };

            return cell;
        };
        colcreditbtn.setCellFactory(cellFactory5);

        textA.setOnMouseClicked(e -> {
            int CC, AA, BB;
            AA = Integer.parseInt(textQt.getText());
            BB = Integer.parseInt(textP.getText());
            CC = AA * BB;
            textA.setText(String.valueOf(CC));
        });

        btnDB.setOnAction(e -> {
            paneDDT.setVisible(false);
        });
        btnBC.setOnAction(e -> {
            adjustE.setVisible(false);
        });

        btnSC.setOnAction(e -> {
            try {
                emiinvoice();
            } catch (SQLException ex) {
                Logger.getLogger(AccountantDashController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JRException ex) {
                Logger.getLogger(AccountantDashController.class.getName()).log(Level.SEVERE, null, ex);
            }
            Connection con = mysqlconnect.connectDb();
//            System.out.println(sql);
            CCC = Integer.parseInt(txtPPAN.getText());
            int B = CAA - CCC;
            int A = CCC + CBB;
            try {

                String sql21 = "update emi set dueamount='" + String.valueOf(B) + "', paidamount='" + String.valueOf(A) + "' where emiid='" + ceid2 + "'";
//                System.out.println(sql21);

                PreparedStatement pst2 = conn.prepareStatement(sql21);
                pst2.execute();
                UpdateTable2();
                UpdateTable3();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
            Connection conn23 = mysqlconnect.connectDb();
            String sql2 = "insert into transactionhistory (patientid, date, time, totalamount, invoicepdf)values(?,?,?,?,?)";
            try {

                PreparedStatement pst2 = conn23.prepareStatement(sql2);
                pst2.setString(1, ceid);
                java.sql.Date d = java.sql.Date.valueOf(dada);
                pst2.setDate(2, d);
                pst2.setString(3, titi);
                pst2.setString(4, txtPPAN.getText());
                File file = new File(pdf);
                FileInputStream input = new FileInputStream(file);
                pst2.setBinaryStream(5, input);
                pst2.execute();
            } catch (SQLException eX) {
                JOptionPane.showMessageDialog(null, eX);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(AccountantDashController.class.getName()).log(Level.SEVERE, null, ex);
            }
            updateTable();
            search_user();
            updateTable2();
            UpdateTable2();
            UpdateTable3();
            updateTable3();

            adjustE.setVisible(false);
        });

        btnDS.setOnAction(e -> {
            Connection con = mysqlconnect.connectDb();
//            System.out.println(sql);
            try {
                DCC = Integer.parseInt(textDPA.getText());
                int A = DAA + DCC;
                int B = DBB - DCC;
                String sql2 = "update duedebit set dueamount='" + String.valueOf(B) + "', paidamount='" + String.valueOf(A) + "' where durdebitid='" + deid + "'";
//                System.out.println(sql2);
//                System.out.println(A);
                PreparedStatement pst2 = conn.prepareStatement(sql2);
                pst2.execute();
                UpdateTable2();
                UpdateTable3();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
            Connection conn2 = mysqlconnect.connectDb();
            String sql2 = "insert into debithistory (distributername,number, date, time, pamount)values(?,?,?,?,?)";
//            System.out.println(sql);
            try {
                PreparedStatement pst2 = conn2.prepareStatement(sql2);
                pst2.setString(1, lblDN.getText());
                pst2.setString(2, lblDMN.getText());
                java.sql.Date d = java.sql.Date.valueOf(dada);
                pst2.setDate(3, d);
                pst2.setString(4, titi);
                pst2.setString(5, textDPA.getText());
                pst2.execute();
                UpdateTable2();
                UpdateTable3();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }

            paneDDT.setVisible(false);
        });

        btnPlus.setOnAction(e -> {
            String A, B, C, D;
            A = comboPN.getValue();
            B = textQt.getText();
            C = textP.getText();
            D = textA.getText();
            val = val + Integer.parseInt(textA.getText());
            lblDTA.setText(String.valueOf(val));
            listproduct.add(new product(A, B, C, D));
            tableproduct.setItems(listproduct);
            comboPN.getSelectionModel().clearSelection();
            textQt.clear();
            textP.clear();
            textA.clear();
        });

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
                    Alert a = new Alert(Alert.AlertType.NONE);
                    a.setAlertType(Alert.AlertType.CONFIRMATION);
                    a.setContentText("Sheet Created Succesfully");
                    a.show();

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
                    String sqls = "Select * from transactionhistory as t join patient as p where t.patientid=p.patientid and date='" + value + "'";
                    Connection conns = mysqlconnect.connectDb();
                    PreparedStatement psts = conns.prepareStatement(sqls);
                    ResultSet rs1 = psts.executeQuery();
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
                    String path = "C:\\Sheets\\Fixed Dates\\" + value + ".xls";
                    FileOutputStream fileout = new FileOutputStream(path);
                    wb.write(fileout);
                    fileout.close();
                    System.out.println("Sheet Created Successfully");
                    psts.close();
                    rs1.close();
                    Alert a = new Alert(Alert.AlertType.NONE);
                    a.setAlertType(Alert.AlertType.CONFIRMATION);
                    a.setContentText("Sheet Created Succesfully");
                    a.show();

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
    }

}
