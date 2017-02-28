package ru.merkulyevsasha;

import java.util.List;
import java.util.concurrent.ExecutorService;

import ru.merkulyevsasha.core.data.TaskEntity;
import ru.merkulyevsasha.core.callback.TasksCallback;
import ru.merkulyevsasha.core.data.TasksRepository;
import ru.merkulyevsasha.core.domain.TaskModel;
import ru.merkulyevsasha.core.domain.TasksInteractor;
import ru.merkulyevsasha.core.mapping.TasksMapper;


public class TasksInteractorImpl implements TasksInteractor{

    private final TasksRepository repository;
    private final ExecutorService executor;
    private TasksCallback callback;
    private final TasksMapper mapper;

    public TasksInteractorImpl(TasksRepository repository, ExecutorService executor, TasksMapper mapper){
        this.repository = repository;
        this.executor = executor;
        this.mapper = mapper;
    }

    public void setCallback(TasksCallback callback){
        this.callback = callback;
    }

    @Override
    public void getTasks() {

        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    List<TaskEntity> tasks = repository.getTasks();
                    callback.getTasksCallback(mapper.mapToModel(tasks));
                } catch(Exception e){
                    e.printStackTrace();
                    callback.getTasksFailureCallback(e);
                }
            }
        });

    }

    @Override
    public void addTask(final TaskModel task) {

        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    long id = repository.addTask(mapper.mapToEntity(task));
                    task.setId(id);
                    callback.addTaskCallback(task);
                } catch(Exception e){
                    e.printStackTrace();
                    callback.addTaskFailureCallback(task, e);
                }
            }
        });

    }

    @Override
    public void updateTask(final TaskModel task) {

        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    repository.updateTask(mapper.mapToEntity(task));
                    callback.updateTaskCallback(task);
                } catch(Exception e){
                    e.printStackTrace();
                    callback.updateTaskFailureCallback(task, e);
                }
            }
        });

    }

    @Override
    public void setTaskStatus(final long id, final int status) {

        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    repository.setTaskStatus(id, status);
                    callback.setTaskStatusCallback(id, status);
                } catch(Exception e){
                    e.printStackTrace();
                    callback.setTaskStatusFailureCallback(id, status, e);
                }
            }
        });

    }
}
