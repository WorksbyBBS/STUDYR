package qa.edu.qu.cmps312.studyr.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import qa.edu.qu.cmps312.studyr.R;
import qa.edu.qu.cmps312.studyr.models.Assignment;
import qa.edu.qu.cmps312.studyr.repository.AssignmentDAO;
import qa.edu.qu.cmps312.studyr.repository.CourseDAO;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Assignment> assignments;
    AssignmentDAO dao;
    CourseDAO courseDAO;
    AssignmentInnerAdapter assignmentInnerAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private static int c = 1;
    private String flag;

    public AssignmentAdapter(Context context, ArrayList<Assignment> assignments) {
        this.context = context;
        this.assignments = assignments;
        for (Assignment a : assignments
                ) {
            Log.i("aass", "AssignmentAdapter: " + a);
        }
        dao = new AssignmentDAO(context);
        courseDAO = new CourseDAO(context);
        assignmentInnerAdapter = new AssignmentInnerAdapter(context, assignments);
        this.flag = "date";
//        layoutManager = new LinearLayoutManager(context);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView titleTv;
        RecyclerView inner_recycler;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTv = itemView.findViewById(R.id.recycler_row_item_title_tv);//they are in the layout itemview so use it to access them
            inner_recycler = itemView.findViewById(R.id.inner_recycler);

        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_list_row_item, viewGroup, false);//viewgroup is the parent
        MyViewHolder myViewHolder = new MyViewHolder(itemView);
//        if(c<=1){
//            myViewHolder.inner_recycler.setLayoutManager(layoutManager);
//            c++;
//        }
//        assignmentInnerAdapter=new AssignmentInnerAdapter(context,assignments);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, final int position) {//set the data
        ArrayList<Assignment> innerAssignments = dao.getAllAssignments();
        layoutManager = new LinearLayoutManager(context);
        ArrayList<Assignment> sameDate = new ArrayList<>();
        int i = 0;
        switch (flag) {
            case "date":
                if (position < assignments.size())
                    viewHolder.titleTv.setText(assignments.get(position).getDueDate());
                while (i < innerAssignments.size()) {
                    if (innerAssignments.get(i).getDueDate().equals(assignments.get(position).getDueDate())) {
                        sameDate.add(innerAssignments.get(i));
                    }
                    i++;
                }
                break;
            case "priority":
                if (position < assignments.size())
                    viewHolder.titleTv.setText(assignments.get(position).getPriority());
                while (i < innerAssignments.size()) {
                    if (innerAssignments.get(i).getPriority().equals(assignments.get(position).getPriority())) {
                        sameDate.add(innerAssignments.get(i));
                    }
                    i++;
                }
                break;
            case "course":
                if (position < assignments.size())
                    viewHolder.titleTv.setText(courseDAO.getCourse(assignments.get(position).getCourseId()).getCourseName());
                while (i < innerAssignments.size()) {
                    if (innerAssignments.get(i).getCourseId() == (assignments.get(position).getCourseId())) {
                        sameDate.add(innerAssignments.get(i));
                    }
                    i++;
                }
                break;
            default:
                if (position < assignments.size())
                    viewHolder.titleTv.setText(assignments.get(position).getDueDate());
                while (i < innerAssignments.size()) {
                    if (innerAssignments.get(i).getDueDate().equals(assignments.get(position).getDueDate())) {
                        sameDate.add(innerAssignments.get(i));
                    }
                    i++;
                }
                break;
        }
//        if(position<assignments.size()) viewHolder.titleTv.setText(assignments.get(position).getPriority());
// ArrayList<Assignment> innerAssignments= dao.getAllAssignments();
        for (Assignment ass : assignments
                ) {
            Log.i("inneradapt2", "onBindViewHolder: " + ass.getDueDate());
        }
        Log.i("inneradapt2", "onBindViewHolder: " + assignments.size());
        Log.i("inneradapt2", "onBindViewHolder:pos " + position);

//        layoutManager = new LinearLayoutManager(context);
//        ArrayList<Assignment> sameDate=new ArrayList<>();


//    while(i<innerAssignments.size()){
//        if(innerAssignments.get(i).getDueDate().equals(innerAssignments.get(position).getDueDate()))
//        {
//            sameDate.add(innerAssignments.get(i));
//        }
//        i++;
//    }
        assignmentInnerAdapter = new AssignmentInnerAdapter(context, sameDate);
//        assignmentInnerAdapter.notifyChange(assignments);
//        assignmentInnerAdapter.notifyDataSetChanged();
        viewHolder.inner_recycler.setLayoutManager(layoutManager);
        viewHolder.inner_recycler.setAdapter(assignmentInnerAdapter);

//        viewHolder.currentDateRecyclerView.se(stadiums.get(position).getHostCity());

    }

    public void notifyChange(ArrayList<Assignment> newAssignmentList) {
        if (assignments != null) {
            assignments.clear();
            assignments.addAll(newAssignmentList);
            for (int i = 0; i < assignments.size(); i++)
                Log.i("inneradapt2", "notifyinadapter: " + assignments.get(i).getTitle());
//            assignmentInnerAdapter.notifyChange(assignments);
        } else {
            assignments = newAssignmentList;
        }
        notifyDataSetChanged();
    }

    public void notifyChange(ArrayList<Assignment> newAssignmentList, String flag) {
        if (assignments != null) {
            assignments.clear();
            assignments.addAll(newAssignmentList);
            switch (flag) {
                case "date":
                    this.assignments = dao.getAssignmentsBasedonDate();
                    break;
                case "priority":
                    this.assignments = dao.getAssignmentsBasedonPriority();
                    break;
                case "course":
                    this.assignments = dao.getAssignmentsBasedonCourse();
                    break;
            }
            this.flag = flag;
            for (int i = 0; i < assignments.size(); i++)
                Log.i("inneradapt2", "notifyinadapter: " + assignments.get(i).getTitle());
//            assignmentInnerAdapter.notifyChange(assignments);
        } else {
            assignments = newAssignmentList;
        }
        Log.i("aass", "notifyChange: " + flag);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return assignments.size();
    }
}
