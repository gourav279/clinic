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
import java.sql.SQLException;
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
import javafx.scene.control.Alert.AlertType;
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
public class AccountantController implements Initializable {

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
    private TextField txt_password;

    @FXML
    private TextArea txt_address;

    @FXML
    private TextField filterField;

    @FXML
    private TableView<Accountant> table_users;

    @FXML
    private TableColumn<Accountant, Integer> col_id;

    @FXML
    private TableColumn<Accountant, String> col_name;

    @FXML
    private TableColumn<Accountant, String> col_number;

    @FXML
    private TableColumn<Accountant, String> col_branch;

    @FXML
    private TableColumn<Accountant, String> col_email;

    @FXML
    private TableColumn<Accountant, String> colAddress;

    @FXML
    private TableColumn colCom;

    @FXML
    private Button btnback, add_btn;
    @FXML
    private Button edit_btn;

    @FXML
    void back(ActionEvent event) {
        btnback.getScene().getWindow().hide();
    }

    @FXML
    private AnchorPane addPane, editPane, messagePane;

    @FXML
    private Pane informationMessage;

    ObservableList<Accountant> listM;
    ObservableList<Accountant> dataList;
    int index = -1;
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    void getSelected(MouseEvent event) {
        index = table_users.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        txt_id.setText(col_id.getCellData(index).toString());
        txt_name.setText(col_name.getCellData(index).toString());
        txt_number.setText(col_number.getCellData(index).toString());
//        txt_branch.setText(col_branch.getCellData(index).toString());
        txt_email.setText(col_email.getCellData(index).toString());
        txt_address.setText(colAddress.getCellData(index).toString());
    }

    @FXML
    private Button add_btn_admin;

    @FXML
    void sendMeassage(ActionEvent event) {

//        sendSms.SendSms(messageDoc.getText(), num);
        messagePane.setVisible(false);
    }
    private TextArea messageTxt;
    private AnchorPane editid;

    @FXML
    void add(ActionEvent event) {
        informationMessage.setVisible(false);
        ObservableList<String> data = FXCollections.observableArrayList();
        String sql = "select * from branch";
        Connection conn = mysqlconnect.connectDb();
        try {
            pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                data.add(rs.getString("name"));
            }
            txt_branch.setItems(data);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        addPane.setVisible(true);
    }

    @FXML
    void addBack(ActionEvent event) {
        addPane.setVisible(false);
        editPane.setVisible(false);
        messagePane.setVisible(false);
        shiftPane.setVisible(false);
        UpdateTable();
    }

    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();//To change body of generated methods, choose Tools | Templates.
    }

    void addAdmin(ActionEvent event) {

    }
    @FXML
    private CheckBox mail, sms;
