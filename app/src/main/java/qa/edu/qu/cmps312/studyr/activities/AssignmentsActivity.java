package qa.edu.qu.cmps312.studyr.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import qa.edu.qu.cmps312.studyr.R;
import qa.edu.qu.cmps312.studyr.adapters.AssignmentAdapter;
import qa.edu.qu.cmps312.studyr.fragments.NewAssignmentFragment;
import qa.edu.qu.cmps312.studyr.models.Assignment;
import qa.edu.qu.cmps312.studyr.repository.AssignmentDAO;

public class AssignmentsActivity extends AppCompatActivity implements NewAssignmentFragment.OnFragmentInteractionListener {
    FloatingActionButton floatingAddButtonAssignment;
    private final String TAG = "AssignmentActivity";
    private Toolbar myToolbar;
    private AssignmentDAO dao;
    private ArrayList<Assignment> assignments;
    AssignmentAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignments);
        dao = new AssignmentDAO(this);
        //add menu to toolbar
        myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        floatingAddButtonAssignment = findViewById(R.id.floatingActionButtonAssignment);
        floatingAddButtonAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewAssignmentFragment newAssignmentFragment = new NewAssignmentFragment();
                newAssignmentFragment.show(getSupportFragmentManager(), "Assignment_Fragment");
            }
        });
        adapter = new AssignmentAdapter(this, getAllAssignments());

        recyclerView = findViewById(R.id.recycler_ass_list);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String itemText = item.toString();
        if(itemText.equalsIgnoreCase("schedule")){
            Intent intent = new Intent (AssignmentsActivity.this, MainActivity.class);
            startActivity(intent);
        } else if(itemText.equalsIgnoreCase("courses")){
            Intent intent = new Intent (AssignmentsActivity.this, CourseActivity.class);
            startActivity(intent);
        }
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getExtras() != null) {
        }
        assignments = dao.getAllAssignments();
        adapter.notifyChange(assignments);
    }

    @Override
    public void addAssignment(Assignment assignment) {
        dao.addAssignment(assignment);
        assignments = dao.getAllAssignments();
        adapter.notifyChange(assignments);
        dismissFragment();
    }

    @Override
    public void dismissFragment() {
///??????
    }

    public ArrayList<Assignment> getAllAssignments() {
        assignments = new ArrayList<>();
        assignments = dao.getAllAssignments();
        layoutManager = new LinearLayoutManager(this);
        return assignments;
    }
}
