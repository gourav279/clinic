/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dental;

import static dental.PatientController.*;
import static dental.mysqlconnect.connectDb;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author doagu
 */
public class PatientProfileController implements Initializable {

    @FXML
    private ImageView imageview;
    @FXML
    private Label lblName;
    @FXML
    private Label lbPage;
    @FXML
    private Label lblGender;
    @FXML
    private Label lblNumber;
    @FXML
    private Label lblEmail;
    @FXML
    private Label lblAddress;
    @FXML
    private Label lblUHID;
    @FXML
    private TableView<PatientProfile> tableview;
    @FXML
    private TableColumn<PatientProfile, String> col_name;
    @FXML
    private TableColumn<PatientProfile, String> col_date;

    /**
     * Initializes the controller class.
     *
     */
        public static ObservableList<PatientProfile> getProfile() {
        Connection conn = mysqlconnect.connectDb();
        ObservableList<PatientProfile> list = FXCollections.observableArrayList();
        try {
            String sql = "select * from treatmenthistory where patientId='" + i + "'";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
//            System.out.println("Observation List"+rs.getString(1));
            while (rs.next()) {
                list.add(new PatientProfile(rs.getString("patientname"), rs.getString("date")));
            }
        } catch (Exception e) {
            System.out.print("Exception " + e);
        }
        return list;
    }
    ObservableList<PatientProfile> listM;
        public void UpdateTable() throws SQLException {
        col_name.setCellValueFactory(new PropertyValueFactory<PatientProfile, String>("name"));
        col_date.setCellValueFactory(new PropertyValueFactory<PatientProfile, String>("date"));

        listM = getProfile();
        tableview.setItems(listM);
    }
    


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            UpdateTable();
        } catch (SQLException ex) {
            Logger.getLogger(PatientProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
        // TODO
//        String Name=n;
//        String Email=e;
//        String Address=ad;
        lblName.setText(n);
        lblEmail.setText(e);
        lblAddress.setText(ad);
        lblNumber.setText(no);
        lblGender.setText(PatientController.gender);
        lbPage.setText(PatientController.age);
        lblUHID.setText(PatientController.uhid);

    }

}
