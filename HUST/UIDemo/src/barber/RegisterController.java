package barber;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

public class RegisterController {
    @FXML
    private Button exitButton;
    @FXML
    private Button registerButton;
    @FXML
    private TextField inputUsername;
    @FXML
    private PasswordField inputPassword;
    @FXML
    private PasswordField inputPassword1;
    @FXML
    private TextField inputCharge;
    @FXML
    private Label statusLabel1;

    public void initialize(){
        inputCharge.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER){
                onRegisterClick();
            }
        });

        inputPassword.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (inputPassword.getText().length() != 8) {
                    setStatusLabel1("密码必须为8位");
                    return;
                } else {
                    setStatusLabel1("");
                }
            }
        });

        inputPassword1.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!inputPassword1.getText().equals(inputPassword.getText())) {
                    setStatusLabel1("两次输入密码不一致");
                    return;
                } else {
                    setStatusLabel1("");
                }
            }
        });


    }

    public void onRegisterClick(){

        try {
            if (inputUsername.getText().isEmpty()) {
                setStatusLabel1("请先输入姓名");
                return;
            }

            if ( inputPassword.getText().isEmpty() || inputPassword1.getText().isEmpty() ||
                    !inputPassword1.getText().equals(inputPassword.getText())) {
                setStatusLabel1("两次输入密码不一致，请重新输入");
                return;
            }

            if (!inputCharge.getText().matches("([1-9]+[0-9]*|0)(\\.[\\d]+)?")) {
                setStatusLabel1("请重新输入充值金额");
                return;
            }

            if (Double.parseDouble(inputCharge.getText()) < 100) {
                setStatusLabel1("最少需要充值100元");
                return;
            }

            String status = DBConnector.getInstance().tryRegister(inputUsername.getText(),inputPassword.getText());
            String[] strings = status.split(" ");
            status = DBConnector.getInstance().tryCharge(strings[1],Double.parseDouble(inputCharge.getText()),0);

            if (status.contains("success")) {
                JOptionPane.showMessageDialog(null, "注册成功，您的会员编号为 " + strings[1] +
                        " 请返回登录" , "理发店收银系统", JOptionPane.PLAIN_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, status, "理发店收银系统", JOptionPane.ERROR_MESSAGE);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onExitClick(){
        Stage nowStage = (Stage) exitButton.getScene().getWindow();
        nowStage.close();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root, 600, 360);
        scene.setRoot(root);
        Stage stage = new Stage();
        stage.setTitle("理发店收银系统");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * 设置出错信息，颜色为红色
     * @param warningMsg 出错提示
     */
    private void setStatusLabel1(String warningMsg) {
        statusLabel1.setText(warningMsg);
        statusLabel1.setStyle("-fx-text-fill: #ff0000;");
    }



}
