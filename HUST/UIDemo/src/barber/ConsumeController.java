package barber;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import javax.swing.*;
import java.io.IOException;

public class ConsumeController {


    @FXML
    private ComboBox<String> inputType2;        //发型师类型
    @FXML
    private ComboBox<String> inputWorker1;      //学徒姓名
    @FXML
    private ComboBox<String> inputWorker2;      //发型师姓名
    @FXML
    private TextField feeLabel;  //费用
    @FXML
    private Label statusLabel;  //状态信息
    @FXML
    private TextField refundLabel;  //找零
    @FXML
    private Label welcomeLabel;  //欢迎label
    @FXML
    private TextField inputMoney;  //输入的交款金额
    @FXML
    private Button payButton;  //挂号
    @FXML
    private Button exitButton;  //退出按钮
    @FXML
    private ToggleGroup type;
    @FXML
    private RadioButton type0;
    @FXML
    private RadioButton type1;
    @FXML
    private RadioButton type2;
    @FXML
    private Label worker2Label;
    @FXML
    private Label worker2Name;
    @FXML
    private AnchorPane pane;

    private static String memberName;  //会员姓名
    private static String memberId;     //会员编号
    private static double memberYCYE;  //余额

    private static double barberFee;  //理发费用
    private static double chargePay;  //充值金额
    private String xtGZ = "000001";     //学徒工种

//    private int lastInputDepartment = -1;
    private int lastInputType2 = -1;
    private int lastInputWorker1 = -1;
    private int lastInputRegister = -1;
    private int consumeType;        //消费类型 0--洗头  1--洗剪吹


