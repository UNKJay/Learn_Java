package homework.ch11_13.p3;

public class Student extends Person implements Cloneable{
    private String classNo;     //所在班级
    private String department;      //所在院系
    private int studentId;      //学生Id

    public Student() {
        super();
        classNo = "";
        department = "";
        studentId = 0;
    }

    public Student(String name, int age, int studentId, String department, String classNo) {
        super(name, age);
        this.classNo = classNo;
        this.department = department;
        this.studentId = studentId;
    }

    public String getClassNo() {
        return classNo;
    }

    public void setClassNo(String classNo) {
        this.classNo = classNo;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Student) {
            boolean classNoEquals = true;
            boolean departmentEquals = true;

            try {
                classNoEquals = this.classNo.equals(((Student)obj).classNo);
            } catch (NullPointerException e) {
                classNoEquals = (((Student)obj).classNo == null);
            }

            try {
                departmentEquals = this.department.equals(((Student)obj).department);
            } catch (NullPointerException e) {
                departmentEquals = (((Student)obj).department == null);
            }

            return super.equals(obj)
                    && this.studentId == ((Student)obj).studentId
                    && departmentEquals
                    && classNoEquals;
        }
        return false;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Student newObj = (Student)super.clone();
        newObj.studentId = this.studentId;
        newObj.classNo = new String(this.classNo);
        newObj.department = new String(this.department);
        return newObj;
    }

    @Override
    public String toString() {
        return super.toString() + ", studentId: " + studentId + ", department: " + department
                + ", classNo: " + classNo + "\n";
    }
}
