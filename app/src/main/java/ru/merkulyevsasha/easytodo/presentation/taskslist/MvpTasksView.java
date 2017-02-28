package ru.merkulyevsasha.easytodo.presentation.taskslist;


import java.util.List;

import ru.merkulyevsasha.core.domain.TaskModel;
import ru.merkulyevsasha.easytodo.presentation.MvpView;

public interface MvpTasksView extends MvpView {

    void showList(List<TaskModel> models);
    void changedTaskStatus(long id, int status);
}
