/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dental;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import static dental.DocControllerDash.dt;
import static dental.mysqlconnect.connectDb;
import java.awt.HeadlessException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileOutputStream;
import java.net.URL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
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
public class ReceptionistDeshController implements Initializable {

    static String b, no, ad, e, i, a, n, value, t;

    //attendence taking List show
    @FXML
    private AnchorPane attendence_taken_list;

    @FXML
    private ComboBox employer;

    @FXML
    private ComboBox comboPType;

    @FXML
    private TableView<Attendence> attendence_taking_list;

    @FXML
    private TableColumn<Attendence, String> col_name1;

    @FXML
    private TableColumn<Attendence, String> col_number1;

    @FXML
    private TableColumn<Attendence, String> col_email1;

    @FXML
    private TableColumn<Attendence, String> col_date;

    @FXML
    private TableColumn col_taking_attendence;
    @FXML
    private AnchorPane rec;
    @FXML
    private Label labBranch;
    @FXML
    private Text aName1;
    @FXML
    private TextField filterField;

    @FXML
    void handelattendenceList(ActionEvent event) {
        attendence_taken_list.setVisible(true);
        attendence_taking_list.setVisible(true);
        attendence_show_list.setVisible(false);
        attendence_mark_pane.setVisible(false);
    }

    @FXML
    void handelback2(ActionEvent event) {
        attendence_mark_pane.setVisible(false);
        attendence_show_pane.setVisible(false);
    }

    public ObservableList<Attendence> getAttendence() {
        Connection conn = connectDb();
        try {
            if (!t.equals("null")) {
                t = (String) employer.getValue();
            }
        } catch (Exception e) {
            t = "doctor";
        }
        ObservableList<Attendence> list = FXCollections.observableArrayList();
        try {
            t = t.toLowerCase();
            String sql = "select * from " + t;
            Calendar cal = Calendar.getInstance();
            java.util.Date date = cal.getTime();
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            String formattedDate = dateFormat.format(date);
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            String id = t + "id";
            while (rs.next()) {
                list.add(new Attendence(rs.getString(id), rs.getString("name"), rs.getString("number"), rs.getString("email"), formattedDate));
            }
        } catch (Exception e) {
            System.out.print("Exception " + e);
        }
        return list;
    }
    ObservableList<Attendence> listM;

    public void UpdateTable() {
        col_name1.setCellValueFactory(new PropertyValueFactory<Attendence, String>("name"));
        col_number1.setCellValueFactory(new PropertyValueFactory<Attendence, String>("number"));
        col_email1.setCellValueFactory(new PropertyValueFactory<Attendence, String>("email"));
        col_date.setCellValueFactory(new PropertyValueFactory<Attendence, String>("date"));
        listM = getAttendence();
        col_name1.setStyle("-fx-alignment: CENTER;");
        attendence_taking_list.setItems(listM);
    }

    @FXML
    void handelback(ActionEvent event) {
        attendence_taken_list.setVisible(false);
        attendence_taking_list.setVisible(false);
        attendence_show_list.setVisible(false);
    }

//    End attendence List
//    attendence show
    @FXML
    private TableView<Attendence> attendence_show_list;

    @FXML
    private TableColumn<Attendence, String> col_name;

    @FXML
    private TableColumn<Attendence, String> col_number;

    @FXML
    private TableColumn<Attendence, String> col_email;

    @FXML
    private TableColumn show_attendence;

    @FXML
    private AnchorPane attendence_mark_pane, attendence_show_pane;

    @FXML
    private ComboBox tableView;

    public ObservableList<Attendence> getAttendenceShow() {
        Connection conn = connectDb();
        try {
            if (!t.equals("null")) {
                t = (String) employer.getValue();
            }
        } catch (Exception e) {
            t = "doctor";
        }
        ObservableList<Attendence> list = FXCollections.observableArrayList();
        try {
            t = t.toLowerCase();
            String sql = "select * from " + t;
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            String id = t + "id";
            while (rs.next()) {
                list.add(new Attendence(rs.getString(id), rs.getString("name"), rs.getString("number"), rs.getString("email")));
            }
        } catch (Exception e) {
            System.out.print("Exception " + e);
        }
        return list;
    }
    ObservableList<Attendence> listM2, listM3;

    public void UpdateTable2() {
        col_name.setCellValueFactory(new PropertyValueFactory<Attendence, String>("name"));
        col_number.setCellValueFactory(new PropertyValueFactory<Attendence, String>("number"));
        col_email.setCellValueFactory(new PropertyValueFactory<Attendence, String>("email"));

        listM2 = getAttendenceShow();
        attendence_show_list.setItems(listM2);
        col_name.setStyle("-fx-alignment: CENTER;");
    }

    void handle(ActionEvent event) {

    }

//    end attendence Show
//    show attendence in table 
    @FXML
    private TableView<Attendence> attendence_view;

    @FXML
    private TableColumn<Attendence, String> adate;

    @FXML
    private TableColumn<Attendence, String> astatus;

    public ObservableList<Attendence> getAttendenceTable() {
        Connection conn = connectDb();
        try {
            if (!t.equals("null")) {
                t = (String) employer.getValue();
            }
        } catch (Exception e) {
            t = "doctor";
        }
        ObservableList<Attendence> list = FXCollections.observableArrayList();
        try {
            t = t.toLowerCase();
            String sql = "select * from attendence_table where type='" + t + "' and id=" + i;
//                LocalDate myObj = LocalDate.now();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            String id = "id";
            while (rs.next()) {
                list.add(new Attendence(rs.getString(id), rs.getString("date"), rs.getString("status")));
            }
        } catch (Exception e) {
            System.out.print("Exception " + e);
        }
        return list;
    }

