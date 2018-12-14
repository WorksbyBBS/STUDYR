package qa.edu.qu.cmps312.studyr.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import qa.edu.qu.cmps312.studyr.R;
import qa.edu.qu.cmps312.studyr.models.Course;
import qa.edu.qu.cmps312.studyr.models.CourseClass;
import qa.edu.qu.cmps312.studyr.repository.CourseDAO;

public class ClassDialogFragment extends android.support.v4.app.DialogFragment implements View.OnClickListener {
    public static final String CLASS_KEY = "CLASS";
    private static boolean isEdit = false;

    CourseClass classObj;
    Context context;
    ArrayList<Course> courseArrayList;
    TextView classFrag_title;
    Spinner courseSpinner;
    TextView startDate;
    TextView endDate;
    TextView startTime;
    TextView endTime;
    EditText location;

    Button saveButton;
    Button cancelButton;
    Button startDateButton;
    Button endDateButton;
    Button startTimeButton;
    Button endTimeButton;

    CheckBox checkBoxSat;
    CheckBox checkBoxSun;
    CheckBox checkBoxMon;
    CheckBox checkBoxTue;
    CheckBox checkBoxWed;
    CheckBox checkBoxThu;
    CheckBox checkBoxFri;


    View view;
    ClassDialogFragment.DialogFragmentInteraction interaction;
    CourseDAO dao;

    public interface DialogFragmentInteraction {
        void addClass(CourseClass classObj);

        void updateClass(CourseClass classObj);

        void dismissFragment();
    }

    public ClassDialogFragment() {

    }

