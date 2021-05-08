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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author doagu
 */
public class MedicineController implements Initializable {

    @FXML
    private AnchorPane editid;
    @FXML
    private TextField txt_name;

    @FXML
    private TextField txt_mg;

    @FXML
    private TextField txt_brand;

    @FXML
    private TextField txt_id;

    @FXML
    private TextField txt_distributer;
    
    @FXML
    private AnchorPane message_panel;
    @FXML
    private TextField filterField;
    @FXML
    private TableView<Medicine> table_users;
    @FXML
    private TableColumn<Medicine, Integer> col_id;
    @FXML
    private TableColumn<Medicine, String> col_Name;
    @FXML
    private TableColumn<Medicine, String> col_Brand;
    @FXML
    private TableColumn<Medicine, String> col_Mg;
    @FXML
    private TableColumn<Medicine, String> col_Distributer;
    @FXML
    private TableColumn<Medicine, String> colCom;
    
     @FXML
    private Button btnback;

    @FXML
    void back(ActionEvent event) {
        btnback.getScene().getWindow().hide();
    }
    
    @FXML
    private Button add_btn_medicine;

    ObservableList<Medicine> listM;
    ObservableList<Medicine> dataList;
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
        txt_name.setText(col_Name.getCellData(index).toString());
        txt_mg.setText(col_Mg.getCellData(index).toString());
        txt_brand.setText(col_Brand.getCellData(index).toString());
        txt_distributer.setText(col_Distributer.getCellData(index).toString());
    }
    
    public void UpdateTable() {
        col_id.setCellValueFactory(new PropertyValueFactory<Medicine, Integer>("id"));
        col_Name.setCellValueFactory(new PropertyValueFactory<Medicine, String>("name"));
        col_Brand.setCellValueFactory(new PropertyValueFactory<Medicine, String>("brand"));
        col_Mg.setCellValueFactory(new PropertyValueFactory<Medicine, String>("mg"));
        col_Distributer.setCellValueFactory(new PropertyValueFactory<Medicine, String>("distributer"));
        listM = mysqlconnect.getMedicine();
        table_users.setItems(listM);
    }

    @FXML
    void search_user() {
        col_id.setCellValueFactory(new PropertyValueFactory<Medicine, Integer>("id"));
        col_Name.setCellValueFactory(new PropertyValueFactory<Medicine, String>("name"));
        col_Brand.setCellValueFactory(new PropertyValueFactory<Medicine, String>("brand"));
        col_Mg.setCellValueFactory(new PropertyValueFactory<Medicine, String>("mg"));
        col_Distributer.setCellValueFactory(new PropertyValueFactory<Medicine, String>("distributer"));
        dataList = mysqlconnect.getMedicine();
        table_users.setItems(dataList);
        FilteredList<Medicine> filteredData = new FilteredList<>(dataList, b -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (person.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches username
                } else if (person.getBrand().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches password
                } else if (person.getMg().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches password
                } else if (String.valueOf(person.getDistributer()).indexOf(lowerCaseFilter) != -1) {
                    return true;// Filter matches email
                } else {
                    return false; // Does not match.
                }
            });
        });
        SortedList<Medicine> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table_users.comparatorProperty());
        table_users.setItems(sortedData);
    }
    /**
     * Initializes the controller class.
     */
    @FXML
    void clickSubmit(ActionEvent event) {
        editid.setVisible(false);
                                    try {
                                
                                conn = mysqlconnect.connectDb();
                                String value1 = txt_id.getText();
                                String value2 = txt_name.getText();
                                String value3 = txt_mg.getText();
                                String value5 = txt_brand.getText();
                                String value6 = txt_distributer.getText();
                                String sql = "update medicine set Medicineid='" + value1 + "', Name='" + value2 + "', Mg='" + value3 + "', Brand='" + value5 + "',Distributer='" + value6 + "' where Medicineid=" + value1 + "";
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
    void add(ActionEvent event) {
        value="add";
         try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MedicineAdd.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Add Medicine");
            stage.setScene(new Scene(root1));
            stage.show();
            add_btn_medicine.getScene().getWindow().hide();
        } catch (Exception e) {
//            System.out.println("cont load");
        }
    }
    static String value,n,m,b,d;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        col_id.setCellValueFactory(new PropertyValueFactory<Medicine, Integer>("id"));
        col_Name.setCellValueFactory(new PropertyValueFactory<Medicine, String>("name"));
        col_Brand.setCellValueFactory(new PropertyValueFactory<Medicine, String>("brand"));
        col_Mg.setCellValueFactory(new PropertyValueFactory<Medicine, String>("mg"));
        col_Distributer.setCellValueFactory(new PropertyValueFactory<Medicine, String>("distributer"));
        listM = mysqlconnect.getMedicine();
        table_users.setItems(listM);
        
        Callback<TableColumn<Medicine, String>, TableCell<Medicine, String>> cellco = (params) -> {

            final TableCell<Medicine, String> cell = new TableCell<Medicine, String>() {
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
//                            System.out.println();
                            if (value.equals("Edit")) {
                                Medicine a = getTableView().getItems().get(getIndex());
                                n = a.getName();
                                d=a.getDistributer();
                                m=a.getMg();
                                b = a.getBrand();
//                                System.out.println(n+""+d+""+ m +""+b);
                                Parent root;
                                try {
                                    root = FXMLLoader.load(getClass().getResource("MedicineAdd.fxml"));
                                    Stage mainStage = new Stage();
                                    Scene scene = new Scene(root);
                                    mainStage.setScene(scene);
                                    mainStage.setTitle("Medicine Viewer");
                                    mainStage.show();
                                    comboBox.getScene().getWindow().hide();
                                } catch (IOException ex) {
                                    Logger.getLogger(MedicineController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } else if (value.equals("Delete")) {
                                Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Confirmation Dialog");
                                alert.setHeaderText(null);
                                alert.setContentText("Are you sure want to delete");
                                Optional <ButtonType> action= alert.showAndWait();
                                if(action.get()== ButtonType.OK){
                                conn = mysqlconnect.connectDb();
                                Medicine a = getTableView().getItems().get(getIndex());
                                String sql = String.format("delete from medicine where medicineid = %d", a.getId());
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
           
    }    

    
}
