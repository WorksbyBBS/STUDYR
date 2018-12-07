package qa.edu.qu.cmps312.studyr.repository;

import android.provider.BaseColumns;
public interface CourseContract {
    public class CoursesTable implements BaseColumns{
        public static final String TABLE_NAME = "courses";
        public static final String COLUMN_NAME_COURSE_ID = "courseId";
        public static final String COLUMN_NAME_COURSE_NAME = "courseName";
        public static final String COLUMN_NAME_COURSE_COLORHEX = "colorHex";

    }
}
