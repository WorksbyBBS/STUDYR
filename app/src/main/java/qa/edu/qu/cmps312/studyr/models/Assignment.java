package qa.edu.qu.cmps312.studyr.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Assignment implements Parcelable{
    public int assignmentId;
    public String title;
    public int courseId;
    public String dueDate;
    public String dueTime;
    public String notes;
    public String priority;
    private static int counter = 1;


    public Assignment(String title, int courseId, String dueDate, String dueTime, String notes, String priority) {
        this.assignmentId = counter;
        this.title = title;
        this.courseId = courseId;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
        this.notes = notes;
        this.priority = priority;
        counter++;
    }

    protected Assignment(Parcel in) {
        assignmentId = in.readInt();
        title = in.readString();
        courseId = in.readInt();
        dueDate = in.readString();
        dueTime = in.readString();
        notes = in.readString();
        priority = in.readString();
    }

    public Assignment() {
        this.assignmentId = counter;
        counter++;
    }

    public static final Creator<Assignment> CREATOR = new Creator<Assignment>() {
        @Override
        public Assignment createFromParcel(Parcel in) {
            return new Assignment(in);
        }

        @Override
        public Assignment[] newArray(int size) {
            return new Assignment[size];
        }
    };

    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getDueTime() {
        return dueTime;
    }

    public void setDueTime(String dueTime) {
        this.dueTime = dueTime;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(assignmentId);
        dest.writeString(title);
        dest.writeInt(courseId);
        dest.writeString(dueDate);
        dest.writeString(dueTime);
        dest.writeString(notes);
        dest.writeString(priority);
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "assignmentId=" + assignmentId +
                ", title='" + title + '\'' +
                ", courseId=" + courseId +
                ", dueDate='" + dueDate + '\'' +
                ", dueTime='" + dueTime + '\'' +
                ", notes='" + notes + '\'' +
                ", priority='" + priority + '\'' +
                '}';
    }
}
