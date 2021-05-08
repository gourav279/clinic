package dental;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;

public class mysqlconnect {

    Connection conn = null;
    static String b;

    public static Connection connectDb() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dentalhouse", "root", "DO@Guru2019");

            System.out.println("Connection Established "+conn.getSchema());

            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static ObservableList<B> getB() {
        Connection conn = connectDb();
        ObservableList<B> list = FXCollections.observableArrayList();
        try {   
                String sql="select * from treatmenthistory";
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    list.add(new B( rs.getString("patientname"), rs.getString("patientmobileno"), rs.getString("date"), rs.getString("time")));
                }
        } catch (Exception e) {
            System.out.print("Exception " + e);
        }
        return list;
    }
    public static ObservableList<A> getA(){
        Connection conn = connectDb();
        ObservableList<A> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from appointment where status='appoint' or status='Check In' or status=''");
            ResultSet rs = ps.executeQuery();
//            System.out.println("Observation List"+rs.getString(1));
            while (rs.next()) {
                list.add(new A(rs.getString("patientid"),rs.getString("patientname"), rs.getString("Date"),rs.getString("patienttype"), rs.getString("time")));
            }
        } catch (Exception e) {
            System.out.print("Exception " + e);
        }
        return list;
    }

    public static ObservableList<item> getitem() {
        Connection conn = connectDb();
        ObservableList<item> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from libary as a join patient as p where a.patientid=p.patientid");
            ResultSet rs = ps.executeQuery();
//            System.out.println("Observation List"+rs.getString(1));
            while (rs.next()) {
//                System.out.println(rs.getString("name"));
                list.add(new item(rs.getString("libaryid"), rs.getString("name"), rs.getString("Number"), rs.getString("productname"), rs.getString("orderdate"), rs.getString("expectdate"), rs.getString("receivedate"),rs.getString("status")));
            }
        } catch (Exception e) {
//            System.out.print("Exception " + e);
        }
        return list;
    }
    
    public static ObservableList<Appointment> getAppointment() {
        Connection conn = connectDb();
        ObservableList<Appointment> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from appointment ");
            ResultSet rs = ps.executeQuery();
//            System.out.println("Observation List"+rs.getString(1));
            while (rs.next()) {
                list.add(new Appointment(Integer.parseInt(rs.getString("Appointmentid")), rs.getString("PatientName")));
            }
        } catch (Exception e) {
//            System.out.print("Exception " + e);
        }
        return list;
    }

    public static ObservableList<Admin> getDataAdmin() {
        Connection conn = connectDb();
        ObservableList<Admin> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from admin where Status=0");
            ResultSet rs = ps.executeQuery();
//            System.out.println("Observation List"+rs.getString(1));
            while (rs.next()) {
                list.add(new Admin(Integer.parseInt(rs.getString("Adminid")), rs.getString("Name"), rs.getString("Number"), rs.getString("Branch"), rs.getString("Email"), rs.getString("Address"),rs.getString("shift")));
            }
        } catch (Exception e) {
//            System.out.print("Exception " + e);
        }
        return list;
    }

    public static ObservableList<Doctor> getDoctor() throws SQLException {
        Connection conn = connectDb();
        ObservableList<Doctor> list = FXCollections.observableArrayList();
        try {
            if (LoginController.t.equals("superadmin")) {
                PreparedStatement ps = conn.prepareStatement("select * from doctor");
                ResultSet rs = ps.executeQuery();
//            System.out.println("Observation List"+rs.getString(1));
                while (rs.next()) {
                    list.add(new Doctor(Integer.parseInt(rs.getString("Doctorid")), rs.getString("Name"), rs.getString("Number"), rs.getString("Branch"), rs.getString("Email"), rs.getString("Address"), rs.getString("Specialist"),rs.getString("shift")));
//                    System.out.println(list);
                }
            } else if (LoginController.t.equals("admin") || LoginController.t.equals("helper") || LoginController.t.equals("receptionist") || LoginController.t.equals("accountant")) {
                Connection con1 = connectDb();
                try {
                    String sql = String.format("select * from %s where name='%s' and password='%s'", LoginController.t, LoginController.u, LoginController.p);
                    PreparedStatement ps = con1.prepareStatement(sql);
                    ResultSet rs = ps.executeQuery();
//                    System.out.println("Observation List" + rs.getString(1));
                    if (rs.next()) {
                        b = rs.getString("branch");
                    }

                } catch (Exception e) {
//                    System.out.print("Exception " + e);
                }
                String sql = String.format("select * from doctor where branch='%s'", b);
//                System.out.println(sql);

                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
//            System.out.println("Observation List"+rs.getString(1));
                while (rs.next()) {
                    list.add(new Doctor(Integer.parseInt(rs.getString("Doctorid")), rs.getString("Name"), rs.getString("Number"), rs.getString("Branch"), rs.getString("Email"), rs.getString("Address"), rs.getString("Specialist"),rs.getString("shift")));
                }
            }
        } catch (Exception e) {
//            System.out.print("Exception " + e);
        }
        return list;
    }

    public static ObservableList<Receptionist> getReceptionist() {
        Connection conn = connectDb();
        ObservableList<Receptionist> list = FXCollections.observableArrayList();
        try {
            if (LoginController.t.equals("superadmin")) {
                PreparedStatement ps = conn.prepareStatement("select * from receptionist");
                ResultSet rs = ps.executeQuery();
//            System.out.println("Observation List"+rs.getString(1));
                while (rs.next()) {
                    list.add(new Receptionist(Integer.parseInt(rs.getString("Receptionistid")), rs.getString("Name"), rs.getString("Number"), rs.getString("Branch"), rs.getString("Email"), rs.getString("Address"),rs.getString("shift")));
                }
            } else if (LoginController.t.equals("admin") || LoginController.t.equals("receptionist") || LoginController.t.equals("accountant") || LoginController.t.equals("helper")) {
                Connection con1 = connectDb();
                try {
                    String sql = String.format("select * from %s where name='%s' and password='%s'", LoginController.t, LoginController.u, LoginController.p);
                    PreparedStatement ps = con1.prepareStatement(sql);
                    ResultSet rs = ps.executeQuery();
//            System.out.println("Observation List"+rs.getString(1));
                    if (rs.next()) {
                        b = rs.getString("branch");
                    }

                } catch (Exception e) {
//                    System.out.print("Exception " + e);
                }
                String sql = String.format("select * from receptionist where branch='%s'", b);
//                System.out.println(sql);

                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    list.add(new Receptionist(Integer.parseInt(rs.getString("Receptionistid")), rs.getString("Name"), rs.getString("Number"), rs.getString("Branch"), rs.getString("Email"), rs.getString("Address"),rs.getString("shift")));
                }

            }
        } catch (Exception e) {
//            System.out.print("Exception " + e);
        }
        return list;
    }

    public static ObservableList<Accountant> getAccountant() {
        Connection conn = connectDb();
        ObservableList<Accountant> list = FXCollections.observableArrayList();
        try {
            if (LoginController.t.equals("superadmin")) {
                PreparedStatement ps = conn.prepareStatement("select * from accountant");
                ResultSet rs = ps.executeQuery();
//            System.out.println("Observation List"+rs.getString(1));
                while (rs.next()) {
                    list.add(new Accountant(Integer.parseInt(rs.getString("Accountantid")), rs.getString("Name"), rs.getString("Number"), rs.getString("Branch"), rs.getString("Email"), rs.getString("Address"),rs.getString("shift")));
                }
            } else if (LoginController.t.equals("admin") || LoginController.t.equals("receptionist") || LoginController.t.equals("accountant") || LoginController.t.equals("helper")) {
                Connection con1 = connectDb();
                try {
                    String sql = String.format("select * from %s where name='%s' and password='%s'", LoginController.t, LoginController.u, LoginController.p);
                    PreparedStatement ps = con1.prepareStatement(sql);
                    ResultSet rs = ps.executeQuery();
//            System.out.println("Observation List"+rs.getString(1));
                    if (rs.next()) {
                        b = rs.getString("branch");
                    }

                } catch (Exception e) {
//                    System.out.print("Exception " + e);
                }
                String sql = String.format("select * from accountant where branch='%s'", b);
//                System.out.println(sql);

                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    list.add(new Accountant(Integer.parseInt(rs.getString("Accountantid")), rs.getString("Name"), rs.getString("Number"), rs.getString("Branch"), rs.getString("Email"), rs.getString("Address"),rs.getString("shift")));
                }

            }
        } catch (Exception e) {
//            System.out.print("Exception " + e);
        }
        return list;
    }

    public static ObservableList<Helper> getstaff() {
        Connection conn = connectDb();
        ObservableList<Helper> list = FXCollections.observableArrayList();
        try {
            if (LoginController.t.equals("superadmin")) {
                PreparedStatement ps = conn.prepareStatement("select * from helper");
                ResultSet rs = ps.executeQuery();
           
                while (rs.next()) {
//                     System.out.println("Observation List"+rs.getString("name"));
                    list.add(new Helper(Integer.parseInt(rs.getString("helperid")), rs.getString("Name"), rs.getString("Number"), rs.getString("Branch"), rs.getString("Email"), rs.getString("Address"),rs.getString("shift")));
                }
            } else if (LoginController.t.equals("admin") || LoginController.t.equals("receptionist") || LoginController.t.equals("accountant") || LoginController.t.equals("helper")) {
                Connection con1 = connectDb();
                try {
                    String sql = String.format("select * from %s where name='%s' and password='%s'", LoginController.t, LoginController.u, LoginController.p);
                    PreparedStatement ps = con1.prepareStatement(sql);
                    ResultSet rs = ps.executeQuery();
            System.out.println("Observation List"+rs.getString(1));
                    if (rs.next()) {
                        b = rs.getString("branch");
                    }

                } catch (Exception e) {
                    System.out.print("Exception " + e);
                }
                String sql = String.format("select * from helper where branch='%s'", b);
                System.out.println(sql);

                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    list.add(new Helper(Integer.parseInt(rs.getString("helperid")), rs.getString("Name"), rs.getString("Number"), rs.getString("Branch"), rs.getString("Email"), rs.getString("Address"),rs.getString("shift")));
                }

            }
        } catch (Exception e) {
            System.out.print("Exception " + e);
        }
        return list;
    }

    public static ObservableList<Branch> getbranch() {
        Connection conn = connectDb();
        ObservableList<Branch> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from branch");
            ResultSet rs = ps.executeQuery();
//            System.out.println("Observation List"+rs.getString(1));
            while (rs.next()) {
                list.add(new Branch(Integer.parseInt(rs.getString("Branchid")), rs.getString("Name"), rs.getString("Number"), rs.getString("Email"), rs.getString("Address")));
            }
        } catch (Exception e) {
//            System.out.print("Exception " + e);
        }
        return list;
    }

    public static ObservableList<Treatment> getTreatment() {
        Connection conn = connectDb();
        ObservableList<Treatment> list = FXCollections.observableArrayList();
        try {
            System.out.println(LoginController.t);
            if (LoginController.t.equals("superadmin")) {
                PreparedStatement ps = conn.prepareStatement("select * from treatment");
                ResultSet rs = ps.executeQuery();
//            System.out.println("Observation List"+rs.getString(1));
                while (rs.next()) {
                    list.add(new Treatment(Integer.parseInt(rs.getString("Treatmentid")), rs.getString("Name"), rs.getString("Price"), rs.getString("Category"), rs.getString("Branch")));
                }
            } else if (LoginController.t.equals("admin") || LoginController.t.equals("receptionist") || LoginController.t.equals("accountant") || LoginController.t.equals("helper")) {
                Connection con1 = connectDb();
                
                String sql = String.format("select * from treatment where branch='%s'", LoginController.b);
//                System.out.println(sql);

                PreparedStatement ps = con1.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    System.out.println(rs.getString("treatmentid"));
                    list.add(new Treatment(Integer.parseInt(rs.getString("treatmentid")), rs.getString("Name"), rs.getString("Price"), rs.getString("Category"), rs.getString("Branch")));
                }

            }

        } catch (Exception e) {
//            System.out.print("Exception " + e);
        }
        return list;
    }

    public static ObservableList<Medicine> getMedicine() {
        Connection conn = connectDb();
        ObservableList<Medicine> list = FXCollections.observableArrayList();
        try {
            if (LoginController.t.equals("superadmin")) {
                PreparedStatement ps = conn.prepareStatement("select * from medicine");
                ResultSet rs = ps.executeQuery();
//            System.out.println("Observation List"+rs.getString(1));
                while (rs.next()) {
                    list.add(new Medicine(Integer.parseInt(rs.getString("Medicineid")), rs.getString("Name"), rs.getString("Brand"), rs.getString("Mg"), rs.getString("Distributer")));
                }
            } else if (LoginController.t.equals("admin") || LoginController.t.equals("receptionist") || LoginController.t.equals("accountant") || LoginController.t.equals("helper")) {
                Connection con1 = connectDb();
                try {
                    String sql = String.format("select * from %s where name='%s' and password='%s'", LoginController.t, LoginController.u, LoginController.p);
                    PreparedStatement ps = con1.prepareStatement(sql);
                    ResultSet rs = ps.executeQuery();
//            System.out.println("Observation List"+rs.getString(1));
                    if (rs.next()) {
                        b = rs.getString("branch");
                    }

                } catch (Exception e) {
//                    System.out.print("Exception " + e);
                }
                String sql = String.format("select * from medicine where branch='%s'", b);
//                System.out.println(sql);

                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    list.add(new Medicine(Integer.parseInt(rs.getString("Medicineid")), rs.getString("Name"), rs.getString("Brand"), rs.getString("Mg"), rs.getString("Distributer")));
                }

            }
        } catch (Exception e) {
//            System.out.print("Exception " + e);
        }
        return list;
    }

    public static ObservableList<Inventory> getInventory() {
        Connection conn = connectDb();
        ObservableList<Inventory> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from inventory");
            ResultSet rs = ps.executeQuery();
//            System.out.println("Observation List"+rs.getString(1));
            while (rs.next()) {
                list.add(new Inventory(Integer.parseInt(rs.getString("Inventoryid")), rs.getString("Name"), rs.getString("Brand"), rs.getString("Price"), rs.getString("Distributer"), rs.getString("QTY")));
            }
        } catch (Exception e) {
//            System.out.print("Exception " + e);
        }
        return list;
    }
    
        public static ObservableList<payment> getpayment() {
        Connection conn = connectDb();
        ObservableList<payment> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from treatmenthistory where paymentstatus='Pending'");
            ResultSet rs = ps.executeQuery();
//            System.out.println("Observation List"+rs.getString(1));
            while (rs.next()) {
                list.add(new payment(rs.getString("patientid"),rs.getString("patientname"), rs.getString("patientmobileno"), rs.getString("date"), rs.getString("tamount"), rs.getString("paymentstatus")));
            }
        } catch (Exception e) {
//            System.out.print("Exception " + e);
        }
        return list;
    }

    public static ObservableList<Apphistory> getapphistory() {
        Connection conn = connectDb();
        ObservableList<Apphistory> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from appointment as a join patient as p where a.patientid=p.patientid");
            ResultSet rs = ps.executeQuery();
//            System.out.println("Observation List"+rs.getString(1));
            while (rs.next()) {
//                System.out.println(rs.getString("name"));
//                String s="UH";
                list.add(new Apphistory(rs.getString("Patientid"),rs.getString("name"), rs.getString("Number"), rs.getString("date"), rs.getString("patienttype"), rs.getString("status")));
            }
        } catch (Exception e) {
//            System.out.print("Exception " + e);
        }
        return list;
    }
            
