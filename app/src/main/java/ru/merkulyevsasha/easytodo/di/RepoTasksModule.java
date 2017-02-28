package ru.merkulyevsasha.easytodo.di;

import android.content.Context;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.merkulyevsasha.core.data.TasksRepository;
import ru.merkulyevsasha.data.DbTasksRepositoryImpl;
import ru.merkulyevsasha.data.NetTasksRepositoryImpl;


@Module
public class RepoTasksModule {

    @Singleton
    @Provides
    public TasksRepository getDbRepo(Context context){
        return new DbTasksRepositoryImpl(new File(context.getFilesDir(), DbTasksRepositoryImpl.DATABASE_NAME).getPath());
    }

//    @Singleton
//    @Provides
//    public TasksRepository getNetRepo(Context context){
//        return new NetTasksRepositoryImpl();
//    }

}
