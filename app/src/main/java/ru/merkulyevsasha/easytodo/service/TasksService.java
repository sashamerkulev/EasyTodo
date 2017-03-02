package ru.merkulyevsasha.easytodo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import ru.merkulyevsasha.core.domain.TasksInteractor;
import ru.merkulyevsasha.easytodo.TodoApp;

public class TasksService extends Service {

    @Inject
    public TasksInteractor interactor;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        ((TodoApp)getApplication()).getComponent().inject(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    System.out.println("!!service!!");

                } catch(Exception e){
                    e.printStackTrace();
                } finally {
                    AlarmHelper.register(TasksService.this);
                    stopSelf();
                }
            }
        }).start();

        return super.onStartCommand(intent, flags, startId);
    }

}
