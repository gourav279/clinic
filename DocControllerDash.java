/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dental;

import com.lowagie.text.pdf.PdfWriter;
import static dental.mysqlconnect.connectDb;
import java.awt.Desktop;
import java.awt.HeadlessException;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

/**
 *
 * @author doagu
 */
public class DocControllerDash implements Initializable {

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
    private Pane changePane;

    @FXML
    private Button log_btn;

    @FXML
    private TextField p;

    @FXML
    private TextField cp;

    @FXML
    private TableView<medicinelist> medicinetable;

    @FXML
    private TableColumn<medicinelist, String> colMedicineName;

    @FXML
    private TableColumn<medicinelist, String> colDosage;

    @FXML
    private TableColumn<medicinelist, String> colFrequency;

    @FXML
    private TableColumn<medicinelist, String> colDuration;

    @FXML
    private TableColumn<medicinelist, String> colNote;

    private ObservableList<medicinelist> medicinedata;
//    @FXML
//    private TableColumn<medicinelist, ?> colBtn;
    @FXML
    private AnchorPane doctorPane;

    @FXML
    private TitledPane titledL;
    @FXML
    private Pane medicineAdd;
    @FXML
    private ComboBox<String> personname;
    @FXML
    private ComboBox<String> productname;

    @FXML
    void forgetWindowOpen(ActionEvent event) {
        profilePane.setVisible(true);
    }

//    @FXML 
//    private PDFViewer m_PDFViewer;
    void insertpdf(ActionEvent event) throws FileNotFoundException {
        String sql = "insert into experi(image)values(?)";
        String Abcd = "C:\\dentalHouse\\report2.pdf";
        con = mysqlconnect.connectDb();
        try {
            File file = new File(Abcd);
            FileInputStream input = new FileInputStream(file);
            pst = con.prepareStatement(sql);
            pst.setBinaryStream(1, input);
            pst.execute();

        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    @FXML
    private ImageView imageView;

    @FXML
    void logInWindoeOpen(ActionEvent event) throws IOException {
        log_btn.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Stage mainStage = new Stage();
        Scene scene = new Scene(root);
        mainStage.setTitle("LogIn Page");
        mainStage.setScene(scene);
        mainStage.show();
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
    private TableView<A> Appointmenttable;

    @FXML
    private TableColumn<A, String> date;

    @FXML
    private TableColumn<A, String> button;

    @FXML
    private TableColumn<A, String> pname;

    @FXML
    private TableColumn<A, String> pType;

    @FXML
    private TableColumn<A, String> ptimeee;

    @FXML
    private TableColumn<A, String> col_ID;

    @FXML
    private TableColumn colBtn;
    ObservableList<A> list;
    ObservableList<dia> listA;
    ObservableList<complaint> listB;

    public void Update() {
        pname.setCellValueFactory(new PropertyValueFactory<>("name"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        pType.setCellValueFactory(new PropertyValueFactory<A, String>("ptye"));
        ptimeee.setCellValueFactory(new PropertyValueFactory<>("time"));
        col_ID.setCellValueFactory(new PropertyValueFactory<A, String>("id"));
        list = mysqlconnect.getA();
        Appointmenttable.setItems(list);
    }

    @FXML
    private TableView<B> Patienttable;

    @FXML
    private TableColumn<B, String> pname1;

    @FXML
    private TableColumn<B, String> phone1;

    @FXML
    private TableColumn<B, String> pdate1;

    @FXML
    private TableColumn<B, String> ptime1;

    @FXML
    private TableColumn ptreatment1;

    @FXML
    private TableColumn pmedicine1;

    @FXML
    private AnchorPane libaryPane;
    @FXML
    private ScrollBar scrollvalue;
    static GraphicsContext gc;

    ObservableList<B> list2;
    ObservableList<treatmentlist> list3;

//    search Text Field
    @FXML
    private TextField filterField;

    ObservableList<B> list22;

    public void Update2() {
        pname1.setCellValueFactory(new PropertyValueFactory<>("name"));
        phone1.setCellValueFactory(new PropertyValueFactory<>("number"));
        pdate1.setCellValueFactory(new PropertyValueFactory<>("date"));
        ptime1.setCellValueFactory(new PropertyValueFactory<>("time"));
        list2 = mysqlconnect.getB();
        Patienttable.setItems(list2);
    }

//    search Method
    void search_user() {
        pname1.setCellValueFactory(new PropertyValueFactory<>("name"));
        phone1.setCellValueFactory(new PropertyValueFactory<>("number"));
        pdate1.setCellValueFactory(new PropertyValueFactory<>("date"));
        ptime1.setCellValueFactory(new PropertyValueFactory<>("time"));
        list22 = mysqlconnect.getB();
        Patienttable.setItems(list22);
        FilteredList<B> filteredData = new FilteredList<>(list22, b -> true);
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
                } else {
                    return false; // Does not match.
                }
            });
        });
        SortedList<B> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(Patienttable.comparatorProperty());
        Patienttable.setItems(sortedData);
    }

    @FXML
    void patientTableOpen(ActionEvent event) {
        Patienttable.setVisible(true);
        filterField.setVisible(true);
        Appointmenttable.setVisible(false);
        Update2();
        schedulePane.setVisible(false);
        libary.setVisible(false);
    }

