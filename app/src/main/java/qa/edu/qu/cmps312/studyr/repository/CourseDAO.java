package qa.edu.qu.cmps312.studyr.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import qa.edu.qu.cmps312.studyr.models.Assignment;
import qa.edu.qu.cmps312.studyr.models.Course;

public class CourseDAO implements CourseContract, ClassContract, AssignmentContract {
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public CourseDAO(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    //add Course
    public long addCourse(Course course) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = changeCourseToContentValues(course);
        long rowId = db.insert(CourseContract.CoursesTable.TABLE_NAME, null, values);
        return rowId;
    }

    public long updateCourse(Course course) {
        //get writable database
        db = dbHelper.getWritableDatabase();
        String whereClause = CoursesTable.COLUMN_NAME_COURSE_ID + " = ?";
        String selectionArgs[] = {String.valueOf(course.getCourseId())};

        //content values

        ContentValues values = changeCourseToContentValues(course);

        return db.update(CoursesTable.TABLE_NAME, values, whereClause, selectionArgs);
    }

    //get course
    public Course getCourse(int courseId) {
        //1
        db = dbHelper.getWritableDatabase();
        String columns[] = {CoursesTable.COLUMN_NAME_COURSE_NAME, CoursesTable.COLUMN_NAME_COURSE_COLORHEX, CoursesTable.COLUMN_NAME_COURSE_ID};
        String selection = CoursesTable.COLUMN_NAME_COURSE_ID + " = ?";
        String selectionArgs[] = {String.valueOf(courseId)};
        Cursor cursor = db.query(CoursesTable.TABLE_NAME, columns, selection, selectionArgs,
                null, null, null, null);
        if (cursor.moveToFirst())
            return changeCursorToTodoObject(cursor);
        else return null;
    }

    //delete course and all its assignments and classes!!!
    public long deleteCourse(Course course) {
        //get writable database
        db=dbHelper.getWritableDatabase();
        String whereClause = CourseContract.CoursesTable.COLUMN_NAME_COURSE_ID + " = ?";
        String selectionArgs[] = {String.valueOf(course.getCourseId())};
        deleteClassesOfCourse(course.getCourseId());
        deleteAssignmentsOfCourse(course.getCourseId());
        return db.delete(CourseContract.CoursesTable.TABLE_NAME, whereClause, selectionArgs);
    }

    public void deleteClassesOfCourse(int courseId) {
        db = dbHelper.getWritableDatabase();
        String whereClause = ClassContract.ClassesTable.COLUMN_NAME_CLASS_COURSE + " = ?";
        String selectionArgs[] = {String.valueOf(courseId)};
        db.delete(ClassContract.ClassesTable.TABLE_NAME, whereClause, selectionArgs);
    }

    public void deleteAssignmentsOfCourse(int courseId) {
        db = dbHelper.getWritableDatabase();
        String whereClause = AssignmentsTable.COLUMN_NAME_ASSIGNMENT_COURSE + " = ?";
        String selectionArgs[] = {String.valueOf(courseId)};
        db.delete(AssignmentContract.AssignmentsTable.TABLE_NAME, whereClause, selectionArgs);
    }

    private ContentValues changeCourseToContentValues(Course course) {
        ContentValues values=new ContentValues();

        values.put(CoursesTable.COLUMN_NAME_COURSE_ID, course.getCourseId());
        values.put(CoursesTable.COLUMN_NAME_COURSE_NAME,course.getCourseName());
        values.put(CoursesTable.COLUMN_NAME_COURSE_COLORHEX,course.getColorId());

        return values;
    }
//
    private Course changeCursorToTodoObject(Cursor cursor) {
        Course course=new Course();
        course.setCourseId(cursor.getInt(cursor.getColumnIndex(CoursesTable.COLUMN_NAME_COURSE_ID)));
        course.setCourseName(cursor.getString(cursor.getColumnIndex(CoursesTable.COLUMN_NAME_COURSE_NAME)));
        course.setColorId(cursor.getString(cursor.getColumnIndex(CoursesTable.COLUMN_NAME_COURSE_COLORHEX)));

        return course;
    }
//
    public ArrayList<Course> getAllCourses(){
        ArrayList<Course> courses = new ArrayList<>();

        db=dbHelper.getReadableDatabase();

        String selectQuery= "SELECT * FROM "+ CoursesTable.TABLE_NAME;

        Cursor cursor=db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do{
                courses.add(changeCursorToTodoObject(cursor));
            }while(cursor.moveToNext());
        }
        return courses;
    }
}
