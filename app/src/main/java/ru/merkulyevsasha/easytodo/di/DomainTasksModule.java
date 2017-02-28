package ru.merkulyevsasha.easytodo.di;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.merkulyevsasha.TasksInteractorImpl;
import ru.merkulyevsasha.core.data.TasksRepository;
import ru.merkulyevsasha.core.domain.TasksInteractor;
import ru.merkulyevsasha.core.mapping.TasksMapper;


@Module
public class DomainTasksModule {

    @Singleton
    @Provides
    public ExecutorService getExecutorService(){
        return Executors.newCachedThreadPool();
    }

    @Singleton
    @Provides
    public TasksMapper getTasksMapper(){
        return new TasksMapper();
    }

    @Singleton
    @Provides
    public TasksInteractor getTasksInteractor(TasksRepository repository, ExecutorService executor, TasksMapper mapper){
        return new TasksInteractorImpl(repository, executor, mapper);
    }

}
