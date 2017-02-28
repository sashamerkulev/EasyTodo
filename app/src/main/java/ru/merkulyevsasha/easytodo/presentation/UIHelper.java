package ru.merkulyevsasha.easytodo.presentation;

import android.content.Context;

import ru.merkulyevsasha.core.domain.TaskModel;
import ru.merkulyevsasha.easytodo.R;


public class UIHelper {

    public static final String getStatusName(Context context, int statusId){
        if (statusId == TaskModel.STATUS_CREATED)
            return context.getString(R.string.task_new_status_dialog_text);
        if (statusId == TaskModel.STATUS_DONE)
            return context.getString(R.string.task_done_status_dialog_text);
        if (statusId == TaskModel.STATUS_INPROGRESS)
            return context.getString(R.string.task_inprogress_status_dialog_text);
        if (statusId == TaskModel.STATUS_SUSPENDED)
            return context.getString(R.string.task_suspend_status_dialog_text);
        return "";
    }


}
