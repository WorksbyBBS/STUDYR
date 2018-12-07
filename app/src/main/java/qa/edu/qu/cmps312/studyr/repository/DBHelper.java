package qa.edu.qu.cmps312.studyr.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper implements CourseContract, ClassContract, AssignmentContract{

    public static final String DATABASE_NAME = "studyr.db";
    public static final int VERSION = 1;


    //creating tables
    private static final String SQL_CREATE_COURSE_TABLE = "CREATE TABLE "+
            CoursesTable.TABLE_NAME+"("+
            CoursesTable._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            CoursesTable.COLUMN_NAME_COURSE_ID+" INTEGER, "+
            CoursesTable.COLUMN_NAME_COURSE_NAME+" TEXT , "+
            CoursesTable.COLUMN_NAME_COURSE_COLORHEX+" TEXT);";

    private static final String SQL_CREATE_CLASS_TABLE = "CREATE TABLE "+
            ClassesTable.TABLE_NAME+"("+
            ClassesTable._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            ClassesTable.COLUMN_NAME_CLASS_ID+" INTEGER, "+
            ClassesTable.COLUMN_NAME_CLASS_COURSE+" TEXT , "+
            ClassesTable.COLUMN_NAME_CLASS_STARTTIME+" TEXT , "+
            ClassesTable.COLUMN_NAME_CLASS_ENDTIME+" TEXT , "+
            ClassesTable.COLUMN_NAME_CLASS_STARTDATE+" TEXT , "+
            ClassesTable.COLUMN_NAME_CLASS_ENDDATE+" TEXT , "+
            ClassesTable.COLUMN_NAME_CLASS_DAYS+" TEXT , "+
            ClassesTable.COLUMN_NAME_CLASS_LOCATION+" TEXT);";

    private static final String SQL_CREATE_ASSIGNMENT_TABLE = "CREATE TABLE "+
            AssignmentsTable.TABLE_NAME+"("+
            AssignmentsTable._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            AssignmentsTable.COLUMN_NAME_ASSIGNMENT_ID+" INTEGER, "+
            AssignmentsTable.COLUMN_NAME_ASSIGNMENT_TITLE+" TEXT , "+
            AssignmentsTable.COLUMN_NAME_ASSIGNMENT_COURSE+" TEXT , "+
            AssignmentsTable.COLUMN_NAME_ASSIGNMENT_DUEDATE+" TEXT , "+
            AssignmentsTable.COLUMN_NAME_ASSIGNMENT_DUETIME+" TEXT);";

    //dropping tables

    public static final String SQL_DELETE_COURSES_TABLE = "DROP TABLE IF EXISTS "+CoursesTable.TABLE_NAME+";";
    public static final String SQL_DELETE_CLASSES_TABLE = "DROP TABLE IF EXISTS "+ClassesTable.TABLE_NAME+";";
    public static final String SQL_DELETE_ASSIGNMENTS_TABLE = "DROP TABLE IF EXISTS "+AssignmentsTable.TABLE_NAME+";";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_COURSE_TABLE);
        db.execSQL(SQL_CREATE_CLASS_TABLE);
        db.execSQL(SQL_CREATE_ASSIGNMENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ASSIGNMENTS_TABLE);
        db.execSQL(SQL_CREATE_CLASS_TABLE);
        db.execSQL(SQL_DELETE_COURSES_TABLE);
        onCreate(db);
    }
}
