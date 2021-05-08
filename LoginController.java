/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dental;

import java.awt.HeadlessException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javax.swing.JOptionPane;
import dental.sendSms;

public class LoginController implements Initializable {

    @FXML
    private Pane otpPane;

    @FXML
    private Pane forgetPane;

    @FXML
    private Pane loginpane;
    @FXML
    private TextField password;
    @FXML
    private Button visible;
    @FXML
    private Button hide;

    @FXML
    void handleForgetAction(ActionEvent event) {
        otpPane.setVisible(true);
        loginpane.setVisible(false);
        forgetPane.setVisible(false);
    }

    @FXML
    void handleForgetBackAction(ActionEvent event) {
        otpPane.setVisible(false);
        loginpane.setVisible(true);
        forgetPane.setVisible(false);
    }
    @FXML
    private TextField txt_username;

    @FXML
    private TextField txt_password;

    @FXML
    private ComboBox<String> type;

    @FXML
    private Button btn_login;

    @FXML
    private TextField con;

    @FXML
    private TextField mail;

    @FXML
    void handleAdminBox() {
//        System.out.println(Admin.isIndeterminate());
        if (Admin.isSelected()) {
            SuperAdmin.setSelected(false);
            Doctor.setSelected(false);
            Recption.setSelected(false);
        }
        typess = "admin";
    }
    String typess, phone, email;

    @FXML
    void handleSuperAdminBox() {
        if (SuperAdmin.isSelected()) {
            Admin.setSelected(false);
            Doctor.setSelected(false);
            Recption.setSelected(false);
        }
        typess = "superadmin";
//        System.out.println(typess);
    }

    @FXML
    void handleDoctorBox() {
        if (Doctor.isSelected()) {
            SuperAdmin.setSelected(false);
            Admin.setSelected(false);
            Recption.setSelected(false);

//            SuperAdmin.setSelected(false);
        }
        typess = "doctor";
    }

    @FXML
    void handleRecptionBox() {
        if (Recption.isSelected()) {
            SuperAdmin.setSelected(false);
            Doctor.setSelected(false);
            Admin.setSelected(false);

//            SuperAdmin.setSelected(false);
        }
        typess = "rectionist";
    }

    @FXML
    private CheckBox SuperAdmin;

    @FXML
    private CheckBox Admin;

    @FXML
    private CheckBox Doctor;

    @FXML
    private CheckBox Recption;

    private CheckBox Helper;


    @FXML
    private TextField pass;

    @FXML
    private TextField confirm_pass;

    @FXML
    private TextField otp_txt;