    @FXML
    void AppointTableShow(ActionEvent event) {
        Patienttable.setVisible(false);
        filterField.setVisible(false);
        Update();
        Appointmenttable.setVisible(true);
        schedulePane.setVisible(false);
        libary.setVisible(false);
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

    @FXML
    void changeWindow(ActionEvent event) {
        changePane.setVisible(true);
    }

    @FXML
    void closeWindow(ActionEvent event) {
        profilePane.setVisible(false);
    }

    void scheduleWindow(ActionEvent event) throws SQLException {
        Patienttable.setVisible(false);
        Appointmenttable.setVisible(false);
        schedulePane.setVisible(true);
        Connection con = mysqlconnect.connectDb();
        PreparedStatement pst = con.prepareStatement("select * from shift");
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            shift1.setText(rs.getString("shift1"));
            shift2.setText(rs.getString("shift2"));
            shift3.setText(rs.getString("shift3"));
        }
    }
    @FXML
    private Pane schedulePane;

    @FXML
    private Label shift1;

    @FXML
    private Label shift2;

    @FXML
    private Label shift3;

    String n1, d1;

    @FXML
    private Label pName;

    @FXML
    private ColorPicker color2;

    @FXML
    private Label pMobile;

    @FXML
    private Label pAge;

    @FXML
    private Canvas canvas;

    @FXML
    private Label pAddress;

    static int index = -1;

    @FXML
    void mainWindow(ActionEvent event) {
        libaryPane.setVisible(false);
        complainttable.getItems().clear();
        treatmenttable.getItems().clear();
        medicinetable.getItems().clear();
        diagnosistable.getItems().clear();

    }

    @FXML
    private TitledPane titledT;

    @FXML
    private TitledPane titled1;

    @FXML
    private TitledPane titledP;

    @FXML
    private TableView<Libary> libary;

    @FXML
    private TableColumn<Libary, String> distributer;

    @FXML
    private TableColumn<Libary, String> pname2, porder2, pproduct2, pexpected2, precive2, pstatus2;

    public class Libary {

        private String name, productName, orderdate, expectdate, receivedate, status, distributer;

        public Libary(String name, String productName, String orderdate, String expectdate, String receivedate, String status, String distributer) {
            this.name = name;
            this.productName = productName;
            this.orderdate = orderdate;
            this.expectdate = expectdate;
            this.receivedate = receivedate;
            this.status = status;
            this.distributer = distributer;
        }

        public Libary(String productName) {
            this.productName = productName;
        }
        

        public String getDistributer() {
            return distributer;
        }

        public void setDistributer(String distributer) {
            this.distributer = distributer;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getOrderdate() {
            return orderdate;
        }

        public void setOrderdate(String orderdate) {
            this.orderdate = orderdate;
        }

        public String getExpectdate() {
            return expectdate;
        }

        public void setExpectdate(String expectdate) {
            this.expectdate = expectdate;
        }

        public String getReceivedate() {
            return receivedate;
        }

        public void setReceivedate(String receivedate) {
            this.receivedate = receivedate;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }

    public void getLibary() {
        Connection conn = mysqlconnect.connectDb();
        ObservableList<Libary> list = FXCollections.observableArrayList();
        ObservableList<String> list1 = FXCollections.observableArrayList();
        ObservableList<String> list2 = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from libary as l join patient as p where l.patientid=p.patientid");
            ResultSet rs = ps.executeQuery();
//            System.out.println("Observation List"+rs.getString(1));
            while (rs.next()) {
//                System.out.println(rs.getString("name"));
                list.add(new Libary(rs.getString("name"), rs.getString("productName"), rs.getString("orderdate"), rs.getString("expectdate"), rs.getString("receivedate"), rs.getString("status"), rs.getString("distributer")));
                list1.add(rs.getString("productName"));
                list2.add(rs.getString("distributer"));
            }
        } catch (Exception e) {
            System.out.print("Exception " + e);
        }
        pname2.setCellValueFactory(new PropertyValueFactory<>("name"));
        pproduct2.setCellValueFactory(new PropertyValueFactory<>("productName"));
        porder2.setCellValueFactory(new PropertyValueFactory<>("orderdate"));
        pexpected2.setCellValueFactory(new PropertyValueFactory<>("expectdate"));
        precive2.setCellValueFactory(new PropertyValueFactory<>("receivedate"));
        pstatus2.setCellValueFactory(new PropertyValueFactory<>("status"));
        distributer.setCellValueFactory(new PropertyValueFactory<>("distributer"));
        libary.setItems(list);
        productname.setItems(list1);
        personname.setItems(list2);
    }

    @FXML
    void mainWindowNP(ActionEvent event) {
        newstatus();
        libaryPane.setVisible(false);
        complainttable.getItems().clear();
        treatmenttable.getItems().clear();
        medicinetable.getItems().clear();
        diagnosistable.getItems().clear();
        Update2();
        Update();

    }

    @FXML
    private ComboBox treamentType;

    Color color;

    @FXML
    void getColor(ActionEvent event) {
        color = color2.getValue();
    }

