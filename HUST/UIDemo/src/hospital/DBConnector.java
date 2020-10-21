package hospital;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.LinkedList;

public class DBConnector {
    private static volatile DBConnector instance = null;
    private Connection connection;
    private Statement statement;

    /**
     * 加载mariadb驱动
     */
    private DBConnector(){
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("not found mariadb driver");
            e.printStackTrace();
        }
    }

    /**
     * 线程安全的单例模式
     * @return 数据库连接实例
     */
    public static DBConnector getInstance(){
        if(instance == null){
            synchronized (DBConnector.class){
                if (instance == null) {
                    instance = new DBConnector();
                }
            }
        }
        return instance;
    }

    /**
     * 建立数据库连接
     * @param hostName 主机名
     * @param port 端口号
     * @param dbName 数据库名
     * @param user 用户名
     * @param passwd 密码
     */
    public void connectDB(String hostName,int port,String dbName,String user,String passwd){
        String url = "jdbc:mariadb://"+hostName+":"+port+"/" +
                dbName+"?autoReconnect=true&characterEncoding=UTF-8&characterSetResults=UTF-8&serverTimezone=UTC";
        try {
            connection = DriverManager.getConnection(url,user,passwd);
            statement = connection.createStatement();
        } catch (SQLException throwables) {
            System.out.println("cannot connect to mariadb");
            throwables.printStackTrace();
        }
    }

    /**
     * 关闭数据库连接
     */
    public void closeConnect(){
        try{
            connection.close();
            statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 获取病人信息
     * @param pid 病人编号
     * @return 病人信息
     */
    public ResultSet getPatientInfo(String pid) {
        try {
            return statement.executeQuery("SELECT * FROM T_BRXX WHERE BRBH =" + pid);
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * 获取医生信息
     * @param docid 医生编号
     * @return 医生信息
     */
    public ResultSet getDoctorInfo(String docid) {
        try {
            return statement.executeQuery("SELECT * FROM  T_KSYS WHERE YSBH =" + docid);
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * 更新登录日期
     * @param loginType 登录身份
     * @param user 用户
     */
    public void updateDLRQ(String loginType, String user){
        try{
            String table = loginType.equals("患者")?"T_BRXX" : "T_KSYS";
            String bhType = loginType.equals("患者")?"BRBH" : "YSBH";
            statement.executeUpdate("update " + table + " set DLRQ = DATE_FORMAT(NOW(),'%Y-%m-%d %H:%m:%s') " +
                    "where " + bhType + "=" + user);
            //System.out.println("updated");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 获取科室信息表
     * @return 科室编号+科室名称
     */
    public LinkedList<String> getDepInfor(){
        try{
            ResultSet resultSet = statement.executeQuery("SELECT * FROM T_KSXX");
            LinkedList<String> depInfo = new LinkedList<>();
            while (resultSet.next()){
                String depid = resultSet.getString("KSBH").trim();
                String depName = resultSet.getString("KSMC").trim();
                depInfo.add(depid + " " + depName);
            }
            return depInfo;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取挂号信息
     * @param department 科室编号
     * @param isSP 是否为专家号
     * @param regTypePY 号种名称的拼音
     * @return 号种编号+号种+挂号费用
     */
    public LinkedList<String> getRegisterInfo(String department, String isSP, String regTypePY){
        try{
            int ifSp = isSP.equals("专家号") ? 1 : 0;
            ResultSet result = statement.executeQuery("SELECT HZBH,HZMC,PYZS,GHRS,GHFY FROM T_HZXX " +
                    "WHERE KSBH = " + department + " AND SFZJ = " + ifSp +
                        " AND (GHRS > (SELECT IFNULL(MAX(GHRC),0) FROM T_GHXX " +
                        "WHERE HZBH=T_HZXX.HZBH AND TO_DAYS(RQSJ)=TO_DAYS(NOW())))");

            LinkedList<String> registerInfo = new LinkedList<>();
            String registerPY;
            while (result.next()){
                String registerId = result.getString("HZBH").trim();
                String registerName = result.getString("HZMC").trim();
                registerPY = result.getString("PYZS").trim();
                Double registerPay = result.getDouble("GHFY");
                if(regTypePY == null) {
                    registerInfo.add(registerId+" "+registerName+" "+isSP+" "+registerPay+" 元");
                }
                else{
                    if(registerPY.contains(regTypePY)) {
                        registerInfo.add(registerId+" "+registerName+" "+isSP+" "+registerPay+" 元");
                    }
                }
            }
            return registerInfo;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取医生名称
     * @param docid 医生编号
     * @return 医生名称
     */
    public String getDoctorName(String docid){
        try {
            ResultSet resultSet = statement.executeQuery("SELECT YSMC FROM T_KSYS WHERE YSBH = "+ docid);
            while (resultSet.next())
                return resultSet.getString("YSMC");
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取病人信息
     * @param docid 医生编号
     * @return 病人信息
     */
    public ObservableList<DoctorController.Register> getPatients(String docid){
        try {
            ObservableList<DoctorController.Register> patients = FXCollections.observableArrayList();
            ResultSet resultSet = statement.executeQuery("SELECT GHBH,BRMC,RQSJ,SFZJ " +
                    "FROM T_GHXX,T_BRXX,T_HZXX WHERE T_GHXX.YSBH= "+ docid +
                    " AND T_GHXX.BRBH=T_BRXX.BRBH AND T_GHXX.HZBH=T_HZXX.HZBH ORDER BY GHBH");
            while (resultSet.next()){
                String registerId = resultSet.getString("GHBH").trim();
                String patientName = resultSet.getString("BRMC").trim();
                String regTime = resultSet.getString("RQSJ").trim();
                int isSP = resultSet.getInt("SFZJ");
                DoctorController.Register patient = new DoctorController.Register(registerId,patientName,regTime,isSP);
                patients.add(patient);
            }
            return patients;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据输入表名或者拼音得到具体科室信息、号种信息或挂号信息
     * @param table 要访问的表
     * @param tablePY 表拼音
     * @return 具体信息
     */
    public LinkedList<String> getInfo(String table, String tablePY){
        LinkedList<String> info = new LinkedList<>();
        String queryTable = "";
        String number = "";         //编号
        String name = "";
        String fee = "";
        if(!table.equals("")){
            switch (table){
                case "department":
                    queryTable = "T_KSXX";
                    number = "KSBH";
                    name = "KSMC";
                    break;
                case "haozhong":
                    queryTable = "T_HZXX";
                    number = "HZBH";
                    name = "HZMC";
                    fee = "GHFY";
                    break;
                case "doctor":
                    queryTable = "T_KSYS";
                    number = "YSBH";
                    name = "YSMC";
                    break;
                default:
                    queryTable = null;
                    break;
            }
        }
        try{
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + queryTable);
            while (resultSet.next()){
                if(tablePY.isEmpty()){
                    info.add(resultSet.getString(number) + " " + resultSet.getString(name));
                    if(!fee.equals("")) {
                        info.add(info.getLast() + " " + resultSet.getDouble(fee) + "元");
                    }
                }else{
                    //设置为大写
                    tablePY = tablePY.toUpperCase();
                    if(resultSet.getString("PYZS").contains(tablePY)){
                        info.add(resultSet.getString(number)+" "+resultSet.getString(name));
                        if(!fee.equals("")){
                            info.add(info.getLast()+" "+resultSet.getDouble(fee)+"元");
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return info;
    }

    /**
     * 根据输入的科室号选择医生
     * @param depId 科室编号
     * @param docPY 医生拼音
     * @return 医生信息
     */
    public LinkedList<String> getDepartDoctor(String depId, String docPY){
        LinkedList<String> info = new LinkedList<>();
        try{
            ResultSet resultSet = statement.executeQuery("SELECT YSBH,YSMC,PYZS FROM T_KSYS " +
                    "WHERE KSBH = " + depId);
            while (resultSet.next()){
                if(docPY == null){
                    info.add(resultSet.getString("YSBH")+" " + resultSet.getString("YSMC"));
                }else
                {
                    //设置为大写
                    docPY = docPY.toUpperCase();
                    if(resultSet.getString("PYZS").contains(docPY)){
                        info.add(resultSet.getString("YSBH")+" " + resultSet.getString("YSMC"));
                    }
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return info;
    }

    /**
     * 尝试挂号
     * @param HZBH 号种编号
     * @param YSBH 医生编号
     * @param BRBH 病人编号
     * @param GHFY 挂号费用
     * @param patientPay 病人付款
     * @return 状态信息
     */
    public String tryRegister(String HZBH,String YSBH,String BRBH,double GHFY,double patientPay){
        try{
            ResultSet resultSet = statement.executeQuery("SELECT * FROM T_GHXX ORDER BY GHBH DESC limit 1");
            int registerId,count,maxRegister = 0;  //挂号id和当前挂号人数，最大挂号人数
            if(!resultSet.next()){
                registerId = 1;
            } else{
                registerId = Integer.parseInt(resultSet.getString("GHBH"))+1;
            }
            resultSet = statement.executeQuery("SELECT * FROM T_GHXX WHERE HZBH = " +
                    HZBH + " and RQSJ >= DATE_FORMAT(NOW(),'%Y-%m-%d 00:00:00') ORDER BY GHRC DESC limit 1");
            if(!resultSet.next()){
                count = 0;
            } else {
                count = resultSet.getInt("GHRC");
            }
            resultSet = statement.executeQuery("SELECT * FROM T_HZXX WHERE HZBH = " + HZBH);
            while (resultSet.next()){
                maxRegister = resultSet.getInt("GHRS");
            }
            if(count >= maxRegister){
                return "当前号种挂号人数已满,请选择其他号种";
            }
            statement.executeUpdate(
                    String.format("INSERT INTO T_GHXX VALUE (\"%06d\",\"%s\",\"%s\",\"%s\",%d,0,%.2f, current_timestamp)",
                            registerId, HZBH, YSBH, BRBH, count+1, GHFY));

            statement.executeUpdate(String.format("UPDATE T_BRXX SET YCJE = %.2f WHERE BRBH = %s",patientPay-GHFY,BRBH));
            double refund = Double.parseDouble(String.format("%.2f",patientPay-GHFY));
            RegisterController.setPatientYCYE(refund);
            return "success "+registerId;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 医生查询时间病人信息
     * @param doctorID 医生编号
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 病人列表
     */
    public LinkedList<DoctorController.Register> getPatientInfo(String doctorID, String startTime, String endTime){
        LinkedList<DoctorController.Register> patients = new LinkedList<>();
        try{
            String query = "SELECT T_GHXX.GHBH,T_BRXX.BRMC,T_GHXX.RQSJ,T_HZXX.SFZJ " +
                    "FROM T_GHXX,T_BRXX,T_HZXX WHERE T_GHXX.YSBH = "+ doctorID +
                    " AND T_HZXX.HZBH = T_GHXX.HZBH AND T_GHXX.BRBH = T_BRXX.BRBH AND RQSJ >= '" + startTime + "' and RQSJ <= '"+ endTime + "'";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                patients.add(new DoctorController.Register(resultSet.getString("GHBH"),resultSet.getString("BRMC"),resultSet.getString("RQSJ"),resultSet.getInt("SFZJ")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return patients;
    }

    /**
     * 获取收入信息
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 收入信息列表
     */
    public ObservableList<DoctorController.Income> getIncomeInfo(String startTime, String endTime){
        ObservableList<DoctorController.Income> incomes = FXCollections.observableArrayList();
        try {
            String query = "SELECT KSMC,T_GHXX.YSBH,YSMC,T_HZXX.SFZJ,COUNT(*) AS CNT ,SUM(T_GHXX.GHFY) AS INCOME FROM T_GHXX,T_KSXX,T_KSYS,T_HZXX WHERE " +
                    "T_GHXX.YSBH=T_KSYS.YSBH AND T_KSYS.KSBH=T_KSXX.KSBH AND T_GHXX.HZBH=T_HZXX.HZBH AND T_GHXX.RQSJ >= '" +
                    startTime + "' AND T_GHXX.RQSJ <= '" + endTime + "' AND T_GHXX.THBZ = 0 GROUP BY T_GHXX.YSBH";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                incomes.add(new DoctorController.Income(resultSet.getString("KSMC"),resultSet.getString("YSBH"),resultSet.getString("YSMC"),
                        resultSet.getInt("SFZJ"),resultSet.getInt("cnt"),resultSet.getDouble("income")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return incomes;
    }
}
