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
public class TreatmentController implements Initializable {

    @FXML
    private TextField txt_name;

    @FXML
    private TextField txt_price;

    @FXML
    private TextField txt_branch;

    @FXML
    private TextField txt_category,txt_name1,txt_number1,txt_email1;

    @FXML
    private TextField txt_id,txt_password;

    @FXML
    private TextArea txt_address2;

    @FXML
    private TextField filterField;

    @FXML
    private TableView<Treatment> table_users;

    @FXML
    private TableColumn<Treatment, Integer> col_id;

    @FXML
    private TableColumn<Treatment, String> col_name;

    @FXML
    private TableColumn<Treatment, String> col_price;

    @FXML
    private TableColumn<Treatment, String> col_Category;

    @FXML
    private TableColumn<Treatment, String> colBranch;

    @FXML
    private TableColumn colAction;
    
    @FXML
    void addAdmin(ActionEvent event) {
         try {

            conn = mysqlconnect.connectDb();
            String value2 = txt_name1.getText();
            String value3 = txt_number1.getText();
            
            String value5 = txt_email1.getText();
            String value6 = txt_address2.getText();

            String sql = "update treatment set Name='" + value2 + "', Price='" + value3 + "', Category='" + value5 + "',Branch='" + value6 + "' where Treatmentid=" + TreatmentController.i + "";
//            System.out.println(sql);
            pst = conn.prepareStatement(sql);
            pst.execute();
            editPane.setVisible(false);
            UpdateTable();
            Window owner = btnback.getScene().getWindow();
            showAlert(Alert.AlertType.WARNING, owner, "Succesfull!",
                    "Treatment is edited");
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
        String sql = "insert into treatment (Name,Price,Category,Branch)values(?,?,?,?)";
        try {
//            System.out.println(txt_name.getText() + " ");
            pst = conn.prepareStatement(sql);
            pst.setString(1, txt_name.getText());
            pst.setString(2, txt_price.getText());
            
            pst.setString(3, txt_category.getText());
            pst.setString(4, txt_branch.getText());
            pst.execute();
//            String message = "Name=" + txt_name.getText() + "/nNumber=" + txt_number;
//            if (mail.isSelected()) {
//                String sql1 = String.format("select * from treatment where name='%s' and password='%s'");
//                pst = conn.prepareStatement(sql1);
//                rs = pst.executeQuery();
//                String b;
//                if (rs.next()) {
//                    b = rs.getString("treatment");
//                    sql1 = String.format("Select email from treatment ");
//                    pst = conn.prepareStatement(sql1);
//                    rs = pst.executeQuery();
//
//                    if (rs.next()) {
//                        javaMail.sendEmail(message, rs.getString("email"));
//                        System.out.println(rs.getString("email"));
//                        while (rs.next()) {
//                            javaMail.sendEmail(message, rs.getString("email"));
//                            System.out.println(rs.getString("email"));
//                        }
//                    }
//                }
//            }
//            if (sms.isSelected()) {
//                String sql1 = String.format("select * from treatment");
//                pst = conn.prepareStatement(sql1);
//                rs = pst.executeQuery();
//                String b;
//                if (rs.next()) {
//                    b = rs.getString("treatment");
//                    sql1 = String.format("Select number from treatment");
//                    pst = conn.prepareStatement(sql1);
//                    rs = pst.executeQuery();
//
//                    if (rs.next()) {
//                        sendSms.SendSms(message, rs.getString("number"));
//                        System.out.println(rs.getString("number"));
//                        while (rs.next()) {
//                            sendSms.SendSms(message, rs.getString("number"));
//                            System.out.println(rs.getString("email"));
//                        }
//                    }
//                }
//            }
            UpdateTable();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        txt_name.clear();
        txt_branch.clear();
        txt_category.clear();
        txt_price.clear();
        
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
    private TextArea messageDoc;
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
        String sql = "select * from treatment";
        try {
            Window owner = btnback.getScene().getWindow();
            pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                if (rs.getString("email").equals(txt_category.getText())) {
                    showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
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
    private Label name_doc;

    @FXML
    private Label number_doc;
    @FXML
    void sendMeassage(ActionEvent event) {
//        System.out.println(messageDoc.getText());
//        sendSms.SendSms(messageDoc.getText(), num);
        meassage_panel.setVisible(false);
    }
    
    ObservableList<Treatment> listM;
    ObservableList<Treatment> dataList;
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
        txt_price.setText(col_price.getCellData(index).toString());
        txt_category.setText(col_Category.getCellData(index).toString());
        txt_branch.setText(colBranch.getCellData(index).toString());
    }
   
    @FXML
    private Button add_btn_branch;
    
    @FXML
    void add(ActionEvent event) {
         addPane.setVisible(true);
         
    }


    public void UpdateTable() {
        col_id.setCellValueFactory(new PropertyValueFactory<Treatment, Integer>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<Treatment, String>("name"));
        col_price.setCellValueFactory(new PropertyValueFactory<Treatment, String>("price"));
        col_Category.setCellValueFactory(new PropertyValueFactory<Treatment, String>("category"));
        colBranch.setCellValueFactory(new PropertyValueFactory<Treatment, String>("branch"));
        listM = mysqlconnect.getTreatment();
        table_users.setItems(listM);
    }

    @FXML
    void search_user() {
        col_id.setCellValueFactory(new PropertyValueFactory<Treatment, Integer>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<Treatment, String>("name"));
        col_price.setCellValueFactory(new PropertyValueFactory<Treatment, String>("price"));
        col_Category.setCellValueFactory(new PropertyValueFactory<Treatment, String>("category"));
        colBranch.setCellValueFactory(new PropertyValueFactory<Treatment, String>("branch"));
        dataList = mysqlconnect.getTreatment();
        table_users.setItems(dataList);
        FilteredList<Treatment> filteredData = new FilteredList<>(dataList, b -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (person.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches username
                } else if (person.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches password
                } else if (person.getPrice().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches password
                }else if (person.getCategory().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches password
                } else if (String.valueOf(person.getBranch()).indexOf(lowerCaseFilter) != -1) {
                    return true;// Filter matches email
                } else {
                    return false; // Does not match.
                }
            });
        });
        SortedList<Treatment> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table_users.comparatorProperty());
        table_users.setItems(sortedData);
    }

