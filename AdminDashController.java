/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dental;

import static dental.mysqlconnect.connectDb;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Callback;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.CheckBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import javafx.geometry.Pos;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.text.Text;

/**
 *
 * @author doagu
 */
public class AdminDashController implements Initializable {
//attendence taking List show

    @FXML
    private AnchorPane attendence_taken_list;

    @FXML
    private ComboBox employer;

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
//            System.out.print("Exception " + e);
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
            String sql = "select * from " + t;
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            String id = t + "id";
            while (rs.next()) {
                list.add(new Attendence(rs.getString(id), rs.getString("name"), rs.getString("number"), rs.getString("email")));
            }
        } catch (Exception e) {
//            System.out.print("Exception " + e);
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

    @FXML
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
            String sql = "select * from attendence_table where type='" + t + "' and id=" + i;
//                LocalDate myObj = LocalDate.now();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            String id = "id";;
            while (rs.next()) {
                list.add(new Attendence(rs.getString(id), rs.getString("date"), rs.getString("status")));
            }
        } catch (Exception e) {
//            System.out.print("Exception " + e);
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
    void profileInfo() {
        Connection conn = mysqlconnect.connectDb();
        String sql = String.format("Select * from %s where name='%s' and password='%s'", LoginController.t, LoginController.u, LoginController.p);
//        System.out.println(sql);
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
    private Label name;

    @FXML
    private Label moblie;

    @FXML
    private Button attendence_btn;

    @FXML
    private Label email2;

    @FXML
    private Label branch;

    @FXML
    private Label address;

    @FXML
    private AnchorPane profilePane;

    @FXML
    private DatePicker start_date;

    @FXML
    private DatePicker end_date;

    @FXML
    void handleProfile(ActionEvent event) throws IOException {
        profilePane.setVisible(true);
        Connection conn = mysqlconnect.connectDb();
        String sql = String.format("Select * from %s where name='%s' and password='%s'", LoginController.t, LoginController.u, LoginController.p);
//        System.out.println(sql);
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
    void closeWindow(ActionEvent event) {
        profilePane.setVisible(false);
    }

    @FXML
    void handelaccountbranches(ActionEvent event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Accountant.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Branches window");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
//            System.out.println("cont load");
        }
    }

    @FXML
    void handleDoctorStatus(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Shift.fxml"));
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
            stage.setTitle("Admin window");
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
            stage.setTitle("Admin window");
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
            stage.setTitle("branchestwo window");
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
            stage.setTitle("doctors window");
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
    private Pane changePane;

    @FXML
    void changeWindow(ActionEvent event) {
        changePane.setVisible(true);
    }

    @FXML
    void ForgetWindow(ActionEvent event) {
        if (p.getText().equals(cp.getText())) {
            Connection conn = mysqlconnect.connectDb();
            String sql = "update admin set password= ? where AdminId=" + LoginController.id;
            try {
//                System.out.println(sql + " ");
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
    private Button calender_btn;

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
    private CheckBox present;

    @FXML
    private CheckBox absent;

    @FXML
    private CheckBox halfday;

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
    String b, no, ad, e, i, n, value, t;

    @FXML
    private Text aName;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        

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
        t = "doctor";
        UpdateTable();
        ObservableList<String> data = FXCollections.observableArrayList();
        data.addAll("Mark Attendence", "View Attendence");
        tableView.setItems(data);
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
//            System.out.println(id);
//                            int index=col_taking_attendence.getColumns().indexOf(c);                            Attendence d = col_taking_attendence.getTableView().getItems().get(getIndex());
            Attendence c = attendence_taking_list.getItems().get(id);
            Calendar cal = Calendar.getInstance();
            java.util.Date date = cal.getTime();//2021-03-24
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = dateFormat.format(date);
//                                System.out.println(formattedDate);
            Connection conn = mysqlconnect.connectDb();
            String sql1 = "select count(*) as row from attendence_table where date='" + formattedDate + "' and id=" + c.getId() + " and type='" + t + "'";
//                                System.out.println(sql1);
            PreparedStatement pst2;
            try {
                pst2 = conn.prepareStatement(sql1);
                ResultSet d1 = pst2.executeQuery(sql1);
                d1.next();
                dcount = d1.getInt("row");
            } catch (SQLException ex) {
                Logger.getLogger(AdminDashController.class.getName()).log(Level.SEVERE, null, ex);
            }
//                                System.out.println(dcount);
            if (dcount == 0) {
                Connection con = mysqlconnect.connectDb();

                String sql = "insert into attendence_table(id,type,date,status) values (?,?,?,?)";
                PreparedStatement pst;
//                System.out.println(c.getId());
//                                    System.out.println(ad + " " + d.getDate() + " " + t + " " + d.getId());
                try {

                    long l = date.getTime();
                    try {
                        java.util.Date udate = dateFormat.parse(formattedDate);
                        java.sql.Date sdate = new java.sql.Date(l);
                        pst = con.prepareStatement(sql);
                        pst.setString(1, c.getId());
                        pst.setString(2, t);
                        pst.setDate(3, sdate);
                        pst.setString(4, ad);
                        pst.execute();
                        UpdateTable2();
                    } catch (ParseException ex) {
                        Logger.getLogger(AdminDashController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    attendence_mark_pane.setVisible(false);
                } catch (SQLException ex) {
                    Logger.getLogger(AdminDashController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                Connection con = mysqlconnect.connectDb();

                String sql = "update attendence_table set status='" + ad + "' where id=" + c.getId() + " and date='" + formattedDate + "'";
//                                System.out.println(ad + " " + c.getDate() + " " + t + " " + c.getId());
                try {
                    try {
                        PreparedStatement pst = con.prepareStatement(sql);
                        pst.execute();
                        UpdateTable2();
                    } catch (Exception ex) {
                        Logger.getLogger(AdminDashController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    attendence_mark_pane.setVisible(false);
                } catch (Exception ex) {
                    Logger.getLogger(AdminDashController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            present.setSelected(false);
            absent.setSelected(false);
            halfday.setSelected(false);

        });
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
                            i = c.getId();

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
                            String sql = "SELECT * from attendence_table WHERE date between '" + start_date.getValue() + "' and '" + end_date.getValue() + "' and id=" + c.getId() + " and type='" + t + "'";
                            try {
                                ObservableList<Attendence> list = FXCollections.observableArrayList();
                                PreparedStatement pst = con.prepareStatement(sql);
//                                System.out.println(sql);
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
                                Logger.getLogger(AdminDashController.class.getName()).log(Level.SEVERE, null, ex);
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
    }

}
