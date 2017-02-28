package ru.merkulyevsasha.core.domain;


import ru.merkulyevsasha.core.callback.TasksCallback;

public interface  TasksInteractor {

    void setCallback(TasksCallback callback);
    void getTasks();
    void addTask(final TaskModel task);
    void updateTask(final TaskModel task);
    void setTaskStatus(final long id, final int status);


}