    @FXML
    private AnchorPane editid;
    
    
    static String i, n, e, ad, no, t, b,value;

    @FXML
    void clickSubmit(ActionEvent event) {
        editid.setVisible(false);
                                    try {
                                
                                conn = mysqlconnect.connectDb();
                                String value1 = txt_id.getText();
                                String value2 = txt_name.getText();
                                String value3 = txt_price.getText();
                                String value5 = txt_category.getText();
                                String value6 = txt_branch.getText();
                                String sql = "update treatment set Treatmentid='" + value1 + "', Name='" + value2 + "', Price='" + value3 + "', Category='" + value5 + "',Branch='" + value6 + "' where Treatmentid=" + TreatmentController.i + "";
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
        UpdateTable();
        search_user();
//        Shadow s=new Shadow();
//        Glow glow = new Glow();
//        glow.setLevel(0.9);
//        table_users.setEffect(glow);
        Callback<TableColumn<Treatment, String>, TableCell<Treatment, String>> cellco = (params) -> {

            final TableCell<Treatment, String> cell = new TableCell<Treatment, String>() {
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

                        comboBox.setOnAction(event -> {
                            value = (String) comboBox.getValue();
                            if (value.equals("Edit")) {
                                Treatment a = getTableView().getItems().get(getIndex());
                                n = a.getName();
                                i = String.valueOf(a.getId());
                                e = a.getPrice();
                                ad = a.getCategory();
                                no = a.getBranch();
//                                b = a.getBranch();
//                                System.out.println(ad);
                                txt_name1.setText(n);
                                txt_email1.setText(ad);
                                txt_number1.setText(e);
                                txt_address2.setText(no);
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
                                Treatment a = getTableView().getItems().get(getIndex());
                                String sql = String.format("delete from treatment where Treatmentid = %d", a.getId());
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
                                Treatment a = getTableView().getItems().get(getIndex());
                                n = a.getName();
                                i = String.valueOf(a.getId());
                                e = a.getCategory();
                                ad = a.getBranch();
                                no = a.getPrice();
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
        
        colAction.setCellFactory(cellco);
        }

    }
