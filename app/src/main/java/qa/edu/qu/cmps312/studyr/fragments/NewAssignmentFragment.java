package qa.edu.qu.cmps312.studyr.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import qa.edu.qu.cmps312.studyr.R;
import qa.edu.qu.cmps312.studyr.models.Assignment;
import qa.edu.qu.cmps312.studyr.models.Course;
import qa.edu.qu.cmps312.studyr.repository.CourseDAO;


public class NewAssignmentFragment extends android.support.v4.app.DialogFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    Assignment assignment;
    EditText titleEdt, notesEdt;
    Spinner courseSpinner;
    Context context;
    ArrayList<Course> courseArrayList;
    CourseDAO dao;
    Button dueDateBtn, dueTimeBtn, cancelBtn, doneBtn;
    RadioGroup priorityRG;
    RadioButton highRB, mediumRB, lowRB;
    View view;
    TextView dialog_title_tv;
    private static boolean isEdit = false;

    private static final String ASSIGNMENT_KEY = "Assignment";

    private OnFragmentInteractionListener mListener;

    public NewAssignmentFragment() {

        // Required empty public constructor
    }

    public static NewAssignmentFragment newInstance() {
        isEdit = false;
        NewAssignmentFragment fragment = new NewAssignmentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    // TODO: Rename and change types and number of parameters
    public static NewAssignmentFragment newInstance(Assignment assignment) {
        isEdit = true;
        NewAssignmentFragment fragment = new NewAssignmentFragment();
        Bundle args = new Bundle();
        args.putParcelable(ASSIGNMENT_KEY, assignment);
        fragment.setArguments(args);
        return fragment;
    }

    //This method is called after the layout is rendered and you will be able to access the layout by using the rootView object
    @Override
    public void onViewCreated(@NonNull final View rootView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);
        if (getArguments() != null && isEdit) {
            this.assignment = getArguments().getParcelable(ASSIGNMENT_KEY);
        } else
            this.assignment = new Assignment();

        initializeViews(rootView);

    }

    public void initializeViews(final View view) {
        this.view = view;
        titleEdt = view.findViewById(R.id.new_ass_title_edt);
        notesEdt = view.findViewById(R.id.notes_edt);
        courseSpinner = view.findViewById(R.id.newAssi_courses_spinner);
        dueDateBtn = view.findViewById(R.id.date_newass_btn);
        dueTimeBtn = view.findViewById(R.id.time_newass_btn2);
        cancelBtn = view.findViewById(R.id.cancel_newass_btn);
        doneBtn = view.findViewById(R.id.done_newass_btn);
        priorityRG = view.findViewById(R.id.priority_radiogrp_newass);
        highRB = view.findViewById(R.id.high_newass_rb);
        mediumRB = view.findViewById(R.id.medium_newass_rb);
        lowRB = view.findViewById(R.id.low_newass_rb);
        dialog_title_tv = view.findViewById(R.id.new_ass_dialog_title_tv);

        List<String> spinnerArray = new ArrayList<String>();
        for (int i = 0; i < courseArrayList.size(); i++) {
            Log.i("CLASSFRAG", courseArrayList.get(i).getCourseName());
            spinnerArray.add(courseArrayList.get(i).getCourseName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.spinner_style, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Spinner sItems = (Spinner) view.findViewById(R.id.add_edit_class_spinner);
        courseSpinner.setAdapter(adapter);

        courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                assignment.setCourseId(courseArrayList.get(position).getCourseId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        priorityRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton rb = view.findViewById(checkedId);
                assignment.setPriority(rb.getText().toString());
            }
        });


        priorityRG.setOnClickListener(this);
        dueTimeBtn.setOnClickListener(this);
        dueDateBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
        doneBtn.setOnClickListener(this);

        if (isEdit) {
            dialog_title_tv.setText("Edit Assignment");
            titleEdt.setText(assignment.getTitle());
            int index = 0;
            for (int i = 0; i < courseArrayList.size(); i++) {
                if (courseArrayList.get(i).getCourseId() == assignment.getCourseId())
                    index = i;
            }
            courseSpinner.setSelection(index);
            Log.i("aass", "initializeViews: " + assignment.getCourseId());
            switch (assignment.getPriority()) {
                case "High":
                    highRB.setChecked(true);
                    break;
                case "Medium":
                    mediumRB.setChecked(true);
                    break;
                case "Low":
                    lowRB.setChecked(true);
                    break;
            }
            if (assignment.getNotes() != null)
                notesEdt.setText(assignment.getNotes());
            Log.i("aass", "initializeViews: " + assignment);
        } else {
            dialog_title_tv.setText("Add Assignment");
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.date_newass_btn:
                new DatePickerDialog(getActivity(), R.style.DateTimeDialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        assignment.setDueDate(dayOfMonth + "/" + month + "/" + year);
                    }
                }, Calendar.getInstance().get(Calendar.YEAR)
                        , Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.time_newass_btn2:
                new TimePickerDialog(getActivity(), R.style.DateTimeDialogTheme, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String AM_PM;
                        if (hourOfDay < 12) {
                            AM_PM = "AM";
                        } else {
                            AM_PM = "PM";
                        }

                        assignment.setDueTime(hourOfDay + ":" + minute + (minute == 0 ? "0" : "") + " " + AM_PM);

                    }
                }, 0, 0, false).show();
                break;
            case R.id.cancel_newass_btn:
                this.dismiss();
                mListener.dismissFragment();
                break;
            case R.id.done_newass_btn:
                assignment.setTitle(titleEdt.getText().toString());
//                if(notesEdt.getText().toString()!=null)
                Log.i("aass", "onClick: " + notesEdt.getText());
                assignment.setNotes(notesEdt.getText().toString());
                Log.i("aass", "onClick: 2" + assignment.getNotes());
                if (validInput()) {
                    if (isEdit)
                        mListener.updateAssignment(assignment);
                    else {
                        Log.i("aass", "onClick: else" + assignment);
                        mListener.addAssignment(assignment);
                    }
                    this.dismiss();
                } else {
                    Toast.makeText(getActivity(), "Please enter the missing values", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public boolean validInput() {
        if (assignment.getTitle() != null
                && assignment.getDueDate() != null
                && assignment.getDueTime() != null
                && assignment.getPriority() != null
                && assignment.getCourseId() != -1)
            return true;
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // the following if condition is just to round the corners of the dialog
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        return inflater.inflate(R.layout.fragment_new_assignment, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dao = new CourseDAO(context);
        courseArrayList = dao.getAllCourses();
        this.context = context;
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void addAssignment(Assignment assignment);

        void dismissFragment();

        void updateAssignment(Assignment assignment);
    }


}