    public void keypressed() {
        treamentType.setOnKeyPressed(event -> {
//            try {
////            ObservableList<String> data = FXCollections.observableArrayList();
//            Connection con = mysqlconnect.connectDb();
//            PreparedStatement pst = con.prepareStatement("select * from treatment where name=%s", treamentType.getValue());
//            ResultSet rs = pst.executeQuery();
//
////            while (rs.next()) {
//                System.out.println(rs.getString("price"));
//                cost.setText(rs.getString("price"));
////            }
//            
//        } catch (Exception ex) {
//                System.out.println(ex);
//        }
        });
    }
    @FXML
    private ComboBox<String> txtMedicine;

    @FXML
    private ComboBox<String> comboDosages;

    @FXML
    private ComboBox<String> comboFrequency;

    @FXML
    private ComboBox<String> comboDuration;

    @FXML
    private ComboBox<String> comboNote;

    @FXML
    private ComboBox<String> medicineType;

    @FXML
    private TextField treatmentCost;

    @FXML
    private TextField tdiscount;

    @FXML
    private TextField ttcost;

    @FXML
    private Button btnTadd, libaryAction;

    @FXML
    private TableView<treatmentlist> treatmenttable;

    @FXML
    private TableColumn<treatmentlist, String> colTreatment;

    @FXML
    private TableColumn<treatmentlist, Double> colCost;

    @FXML
    private TableColumn<treatmentlist, Double> colDiscount;

    @FXML
    private TableColumn<treatmentlist, Double> colTcost;

    @FXML
    private TableColumn colTbtn;

