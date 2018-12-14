package qa.edu.qu.cmps312.studyr.repository;

import android.provider.BaseColumns;
public interface ClassContract {
    public class ClassesTable implements BaseColumns{

        public static final String TABLE_NAME = "classes";
        public static final String COLUMN_NAME_CLASS_ID = "classId";
        public static final String COLUMN_NAME_CLASS_COURSE = "courseId";
        public static final String COLUMN_NAME_CLASS_STARTTIME = "startTime";
        public static final String COLUMN_NAME_CLASS_ENDTIME = "endTime";
        public static final String COLUMN_NAME_CLASS_STARTDATE = "startDate";
        public static final String COLUMN_NAME_CLASS_ENDDATE = "endDate";
        public static final String COLUMN_NAME_CLASS_DAYS = "days";
        public static final String COLUMN_NAME_CLASS_LOCATION = "location";

    }
}
