package qa.edu.qu.cmps312.studyr.database;

public class ClassesTable {

    public static final String TABLE_CLASSES = "classes";
    public static final String COLUMN_ID = "classId";
    public static final String COLUMN_COURSE = "courseId";
    public static final String COLUMN_STARTTIME = "startTime";
    public static final String COLUMN_ENDTIME = "endTime";
    public static final String COLUMN_STARTDATE = "startDate";
    public static final String COLUMN_ENDDATE = "endDate";
    public static final String COLUMN_DAYS = "days";
    public static final String COLUMN_LOCATION = "location";
    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_CLASSES + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_COURSE + " INTEGER," +
                    COLUMN_STARTTIME + " TEXT," +
                    COLUMN_ENDTIME + " TEXT," +
                    COLUMN_STARTDATE + " TEXT," +
                    COLUMN_ENDDATE + " TEXT," +
                    COLUMN_LOCATION + " TEXT," +
                    COLUMN_DAYS + " TEXT" + ");";
    public static final String SQL_DELETE =
            "DROP TABLE " + TABLE_CLASSES;

}
