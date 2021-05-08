/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dental;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javax.swing.JOptionPane;

/**
 *
 * @author doagu
 */
public class DotorDashController implements Initializable{

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
    }

    @FXML
    void handelhelper(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Helper.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Helper window");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
//            System./out.println("cont load");
        }
    }

    @FXML
    void handelpatient(ActionEvent event) {
         try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Patient.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Patient window");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
//            System.out.println("cont load");
        }
    }

    @FXML
    void handelreceptionest(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Receptionist.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("receptionest window");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
//            System.out.println("cont load");
        }
    }

    @FXML
    void handleProfile(ActionEvent event) {
        profilePane.setVisible(true);
        Connection conn = mysqlconnect.connectDb();
        String sql=String.format("Select * from %s where name='%s' and password='%s'",LoginController.t,LoginController.u,LoginController.p);
//        System.out.println(sql);
        Statement st;
        try {
            st = conn.createStatement();
            ResultSet rs=st.executeQuery(sql);
            if(rs.next()){
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
    private Pane changePane;
    @FXML
    void changeWindow(ActionEvent event) {
        changePane.setVisible(true);
    }
    
    @FXML
    void ForgetWindow(ActionEvent event) {
//        System.out.println(p.getText().equals(cp.getText()));
        if(p.getText().equals(cp.getText())){
        Connection conn = mysqlconnect.connectDb();
        String sql = "update doctor set password= ? where DoctorId="+LoginController.id;
        try {
//            System.out.println(sql + " ");
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, p.getText());
            pst.execute();
            Window owner = log_btn.getScene().getWindow();
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Password is Changed");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        }else{
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
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
}
