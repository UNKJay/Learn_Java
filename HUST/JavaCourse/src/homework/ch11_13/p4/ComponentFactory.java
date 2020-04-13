package homework.ch11_13.p4;

public class ComponentFactory {
    public  static Component create(){
        int id = 0;
        //创建计算对象
        Component computer = new CompositeComponent(id++, "Think Pad", 0.0);

        //创建键盘对象
        Component keyboard = new AtomicComponent(id++, "Keyboard", 20.0);
        //创建鼠标对象
        Component mouse = new AtomicComponent(id++, "Mouse", 20.0);
        //创建显示器对象
        Component monitor = new AtomicComponent(id++, "Monitor", 1000.0);
        computer.add(keyboard);     //键盘加入computer
        computer.add(mouse);        //鼠标加入computer
        computer.add(monitor);      //显示器加入computer

        //创建主机对象
        Component mainFrame= new CompositeComponent(id++, "Main frame", 0.0);
        //创建硬盘对象
        Component hardDisk = new AtomicComponent(id++, "Hard disk",1000);
        //创建电源对象
        Component powerSupplier = new AtomicComponent(id++, "Power supplier",500);
        mainFrame.add(hardDisk);
        mainFrame.add(powerSupplier);

        //创建主板对象
        Component mainBoard = new CompositeComponent(id++, "Main board", 0.0);
        //创建CPU对象
        Component cpu = new AtomicComponent(id++, "CPU", 1500.0);
        //创建显卡对象
        Component videoCard = new AtomicComponent(id++, "Video card", 900);
        //创建网卡对象
        Component networkCard = new AtomicComponent(id++, "Network card", 100);
        mainBoard.add(cpu);         //cpu加入主板
        mainBoard.add(videoCard);   //videoCard加入主板
        mainBoard.add(networkCard); //networkCard加入主板

        mainFrame.add(mainBoard);   //mainBoard加入主机
        computer.add(mainFrame);    //将主机加入computer
        return computer;
    }
}

/*
@DataProvider(name="compositeComponentDataProvider")
        public static Object[][] testCompositeComponent(Method method){
            Object[][] objects = null;
            int id = 0;

            //原子节点基本值
            String aName1 = "aaa1";
            String aName2 = "aaa2";
            String aName3 = "aaa3";
            String aName4 = "aaa4";
            double aPrice1 = 5.0;
            double aPrice2 = 10.0;
            double aPrice3 = 15.0;
            double aPrice4 = 25.0;
            //创建原子节点
            Component a1 = new AtomicComponent(id++, aName1, aPrice1);
            Component a2 = new AtomicComponent(id++, aName2, aPrice2);
            Component a3 = new AtomicComponent(id++, aName3, aPrice3);
            Component a4 = new AtomicComponent(id++, aName4, aPrice4);

            //复合节点基本值
            String cName1 = "ccc1";
            String cName2 = "ccc2";
            String cName3 = "ccc3";
            //创建复合节点
            Component c1 = new CompositeComponent(id++, cName1, 10.0);
            Component c2 = new CompositeComponent(id++, cName2, 10.0);

            //根节点基本值
            String tName = "root";
            //创建根节点
            Component root = new CompositeComponent(id++, tName, 100.0);


            if(method.getName().equals("testGetPrice")){
                c1.add(a1);
                c1.add(a2);
                c1.add(c2);
                c2.add(a3);
                c2.add(a4);
                objects = new Object[][]{
                        {a1, aPrice1},  //原子节点
                        {root, 0.0},    //空的复合节点
                        {c1, aPrice1 + aPrice2 + aPrice3 + aPrice4}
                };
            }
            else if(method.getName().equals("testTestToString")){
                c1.add(a1);
                c1.add(a2);
                c1.add(c2);
                c2.add(a3);
                c2.add(a4);
                root.add(c1);
                objects = new Object[][]{
                        {
                                root, new String[]{c1.toString(),c2.toString(),a1.toString(),a2.toString(),a3.toString(),a4.toString()}
                        }
                };

            }
            else if(method.getName().equals("testAdd")){
                objects = new Object[][]{
                        {c1, new Component[]{a1,a2,c2}},
                        {c2, new Component[]{a3,a4}},
                        {root, new Component[]{c1}}
                };
            }
            else if(method.getName().equals("testRemove")){
                c1.add(a1);
                c1.add(a2);
                c1.add(c2);
                c2.add(a3);
                c2.add(a4);
                root.add(c1);
                objects = new Object[][]{
                        {c2, new Component[]{a3,a4}},
                        {c1, new Component[]{a1,a2,c2}},
                        {root, new Component[]{c1}}
                };
            }
            else if(method.getName().equals("testCalcPrice")){
                c1.add(a1);
                c1.add(a2);
                c1.add(c2);
                c2.add(a3);
                c2.add(a4);
                objects = new Object[][]{
                        {a1, aPrice1},  //原子节点
                        {root, 0.0},    //空的复合节点
                        {c1, aPrice1 + aPrice2 + aPrice3 + aPrice4}
                };
            }
            else if(method.getName().equals("testIterator")){
                objects = new Object[][]{{c1, CompositeIterator.class}};
            }

            return objects;
        }
* */