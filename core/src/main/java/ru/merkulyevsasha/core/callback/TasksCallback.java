package ru.merkulyevsasha.core.callback;


import java.util.List;

import ru.merkulyevsasha.core.domain.TaskModel;

public interface TasksCallback {

    interface LoadTasksCallback {
        void loadTasksCallback(final List<TaskModel> tasks);
    }

    interface LoadTasksFailureCallback {
        void loadTasksFailureCallback(final Exception e);
    }

    interface AddTaskCallback {
        void addTaskCallback(final TaskModel task);
    }

    interface AddTaskFailureCallback {
        void addTaskFailureCallback(final TaskModel task, final Exception e);
    }

    interface UpdateTaskCallback {
        void updateTaskCallback(final TaskModel task);
    }

    interface UpdateTaskFailureCallback {
        void updateTaskFailureCallback(final TaskModel task, final Exception e);
    }

    interface UpdateTaskStatusCallback {
        void updateTaskStatusCallback(final long id, final int status);
    }

    interface UpdateTaskStatusFailureCallback {
        void updateTaskStatusFailureCallback(final long id, final int status, final Exception e);
    }

}