    @FXML
    void initialize() {
        setWelcomeLabel();

        type0.setUserData("0");
        type1.setUserData("1");
        type2.setUserData("2");
        type1.setSelected(true);
        type1.requestFocus();

        type.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
                if (type.getSelectedToggle().getUserData().toString() == "0") {
                    inputWorker1.setEditable(true);
                    inputWorker1.setDisable(false);
                    inputType2.setEditable(false);
                    inputType2.setDisable(true);
                    inputWorker2.setEditable(false);
                    inputWorker2.setDisable(true);
                    inputMoney.setEditable(false);
                    inputMoney.setDisable(true);
                    payButton.setText("付款");
                } else if (type.getSelectedToggle().getUserData().toString() == "1") {
                    inputWorker1.setEditable(true);
                    inputWorker1.setDisable(false);
                    inputType2.setEditable(true);
                    inputType2.setDisable(false);
                    inputWorker2.setEditable(true);
                    inputWorker2.setDisable(false);
                    inputMoney.setEditable(false);
                    inputMoney.setDisable(true);
                    payButton.setText("付款");
                } else if (type.getSelectedToggle().getUserData().toString() == "2") {
                    inputWorker1.setEditable(false);
                    inputWorker1.setDisable(true);
                    inputType2.setEditable(false);
                    inputType2.setDisable(true);
                    inputWorker2.setEditable(false);
                    inputWorker2.setDisable(true);
                    inputMoney.setEditable(true);
                    inputMoney.setDisable(false);
                    payButton.setText("充值");
                }
            }
        });

        inputWorker1.addEventHandler(ComboBox.ON_SHOWING, event -> {
            if (!inputWorker1.getEditor().getText().isEmpty()) {
                return;
            }
            setInputWorker1();
            event.consume();
        });

        inputWorker1.getEditor().setOnKeyReleased(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER || keyEvent.getCode() == KeyCode.UP
                    || keyEvent.getCode() == KeyCode.DOWN){
                return;
            }
            setInputWorker1();
            if (!inputWorker1.isShowing())
                inputWorker1.show();
            else {
                inputWorker1.hide();
                inputWorker1.show();
            }
        });

        inputWorker1.addEventHandler(ComboBox.ON_HIDDEN, event -> {
            int index;
            if ((index = inputWorker1.getSelectionModel().getSelectedIndex()) != lastInputWorker1) {
                lastInputWorker1 = index;
                if (type0.isSelected()) {
                    setFeeLabel();
                    checkYCJE();
                } else if (type1.isSelected()) {
                    setInputType2();
                }
            }
            event.consume();
        });

        inputType2.getEditor().setOnKeyReleased(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER || keyEvent.getCode() == KeyCode.UP
                    || keyEvent.getCode() == KeyCode.DOWN) {
                return;
            }
            setInputType2();
            if (!inputType2.isShowing())
                inputType2.show();
            else {
                inputType2.hide();
                inputType2.show();
            }
        });

        inputType2.addEventHandler(ComboBox.ON_SHOWING, event -> {
            if (!inputType2.getEditor().getText().isEmpty())
                return;
            setInputType2();
            event.consume();
        });

        inputType2.addEventHandler(ComboBox.ON_HIDDEN, event -> {
            int index;
            if ((index = inputType2.getSelectionModel().getSelectedIndex()) != lastInputType2) {
                lastInputType2 = index;
                inputWorker2.getItems().clear();
                setInputWorker2();
            }
            event.consume();
        });


        inputWorker2.getEditor().setOnKeyReleased(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER || keyEvent.getCode() == KeyCode.UP
                    || keyEvent.getCode() == KeyCode.DOWN){
                return;
            }
            setInputWorker2();
            if (!inputWorker2.isShowing())
                inputWorker2.show();
            else {
                inputWorker2.hide();
                inputWorker2.show();
            }
        });
        inputWorker2.addEventHandler(ComboBox.ON_SHOWING, event -> {
            if (!inputWorker2.getEditor().getText().isEmpty()){
                return;
            }
            setInputWorker2();
            event.consume();
        });

        inputWorker2.addEventHandler(ComboBox.ON_HIDDEN, event -> {
            int index;
            if ((index = inputWorker2.getSelectionModel().getSelectedIndex()) != lastInputRegister) {
                lastInputRegister = index;
            }
            if (index != -1) {
                String registerInformation = inputWorker2.getEditor().getText();
                setFeeLabel();
                checkYCJE();
            }
            event.consume();
        });

        inputMoney.setOnKeyReleased(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER || keyEvent.getCode() == KeyCode.BACK_SPACE)
                return;
            String s = inputMoney.getText().trim();
            if (!s.matches("([1-9]+[0-9]*|0)(\\.[\\d]+)?")) {
                setStatusLabel("输入非法，请重新输入");
                return;
            }
            setStatusLabel("");
            chargePay = Double.parseDouble(s);
        });

        exitButton.setOnKeyReleased(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                try {
                    onExitButtonClick();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        payButton.setOnKeyReleased(keyEvent -> {});
    }

    /**
     * 退出按钮点击事件
     * @throws IOException 异常
     */
    @FXML
    void onExitButtonClick() throws IOException {
        Stage nowStage = (Stage) exitButton.getScene().getWindow();
        nowStage.close();
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(root, 600, 360);
        scene.setRoot(root);
        Stage stage = new Stage();
        stage.setTitle("理发店收银系统");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * 设置复选框学徒姓名
     */
    private void setInputWorker1() {
        String pinyin = inputWorker1.getEditor().getText();
        if (pinyin != null) {
            inputWorker1.getItems().clear();
            inputWorker1.getItems().addAll(DBConnector.getInstance().getXTName(pinyin));
        } else {
            inputWorker1.getItems().clear();
            inputWorker1.getItems().addAll(DBConnector.getInstance().getXTName(null));
        }
    }

    /**
     * 设置复选框选择发型师类型
     */
    private void setInputType2() {
        String pinyin = inputType2.getEditor().getText();
        if (pinyin != null) {
            inputType2.getItems().clear();
            inputType2.getItems().addAll(DBConnector.getInstance().getBarberType(pinyin));
        } else {
            inputType2.getItems().clear();
            inputType2.getItems().addAll(DBConnector.getInstance().getBarberType(null));
        }
    }

    /**
     * 设置复选框选择发型师
     */
    private void setInputWorker2() {
        String pinyin = inputWorker2.getEditor().getText();
        String barberType = inputType2.getEditor().getText();

        String[] strings = barberType.split(" ");
        barberType = strings[0];

        if (barberType.isEmpty()) {
            setStatusLabel("请先选择发型师类型");
            return;
        }

        if (pinyin != null) {
            inputWorker2.getItems().clear();
            inputWorker2.getItems().addAll(DBConnector.getInstance().getBarberName(barberType, pinyin));
        } else {
            inputWorker2.getItems().clear();
            inputWorker2.getItems().addAll(DBConnector.getInstance().getBarberName(barberType, null));
        }
    }

    /**
     * 检查预存金额是否够付款
     */
    private void checkYCJE() {
        if (memberYCYE < barberFee) {
            setStatusLabel("余额不足,请先选择充值");
            inputMoney.setText("");
        } else {
            statusLabel.setText("余额充足，点击付款按钮进行付款");
            statusLabel.setStyle("-fx-text-fill: #000000;");
        }
    }

    /**
     * 付款按钮点击事件
     */
    @FXML
    void onPayClick() {
        if (type0.isSelected()) {               //选择洗头
            String xt = inputWorker1.getEditor().getText();
            if (xt.isEmpty()) {
                setStatusLabel("请先选择学徒姓名");
                return;
            }

            if (memberYCYE < barberFee ) {      //余额不足
                setStatusLabel("余额不足，请先选择充值");
                inputMoney.setText("");
                return;
            }

            String[] strings = xt.split(" ");
            String DYBH = strings[0];               //DYBH

            pane.setDisable(true);

            String status = DBConnector.getInstance().tryPay(xtGZ, DYBH, memberId, barberFee, memberYCYE);

            if (status.contains("success")) {
                setWelcomeLabel();
                JOptionPane.showMessageDialog(null, "结算成功!\t当前余额：" + memberYCYE , "理发店收银系统", JOptionPane.PLAIN_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, status, "理发店收银系统", JOptionPane.ERROR_MESSAGE);
            }
            pane.setDisable(false);
        } else if (type1.isSelected()) {            //洗剪吹
            String xt = inputWorker1.getEditor().getText();
            if (xt.isEmpty()) {
                setStatusLabel("请先选择学徒姓名");
                return;
            }
            String[] strings = xt.split(" ");
            String XTDYBH = strings[0];               //学徒编号

            String fxsType = inputType2.getEditor().getText();
            if (fxsType.isEmpty()) {
                setStatusLabel("请先选择发型师类型");
                return;
            }
            strings = fxsType.split(" ");
            fxsType = strings[0];

            String fxsBH = inputWorker2.getEditor().getText();
            if (fxsBH.isEmpty()) {
                setStatusLabel("请先选择发型师");
                return;
            }
            strings = fxsBH.split(" ");
            fxsBH = strings[0];

            if (memberYCYE < barberFee ) {      //余额不足
                setStatusLabel("余额不足，请先选择充值");
                inputMoney.setText("");
                return;
            }

            pane.setDisable(true);

            double xtPay = DBConnector.getInstance().getPay(xtGZ);

            double barberPay = DBConnector.getInstance().getPay(fxsType);

            String status1 = DBConnector.getInstance().tryPay(xtGZ, XTDYBH, memberId, xtPay * (isMember()?0.8:1), memberYCYE);
            String status2 = DBConnector.getInstance().tryPay(fxsType, fxsBH, memberId, barberPay * (isMember()?0.8:1), memberYCYE);

            if (status1.contains("success") && status2.contains("success")) {
                setWelcomeLabel();
                JOptionPane.showMessageDialog(null, "结算成功!\t当前余额：" + memberYCYE , "理发店收银系统", JOptionPane.PLAIN_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, status1 + " " + status2, "理发店收银系统", JOptionPane.ERROR_MESSAGE);
            }
            pane.setDisable(false);
        } else if (type2.isSelected()) {        //充值

            if (!inputMoney.isDisable() && !inputMoney.getText().isEmpty()) {
                if (!inputMoney.getText().matches("([1-9]+[0-9]*|0)(\\.[\\d]+)?")) {
                    setStatusLabel("输入非法，请重新输入充值金额");
                    return;
                }
                chargePay = Double.parseDouble(inputMoney.getText());
            } else {
                chargePay = 0;
            }
            pane.setDisable(true);
            String status = DBConnector.getInstance().tryCharge(memberId, chargePay, memberYCYE);
            if (status.contains("success")) {
                setWelcomeLabel();
                JOptionPane.showMessageDialog(null, "充值成功!\t当前余额：" + memberYCYE , "理发店收银系统", JOptionPane.PLAIN_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, status, "理发店收银系统", JOptionPane.ERROR_MESSAGE);
            }
            pane.setDisable(false);
        }
    }

    /**
     * 是否为会员
     * @return true or false
     */
    private boolean isMember() {
        if (memberName.equals("非会员")) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 设置出错信息，颜色为红色
     * @param warningMsg 出错提示
     */
    private void setStatusLabel(String warningMsg) {
        statusLabel.setText(warningMsg);
        statusLabel.setStyle("-fx-text-fill: #ff0000;");
    }

    /**
     * 设置显示信息
     */
    private void setWelcomeLabel() {
        welcomeLabel.setText(memberName + " 的账户余额:" + memberYCYE + "元");
    }

    /**
     * 设置应付款信息
     */
    private void setFeeLabel() {
        if (type1.isSelected()) {       //两项费用


            double xtPay = DBConnector.getInstance().getPay(xtGZ);

            String barberGZ = inputType2.getEditor().getText();
            String[] strings = barberGZ.split(" ");
            barberGZ = strings[0];

            double barberPay = DBConnector.getInstance().getPay(barberGZ);
            double totalPay = (xtPay + barberPay) * (isMember()?0.8:1);
            feeLabel.setText( totalPay + " 元");
            barberFee = totalPay;

        } else if (type0.isSelected()) {                        //洗头费用
            double xtPay = DBConnector.getInstance().getPay(xtGZ);

            double totalPay = xtPay * (isMember()?0.8:1);
            System.out.println(totalPay);
            feeLabel.setText( totalPay + " 元");
            barberFee = totalPay;
        }
    }

    public static String getMemberName() {
        return memberName;
    }

    public static void setMemberName(String memberName) {
        ConsumeController.memberName = memberName;
    }

    public static String getMemberId() {
        return memberId;
    }

    public static void setMemberId(String memberId) {
        ConsumeController.memberId = memberId;
    }

    public static void setMemberYCYE(double memberYCYE) {
        ConsumeController.memberYCYE = memberYCYE;
    }

    public static double getMemberYCYE() {
        return memberYCYE;
    }
}
