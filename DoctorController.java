/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dental;

import com.jfoenix.controls.JFXTimePicker;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import javax.swing.JOptionPane;

/**
 *
 * @author doagu
 */
public class DoctorController implements Initializable {

    @FXML
    private TextField txt_name;

    @FXML
    private TextField txt_number;

    @FXML
    private ComboBox txt_branch;

    @FXML
    private TextField txt_email;

    private TextField txt_id;

    @FXML
    private TextArea txt_address;

    @FXML
    private TextField txt_type;

    @FXML
    private TextField filterField;

    @FXML
    private TableView<Doctor> table_users;

    @FXML
    private TableColumn<Doctor, Integer> col_id;

    @FXML
    private TableColumn<Doctor, String> col_name;

    @FXML
    private TableColumn<Doctor, String> col_number;

    @FXML
    private TableColumn<Doctor, String> col_branch;

    @FXML
    private TableColumn<Doctor, String> col_email;

    @FXML
    private TableColumn<Doctor, String> colType;

    @FXML
    private TableColumn<Doctor, String> colAddress;

    @FXML
    private TableColumn colCom;
    @FXML
    private TableColumn<Doctor, String> colShift;
    static String i, n, e, ad, no, t, b, value;

    @FXML
    private Button btnback;
    @FXML
    private AnchorPane message_panel;
    @FXML
    private Button add_btn;
    @FXML
    private Button edit_btn;

