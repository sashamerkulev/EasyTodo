package ru.merkulyevsasha.data;

import java.util.List;

import ru.merkulyevsasha.core.data.TaskEntity;
import ru.merkulyevsasha.core.data.TasksRepository;


public class NetTasksRepositoryImpl implements TasksRepository {
    @Override
    public List<TaskEntity> getTasks() {
        return null;
    }

    @Override
    public long addTask(final TaskEntity task) {
        return -1;
    }

    @Override
    public void updateTask(final TaskEntity task) {

    }

    @Override
    public void setTaskStatus(final long id, final int status) {

    }
}
