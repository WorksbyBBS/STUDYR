package qa.edu.qu.cmps312.studyr.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import qa.edu.qu.cmps312.studyr.R;
import qa.edu.qu.cmps312.studyr.models.Assignment;
import qa.edu.qu.cmps312.studyr.models.Course;
import qa.edu.qu.cmps312.studyr.repository.AssignmentDAO;

public class AssignmentInnerAdapter extends RecyclerView.Adapter<AssignmentInnerAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Assignment> assignments;
    AssignmentDAO dao;
    private AdapterInteraction interaction;

    public AssignmentInnerAdapter(Context context, ArrayList<Assignment> assignments) {
        this.context = context;
        this.assignments = assignments;
        for (int i = 0; i < assignments.size(); i++)
            Log.i("inneradapt2", "innerconst: " + assignments.get(i).getTitle());
        dao = new AssignmentDAO(context);
        try {
            if (!(context instanceof AdapterInteraction)) throw new AssertionError();
            interaction = (AdapterInteraction) context;
        } catch (AssertionError e) {

        }

    }

    public interface AdapterInteraction {
        void deleteAssignment(Assignment assignment);

        void syncAssignment(Assignment assignment);

        void editAssignment(Assignment assignment);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView inner_row_item_title_tv;
        TextView row_item_inner_notes_tv;
        CheckBox completedCheckBox;
        Button sync_btn, edt_btn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            inner_row_item_title_tv = itemView.findViewById(R.id.inner_row_item_title_tv);//they are in the layout itemview so use it to access them
            row_item_inner_notes_tv = itemView.findViewById(R.id.row_item_inner_notes);
            completedCheckBox = itemView.findViewById(R.id.completed_chbox);
            sync_btn = itemView.findViewById(R.id.sync_btn);
            edt_btn = itemView.findViewById(R.id.edit_btn);
        }
    }

    @NonNull
    @Override
    public AssignmentInnerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item_inner_row, viewGroup, false);
        return new AssignmentInnerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentInnerAdapter.MyViewHolder viewHolder, final int position) {//set the data

        viewHolder.inner_row_item_title_tv.setText(assignments.get(position).getTitle());
        String notes = assignments.get(position).getNotes();
        if (notes == null || notes.equalsIgnoreCase("")) {
            viewHolder.row_item_inner_notes_tv.setVisibility(View.GONE);
            viewHolder.row_item_inner_notes_tv.setText("No notes for this assignment");
        } else {
            viewHolder.row_item_inner_notes_tv.setVisibility(View.VISIBLE);
            viewHolder.row_item_inner_notes_tv.setText(assignments.get(position).getNotes());
        }
//        viewHolder.inner_row_item_title_tv.setText(assignments.get(position).getTitle());
//        viewHolder.completedCheckBox.setChecked(true);
        for (int i = 0; i < assignments.size(); i++)
            Log.i("inneradapt2", "onbind ineer: " + assignments.get(i).getTitle());
        Log.i("inneradapt2", "onbind ineer: " + position);
//        Log.i("inneradapt2", "onBindViewHolder: "+assignments.get(position).getTitle());
        viewHolder.completedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    interaction.deleteAssignment(assignments.get(position));
                }
            }
        });
        viewHolder.sync_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interaction.syncAssignment(assignments.get(position));
            }
        });
        viewHolder.edt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interaction.editAssignment(assignments.get(position));
            }
        });

    }

    public void notifyChange(ArrayList<Assignment> newAssignmentList) {
        if (assignments != null) {
            assignments.clear();
            assignments.addAll(newAssignmentList);

        } else {
            assignments = newAssignmentList;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return assignments.size();
    }
}
