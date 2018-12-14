package qa.edu.qu.cmps312.studyr.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import qa.edu.qu.cmps312.studyr.R;
import qa.edu.qu.cmps312.studyr.adapters.ClassAdapter;
import qa.edu.qu.cmps312.studyr.fragments.ClassDialogFragment;
import qa.edu.qu.cmps312.studyr.models.Course;
import qa.edu.qu.cmps312.studyr.models.CourseClass;
import qa.edu.qu.cmps312.studyr.repository.ClassDAO;
import qa.edu.qu.cmps312.studyr.repository.CourseDAO;

public class MainActivity extends AppCompatActivity implements ClassDialogFragment.DialogFragmentInteraction, ClassAdapter.AdapterInteraction {

    private Toolbar myToolbar;
    private final String TAG = "MainActivity";
    private RecyclerView myRecyclerView;
    private ClassAdapter classAdapter;
    private ArrayList<Course> courses;
    private ArrayList<CourseClass> courseClasses;
    private ArrayList<CourseClass> classesInSpecificDate;
    private View rowLayout;
    private RecyclerView.LayoutManager layoutManager;
    private CalendarView calendarView;
    private FloatingActionButton floatingAddButton;
    private View myCustomClassDialogLayout;
    private AlertDialog classDialog;

    private ClassDAO dao;
    private CourseDAO courseDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dao = new ClassDAO(this);
        courseDAO = new CourseDAO(this);

        //add menu to toolbar
        myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


        //RECYCLERVIEW PART
        calendarView = findViewById(R.id.calendarView);

        myRecyclerView = findViewById(R.id.classRecyclerView);

        courses = courseDAO.getAllCourses();
        courseClasses = dao.getAllClasses();
        classesInSpecificDate = new ArrayList<>();

        //for today when first launching activity
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/M/yyyy");
        String formattedDate = df.format(c);
        setSpecificClasses(formattedDate);

        //for changing dates
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                classesInSpecificDate.clear();
                String date = String.format(dayOfMonth+"/"+(month+1)+"/"+year);
                setSpecificClasses(date);
            }
        });


        layoutManager = new LinearLayoutManager(this);
        myRecyclerView.setLayoutManager(layoutManager);

        classAdapter = new ClassAdapter(classesInSpecificDate, courses, this);
        myRecyclerView.setAdapter(classAdapter);

        floatingAddButton = findViewById(R.id.floatingActionButtonClass);
        floatingAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClassDialogFragment dialogFragment = ClassDialogFragment.newInstance();
                dialogFragment.show(getSupportFragmentManager(), "MY_DIALOG");
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String itemText = item.toString();
        if(itemText.equalsIgnoreCase("assignments")){
            Intent intent = new Intent (MainActivity.this, AssignmentsActivity.class);
            startActivity(intent);
        } else if(itemText.equalsIgnoreCase("courses")){
            Intent intent = new Intent (MainActivity.this, CourseActivity.class);
            startActivity(intent);
        }
        return true;
    }

    public void setSpecificClasses(String date) {

        //get all classes in this specific day 'NAME' i.e: all classes on SUNday
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date chosenDate = null;
        Date startDate = null;
        Date endDate = null;
        try {
            chosenDate = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            }
        String dayOfWeek = new SimpleDateFormat("EEE").format(chosenDate);
        ArrayList<CourseClass> classesInSpecificDays = dao.getClassesOnDay(dayOfWeek);
        Log.i("Mainact", classesInSpecificDays.size() + "");


        //then for each on this list, check if it falls within the start and end range
        for (int i = 0; i < classesInSpecificDays.size(); i++) {
            try {
                startDate = dateFormat.parse(classesInSpecificDays.get(i).getStartDate());
                endDate = dateFormat.parse(classesInSpecificDays.get(i).getEndDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (!chosenDate.before(startDate) && !chosenDate.after(endDate))
                classesInSpecificDate.add(classesInSpecificDays.get(i));
        }

        Log.i("Mainact", classesInSpecificDate.size() + " on " + chosenDate.toString());

        //then set adapter
        classAdapter = new ClassAdapter(classesInSpecificDate,courses,this);
        myRecyclerView.setAdapter(classAdapter);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getExtras() != null) {
        }
        courseClasses = dao.getAllClasses();
        classAdapter.notifyChange(courseClasses);
    }

    @Override
    public void deleteClass(CourseClass courseClass) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.DialogTheme);
        builder.setIcon(R.drawable.ic_warning_red_24dp);
        builder.setTitle(getString(R.string.delete_class));
        builder.setMessage(getString(R.string.delete_class_body_message));
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dao.deleteClass(courseClass);
                Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                courseClasses = dao.getAllClasses();
                classAdapter.notifyChange(courseClasses);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
// the following if condition is just to round the corners of the dialog
        if (alertDialog != null && alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_background);
            //alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        alertDialog.show();
    }

    @Override
    public void editClass(CourseClass courseClass) {
        ClassDialogFragment dialogFragment = ClassDialogFragment
                .newInstance(courseClass);

        dialogFragment.show(getSupportFragmentManager(), "MY_DIALOG");
    }

    @Override
    public void addClass(CourseClass classObj) {
        Log.i("MAINACT", courseClasses.size() + "");
        dao.addClass(classObj);
        courseClasses = dao.getAllClasses();
        Log.i("MAINACT", courseClasses.size() + "");
        classAdapter.notifyChange(courseClasses);
        dismissFragment();    //this will remove the dialog from screen
    }

    @Override
    public void updateClass(CourseClass classObj) {
        dao.updateClass(classObj);
        courseClasses = dao.getAllClasses();
        classAdapter.notifyChange(courseClasses);
//        adapter.notifyDataSetChanged();
        dismissFragment();
    }

    @Override
    public void dismissFragment() {

    }
}
