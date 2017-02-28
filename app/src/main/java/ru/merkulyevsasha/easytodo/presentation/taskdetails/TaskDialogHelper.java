package ru.merkulyevsasha.easytodo.presentation.taskdetails;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import ru.merkulyevsasha.easytodo.R;

public class TaskDialogHelper {

    public static Dialog getTaskStatusDialog(final Activity context, int currentItemIndex, final OnDialogIndexListener listener){

        final String[] sortItems = {
                context.getString(R.string.task_new_status_dialog_text),
                context.getString(R.string.task_inprogress_status_dialog_text),
                context.getString(R.string.task_suspend_status_dialog_text),
                context.getString(R.string.task_done_status_dialog_text)
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.task_status_dialog_title);

        builder.setSingleChoiceItems(sortItems, currentItemIndex,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int index) {

                        dialog.dismiss();

                        listener.onClick(index);
                    }
                });

        return builder.create();

    }

    public interface OnDialogIndexListener{
        void onClick(int status);
    }

}
