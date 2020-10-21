package barber;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;


public class LoginController {
    @FXML
    private Button loginButton;
    @FXML
    private TextField inputUsername;
    @FXML
    private PasswordField inputPassword;
    @FXML
    public ChoiceBox<String> loginType;

    String user = "会员"; //默认用户为患者

    /* initialize方法，这个方法则会自动在构造函数之后调用
    * 使用了匿名函数、lambda表达式
    * 此处将按下回车键等同于点击loginButton
    */
    @FXML
    public void initialize(){
        inputPassword.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER){
                onLoginClick();
            }
        });

        loginType.getSelectionModel().selectedIndexProperty()
                .addListener(new ChangeListener<Number>() {
                    public void changed(ObservableValue ov, Number value, Number new_value) {
                        if (new_value.intValue() == 0) {
                            inputUsername.setText("000000");
                            inputPassword.setText("12345678");
                        } else if (new_value.intValue() == 3) {
                            inputUsername.setText("000000");
                        } else {
                            inputUsername.setText("");
                            inputPassword.setText("");
                        }
                    }
                });
    }

    public void onLoginClick(){
        user = loginType.getValue();

        try {
            if(tryLogin()){
                String nextScene = "Consume.fxml";         //下一切换场景
                String title = "理发店收银系统";

                if(user.equals("店员") || user.equals("店长")) {                        //店员成功登录
                    WorkerController.setWorkerId(inputUsername.getText());
                    WorkerController.setWorkerName(DBConnector.getInstance().getWorkerName(inputUsername.getText()));
                    nextScene = "Worker.fxml";
                    title = "店员系统";
                } else if (user.equals("会员") || user.equals("非会员")){               //会员成功登录
                    ConsumeController.setMemberId(inputUsername.getText().trim());
                    ResultSet resultSet = DBConnector.getInstance().getMemberInfo(inputUsername.getText().trim());
                    while (resultSet.next()){
                        ConsumeController.setMemberName(resultSet.getString("HYMC"));
                        ConsumeController.setMemberYCYE(resultSet.getDouble("YCJE"));
                    }
                }

                Stage nowStage = (Stage) loginButton.getScene().getWindow();
                nowStage.close();
                Parent root = FXMLLoader.load(getClass().getResource(nextScene));
                Scene scene = new Scene(root, 620, 400);
                scene.setRoot(root);
                Stage stage = new Stage();
                stage.setTitle(title);
                stage.setScene(scene);
                stage.show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onRegisterClick() {
        String nextScene = "Register.fxml";         //下一切换场景
        String title = "终于等到您";


        Stage nowStage = (Stage) loginButton.getScene().getWindow();
        nowStage.close();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(nextScene));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root, 620, 400);
        scene.setRoot(root);
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * 登录
     * @return  登录是否成功
     */
    public boolean tryLogin(){

        //检查是否输入姓名和密码
        if(inputUsername.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("姓名为空！");
            alert.showAndWait();
            return false;
        }

        if(inputPassword.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("密码为空！");
            alert.showAndWait();
            return false;
        }

        //去除多余的空格
        inputUsername.setText(inputUsername.getText().trim());
        inputPassword.setText(inputPassword.getText().trim());

        ResultSet result = null;
        if(user.equals("会员")){
            result = DBConnector.getInstance().getMemberInfo(inputUsername.getText());
        }
        else{
            result = DBConnector.getInstance().getWorkerInfo(inputUsername.getText());
        }

        if(result == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("数据库读取错误！");
            alert.showAndWait();
        }
        try {
            assert result != null;
            if(!result.next()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);       //提示信息
                alert.setHeaderText("用户不存在！");
                alert.showAndWait();
                return false;
            }
            else if(!result.getString("DLKL").equals(inputPassword.getText())){ //登录口令不对
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("密码错误！");
                alert.showAndWait();
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        //登录成功
        return true;
    }
}
