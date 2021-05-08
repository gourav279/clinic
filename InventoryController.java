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
public class InventoryController implements Initializable {

    @FXML
    private TextField txt_name;

    @FXML
    private TextField txt_brand;

    @FXML
    private TextField txt_price;
    private TextField txt_category;
    @FXML
    private TextField txt_name1;


    private TextField txt_id;

    private TextArea txt_address2;

    @FXML
    private TextField filterField;

    @FXML
    private TableView<Inventory> table_users;

    @FXML
    private TableColumn<Inventory, Integer> col_id;

    @FXML
    private TableColumn<Inventory, String> col_name;

    @FXML
    private TableColumn<Inventory, String> col_brand;

    @FXML
    private TableColumn<Inventory, String> col_price;

    @FXML
    private TableColumn<Inventory, String> colDistributer;
    
    @FXML
    private TableColumn<Inventory, String> colQTY;
            
    @FXML
    private TableColumn colAction;
    @FXML
    private Button add_btn;
    @FXML
    private TextArea txt_address11;
    @FXML
    private TextField txt_qty;
    @FXML
    private TextField txt_distributer;
    @FXML
    private TextField txt_brand1;
    @FXML
    private TextField txt_price1;
    @FXML
    private TextField txt_distributer1;
    @FXML
    private TextField txt_qty1;
  
    
    @FXML
    void addAdmin(ActionEvent event) {
         try {

            conn = mysqlconnect.connectDb();
            String value1 = txt_name1.getText();
            String value2 = txt_brand1.getText();
            String value3 = txt_price1.getText();
            String value4 = txt_distributer1.getText();
            String value5 = txt_qty1.getText();

            String sql = "update inventory set Name='" + value1 + "', Brand='" + value2 + "', Price='" + value3 + "',Distributer='" + value4 + "',QTY='" + value5 + "' where Inventoryid=" + InventoryController.i + "";
//            System.out.println(sql);
            pst = conn.prepareStatement(sql);
            pst.execute();
            editPane.setVisible(false);
            UpdateTable();
            Window owner = btnback.getScene().getWindow();
            showAlert(Alert.AlertType.WARNING, owner, "Succesfull!",
                    "Inventory is edited");
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
        String sql = "insert into inventory (Name,Brand,Price,Distributer,QTY)values(?,?,?,?,?)";
        try {
//            System.out.println(txt_name.getText() + " ");
            pst = conn.prepareStatement(sql);
            pst.setString(1, txt_name.getText());
            pst.setString(2, txt_brand.getText());
            
            pst.setString(3, txt_price.getText());
            pst.setString(4, txt_distributer.getText());
            pst.setString(5, txt_qty.getText());
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
        txt_brand.clear();
        txt_price.clear();
        txt_distributer.clear();
        txt_qty.clear();
        
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
    void CheckEmail(ActionEvent event) {
        Connection conn = mysqlconnect.connectDb();
        String sql = "select * from inventory";
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
    void sendMeassage(ActionEvent event) {
//        System.out.println(messageDoc.getText());
//        sendSms.SendSms(messageDoc.getText(), num);
        meassage_panel.setVisible(false);
    }
    
    ObservableList<Inventory> listM;
    ObservableList<Inventory> dataList;
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
        txt_brand.setText(col_brand.getCellData(index).toString());
        txt_price.setText(col_price.getCellData(index).toString());
        txt_distributer.setText(colDistributer.getCellData(index).toString());
        txt_qty.setText(colQTY.getCellData(index).toString());
    }
   
    @FXML
    private Button add_btn_branch;
    
    @FXML
    void add(ActionEvent event) {
         addPane.setVisible(true);
         
    }


    public void UpdateTable() {
        col_id.setCellValueFactory(new PropertyValueFactory<Inventory, Integer>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<Inventory, String>("name"));
        col_brand.setCellValueFactory(new PropertyValueFactory<Inventory, String>("brand"));
        col_price.setCellValueFactory(new PropertyValueFactory<Inventory, String>("price"));
        colDistributer.setCellValueFactory(new PropertyValueFactory<Inventory, String>("distributer"));
        colQTY.setCellValueFactory(new PropertyValueFactory<Inventory, String>("qty"));
        listM = mysqlconnect.getInventory();
        table_users.setItems(listM);
    }

    void search_user() {
        col_id.setCellValueFactory(new PropertyValueFactory<Inventory, Integer>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<Inventory, String>("name"));
        col_brand.setCellValueFactory(new PropertyValueFactory<Inventory, String>("brand"));
        col_price.setCellValueFactory(new PropertyValueFactory<Inventory, String>("price"));
        colDistributer.setCellValueFactory(new PropertyValueFactory<Inventory, String>("distributer"));
        colQTY.setCellValueFactory(new PropertyValueFactory<Inventory, String>("qty"));
        dataList = mysqlconnect.getInventory();
        table_users.setItems(dataList);
        FilteredList<Inventory> filteredData = new FilteredList<>(dataList, b -> true);
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
                } else if (person.getBrand().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches password
                }else if (person.getPrice().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches password
                } else if (String.valueOf(person.getDistributer()).indexOf(lowerCaseFilter) != -1) {
                    return true;// Filter matches email
                } else {
                    return false; // Does not match.
                }
            });
        });
        SortedList<Inventory> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table_users.comparatorProperty());
        table_users.setItems(sortedData);
    }

    private AnchorPane editid;
    
    
    static String i, n, e, ad, no, t, b,value,q;

    void clickSubmit(ActionEvent event) {
        editid.setVisible(false);
                                    try {
                                
                                conn = mysqlconnect.connectDb();
                                String value1 = txt_id.getText();
                                String value2 = txt_name.getText();
                                String value3 = txt_brand.getText();
                                String value5 = txt_price.getText();
                                String value6 = txt_distributer.getText();
                                String value7 = txt_qty.getText();
                                String sql = "update inventory set Inventoryid='" + value1 + "', Name='" + value2 + "', Brand='" + value3 + "', Price='" + value5 + "',Distributer='" + value6 + "',QTY='" + value7 + "' where Inventoryid=" + InventoryController.i + "";
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
        Callback<TableColumn<Inventory, String>, TableCell<Inventory, String>> cellco = (params) -> {

            final TableCell<Inventory, String> cell = new TableCell<Inventory, String>() {
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
             
                                Inventory a = getTableView().getItems().get(getIndex());
                                n = a.getName();
                                i = String.valueOf(a.getId());
                                e = a.getBrand();
                                ad = a.getPrice();
                                no = a.getDistributer();
                                q = a.getQty();
//                                b = a.getBranch();
//                                System.out.println(n);
                                txt_name1.setText(n);
                                txt_brand1.setText(e);
                                txt_price1.setText(ad);
                                txt_distributer1.setText(no);
                                txt_qty1.setText(q);
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
                                Inventory a = getTableView().getItems().get(getIndex());
                                String sql = String.format("delete from inventory where Inventoryid = %d", a.getId());
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
                                Inventory a = getTableView().getItems().get(getIndex());
                                n = a.getName();
                                i = String.valueOf(a.getId());
                                e = a.getPrice();
                                ad = a.getDistributer();
                                no = a.getBrand();
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
