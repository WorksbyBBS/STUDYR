package qa.edu.qu.cmps312.studyr.fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.pavelsikun.vintagechroma.ChromaDialog;
import com.pavelsikun.vintagechroma.ChromaUtil;
import com.pavelsikun.vintagechroma.IndicatorMode;
import com.pavelsikun.vintagechroma.colormode.ColorMode;

import android.widget.Toast;

import qa.edu.qu.cmps312.studyr.R;
import qa.edu.qu.cmps312.studyr.models.Course;

public class CourseDialogFragment extends android.support.v4.app.DialogFragment implements View.OnClickListener{
    public static final String COURSE_KEY = "COURSE";
    private static boolean isEdit = false;

    Course course;
    Activity act = getActivity();
    EditText courseTitle;
    TextView courseFrag_title;
    TextView chosenColorText;
    ImageView courseColor;
    Button chooseColorButton;
    Button cancelButton;
    Button saveButton;
    View view;
    DialogFragmentInteraction interaction;

    public interface DialogFragmentInteraction {
        void addCourse(Course course);
        void updateCourse(Course course);
        void dismissFragment();
    }

    public CourseDialogFragment() {
        // Required empty public constructor
    }

    //This method is used when we want to add a new course
    public static CourseDialogFragment newInstance() {

        isEdit = false;    //this is used by the initializeViews method
        Bundle args = new Bundle();

        CourseDialogFragment fragment = new CourseDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //This method is used when we want to edit and delete a course
    public static CourseDialogFragment newInstance(Course course) {

        isEdit = true;   //this is used by the initializeViews method
        Bundle args = new Bundle();
        args.putParcelable(COURSE_KEY, course);

        CourseDialogFragment fragment = new CourseDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof DialogFragmentInteraction)) throw new AssertionError();
        interaction = (DialogFragmentInteraction) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // the following if condition is just to round the corners of the dialog
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }


        return inflater.inflate(R.layout.add_edit_course_layout, container, false);
    }

    //This method is called after the layout is rendered and you will be able to access the layout by using the rootView object
    @Override
    public void onViewCreated(@NonNull final View rootView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);
        if (getArguments() != null && isEdit)
            this.course = getArguments().getParcelable(COURSE_KEY);
        else
            this.course = new Course();

        initializeViews(rootView);

    }

    /*
        This method gets all the ids of the views
        If it is edit it populates data into each view otherwise
        It shows empty dialog with empty views that the user can add too
     */
    public void initializeViews(final View view) {
        this.view = view;
        courseTitle = view.findViewById(R.id.add_edit_course_titleEDIT);
        courseFrag_title = view.findViewById(R.id.add_edit_courseFrag_title);
        chosenColorText = view.findViewById(R.id.add_edit_course_chosen_color_TEXT);
        courseColor = view.findViewById(R.id.add_edit_chosen_color_imageView);
        chooseColorButton = view.findViewById(R.id.add_edit_course_choose_color);
        cancelButton = view.findViewById(R.id.add_edit_course_cancel_button);
        saveButton = view.findViewById(R.id.add_edit_course_save_button);

        chooseColorButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);


        //If populate necessary fields
        if (isEdit) {
            courseFrag_title.setText("Edit Course");
            courseTitle.setText(course.getCourseName());
            chosenColorText.setText(course.getColorId());
            courseColor.setColorFilter(Color.parseColor(course.getColorId()));

        } else {
            courseFrag_title.setText("Add Course");
        }

    }

    //listener method
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_edit_course_choose_color:
                String startingColor = "#d5d5d5";
                if(course.getColorId()!=null)
                    startingColor = course.getColorId();

                new ChromaDialog.Builder()
                        .initialColor(Color.parseColor(startingColor))
                        .colorMode(ColorMode.RGB) // RGB, ARGB, HVS, CMYK, CMYK255, HSL
                        .indicatorMode(IndicatorMode.HEX) //HEX or DECIMAL; Note that (HSV || HSL || CMYK) && IndicatorMode.HEX is a bad idea
                        .onColorSelected(color ->
                                onColorSelection(ChromaUtil.getFormattedColorString(color,false)))
                        .create()
                        .show(getFragmentManager(), "ChromaDialog");
                break;
            case R.id.add_edit_course_save_button:
                course.setCourseName(courseTitle.getText().toString());
                course.setColorId(chosenColorText.getText().toString());
                //Log.i("COURSEFRAG",course.getColorId());
                if (validInput()) {
                    if (isEdit)
                        interaction.updateCourse(course);
                    else
                        interaction.addCourse(course);
                    this.dismiss();
                } else {
                    Toast.makeText(getActivity(), "Please Fill Out all Details!", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.add_edit_course_cancel_button:
                this.dismiss();
                interaction.dismissFragment();
                break;

        }
    }

    public boolean validInput() {
        if (courseTitle != null)
            return true;
        return false;
    }

    public void onColorSelection(String color){
        chosenColorText.setText(color);
        course.setColorId(color);
        courseColor.setColorFilter(Color.parseColor(color));
    }

}