    public void UpdateTable3() {
        adate.setCellValueFactory(new PropertyValueFactory<Attendence, String>("date"));
        astatus.setCellValueFactory(new PropertyValueFactory<Attendence, String>("status"));
        listM3 = getAttendenceTable();
        attendence_view.setItems(listM3);
        col_name.setStyle("-fx-alignment: CENTER;");
    }

//    End attendence in table
    @FXML
    private Button log_btn;

    @FXML
    private AnchorPane profilePane;

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
    void closeWindow(ActionEvent event) {
        profilePane.setVisible(false);
        options.setVisible(true);
    }
    @FXML
    private AnchorPane optionPane;

    private ComboBox doctorType;
    private ComboBox dotor2;

    @FXML
    private AnchorPane viewAppointmentPane;

    @FXML
    private AnchorPane newAppointmentPane;

    @FXML
    private AnchorPane doctorSchedulePane;

    @FXML
    private Pane newPatientPane;

    @FXML
    private RadioButton btnREP;

    @FXML
    private RadioButton btnRNP;

    @FXML
    private Pane exitPatientPane;

    @FXML
    void existPatient(ActionEvent event) {
        newPatientPane.setVisible(false);
        exitPatientPane.setVisible(true);
        btnRNP.setSelected(false);
    }

    @FXML
    void newPatient(ActionEvent event) {
        newPatientPane.setVisible(true);
        exitPatientPane.setVisible(false);
        btnREP.setSelected(false);
    }

    @FXML
    public void newAppointMent() {
        optionPane.setVisible(false);
        newAppointmentPane.setVisible(true);
        doctorSchedulePane.setVisible(false);
        btnREP.setSelected(false);
        btnRNP.setSelected(false);
        newPatientPane.setVisible(false);
        exitPatientPane.setVisible(false);
        viewAppointmentPane.setVisible(false);
        profilePane.setVisible(false);
    }

    @FXML
    public void docterPane() {
        optionPane.setVisible(false);
        newAppointmentPane.setVisible(false);
        doctorSchedulePane.setVisible(true);
        viewAppointmentPane.setVisible(false);
        profilePane.setVisible(false);
    }

    @FXML
    public void optionPane() {
        optionPane.setVisible(true);
        newAppointmentPane.setVisible(false);
        doctorSchedulePane.setVisible(false);
        viewAppointmentPane.setVisible(false);
        profilePane.setVisible(false);
    }

    @FXML
    public void AppointmentPane() {
        optionPane.setVisible(false);
        newAppointmentPane.setVisible(false);
        doctorSchedulePane.setVisible(false);
        viewAppointmentPane.setVisible(true);
        profilePane.setVisible(false);
        update();
        update2();
    }

//    void handeldoctors(ActionEvent event) {
//        try {
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Doctor.fxml"));
//            Parent root1 = (Parent) fxmlLoader.load();
//            Stage stage = new Stage();
//            stage.setTitle("doctors window");
//            stage.setScene(new Scene(root1));
//            stage.show();
//        } catch (Exception e) {
//            System.out.println("cont load");
//        }
    @FXML
    private Pane changePane;

    @FXML
    void changeWindow(ActionEvent event) {
        changePane.setVisible(true);
//        profilePane.setVisible(false);
    }

    @FXML
    private Label lblPatientName;

    @FXML
    private Label lblPatientMobileNo;

    @FXML
    private Label lblBranch;

    @FXML
    private Label lblPatientEmail;

    @FXML
    private Label lblAddress;

    @FXML
    private Label lblGender;

    @FXML
    private Label lblAge;

    @FXML
    private TextField txtMobile;
    static int pid;

    @FXML
    void exitEnter(ActionEvent event) {
        String s = txtMobile.getText().substring(3);
        String sql = String.format("select * from patient where patientid='%s'", s);
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                pid = rs.getInt("patientid");
                lblPatientName.setText(rs.getString("Name"));
                lblAge.setText(rs.getString("Age"));
                lblAddress.setText(rs.getString("Address"));
                lblGender.setText(rs.getString("Gender"));
                lblPatientEmail.setText(rs.getString("Email"));
                lblBranch.setText(rs.getString("Branch"));
                lblPatientMobileNo.setText(rs.getString("Number"));
            }
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    @FXML
    private TextField txtPatientName;

    @FXML
    private TextField txtPatientEmail;

    @FXML
    private TextField txtAge;

    @FXML
    private ComboBox txtGender;

    @FXML
    private TextField txtPatientMobileNo;

    @FXML
    private TextArea txtAddress;

