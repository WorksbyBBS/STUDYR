package qa.edu.qu.cmps312.studyr.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import qa.edu.qu.cmps312.studyr.R;
import qa.edu.qu.cmps312.studyr.models.CourseClass;
import qa.edu.qu.cmps312.studyr.models.Course;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.CustomViewHolder> {

    private List<CourseClass> courseClassList;
    private List<Course> courseList;
    private AdapterInteraction interaction;
    //private List<Integer> assignmentsTotalList;
    private Context context;
    //private RecyclerViewClickListener listener;

    public ClassAdapter(List<CourseClass> courseClassList, List<Course> courseList, Context context) {
        this.courseClassList = courseClassList;
        this.courseList = courseList;
        this.context = context;
        try {
            if (!(context instanceof AdapterInteraction)) throw new AssertionError();
            interaction = (AdapterInteraction) context;
        } catch (AssertionError e) {

        }
        //this.assignmentsTotalList = assignmentsTotalList;
        //this.listener = listener;
    }

    public interface AdapterInteraction {
        void deleteClass(CourseClass courseClass);

        void editClass(CourseClass courseClass);
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView courseTitle;
        TextView classLocation;
        TextView startTime;
        TextView endTime;
        ImageView courseColor;
        ImageButton editButton;
        ImageButton deleteButton;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            courseTitle = itemView.findViewById(R.id.class_row_title);
            classLocation = itemView.findViewById(R.id.class_row_location);
            courseColor = itemView.findViewById(R.id.class_row_color_image);
            startTime = itemView.findViewById(R.id.class_row_stime);
            endTime = itemView.findViewById(R.id.class_row_etime);
            editButton = itemView.findViewById(R.id.class_row_edit_button);
            deleteButton = itemView.findViewById(R.id.class_row_delete_button);

        }
    }

    @NonNull
    @Override
    public ClassAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.class_row_design, viewGroup, false);
        CustomViewHolder vh = new CustomViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ClassAdapter.CustomViewHolder customViewHolder, final int position) {

        //look for course that matches ID
        for (int i = 0; i < courseList.size(); i++) {
            if (courseList.get(i).getCourseId() == ((courseClassList.get(position)).getCourseId())) {
                customViewHolder.courseTitle.setText(courseList.get(i).getCourseName());
                customViewHolder.courseColor.setColorFilter(Color.parseColor(courseList.get(i).getColorId()));
                break;
            }
        }

        customViewHolder.classLocation.setText(courseClassList.get(position).getLocation());
        customViewHolder.startTime.setText(courseClassList.get(position).getStartTime());
        customViewHolder.endTime.setText(courseClassList.get(position).getEndTime());

        customViewHolder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interaction.editClass(courseClassList.get(position));
            }
        });

        customViewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interaction.deleteClass(courseClassList.get(position));
            }
        });

    }

    public void notifyChange(ArrayList<CourseClass> newClassList) {
        if (courseClassList != null) {
            courseClassList.clear();
            courseClassList.addAll(newClassList);

        } else {
            courseClassList = newClassList;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return courseClassList.size();
    }


    public void editItem(int position, Course item) {
//        ToDo itemToBeEdited = toDoList.get(position);
//        itemToBeEdited.setTitle(item.getTitle());
//        itemToBeEdited.setTime(item.getTime());
//        itemToBeEdited.setStatus(item.getStatus());
//        itemToBeEdited.setPriority(item.getPriority());
//        itemToBeEdited.setDate(item.getDate());
//        notifyDataSetChanged();
    }

}
