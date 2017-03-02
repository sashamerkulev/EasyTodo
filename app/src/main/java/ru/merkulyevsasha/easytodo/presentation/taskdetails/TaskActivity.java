package ru.merkulyevsasha.easytodo.presentation.taskdetails;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;

import ru.merkulyevsasha.core.domain.TaskModel;
import ru.merkulyevsasha.easytodo.R;
import ru.merkulyevsasha.easytodo.presentation.UIHelper;


public class TaskActivity extends AppCompatActivity {

    public static final int TASK_RESULT_CODE = 222;

    public static final String KEY_TASK = "task";
    public static final String KEY_ID_TASKS = "task_ids";

    private TextView shortName;
    private TextView shortDescription;
    private TextView expiryDate;
    private TextView expiryTime;
    private TextView status;

    private TaskModel task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        if (ab != null){
            ab.setDisplayHomeAsUpEnabled(true);
        }


        shortName = (TextInputEditText)findViewById(R.id.textview_short_name);
        shortDescription = (TextInputEditText)findViewById(R.id.textview_short_description);
        expiryDate = (TextInputEditText)findViewById(R.id.textview_expiry_date);
        expiryTime = (TextInputEditText)findViewById(R.id.textview_expiry_time);
        status = (TextInputEditText)findViewById(R.id.textview_status);

        Intent intent = getIntent();
        task = (TaskModel)intent.getSerializableExtra(KEY_TASK);

        final Calendar calendar = Calendar.getInstance();

        if (task == null){
            task = new TaskModel();
            task.setId(TaskModel.EMPTY_ID);
            task.setStatus(TaskModel.STATUS_CREATED);
            task.setExpiryAt(calendar.getTimeInMillis());
            task.setShortName("");
            task.setShortDescription("");
        }

        shortName.setText(task.getShortName());
        shortDescription.setText(task.getShortDescription());

        calendar.setTimeInMillis(task.getExpiryAt());

        expiryDate.setText(DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime()));
        expiryDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();

                DatePickerDialog dialog =
                        new DatePickerDialog(TaskActivity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                calendar.set(year, month, dayOfMonth);
                                task.setExpiryAt(calendar.getTimeInMillis());
                                expiryDate.setText(DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime()));
                            }
                        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });

        expiryTime.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime()));
        expiryTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();

                TimePickerDialog dialog = new TimePickerDialog(TaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), hourOfDay, minute);
                        task.setExpiryAt(calendar.getTimeInMillis());
                        expiryTime.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime()));
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
                dialog.show();
            }
        });

        status.setText(UIHelper.getStatusName(this, task.getStatus()));
        if (task.getId() != TaskModel.EMPTY_ID ) {
            status.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    hideSoftKeyboard();

                    TaskDialogHelper.getTaskStatusDialog(TaskActivity.this, task.getStatus(), new TaskDialogHelper.OnDialogIndexListener() {
                        @Override
                        public void onClick(int index) {
                            task.setStatus(index);
                            status.setText(UIHelper.getStatusName(TaskActivity.this, index));
                        }
                    }).show();

                }
            });
        }

    }

    private void hideSoftKeyboard(){
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_task, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void attemptDone(){

        shortName.setError(null);
        shortDescription.setError(null);
        expiryDate.setError(null);

        final String shortNameText = shortName.getText().toString();
        final String shortDescriptionText = shortDescription.getText().toString();
        final String expiryText = expiryDate.getText().toString();

        boolean cancel = false;
        if (TextUtils.isEmpty(shortNameText)) {
            shortName.setError(getString(R.string.error_field_required));
            cancel = true;
        }

        if (TextUtils.isEmpty(shortDescriptionText)) {
            shortDescription.setError(getString(R.string.error_field_required));
            cancel = true;
        }

        if (TextUtils.isEmpty(expiryText)) {
            expiryDate.setError(getString(R.string.error_field_required));
            cancel = true;
        }

        if (!cancel){
            task.setShortDescription(shortDescriptionText);
            task.setShortName(shortNameText);

            setResult(TASK_RESULT_CODE, new Intent().putExtra(KEY_TASK, task));
            finish();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_done){
            attemptDone();
        }

        if (id == android.R.id.home) {
            onBackPressed();
        }

        return true;
    }


}
