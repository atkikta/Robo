package application;

import java.io.IOException;

import javafx.fxml.FXMLLoader;

public class Test {
    public static void main(String[] args) throws IOException {     
        Robo robo = FXMLLoader.load(Test.class.getResource("/application/Robo.fxml"));
        System.out.println(robo.getWidth()+" "+robo.getHeight());
    }
}
