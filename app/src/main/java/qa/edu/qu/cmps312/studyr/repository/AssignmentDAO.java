package qa.edu.qu.cmps312.studyr.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import qa.edu.qu.cmps312.studyr.models.Assignment;

public class AssignmentDAO implements AssignmentContract {
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public AssignmentDAO(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    //add Book
    public long addAssignment(Assignment assignment) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = changeAssignmentIdToContentValues(assignment);
        long rowId = db.insert(AssignmentContract.AssignmentsTable.TABLE_NAME, null, values);
        return rowId;
    }

    public Assignment getAssignment(int assignmentId) {
        //1
        db = dbHelper.getWritableDatabase();
        String columns[] = {AssignmentsTable.COLUMN_NAME_ASSIGNMENT_TITLE, AssignmentsTable.COLUMN_NAME_ASSIGNMENT_PRIORITY, AssignmentsTable.COLUMN_NAME_ASSIGNMENT_COURSE, AssignmentsTable.COLUMN_NAME_ASSIGNMENT_DUEDATE, AssignmentsTable.COLUMN_NAME_ASSIGNMENT_DUETIME, AssignmentsTable.COLUMN_NAME_ASSIGNMENT_ID, AssignmentsTable.COLUMN_NAME_ASSIGNMENT_NOTES};
        String selection = AssignmentsTable.COLUMN_NAME_ASSIGNMENT_ID + " = ?";
        String selectionArgs[] = {String.valueOf(assignmentId)};
        Cursor cursor = db.query(AssignmentsTable.TABLE_NAME, columns, selection, selectionArgs,
                null, null, null, null);
        if (cursor.moveToFirst())
            return changeCursorToTodoObject(cursor);
        else return null;
    }

    //
    public long deleteAssignment(int assignmentId) {
        //get writable database
        db = dbHelper.getWritableDatabase();
        String whereClause = AssignmentsTable.COLUMN_NAME_ASSIGNMENT_ID + " = ?";
        String selectionArgs[] = {String.valueOf(assignmentId)};
        return db.delete(AssignmentContract.AssignmentsTable.TABLE_NAME, whereClause, selectionArgs);
    }

    public long updateAssignment(Assignment assignment) {
        //get writable database
        db = dbHelper.getWritableDatabase();
        String whereClause = AssignmentsTable.COLUMN_NAME_ASSIGNMENT_ID + " = ?";
        String selectionArgs[] = {String.valueOf(assignment.getAssignmentId())};

        //content values

        ContentValues values = changeAssignmentIdToContentValues(assignment);

        return db.update(AssignmentsTable.TABLE_NAME, values, whereClause, selectionArgs);
    }

    private ContentValues changeAssignmentIdToContentValues(Assignment assignment) {
        ContentValues values = new ContentValues();

        values.put(AssignmentsTable.COLUMN_NAME_ASSIGNMENT_ID, assignment.getAssignmentId());
        values.put(AssignmentsTable.COLUMN_NAME_ASSIGNMENT_COURSE, assignment.getCourseId());
        values.put(AssignmentsTable.COLUMN_NAME_ASSIGNMENT_DUEDATE, assignment.getDueDate());
        values.put(AssignmentsTable.COLUMN_NAME_ASSIGNMENT_DUETIME, assignment.getDueTime());
        values.put(AssignmentsTable.COLUMN_NAME_ASSIGNMENT_TITLE, assignment.getTitle());
        values.put(AssignmentsTable.COLUMN_NAME_ASSIGNMENT_NOTES, assignment.getNotes());
        values.put(AssignmentsTable.COLUMN_NAME_ASSIGNMENT_PRIORITY, assignment.getPriority());

        return values;
    }

    //
    private Assignment changeCursorToTodoObject(Cursor cursor) {
        Assignment assignment = new Assignment();
        assignment.setAssignmentId(cursor.getInt(cursor.getColumnIndex(AssignmentsTable.COLUMN_NAME_ASSIGNMENT_ID)));
        assignment.setCourseId(cursor.getInt(cursor.getColumnIndex(AssignmentsTable.COLUMN_NAME_ASSIGNMENT_COURSE)));
        assignment.setDueDate(cursor.getString(cursor.getColumnIndex(AssignmentsTable.COLUMN_NAME_ASSIGNMENT_DUEDATE)));
        assignment.setDueTime(cursor.getString(cursor.getColumnIndex(AssignmentsTable.COLUMN_NAME_ASSIGNMENT_DUETIME)));
        assignment.setTitle(cursor.getString(cursor.getColumnIndex(AssignmentsTable.COLUMN_NAME_ASSIGNMENT_TITLE)));
        assignment.setPriority(cursor.getString(cursor.getColumnIndex(AssignmentsTable.COLUMN_NAME_ASSIGNMENT_PRIORITY)));
        assignment.setNotes(cursor.getString(cursor.getColumnIndex(AssignmentsTable.COLUMN_NAME_ASSIGNMENT_NOTES)));
        return assignment;
    }

