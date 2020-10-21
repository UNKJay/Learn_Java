package hospital;

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

public class DoctorController {

    /**
     * 病人列表内部类，包含挂号编号，病人名称，挂号时间，号种类别
     */
    static class Register{
        public StringProperty registerNumber;
        public StringProperty patientName;
        public StringProperty registerDate;
        public StringProperty isSp;

        public Register(String registerNumber, String patientName, String registerDate, int isSp) {
            this.registerNumber = new SimpleStringProperty(registerNumber);
            this.patientName = new SimpleStringProperty(patientName);
            this.registerDate = new SimpleStringProperty(registerDate);
            this.isSp = new SimpleStringProperty(isSp == 1 ? "专家号" : "普通号");
        }
    }

    /**
     * 医生收入列表内部类，包含科室编号，医生编号，医生名称，号种类别，挂号人次，收入合计
     */
    static class Income {
        public StringProperty departmentName;
        public StringProperty doctorId;
        public StringProperty doctorName;
        public StringProperty isSp;
        public StringProperty registerCount;
        public StringProperty incomeSum;

        public Income(String departmentName, String doctorId, String doctorName, int isSp, int registerCount, double income){
            this.departmentName = new SimpleStringProperty(departmentName);
            this.doctorId = new SimpleStringProperty(doctorId);
            this.doctorName = new SimpleStringProperty(doctorName);
            this.isSp = new SimpleStringProperty(isSp == 1 ? "专家号" : "普通号");
            this.registerCount = new SimpleStringProperty(Integer.toString(registerCount));
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
    private Tab patinetsTab;  //病人列表页
    @FXML
    private TableView<Register> patientTable;  //病人列表
    @FXML
    private TableColumn<Register,String> registerNum;  //挂号编号
    @FXML
    private TableColumn<Register,String> patientName;  //病人名称
    @FXML
    private TableColumn<Register,String> registerDate;  //挂号时间
    @FXML
    private TableColumn<Register,String> isSp;  //挂号号种类别
    @FXML
    private Tab incomeTab;  //收入列表页
    @FXML
    private TableView<Income> incomeTable;  //收入列表
    @FXML
    private TableColumn<Income,String> departmentName; //科室名
    @FXML
    private TableColumn<Income,String> docId;  //医生编号
    @FXML
    private TableColumn<Income,String> docName;  //医生名称
    @FXML
    private TableColumn<Income,String> isSpecial;  //号种类别
    @FXML
    private TableColumn<Income,String> registerCount;  //挂号人次
    @FXML
    private TableColumn<Income,String> incomeSum;  //收入总计

    private static String doctorId;  //医生编号
    private static String doctorName;  //医生名称

    private ObservableList<Register> patientList = FXCollections.observableArrayList();  //病人列表
    private ObservableList<Income> incomeList = FXCollections.observableArrayList();  //收入列表

    @FXML
    void initialize(){
        welcomeLabel.setText(doctorName + "医生您好");
        setLabelVisible(false);
        pickDateStart.setValue(LocalDate.now());
        pickDateEnd.setValue(LocalDate.now());
        setPatientInfo();
        setIncomeInfo();

        patinetsTab.setOnSelectionChanged(event -> {
            setPatientInfo();
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
     * 设置病人列表信息
     */
    private void setPatientInfo(){
        registerNum.setCellValueFactory((TableColumn.CellDataFeatures<Register, String> param) -> param.getValue().registerNumber);
        patientName.setCellValueFactory((TableColumn.CellDataFeatures<Register, String> param) -> param.getValue().patientName);
        registerDate.setCellValueFactory((TableColumn.CellDataFeatures<Register, String> param) -> param.getValue().registerDate);
        isSp.setCellValueFactory((TableColumn.CellDataFeatures<Register, String> param) -> param.getValue().isSp);
        patientList.clear();
        patientList.addAll(DBConnector.getInstance().
                getPatientInfo(doctorId,pickDateStart.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " 00:00:00",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        patientTable.setItems(patientList);
    }

    /**
     * 设置收入列表
     */
    private void setIncomeInfo(){
        departmentName.setCellValueFactory((TableColumn.CellDataFeatures<Income, String> param) -> param.getValue().departmentName);
        docId.setCellValueFactory((TableColumn.CellDataFeatures<Income, String> param) -> param.getValue().doctorId);
        docName.setCellValueFactory((TableColumn.CellDataFeatures<Income, String> param) -> param.getValue().doctorName);
        isSpecial.setCellValueFactory((TableColumn.CellDataFeatures<Income, String> param) -> param.getValue().isSp);
        registerCount.setCellValueFactory((TableColumn.CellDataFeatures<Income, String> param) -> param.getValue().registerCount);
        incomeSum.setCellValueFactory((TableColumn.CellDataFeatures<Income, String> param) -> param.getValue().incomeSum);
        incomeList.clear();
        incomeList.addAll(DBConnector.getInstance().getIncomeInfo(pickDateStart.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " 00:00:00",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
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
        if(mainPane.getSelectionModel().getSelectedItem() == patinetsTab){
            patientList.clear();
            patientList.addAll(DBConnector.getInstance().getPatientInfo(doctorId,start,end));
        }else if(mainPane.getSelectionModel().getSelectedItem() == incomeTab){
            incomeList.clear();
            incomeList.addAll(DBConnector.getInstance().getIncomeInfo(start,end));
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

    public static String getDoctorId() {
        return doctorId;
    }

    public static void setDoctorId(String doctorId) {
        DoctorController.doctorId = doctorId;
    }

    public static String getDoctorName() {
        return doctorName;
    }

    public static void setDoctorName(String doctorName) {
        DoctorController.doctorName = doctorName;
    }


}
