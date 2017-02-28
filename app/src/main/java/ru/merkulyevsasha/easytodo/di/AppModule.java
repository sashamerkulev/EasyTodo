package ru.merkulyevsasha.easytodo.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class AppModule {

    private final Context appContext;

    public AppModule(Context appContext){
        this.appContext = appContext;
    }

    @Singleton
    @Provides
    Context providesContext() {
        return appContext;
    }


}
