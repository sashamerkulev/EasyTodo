package ru.merkulyevsasha.core.domain;



import java.util.ArrayList;

import ru.merkulyevsasha.core.callback.TasksCallback;

public interface  TasksInteractor {

    void loadTasks(ArrayList<Integer> ids, final TasksCallback.LoadTasksCallback loadCallback, final TasksCallback.LoadTasksFailureCallback loadFailureCallback);
    void loadTasks(final TasksCallback.LoadTasksCallback loadCallback, final TasksCallback.LoadTasksFailureCallback loadFailureCallback);
    void loadExpiredTasks(final TasksCallback.LoadTasksCallback loadCallback, final TasksCallback.LoadTasksFailureCallback loadFailureCallback);
    void addTask(final TaskModel task, final TasksCallback.AddTaskCallback addCallback, final TasksCallback.AddTaskFailureCallback addFailureCallback);
    void updateTask(final TaskModel task, final TasksCallback.UpdateTaskCallback updateCallback, final TasksCallback.UpdateTaskFailureCallback updateFailureCallback);
    void updateTaskStatus(final long id, final int status, final TasksCallback.UpdateTaskStatusCallback updateStatusCallback,
                          final TasksCallback.UpdateTaskStatusFailureCallback updateStatusFailureCallback);


}