    @FXML
    void back(ActionEvent event) {
        btnback.getScene().getWindow().hide();
    }
    @FXML
    private AnchorPane addPane, editPane, messagePane;

    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();//To change body of generated methods, choose Tools | Templates.
    }

    @FXML
    private Pane informationMessage;

    @FXML
    void addBack(ActionEvent event) {
        addPane.setVisible(false);
        editPane.setVisible(false);
        messagePane.setVisible(false);
        shiftPane.setVisible(false);
        try {
            UpdateTable();
        } catch (SQLException ex) {
            Logger.getLogger(DoctorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private CheckBox mail, sms;

    @FXML
    void addMember(ActionEvent event) {
        conn = mysqlconnect.connectDb();
        String sql = "insert into doctor (Name,Number,Branch,Email,Password,Address,Specialist)values(?,?,?,?,?,?,?)";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, txt_name.getText());
            pst.setString(2, txt_number.getText());
            pst.setString(3, String.valueOf(txt_branch.getValue()));
            pst.setString(4, txt_email.getText());
            pst.setString(5, txt_password.getText());
            pst.setString(6, txt_address.getText());
            pst.setString(7, txt_type.getText());
            pst.execute();

            String message = "Name=" + txt_name.getText() + "\nNumber=" + txt_number + "\nNew Doctor is comming";
//            System.out.println(txt_name.getText() + " " + txt_number.getText() + " " + txt_branch.getValue());
            if (mail.isSelected()) {
                String sql1 = String.format("select * from doctor where name='%s' and password='%s'", LoginController.u, LoginController.p);
                pst = conn.prepareStatement(sql1);
                rs = pst.executeQuery();
                String b;
                if (rs.next()) {
                    b = rs.getString("branch");
                    sql1 = String.format("Select email from doctor where branch='%s'", b);
                    pst = conn.prepareStatement(sql1);
                    rs = pst.executeQuery();

                    if (rs.next()) {
                        javaMail.sendEmail(message, rs.getString("email"));
//                        System.out.println(rs.getString("email"));
                        while (rs.next()) {
                            javaMail.sendEmail(message, rs.getString("email"));
//                            System.out.println(rs.getString("email"));
                        }
                    }
                }
            }
            if (sms.isSelected()) {
                String sql1 = String.format("select * from doctor where name='%s' and password='%s'", LoginController.u, LoginController.p);
                pst = conn.prepareStatement(sql1);
                rs = pst.executeQuery();
                String b;
                if (rs.next()) {
                    b = rs.getString("branch");
                    sql1 = String.format("Select number from doctor where branch='%s'", b);
                    pst = conn.prepareStatement(sql1);
                    rs = pst.executeQuery();

                    if (rs.next()) {
                        sendSms.SendSms(message, rs.getString("number"));
//                        System.out.println(rs.getString("number"));
                        while (rs.next()) {
                            sendSms.SendSms(message, rs.getString("number"));
//                            System.out.println(rs.getString("email"));
                        }
                    }
                }
            }
//            System.out.println(mail.isSelected() + "" + sms.isSelected());
//            sql = "insert into shift(name) values(?)";
//            pst = conn.prepareStatement(sql);
//            pst.setString(1, txt_name.getText());
//            pst.execute();
            addPane.setVisible(false);
            messagePane.setVisible(false);
            Window owner = btnback.getScene().getWindow();
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Doctor is added");
            UpdateTable();
            txt_type.clear();
            txt_address.clear();
            txt_password.clear();
            txt_email.clear();
            txt_number.clear();
            txt_name.clear();
            txt_branch.getSelectionModel().clearSelection();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    ObservableList<Doctor> listM;
    ObservableList<Doctor> dataList;
    int index = -1;
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    Statement st = null;
    static String num;
    static String name;

//    public void Add_users() {
//        conn = mysqlconnect.connectDb();
//        String sql = "insert into Doctor (Name,Number,Branch,Email)values(?,?,?,?)";
//        try {
//            pst = conn.prepareStatement(sql);
//            pst.setString(1, txt_username.getText());
//            pst.setString(2, txt_password.getText());
//            pst.setString(3, txt_email.getText());
//            pst.setString(4, txt_type.getText());
//            pst.execute();
//            JOptionPane.showMessageDialog(null, "Users Add succes");
//            UpdateTable();
//            search_user();
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, e);
//        }
//    }
    @FXML
    private Button add_btn_doctor;

    @FXML
    void add(ActionEvent event) {
        informationMessage.setVisible(false);
        ObservableList<String> data = FXCollections.observableArrayList();
        String sql = "select * from branch";
        Connection conn = mysqlconnect.connectDb();
        try {
            pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
//            System.out.println(rs.next());
            while (rs.next()) {
//                System.out.println(rs.getString("name"));
                data.add(rs.getString("name"));
            }
            txt_branch.setItems(data);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        addPane.setVisible(true);
    }

    public void UpdateTable() throws SQLException {
        col_id.setCellValueFactory(new PropertyValueFactory<Doctor, Integer>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<Doctor, String>("name"));
        col_number.setCellValueFactory(new PropertyValueFactory<Doctor, String>("number"));
        col_branch.setCellValueFactory(new PropertyValueFactory<Doctor, String>("branch"));
        col_email.setCellValueFactory(new PropertyValueFactory<Doctor, String>("email"));
        colAddress.setCellValueFactory(new PropertyValueFactory<Doctor, String>("Address"));
        colType.setCellValueFactory(new PropertyValueFactory<Doctor, String>("type"));
        colShift.setCellValueFactory(new PropertyValueFactory<Doctor, String>("Shift"));
        listM = mysqlconnect.getDoctor();
//        if(LoginController.t.equals(colEdit))
        table_users.setItems(listM);
    }

    void search_user() throws SQLException {
        col_id.setCellValueFactory(new PropertyValueFactory<Doctor, Integer>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<Doctor, String>("name"));
        col_number.setCellValueFactory(new PropertyValueFactory<Doctor, String>("number"));
        col_branch.setCellValueFactory(new PropertyValueFactory<Doctor, String>("branch"));
        col_email.setCellValueFactory(new PropertyValueFactory<Doctor, String>("email"));
        colAddress.setCellValueFactory(new PropertyValueFactory<Doctor, String>("Address"));
        colType.setCellValueFactory(new PropertyValueFactory<Doctor, String>("type"));
        dataList = mysqlconnect.getDoctor();
        table_users.setItems(dataList);
        FilteredList<Doctor> filteredData = new FilteredList<>(dataList, b -> true);
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
                } else if (String.valueOf(person.getAddress()).indexOf(lowerCaseFilter) != -1) {
                    return true;// Filter matches email
                } else if (String.valueOf(person.getType()).indexOf(lowerCaseFilter) != -1) {
                    return true;// Filter matches email
                } else {
                    return false; // Does not match.
                }
            });
        });
        SortedList<Doctor> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table_users.comparatorProperty());
        table_users.setItems(sortedData);
    }

    private AnchorPane editid;

