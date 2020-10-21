package barber;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class WorkerController {

    /**
     * 消费内部类，包括消费编号，会员名称，消费日期，获取提成
     */
    static class Consume {
        public StringProperty consumeNumber;
        public StringProperty memberName;
        public StringProperty consumeDate;
        public StringProperty bonus;

        public Consume(String consumeNumber, String memberName, String consumeDate, double bonus) {
            this.consumeNumber = new SimpleStringProperty(consumeNumber);
            this.memberName = new SimpleStringProperty(memberName);
            this.consumeDate = new SimpleStringProperty(consumeDate);
            this.bonus = new SimpleStringProperty(Double.toString(bonus));
        }
    }

    /**
     * 收入列表内部类，包含工种编号，店员编号，店员名称，号种类别，挂号人次，收入合计
     */
    static class Income {
        public StringProperty gzName;
        public StringProperty DYBH;
        public StringProperty DYMC;
        public StringProperty incomeSum;

        public Income(String gzName, String DYBH, String DYMC, double income){
            this.gzName = new SimpleStringProperty(gzName);
            this.DYBH = new SimpleStringProperty(DYBH);
            this.DYMC = new SimpleStringProperty(DYMC);
            this.incomeSum = new SimpleStringProperty(String.format("%.2f",income));
        }
    }

    @FXML
    private Label welcomeLabel;
    @FXML
    private Button exitButton;
    @FXML
    private Label startLabel;
    @FXML
    private Label startTimeLabel;
    @FXML
    private Label endLabel;
    @FXML
    private Label endTimeLabel;
    @FXML
    private DatePicker pickDateStart;
    @FXML
    private DatePicker pickDateEnd;
    @FXML
    private TabPane mainPane;  //外层tab

    @FXML
    private Tab consumeTab;  //消费列表页
    @FXML
    private TableView<Consume> consumeTable;  //病人列表
    @FXML
    private TableColumn<Consume,String> consumeNumber;  //单号
    @FXML
    private TableColumn<Consume,String> memberName;  //会员名称
    @FXML
    private TableColumn<Consume,String> consumeDate;  //挂号时间
    @FXML
    private TableColumn<Consume,String> bonus;  //提成

    @FXML
    private Tab incomeTab;  //收入列表页
    @FXML
    private TableView<Income> incomeTable;  //收入列表
    @FXML
    private TableColumn<Income,String> gzName; //工种名
    @FXML
    private TableColumn<Income,String> DYBH;  //店员编号
    @FXML
    private TableColumn<Income,String> DYMC;  //店员名称
    @FXML
    private TableColumn<Income,String> incomeSum;  //收入总计

    private static String workerId;  //医生编号
    private static String workerName;  //医生名称

    private ObservableList<Consume> consumeList = FXCollections.observableArrayList();  //病人列表
    private ObservableList<Income> incomeList = FXCollections.observableArrayList();  //收入列表

    @FXML
    void initialize(){
        if (workerId.equals(DBConnector.getInstance().getBossID())) {       //店长查看界面
            consumeTab.setText("充值记录");
            bonus.setText("充值金额");
            incomeTab.setDisable(false);
            incomeTable.setVisible(true);
        } else {                                //店员查看界面
            consumeTab.setText("消费记录");
            bonus.setText("提成");
            incomeTab.setDisable(true);
            incomeTable.setVisible(false);
        }


        welcomeLabel.setText(workerName + "您好");
        setLabelVisible(false);
        pickDateStart.setValue(LocalDate.now());
        pickDateEnd.setValue(LocalDate.now());
        setConsumeInfo();
        setIncomeInfo();

        consumeTab.setOnSelectionChanged(event -> {
            setConsumeInfo();
            setLabelVisible(false);
            pickDateStart.setValue(LocalDate.now());
            pickDateEnd.setValue(LocalDate.now());
        });
        incomeTab.setOnSelectionChanged(event -> {
            setIncomeInfo();
            setLabelVisible(false);
            pickDateStart.setValue(LocalDate.now());
            pickDateEnd.setValue(LocalDate.now());
        });
    }

    /**
     * 设置消费列表信息
     */
    private void setConsumeInfo(){
        consumeNumber.setCellValueFactory((TableColumn.CellDataFeatures<Consume, String> param) -> param.getValue().consumeNumber);
        memberName.setCellValueFactory((TableColumn.CellDataFeatures<Consume, String> param) -> param.getValue().memberName);
        consumeDate.setCellValueFactory((TableColumn.CellDataFeatures<Consume, String> param) -> param.getValue().consumeDate);
        bonus.setCellValueFactory((TableColumn.CellDataFeatures<Consume, String> param) -> param.getValue().bonus);
        consumeList.clear();
        consumeList.addAll(DBConnector.getInstance().
                getConsumeInfo(workerId,pickDateStart.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " 00:00:00",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        consumeTable.setItems(consumeList);
    }

    /**
     * 设置收入列表
     */
    private void setIncomeInfo(){
        gzName.setCellValueFactory((TableColumn.CellDataFeatures<Income, String> param) -> param.getValue().gzName);
        DYBH.setCellValueFactory((TableColumn.CellDataFeatures<Income, String> param) -> param.getValue().DYBH);
        DYMC.setCellValueFactory((TableColumn.CellDataFeatures<Income, String> param) -> param.getValue().DYMC);
        incomeSum.setCellValueFactory((TableColumn.CellDataFeatures<Income, String> param) -> param.getValue().incomeSum);
        incomeList.clear();
        incomeList.addAll(DBConnector.getInstance().getIncomeInfo( workerId,
                pickDateStart.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " 00:00:00", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        incomeTable.setItems(incomeList);
    }

    public void onQueryButtonClicke() {
        setDatePickerDisable(true);             //关闭可视区
        String start = pickDateStart.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " 00:00:00";
        String end;
        if(!Objects.equals(pickDateEnd.getValue(), LocalDate.now()))  //判断日期修改过
            end = pickDateEnd.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " 23:59:59";
        else
            end = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        if(mainPane.getSelectionModel().getSelectedItem() == consumeTab){
            consumeList.clear();
            consumeList.addAll(DBConnector.getInstance().getConsumeInfo(workerId,start,end));
        }else if(mainPane.getSelectionModel().getSelectedItem() == incomeTab){
            incomeList.clear();
            incomeList.addAll(DBConnector.getInstance().getIncomeInfo(workerId, start, end));
        }
        setLabelVisible(true);
        setDatePickerDisable(false);
        startTimeLabel.setText(start);
        endTimeLabel.setText(end);
    }

    public void onExitButtonClicked(MouseEvent mouseEvent) throws IOException {
        Stage nowStage = (Stage) exitButton.getScene().getWindow();
        nowStage.close();
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(root, 600, 360);
        scene.setRoot(root);
        Stage stage = new Stage();
        stage.setTitle("医院挂号系统");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * 设置具体时间的可见与否
     * @param flag 可见与否标志
     */
    private void setLabelVisible(boolean flag){
        startLabel.setVisible(flag);
        endLabel.setVisible(flag);
        startTimeLabel.setVisible(flag);
        endTimeLabel.setVisible(flag);
    }

    /**
     * 设置是否可以选择日期
     * @param flag 是否可选
     */
    private void setDatePickerDisable(boolean flag){
        pickDateStart.setDisable(flag);
        pickDateEnd.setDisable(flag);
    }

    public static String getWorkerId() {
        return workerId;
    }

    public static void setWorkerId(String workerId) {
        WorkerController.workerId = workerId;
    }

    public static String getWorkerName() {
        return workerName;
    }

    public static void setWorkerName(String workerName) {
        WorkerController.workerName = workerName;
    }


}
