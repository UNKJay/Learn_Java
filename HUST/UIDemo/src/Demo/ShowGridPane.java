import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label ;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
public class ShowGridPane extends Application {
    public void start(Stage primaryStage){
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        pane.setHgap(5.5);   pane.setVgap(5.5);
        pane.add(new Label("“First Name:"), 0,0); //0列0行
        pane.add(new TextField(), 1, 0);  //1列0行
        pane.add(new Label("MI:"), 0, 1); //0列1行
        pane.add(new TextField(),1,1); //1列1行
        pane.add(new Label("Last Name"), 0, 2); //0列2行
        pane.add(new TextField(), 1, 2); //1列2行
        Button btnAdd = new Button("Add Name:");
        pane.add(btnAdd, 1,3);  //1列3行 （注意这时一共2列4行）
        //将Button对象在单元格水平右对齐（静态方法）
        GridPane.setHalignment(btnAdd, HPos.RIGHT);

        Scene scene = new Scene(pane);
        primaryStage.setTitle("ShcmGridPane");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
