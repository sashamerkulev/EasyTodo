package ru.merkulyevsasha.easytodo.presentation.taskslist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.merkulyevsasha.core.domain.TaskModel;
import ru.merkulyevsasha.easytodo.R;
import ru.merkulyevsasha.easytodo.TodoApp;
import ru.merkulyevsasha.easytodo.presentation.UIHelper;
import ru.merkulyevsasha.easytodo.presentation.taskdetails.TaskActivity;
import ru.merkulyevsasha.easytodo.presentation.taskdetails.TaskDialogHelper;

public class TasksActivity extends AppCompatActivity implements MvpTasksView {

    public static final int TASK_REQUEST_CODE = 111;

    @Inject
    public TasksPresenter presenter;

    private View rootView;

    private ProgressBar progressBar;

    private TasksRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ((TodoApp)getApplication()).getComponent().inject(this);

        rootView = findViewById(R.id.content_main);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new TasksRecyclerViewAdapter(new ArrayList<TaskModel>(), new TasksRecyclerViewAdapter.OnTaskListener() {
            @Override
            public void onTaskClick(int position, TaskModel model) {
                openTask(model);
            }
        }, new TasksRecyclerViewAdapter.OnStatusTaskListener() {
            @Override
            public void onStatusTaskClick(final int position, final TaskModel model) {

                TaskDialogHelper.getTaskStatusDialog(TasksActivity.this, model.getStatus(), new TaskDialogHelper.OnDialogIndexListener() {
                    @Override
                    public void onClick(int index) {
                        presenter.updateStatus(model.getId(), index);
                    }
                }).show();
            }
        });
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (presenter != null){
            presenter.onStop();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (presenter != null){
            presenter.onStart(this);
            presenter.load();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tasks, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_add){
            openTask(null);
        }

        return true;
    }

    @Override
    public void showList(final List<TaskModel> models) {

        for (TaskModel model :
                models) {
            model.setStatusName(UIHelper.getStatusName(this, model.getStatus()));
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.models = models;
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void showProgress() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void hideProgress() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void showMessage(final int message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Snackbar.make(rootView, getString(message), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void changedTaskStatus(final long id, final int status){
        for (int i=0; i < adapter.models.size(); i++) {
            TaskModel model = adapter.models.get(i);
            if (model.getId() == id){
                model.setStatus(status);
                model.setStatusName(UIHelper.getStatusName(this, status));
                adapter.notifyItemChanged(i);
                break;
            }
        }
    }

    private void openTask(TaskModel model){
        Intent intent = new Intent(this, TaskActivity.class);
        intent.putExtra(TaskActivity.KEY_TASK, model);
        startActivityForResult(intent, TASK_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TASK_REQUEST_CODE && resultCode == TaskActivity.TASK_RESULT_CODE){
            TaskModel task = (TaskModel)data.getSerializableExtra(TaskActivity.KEY_TASK);
            presenter.saveTask(task);
        }
    }
}
