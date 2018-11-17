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

import qa.edu.qu.cmps312.studyr.models.Class;
import qa.edu.qu.cmps312.studyr.models.Course;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.CustomViewHolder> {

    private List<Class> classList;
    private List<Course> courseList;
    //private List<Integer> assignmentsTotalList;
    private Context context;
    //private RecyclerViewClickListener listener;

    public ClassAdapter(List<Class> classList,List<Course> courseList, Context context) {
        this.classList = classList;
        this.courseList = courseList;
        this.context = context;
        //this.assignmentsTotalList = assignmentsTotalList;
        //this.listener = listener;
    }


    public static class CustomViewHolder extends RecyclerView.ViewHolder{

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
        View view = inflater.inflate(R.layout.class_row_design,viewGroup,false);
        CustomViewHolder vh = new CustomViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ClassAdapter.CustomViewHolder customViewHolder, final int position) {

        //look for course that matches ID
        for(int i=0;i<courseList.size();i++){
            if(courseList.get(i).getCourseId()==((classList.get(position)).getCourseId())){
                customViewHolder.courseTitle.setText(courseList.get(i).getCourseName());
                customViewHolder.courseColor.setColorFilter(Color.parseColor(courseList.get(i).getColorId()));
                break;
            }
        }

        customViewHolder.classLocation.setText(classList.get(position).getLocation());
        customViewHolder.startTime.setText(classList.get(position).getStartTime());
        customViewHolder.endTime.setText(classList.get(position).getEndTime());

    }

    @Override
    public int getItemCount() {
        return classList.size();
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
