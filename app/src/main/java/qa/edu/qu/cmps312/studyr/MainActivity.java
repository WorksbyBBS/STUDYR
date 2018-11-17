package qa.edu.qu.cmps312.studyr;

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

import qa.edu.qu.cmps312.studyr.models.Course;
import qa.edu.qu.cmps312.studyr.models.Class;

public class MainActivity extends AppCompatActivity {

    private Toolbar myToolbar;
    private final String TAG = "MainActivity";
    private RecyclerView myRecyclerView;
    private ClassAdapter classAdapter;
    private ArrayList<Course> courses;
    private ArrayList<Class> classes;
    private ArrayList<Class> classesInSpecificDate;
    private View rowLayout;
    private RecyclerView.LayoutManager layoutManager;
    private CalendarView calendarView;
    private FloatingActionButton floatingAddButton;
    private View myCustomClassDialogLayout;
    private AlertDialog classDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //add menu to toolbar
        myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


        //RECYCLERVIEW PART
        calendarView = findViewById(R.id.calendarView);

        //View rowLayout = getLayoutInflater().inflate(R.layout.class_row_design,null,false);
        myRecyclerView = findViewById(R.id.classRecyclerView);

        courses = Course.populateExampleCourses();
        classes = Class.populateExampleClass();
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

        //getting the custom dialog layout and inflating it for course
        myCustomClassDialogLayout = getLayoutInflater().inflate(R.layout.add_edit_class_layout, null, false);
        classDialog = new AlertDialog.Builder(this)
                .setView(myCustomClassDialogLayout)
                .create();


        floatingAddButton = findViewById(R.id.floatingActionButtonClass);
        floatingAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classDialog.show();
            }
        });

        //Spinner For Dialog
        List<String> spinnerArray =  new ArrayList<String>();
        for(int i=0;i<courses.size();i++)
            spinnerArray.add(courses.get(i).getCourseName());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, R.layout.spinner_style, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = (Spinner) myCustomClassDialogLayout.findViewById(R.id.add_edit_class_spinner);
        sItems.setAdapter(adapter);

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
        for(int i=0;i<classes.size();i++){
            //Toast.makeText(MainActivity.this,date,Toast.LENGTH_LONG).show();
            if(date.equalsIgnoreCase(classes.get(i).getStartDate())){
                classesInSpecificDate.add(classes.get(i));
            }
        }
        //Toast.makeText(MainActivity.this,classesInSpecificDate.size()+"",Toast.LENGTH_LONG).show();
        classAdapter = new ClassAdapter(classesInSpecificDate,courses,this);
        myRecyclerView.setAdapter(classAdapter);
        //return classesInSpecificDate;
    }
}
