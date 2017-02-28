package ru.merkulyevsasha.core.mapping;


import java.util.ArrayList;
import java.util.List;

import ru.merkulyevsasha.core.data.TaskEntity;
import ru.merkulyevsasha.core.domain.TaskModel;

public class TasksMapper {

    public TasksMapper(){

    }

    public TaskModel mapToModel(TaskEntity task){
        TaskModel model = new TaskModel();

        model.setId(task.getId());
        model.setExpiryAt(task.getExpiryAt());
        model.setStatus(task.getStatus());
        model.setShortDescription(task.getShortDescription());
        model.setShortName(task.getShortName());
        model.setCreatedAt(task.getCreatedAt());

        return model;
    }

    public List<TaskModel> mapToModel(List<TaskEntity> tasks){
        List<TaskModel> models = new ArrayList<TaskModel>();

        for (TaskEntity task :
                tasks) {
            models.add(mapToModel(task));
        }

        return models;
    }

    public TaskEntity mapToEntity(TaskModel model){
        TaskEntity task = new TaskEntity();

        task.setId(model.getId());
        task.setExpiryAt(model.getExpiryAt());
        task.setStatus(model.getStatus());
        task.setShortDescription(model.getShortDescription());
        task.setShortName(model.getShortName());
        task.setCreatedAt(model.getCreatedAt());

        return task;
    }

    public List<TaskEntity> mapToEntity(List<TaskModel> models){
        List<TaskEntity> tasks = new ArrayList<TaskEntity>();

        for (TaskModel model :
                models) {
            tasks.add(mapToEntity(model));
        }

        return tasks;
    }


}



