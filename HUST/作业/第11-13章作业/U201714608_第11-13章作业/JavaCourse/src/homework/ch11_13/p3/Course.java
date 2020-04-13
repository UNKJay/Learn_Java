package homework.ch11_13.p3;

import java.util.ArrayList;
import java.util.List;

public class Course implements Cloneable{
    private String courseName;
    private List<Person> students;
    private Person teacher;

    public Course(String courseName, Person teacher) {
        this.students = new ArrayList<Person>();
        this.courseName = courseName;
        this.teacher = teacher;
    }

    public void register(Person s) {
        if (!students.contains(s)) {
            students.add(s);
        }
    }

    public String getCourseName() {
        return courseName;
    }

    public List<Person> getStudents() {
        return students;
    }

    public Person getTeacher() {
        return teacher;
    }

    public void unregister(Person s) {
        students.remove(s);
    }

    public int getNumberOfStudent() {
        return students.size();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Course) {

            boolean courseNameEquals = true;
            try {
                courseNameEquals = this.courseName.equals(((Course)obj).courseName);
            } catch (NullPointerException e) {
                courseNameEquals = (((Course)obj).courseName == null);
            }

            return this.teacher.equals(((Course) obj).teacher)
                    && courseNameEquals
                    && this.students.containsAll(((Course) obj).students)
                    && this.students.size() == ((Course) obj).students.size();
        }
        return false;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Course newObj = (Course) super.clone();
        newObj.courseName = new String(this.courseName);
        newObj.teacher = (Person) this.teacher.clone();
        newObj.students = new ArrayList<Person>();
        for (Person e :this.students
             ) {
            newObj.register((Person) e.clone());
        }
        return newObj;
    }

    @Override
    public String toString() {
        StringBuffer studentInfo = new StringBuffer();
        for (Person e: this.students
             ) {
            studentInfo.append(e.toString());
        }
        return "CourseName: " + this.courseName + "\n"
                + this.teacher.toString() + "\n"
                + "student list: \n" + studentInfo.toString()
                + "Totally: " + this.getNumberOfStudent() + " students.";
    }
}
