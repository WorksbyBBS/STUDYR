package qa.edu.qu.cmps312.studyr;

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

import java.util.List;

import qa.edu.qu.cmps312.studyr.models.Course;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CustomViewHolder> {

    private List<Course> courseList;
    private List<Integer> assignmentsTotalList;
    private Context context;
    //private RecyclerViewClickListener listener;

    public CourseAdapter(List<Course> courseList,List<Integer> assignmentsTotalList, Context context) {
        this.courseList = courseList;
        this.context = context;
        this.assignmentsTotalList = assignmentsTotalList;
        //this.listener = listener;
    }


    public static class CustomViewHolder extends RecyclerView.ViewHolder{

        TextView courseTitle;
        TextView noAssignments;
        ImageView courseColor;
        ImageButton editButton;
        ImageButton deleteButton;

//        TextView todoTitleTXT;
//        TextView todoDateTXT;
//        TextView todoTimeTXT;
//        TextView todoPriorityTXT;
//        CheckBox todoCompletedCheckBox;
//        ImageButton editButton;
//        ImageButton deleteRowButton;
//        ImageButton syncButton;
//        ConstraintLayout cardConstraintLayout;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            courseTitle = itemView.findViewById(R.id.class_row_title);
            noAssignments = itemView.findViewById(R.id.course_row_total_assignments);
            courseColor = itemView.findViewById(R.id.class_row_color_image);
            editButton = itemView.findViewById(R.id.class_row_edit_button);
            deleteButton = itemView.findViewById(R.id.course_row_delete_button);

        }
    }

    @NonNull
    @Override
    public CourseAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.course_row_design,viewGroup,false);
        CustomViewHolder vh = new CustomViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final CourseAdapter.CustomViewHolder customViewHolder, final int position) {
        customViewHolder.courseTitle.setText(courseList.get(position).getCourseName());
        customViewHolder.noAssignments.setText(String.format(assignmentsTotalList.get(position)+" Assignments"));
        customViewHolder.courseColor.setColorFilter(Color.parseColor(courseList.get(position).getColorId()));
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public void clearList(){
//        int size = getItemCount();
//        toDoList.clear();
//        notifyItemRangeRemoved(0, size);
    }

    public void removeItem(int position) {
//        toDoList.remove(position);
//        notifyItemRemoved(position);
    }

    public void addItem(Course item){
//        toDoList.add(item);
//        notifyDataSetChanged();
    }

    public void editItem(int position, Course item){
//        ToDo itemToBeEdited = toDoList.get(position);
//        itemToBeEdited.setTitle(item.getTitle());
//        itemToBeEdited.setTime(item.getTime());
//        itemToBeEdited.setStatus(item.getStatus());
//        itemToBeEdited.setPriority(item.getPriority());
//        itemToBeEdited.setDate(item.getDate());
//        notifyDataSetChanged();
    }

}
