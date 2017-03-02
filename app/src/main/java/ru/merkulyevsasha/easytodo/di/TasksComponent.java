package ru.merkulyevsasha.easytodo.di;

import javax.inject.Singleton;

import dagger.Component;
import ru.merkulyevsasha.easytodo.service.TasksService;
import ru.merkulyevsasha.easytodo.presentation.taskslist.TasksActivity;


@Singleton
@Component(modules={AppModule.class, RepoTasksModule.class, DomainTasksModule.class, PresTasksModule.class})
public interface TasksComponent {

    void inject(TasksActivity context);
    void inject(TasksService context);

}
