package qa.edu.qu.cmps312.studyr.models;

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
}
