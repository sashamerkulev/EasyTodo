package ru.merkulyevsasha.easytodo;

import android.app.Application;

import ru.merkulyevsasha.easytodo.di.AppModule;
import ru.merkulyevsasha.easytodo.di.DaggerTasksComponent;
import ru.merkulyevsasha.easytodo.di.DomainTasksModule;
import ru.merkulyevsasha.easytodo.di.PresTasksModule;
import ru.merkulyevsasha.easytodo.di.RepoTasksModule;
import ru.merkulyevsasha.easytodo.di.TasksComponent;


public class TodoApp extends Application {

    private TasksComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerTasksComponent.builder()
                .appModule(new AppModule(this))
                .repoTasksModule(new RepoTasksModule())
                .domainTasksModule(new DomainTasksModule())
                .presTasksModule(new PresTasksModule())
                .build();
    }

    public TasksComponent getComponent() {
        return component;
    }

}
