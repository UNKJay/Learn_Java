package hust.cs.javacourse.ch9;

public class TestSimpleCircle {
    public static void main(String... args) {
        Circle c1 = new Circle(3.0);
        System.out.println("Radius:"+c1.radius);
        c1.setRadius(4.0);
        System.out.println("Radius:"+c1.radius);
    }
}
