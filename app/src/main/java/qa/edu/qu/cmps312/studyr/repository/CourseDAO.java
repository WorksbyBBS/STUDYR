package qa.edu.qu.cmps312.studyr.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import qa.edu.qu.cmps312.studyr.models.Course;

public class CourseDAO implements CourseContract{
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public CourseDAO(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    //add Book
    public long addCourse(int id,Course course){
        db = dbHelper.getWritableDatabase();
        ContentValues values = changeCourseToContentValues(id,course);
        long rowId=db.insert(CoursesTable.TABLE_NAME,null,values);
        return rowId;
    }
//
//    public long deleteTodo(int todoId){
//        //get writable database
//        db=dbHelper.getWritableDatabase();
//        String whereClause=ToDoTable.COLUMN_NAME_TODO_ID+" = ?";
//        String selectionArgs[]= {String.valueOf(todoId)};
//        return   db.delete(ToDoTable.TABLE_NAME,whereClause,selectionArgs);
//    }

    private ContentValues changeCourseToContentValues(int id,Course course){
        ContentValues values=new ContentValues();

        values.put(CoursesTable.COLUMN_NAME_COURSE_ID,id);
        values.put(CoursesTable.COLUMN_NAME_COURSE_NAME,course.getCourseName());
        values.put(CoursesTable.COLUMN_NAME_COURSE_COLORHEX,course.getCourseId());

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
