package qa.edu.qu.cmps312.studyr.database;

public class CoursesTable {
    public static final String TABLE_COURSES = "courses";
    public static final String COLUMN_ID = "courseId";
    public static final String COLUMN_NAME = "courseName";
    public static final String COLUMN_COLORID = "colorId";
    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_COURSES + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME + " TEXT," +
                    COLUMN_COLORID + " INTEGER" + ");";
    public static final String SQL_DELETE =
            "DROP TABLE " + TABLE_COURSES;
}
