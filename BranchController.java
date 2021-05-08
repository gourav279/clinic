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
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import javafx.scene.effect.Glow;
import javafx.scene.effect.Shadow;
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
public class BranchController implements Initializable {

    @FXML
    private TextField txt_name;

    @FXML
    private TextField txt_number;


    @FXML
    private TextField txt_email,txt_name1,txt_number1,txt_email1;

    private TextField txt_id;
    @FXML
    private TextField txt_password;

    @FXML
    private TextArea txt_address,txt_address2;

    @FXML
    private TextField filterField;

    @FXML
    private TableView<Branch> table_users;

    @FXML
    private TableColumn<Branch, Integer> col_id;

    @FXML
    private TableColumn<Branch, String> col_name;

    @FXML
    private TableColumn<Branch, String> col_number;

    @FXML
    private TableColumn<Branch, String> col_email;

    @FXML
    private TableColumn<Branch, String> colAddress;

    @FXML
    private TableColumn colCom;
    @FXML
    private Button add_btn;
    @FXML
    private TextArea txt_address11;
    
    @FXML
    void addAdmin(ActionEvent event) {
         try {

            conn = mysqlconnect.connectDb();
            String value2 = txt_name1.getText();
            String value3 = txt_number1.getText();
            
            String value5 = txt_email1.getText();
            String value6 = txt_address2.getText();

            String sql = "update branch set Name='" + value2 + "', Number='" + value3 + "', Email='" + value5 + "',Address='" + value6 + "' where Branchid=" + BranchController.i + "";
//            System.out.println(sql);
            pst = conn.prepareStatement(sql);
            pst.execute();
            editPane.setVisible(false);
            UpdateTable();
            Window owner = btnback.getScene().getWindow();
            showAlert(Alert.AlertType.CONFIRMATION, owner, "Successfull!",
                    "Branch is edited");
            return;
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
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
    void addBack(ActionEvent event) {
        editPane.setVisible(false);
        addPane.setVisible(false);
        meassage_panel.setVisible(false);
        UpdateTable();
    }

    @FXML
    void addMember(ActionEvent event) {
        Connection conn = mysqlconnect.connectDb();
        String sql = "insert into branch (Name,Number,Email,Password,Address)values(?,?,?,?,?)";
        try {
//            System.out.println(txt_name.getText() + " ");
            pst = conn.prepareStatement(sql);
            pst.setString(1, txt_name.getText());
            pst.setString(2, txt_number.getText());
            
            pst.setString(3, txt_email.getText());
            pst.setString(4, txt_password.getText());
            pst.setString(5, txt_address.getText());
            pst.execute();
            String message = "Name=" + txt_name.getText() + "/nNumber=" + txt_number;
            if (mail.isSelected()) {
                String sql1 = String.format("select * from branch where name='%s' and password='%s'");
                pst = conn.prepareStatement(sql1);
                rs = pst.executeQuery();
                String b;
                if (rs.next()) {
                    b = rs.getString("branch");
                    sql1 = String.format("Select email from branch ");
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
                String sql1 = String.format("select * from branch");
                pst = conn.prepareStatement(sql1);
                rs = pst.executeQuery();
                String b;
                if (rs.next()) {
                    b = rs.getString("branch");
                    sql1 = String.format("Select number from brach");
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
            UpdateTable();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        txt_name.clear();
        txt_address.clear();
        txt_password.clear();
        txt_email.clear();
        txt_number.clear();
        
        addPane.setVisible(false);
        editPane.setVisible(false);
        meassage_panel.setVisible(false);
    }
    @FXML
    private CheckBox mail;

    @FXML
    private CheckBox sms;
    static String num;
    @FXML
    private AnchorPane meassage_panel;
    
     @FXML
    private Button btnback;

    @FXML
    void back(ActionEvent event) {
        btnback.getScene().getWindow().hide();
    }
    @FXML
    void CheckEmail(ActionEvent event) {
        Connection conn = mysqlconnect.connectDb();
        String sql = "select * from branch";
        try {
            Window owner = btnback.getScene().getWindow();
            pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                if (rs.getString("email").equals(txt_email.getText())) {
                    showAlert(Alert.AlertType.CONFIRMATION, owner, "Succesfull!",
                            "Email already Exisit");
                    return;
                }
            }
            informationMessage.setVisible(true);

        } catch (Exception e) {
//            System.out.println(e);
        }
    }
    
    @FXML
    private Pane informationMessage;
    @FXML
    void sendMeassage(ActionEvent event) {
//        System.out.println(messageDoc.getText());
//        sendSms.SendSms(messageDoc.getText(), num);
        meassage_panel.setVisible(false);
    }
    
    ObservableList<Branch> listM;
    ObservableList<Branch> dataList;
    int index = -1;
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;


    @FXML
    void getSelected(MouseEvent event) {
        index = table_users.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        txt_id.setText(col_id.getCellData(index).toString());
        txt_name.setText(col_name.getCellData(index).toString());
        txt_number.setText(col_number.getCellData(index).toString());
        txt_email.setText(col_email.getCellData(index).toString());
        txt_address.setText(colAddress.getCellData(index).toString());
    }
   
    @FXML
    private Button add_btn_branch;
    
    @FXML
    void add(ActionEvent event) {
         addPane.setVisible(true);
         
    }


    public void UpdateTable() {
        col_id.setCellValueFactory(new PropertyValueFactory<Branch, Integer>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<Branch, String>("name"));
        col_number.setCellValueFactory(new PropertyValueFactory<Branch, String>("number"));
        col_email.setCellValueFactory(new PropertyValueFactory<Branch, String>("email"));
        colAddress.setCellValueFactory(new PropertyValueFactory<Branch, String>("Address"));
        listM = mysqlconnect.getbranch();
        table_users.setItems(listM);
    }

    void search_user() {
        col_id.setCellValueFactory(new PropertyValueFactory<Branch, Integer>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<Branch, String>("name"));
        col_number.setCellValueFactory(new PropertyValueFactory<Branch, String>("number"));
        col_email.setCellValueFactory(new PropertyValueFactory<Branch, String>("email"));
        colAddress.setCellValueFactory(new PropertyValueFactory<Branch, String>("Address"));
        dataList = mysqlconnect.getbranch();
        table_users.setItems(dataList);
        FilteredList<Branch> filteredData = new FilteredList<>(dataList, b -> true);
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
                } else {
                    return false; // Does not match.
                }
            });
        });
        SortedList<Branch> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table_users.comparatorProperty());
        table_users.setItems(sortedData);
    }

    private AnchorPane editid;
    
    
    static String i, n, e, ad, no, t, b,value;

    void clickSubmit(ActionEvent event) {
        editid.setVisible(false);
                                    try {
                                
                                conn = mysqlconnect.connectDb();
                                String value1 = txt_id.getText();
                                String value2 = txt_name.getText();
                                String value3 = txt_number.getText();
                                String value5 = txt_email.getText();
                                String value6 = txt_address.getText();
                                String sql = "update branch set Branchid='" + value1 + "', Name='" + value2 + "', Number='" + value3 + "', Email='" + value5 + "',Address='" + value6 + "' where Branchid=" + BranchController.i + "";
                                pst = conn.prepareStatement(sql);
                                pst.execute();
//                                JOptionPane.showMessageDialog(null, "Update");
                                UpdateTable();
                                search_user();
                            } catch (Exception e) {
                                JOptionPane.showMessageDialog(null, e);
                            }
    }
    
    @FXML
    private AnchorPane addPane;
    @FXML
    private AnchorPane editPane;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mail.setSelected(true);
        sms.setSelected(true);
        UpdateTable();
        search_user();