//    @FXML
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
//            String sql = "update accountant set Name='" + value2 + "', Number='" + value3 + "', Branch='" + value4 + "', Email='" + value5 + "',Address='" + value6 + "' where Accountantid=" + AccountantController.i;
//            
//            pst = conn.prepareStatement(sql);
//            pst.execute();
//
//            UpdateTable();
//            editPane.setVisible(false);
//
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e);
//        }
//    }
    @FXML
    void addMember(ActionEvent event) {
        Connection conn = mysqlconnect.connectDb();
        String sql = "insert into accountant (Name,Number,Branch,Email,Password,Address)values(?,?,?,?,?,?)";
        try {

            pst = conn.prepareStatement(sql);
            pst.setString(1, txt_name.getText());
            pst.setString(2, txt_number.getText());
            pst.setString(3, String.valueOf(txt_branch.getValue()));
            pst.setString(4, txt_email.getText());
            pst.setString(5, txt_password.getText());
            pst.setString(6, txt_address.getText());
            pst.execute();
            String message = "Name=" + txt_name.getText() + "/nNumber=" + txt_number;
            if (mail.isSelected()) {
                String sql1 = String.format("select * from accountant where name='%s' and password='%s'", LoginController.u, LoginController.p);
                pst = conn.prepareStatement(sql1);
                rs = pst.executeQuery();
                String b;
                if (rs.next()) {
                    b = rs.getString("branch");
                    sql1 = String.format("Select email from accountant where branch='%s'", b);
                    pst = conn.prepareStatement(sql1);
                    rs = pst.executeQuery();

                    if (rs.next()) {
                        javaMail.sendEmail(message, rs.getString("email"));

                        while (rs.next()) {
                            javaMail.sendEmail(message, rs.getString("email"));

                        }
                    }
                }
            }
            if (sms.isSelected()) {
                String sql1 = String.format("select * from accountant where name='%s' and password='%s'", LoginController.u, LoginController.p);
                pst = conn.prepareStatement(sql1);
                rs = pst.executeQuery();
                String b;
                if (rs.next()) {
                    b = rs.getString("branch");
                    sql1 = String.format("Select number from accountant where branch='%s'", b);
                    pst = conn.prepareStatement(sql1);
                    rs = pst.executeQuery();

                    if (rs.next()) {
                        sendSms.SendSms(message, rs.getString("number"));

                        while (rs.next()) {
                            sendSms.SendSms(message, rs.getString("number"));

                        }
                    }
                }
            }
            UpdateTable();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        txt_name.clear();
        txt_address.clear();
        txt_password.clear();
        txt_email.clear();
        txt_number.clear();
        txt_branch.getSelectionModel().clearSelection();
        addPane.setVisible(false);
        editPane.setVisible(false);
        messagePane.setVisible(false);
    }

    void messageSend(ActionEvent event) {
        sendSms.SendSms(messageTxt.getText(), AccountantController.no);

    }

    public void UpdateTable() {
        col_id.setCellValueFactory(new PropertyValueFactory<Accountant, Integer>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<Accountant, String>("name"));
        col_number.setCellValueFactory(new PropertyValueFactory<Accountant, String>("number"));
        col_branch.setCellValueFactory(new PropertyValueFactory<Accountant, String>("branch"));
        col_email.setCellValueFactory(new PropertyValueFactory<Accountant, String>("email"));
        colAddress.setCellValueFactory(new PropertyValueFactory<Accountant, String>("Address"));
        Shift.setCellValueFactory(new PropertyValueFactory<Accountant, String>("Shift"));
        listM = mysqlconnect.getAccountant();
        table_users.setItems(listM);
    }

    @FXML
    private TableColumn<Accountant, String> Shift;

    void search_user() {
        col_id.setCellValueFactory(new PropertyValueFactory<Accountant, Integer>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<Accountant, String>("name"));
        col_number.setCellValueFactory(new PropertyValueFactory<Accountant, String>("number"));
        col_branch.setCellValueFactory(new PropertyValueFactory<Accountant, String>("branch"));
        col_email.setCellValueFactory(new PropertyValueFactory<Accountant, String>("email"));
        colAddress.setCellValueFactory(new PropertyValueFactory<Accountant, String>("Address"));
        dataList = mysqlconnect.getAccountant();
        table_users.setItems(dataList);
        FilteredList<Accountant> filteredData = new FilteredList<>(dataList, b -> true);
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
                } else if (person.getBranch().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches password
                } else if (String.valueOf(person.getAddress()).indexOf(lowerCaseFilter) != -1) {
                    return true;// Filter matches email
                } else {
                    return false; // Does not match.
                }
            });
        });
        SortedList<Accountant> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table_users.comparatorProperty());
        table_users.setItems(sortedData);
    }

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
            String sql = "update accountant set Acountantid='" + value1 + "', Name='" + value2 + "', Number='" + value3 + "', Branch='" + value4 + "', Email='" + value5 + "',Address='" + value6 + "' where Accountantid=" + AccountantController.i + "";
            pst = conn.prepareStatement(sql);
            pst.execute();
