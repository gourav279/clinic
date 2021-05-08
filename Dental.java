/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dental;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author doagu
 */
public class Dental extends Application {

    public static String findExecutableOnPath(String name) {
        for (String dirname : System.getenv("PATH").split(File.pathSeparator)) {
            File file = new File(dirname, name);
            if (file.isFile() && file.canExecute()) {
                return file.getAbsolutePath();
            }

        }
        throw new AssertionError("should have found the executable");
    }

    @Override
    public void start(Stage stage) throws Exception {
//        Process process = Runtime.getRuntime().exec("where notepad.exe");
//        try (BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
//            File exePath = new File(in.readLine());
//            System.out.println(exePath.getParent());
//            System.out.println(new InputStreamReader(process.getInputStream()));
//        } 
//        System.out.println(System.getenv("PATH"));
//        File file=new File(Dental.class.getProtectionDomain().getCodeSource().getLocation().toURI());
//        System.out.println(file);
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
