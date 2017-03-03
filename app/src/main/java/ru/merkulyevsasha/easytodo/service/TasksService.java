package ru.merkulyevsasha.easytodo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.merkulyevsasha.core.callback.TasksCallback;
import ru.merkulyevsasha.core.domain.TaskModel;
import ru.merkulyevsasha.core.domain.TasksInteractor;
import ru.merkulyevsasha.easytodo.TodoApp;

public class TasksService extends Service{

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
                try {
                    interactor.loadExpiredTasks(new TasksCallback.LoadTasksCallback() {
                        @Override
                        public void loadTasksCallback(List<TaskModel> models) {
                            ArrayList<Integer> ids = new ArrayList<>();
                            for (TaskModel model :
                                    models) {
                                ids.add((int) model.getId());
                            }
                            if (ids.size() > 0) {
                                AlarmHelper.setNotification(TasksService.this, ids);
                            }
                        }
                    }, new TasksCallback.LoadTasksFailureCallback() {
                        @Override
                        public void loadTasksFailureCallback(Exception e) {

                        }
                    });

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
