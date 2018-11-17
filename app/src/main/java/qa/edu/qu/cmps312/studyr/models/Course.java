package qa.edu.qu.cmps312.studyr.models;

import java.util.ArrayList;

public class Course {

    private int courseId;
    private String courseName;
    private String colorId;

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getColorId() {
        return colorId;
    }

    public void setColorId(String colorId) {
        this.colorId = colorId;
    }

    public Course(int courseId, String courseName, String colorId) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.colorId = colorId;
    }

    public static ArrayList<Course> populateExampleCourses(){
        ArrayList<Course> c = new ArrayList<>();
        c.add(new Course(1,"GENG300","#42f4f1"));
        c.add(new Course(2,"CMPS312","#d3f441"));
        return c;
    }
}
