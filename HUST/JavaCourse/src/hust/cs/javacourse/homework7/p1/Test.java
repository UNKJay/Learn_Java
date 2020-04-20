package hust.cs.javacourse.homework7.p1;

public class Test{
    public static void main(String[] args){
        Container<Object> container = new Container<>(6);
        container.add("12");
        container.add("34");
        container.add("56");
        container.add("78");
        container.add("9");
        container.add(10);
        Iterator it = container.iterator();
        while (it.hasNext()){
            Object s = it.next();
            if( s != null)
                System.out.println(s);
        }
    }
}