    @FXML
    void ForgetWindow(ActionEvent event) {
        if (p.getText().equals(cp.getText())) {
            Connection conn = mysqlconnect.connectDb();
            String sql = "update receptionist set password= ? where ReceptionistId=" + LoginController.id;
            try {
                System.out.println(sql + " ");
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, p.getText());
                pst.execute();
                Window owner = log_btn.getScene().getWindow();
                showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
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
    @FXML
    private TextField p;

    @FXML
    private TextField cp;

    @FXML
    private AnchorPane options;

    void handelpatient(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Patient.fxml"));
        Stage mainStage = new Stage();
        Scene scene = new Scene(root);
        mainStage.setTitle("Personal Infomation");
        mainStage.setScene(scene);
        mainStage.show();
    }

    void handleDoctorStatus(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Shift.fxml"));
        Stage mainStage = new Stage();
        Scene scene = new Scene(root);
        mainStage.setTitle("Personal Infomation");
        mainStage.setScene(scene);
        mainStage.show();
    }

    @FXML
    void handleProfile(ActionEvent event) {
        optionPane.setVisible(true);
        newAppointmentPane.setVisible(false);
        doctorSchedulePane.setVisible(false);
        viewAppointmentPane.setVisible(false);
        profilePane.setVisible(true);
        changePane.setVisible(false);
        Connection conn = mysqlconnect.connectDb();
        String sql = String.format("Select * from %s where name='%s' and password='%s'", LoginController.t, LoginController.u, LoginController.p);
        System.out.println(sql);
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
                lblBranch.setText(rs.getString("branch"));
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
    PreparedStatement pst;
    Connection conn = connectDb();
    ResultSet rs;

    @FXML
    void cancelPane(ActionEvent event) {
        optionPane.setVisible(true);
        newAppointmentPane.setVisible(false);
    }

    public void clearnew() {
        txtPatientName.clear();
        txtAge.clear();
        txtGender.getSelectionModel().clearSelection();
        txtPatientMobileNo.clear();
        txtPatientEmail.clear();
        txtAddress.clear();
//        dotor2.getSelectionModel().clearSelection();
        date2.getEditor().clear();
        time2.getEditor().clear();

    }
    @FXML
    private TableView<Apphistory> appointhistory;

    @FXML
    private TableColumn<Apphistory, String> colAPN;

    @FXML
    private TableColumn<Apphistory, String> colAPMN;

    @FXML
    private TableColumn<Apphistory, String> colAD;

    @FXML
    private TableColumn<Apphistory, String> colPT;

    @FXML
    private TableColumn<Apphistory, String> colAHS;

    @FXML
    private TableColumn<Apphistory, String> coluhid;

    ObservableList<Apphistory> listhis;

    public void updatehistory() {
        colAPN.setCellValueFactory(new PropertyValueFactory<Apphistory, String>("name"));
        colAPMN.setCellValueFactory(new PropertyValueFactory<Apphistory, String>("number"));
        colAD.setCellValueFactory(new PropertyValueFactory<Apphistory, String>("date"));
        colPT.setCellValueFactory(new PropertyValueFactory<Apphistory, String>("type"));
        colAHS.setCellValueFactory(new PropertyValueFactory<Apphistory, String>("status"));
        coluhid.setCellValueFactory(new PropertyValueFactory<Apphistory, String>("uhid"));
        listhis = mysqlconnect.getapphistory();
        appointhistory.setItems(listhis);
    }
    
     ObservableList<Apphistory> dataList;
     public void search_patient() {
        colAPN.setCellValueFactory(new PropertyValueFactory<Apphistory, String>("name"));
        colAPMN.setCellValueFactory(new PropertyValueFactory<Apphistory, String>("number"));
        colAD.setCellValueFactory(new PropertyValueFactory<Apphistory, String>("date"));
        colPT.setCellValueFactory(new PropertyValueFactory<Apphistory, String>("type"));
        colAHS.setCellValueFactory(new PropertyValueFactory<Apphistory, String>("status"));
        coluhid.setCellValueFactory(new PropertyValueFactory<Apphistory, String>("uhid"));
        dataList = mysqlconnect.getapphistory();
        appointhistory.setItems(dataList);
        FilteredList<Apphistory> filteredData = new FilteredList<>(dataList, b -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (person.getUhid().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (person.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (person.getNumber().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }else {
                    return false; 
                }
            });
        });
        SortedList<Apphistory> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(appointhistory.comparatorProperty());
        appointhistory.setItems(sortedData);
    }

    @FXML
    private AnchorPane historyPane;

    @FXML
    void backto(ActionEvent event) {
        historyPane.setVisible(false);
    }

    @FXML
    void historyshow(ActionEvent event) {
        historyPane.setVisible(true);
    }

    @FXML
    void newPatientA(ActionEvent event) throws JRException, SQLException {
//        coderP();
        conn = mysqlconnect.connectDb();
        String sql = "insert into patient (Name, Age, Gender, Number, Branch, Email, Address,patienttype) values ( ?,?,?,?,?,?,?,?)";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, txtPatientName.getText());
            pst.setString(2, txtAge.getText());
            pst.setString(3, (String) txtGender.getValue());
            pst.setString(4, txtPatientMobileNo.getText());
            pst.setString(5, lblBranch.getText());
            pst.setString(6, txtPatientEmail.getText());
            pst.setString(7, txtAddress.getText());
            pst.setString(8, comboPType.getValue().toString());

            pst.execute();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        sql = "select * from patient where number=" + txtPatientMobileNo.getText();
        System.out.println(sql);
        try {
            System.out.println(sql);
            Connection conn2 = mysqlconnect.connectDb();

            PreparedStatement pst2 = conn.prepareStatement(sql);

            ResultSet rs = pst2.executeQuery();
            if (rs.next()) {
                System.out.println(rs.getString("patientid"));
                sql = "insert into appointment (date, time,patientid, status,note,PatientName,branch,patienttype) values (?,?,?,?,?,?,?,?)";
                System.out.println(sql);
                try {
                    pst = conn2.prepareStatement(sql);
                    java.sql.Date d = java.sql.Date.valueOf(date2.getValue());
                    pst.setDate(1, d);
                    pst.setString(2, time2.getValue().toString());
//                    pst.setString(3, (String) dotor2.getValue());
                    pst.setString(3, rs.getString("patientId"));
                    pst.setString(4, "appoint");
                    pst.setString(5, note2.getText());
                    pst.setString(6, txtPatientName.getText());
                    pst.setString(7, branch.getText());
                    pst.setString(8, "New Patient");
                    pst.execute();
                    Window owner = log_btn.getScene().getWindow();
                    optionPane.setVisible(true);
                    newAppointmentPane.setVisible(false);
                } catch (HeadlessException | SQLException e) {
                    JOptionPane.showMessageDialog(null, e);
                }

            }
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

        clearnew();
        update();
        update2();
    }

    public void clearE() {
        lblPatientName.setText("");
        lblAge.setText("");
        lblAddress.setText("");
        lblGender.setText("");
        lblPatientEmail.setText("");
        lblBranch.setText("");
        lblPatientMobileNo.setText("");
//        doctorType.getSelectionModel().clearSelection();
        date.getEditor().clear();
        time.getEditor().clear();
        note.clear();
    }

    @FXML
    void appointmentFixed(ActionEvent event) throws JRException, SQLException {
//        coderEP();
        String sql = "select * from patient where number=" + txtMobile.getText();
        System.out.println(sql);
        try {
            Connection conn2 = mysqlconnect.connectDb();

            PreparedStatement pst2 = conn2.prepareStatement(sql);

            ResultSet rs = pst2.executeQuery();
            if (rs.next()) {
                sql = "insert into appointment (date, time,  patientid, status,note,PatientName,branch,patienttype) values ( ?,?,?,?,?,?,?,?)";
                System.out.println(sql);
                try {
                    pst = conn2.prepareStatement(sql);
                    java.sql.Date d = java.sql.Date.valueOf(date.getValue());
                    pst.setDate(1, d);
                    pst.setString(2, time.getValue().toString());
//                    pst.setString(3, (String) doctorType.getValue());
                    pst.setString(3, rs.getString("patientId"));
                    pst.setString(4, "appoint");
                    pst.setString(5, note.getText());
                    pst.setString(6, lblPatientName.getText());
                    pst.setString(7, branch.getText());
                    pst.setString(8, "Exist Patient");
                    pst.execute();

                    Window owner = log_btn.getScene().getWindow();

                    optionPane.setVisible(true);
                    newAppointmentPane.setVisible(false);
                } catch (HeadlessException | SQLException e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

        clearE();
        update();
        update2();
    }
    @FXML
    private DatePicker date2;

    @FXML
    private TextArea note2;
    @FXML
    private JFXTimePicker time2, time;

    @FXML
    private DatePicker date;

    @FXML
    private TextArea note;

    @FXML
    private TableView<Doctor> shedule;

    @FXML
    private TableColumn<Doctor, String> doctor;

    @FXML
    private TableColumn shift;
    @FXML
    private TableColumn<Doctor, String> moblie2;

    public void update() {
        doctor.setCellValueFactory(new PropertyValueFactory<Doctor, String>("name"));
        moblie2.setCellValueFactory(new PropertyValueFactory<Doctor, String>("number"));
        shift.setCellValueFactory(new PropertyValueFactory<Doctor, String>("shift"));
        
        list1 = mysqlconnect.getShedule();
        shedule.setItems(list1);
    }

    public static ObservableList<App> listAttendence() {
        Connection conn = mysqlconnect.connectDb();
        ObservableList<App> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from appointment where status ='appoint' or status='Check In' or status='PostPone' or status='In Process' or status='Treatment Done'");
            ResultSet rs = ps.executeQuery();
//            System.out.println("Observation List"+rs.getString(1));
            while (rs.next()) {
                list.add(new App(rs.getString("patientid"),rs.getString("patientname"), rs.getString("date"), rs.getString("time"), rs.getString("status")));
            }

        } catch (Exception e) {
            System.out.print("Exception " + e);
        }
        return list;
    }
    ObservableList<App> listM21;

    public void update2() {
        Pname.setCellValueFactory(new PropertyValueFactory<App, String>("Patientname"));
//        Dname.setCellValueFactory(new PropertyValueFactory<App, String>("Doctorname"));
        datea.setCellValueFactory(new PropertyValueFactory<App, String>("date"));
        timea.setCellValueFactory(new PropertyValueFactory<App, String>("time"));
        Status.setCellValueFactory(new PropertyValueFactory<App, String>("status"));
        colUHID.setCellValueFactory(new PropertyValueFactory<App, String>("id"));

        listM21 = listAttendence();
        appointmentTable.setItems(listM21);
    }
    ObservableList<Doctor> list1;
    @FXML
    private TableView<App> appointmentTable;
    @FXML
    private TableColumn<App, String> colUHID;
    @FXML
    private TableColumn<App, String> Status;
    @FXML
    private TableColumn<App, String> Pname;

    @FXML
    private TableColumn<App, String> datea;

    @FXML
    private TableColumn<App, String> timea;
    @FXML
    private TableColumn treatmentStatus;

    @FXML
    private AnchorPane productStatusPane;

    @FXML
    public void visible() {
        updatepro();
        productStatusPane.setVisible(true);

    }

    @FXML
    public void invisible() {
        updatepro();
        productStatusPane.setVisible(false);
    }

    @FXML
    private Button btnBackProduct;

    @FXML
    private TableView<item> tablelilist;

    @FXML
    private TableColumn<item, String> colliPN;

    @FXML
    private TableColumn<item, String> colliPMN;

    @FXML
    private TableColumn<item, String> colliProN;

    @FXML
    private TableColumn<item, String> colliOD;

    @FXML
    private TableColumn<item, String> colliED;

    @FXML
    private TableColumn<item, String> colliRD;

    @FXML
    private TableColumn<item, String> colliid;

    @FXML
    private TableColumn<item, String> colliS;

    @FXML
    private TableColumn colliBtn;

    ObservableList<item> listItem;

    public void updatepro() {

        colliid.setCellValueFactory(new PropertyValueFactory("A"));
        colliPN.setCellValueFactory(new PropertyValueFactory("B"));
        colliPMN.setCellValueFactory(new PropertyValueFactory("C"));
        colliProN.setCellValueFactory(new PropertyValueFactory("D"));
        colliOD.setCellValueFactory(new PropertyValueFactory("E"));
        colliED.setCellValueFactory(new PropertyValueFactory("F"));
        colliRD.setCellValueFactory(new PropertyValueFactory("G"));
        colliS.setCellValueFactory(new PropertyValueFactory("H"));

        listItem = mysqlconnect.getitem();
        tablelilist.setItems(listItem);
    }

//    static String value;
    static String Adddt;

    public void adddate() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-mm-dd");
        try {
            cal.setTime(f.parse(dt));
        } catch (ParseException ex) {
            Logger.getLogger(ReceptionistDeshController.class.getName()).log(Level.SEVERE, null, ex);
        }
        cal.add(Calendar.DATE, 1);
    }

    public void updateSchedule() {

        Connection conn = mysqlconnect.connectDb();
        String sql2 = "update appointment set status='appoint' where status='PostPone' and date= '" + dt + "'";
        String sql = "update appointment set date= '" + dt + "' where status= 'appoint' and date= '" + dd + "' or date= '" + ddd + "' or date= '" + dddd + "'";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            PreparedStatement pst2 = conn.prepareStatement(sql2);
            pst.execute();
            pst2.execute();
            update();
            update2();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    String d;
    String dd, ddd, dddd, ddddd;

    @FXML
    private AnchorPane newdatepane;

    @FXML
    private Button newdatesave;

    @FXML
    private Button newdateback;

    @FXML
    private DatePicker newdate;
    
//    static String zz;

    public void fillCombo() {
        ObservableList<String> dataA = FXCollections.observableArrayList();
        ObservableList<String> dataB = FXCollections.observableArrayList();
        dataB.addAll("Genral", "CGHS(Serving)", "CGHS(Pensioner)", "CSMA");
        dataA.addAll("Genral", "CGHS(Serving)", "CGHS(Pensioner)", "CSMA");
//        comboEPType.getItems().addAll(dataA);
        comboPType.getItems().addAll(dataB);
    }
    static String pname, page, pmoblie, paddress, ppatienttype;
    static int jasper = 0;

    public void coderP() throws JRException, SQLException {
        Connection co = mysqlconnect.connectDb();
        PreparedStatement ps;
        jasper = 1;    
        String sql = "select * from appointment";
        ps = co.prepareStatement(sql);
        JasperDesign j = JRXmlLoader.load("otp.jrxml");
        JRDesignQuery update = new JRDesignQuery();
        update.setText(sql);
        j.setQuery(update);
        HashMap<String, Object> para = new HashMap<>();
//        System.out.println(lblDPNN.getText() + " " + lblPMNN.getText() + " " + txtPPAN.getText());
        para.put("parameter1", pname);
        para.put("parameter2", page);
        para.put("parameter3", pmoblie);
        para.put("parameter4", paddress);
        if (ppatienttype.equals("Genral")) {
            para.put("parameter5", "150");
        } else if (ppatienttype.equals("CGHS(Serving)")) {
            para.put("parameter5", "135");
        } else if (ppatienttype.equals("CGHS(Pensioner)")) {
            para.put("parameter5", "0");
        } else if (ppatienttype.equals("CSMA")) {
            para.put("parameter5", "135");
        }
        JasperReport jr = JasperCompileManager.compileReport(j);
        JasperPrint jp = JasperFillManager.fillReport(jr, para, co);

        JasperViewer.viewReport(jp, false);
    }

    @FXML
    public void savenewdate() {
        Connection con = mysqlconnect.connectDb();
        try {
            App b = appointmentTable.getItems().get(index);

            String sql = "update appointment set Status='" + value + "', date='" + newdate.getValue().toString() + "'where patientname='" + b.getPatientname() + "' and date='" + b.getDate() + "' and time='" + b.getTime() + "'";

            System.out.println(sql);
            PreparedStatement pst = con.prepareStatement(sql);
//   ResultSet rs = pst.executeQuery();
//   pst.setString(1, value);
            pst.execute();
            update();
            update2();
            newdatepane.setVisible(false);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    public void backnewdate() {
        newdatepane.setVisible(false);
    }

    @FXML
    private CheckBox present;

    @FXML
    private CheckBox absent;

    @FXML
    private CheckBox halfday;

    @FXML
    private Button attendence_btn;

    @FXML
    private Button calender_btn;

    @FXML
    private Text aName;

    @FXML
    private DatePicker start_date;

    @FXML
    private DatePicker end_date;

    @FXML
    private AnchorPane recordpane;

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
    private Button recordback;

    static int index;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        search_patient();
        recordback.setOnAction(e -> {
            recordpane.setVisible(false);
        });
        fillCombo();
        Calendar cal = Calendar.getInstance();

        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sf = new SimpleDateFormat("HH:MM");
        d = sf.format(cal.getTime());
        dt = f.format(cal.getTime());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -1);
        dd = sdf.format(c.getTime());
        c.add(Calendar.DATE, -1);
        ddd = sdf.format(c.getTime());
        c.add(Calendar.DATE, -1);
        dddd = sdf.format(c.getTime());

        update();
        update2();
        updateSchedule();
//        updatehistory();
        updatepro();

        ObservableList<String> data = FXCollections.observableArrayList();
        data.addAll("Male", "Female");
        txtGender.getItems().addAll(data);
        try {
            ObservableList<String> data2 = FXCollections.observableArrayList();
            Connection con = mysqlconnect.connectDb();
            String sql = "select * from doctor where branch='" + LoginController.b + "'";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                data2.add(rs.getString("name"));
            }
            dotor2.setItems(data2);
            doctorType.setItems(data2);
        } catch (Exception ex) {
            System.out.println(ex);
        }

        Callback<TableColumn<App, String>, TableCell<App, String>> cellco2 = (params) -> {

            final TableCell<App, String> cell = new TableCell<App, String>() {
                //override updatesItem method
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //ensure that cell is creates only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        App a = getTableView().getItems().get(getIndex());

                        ComboBox comboBox = new ComboBox();

                        comboBox.setPromptText("Action");

                        comboBox.setMaxWidth(100);

                        comboBox.getItems().addAll("Cancel", "Check In", "Check Out", "PostPone", "appoint");
//                        comboBox.getItems().add("Reshedule");
                        JFXTimePicker j = new JFXTimePicker();

                        j.setVisible(false);

                        comboBox.setOnAction(event -> {
                            value = (String) comboBox.getValue();
                            System.out.println(value);
                            if (value.equals("Cancel")) {
                                Connection con = mysqlconnect.connectDb();
                                try {
                                    App b = getTableView().getItems().get(getIndex());

                                    String sql = "update appointment set Status='" + value + "' where patientname='" + b.getPatientname() + "' and date='" + b.getDate() + "' and time='" + b.getTime() + "'";

                                    System.out.println(sql);
                                    PreparedStatement pst = con.prepareStatement(sql);
//                                    ResultSet rs = pst.executeQuery();
//                                    pst.setString(1, value);
                                    pst.execute();
                                    update();
                                    update2();
                                    comboBox.setVisible(false);
                                } catch (Exception e) {
                                    System.out.println(e);
                                }
                            } else if (value.equals("Check Out")) {
                                Connection con = mysqlconnect.connectDb();
                                try {
                                    App b = getTableView().getItems().get(getIndex());

                                    String sql = "update appointment set Status='" + value + "' where patientname='" + b.getPatientname() + "' and date='" + b.getDate() + "' and time='" + b.getTime() + "'";

                                    System.out.println(sql);
                                    PreparedStatement pst = con.prepareStatement(sql);
//                                    ResultSet rs = pst.executeQuery();
//                                    pst.setString(1, value);
                                    pst.execute();
                                    update();
                                    update2();
//                                    comboBox.setVisible(false);
                                } catch (Exception e) {
                                    System.out.println(e);
                                }
                                comboBox.setVisible(false);
                            } else if (value.equals("appoint")) {
                                Connection con = mysqlconnect.connectDb();
                                try {
                                    App b = getTableView().getItems().get(getIndex());

                                    String sql = "update appointment set Status='" + value + "' where patientname='" + b.getPatientname() + "' and date='" + b.getDate() + "' and time='" + b.getTime() + "'";

                                    System.out.println(sql);
                                    PreparedStatement pst = con.prepareStatement(sql);
//                                    ResultSet rs = pst.executeQuery();
//                                    pst.setString(1, value);
                                    pst.execute();
                                    update();
                                    update2();
                                    updateSchedule();
//                                    comboBox.setVisible(false);
                                } catch (Exception e) {
                                    System.out.println(e);
                                }
                                comboBox.setVisible(false);
                            } else if (value.equals("Check In")) {
                                Connection con = mysqlconnect.connectDb();
                                try {
                                    App b = getTableView().getItems().get(getIndex());

                                    String sql = "update appointment set Status='" + value + "' where patientname='" + b.getPatientname() + "' and date='" + b.getDate() + "' and time='" + b.getTime() + "'";
                                    PreparedStatement pst = con.prepareStatement(sql);
                                    pst.execute();
                                    LocalDate l = LocalDate.now();
                                    sql = "select * from appointment as a join patient as p where a.patientname='" + b.getPatientname() + "' and a.date='" + b.getDate() + "' and a.time='" + b.getTime() + "'  ";//and a.date between '" + l.minusDays(90) + "' and '" + l + "'
                                    jasper = 0;
                                    int patientid = 0;
                                    pst = con.prepareStatement(sql);
                                    int count = 0;

                                    ResultSet rs = pst.executeQuery(sql);
                                    if (rs.next()) {
                                        patientid = Integer.parseInt(rs.getString("patientid"));
                                    }
                                    sql = "SELECT COUNT(patientid) as countp from appointment where patientid=" + patientid + " and date between '" + l.minusDays(90) + "' and '" + l + "'";

                                    pst = con.prepareStatement(sql);
                                    ResultSet rs3 = pst.executeQuery(sql);
                                    if (rs3.next()) {
                                        count = rs3.getInt("countp");
                                    }
                                    System.out.println(count);
                                    if (count == 1) {
                                        sql = "select * from appointment as a join patient as p where a.patientname='" + b.getPatientname() + "' and a.date='" + b.getDate() + "' and a.time='" + b.getTime() + "'";
                                        PreparedStatement pst2 = con.prepareStatement(sql);
                                        ResultSet rs2 = pst2.executeQuery();
                                        if (rs2.next()) {
                                            pname = rs.getString("name");
                                            page = rs.getString("age");
                                            pmoblie = rs.getString("number");
                                            paddress = rs.getString("address");
                                            ppatienttype = rs.getString("patienttype");
                                            coderP();
                                        }
                                    } else if (count > 0) {
//                                        SELECT * from appointment where patientId=22 order by Appointmentid desc limit 1,1
                                        sql = "SELECT * from appointment where patientId=" + patientid + " order by Appointmentid desc limit 1,1";
                                        PreparedStatement pst2 = con.prepareStatement(sql);
                                        ResultSet rs2 = pst2.executeQuery();
                                        if (rs2.next()) {
                                            String s = "Last visit date of patient is " + rs.getString("date");
                                            Window owner = log_btn.getScene().getWindow();
                                            showAlert(Alert.AlertType.CONFIRMATION, owner, "Form Patient",
                                                    s);
                                            return;
                                        }
                                    }
                                    update();
                                    update2();
                                } catch (Exception e) {
                                    System.out.println(e);
                                }
//                                comboBox.setVisible(false);
                            } else if (value.equals("PostPone")) {

                                App b = getTableView().getItems().get(getIndex());

                                index = getTableRow().getIndex();
//                             zz=b1.getPatientname();
                                newdatepane.setVisible(true);
                            }
                        });

//                        j.get(e -> {
////                            if (e.getCode() == KeyCode.A) {
////                            String t = j.getValue().toString();
//                            System.out.println("A key was pressed");
////                            }
//                        });
//                        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
//                            @Override
//                            public void handle(MouseEvent e) {
//                                System.out.println("Hello World");
//
//                            }
//                        };
                        setGraphic(comboBox);
//                        setGraphic(j);
                        setText(null);
                    }
                }

            };

            return cell;
        };
        treatmentStatus.setCellFactory(cellco2);

        halfday.setOnAction(e -> {
            if (halfday.isSelected()) {
                absent.setSelected(false);
                present.setSelected(false);
            }
            ad = "H";
        });
        absent.setOnAction(e -> {
            if (absent.isSelected()) {
                halfday.setSelected(false);
                present.setSelected(false);
            }
            ad = "A";
        });
        present.setOnAction(e -> {
            if (present.isSelected()) {
                halfday.setSelected(false);
                absent.setSelected(false);
            }
            ad = "P";
        });
        ObservableList<String> data2 = FXCollections.observableArrayList();
        data2.addAll("Doctor", "Receptionist", "Helper", "Accountant");
        employer.setItems(data2);
        employer.setOnAction(e -> {
            if (employer.getValue().equals("Doctor")) {
                t = "doctor";
                UpdateTable();
                UpdateTable2();
            } else if (employer.getValue().equals("Receptionist")) {
                t = "Receptionist";
                UpdateTable();
                UpdateTable2();
            } else if (employer.getValue().equals("Helper")) {
                t = "Helper";
                UpdateTable();
                UpdateTable2();
            } else if (employer.getValue().equals("Accountant")) {
                t = "Accountant";
                UpdateTable();
                UpdateTable2();
            } else {
                t = "doctor";
                UpdateTable();
                UpdateTable2();
            }
        });

        UpdateTable();
        ObservableList<String> data31 = FXCollections.observableArrayList();
        data31.addAll("Mark Attendence", "View Attendence");
        tableView.setItems(data31);
        tableView.setOnAction(e -> {
//            System.out.println(employer.getValue());
            try {
                if (employer.getValue().equals("null")) {
                    Window owner = log_btn.getScene().getWindow();
                    showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                            "Please Enter the type");
                    return;
                }

            } catch (Exception ex) {
                Window owner = log_btn.getScene().getWindow();
                showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                        "Please Enter the type");
                return;
            }
            if (tableView.getValue().equals("Mark Attendence")) {
                attendence_taking_list.setVisible(true);
                attendence_show_list.setVisible(false);
            } else {
                attendence_taking_list.setVisible(false);
                attendence_show_list.setVisible(true);
            }
        });
        t = "doctor";
        Callback<TableColumn<Attendence, String>, TableCell<Attendence, String>> cellco = (params) -> {

            final TableCell<Attendence, String> cell = new TableCell<Attendence, String>() {
                //override updatesItem method
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //ensure that cell is creates only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {

                        Text b = new Text("Attendence");
                        b.setOnMouseClicked(e -> {
                            attendence_mark_pane.setVisible(true);
                        });

                        b.setStyle("-fx-text-fill:black;"
                                + "-fx-background-color:F9F3F2;"
                                + "-fx-background-radius:50");

//                        Position pos=new Position();
                        setGraphic(b);

                        setAlignment(Pos.CENTER);
                        setText(null);
                    }
                }
            };

            return cell;
        };
        attendence_btn.setOnAction(e -> {
            int dcount = 0;
            int id = attendence_taking_list.getSelectionModel().getSelectedIndex();
//                            String s=(String)col_taking_attendence.cellFactoryProperty().getValue();
            System.out.println(id);
//                            int index=col_taking_attendence.getColumns().indexOf(c);                            Attendence d = col_taking_attendence.getTableView().getItems().get(getIndex());
            Attendence c1 = attendence_taking_list.getItems().get(id);
            Calendar cal1 = Calendar.getInstance();
            java.util.Date date = cal1.getTime();//2021-03-24
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = dateFormat.format(date);
//                                System.out.println(formattedDate);
            Connection conn = mysqlconnect.connectDb();
            String sql1 = "select count(*) as row from attendence_table where date='" + formattedDate + "' and id=" + c1.getId() + " and type='" + t + "'";
//                                System.out.println(sql1);
            PreparedStatement pst2;
            try {
                pst2 = conn.prepareStatement(sql1);
                ResultSet d1 = pst2.executeQuery(sql1);
                d1.next();
                dcount = d1.getInt("row");
            } catch (SQLException ex) {
                Logger.getLogger(ReceptionistDeshController.class.getName()).log(Level.SEVERE, null, ex);
            }
//                                System.out.println(dcount);
            if (dcount == 0) {
                Connection con = mysqlconnect.connectDb();

                String sql = "insert into attendence_table(id,type,date,status) values (?,?,?,?)";

                PreparedStatement pst;
                System.out.println(sql);

//                                    System.out.println(ad + " " + c.getDate() + " " + t + " " + d.getId());
                try {

                    long l = date.getTime();
                    try {
                        java.util.Date udate = dateFormat.parse(formattedDate);
                        java.sql.Date sdate = new java.sql.Date(l);
                        pst = con.prepareStatement(sql);
                        pst.setString(1, c1.getId());
                        pst.setString(2, t);
                        pst.setDate(3, sdate);
                        pst.setString(4, ad);
                        System.out.println(c1.getId() + " " + t + " " + sdate + " " + ad);
                        pst.execute();
                        UpdateTable2();
                    } catch (ParseException ex) {
                        Logger.getLogger(ReceptionistDeshController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    attendence_mark_pane.setVisible(false);
                } catch (SQLException ex) {
                    Logger.getLogger(ReceptionistDeshController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                Connection con = mysqlconnect.connectDb();

                String sql = "update attendence_table set status='" + ad + "' where id=" + c1.getId() + " and date='" + formattedDate + "'";
//                                System.out.println(ad + " " + c.getDate() + " " + t + " " + c.getId());
                try {
                    try {
                        PreparedStatement pst = con.prepareStatement(sql);
                        pst.execute();
                        UpdateTable2();
                    } catch (Exception ex) {
                        Logger.getLogger(ReceptionistDeshController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    attendence_mark_pane.setVisible(false);
                } catch (Exception ex) {
                    Logger.getLogger(ReceptionistDeshController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            present.setSelected(false);
            absent.setSelected(false);
            halfday.setSelected(false);

        });

        Callback<TableColumn<item, String>, TableCell<item, String>> cell23 = (params) -> {

            final TableCell<item, String> cell = new TableCell<item, String>() {
                //override updatesItem method
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //ensure that cell is creates only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button button3 = new Button("Received");
                        button3.setStyle("-fx-background-color:red");
                        button3.setStyle("-fx-background-radius:50 50 50 50");
                        button3.setStyle("-fx-text-fill:white");
                        button3.centerShapeProperty();
                        button3.setOnAction(event -> {
                            item m = getTableView().getItems().get(getIndex());
//                                    String id = m.getA();
                            Connection con = mysqlconnect.connectDb();
                            PreparedStatement pst;
                            try {
                                String sql = "update libary set Status='Received' and receivedate= '" + dt + "' where libaryid=" + m.getA() + "";
                                System.out.println(sql);
                                pst = con.prepareStatement(sql);
                                pst.execute();
                                updatepro();
                            } catch (Exception e) {

                            }

                        });

                        setGraphic(button3);
                        setText(null);

                    }
                }
            };

            return cell;
        };
        colliBtn.setCellFactory(cell23);

        Callback<TableColumn<Attendence, String>, TableCell<Attendence, String>> cellc = (params) -> {

            final TableCell<Attendence, String> cell = new TableCell<Attendence, String>() {
                //override updatesItem method
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //ensure that cell is creates only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        Attendence c = getTableView().getItems().get(getIndex());
                        Button b = new Button("Show Attendence");
                        b.setStyle("-fx-text-fill:black;"
                                + "-fx-background-color:F9F3F2;"
                                + "-fx-background-radius:50");
//                        Position pos=new Position();
                        b.setOnAction(e -> {
                            attendence_show_pane.setVisible(true);
                            aName.setText(c.getName());
                            a = c.getId();
                            System.out.println(a + " " + aName.getText());

                            UpdateTable3();
                        });

                        calender_btn.setOnAction(e -> {
//                             start_date;
                            try {
                                if (start_date.getValue().toString().equals("null") || end_date.getValue().toString().equals("null")) {

                                }
                            } catch (Exception ex) {
                                Window owner = log_btn.getScene().getWindow();
                                showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                                        "Please enter the date");
                                return;
                            }

                            Connection con = mysqlconnect.connectDb();
                            String sql = "SELECT * from attendence_table WHERE date between '" + start_date.getValue() + "' and '" + end_date.getValue() + "' and id=" + a + " and type='" + t + "'";
                            try {
                                ObservableList<Attendence> list = FXCollections.observableArrayList();
                                PreparedStatement pst = con.prepareStatement(sql);
                                System.out.println(sql);
                                ResultSet rs = pst.executeQuery();
//                                String u = t + "id";
                                while (rs.next()) {
                                    list.add(new Attendence(rs.getString("id"), rs.getString("date"), rs.getString("status")));
                                }
                                adate.setCellValueFactory(new PropertyValueFactory<Attendence, String>("date"));
                                astatus.setCellValueFactory(new PropertyValueFactory<Attendence, String>("status"));
//                                list = getAttendenceTable();
                                attendence_view.setItems(list);
//                             end_date;
                            } catch (SQLException ex) {
                                Logger.getLogger(ReceptionistDeshController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                        setGraphic(b);
                        setAlignment(Pos.CENTER);

                        setText(null);
                    }
                }
            };

            return cell;
        };

        col_taking_attendence.setCellFactory(cellco);
        show_attendence.setCellFactory(cellc);

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
                            int v = rsw.getInt(1);
                            int d = rsy.getInt(1);
                            sum = sum + v;
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
                    Alert a = new Alert(AlertType.NONE);
                    a.setAlertType(AlertType.CONFIRMATION);
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
                            int v = rsw.getInt(1);
                            int d = rsy.getInt(1);
                            sum = sum + v;
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
                    Alert a = new Alert(AlertType.NONE);
                    a.setAlertType(AlertType.CONFIRMATION);
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

    @FXML
    private void changeWindow(MouseEvent event) {
    }

    @FXML
    private void handelrecords(ActionEvent event) {
        recordpane.setVisible(true);
    }

}
