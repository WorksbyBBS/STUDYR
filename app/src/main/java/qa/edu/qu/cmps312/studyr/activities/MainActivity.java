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

        //View rowLayout = getLayoutInflater().inflate(R.layout.class_row_design,null,false);
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
                Log.i(TAG,classesInSpecificDate.size()+"ONSELECTED");
            }
        });

        Log.i(TAG,classesInSpecificDate.size()+"RIGHT BEFORE ADAPTER");


        layoutManager = new LinearLayoutManager(this);
        myRecyclerView.setLayoutManager(layoutManager);

        floatingAddButton = findViewById(R.id.floatingActionButtonClass);
        floatingAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClassDialogFragment dialogFragment = ClassDialogFragment.newInstance();
                dialogFragment.show(getSupportFragmentManager(), "MY_DIALOG");
            }
        });

//        //Spinner For Dialog
//        List<String> spinnerArray =  new ArrayList<String>();
//        for(int i=0;i<courses.size();i++)
//            spinnerArray.add(courses.get(i).getCourseName());
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//                this, R.layout.spinner_style, spinnerArray);
//
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        Spinner sItems = (Spinner) myCustomClassDialogLayout.findViewById(R.id.add_edit_class_spinner);
//        sItems.setAdapter(adapter);

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
        for (int i = 0; i < courseClasses.size(); i++) {
            //Toast.makeText(MainActivity.this,date,Toast.LENGTH_LONG).show();
            if (date.equalsIgnoreCase(courseClasses.get(i).getStartDate())) {
                classesInSpecificDate.add(courseClasses.get(i));
            }
        }
        //Toast.makeText(MainActivity.this,classesInSpecificDate.size()+"",Toast.LENGTH_LONG).show();
        classAdapter = new ClassAdapter(classesInSpecificDate,courses,this);
        myRecyclerView.setAdapter(classAdapter);
        //return classesInSpecificDate;
    }

    @Override
    public void deleteClass(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.DialogTheme);
        builder.setIcon(R.drawable.ic_warning_red_24dp);
        builder.setTitle(getString(R.string.delete_class));
        builder.setMessage(getString(R.string.delete_class_body_message));
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //todos.remove(position);
                dao.deleteClass(position);
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
        dao.addClass(dao.getAllClasses().size(), classObj);
        courseClasses = dao.getAllClasses();
        classAdapter.notifyChange(courseClasses);
        dismissFragment();    //this will remove the dialog from screen
    }

    @Override
    public void updateClass(CourseClass classObj) {

    }

    @Override
    public void dismissFragment() {

    }
}
