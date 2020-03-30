package hust.cs.javacourse.ch9;

class A implements Cloneable {
    public static final int SIZE = 10;
    private int[] values = new int[SIZE];

    public int[] getValues() {
        return values;
    }

    public void setValues(int[] newValues) {
        this.values = newValues;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof A) {
            return java.util.Arrays.equals(this.getValues(),((A)obj).getValues());
        }
        return false;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        //return super.clone()                      //浅拷贝
        A newObj = new A();
        newObj.values = this.values.clone();        //已经实现好的 clone
        return newObj;
    }

    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();
        for (int v : values) {
            buf.append(v+" ");
        }
        return buf.toString().trim();       //比较实用的写法
    }
}

class B implements Cloneable {
    A a;
    int i;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        B newObj = new B();
        newObj.i = this.i;
        newObj.a = (A)this.a.clone();
        return newObj;
    }
}

public class CloneTest {
    public static void main(String[] args) throws CloneNotSupportedException {
        A o1 = new A();
        o1.setValues(new int[]{1,2,3,4,5,6,7,8,9,10});
        A o2 = (A) o1.clone();
        System.out.println(o2 == o1);
        System.out.println(o1.getValues() == o2.getValues());
        System.out.println(o1.equals(o2));
        System.out.println(o1.toString());
    }
}
