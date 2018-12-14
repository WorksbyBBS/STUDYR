package qa.edu.qu.cmps312.studyr.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Course implements Parcelable{

    private int courseId;
    private String courseName;
    private String colorId;
    private static int counter = 1;

    protected Course(Parcel in) {
        courseId = in.readInt();
        courseName = in.readString();
        colorId = in.readString();
    }

    public static final Creator<Course> CREATOR = new Creator<Course>() {
        @Override
        public Course createFromParcel(Parcel in) {
            return new Course(in);
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
        }
    };

    public Course() {
        this.courseId = counter;
        counter++;
    }

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

    public Course(String courseName, String colorId) {
        this.courseId = counter;
        this.courseName = courseName;
        this.colorId = colorId;
        counter++;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(courseId);
        dest.writeString(courseName);
        dest.writeString(colorId);
    }
}
