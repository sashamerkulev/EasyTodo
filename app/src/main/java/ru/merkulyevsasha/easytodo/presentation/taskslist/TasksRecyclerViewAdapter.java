package ru.merkulyevsasha.easytodo.presentation.taskslist;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.provider.Settings;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ru.merkulyevsasha.core.domain.TaskModel;
import ru.merkulyevsasha.easytodo.R;


public class TasksRecyclerViewAdapter extends RecyclerView.Adapter<TasksRecyclerViewAdapter.ViewHolder>{

    public List<TaskModel> models;
    private OnTaskListener listener;
    private OnStatusTaskListener statusListener;

    public TasksRecyclerViewAdapter(List<TaskModel> models, OnTaskListener listener, OnStatusTaskListener statusListener){
        this.models = models;
        this.listener = listener;
        this.statusListener = statusListener;
    }

    @Override
    public TasksRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_task_item, parent, false);
        return new ViewHolder(view, new OnPositionListener() {
            @Override
            public void onPositionItemClick(int position) {
                listener.onTaskClick(position, models.get(position));
            }
        }, new OnStatusListener() {
            @Override
            public void onPositionItemClick(int position) {
                statusListener.onStatusTaskClick(position, models.get(position));
            }
        });
    }

    @Override
    public void onBindViewHolder(TasksRecyclerViewAdapter.ViewHolder holder, int position) {
        TaskModel item = models.get(position);

        holder.created.setText(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(new Date(item.getCreatedAt())));
        holder.expiry.setText(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(new Date(item.getExpiryAt())));
        holder.shortName.setText(item.getShortName());
        holder.status.setText(item.getStatusName());

        Calendar calendar = Calendar.getInstance();
        long a = item.getExpiryAt() - calendar.getTimeInMillis();
        long b = item.getExpiryAt() - item.getCreatedAt();
        float colorState = Math.abs(a/b);
        //holder.status.setText(String.valueOf(colorState));

        ColorStateList csl;
        if (colorState < 0.33){
            csl = ColorStateList.valueOf(Color.rgb(76, 175, 80)); // green
        } else if (colorState < 0.67){
            csl = ColorStateList.valueOf(Color.rgb(253, 216, 53)); // yellow
        } else if (colorState < 1){
            csl = ColorStateList.valueOf(Color.rgb(211, 47, 47)); // red
        } else {
            csl = ColorStateList.valueOf(Color.rgb(33, 33, 33)); // black
        }

        DrawableCompat.setTintList(holder.imageview.getDrawable(), csl);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView created;
        private final TextView expiry;
        private final TextView shortName;
        private final TextView status;
        private final ImageView imageview;

        public ViewHolder(View itemView, final OnPositionListener listener, final OnStatusListener statusListener) {
            super(itemView);
            created = (TextView)itemView.findViewById(R.id.textview_created);
            expiry = (TextView)itemView.findViewById(R.id.textview_expiry);
            shortName = (TextView)itemView.findViewById(R.id.textview_short_name);
            status = (TextView)itemView.findViewById(R.id.textview_status);
            imageview = (ImageView)itemView.findViewById(R.id.imageview);

            status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    statusListener.onPositionItemClick(getAdapterPosition());
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onPositionItemClick(getAdapterPosition());
                }
            });

        }
    }

    public interface OnTaskListener{
        void onTaskClick(int position, TaskModel model);
    }

    public interface OnStatusTaskListener{
        void onStatusTaskClick(int position, TaskModel model);
    }

    public interface OnPositionListener{
        void onPositionItemClick(int position);
    }

    public interface OnStatusListener{
        void onPositionItemClick(int position);
    }

}