//        Shadow s=new Shadow();
//        Glow glow = new Glow();
//        glow.setLevel(0.9);
//        table_users.setEffect(glow);
        Callback<TableColumn<Branch, String>, TableCell<Branch, String>> cellco = (params) -> {

            final TableCell<Branch, String> cell = new TableCell<Branch, String>() {
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

                        comboBox.setOnAction(event -> {
                            value = (String) comboBox.getValue();
                            if (value.equals("Edit")) {
                                Branch a = getTableView().getItems().get(getIndex());
                                n = a.getName();
                                i = String.valueOf(a.getId());
                                e = a.getEmail();
                                ad = a.getAddress();
                                no = a.getNumber();
//                                b = a.getBranch();
//                                System.out.println(ad);
                                txt_name1.setText(n);
                                txt_email1.setText(e);
                                txt_number1.setText(no);
                                txt_address2.setText(ad);
                                editPane.setVisible(true);
                                
//                                
                            } else if (value.equals("Delete")) {
                                Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Confirmation Dialog");
                                alert.setHeaderText(null);
                                alert.setContentText("Are you sure want to delete");
                                Optional <ButtonType> action= alert.showAndWait();
                                if(action.get()== ButtonType.OK){
                                conn = mysqlconnect.connectDb();
                                Branch a = getTableView().getItems().get(getIndex());
                                String sql = String.format("delete from branch where branchid = %d", a.getId());
                                try {
//                                    JOptionPane.showMessageDialog(null, "Delete");
                                    pst = conn.prepareStatement(sql);
//                                    pst.setString(1, txt_id.getText());
                                    pst.execute();
//                                    System.out.println("delete");
                                    UpdateTable();
                                    search_user();
                                } catch (Exception e) {
                                    JOptionPane.showMessageDialog(null, e);
                                }
                                }else{
                                    UpdateTable();
                                }
                            } else if (value.equals("SMS")) {
//                                System.out.println("Sms");
                                Branch a = getTableView().getItems().get(getIndex());
                                n = a.getName();
                                i = String.valueOf(a.getId());
                                e = a.getEmail();
                                ad = a.getAddress();
                                no = a.getNumber();
                                meassage_panel.setVisible(true);
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
        
        colCom.setCellFactory(cellco);
        add_btn.setOnAction(e -> {
            if (validateNumber() && validateEmail()) {
                Connection conn = mysqlconnect.connectDb();
        String sql = "select * from branch";
        try {
            Window owner = btnback.getScene().getWindow();
            pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                if (rs.getString("email").equals(txt_email.getText())) {
                    showAlert(Alert.AlertType.CONFIRMATION, owner, "Succesfull!",
                            "Email already Exisit");
                    return;
                }
            }
            informationMessage.setVisible(true);

        } catch (Exception a) {
//            System.out.println(a);
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
    
}