/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dental;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
import javafx.util.Callback;
import javax.swing.JOptionPane;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDJpeg;

/**
 * FXML Controller class
 *
 * @author doagu
 */
public class SuperAdminControllerDashboard implements Initializable {

    @FXML
    void profileInfo(){
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
    private AnchorPane profilePane;
    
    
    @FXML
    void handleProfile(ActionEvent event) throws IOException{
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
    void closeWindow(ActionEvent event) {
        profilePane.setVisible(false);
    }
    
    @FXML
    void handelaccountbranches(ActionEvent event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Accountant.fxml"));          
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Accountant Window");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
//            System.out.println("cont load");
        }
    }
    
    @FXML
    void handleDoctorStatus(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("Inventory.fxml"));
            Stage mainStage = new Stage();
            Scene scene = new Scene(root);
            mainStage.setTitle("Personal Infomation");
            mainStage.setScene(scene);
            mainStage.show();
    }
    
    @FXML
    void handelpatient(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Patient.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Patient Window");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
//            System.out.println("cont load");
        }
        
    }
    @FXML
    void handeladmin(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Admin.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Admin Window");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
//            System.out.println("cont load");
        }
        
    }
    
    @FXML
    void handelbranches(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Branch.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Branches Window");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
//            System.out.println("cont load");
        }
    }
    
    @FXML
    void handeldoctors(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Doctor.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Doctors Window");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
//            System.out.println("cont load");
        }
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
//            System.out.println("cont load");
        }
    }
    
    @FXML
    void handelreceptionest(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Receptionist.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Receptionist Window");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
//            System.out.println("cont load");
        }
    }
    
    @FXML
    void handeltreatment(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Treatment.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Treatment window");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
//            System.out.println("cont load");
        }
    }
    
    @FXML
//    private Button log_btn;
    public Button log_btn;
    
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
    String b,no,ad,e,i,n,value,t;
    @Override
    public void initialize(URL url, ResourceBundle rb) {  
        
    }
    
    
    @FXML
    private Pane changePane;
    
    @FXML
    void changeWindow(ActionEvent event) {
        changePane.setVisible(true);
    }
    
    public void newpdf() throws FileNotFoundException, IOException{
    PDDocument doc= new  PDDocument();
    PDPage page = new PDPage();
    doc.addPage(page);
    FileInputStream in = new FileInputStream("C://Users//doagu//Downloads//3203243.pdf");
    PDJpeg img = new PDJpeg(doc, in);
//    PDPageContentStream str = new PDPageContentStream(img);
    
    }
    
    @FXML
    void ForgetWindow(ActionEvent event) {
        if(p.getText().equals(cp.getText())){
        Connection conn = mysqlconnect.connectDb();
        String sql = "update superadmin set password= ? where superadminId="+LoginController.id;
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
    
}
