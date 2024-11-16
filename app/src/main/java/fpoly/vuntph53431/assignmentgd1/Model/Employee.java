package fpoly.vuntph53431.assignmentgd1.Model;

public class Employee {
    private int id;
    private String employname;
    private int employage;
    private String employjob;

    public Employee(String employname, int employage, String employjob) {
        this.employname = employname;
        this.employage = employage;
        this.employjob = employjob;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmployname() {
        return employname;
    }

    public void setEmployname(String employname) {
        this.employname = employname;
    }

    public int getEmployage() {
        return employage;
    }

    public void setEmployage(int employage) {
        this.employage = employage;
    }

    public String getEmployjob() {
        return employjob;
    }

    public void setEmployjob(String employjob) {
        this.employjob = employjob;
    }
}
