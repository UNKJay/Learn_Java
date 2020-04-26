package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Circle circle = new Circle(100,100,50);

        circle.setStroke(Color.BLACK) ;
        circle.setFill (Color .WHITE) ;
        Pane pane = new Pane() ;
        pane.getChildren().add(circle) ;
        circle.centerXProperty( ).bind(pane.widthProperty( ).divide(2)); // widthProperty被绑定
        circle.centerYProperty( ).bind(pane.heightProperty( ).divide(2)); // heightProperty被绑定
        primaryStage.setTitle("ShowCircle");
        primaryStage.setScene(new Scene(pane , 200, 200));
        primaryStage.show() ;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
