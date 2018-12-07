package qa.edu.qu.cmps312.studyr.activities;

import android.content.Intent;
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
import android.widget.Button;
import android.support.design.widget.FloatingActionButton;
import android.widget.ImageView;

import java.util.ArrayList;

import qa.edu.qu.cmps312.studyr.R;
import qa.edu.qu.cmps312.studyr.adapters.CourseAdapter;
import qa.edu.qu.cmps312.studyr.fragments.CourseDialogFragment;
import qa.edu.qu.cmps312.studyr.models.Course;
import qa.edu.qu.cmps312.studyr.repository.CourseDAO;

public class CourseActivity extends AppCompatActivity implements CourseDialogFragment.DialogFragmentInteraction, CourseAdapter.AdapterInteraction {

    private Toolbar myToolbar;
    private final String TAG = "CourseActivity";
    private FloatingActionButton floatingAddButton;
    private View myCustomCourseDialogLayout;
    private RecyclerView myRecyclerView;
    private CourseAdapter courseAdapter;
    private ArrayList<Course> courses;
    private ArrayList<Integer> totalAssignments;
    private View rowLayout;
    private RecyclerView.LayoutManager layoutManager;

    private CourseDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        dao= new CourseDAO(this);

        //add menu to toolbar
        myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        floatingAddButton = findViewById(R.id.floatingActionButtonCourse);
        floatingAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CourseDialogFragment dialogFragment = CourseDialogFragment.newInstance();
                dialogFragment.show(getSupportFragmentManager(), "MY_DIALOG");
            }
        });


        //RECYCLERVIEW PART
        View rowLayout = getLayoutInflater().inflate(R.layout.course_row_design,null,false);
        myRecyclerView = findViewById(R.id.courseRecyclerView);

        int counter = 0;
        courses = dao.getAllCourses();
        totalAssignments = new ArrayList<>();
        totalAssignments.add(counter++);

        courseAdapter = new CourseAdapter(courses,totalAssignments, this);

        layoutManager = new LinearLayoutManager(this);
        myRecyclerView.setLayoutManager(layoutManager);
        myRecyclerView.setAdapter(courseAdapter);



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
            Intent intent = new Intent (CourseActivity.this, AssignmentsActivity.class);
            startActivity(intent);
        } else if(itemText.equalsIgnoreCase("schedule")){
            Intent intent = new Intent (CourseActivity.this, MainActivity.class);
            startActivity(intent);
        }
        return true;
    }

    @Override
    public void deleteCourse(int position) {
        
    }

    @Override
    public void editCourse(Course course){
        CourseDialogFragment dialogFragment = CourseDialogFragment
                .newInstance(course);

        dialogFragment.show(getSupportFragmentManager(), "MY_DIALOG");
    }
    @Override
    public void addCourse(Course course) {
        dao.addCourse(dao.getAllCourses().size(),course);
        courses = dao.getAllCourses();
        courseAdapter.notifyChange(courses);
        dismissFragment();    //this will remove the dialog from screen

    }

    @Override
    public void updateCourse(Course course) {

    }

    @Override
    public void dismissFragment() {

    }
}