    void resend_password(ActionEvent event) {
        String sql = "select * from otptable order by id desc limit 1 ";
        Connection conn = mysqlconnect.connectDb();

        try {
            pst = conn.prepareStatement(sql);
//                pst.setString(1, email);
            rs = pst.executeQuery();
            if (rs.next()) {
                sendSms.SendSms(rs.getString("otp"), phone);
//                    System.out.println(rs.getString("otp"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    @FXML
    void forget_password(ActionEvent event) throws Exception {
//        System.out.println(Admin.isIndeterminate());
//        System.out.println(SuperAdmin.isIndeterminate());
//        System.out.println(Doctor.isIndeterminate());
//        System.out.println(Recption.isIndeterminate());

        String mailS = mail.getText();
        String conS = con.getText();

        Window owner = con.getScene().getWindow();

//        System.out.println("con" + con.getText().isEmpty());
        if (mail.getText().isEmpty() && con.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your Mail or Contant number");
            return;
        }

//        System.out.println(mail.getText().isEmpty() || con.getText().isEmpty());
        if (!(Admin.isSelected() || SuperAdmin.isSelected() || Doctor.isSelected() || Recption.isSelected())) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your Formate");
            return;
        }
        Random rand = new Random();
        int rand_int1 = rand.nextInt(9999);

//        System.out.println(rand_int1);
        String message = String.valueOf(rand_int1);

        if (!con.getText().isEmpty()) {
            String sql = String.format("Select * from %s where number= ?", typess);
            Connection conn = mysqlconnect.connectDb();

            try {
                pst = conn.prepareStatement(sql);
                pst.setString(1, con.getText());
//                sendSms.smsSend(message,l);
                rs = pst.executeQuery();
                phone = con.getText();
//                System.out.println(phone);
//                sendSms.SendSms(message,phone);

//                sNo=rs.getInt("id");
                if (rs.next()) {
                    sql = "insert into otptable (email,otp) values (?,?)";
                    try {
                        PreparedStatement pst = conn.prepareStatement(sql);
                        email = rs.getString("email");
                        pst.setString(1, email);

                        pst.setString(2, message);

//                        System.out.println(rs.getString("email"));
//                        sNo=rs.getInt("id");
                        pst.execute();

                        JOptionPane.showMessageDialog(null, "Otp is sended to the Contant Number");
                    } catch (HeadlessException | SQLException e) {
                        JOptionPane.showMessageDialog(null, e);
                    }
//                    javaMail.sendEmail(message, mail.getText());
                    /////////////////////////
                    loginpane.setVisible(false);
                    forgetPane.setVisible(true);
                    otpPane.setVisible(false);

//                    System.out.println("hello");
                } else {
                    JOptionPane.showMessageDialog(null, "Enter Contant Number doesn't exist");
//                    System.out.println("Enter Contant Number doesn't exist");
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }

        } else if (!mail.getText().isEmpty()) {
//            System.out.println(typess);
            String sql = String.format("Select email from %s where email= ?", typess);
            Connection conn = mysqlconnect.connectDb();

            try {
                pst = conn.prepareStatement(sql);
                pst.setString(1, mail.getText());
                rs = pst.executeQuery();
//                sNo=rs.getInt("id");
                if (rs.next()) {
                    sql = "insert into otptable (mail,otp) values (?,?)";
                    try {
                        PreparedStatement pst = conn.prepareStatement(sql);
                        email = mail.getText();
                        pst.setString(1, mail.getText());

                        pst.setString(2, message);

//                        sNo=rs.getInt("id");
                        pst.execute();

                        JOptionPane.showMessageDialog(null, "Otp is sended to the E-Mail");
                    } catch (HeadlessException | SQLException e) {
                        JOptionPane.showMessageDialog(null, e);
                    }
                    javaMail.sendEmail(message, mail.getText());
                    ///////////////////////////////////

                    loginpane.setVisible(false);
                    forgetPane.setVisible(true);
                    otpPane.setVisible(false);

//                    System.out.println("hello");
                } else {
                    JOptionPane.showMessageDialog(null, "Enter E-Mail doesn't exist");
//                    System.out.println("Enter E-Mail doesn't exist");
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }

        }

    }

    void handleForget(ActionEvent event) {
//        System.out.println(Admin.isIndeterminate());
//        System.out.println(SuperAdmin.isIndeterminate());
//        System.out.println(Doctor.isIndeterminate());
//        System.out.println(Recption.isIndeterminate());
//        System.out.println(Helper.isIndeterminate());
        String mailS = mail.getText();
        String conS = con.getText();

        Window owner = mail.getScene().getWindow();

//        System.out.println("con" + con.getText().isEmpty());
        if (mail.getText().isEmpty() && con.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your Mail or Contant number");
            return;
        }

//        System.out.println(mail.getText().isEmpty() || con.getText().isEmpty());
        if (!(Helper.isSelected() || Admin.isSelected() || SuperAdmin.isSelected() || Doctor.isSelected() || Recption.isSelected())) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your Formate");
            return;
        }
        Random rand = new Random();
        int rand_int1 = rand.nextInt(9999);

//        System.out.println(rand_int1);
        String message = String.valueOf(rand_int1);

        if (!con.getText().isEmpty()) {
            String sql = String.format("Select * from %s where number= ?", type);
            Connection conn = mysqlconnect.connectDb();

            try {
                pst = conn.prepareStatement(sql);
                pst.setString(1, con.getText());
//                sendSms.smsSend(message,l);
                rs = pst.executeQuery();
                phone = con.getText();
//                System.out.println(phone);
//                sendSms.SendSms(message,phone);

//                sNo=rs.getInt("id");
                if (rs.next()) {
                    sql = "insert into otptable (email,otp) values (?,?)";
                    try {
                        PreparedStatement pst = conn.prepareStatement(sql);
                        email = rs.getString("email");
                        pst.setString(1, email);

                        pst.setString(2, message);

//                        System.out.println(rs.getString("email"));
//                        sNo=rs.getInt("id");
                        pst.execute();

                        JOptionPane.showMessageDialog(null, "Otp is sended to the Contant Number");
                    } catch (HeadlessException | SQLException e) {
                        JOptionPane.showMessageDialog(null, e);
                    }
//                    javaMail.sendEmail(message, mail.getText());
//                    otpPane.setVisible(false);
//                    loginpane.setVisible(true);
                    /////////////////////////////
//                    System.out.println("hello");
                } else {
                    JOptionPane.showMessageDialog(null, "Enter Contant Number doesn't exist");
//                    System.out.println("Enter Contant Number doesn't exist");
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }

        } else if (!mail.getText().isEmpty()) {
            String sql = String.format("Select email from %s where email= ?", type);
            Connection conn = mysqlconnect.connectDb();

            try {
                pst = conn.prepareStatement(sql);
                pst.setString(1, mail.getText());
                rs = pst.executeQuery();
//                sNo=rs.getInt("id");
                if (rs.next()) {
                    sql = "insert into otptable (mail,otp) values (?,?)";
                    try {
                        PreparedStatement pst = conn.prepareStatement(sql);
                        email = mail.getText();
                        pst.setString(1, mail.getText());

                        pst.setString(2, message);

//                        sNo=rs.getInt("id");
                        pst.execute();

                        JOptionPane.showMessageDialog(null, "Otp is sended to the E-Mail");
                    } catch (HeadlessException | SQLException e) {
                        JOptionPane.showMessageDialog(null, e);
                    }
                    javaMail.sendEmail(message, mail.getText());
                    /////////////////////

//                    System.out.println("hello");
                } else {
                    JOptionPane.showMessageDialog(null, "Enter E-Mail doesn't exist");
//                    System.out.println("Enter E-Mail doesn't exist");
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }

        }
        otpPane.setVisible(false);
        loginpane.setVisible(true);
    }
    @FXML
    private Button back;
    @FXML
    private Button Confirm_btn;

    @FXML
    void otp_sumit_method(ActionEvent event) {
        String sql = "select * from otptbale where otp =?";
        Connection conn = mysqlconnect.connectDb();
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, otp_txt.getText());
            rs = pst.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Enter OTP is match");
//                System.out.println("Enter OTP is match");
                ////////////////////////////

                pass.setVisible(true);
                confirm_pass.setVisible(true);
                back.setVisible(true);
                Confirm_btn.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Enter OTP doesn't exist");
//                System.out.println("Enter OTP doesn't exist");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    @FXML
    void handleLog(ActionEvent event) {
//        System.out.println(typess);
//        System.out.println(sNo);
        String sql = String.format("update %s set password=? where email='%s'", typess, email);
//        System.out.println(sql);
        Connection conn = mysqlconnect.connectDb();
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, pass.getText());
            if (pass.getText().equals(confirm_pass.getText())) {
                pst.execute();
//                System.out.println("hello");
                ///////////////////////
                loginpane.setVisible(true);
                forgetPane.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(null, "Password are not match");
            }

//            pst.setString(2, message);
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    @FXML
    void handleLogInAction(ActionEvent event) {
        Window owner = btn_login.getScene().getWindow();
        if (txt_username.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your name");
            return;
        }
        if (txt_password.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter a password");
            return;
        }

//        System.out.println(type.isPressed());
//        if(type.i){
//            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
//                    "Please select the type to Login as");
//            return;
//        }
//System.out.println(type.getValue()==null);
        types = type.getValue();
        try {
            types = types.toLowerCase();
        } catch (Exception e) {
//            System.out.println("hello");
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please select the type to Login as");
            return;
        }
        if (types.equals("super-admin")) {
            types = "superadmin";
        }
        String user = txt_username.getText();
        Connection conn = mysqlconnect.connectDb();
        String sql = String.format("select * from %s where email= ? and password= ?", types);
//        String sql = "Select * from admin where username = ? and password = ?  ";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, txt_username.getText());
            pst.setString(2, txt_password.getText());
//            pst.setString(3, type.getValue().toString());

            rs = pst.executeQuery();

            if (rs.next()) {
                user = rs.getString("name");
                b = rs.getString("branch");
                id = rs.getString(1);
//                System.out.println(id);
                e = rs.getString("email");
//                System.out.println(rs.getString("email"));
            }
        } catch (Exception e) {
//            System.out.println(e);
        }

        u = user;
//        System.out.println(u);
        p = txt_password.getText();
//        String sql=String.format("Select * from admin where username='%s' and password='%s'",user,pass);
//        System.out.println(sql);
//        Statement st=conn.createStatement();
//        ResultSet rs=st.executeQuery(sql);
        t = types;
        sql = String.format("select * from %s where email= ? and password= ?", types);
//        String sql = "Select * from admin where username = ? and password = ?  ";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, txt_username.getText());
            pst.setString(2, txt_password.getText());
//            pst.setString(3, type.getValue().toString());

            rs = pst.executeQuery();

            if (rs.next()) {
//                if (true) {
//                    System.out.println();
//                    Window owners = btn_login.getScene().getWindow();
//                    showAlert(Alert.AlertType.CONFIRMATION, owners, "Form Error!", "Username And Password is Correct");
//                }
//                  JOptionPane.showMessageDialog(null, "Username And Password is Correct");
                if (types.equals("admin")) {
//                    System.out.println("admin");

                    btn_login.getScene().getWindow().hide();
                    Parent root = FXMLLoader.load(getClass().getResource("Admindeshboard.fxml"));
                    Stage mainStage = new Stage();
                    Scene scene = new Scene(root);
                    mainStage.setScene(scene);
                    mainStage.show();
                } else if (types.equals("doctor")) {
//                    System.out.println("Doctor");
                    btn_login.getScene().getWindow().hide();
                    Parent root = FXMLLoader.load(getClass().getResource("Appointment.fxml"));
                    Stage mainStage = new Stage();
                    Scene scene = new Scene(root);
                    mainStage.setTitle("Doctor Page");
                    mainStage.setScene(scene);
                    mainStage.show();
                } else if (types.equals("superadmin")) {
//                    System.out.println("Super-Admin");
                    btn_login.getScene().getWindow().hide();
                    Parent root = FXMLLoader.load(getClass().getResource("SuperAdmin.fxml"));
                    Stage mainStage = new Stage();
                    Scene scene = new Scene(root);
                    mainStage.setTitle("Super-Admin Page");
                    mainStage.setScene(scene);
                    mainStage.show();
                } else if (types.equals("receptionist")) {
//                    System.out.println("Resptionest");
                    btn_login.getScene().getWindow().hide();
                    Parent root = FXMLLoader.load(getClass().getResource("ReceptionestDeshboard.fxml"));
                    Stage mainStage = new Stage();
                    Scene scene = new Scene(root);
                    mainStage.setTitle("Resptionest Page");
                    mainStage.setScene(scene);
                    mainStage.show();
                } else if (types.equals("accountant")) {
//                    System.out.println("Accountant");
                    btn_login.getScene().getWindow().hide();
                    Parent root = FXMLLoader.load(getClass().getResource("AccountantDashboard.fxml"));
                    Stage mainStage = new Stage();
                    Scene scene = new Scene(root);
                    mainStage.setTitle("Accountant Page");
                    mainStage.setScene(scene);
                    mainStage.show();
                }
            } else {
                showAlert(Alert.AlertType.ERROR, owner, "Form Error!", "Invalide Username Or Password");
//                JOptionPane.showMessageDialog(null, "Invalide Username Or Password");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    static String u, p, t, types, b, id, e;

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txt_password.onKeyReleasedProperty().set(e->{
            password.setText(txt_password.getText());
        });
        password.onKeyReleasedProperty().set(e->{
            txt_password.setText(password.getText());
        });
        boolean addAll;
//        profileInfo();
//        addAll = type_up.getItems().addAll("Super", "Admin", "Reception", "Doctor");
        addAll = type.getItems().addAll("Super-Admin", "Admin", "Doctor", "Receptionist", "Accountant");
        visible.setOnAction(e->{
            txt_password.setVisible(false);
            password.setText(txt_password.getText());
            password.setVisible(true);
            visible.setVisible(false);
            hide.setVisible(true);
        });
        hide.setOnAction(e->{
            password.setVisible(false);
            txt_password.setText(password.getText());
            txt_password.setVisible(true);
            hide.setVisible(false);
            visible.setVisible(true);
        });
    }

    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();//To change body of generated methods, choose Tools | Templates.
    }
}