    //
    public ArrayList<Assignment> getAllAssignments() {
        ArrayList<Assignment> assignments = new ArrayList<>();

        db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + AssignmentContract.AssignmentsTable.TABLE_NAME + " ORDER BY " + AssignmentsTable.COLUMN_NAME_ASSIGNMENT_PRIORITY;

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                assignments.add(changeCursorToTodoObject(cursor));
            } while (cursor.moveToNext());
        }
        return assignments;
    }

    public ArrayList<Assignment> getAllAssignments(String type) {
        ArrayList<Assignment> assignments = new ArrayList<>();

        db = dbHelper.getReadableDatabase();
        String selectQuery;
        switch (type) {
            case "date":
                selectQuery = "SELECT * FROM " + AssignmentContract.AssignmentsTable.TABLE_NAME + " ORDER BY " + AssignmentsTable.COLUMN_NAME_ASSIGNMENT_DUEDATE;
                break;
            case "course":
                selectQuery = "SELECT * FROM " + AssignmentContract.AssignmentsTable.TABLE_NAME + " ORDER BY " + AssignmentsTable.COLUMN_NAME_ASSIGNMENT_COURSE;
                break;
            case "priority":
                selectQuery = "SELECT * FROM " + AssignmentContract.AssignmentsTable.TABLE_NAME + " ORDER BY " + AssignmentsTable.COLUMN_NAME_ASSIGNMENT_PRIORITY;
                break;
            default:
                selectQuery = "SELECT * FROM " + AssignmentContract.AssignmentsTable.TABLE_NAME + " ORDER BY " + AssignmentsTable.COLUMN_NAME_ASSIGNMENT_DUEDATE;
        }
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                assignments.add(changeCursorToTodoObject(cursor));
            } while (cursor.moveToNext());
        }
        return assignments;
    }

    public String getAssignmentsBasedonPriority(String priority) {
        ArrayList<Assignment> assignments = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + AssignmentContract.AssignmentsTable.TABLE_NAME + " WHERE " + AssignmentsTable.COLUMN_NAME_ASSIGNMENT_PRIORITY + " = " + priority;


        return selectQuery;
    }

    public ArrayList<Assignment> getAssignmentsBasedonDate() {
        ArrayList<Assignment> assignments = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + AssignmentContract.AssignmentsTable.TABLE_NAME + " GROUP BY " + AssignmentsTable.COLUMN_NAME_ASSIGNMENT_DUEDATE;

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                assignments.add(changeCursorToTodoObject(cursor));
            } while (cursor.moveToNext());
        }
        return assignments;
    }

    public ArrayList<Assignment> getAssignmentsBasedonPriority() {
        ArrayList<Assignment> assignments = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + AssignmentContract.AssignmentsTable.TABLE_NAME + " GROUP BY " + AssignmentsTable.COLUMN_NAME_ASSIGNMENT_PRIORITY;

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                assignments.add(changeCursorToTodoObject(cursor));
            } while (cursor.moveToNext());
        }
        return assignments;
    }

    public ArrayList<Assignment> getAssignmentsBasedonCourse() {
        ArrayList<Assignment> assignments = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + AssignmentContract.AssignmentsTable.TABLE_NAME + " GROUP BY " + AssignmentsTable.COLUMN_NAME_ASSIGNMENT_COURSE;

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                assignments.add(changeCursorToTodoObject(cursor));
            } while (cursor.moveToNext());
        }
        return assignments;
    }

    public ArrayList<Assignment> getAssignmentsOfCourse(int courseId) {
        ArrayList<Assignment> assignments = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + AssignmentContract.AssignmentsTable.TABLE_NAME + " WHERE " + AssignmentsTable.COLUMN_NAME_ASSIGNMENT_COURSE + "=" + courseId;

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                assignments.add(changeCursorToTodoObject(cursor));
            } while (cursor.moveToNext());
        }
        return assignments;
    }

    public long deleteAll() {
        db = dbHelper.getWritableDatabase();
        return db.delete(AssignmentsTable.TABLE_NAME, "", null);
    }
}