    @FXML
    void printMedicine(ActionEvent event) throws SQLException, FileNotFoundException {
        ++number;
        String A, B;
        A = String.valueOf(number);
        B = "report" + A;
        folder();
        String sourceFile;
        sourceFile = "medicine.jrxml";
        try {
            JasperReport jr = JasperCompileManager.compileReport(sourceFile);
            HashMap<String, Object> para = new HashMap<>();
            para.put("pname", pName.getText());
            para.put("page", pAge.getText());
            para.put("pmobileno", pMobile.getText());
            para.put("paddress", pAddress.getText());
            para.put("rid", "UH_" + rpid);
            para.put("pid", "RG_" + rpid);
//            para.put("img", this.getServletContext().getRealPath("/")+"/images/asteroids.jpg");
//            String name= billCustomerMobileNo.getText();
            ArrayList<medicinelist2> plist = new ArrayList<>();
            medicinedata.forEach(ol -> {
                plist.add(new medicinelist2(" " + ol.getMedicine(), " " + ol.getDosages(), " " + ol.getFrequency(), " " + ol.getDuration(), " " + ol.getNotes()));
            });
            JRBeanCollectionDataSource jcs = new JRBeanCollectionDataSource(plist);
            JasperPrint jp = JasperFillManager.fillReport(jr, para, jcs);
            String exportPath = "C:/dentalhouse/" + dt + "/" + ser3 + "/" + B + ".pdf";
            pdf1 = "C:/dentalhouse/" + dt + "/" + ser3 + "/" + B + ".pdf";
            JasperExportManager.exportReportToPdfFile(jp, exportPath);
            JasperViewer.viewReport(jp, false);
            pdfsave();
        } catch (JRException ex) {
            Logger.getLogger(DocControllerDash.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    double X = 0;

    @FXML
    private Label lblTamount;

    @FXML
    public void addtreatment() {
        String treatmenttype;

        double cost, discount = 0, tcost;
        treatmenttype = (String) treamentType.getValue();
        cost = Double.parseDouble(treatmentCost.getText());
        discount = Double.parseDouble(tdiscount.getText());
        tcost = cost - discount;
        X = X + tcost;
        lblTamount.setText(String.valueOf(X));
        ttcost.setText(String.valueOf(tcost));
        list3.add(new treatmentlist(treatmenttype, cost, discount, tcost));
        treatmenttable.setItems(list3);
        treatmentCost.clear();
        ttcost.clear();
        tdiscount.clear();
        treamentType.getSelectionModel().clearSelection();
    }

    @FXML
    void fillclicked(MouseEvent event) {
        double B, C, D;
        D = Double.parseDouble(treatmentCost.getText());
        B = Double.parseDouble(tdiscount.getText());
        C = D - B;
//   A=tcost;
        ttcost.setText(String.valueOf(C));

    }
    String comA, comB, comC, comD;
    String diaA, diaB, diaC, diaD;

    @FXML
    public void addComplaint() {
        System.out.println(ww);
        comA = textCom.getText();
        comB = chiefComplaint.getValue();
        comA = comB + "," + comA;
        textCom.setText(comA);
    }

    @FXML
    public void addDiagnosis() {
        diaA = textDia.getText();
        diaB = diagnosisType.getValue();
        diaA = diaB + "," + diaA;
        textDia.setText(diaA);

    }

    @FXML
    public void addmedicines() {

        String medicine, dosages, frequency, duration, note;
        medicine = txtMedicine.getValue();
        dosages = (String) comboDosages.getValue();
        frequency = (String) comboFrequency.getValue();
        duration = (String) comboDuration.getValue();
        note = (String) comboNote.getValue();
        medicinedata.add(new medicinelist(medicine, dosages, frequency, duration, note));
        medicinetable.setItems(medicinedata);

        txtMedicine.getSelectionModel().clearSelection();
        comboDosages.getSelectionModel().clearSelection();
        comboFrequency.getSelectionModel().clearSelection();
        comboDuration.getSelectionModel().clearSelection();
        comboNote.getSelectionModel().clearSelection();
    }

    public void fillCombo() {
        ObservableList<String> dataA = FXCollections.observableArrayList();
        ObservableList<String> dataB = FXCollections.observableArrayList();
        ObservableList<String> dataC = FXCollections.observableArrayList();
        ObservableList<String> dataD = FXCollections.observableArrayList();
        ObservableList<String> dataE = FXCollections.observableArrayList();
        ObservableList<String> dataF = FXCollections.observableArrayList();
        ObservableList<String> dataG = FXCollections.observableArrayList();
        ObservableList<String> dataH = FXCollections.observableArrayList();
//        ObservableList<String> dataA = FXCollections.observableArrayList();
//        try {
//            pst = con.prepareStatement("select * from medicine");
//            rs = pst.executeQuery();
//            while (rs.next()) {
//            dataH.add(rs.getString("Brand"));
//            }
//        } catch (SQLException e) {
//        }
//         try {
//            pst = con.prepareStatement("select * from medicine where brand='"+medicineType.getValue()+"'");
//            rs = pst.executeQuery();
//            while (rs.next()) {
//            dataG.add(rs.getString("name"));
//            }
//        } catch (SQLException e) {
//        }
        dataA.addAll("100 mg", "200 mg", "500 mg");
        dataB.addAll("1-1-1", "1-1-0", "0-1-1", "1-0-1", "0-0-1", "0-1-0", "1-0-0");
        dataC.addAll("1 days", "2 days", "3 days", "4 days", "5 days", "6 days", "1 Week", "2 Week", "3 Week", "1 Month", "3 Months");
        dataD.addAll("Before Meal", "After Meal");
        dataE.addAll("A", "B");
        dataF.addAll("A", "B");
        dataG.addAll("A", "B");
        dataH.addAll("A", "B");
        comboDosages.getItems().addAll(dataA);
        comboFrequency.getItems().addAll(dataB);
        comboDuration.getItems().addAll(dataC);
        comboNote.getItems().addAll(dataD);
        diagnosisType.getItems().addAll(dataE);
        chiefComplaint.getItems().addAll(dataF);
//        txtMedicine.getItems().addAll(dataG);
//        medicineType.getItems().addAll(dataH);
        chiefComplaint.setEditable(true);
        diagnosisType.setEditable(true);
        txtMedicine.setEditable(true);
        comboNote.setEditable(true);

    }
    @FXML
    private Button addComplaint;

    @FXML
    private Button addDiagnosis;

    private TableView<complaint> complainttable;

    private TableView<dia> diagnosistable;

    @FXML
    private ComboBox<String> diagnosisType;

    @FXML
    private ComboBox<String> chiefComplaint;

    public void newstatus() {

        Connection con = mysqlconnect.connectDb();
        try {
            String sql = "update appointment set Status= 'Treatment Done' where patientname='" + nn + "' and date='" + da + "' and time='" + tt + "'";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.execute();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    private Button btnbbck;

    Image image;

    @FXML
    private Button btnrrset;

    @FXML
    void clickeddd(MouseEvent event) {
        btnbbck.setVisible(false);
        Connection con = mysqlconnect.connectDb();
        try {
            String sql = "update appointment set Status= 'In Process' where patientname='" + nn + "' and date='" + da + "' and time='" + tt + "'";

            System.out.println(sql);
            PreparedStatement pst = con.prepareStatement(sql);
            pst.execute();

        } catch (Exception e) {
//                                    System.out.println(e);
        }
    }

    @FXML
    void clicked(MouseEvent event) throws SQLException {
        String sql;
        s = (String) treamentType.getValue();
        sql = String.format("select * from treatment where Name='%s'", s);
//         System.out.println(sql);
        Connection con = mysqlconnect.connectDb();
//        Connection conn=mysqlconnect().connectDb();
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
//            System.out.println(rs.getString("Price"));
            treatmentCost.setText(String.valueOf(rs.getString("Price")));
        }
    }
    ResultSet rs;
    PreparedStatement pst;
    Connection con;
    static String s;
    double A;

    public void treatmentCombofill() {
        try {
            ObservableList<String> data = FXCollections.observableArrayList();
            Connection con = mysqlconnect.connectDb();
            pst = con.prepareStatement("select * from treatment");
            rs = pst.executeQuery();

            while (rs.next()) {
//                System.out.println(rs.getString("name"));
                data.add(rs.getString("name"));
            }
            treamentType.setItems(data);
//                System.out.println(treamentType.getValue());
//            treamentType.setEditable(true);

//                System.out.println(s);
        } catch (Exception e) {

        }
    }
    static String rpid;

    void nextPatient(ActionEvent event) {
        number = 0;
        index++;
        medicinetable.getItems().clear();
        treatmenttable.getItems().clear();
        textCom.clear();
        textDia.clear();
        textNote.clear();
//        System.out.println(index++);
        if (Appointmenttable.getItems().size() > index) {
            try {
                A a = Appointmenttable.getItems().get(index);
//                System.out.println();
//                System.out.println(a.getName());
                String name = a.getDate();
//                System.out.println(name);
                Connection con = mysqlconnect.connectDb();
                try {
                    String sql2 = "select * from appointment where patientname='" + a.getName() + "' and date='" + a.getDate() + "'";
                    PreparedStatement pst = con.prepareStatement(sql2);
                    ResultSet rs = pst.executeQuery();
                    if (rs.next()) {
                        String sql = "select * from patient where patientid=" + rs.getString("patientid");
                        pst = con.prepareStatement(sql);
                        rs = pst.executeQuery();
                        if (rs.next()) {

//                                        System.out.println(rs.getString("name"));
                            rpid = rs.getString("patientid");
                            pName.setText(rs.getString("name"));
                            pAge.setText(rs.getString("age"));
                            pAddress.setText(rs.getString("address"));
                            pMobile.setText(rs.getString("number"));
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(DocControllerDash.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (IndexOutOfBoundsException e) {
                Window owner = log_btn.getScene().getWindow();
                showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                        "There is no more patient");
                return;
            }
        } else {
            Window owner = log_btn.getScene().getWindow();
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "There is no more patient");
            return;
        }
    }
//    @FXML
//    private ScrollBar scrollValue;
//    static GraphicsContext gc;
//    @FXML
//    private Canvas canvas;

    @FXML
    void draw(MouseEvent event) {

    }
    @FXML
    private TextArea textCom;

    @FXML
    private TextArea textDia;

    @FXML
    private TextArea textNote;

    @FXML
    public void savenew() throws IOException, COSVisitorException {
//          File file = new File("C:/dentalhouse/test.pdf");  
//          PDDocument doc = new PDDocument(); 
//          PDPage page = new PDPage(); 
//          PDImageXObject pdImage = PDImageXObject.createFromFile("C:/dentalhouse/test.png",doc);  
//          PDPageContentStream contents = new PDPageContentStream(doc, page);  
//          contents.drawImage(pdImage);  
//          contents.close();  
//          doc.save("C:/dentalhouse/test2.pdf");  
//          doc.close();

//         Document doc = new Document();
//         PdfWriter.getInstance(doc, new FileOutputStream("report.pdf"));
//         doc.open();
//         Image image = Image.getInstance("C:\\Users\\doagu\\Downloads\\3203243.png");
//         doc.add(image);
//         doc.close();
//         System.out.println("save");
    }

    String com, dia, not;
    String dia1, dia2;
    String com1, com2;

    @FXML
    void saveExamination(ActionEvent event) throws IOException {
//    com=textCom.getText();
//    dia=textDia.getText();
//    not=textNote.getText();
        String imagePath = "C:\\dentalHouse\\test.png";
        File file = new File(imagePath);
        WritableImage writableImage = new WritableImage(591, 327);
        canvas.snapshot(null, writableImage);
        RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
        ImageIO.write(renderedImage, "png", file);
    }
    int number = 0;
    String ser, ser2, ser3;
//     img = new Image(new FileInputStream("C:\\Users\\doagu\\Documents\\NetBeansProjects\\dental\\src\\dental\\image\\teeth group.png"));
    String R, RR;

    @FXML
    void printTreatment(ActionEvent event) throws FileNotFoundException, IOException {
        ++number;

        R = String.valueOf(number);
        RR = "report" + R;
        folder();
        String sourceFile;
        sourceFile = "treatmentreport.jrxml";
        try {
            JasperReport jr = JasperCompileManager.compileReport(sourceFile);
            HashMap<String, Object> para = new HashMap<>();
            para.put("pname", pName.getText());
            para.put("page", pAge.getText());
            para.put("pmobileno", pMobile.getText());
            para.put("paddress", pAddress.getText());
            para.put("rid", "UH_" + rpid);
            para.put("pid", "RG_" + rpid);
            para.put("com", textCom.getText());
            para.put("dia", textDia.getText());
            para.put("note", textNote.getText());
            para.put("total", lblTamount.getText());

            ArrayList<treatmentlist2> plist = new ArrayList<>();
            list3.forEach(ol -> {
                plist.add(new treatmentlist2(" " + ol.getTreatmenttype(), " " + ol.getCost(), " " + ol.getDiscount(), " " + ol.getTcost()));
            });
            JRBeanCollectionDataSource jcs = new JRBeanCollectionDataSource(plist);
            JasperPrint jp = JasperFillManager.fillReport(jr, para, jcs);
            String exportPath = "C:/dentalhouse/" + dt + "/" + ser3 + "/" + RR + ".pdf";
            pdf2 = "C:/dentalhouse/" + dt + "/" + ser3 + "/" + RR + ".pdf";
            JasperExportManager.exportReportToPdfFile(jp, exportPath);
            JasperViewer.viewReport(jp, false);
        } catch (JRException ex) {
            Logger.getLogger(DocControllerDash.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
//    public void newtreatmentpdf() throws IOException, COSVisitorException, DocumentException{
// PDDocument doc = new PDDocument();
// PDPage page = new PDPage();
// doc.addPage(page);
// FileInputStream in = new  FileInputStream("C:/Users/doagu/Downloads/3203243.png");
// PDJpeg img = new PDJpeg(doc, in);
// PDPageContentStream = new PDPageContentStream(doc, page);
// stream.
//    }
    static String dt, dt2, time;
    static double length;

    public void folder() {
        String dir = "C:/dentalHouse/" + dt + "/" + ser3 + "/";
        File file = new File(dir);
        file.mkdirs();

    }
    static String pdf1, pdf2;

    public void pdfsave() throws FileNotFoundException {
        con = mysqlconnect.connectDb();
        String sql = "insert into treatmenthistory(patientname, patientmobileno, date, time, medicinepdf, treatmentpdf,tamount, patientid,paymentstatus)values(?,?,?,?,?,?,?,?,?)";
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, pName.getText());
            pst.setString(2, pMobile.getText());
            java.sql.Date d = java.sql.Date.valueOf(dt2);
            pst.setDate(3, d);
            pst.setString(4, time);
            File file = new File(pdf1);
            FileInputStream input = new FileInputStream(file);
            pst.setBinaryStream(5, input);
            File file2 = new File(pdf2);
            FileInputStream input2 = new FileInputStream(file2);
            pst.setBinaryStream(6, input2);
            pst.setString(7, lblTamount.getText());
            pst.setString(8, patid);
            pst.setString(9, "Pending");
            pst.execute();

        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }
    static String tt, da, nn, ww;
    static String patid;

    @FXML
    private DatePicker expectdate, orderdate;


    @FXML
    private Button libaryAction2;

    @FXML
    void medicinesort(MouseEvent event) {

        ww = medicineType.getValue();
        if (ww == null) {
            try {
                ObservableList<String> data2 = FXCollections.observableArrayList();
                Connection con = mysqlconnect.connectDb();
                String sql = "select * from medicine ";
//                    System.out.println(sql);
                PreparedStatement pst = con.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();

                while (rs.next()) {
//                System.out.println(rs.getString("Name"));
//                 System.out.println(sql);
                    data2.add(rs.getString("Name"));
                }
                txtMedicine.setItems(data2);

            } catch (Exception ex) {
//            System.out.println(ex);
            }
        } else {
            try {
                ObservableList<String> dataAA = FXCollections.observableArrayList();
                Connection con = mysqlconnect.connectDb();
                String sql = "select * from medicine where brand='" + ww + "' ";
//                    System.out.println(sql);
                PreparedStatement pst = con.prepareStatement(sql);

                while (rs.next()) {
//                System.out.println(rs.getString("Name"));
//                System.out.println(sql);
                    dataAA.add(rs.getString("Name"));
                }
                txtMedicine.setItems(dataAA);
                ResultSet rs = pst.executeQuery();

            } catch (Exception ex) {
//            System.out.println(ex);
            }
        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getLibary();
//        final ObservableList option= FXCollections.observableArrayList();
//        ResultSet rsq= con.createStatement().executeQuery("SELECT * from libary");
//        while (rs.next()) {
//                //get string from db,whichever way 
//                option.add(new Libary(rs.getString("username"),(rs.getString("username")));
//            }
         

//        productname.getItems().addAll("A","B","C","E");
        libaryAction2.setOnAction(e->{
//            String sql = "insert into admin (Name,Number,Branch,Email,Password,Address,Status)values(?,?,?,?,?,?,?)";
            String sql = "insert into libary (patientid,productname,orderdate,expectdate,status,deleiveredto)values(?,?,?,?,?,?)";
            try {
                    pst.execute(sql);
                    pst.setString(1, patid);
                    pst.setString(2, productname.getValue());
                    java.sql.Date d=java.sql.Date.valueOf(orderdate.getValue().toString());
                    pst.setDate(3, d);
                    java.sql.Date d2=java.sql.Date.valueOf(expectdate.getValue().toString());
                    pst.setDate(4, d2);
                    pst.setString(5, "pending");
                    pst.setString(6, personname.getValue());
                    
            } catch (SQLException ex) {
                Logger.getLogger(DocControllerDash.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(ex);
            }
        });
//        medicine Type
        try {
            ObservableList<String> data2 = FXCollections.observableArrayList();
            Connection con = mysqlconnect.connectDb();
            String sql = "select DISTINCT Brand from medicine ";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
//                System.out.println(rs.getString("Brand"));
                data2.add(rs.getString("Brand"));
            }
            medicineType.setItems(data2);

        } catch (Exception ex) {
//            System.out.println(ex);
        }

//         medicine Name
        search_user();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat f = new SimpleDateFormat("ddMMyyyy");
        SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat sdf = new SimpleDateFormat("HH:MM");
        dt = f.format(cal.getTime());
        dt2 = sf.format(cal.getTime());
        time = sdf.format(cal.getTime());

//         System.out.println(dt+dt2+time);
        treatmentCombofill();

//       String path="C:/dentalhouse";
//       File file= new File(path);
//        Color color = color2.getValue();
        scrollvalue.setValue(5);
        gc = canvas.getGraphicsContext2D();

        try {
            image = new Image(new FileInputStream("teeth group.png"));

            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(332);
            imageView.setFitWidth(400);
            //gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
//    teeth2.getImage();
//            Image value = teeth2.getImage();
//            System.out.println(value);
//            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.drawImage(image, 0, 0);
        } catch (FileNotFoundException ex) {
//            System.out.println("here is error");
            Logger.getLogger(DocControllerDash.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Setting the image view 
        canvas.setOnMousePressed(e -> {
//        gc.beginPath();
            gc.setFill(color);
            length = scrollvalue.getValue();
//            gc.lineTo(e.getSceneX(), e.getSceneY());
//            gc.stroke();
        });
        canvas.setOnMouseDragged(e -> {
            gc.fillRect(e.getX(), e.getY(), length, length);

//            gc.lineTo(e.getSceneX(), e.getSceneY());
//            gc.stroke();
        });

        btnrrset.setOnAction(e -> {
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            gc.drawImage(image, 0, 0);
        });
        Update();
        Update2();

        fillCombo();
        colTreatment.setCellValueFactory(new PropertyValueFactory<>("treatmenttype"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colTcost.setCellValueFactory(new PropertyValueFactory<>("tcost"));
        treatmenttable.setItems(list3);
        list3 = FXCollections.observableArrayList();

        colMedicineName.setCellValueFactory(new PropertyValueFactory<>("medicine"));
        colDosage.setCellValueFactory(new PropertyValueFactory<>("dosages"));
        colFrequency.setCellValueFactory(new PropertyValueFactory<>("frequency"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colNote.setCellValueFactory(new PropertyValueFactory<>("note"));
        medicinetable.setItems(medicinedata);
        medicinedata = FXCollections.observableArrayList();
        Callback<TableColumn<A, String>, TableCell<A, String>> cellFactory = (params) -> {

            final TableCell<A, String> cell = new TableCell<A, String>() {
                //override updatesItem method
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //ensure that cell is creates only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button button1 = new Button("Start Treatment");
                        button1.setStyle("-fx-background-color:grey;");
                        button1.setStyle("-fx-background-radius:50");
                        button1.setStyle("-fx-text-fill:white");
                        button1.setOnAction(event -> {
//                             titledP.setCollapsible(false);
//                             titled1.setCollapsible(false);
//                             titledP.setCollapsible(false);
                            titledP.setExpanded(false);
                            titled1.setExpanded(false);
                            titledP.setExpanded(false);
                            libaryPane.setVisible(true);
                            A a = getTableView().getItems().get(getIndex());
                            tt = a.getTime();
                            da = a.getDate();
                            nn = a.getName();
                            index = getTableRow().getIndex();

//                            System.out.println(index);
                            Connection con = mysqlconnect.connectDb();
                            try {
                                PreparedStatement pst = con.prepareStatement("select * from appointment where date='" + da + "'and time='" + tt + "' and patientname='" + nn + "'");
                                ResultSet rs = pst.executeQuery();

                                if (rs.next()) {
                                    String sql = "select * from patient where patientid=" + rs.getString("patientId");
                                    pst = con.prepareStatement(sql);
                                    rs = pst.executeQuery();
//                                    System.out.println(sql);
                                    if (rs.next()) {

//                                        System.out.println(rs.getString("name"));
                                        pName.setText(rs.getString("name"));
                                        pAge.setText(rs.getString("age"));
                                        pAddress.setText(rs.getString("address"));
                                        pMobile.setText(rs.getString("number"));
                                        patid = rs.getString("patientid");

                                        ser = pName.getText();
                                        ser2 = pMobile.getText();
                                        ser3 = ser + ser2;
//                                        System.out.println(ser3);
                                    }
                                }
                            } catch (SQLException ex) {
                                Logger.getLogger(DocControllerDash.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        });

                        setGraphic(button1);
                        setText(null);

                    }
                }
            };

            return cell;
        };

        button.setCellFactory(cellFactory);
        int i = Appointmenttable.getItems().size();

        Callback<TableColumn<medicinelist, String>, TableCell<medicinelist, String>> cellFactory2 = (params) -> {

            final TableCell<medicinelist, String> cell = new TableCell<medicinelist, String>() {
                //override updatesItem method
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //ensure that cell is creates only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button button1 = new Button("X");
                        button1.setStyle("-fx-background-color:red");
                        button1.setStyle("-fx-background-radius:50 50 50 50");
                        button1.setStyle("-fx-text-fill:white");
                        button1.centerShapeProperty();
                        button1.setOnAction(event -> {
                            medicinelist m = getTableView().getItems().get(getIndex());
                            medicinetable.getItems().remove(m);
                        });

                        setGraphic(button1);
                        setText(null);

                    }
                }
            };

            return cell;
        };
        colBtn.setCellFactory(cellFactory2);
        Callback<TableColumn<treatmentlist, String>, TableCell<treatmentlist, String>> cellFactory3 = (params) -> {

            final TableCell<treatmentlist, String> cell = new TableCell<treatmentlist, String>() {
                //override updatesItem method
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //ensure that cell is creates only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button button3 = new Button("X");
                        button3.setStyle("-fx-background-color:red");
                        button3.setStyle("-fx-background-radius:50 50 50 50");
                        button3.setStyle("-fx-text-fill:white");
                        button3.centerShapeProperty();
                        button3.setOnAction(event -> {
                            treatmentlist m = getTableView().getItems().get(getIndex());
                            treatmenttable.getItems().remove(m);
                            double Y;
                            Y = m.tcost;
                            X = X - Y;
                            lblTamount.setText(String.valueOf(X));
                        });

                        setGraphic(button3);
                        setText(null);

                    }
                }
            };

            return cell;
        };
        colTbtn.setCellFactory(cellFactory3);

        Callback<TableColumn<B, String>, TableCell<B, String>> cellFactory6 = (params) -> {

            final TableCell<B, String> cell = new TableCell<B, String>() {
                //override updatesItem method
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //ensure that cell is creates only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button button3 = new Button("View Report");
                        button3.setStyle("-fx-background-color:red");
                        button3.setStyle("-fx-background-radius:50");
                        button3.setStyle("-fx-text-fill:white");
                        button3.centerShapeProperty();
                        button3.setOnAction(event -> {
                            int h = 1;
                            B m = getTableView().getItems().get(getIndex());
                            String q = m.getTime();
                            String u = m.getDate();
                            String sql = String.format("select * from treatmenthistory where date='" + u + "' and time='" + q + "'");
//                            System.out.println(sql);

                            try {
                                con = mysqlconnect.connectDb();
                                pst = con.prepareStatement(sql);
                                rs = pst.executeQuery();

                                if (rs.next()) {
                                    String Abcd = "C:\\dentalHouse\\report+" + h + ".pdf";
                                    h = h + 2;
                                    File file = new File(Abcd);
                                    FileOutputStream output = new FileOutputStream(file);

                                    InputStream input = rs.getBinaryStream("treatmentpdf");
                                    byte[] buffer = new byte[1024];
                                    while (input.read(buffer) > 0) {
                                        output.write(buffer);

                                    }

                                    output.close();
                                    Desktop.getDesktop().open(new java.io.File(Abcd));

                                }

                            } //            JOptionPane.showMessageDialog(null, e);
                            catch (FileNotFoundException ex) {
                                Logger.getLogger(DocControllerDash.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                                Logger.getLogger(DocControllerDash.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (SQLException ex) {
                                Logger.getLogger(DocControllerDash.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        });

                        setGraphic(button3);
                        setText(null);

                    }
                }
            };

            return cell;
        };
        ptreatment1.setCellFactory(cellFactory6);
        Callback<TableColumn<B, String>, TableCell<B, String>> cellFactory7 = (params) -> {

            final TableCell<B, String> cell = new TableCell<B, String>() {
                //override updatesItem method
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //ensure that cell is creates only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button button3 = new Button("View Report");
                        button3.setStyle("-fx-background-color:red");
                        button3.setStyle("-fx-background-radius:50");
                        button3.setStyle("-fx-text-fill:white");
                        button3.centerShapeProperty();
                        button3.setOnAction(event -> {
                            int h = 2;
                            B m = getTableView().getItems().get(getIndex());
                            String q = m.getTime();
                            String u = m.getDate();
                            String sql = String.format("select * from treatmenthistory where date='" + u + "' and time='" + q + "'");

                            try {
                                con = mysqlconnect.connectDb();
                                pst = con.prepareStatement(sql);
                                rs = pst.executeQuery();

                                if (rs.next()) {
//                 FileOutputStream fs = new FileOutputStream(rs.getString("image"));
                                    String Abcd = "C:\\dentalHouse\\report+" + h + ".pdf";
                                    h = h + 2;
                                    File file = new File(Abcd);
                                    FileOutputStream output = new FileOutputStream(file);
//            while (rs.next()) {
                                    InputStream input = rs.getBinaryStream("medicinepdf");
                                    byte[] buffer = new byte[1024];
                                    while (input.read(buffer) > 0) {
//                 input.read(buffer);
                                        output.write(buffer);

//                }
                                    }

                                    output.close();
                                    Desktop.getDesktop().open(new java.io.File(Abcd));

//                 file.canRead();
                                }
//     
//        byte requestBytes[] = Content.getBytes();
//        ByteArrayInputStream bis = new ByteArrayInputStream(requestBytes);

                            } //            JOptionPane.showMessageDialog(null, e);
                            catch (FileNotFoundException ex) {
                                Logger.getLogger(DocControllerDash.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                                Logger.getLogger(DocControllerDash.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (SQLException ex) {
                                Logger.getLogger(DocControllerDash.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        });

                        setGraphic(button3);
                        setText(null);

                    }
                }
            };

            return cell;
        };
        pmedicine1.setCellFactory(cellFactory7);

        libaryAction.setOnAction(e -> {
            try {
                Patienttable.setVisible(false);

                Appointmenttable.setVisible(false);
                getLibary();
                libary.setVisible(true);
                filterField.setVisible(false);
            } catch (Exception w) {
                System.out.println(w);
            }
        });
    }

//        System.out.println(i);
//To change body of generated methods, choose Tools | Templates.
}