//    void SubmitEdit(ActionEvent event) {
//        try {
//
//            conn = mysqlconnect.connectDb();
//            String value2 = txt_name1.getText();
//            String value3 = txt_number1.getText();
//            String value4 = txt_branch1.getText();
//            String value5 = txt_email1.getText();
//            String value6 = txt_address1.getText();
//
//            String sql = "update doctor set Name='" + value2 + "', Number='" + value3 + "', Branch='" + value4 + "', Email='" + value5 + "',Address='" + value6 + "' where Doctorid=" + DoctorController.i;
////            System.out.println(sql);
//            pst = conn.prepareStatement(sql);
//            pst.execute();
//
//            Window owner = btnback.getScene().getWindow();
//            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
//                    "Doctor is Edited");
//            editPane.setVisible(false);
//            UpdateTable();
//            search_user();
//
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e);
//        }
//    }

    void clickSubmit(ActionEvent event) {
        editid.setVisible(false);
        try {

            conn = mysqlconnect.connectDb();
            String value1 = txt_id.getText();
            String value2 = txt_name.getText();
            String value3 = txt_number.getText();
            String value4 = String.valueOf(txt_branch.getValue());
            String value5 = txt_email.getText();
            String value6 = txt_address.getText();
            String value7 = txt_type.getText();
            String sql = "update doctor set Doctorid='" + value1 + "', Name='" + value2 + "', Number='" + value3 + "', Branch='" + value4 + "', Email='" + value5 + "',Address='" + value6 + "',Specialist='" + value7 + "' where Doctorid=" + value1 + "";
            pst = conn.prepareStatement(sql);
            pst.execute();
//            sql = "select * from doctor";

//            pst = conn
//                                JOptionPane.showMessageDialog(null, "Update");
            UpdateTable();
            search_user();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private TextArea messageDoc;

    @FXML
    void sendMeassage(ActionEvent event) {
//        System.out.println(messageDoc.getText());
//        sendSms.SendSms(messageDoc.getText(), num);
        meassage_panel.setVisible(false);
    }

    private AnchorPane meassage_panel;
    @FXML
    private TextField txt_name1;

    @FXML
    private TextField txt_number1;

    @FXML
    private TextField txt_email1;

    @FXML
    private TextField txt_branch1, txt_password;

    @FXML
    private TextArea txt_address1;
    @FXML
    private AnchorPane shiftPane;

    @FXML
    void shiftMethod(ActionEvent event) throws SQLException {
        try {
            Connection con = mysqlconnect.connectDb();
            String t = enterTime.getValue().toString() + "-" + goingTime.getValue().toString();
            String sql = "update doctor set shift"
                    + "='" + t + "' where doctorid=" + Aid;
            PreparedStatement pst = con.prepareStatement(sql);

//        pst.setString(1,t);
            pst.execute();
            shiftPane.setVisible(false);
        } catch (Exception e) {
//            System.out.println(e);
        }
        UpdateTable();
    }
    @FXML
    private JFXTimePicker enterTime;

    @FXML
    private JFXTimePicker goingTime;
    static int Aid;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mail.setSelected(true);
        sms.setSelected(true);

        try {
            UpdateTable();
        } catch (SQLException ex) {
            Logger.getLogger(DoctorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            search_user();
        } catch (SQLException ex) {
            Logger.getLogger(DoctorController.class.getName()).log(Level.SEVERE, null, ex);
        }

//        if (LoginController.t.equals("admin") || LoginController.t.equals("doctor") || LoginController.t.equals("receptionist") || LoginController.t.equals("accountant")) {
//            col_branch.setVisible(false);
//
//        }
        Callback<TableColumn<Doctor, String>, TableCell<Doctor, String>> cellco = (params) -> {

            final TableCell<Doctor, String> cell = new TableCell<Doctor, String>() {
                //override updatesItem method
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //ensure that cell is creates only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        ComboBox comboBox = new ComboBox();
                        comboBox.setPromptText("Action Act");
                        comboBox.getItems().add("Edit");
                        comboBox.getItems().add("Delete");
                        comboBox.getItems().add("SMS");
                        if (LoginController.t.equals("superadmin") || LoginController.t.equals("admin")) {
                            comboBox.getItems().add("Shift");
                        }
                        comboBox.setMaxWidth(100);

                        comboBox.setOnAction(event -> {
                            value = (String) comboBox.getValue();
                            if (value.equals("Edit")) {
                                Doctor a = getTableView().getItems().get(getIndex());
                                n = a.getName();
                                i = String.valueOf(a.getId());
                                e = a.getEmail();
                                ad = a.getAddress();
                                no = a.getNumber();
                                b = a.getBranch();

                                editPane.setVisible(true);
                                txt_name1.setText(n);
                                txt_number1.setText(no);
                                txt_email1.setText(e);
                                txt_address1.setText(ad);
                                txt_branch1.setText(b);
                            } else if (value.equals("Delete")) {
                                Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Confirmation Dialog");
                                alert.setHeaderText(null);
                                alert.setContentText("Are you sure want to delete");
                                Optional <ButtonType> action= alert.showAndWait();
                                if(action.get()== ButtonType.OK){
                                conn = mysqlconnect.connectDb();
                                Doctor a = getTableView().getItems().get(getIndex());
                                String s = "DELETE FROM `shift` WHERE DoctorId=" + a.getId();
                                try {
//                                    System.out.println(s + " " + a.getId());
                                    pst = conn.prepareStatement(s);
                                    pst.execute();
                                } catch (SQLException ex) {
                                    Logger.getLogger(DoctorController.class.getName()).log(Level.SEVERE, null, ex);
                                }
//                                    pst.setString(1, txt_id.getText());
                                String sql = String.format("delete from doctor where doctorid = '%s'", a.getId());
                                try {
//                                    System.out.println(sql);
//                                    JOptionPane.showMessageDialog(null, "Delete");
                                    pst = conn.prepareStatement(sql);
//                                    pst.setString(1, txt_id.getText());
                                    pst.execute();
//                                    sql="delete from doctor where name='"+a.getName()+"'";
//                                    pst = conn.prepareStatement(sql);
//                                    pst.execute();
//                                    System.out.println("delete");
                                    UpdateTable();
                                    search_user();
                                } catch (Exception e) {
                                    JOptionPane.showMessageDialog(null, e);
                                }
                                }else{
                                    try {
                                        UpdateTable();
                                    } catch (SQLException ex) {
                                        Logger.getLogger(DoctorController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }

                            } else if (value.equals("SMS")) {
//                                System.out.println("Sms");
                                Doctor a = getTableView().getItems().get(getIndex());
                                n = a.getName();
                                i = String.valueOf(a.getId());
                                e = a.getEmail();
                                ad = a.getAddress();
                                no = a.getNumber();
                                b = a.getBranch();
                                t = "Admin";
                                Parent root;
                                try {
                                    root = FXMLLoader.load(getClass().getResource("DoctorAdd.fxml"));
                                    Stage mainStage = new Stage();
                                    Scene scene = new Scene(root);
                                    mainStage.setScene(scene);
                                    mainStage.show();
                                } catch (IOException ex) {
                                    Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } else if (value.equals("Shift")) {
//                                System.out.println("Shift");
                                Doctor a = getTableView().getItems().get(getIndex());
                                n = a.getName();
                                i = String.valueOf(a.getId());
                                e = a.getEmail();
                                ad = a.getAddress();
                                no = a.getNumber();
                                b = a.getBranch();
                                t = "Doctor";
                                Aid = a.getId();
                                shiftPane.setVisible(true);
                            }
//                            System.out.println(value);
                        });
                        setGraphic(comboBox);
                        setText(null);
                    }
                }
            };

            return cell;
        };

//        colCom.setStyle("-fx-background-color: green;");
        colCom.setCellFactory(cellco);

//        index = table_users.getSelectionModel().getSelectedIndex();
//        col_id.getCellData(index).toString();
//        if(LoginController.t.equals("receptionist")){
//            colCom.setVisible(false);
//        }
        add_btn.setOnAction(e -> {
            if (validateNumber() && validateEmail()) {
                Connection conn = mysqlconnect.connectDb();
                String sql = "select * from doctor";
                try {
                    Window owner = btnback.getScene().getWindow();
                    pst = conn.prepareStatement(sql);
                    ResultSet rs = pst.executeQuery();

                    while (rs.next()) {
                        if (rs.getString("email").equals(txt_email.getText())) {
                            showAlert(Alert.AlertType.CONFIRMATION, owner, "Alert",
                                    "Email already Exist");
                            return;
                        }
                    }
                    informationMessage.setVisible(true);

                } catch (Exception a) {
//                    System.out.println(a);
                }
            }
        });
        edit_btn.setOnAction(e -> {
            if (validateNumber1() && validateEmail1()) {
                try {

                    conn = mysqlconnect.connectDb();
                    String value2 = txt_name1.getText();
                    String value3 = txt_number1.getText();
                    String value4 = txt_branch1.getText();
                    String value5 = txt_email1.getText();
                    String value6 = txt_address1.getText();

                    String sql = "update doctor set Name='" + value2 + "', Number='" + value3 + "', Branch='" + value4 + "', Email='" + value5 + "',Address='" + value6 + "' where Doctorid=" + DoctorController.i;
//            System.out.println(sql);
                    pst = conn.prepareStatement(sql);
                    pst.execute();

                    Window owner = btnback.getScene().getWindow();
                    showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                            "Doctor is Edited");
                    editPane.setVisible(false);
                    UpdateTable();
                    search_user();

                } catch (Exception a) {
                    JOptionPane.showMessageDialog(null, a);
                }
            }
        });
    }

    private boolean validateNumber() {
        Pattern p = Pattern.compile("^\\d{10}$");
        Matcher m = p.matcher(txt_number.getText());
        if (m.find() && m.group().equals(txt_number.getText())) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate Number");
            alert.setHeaderText(null);
            alert.setContentText("Enter Valid Number");
            alert.showAndWait();
            return false;
        }
    }

    private boolean validateEmail() {
        Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9_]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        Matcher m = p.matcher(txt_email.getText());
        if (m.find() && m.group().equals(txt_email.getText())) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate Email");
            alert.setHeaderText(null);
            alert.setContentText("Enter Valid Email");
            alert.showAndWait();
            return false;
        }
    }

    private boolean validateNumber1() {
        Pattern p = Pattern.compile("^\\d{10}$");
        Matcher m = p.matcher(txt_number1.getText());
        if (m.find() && m.group().equals(txt_number1.getText())) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate Number");
            alert.setHeaderText(null);
            alert.setContentText("Enter Valid Number");
            alert.showAndWait();
            return false;
        }
    }

    private boolean validateEmail1() {
        Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9_]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        Matcher m = p.matcher(txt_email1.getText());
        if (m.find() && m.group().equals(txt_email1.getText())) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate Email");
            alert.setHeaderText(null);
            alert.setContentText("Enter Valid Email");
            alert.showAndWait();
            return false;
        }
    }

}
