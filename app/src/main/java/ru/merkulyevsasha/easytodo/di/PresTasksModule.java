package ru.merkulyevsasha.easytodo.di;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.merkulyevsasha.core.domain.TasksInteractor;
import ru.merkulyevsasha.easytodo.presentation.taskslist.TasksPresenter;

@Module
public class PresTasksModule {

    @Singleton
    @Provides
    public TasksPresenter getTasksPresenter(TasksInteractor interactor){
        return new TasksPresenter(interactor);
    }


}
