package homework.ch11_13.p3;

public class Person implements Cloneable{
    private int age;                //年龄
    private String name;            //姓名

    /**
     * 缺省构造函数
     */
    public Person() {
        age = 0;
        name = "";
    }

    /**
     * 构造函数
     * @param name 姓名
     * @param age 年龄
     */
    public Person(String name, int age) {
        this.age = age;
        this.name = name;
    }

    /**
     * 获取年龄
     * @return 年龄
     */
    public int getAge() {
        return age;
    }

    /**
     * 修改年龄
     * @param age 新年龄
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * 获取姓名
     * @return 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 修改姓名
     * @param name 新姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 比较两个 Person 对象的内容是否相等
     * @param obj 待比较的对象
     * @return 当二个对象所有数据成员的内容相等,返回 true
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Person) {
            boolean nameEquals = true;

            try {
                nameEquals = this.name.equals(((Person)obj).name);
            } catch (NullPointerException e) {
                nameEquals = (((Person)obj).name == null);
            }

            return (this.age == ((Person)obj).age)
                    && nameEquals;
        }
        return false;
    }

    /**
     * Person 的深拷贝克隆
     * @return 克隆出来的对象
     * @throws CloneNotSupportedException
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        Person newObj = (Person)super.clone();
        newObj.age = this.age;
        newObj.name = new String(this.name);
        return newObj;
    }

    /**
     * 覆盖 toString
     * @return 描述 Person 对象信息的字符串
     */
    @Override
    public String toString() {
        String s = new String();
        s = "\tname: " + name + ", age: " + age;
        return s;
    }
}
