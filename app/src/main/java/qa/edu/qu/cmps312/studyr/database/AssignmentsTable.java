package qa.edu.qu.cmps312.studyr.database;

public class AssignmentsTable {

    public static final String TABLE_ASSIGNMENTS = "assignments";
    public static final String COLUMN_ID = "assignmentId";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_COURSE = "courseId";
    public static final String COLUMN_DUEDATE = "dueDate";
    public static final String COLUMN_DUETIME = "dueTime";
    public static final String COLUMN_NOTES = "notes";
    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_ASSIGNMENTS + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_TITLE + " TEXT," +
                    COLUMN_COURSE + " INTEGER," +
                    COLUMN_DUEDATE + " TEXT," +
                    COLUMN_DUETIME + " TEXT," +
                    COLUMN_NOTES + " TEXT" + ");";
    public static final String SQL_DELETE =
            "DROP TABLE " + TABLE_ASSIGNMENTS;
}
