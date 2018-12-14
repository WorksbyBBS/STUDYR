package qa.edu.qu.cmps312.studyr.activities;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import android.provider.CalendarContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

import qa.edu.qu.cmps312.studyr.R;
import qa.edu.qu.cmps312.studyr.adapters.AssignmentAdapter;
import qa.edu.qu.cmps312.studyr.adapters.AssignmentInnerAdapter;
import qa.edu.qu.cmps312.studyr.fragments.NewAssignmentFragment;
import qa.edu.qu.cmps312.studyr.models.Assignment;
import qa.edu.qu.cmps312.studyr.receivers.Receiver;
import qa.edu.qu.cmps312.studyr.repository.AssignmentDAO;
import qa.edu.qu.cmps312.studyr.repository.CourseDAO;

public class AssignmentsActivity extends AppCompatActivity implements NewAssignmentFragment.OnFragmentInteractionListener, AssignmentInnerAdapter.AdapterInteraction {
    FloatingActionButton floatingAddButtonAssignment;
    private final String TAG = "AssignmentActivity";
    private Toolbar myToolbar;
    private AssignmentDAO dao;
    private ArrayList<Assignment> assignments;
    AssignmentAdapter adapter;
    //    AssignmentInnerAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    RadioButton dueDateRb, priorityRb, courseRb;
    RadioGroup classificationRG;
    String flag = "date";
    private CourseDAO courseDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignments);
        dao = new AssignmentDAO(this);
        courseDAO = new CourseDAO(this);
        dueDateRb = findViewById(R.id.dueDate_rb);
        priorityRb = findViewById(R.id.priority_rb);
        courseRb = findViewById(R.id.course_rb);
        classificationRG = findViewById(R.id.classification_rg);
        adapter = new AssignmentAdapter(this, getAllAssignments("date"));

        assignments = getAllAssignments("date");
        adapter.notifyChange(assignments, "date");
        classificationRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.dueDate_rb:
                        flag = "date";
                        break;
                    case R.id.priority_rb:
                        flag = "priority";
                        break;
                    case R.id.course_rb:
                        flag = "course";
                        break;
                }
                assignments = getAllAssignments(flag);
                adapter.notifyChange(assignments, flag);
            }
        });
//
        //add menu to toolbar
        myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        floatingAddButtonAssignment = findViewById(R.id.floatingActionButtonAssignment);
        floatingAddButtonAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewAssignmentFragment newAssignmentFragment = NewAssignmentFragment.newInstance();
                newAssignmentFragment.show(getSupportFragmentManager(), "Assignment_Fragment");
            }
        });


//        adapter = new AssignmentAdapter(this, getAllAssignments("date"));
//adapter=new AssignmentInnerAdapter(this,getAllAssignments());
        recyclerView = findViewById(R.id.recycler_ass_list);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String itemText = item.toString();
        if (itemText.equalsIgnoreCase("schedule")) {
            Intent intent = new Intent(AssignmentsActivity.this, MainActivity.class);
            startActivity(intent);
        } else if (itemText.equalsIgnoreCase("courses")) {
            Intent intent = new Intent(AssignmentsActivity.this, CourseActivity.class);
            startActivity(intent);
        }
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getExtras() != null) {
        }
        assignments = dao.getAllAssignments(flag);
        adapter.notifyChange(assignments, flag);
    }

    @Override
    public void addAssignment(Assignment assignment) {
        Log.i("inneradapt2", "addAssignment: " + assignment);
        dao.addAssignment(assignment);
        scheduleNotification(buildNotification(assignment), assignment);
        assignments = dao.getAllAssignments(flag);
        for (int i = 0; i < assignments.size(); i++)
            Log.i("inneradapt2", "addAssignment: " + assignments.get(i).getNotes());
        adapter.notifyChange(assignments, flag);
        dismissFragment();
    }

    @Override
    public void dismissFragment() {

    }

    @Override
    public void updateAssignment(Assignment assignment) {
        dao.updateAssignment(assignment);

        deleteNotification(assignment);
        scheduleNotification(buildNotification(assignment), assignment);

        assignments = dao.getAllAssignments(flag);
        adapter.notifyChange(assignments);
        dismissFragment();
    }

    public ArrayList<Assignment> getAllAssignments(String flag) {
        assignments = new ArrayList<>();
        assignments = dao.getAllAssignments(flag);
        for (Assignment ass : assignments
                ) {
            Log.i("aass", "getAllAssignments: " + ass);
        }
        layoutManager = new LinearLayoutManager(this);
        return assignments;
    }

    //Todo course id change to course name
    @Override
    public void deleteAssignment(Assignment assignment) {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_warning_red_24dp)
                .setTitle("Delete Completed Assignment " + assignment.getTitle())
                .setMessage("Are You Sure You Want to Delete "
                        + assignment.getTitle() + " in Course " + courseDAO.getCourse(assignment.getCourseId()))
                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dao.deleteAssignment(assignment.getAssignmentId());
                        Toast.makeText(AssignmentsActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                        assignments = dao.getAllAssignments(flag);
                        adapter.notifyChange(assignments);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    @Override
    public void syncAssignment(Assignment assignment) {
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.Events.TITLE, assignment.getTitle())
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, assignment.getDueDate())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, assignment.getDueTime());
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public void editAssignment(Assignment assignment) {
        NewAssignmentFragment dialogFragment = NewAssignmentFragment
                .newInstance(assignment);

        dialogFragment.show(getSupportFragmentManager(), "MY_DIALOG");
    }

    private void scheduleNotification(Notification notification, Assignment assignment) {

        Intent intent = new Intent(this, Receiver.class);
        intent.putExtra(Receiver.NOTIF_ID, 1);
        intent.putExtra(Receiver.NOTIF, notification);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, assignment.getAssignmentId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //convert assignment date string, subtract 1 day to be used in notification
        String[] dueDate = assignment.getDueDate().split("/");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.valueOf(dueDate[2]), (Integer.valueOf(dueDate[1]) - 1), Integer.valueOf(dueDate[0]), 12, 00, 0);

        //subtract one day
        calendar.add(Calendar.DAY_OF_MONTH, -1);

        long futureInMillis = calendar.getTimeInMillis();
        //checking time
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
        Log.i("AssignACT", sdf.format(futureInMillis) + " TIMMMMEEE");

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, futureInMillis, pendingIntent);

    }

    private Notification buildNotification(Assignment assignment) {
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default",
                    "Studyr",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Studyr app notification channel");
            mNotificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "default")
                .setSmallIcon(R.drawable.ic_notifications) // notification icon
                .setContentTitle(assignment.getTitle() + " is Due Tomorrow") // title for notification
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentText("Don't forget about it!")// message for notification
                .setAutoCancel(true); // clear notification after click

        //to open activity when clicking on notification
        Intent intent = new Intent(getApplicationContext(), AssignmentsActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, assignment.getAssignmentId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntent);
        //mNotificationManager.notify(0, mBuilder.build());
        return mBuilder.build();
    }

    private void deleteNotification(Assignment assignment) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(getApplicationContext(), Receiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, assignment.getAssignmentId(), intent, 0);
        alarmManager.cancel(pendingIntent);
    }
}
