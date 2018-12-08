package qa.edu.qu.cmps312.studyr.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class CourseClass implements Parcelable {

    public int classId;
    public int courseId;
    public String startTime;
    public String endTime;
    public String startDate;
    public String endDate;
    public String days;
    public String location;

    public CourseClass(int classId, int courseId, String startTime, String endTime, String startDate, String endDate, String days, String location) {
        this.classId = classId;
        this.courseId = courseId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startDate = startDate;
        this.endDate = endDate;
        this.days = days;
        this.location = location;
    }

    public CourseClass() {
    }

    protected CourseClass(Parcel in) {
        classId = in.readInt();
        courseId = in.readInt();
        startTime = in.readString();
        endTime = in.readString();
        startDate = in.readString();
        endDate = in.readString();
        days = in.readString();
        location = in.readString();
    }

    public static final Creator<CourseClass> CREATOR = new Creator<CourseClass>() {
        @Override
        public CourseClass createFromParcel(Parcel in) {
            return new CourseClass(in);
        }

        @Override
        public CourseClass[] newArray(int size) {
            return new CourseClass[size];
        }
    };

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public static ArrayList<CourseClass> populateExampleClass() {
        ArrayList<CourseClass> c = new ArrayList<>();
        c.add(new CourseClass(1, 1, "8:00", "8:50", "17/11/2018", "20/11/2018", "Sat,Sun,Tue,Thurs", "C07-224"));
        c.add(new CourseClass(2, 1, "11:00", "11:50", "18/11/2018", "20/11/2018", "Sat,Sun,Tue,Thurs", "C07-250"));
        c.add(new CourseClass(3, 2, "11:00", "11:50", "19/11/2018", "20/11/2018", "Mon", "C07-105"));
        return c;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(classId);
        dest.writeInt(courseId);
        dest.writeString(startTime);
        dest.writeString(endTime);
        dest.writeString(startDate);
        dest.writeString(endDate);
        dest.writeString(days);
        dest.writeString(location);
    }
}
