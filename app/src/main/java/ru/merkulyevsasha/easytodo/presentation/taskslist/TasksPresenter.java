package ru.merkulyevsasha.easytodo.presentation.taskslist;


import java.util.List;

import ru.merkulyevsasha.core.callback.TasksCallback;
import ru.merkulyevsasha.core.domain.TaskModel;
import ru.merkulyevsasha.core.domain.TasksInteractor;
import ru.merkulyevsasha.easytodo.R;
import ru.merkulyevsasha.easytodo.presentation.MvpPresenter;
import ru.merkulyevsasha.easytodo.presentation.MvpView;

public class TasksPresenter implements MvpPresenter, TasksCallback{

    private final TasksInteractor interactor;

    private MvpTasksView view;

    public TasksPresenter(TasksInteractor interactor){
        this.interactor = interactor;
        interactor.setCallback(this);
    }

    private void showMessage(int messageId){
        if (view != null) {
            view.showMessage(messageId);
        }
    }

    private void showProgress(){
        if (view != null){
            view.showProgress();
        }
    }

    private void hideProgress(){
        if (view != null){
            view.hideProgress();
        }
    }

    public void load(){
        showProgress();
        interactor.getTasks();
    }

    public void saveTask(TaskModel task){
        showProgress();
        if (task.getId() == TaskModel.EMPTY_ID) {
            interactor.addTask(task);
        } else {
            interactor.updateTask(task);
        }
    }

    public void updateStatus(long id, int status){
        showProgress();
        interactor.setTaskStatus(id, status);
    }

    @Override
    public void getTasksCallback(List<TaskModel> tasks) {
        hideProgress();
        if (view != null) {
            view.showList(tasks);
        }
    }

    @Override
    public void getTasksFailureCallback(Exception e) {
        hideProgress();
        showMessage(R.string.get_list_exception_message);
    }

    @Override
    public void addTaskCallback(TaskModel task) {
        showMessage(R.string.add_task_message);
        hideProgress();
    }

    @Override
    public void addTaskFailureCallback(TaskModel task, Exception e) {
        showMessage(R.string.add_task_exception_message);
        hideProgress();
    }

    @Override
    public void updateTaskCallback(TaskModel task) {
        showMessage(R.string.update_task_message);
        hideProgress();
    }

    @Override
    public void updateTaskFailureCallback(TaskModel task, Exception e) {
        showMessage(R.string.update_task_exception_message);
        hideProgress();
    }

    @Override
    public void setTaskStatusCallback(long id, int status) {
        showMessage(R.string.update_status_task_message);
        hideProgress();
        if (view != null) {
            view.changedTaskStatus(id, status);
        }
    }

    @Override
    public void setTaskStatusFailureCallback(long id, int status, Exception e) {
        showMessage(R.string.update_status_task_exception_message);
        hideProgress();
    }

    @Override
    public void onStop() {
        this.view = null;
    }

    @Override
    public void onStart(MvpView view) {
        this.view = (MvpTasksView)view;
    }

}
