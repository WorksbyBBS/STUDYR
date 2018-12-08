package qa.edu.qu.cmps312.studyr.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import qa.edu.qu.cmps312.studyr.models.CourseClass;

public class ClassDAO implements ClassContract {
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public ClassDAO(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    public long addClass(CourseClass courseClass) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = changeCourseToContentValues(courseClass);
        long rowId = db.insert(ClassContract.ClassesTable.TABLE_NAME, null, values);
        return rowId;
    }

    //
    public long deleteClass(CourseClass courseClass) {
        //get writable database
        db = dbHelper.getWritableDatabase();
        String whereClause = ClassesTable.COLUMN_NAME_CLASS_ID + " = ?";
        String selectionArgs[] = {String.valueOf(courseClass.classId)};
        return db.delete(ClassContract.ClassesTable.TABLE_NAME, whereClause, selectionArgs);
    }

    private ContentValues changeCourseToContentValues(CourseClass courseClass) {
        ContentValues values = new ContentValues();

        values.put(ClassesTable.COLUMN_NAME_CLASS_ID, courseClass.getClassId());
        values.put(ClassesTable.COLUMN_NAME_CLASS_COURSE, courseClass.getCourseId());
        values.put(ClassesTable.COLUMN_NAME_CLASS_DAYS, courseClass.getDays());
        values.put(ClassesTable.COLUMN_NAME_CLASS_STARTDATE, courseClass.getStartDate());
        values.put(ClassesTable.COLUMN_NAME_CLASS_ENDDATE, courseClass.getEndDate());
        values.put(ClassesTable.COLUMN_NAME_CLASS_STARTTIME, courseClass.getStartTime());
        values.put(ClassesTable.COLUMN_NAME_CLASS_ENDTIME, courseClass.getEndTime());
        values.put(ClassesTable.COLUMN_NAME_CLASS_LOCATION, courseClass.getLocation());

        return values;
    }

    //
    private CourseClass changeCursorToTodoObject(Cursor cursor) {
        CourseClass courseClass = new CourseClass();
        courseClass.setCourseId(cursor.getInt(cursor.getColumnIndex(ClassesTable.COLUMN_NAME_CLASS_COURSE)));
        courseClass.setClassId(cursor.getInt(cursor.getColumnIndex(ClassesTable.COLUMN_NAME_CLASS_ID)));
        courseClass.setDays(cursor.getString(cursor.getColumnIndex(ClassesTable.COLUMN_NAME_CLASS_DAYS)));
        courseClass.setStartDate(cursor.getString(cursor.getColumnIndex(ClassesTable.COLUMN_NAME_CLASS_STARTDATE)));
        courseClass.setEndDate(cursor.getString(cursor.getColumnIndex(ClassesTable.COLUMN_NAME_CLASS_ENDDATE)));
        courseClass.setStartTime(cursor.getString(cursor.getColumnIndex(ClassesTable.COLUMN_NAME_CLASS_STARTTIME)));
        courseClass.setEndTime(cursor.getString(cursor.getColumnIndex(ClassesTable.COLUMN_NAME_CLASS_ENDTIME)));
        courseClass.setLocation(cursor.getString(cursor.getColumnIndex(ClassesTable.COLUMN_NAME_CLASS_LOCATION)));

        return courseClass;
    }

    //
    public ArrayList<CourseClass> getAllClasses() {
        ArrayList<CourseClass> classes = new ArrayList<>();

        db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + ClassContract.ClassesTable.TABLE_NAME;

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                classes.add(changeCursorToTodoObject(cursor));
            } while (cursor.moveToNext());
        }
        return classes;
    }

    public ArrayList<CourseClass> getClassesOnDay(String day) {
        ArrayList<CourseClass> specificClasses = new ArrayList<>();

        db = dbHelper.getReadableDatabase();


        String selectQuery = "SELECT * FROM " + ClassContract.ClassesTable.TABLE_NAME + " WHERE " + ClassContract.ClassesTable.COLUMN_NAME_CLASS_DAYS + " LIKE \'%" + day + "%\';";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                specificClasses.add(changeCursorToTodoObject(cursor));
            } while (cursor.moveToNext());
        }
        return specificClasses;
    }
}
