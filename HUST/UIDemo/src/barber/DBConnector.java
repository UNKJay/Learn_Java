package barber;

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
     * 获取会员信息
     * @param pid 会员卡编号
     * @return 会员信息
     */
    public ResultSet getMemberInfo(String pid) {
        try {
            return statement.executeQuery("SELECT * FROM T_HYXX WHERE HYBH =" + pid);
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * 获取医生信息
     * @param workerId 医生编号
     * @return 医生信息
     */
    public ResultSet getWorkerInfo(String workerId) {
        try {
            return statement.executeQuery("SELECT * FROM  T_DYXX WHERE DYBH =" + workerId);
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     *
     * @param GZID 工种ID
     * @return 费用
     */
    public double getPay(String GZID) {
        Double result = 0.0;
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM T_XFLX WHERE GZBH = " + GZID);
            while (resultSet.next()) {
                result = resultSet.getDouble("FY");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
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
     * 获取店员名称
     * @param workerID 店员编号
     * @return 店员姓名
     */
    public String getWorkerName(String workerID){
        try {
            ResultSet resultSet = statement.executeQuery("SELECT DYMC FROM T_DYXX WHERE DYBH = "+ workerID);
            while (resultSet.next())
                return resultSet.getString("DYMC");
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }





    /**
     * 选择学徒
     * @param XTPY 学徒拼音
     * @return 医生信息
     */
    public LinkedList<String> getXTName(String XTPY){
        LinkedList<String> info = new LinkedList<>();
        try{

            ResultSet resultSet = statement.executeQuery("SELECT DYBH,DYMC,PYZS FROM T_DYXX " +
                    "WHERE GZBH = 000001" );
            while (resultSet.next()){

                if(XTPY == null){
                    info.add(resultSet.getString("DYBH")+" " + resultSet.getString("DYMC"));

                }else
                {
                    //设置为大写
                    XTPY = XTPY.toUpperCase();
                    if(resultSet.getString("PYZS").contains(XTPY)){
                        info.add(resultSet.getString("DYBH") + " " + resultSet.getString("DYMC"));
                    }
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return info;
    }

    /**
     * 选择发型师类型
     * @param FXSType 发型师类型拼音
     * @return 所有发型师类型
     */
    public LinkedList<String> getBarberType(String FXSType){
        LinkedList<String> info = new LinkedList<>();
        try{

            ResultSet resultSet = statement.executeQuery("SELECT GZBH,GZMC,PYZS FROM T_GZXX " +
                    "WHERE GZBH NOT IN (000000,000001) " );
            while (resultSet.next()){

                if(FXSType == null){
                    info.add(resultSet.getString("GZBH") + " " + resultSet.getString("GZMC"));

                }else
                {
                    //设置为大写
                    FXSType = FXSType.toUpperCase();
                    if(resultSet.getString("PYZS").contains(FXSType)){
                        info.add(resultSet.getString("GZBH") + " " + resultSet.getString("GZMC"));
                    }
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return info;
    }

    /**
     * 选择发型师
     *
     * @param barberType 发型师类型(编号)
     * @param NamePY 发型师姓名
     * @return 所有发型师类型
     */
    public LinkedList<String> getBarberName(String barberType, String NamePY){
        LinkedList<String> info = new LinkedList<>();
        try{

            ResultSet resultSet = statement.executeQuery("SELECT DYBH,DYMC,PYZS FROM T_DYXX " +
                    "WHERE GZBH = " + barberType );
            while (resultSet.next()){
                if(NamePY == null){
                    info.add(resultSet.getString("DYBH") + " " + resultSet.getString("DYMC"));
                }else
                {
                    //设置为大写
                    NamePY = NamePY.toUpperCase();
                    if(resultSet.getString("PYZS").contains(NamePY)){
                        info.add(resultSet.getString("DYBH") + " " + resultSet.getString("DYMC"));
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return info;
    }

    /**
     * 尝试记录消费
     * @param GZBH 工种编号
     * @param DYBH 店员编号
     * @param HYBH 会员编号
     * @param XFFY 消费费用
     * @param YCYE 余额
     * @return 状态信息
     */
    public String tryPay(String GZBH, String DYBH, String HYBH, double XFFY, double YCYE){
        try{
            ResultSet resultSet = statement.executeQuery("SELECT * FROM T_XFJL ORDER BY XFBH DESC limit 1");

            int registerId = 0;  //挂号id

            if(!resultSet.next()){
                registerId = 1;
            } else{
                registerId = Integer.parseInt(resultSet.getString("XFBH"))+1;
            }
            statement.executeUpdate(
                    String.format("INSERT INTO T_XFJL VALUE (\"%06d\",\"%s\",\"%s\",\"%s\",%.2f, current_timestamp)",
                            registerId, GZBH, DYBH, HYBH,  XFFY));
            statement.executeUpdate(String.format("UPDATE T_HYXX SET YCJE = %.2f WHERE HYBH = %s", YCYE - XFFY, HYBH));
            double refund = Double.parseDouble(String.format("%.2f", YCYE - XFFY));
            ConsumeController.setMemberYCYE(refund);
            return "success "+registerId;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 尝试注册会员
     * @param name 姓名
     * @param password 密码
     * @return 状态信息
     */
    public String tryRegister(String name, String password){
        try{
            ResultSet resultSet = statement.executeQuery("SELECT * FROM T_HYXX ORDER BY HYBH DESC limit 1");
            int registerId = 0;  //挂号id
            if(!resultSet.next()){
                registerId = 1;
            } else{
                registerId = Integer.parseInt(resultSet.getString("HYBH"))+1;
            }

            statement.executeUpdate(
                    String.format("INSERT INTO T_HYXX VALUE (\"%06d\",\"%s\",\"%s\",%.2f)",
                            registerId, name, password, 0.0));
            return "success " + String.format("%06d",registerId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param HYBH  会员编号
     * @param chargeFee 充值金额
     * @param YCYE 账户余额
     * @return 充值状态
     */
    public String tryCharge(String HYBH, double chargeFee, double YCYE) {
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery("SELECT * FROM T_XFJL ORDER BY XFBH DESC limit 1");
            int registerId = 0;  //挂号id
            if(!resultSet.next()){
                registerId = 1;
            } else{
                registerId = Integer.parseInt(resultSet.getString("XFBH"))+1;
            }
            statement.executeUpdate(
                    String.format("INSERT INTO T_XFJL VALUE (\"%06d\",\"%s\",\"%s\",\"%s\",%.2f, current_timestamp)",
                            registerId, getBossGZ(), getBossID(), HYBH,  chargeFee));
            chargeFee = chargeFee + (int)chargeFee * (HYBH.equals("000000") ? 0:1)/100*20;
            statement.executeUpdate(String.format("UPDATE T_HYXX SET YCJE = %.2f WHERE HYBH = %s", YCYE + chargeFee, HYBH));
            double refund = Double.parseDouble(String.format("%.2f", YCYE + chargeFee));
            ConsumeController.setMemberYCYE(refund);
            return "success "+registerId;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * 获取店长工种
     * @return
     */
    public String getBossGZ() {
        return "000000";
    }

    /**
     * 获取店长编号
     * @return
     */
    public String getBossID() {
        return "000000";
    }

    /**
     * 查询时间消费信息
     * @param workerID 店员编号
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 消费列表
     */
    public LinkedList<WorkerController.Consume> getConsumeInfo(String workerID, String startTime, String endTime){
        LinkedList<WorkerController.Consume> patients = new LinkedList<>();
        try{
            String query = "SELECT T_XFJL.XFBH,T_HYXX.HYMC,T_XFJL.RQSJ, (T_DYXX.JJBL *T_XFJL.XFFY) AS BONUS " +
                    "FROM T_XFJL,T_HYXX,T_XFLX,T_DYXX WHERE T_XFJL.DYBH = " + workerID +
                    " AND T_XFJL.GZBH = T_XFLX.GZBH AND T_XFJL.HYBH = T_HYXX.HYBH AND T_XFJL.DYBH = T_DYXX.DYBH " +
                    "AND T_XFJL.RQSJ >= '" + startTime + "' and T_XFJL.RQSJ <= '"+ endTime + "'";
            //System.out.println(query);
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()){
                patients.add(new WorkerController.Consume(resultSet.getString("XFBH"),resultSet.getString("HYMC"),resultSet.getString("RQSJ"),resultSet.getDouble("BONUS")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return patients;
    }

    /**
     * 获取收入信息
     *
     * @param workerID ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 收入信息列表
     */
    public ObservableList<WorkerController.Income> getIncomeInfo(String workerID, String startTime, String endTime){
        ObservableList<WorkerController.Income> incomes = FXCollections.observableArrayList();

        try {
            String query = "SELECT GZMC,T_XFJL.DYBH,DYMC, (DX+SUM(T_DYXX.JJBL*T_XFJL.XFFY)) AS INCOME FROM T_XFJL,T_XFLX,T_DYXX,T_GZXX WHERE " +
                    "T_XFJL.DYBH = T_DYXX.DYBH AND T_XFJL.GZBH = T_XFLX.GZBH AND T_GZXX.GZBH = T_XFJL.GZBH AND T_XFJL.RQSJ >= '" +
                    startTime + "' AND T_XFJL.RQSJ <= '" + endTime + "' GROUP BY T_XFJL.DYBH";

            ResultSet resultSet = statement.executeQuery(query);
            double total = 0.0;
            String totalQuery = "SELECT SUM(XFFY) AS INCOME FROM T_XFJL WHERE XFBH IN (SELECT XFBH FROM T_XFJL WHERE T_XFJL.RQSJ >= " +
                    "'" + startTime + "' AND T_XFJL.RQSJ <= '" + endTime + "')";
            ResultSet resultSet1 = statement.executeQuery(totalQuery);
            while (resultSet1.next()) {
                total = resultSet1.getDouble("income");
            }
            while (resultSet.next()){
                if (!resultSet.getString("DYBH").equals(getBossID())) {
                    incomes.add(new WorkerController.Income(resultSet.getString("GZMC"),resultSet.getString("DYBH"),resultSet.getString("DYMC"),
                            resultSet.getDouble("income")));
                } else {
                    incomes.add(new WorkerController.Income(resultSet.getString("GZMC"),resultSet.getString("DYBH"),resultSet.getString("DYMC"),
                            total));
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return incomes;
    }
}
