package homework.ch11_13.p4;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
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

        c1.add(a1);
        c1.add(a2);
        c1.add(c2);
        c2.add(a3);
        c2.add(a4);
        root.add(c1);


//        c2.remove(a3);
//        c2.remove(a4);
//        c1.remove(a1);
//        c1.remove(a2);
//        c1.remove(c2);
//        root.remove(c1);
//        Iterator it = c2.iterator();
        String toString = root.toString();
        String[] values = new String[] {c1.toString(),c2.toString(),a1.toString(),a2.toString(),a3.toString(),a4.toString()};
        for(String value: values){
            int index = toString.indexOf(value);
            System.out.println((index != -1) + " " + index);
        }
        System.out.println(root);

    }
}

/*@Test(dataProvider = "compositeComponentDataProvider", dataProviderClass = CompositeTestDataProvider.class)
        public void testGetPrice(Component c, double price) {
            Assert.assertEquals(c.getPrice(),price);
        }

        @Test(dataProvider = "compositeComponentDataProvider", dataProviderClass = CompositeTestDataProvider.class)
        public void testTestToString(Component c, String[] values) {
            String toString = c.toString();
            Assert.assertNotNull(toString);
            for(String value: values){
                int index = toString.indexOf(value);
                Assert.assertTrue(index != -1, "toString() 没有重用后台节点的toString方法");
            }
        }

        @Test(dataProvider = "compositeComponentDataProvider", dataProviderClass = CompositeTestDataProvider.class)
        public void testAdd(Component parent, Component[] children) {
            for(Component c: children){
                parent.add(c);
            }
            List<Component> list = new ArrayList<>();
            Iterator it = parent.iterator();
            while (it.hasNext()){
                list.add(it.next());
            }
            for (Component c: children){
                Assert.assertTrue(list.contains(c));
            }
        }

        @Test(dataProvider = "compositeComponentDataProvider", dataProviderClass = CompositeTestDataProvider.class)
        public void testRemove(Component parent, Component[] children) {
            for(Component c: children){
                parent.remove(c);
            }
            Iterator it = parent.iterator();
            Assert.assertFalse(it.hasNext());
        }


        @Test(dataProvider = "compositeComponentDataProvider", dataProviderClass = CompositeTestDataProvider.class)
        public void testCalcPrice(Component c, double price) {
            Assert.assertEquals(c.getPrice(),price);
        }

        @Test(dataProvider = "compositeComponentDataProvider", dataProviderClass = CompositeTestDataProvider.class)
        public void testIterator(Component c, Class clz) {
            Assert.assertNotNull(c.iterator());
            Assert.assertEquals(c.iterator().getClass().getName(),clz.getName());
        }*/