package qa.edu.qu.cmps312.studyr.repository;

import android.provider.BaseColumns;
public interface AssignmentContract {
    public class AssignmentsTable implements BaseColumns{

        public static final String TABLE_NAME = "assignments";
        public static final String COLUMN_NAME_ASSIGNMENT_ID = "assignmentId";
        public static final String COLUMN_NAME_ASSIGNMENT_TITLE = "title";
        public static final String COLUMN_NAME_ASSIGNMENT_COURSE = "courseId";
        public static final String COLUMN_NAME_ASSIGNMENT_DUEDATE = "dueDate";
        public static final String COLUMN_NAME_ASSIGNMENT_DUETIME = "dueTime";
        public static final String COLUMN_NAME_ASSIGNMENT_PRIORITY = "priority";

    }
}

