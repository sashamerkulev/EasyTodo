package ru.merkulyevsasha.core.data;

import java.util.List;

public interface TasksRepository {

    List<TaskEntity> getTasks();
    List<TaskEntity> getExpiredTasks(long expired);
    long addTask(final TaskEntity task);
    void updateTask(final TaskEntity task);
    void setTaskStatus(final long id, final int status);

}
