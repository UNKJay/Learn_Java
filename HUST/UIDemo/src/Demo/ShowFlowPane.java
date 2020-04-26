package Demo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class ShowFlowPane extends Application {
    public void start(Stage primaryStage) {
        FlowPane pane = new FlowPane() ;
        pane.setPadding(new Insets(11, 12, 13, 14));
        pane.setHgap(5);   pane.setVgap(5);
        pane.getChildren().addAll(new Label("First Name:"),
                new TextField(), new Label("MI:"));   //向面板添加三个元素
        TextField tfi = new TextField();
        tfi.setPrefColumnCount(1) ;
        pane.getChildren( ).addAll(tfi,
                new Label ("Last Name:") ,new TextField()); //继续添加三个元素
        Scene scene = new Scene(pane,200, 250);
        primaryStage.setTitle("ShowFlowPane");
        primaryStage.setScene(scene);  primaryStage.show();
    }
}