//                                JOptionPane.showMessageDialog(null, "Update");
            UpdateTable();
            search_user();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    static String num;
    static String i, n, e, ad, no, t, b, p, value;
    @FXML
    private TextField txt_name1;

    @FXML
    private TextField txt_number1;

    @FXML
    private TextField txt_email1;

    @FXML
    private TextField txt_branch1;

    @FXML
    private TextArea txt_address1;

    @FXML
    private AnchorPane shiftPane;

    @FXML
    void shiftMethod(ActionEvent event) throws SQLException {
        try {
            Connection con = mysqlconnect.connectDb();
            String t = enterTime.getValue().toString() + "-" + goingTime.getValue().toString();
            String sql = "update accountant set shift"
                    + "='" + t + "' where Accountantid=" + Aid;
            PreparedStatement pst = con.prepareStatement(sql);

//        pst.setString(1,t);
            pst.execute();
            shiftPane.setVisible(false);
        } catch (Exception e) {

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
        UpdateTable();
        search_user();
//        
        mail.setSelected(true);
        sms.setSelected(true);
        Callback<TableColumn<Accountant, String>, TableCell<Accountant, String>> cellco = (params) -> {

            final TableCell<Accountant, String> cell = new TableCell<Accountant, String>() {
                //override updatesItem method
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                                     setText(null);
       //ensure that cell is creates only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                    } else {
                        ComboBox comboBox = new ComboBox();
                        comboBox.setPromptText("Action Act");
                        comboBox.getItems().add("Edit");
                        comboBox.getItems().add("Delete");
                        comboBox.getItems().add("SMS");
                        if (LoginController.t.equals("superadmin") || LoginController.t.equals("admin")) {
                            comboBox.getItems().add("Shift");
                        }
                        comboBox.setOnAction(event -> {
                            value = (String) comboBox.getValue();

                            if (value.equals("Edit")) {
                                Accountant a = getTableView().getItems().get(getIndex());
                                n = a.getName();
                                i = String.valueOf(a.getId());
                                e = a.getEmail();
                                ad = a.getAddress();
                                no = a.getNumber();
                                b = a.getBranch();
//                            p=a.get
                                t = "Accountant";
//                            meassage_panel.setVisible(false);
//                            editid.setVisible(true);
                                txt_name1.setText(AccountantController.n);
                                txt_number1.setText(AccountantController.no);
                                txt_email1.setText(AccountantController.e);
                                txt_address1.setText(AccountantController.ad);
                                txt_branch1.setText(AccountantController.b);

                                editPane.setVisible(true);
//                                
                            } else if (value.equals("Delete")) {
                                Alert alert=new Alert(AlertType.CONFIRMATION);
                                alert.setTitle("Confirmation Dialog");
                                alert.setHeaderText(null);
                                alert.setContentText("Are you sure want to delete");
                                Optional <ButtonType> action= alert.showAndWait();
                                if(action.get()== ButtonType.OK){
                                    conn = mysqlconnect.connectDb();
                                Accountant a = getTableView().getItems().get(getIndex());
                                String sql = String.format("delete from accountant where Accountantid = %d", a.getId());
                                try {
//                                    JOptionPane.showMessageDialog(null, "Delete");
                                    pst = conn.prepareStatement(sql);
//                                    pst.setString(1, txt_id.getText());
                                    pst.execute();

                                    UpdateTable();
                                    search_user();
                                } catch (Exception e) {
                                    JOptionPane.showMessageDialog(null, e);
                                }
                                }else{
                                    UpdateTable();
                                }
                                
                            } else if (value.equals("SMS")) {

                                Accountant a = getTableView().getItems().get(getIndex());
                                n = a.getName();
                                i = String.valueOf(a.getId());
                                e = a.getEmail();
                                ad = a.getAddress();
                                no = a.getNumber();
                                b = a.getBranch();
//                            p=a.get
                                t = "Accountant";
//                            meassage_panel.setVisible(false);
//                            editid.setVisible(true);
                                messagePane.setVisible(true);

                            } else if (value.equals("Shift")) {

                                Accountant a = getTableView().getItems().get(getIndex());
                                n = a.getName();
                                i = String.valueOf(a.getId());
                                e = a.getEmail();
                                ad = a.getAddress();
                                no = a.getNumber();
                                b = a.getBranch();
                                t = "Accountant";
                                Aid = a.getId();
                                shiftPane.setVisible(true);
                            }

                        });
                        setGraphic(comboBox);
                        setText(null);
                    }
                }
            };

            return cell;
        };

        colCom.setCellFactory(cellco);
        add_btn.setOnAction(e -> {
            if (validateNumber() && validateEmail()) {
                Connection conn = mysqlconnect.connectDb();
                String sql = "select * from accountant";
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

                    String sql = "update accountant set Name='" + value2 + "', Number='" + value3 + "', Branch='" + value4 + "', Email='" + value5 + "',Address='" + value6 + "' where Accountantid=" + AccountantController.i;

                    pst = conn.prepareStatement(sql);
                    pst.execute();

                    UpdateTable();
                    editPane.setVisible(false);

                } catch (Exception a) {
                    JOptionPane.showMessageDialog(null, e);
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
