package hospital;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

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

    String user = "患者"; //默认用户为患者

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
    }

    public void onLoginClick(){
        if(loginType.getValue().equals("医生")) //若在下拉栏选择医生，则用户切换为医生
            user = "医生";
        try{
            if(tryLogin()){
                String nextScene = "Consume.fxml";         //下一切换场景
                String title = "医院挂号系统";
                if(user.equals("医生")){ //医生成功登录
                    DoctorController.setDoctorId(inputUsername.getText());
                    DoctorController.setDoctorName(DBConnector.getInstance().getDoctorName(inputUsername.getText()));
                    nextScene = "Worker.fxml";
                    title = "医生系统";
                }
                else{ //患者成功登录
                    RegisterController.setPatientId(inputUsername.getText().trim());
                    ResultSet resultSet = DBConnector.getInstance().getPatientInfo(inputUsername.getText().trim());
                    while (resultSet.next()){
                        RegisterController.setPatientName(resultSet.getString("BRMC"));
                        RegisterController.setPatientYCYE(resultSet.getDouble("YCJE"));
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
        if(user.equals("患者")){
            result = DBConnector.getInstance().getPatientInfo(inputUsername.getText());
        }
        else{
            result = DBConnector.getInstance().getDoctorInfo(inputUsername.getText());
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
        DBConnector.getInstance().updateDLRQ(user, inputUsername.getText());// 更新登录事件
        return true;
    }
}
