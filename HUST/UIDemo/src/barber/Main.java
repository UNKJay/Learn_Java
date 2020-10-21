package barber;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        DBConnector.getInstance().connectDB("localhost",3306,"databasedesign",
                "pkj", "123456");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root = loader.load();
        stage.setTitle("理发店收银系统");
        Scene scene = new Scene(root, 600, 360);
        stage.setScene(scene);
        stage.show();
    }
}
