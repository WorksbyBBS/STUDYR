package qa.edu.qu.cmps312.studyr;

import android.content.Intent;
import com.pes.androidmaterialcolorpickerdialog.ColorPicker;
import com.pes.androidmaterialcolorpickerdialog.ColorPickerCallback;

import android.support.annotation.ColorInt;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.support.design.widget.FloatingActionButton;
import android.widget.ImageView;
import android.widget.Toast;

import qa.edu.qu.cmps312.studyr.models.Course;

public class CourseActivity extends AppCompatActivity {

    private Toolbar myToolbar;
    private final String TAG = "CourseActivity";
    private AlertDialog courseDialog;
    private Button saveCourseButton;
    private Button cancelButton;
    private Button chooseColorButton;
    private ImageView colorPickedImageView;
    private FloatingActionButton floatingAddButton;
    private View myCustomCourseDialogLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        //add menu to toolbar
        myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //getting the custom dialog layout and inflating it for course
        myCustomCourseDialogLayout = getLayoutInflater().inflate(R.layout.add_edit_course_layout, null, false);
        courseDialog = new AlertDialog.Builder(this)
                .setView(myCustomCourseDialogLayout)
                .create();

        floatingAddButton = findViewById(R.id.floatingActionButtonCourse);
        floatingAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courseDialog.show();
            }
        });

        colorPickedImageView = myCustomCourseDialogLayout.findViewById(R.id.add_edit_chosen_color_imageView);
        chooseColorButton = myCustomCourseDialogLayout.findViewById(R.id.add_edit_course_choose_color);

        chooseColorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ColorPicker cp = new ColorPicker(CourseActivity.this);
                cp.show();
                cp.enableAutoClose(); // Enable auto-dismiss for the dialog
                cp.setCallback(new ColorPickerCallback() {
                    @Override
                    public void onColorChosen(@ColorInt int color) {
                        // Do whatever you want
                        // Examples
                        Log.d("Pure Hex", Integer.toHexString(color));

                        colorPickedImageView.setColorFilter(color);

                        // If the auto-dismiss option is not enable (disabled as default) you have to manually dimiss the dialog
                        // cp.dismiss();
                    }
                });
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
            Intent intent = new Intent (CourseActivity.this, AssignmentsActivity.class);
            startActivity(intent);
        } else if(itemText.equalsIgnoreCase("schedule")){
            Intent intent = new Intent (CourseActivity.this, MainActivity.class);
            startActivity(intent);
        }
        return true;
    }
}
