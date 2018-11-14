package qa.edu.qu.cmps312.studyr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class AssignmentsActivity extends AppCompatActivity {

    private final String TAG = "AssignmentActivity";
    private Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignments);

        //add menu to toolbar
        myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
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
}
