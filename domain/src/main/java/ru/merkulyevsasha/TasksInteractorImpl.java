package ru.merkulyevsasha;

import java.util.ArrayList;
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
    private final TasksMapper mapper;

    public TasksInteractorImpl(TasksRepository repository, ExecutorService executor, TasksMapper mapper){
        this.repository = repository;
        this.executor = executor;
        this.mapper = mapper;
    }

    @Override
    public void loadTasks(final TasksCallback.LoadTasksCallback loadCallback, final TasksCallback.LoadTasksFailureCallback loadFailureCallback) {

        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    List<TaskEntity> tasks = repository.getTasks();
                    loadCallback.loadTasksCallback(mapper.mapToModel(tasks));
                } catch(Exception e){
                    e.printStackTrace();
                    loadFailureCallback.loadTasksFailureCallback(e);
                }
            }
        });

    }

    @Override
    public void loadTasks(ArrayList<Integer> ids, final TasksCallback.LoadTasksCallback loadCallback, final TasksCallback.LoadTasksFailureCallback loadFailureCallback) {

        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    List<TaskEntity> tasks = repository.getTasks();
                    loadCallback.loadTasksCallback(mapper.mapToModel(tasks));
                } catch(Exception e){
                    e.printStackTrace();
                    loadFailureCallback.loadTasksFailureCallback(e);
                }
            }
        });

    }


    @Override
    public void loadExpiredTasks(final TasksCallback.LoadTasksCallback loadCallback, final TasksCallback.LoadTasksFailureCallback loadFailureCallback) {

        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    List<TaskEntity> tasks = repository.getExpiredTasks(System.currentTimeMillis());
                    loadCallback.loadTasksCallback(mapper.mapToModel(tasks));
                } catch(Exception e){
                    e.printStackTrace();
                    loadFailureCallback.loadTasksFailureCallback(e);
                }
            }
        });

    }


    @Override
    public void addTask(final TaskModel task, final TasksCallback.AddTaskCallback addCallback, final TasksCallback.AddTaskFailureCallback addFailureCallback) {

        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    long id = repository.addTask(mapper.mapToEntity(task));
                    task.setId(id);
                    addCallback.addTaskCallback(task);
                } catch(Exception e){
                    e.printStackTrace();
                    addFailureCallback.addTaskFailureCallback(task, e);
                }
            }
        });

    }

    @Override
    public void updateTask(final TaskModel task, final TasksCallback.UpdateTaskCallback updateCallback, final TasksCallback.UpdateTaskFailureCallback updateFailureCallback) {

        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    repository.updateTask(mapper.mapToEntity(task));
                    updateCallback.updateTaskCallback(task);
                } catch(Exception e){
                    e.printStackTrace();
                    updateFailureCallback.updateTaskFailureCallback(task, e);
                }
            }
        });

    }

    @Override
    public void updateTaskStatus(final long id, final int status, final TasksCallback.UpdateTaskStatusCallback updateStatusCallback,
                              final TasksCallback.UpdateTaskStatusFailureCallback updateStatusFailureCallback) {

        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    repository.setTaskStatus(id, status);
                    updateStatusCallback.updateTaskStatusCallback(id, status);
                } catch(Exception e){
                    e.printStackTrace();
                    updateStatusFailureCallback.updateTaskStatusFailureCallback(id, status, e);
                }
            }
        });

    }
}