//    public static ObservableList<Patient> getPatient() {
//        Connection conn = connectDb();
//        ObservableList<Patient> list = FXCollections.observableArrayList();
//        try {
//            if (LoginController.t.equals("superadmin")) {
//                PreparedStatement ps = conn.prepareStatement("select * from patient");
//                ResultSet rs = ps.executeQuery();
//            System.out.println("Observation List"+rs.getString(1));
//                while (rs.next()) {
//                    list.add(new Patient(Integer.parseInt(rs.getString("Patientid")), rs.getString("Name"), rs.getString("Number"), rs.getString("Branch"), rs.getString("Email"), rs.getString("Address")));
//                }
//            } else if (LoginController.t.equals("admin") || LoginController.t.equals("receptionist") || LoginController.t.equals("accountant") || LoginController.t.equals("helper")) {
//                Connection con1 = connectDb();
//                try {
//                    String sql = String.format("select * from %s where name='%s' and password='%s'", LoginController.t, LoginController.u, LoginController.p);
//                    PreparedStatement ps = con1.prepareStatement(sql);
//                    ResultSet rs = ps.executeQuery();
//            System.out.println("Observation List"+rs.getString(1));
//                    if (rs.next()) {
//                        b = rs.getString("branch");
//                        System.out.println(b);
//                    }
//
//                } catch (Exception e) {
//                    System.out.print("Exception " + e);
//                }
//                String sql = String.format("select * from patient where branch='%s'", b);
//                System.out.println(sql);
//
//                PreparedStatement ps = conn.prepareStatement(sql);
//                ResultSet rs = ps.executeQuery();
//                while (rs.next()) {
//                    list.add(new Patient(Integer.parseInt(rs.getString("Patientid")), rs.getString("Name"), rs.getString("Number"), rs.getString("Branch"), rs.getString("Email"), rs.getString("Address")));
//                }
//            } 
//        } catch (Exception e) {
//            System.out.print("Exception " + e);
//        }
//        return list;
//    }
//    public static ObservableList<Patient> getPatient() throws SQLException {
//        Connection conn = connectDb();
//        ObservableList<Patient> list = FXCollections.observableArrayList();
//        try {
//            if (LoginController.t.equals("superadmin")) {
//                PreparedStatement ps = conn.prepareStatement("select * from patient");
//                ResultSet rs = ps.executeQuery();
////            System.out.println("Observation List"+rs.getString(1));
//                while (rs.next()) {
//                    list.add(new Patient(Integer.parseInt(rs.getString("Patientid")), rs.getString("Name"), rs.getString("Number"), rs.getString("Branch"), rs.getString("Email"), rs.getString("Address")));
////                    System.out.println(list);
//                }
//            } else if (LoginController.t.equals("admin") || LoginController.t.equals("helper") || LoginController.t.equals("receptionist") || LoginController.t.equals("accountant")) {
//                Connection con1 = connectDb();
//                try {
//                    String sql = String.format("select * from %s where name='%s' and password='%s'", LoginController.t, LoginController.u, LoginController.p);
//                    PreparedStatement ps = con1.prepareStatement(sql);
//                    ResultSet rs = ps.executeQuery();
////                    System.out.println("Observation List" + rs.getString(1));
//                    if (rs.next()) {
//                        b = rs.getString("branch");
//                    }
//
//                } catch (Exception e) {
////                    System.out.print("Exception " + e);
//                }
//                String sql = String.format("select * from patient where branch='%s'", b);
////                System.out.println(sql);
//
//                PreparedStatement ps = conn.prepareStatement(sql);
//                ResultSet rs = ps.executeQuery();
////            System.out.println("Observation List"+rs.getString(1));
//                while (rs.next()) {
//                    list.add(new Patient(Integer.parseInt(rs.getString("Patientid")), rs.getString("Name"), rs.getString("Number"), rs.getString("Branch"), rs.getString("Email"), rs.getString("Address")));
//                }
//            }
//        } catch (Exception e) {
////            System.out.print("Exception " + e);
//        }
//        return list;
//    }
    
        public static ObservableList<Patient> getPatient() throws SQLException {
        Connection conn = connectDb();
        ObservableList<Patient> list = FXCollections.observableArrayList();
        try {
            if (LoginController.t.equals("superadmin")) {
                PreparedStatement ps = conn.prepareStatement("select * from patient");
                ResultSet rs = ps.executeQuery();
//            System.out.println("Observation List"+rs.getString(1));
                while (rs.next()) {
                    try{
//                    String s="UH";
//                    String r="RG";
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
                    LocalDateTime now = LocalDateTime.now(); 
                    list.add(new Patient(Integer.parseInt(rs.getString("Patientid")), rs.getString("Name"), rs.getString("Number"), rs.getString("Branch"), rs.getString("Email"), rs.getString("Address"),rs.getString("Patientid"), rs.getString("Age"), rs.getString("Gender"),rs.getDate("Dateofbirth"), rs.getString("RID")));
//                    System.out.println(list);
                    }catch(Exception e){
                        System.out.println(e);
                    }
                }
            } else if (LoginController.t.equals("admin") || LoginController.t.equals("helper") || LoginController.t.equals("receptionist") || LoginController.t.equals("accountant")) {
                Connection con1 = connectDb();
                try {
                    String sql = String.format("select * from %s where name='%s' and password='%s'", LoginController.t, LoginController.u, LoginController.p);
                    PreparedStatement ps = con1.prepareStatement(sql);
                    ResultSet rs = ps.executeQuery();
//                    System.out.println("Observation List" + rs.getString(1));
                    if (rs.next()) {
                        b = rs.getString("branch");
                    }

                } catch (Exception e) {
//                    System.out.print("Exception " + e);
                }
                String sql = String.format("select * from patient where branch='%s'", b);
//                System.out.println(sql);

                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
//            System.out.println("Observation List"+rs.getString(1));
                while (rs.next()) {
                    String s="UH_";
                    String r="RG_";
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
                    LocalDateTime now = LocalDateTime.now();  
                    list.add(new Patient(Integer.parseInt(rs.getString("Patientid")), rs.getString("Name"), rs.getString("Number"), rs.getString("Branch"), rs.getString("Email"), rs.getString("Address"),s+rs.getString("Patientid"), rs.getString("Age"), rs.getString("Gender"),rs.getDate("Dateofbirth"),rs.getString(r+now+"Patientid")));
                }
            }
        } catch (Exception e) {
            System.out.print("Exception " + e);
        }
        return list;
    }
    public static ObservableList<Doctor> getShedule() {
        Connection conn = connectDb();
        ObservableList<Doctor> list = FXCollections.observableArrayList();
        try {   
                String sql="select * from doctor where branch='"+LoginController.b+"'";
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
//            System.out.println("Observation List"+rs.getString(1));
                while (rs.next()) {
                    list.add(new Doctor(rs.getInt("doctorid"),rs.getString("name"), rs.getString("number"),rs.getString("shift")));
                }
        } catch (Exception e) {
//            System.out.print("Exception " + e);
        }
        return list;
    }
}
