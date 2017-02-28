package ru.merkulyevsasha.core.callback;


import java.util.List;

import ru.merkulyevsasha.core.domain.TaskModel;

public interface TasksCallback {

    void getTasksCallback(final List<TaskModel> tasks);
    void getTasksFailureCallback(final Exception e);

    void addTaskCallback(final TaskModel task);
    void addTaskFailureCallback(final TaskModel task, final Exception e);

    void updateTaskCallback(final TaskModel task);
    void updateTaskFailureCallback(final TaskModel task, final Exception e);

    void setTaskStatusCallback(final long id, final int status);
    void setTaskStatusFailureCallback(final long id, final int status, final Exception e);


}
