package homework.ch11_13.p3;

public class Faculty extends Person implements Cloneable{
    private String email;
    private int facultyId;
    private String title;

    public Faculty() {
        super();
        this.email = "";
        this.facultyId = 0;
        this.title = "";
    }

    public Faculty(String name, int age, int facultyId, String title, String email) {
        super(name, age);
        this.email = email;
        this.facultyId = facultyId;
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Faculty) {
            boolean titleEquals = true;
            boolean emailEquals = true;

            try {
                titleEquals = this.title.equals(((Faculty)obj).title);
            } catch (NullPointerException e) {
                titleEquals = (((Faculty)obj).title == null);
            }

            try {
                emailEquals = this.email.equals(((Faculty)obj).email);
            } catch (NullPointerException e) {
                emailEquals = (((Faculty)obj).email == null);
            }
            return super.equals(obj)
                    && this.facultyId == ((Faculty)obj).facultyId
                    && titleEquals
                    && emailEquals;
        }
        return false;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Faculty newObj = (Faculty) super.clone();
        try {
            newObj.email = new String(this.email);
        } catch (NullPointerException e){
            newObj.email = null;
        }
        newObj.facultyId = this.facultyId;

        try{
            newObj.title = new String(this.title);
        } catch (NullPointerException e) {
            newObj.title = null;
        }
        return newObj;
    }

    @Override
    public String toString() {
        return "Teacher Info: " + super.toString()
                + ", facultyId: " + facultyId + ", title: " + title + ", email: " + email;
    }
}