    //This method is used when we want to add a new class
    public static ClassDialogFragment newInstance() {

        isEdit = false;    //this is used by the initializeViews method
        Bundle args = new Bundle();

        ClassDialogFragment fragment = new ClassDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //This method is used when we want to edit and delete a class
    public static ClassDialogFragment newInstance(CourseClass classObj) {

        isEdit = true;   //this is used by the initializeViews method
        Bundle args = new Bundle();
        args.putParcelable(CLASS_KEY, classObj);

        ClassDialogFragment fragment = new ClassDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof DialogFragmentInteraction)) throw new AssertionError();
        interaction = (DialogFragmentInteraction) context;
        this.context = context;
        dao = new CourseDAO(context);
        courseArrayList = dao.getAllCourses();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // the following if condition is just to round the corners of the dialog
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }


        return inflater.inflate(R.layout.add_edit_class_layout, container, false);
    }

    //This method is called after the layout is rendered and you will be able to access the layout by using the rootView object
    @Override
    public void onViewCreated(@NonNull final View rootView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);
        if (getArguments() != null && isEdit)
            this.classObj = getArguments().getParcelable(CLASS_KEY);
        else
            this.classObj = new CourseClass();

        initializeViews(rootView);

    }

    /*
        This method gets all the ids of the views
        If it is edit it populates data into each view otherwise
        It shows empty dialog with empty views that the user can add too
     */
    public void initializeViews(final View view) {
        this.view = view;

        classFrag_title = view.findViewById(R.id.add_edit_class_main_title);
        courseSpinner = view.findViewById(R.id.add_edit_class_spinner);
        startDate = view.findViewById(R.id.add_edit_class_SDATE);
        endDate = view.findViewById(R.id.add_edit_class_EDATE);
        startTime = view.findViewById(R.id.add_edit_class_STIME);
        endTime = view.findViewById(R.id.add_edit_class_ETIME);
        location = view.findViewById(R.id.add_edit_class_location);

        //buttons
        saveButton = view.findViewById(R.id.add_edit_class_save_btn);
        cancelButton = view.findViewById(R.id.add_edit_class_cancel_btn);
        startDateButton = view.findViewById(R.id.add_edit_class_SDATE_BTN);
        endDateButton = view.findViewById(R.id.add_edit_class_EDATE_BTN);
        startTimeButton = view.findViewById(R.id.add_edit_class_STIME_BTN);
        endTimeButton = view.findViewById(R.id.add_edit_class_ETIME_BTN);

        checkBoxSat = view.findViewById(R.id.checkBoxSat);
        checkBoxSun = view.findViewById(R.id.checkBoxSun);
        checkBoxMon = view.findViewById(R.id.checkBoxMon);
        checkBoxTue = view.findViewById(R.id.checkBoxTue);
        checkBoxWed = view.findViewById(R.id.checkBoxWed);
        checkBoxThu = view.findViewById(R.id.checkBoxThu);
        checkBoxFri = view.findViewById(R.id.checkBoxFri);

        cancelButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        startDateButton.setOnClickListener(this);
        endDateButton.setOnClickListener(this);
        startTimeButton.setOnClickListener(this);
        endTimeButton.setOnClickListener(this);

        //Spinner For Dialog
        List<String> spinnerArray = new ArrayList<String>();
        for (int i = 0; i < courseArrayList.size(); i++) {
            Log.i("CLASSFRAG", courseArrayList.get(i).getCourseName());
            spinnerArray.add(courseArrayList.get(i).getCourseName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.spinner_style, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Spinner sItems = (Spinner) view.findViewById(R.id.add_edit_class_spinner);
        courseSpinner.setAdapter(adapter);
        //If populate necessary fields
        if (isEdit) {
            classFrag_title.setText("Edit Class");
            startDate.setText(classObj.getStartDate());
            endDate.setText(classObj.getEndDate());
            startTime.setText(classObj.getStartTime());
            endTime.setText(classObj.getEndTime());
            location.setText(classObj.getLocation());

            int index = 0;
            for (int i = 0; i < courseArrayList.size(); i++) {
                if (courseArrayList.get(i).getCourseId() == classObj.getCourseId())
                    index = i;
            }
            courseSpinner.setSelection(index);

            //split days and check boxes
            String[] days = classObj.getDays().split("/");
            for (int i = 0; i < days.length; i++) {
                String checkBoxName = "checkBox" + days[i];
                int id = getResources().getIdentifier(checkBoxName, "id", getActivity().getPackageName());
                CheckBox checkBox = view.findViewById(id);
                checkBox.setChecked(true);
            }


        } else {
            classFrag_title.setText("Add Class");
            startDate.setText("--/--/--");
            endDate.setText("--/--/--");
            startTime.setText("00:00:00");
            endTime.setText("00:00:00");
        }

    }

    //listener method
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_edit_class_SDATE_BTN:
                final Calendar calendar = Calendar.getInstance();
                int mYear = calendar.get(Calendar.YEAR);
                int mMonth = calendar.get(Calendar.MONTH);
                int mDay = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.DateTimeDialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        startDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;
            case R.id.add_edit_class_EDATE_BTN:
                final Calendar calendar2 = Calendar.getInstance();
                int mYear2 = calendar2.get(Calendar.YEAR);
                int mMonth2 = calendar2.get(Calendar.MONTH);
                int mDay2 = calendar2.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog2 = new DatePickerDialog(getActivity(), R.style.DateTimeDialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        endDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, mYear2, mMonth2, mDay2);
                datePickerDialog2.show();
                break;
            case R.id.add_edit_class_STIME_BTN:
                final Calendar calendar3 = Calendar.getInstance();
                int hr = calendar3.get(Calendar.HOUR_OF_DAY);
                int min = calendar3.get(Calendar.MILLISECOND);
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), R.style.DateTimeDialogTheme, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String AM_PM;
                        if (hourOfDay < 12) {
                            AM_PM = "AM";
                        } else {
                            AM_PM = "PM";
                        }
                        startTime.setText(hourOfDay + ":" + minute + (minute == 0 ? "0" : "") + " " + AM_PM);
                    }
                }, hr, min, false);
                timePickerDialog.show();
                break;
            case R.id.add_edit_class_ETIME_BTN:
                final Calendar calendar4 = Calendar.getInstance();
                int hr4 = calendar4.get(Calendar.HOUR_OF_DAY);
                int min4 = calendar4.get(Calendar.MILLISECOND);
                TimePickerDialog timePickerDialog2 = new TimePickerDialog(getActivity(), R.style.DateTimeDialogTheme, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String AM_PM;
                        if (hourOfDay < 12) {
                            AM_PM = "AM";
                        } else {
                            AM_PM = "PM";
                        }
                        endTime.setText(hourOfDay + ":" + minute + (minute == 0 ? "0" : "") + " " + AM_PM);
                    }
                }, hr4, min4, false);
                timePickerDialog2.show();
                break;

            case R.id.add_edit_class_save_btn:

                classObj.setCourseId(selectedCourseId(courseSpinner.getSelectedItem().toString()));
                classObj.setStartDate(startDate.getText().toString());
                classObj.setEndDate(endDate.getText().toString());
                classObj.setStartTime(startTime.getText().toString());
                classObj.setEndTime(endTime.getText().toString());
                classObj.setLocation(location.getText().toString());
                classObj.setDays(getCheckedDays());

                if (validInput()) {
                    if (isEdit)
                        interaction.updateClass(classObj);
                    else
                        interaction.addClass(classObj);
                    this.dismiss();
                } else {
                    Toast.makeText(getActivity(), "Please fill out all details!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.add_edit_class_cancel_btn:
                this.dismiss();
                interaction.dismissFragment();
                break;

        }
    }

    public boolean validInput() {
        if (location != null
                && !endTime.getText().toString().equalsIgnoreCase("00:00:00")
                && !startTime.getText().toString().equalsIgnoreCase("00:00:00")
                && !startDate.getText().toString().equalsIgnoreCase("--/--/--")
                && !endDate.getText().toString().equalsIgnoreCase("--/--/--")
                && (checkBoxSat.isChecked() || checkBoxSun.isChecked() || checkBoxMon.isChecked()
                || checkBoxTue.isChecked() || checkBoxWed.isChecked() || checkBoxThu.isChecked() || checkBoxFri.isChecked()))
            return true;
        return false;
    }

    public int selectedCourseId(String selectedCourseName) {
        for (int i = 0; i < courseArrayList.size(); i++) {
            if (courseArrayList.get(i).getCourseName().equalsIgnoreCase(selectedCourseName))
                return courseArrayList.get(i).getCourseId();
        }
        return 0;
    }

    public String getCheckedDays() {
        String days = "";
        if (checkBoxSat.isChecked())
            days += "Sat/";
        if (checkBoxSun.isChecked())
            days += "Sun/";
        if (checkBoxMon.isChecked())
            days += "Mon/";
        if (checkBoxTue.isChecked())
            days += "Tue/";
        if (checkBoxWed.isChecked())
            days += "Wed/";
        if (checkBoxThu.isChecked())
            days += "Thu/";
        if (checkBoxFri.isChecked())
            days += "Fri/";
        return days;
    }
}
