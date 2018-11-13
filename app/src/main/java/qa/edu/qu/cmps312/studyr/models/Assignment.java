package qa.edu.qu.cmps312.studyr.models;

public class Assignment {
    public int assignmentId;
    public String title;
    public int courseId;
    public String dueDate;
    public String dueTime;
    public String priority;
    public String notes;

    public Assignment(int assignmentId, String title, int courseId, String dueDate, String dueTime, String priority, String notes) {
        this.assignmentId = assignmentId;
        this.title = title;
        this.courseId = courseId;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
        this.priority = priority;
        this.notes = notes;
    }

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

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
