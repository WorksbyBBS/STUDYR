package qa.edu.qu.cmps312.studyr.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import qa.edu.qu.cmps312.studyr.R;
import qa.edu.qu.cmps312.studyr.models.Assignment;
import qa.edu.qu.cmps312.studyr.models.Course;
import qa.edu.qu.cmps312.studyr.repository.AssignmentDAO;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CustomViewHolder> {

    private List<Course> courseList;
    private Context context;
    private AdapterInteraction interaction;
    private AssignmentDAO assignmentDAO;
    //private RecyclerViewClickListener listener;

    public CourseAdapter(List<Course> courseList, Context context) {
        this.courseList = courseList;
        this.context = context;
        this.assignmentDAO = new AssignmentDAO(context);
        try {
            if (!(context instanceof AdapterInteraction)) throw new AssertionError();
            interaction = (AdapterInteraction) context;
        } catch (AssertionError e) {

        }

    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView courseTitle;
        TextView noAssignments;
        ImageView courseColor;
        ImageButton editButton;
        ImageButton deleteButton;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            courseTitle = itemView.findViewById(R.id.class_row_title);
            noAssignments = itemView.findViewById(R.id.course_row_total_assignments);
            courseColor = itemView.findViewById(R.id.class_row_color_image);
            editButton = itemView.findViewById(R.id.class_row_edit_button);
            deleteButton = itemView.findViewById(R.id.course_row_delete_button);

        }
    }

    public interface AdapterInteraction {
        void deleteCourse(Course course);

        void editCourse(Course course);
    }

    @NonNull
    @Override
    public CourseAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.course_row_design, viewGroup, false);
        CustomViewHolder vh = new CustomViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final CourseAdapter.CustomViewHolder customViewHolder, final int position) {
        customViewHolder.courseTitle.setText(courseList.get(position).getCourseName());

        //TODO: bind it with assignment table, for now i'll put it always at 10
        ArrayList<Assignment> assignments = assignmentDAO.getAssignmentsOfCourse(courseList.get(position).getCourseId());

        //customViewHolder.noAssignments.setText(String.format(assignmentsTotalList.get(position)+" Assignments"));
        customViewHolder.noAssignments.setText(String.format(assignments.size() + " Assignments"));
        //Log.i("COURSEADAP", courseList.get(position).getColorId());
        customViewHolder.courseColor.setColorFilter(Color.parseColor(courseList.get(position).getColorId()));

        customViewHolder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interaction.editCourse(courseList.get(position));
            }
        });

        customViewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interaction.deleteCourse(courseList.get(position));
            }
        });
    }

    public void notifyChange(ArrayList<Course> newCourseList) {
        if (courseList != null) {
            courseList.clear();
            courseList.addAll(newCourseList);

        } else {
            courseList = newCourseList;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }


}
