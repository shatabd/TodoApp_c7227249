package com.example.todonvvm.todos;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;


import com.example.todonvvm.R;
import com.example.todonvvm.databases.Todo;

import java.util.List;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.TodoViewHolder> {


    private TaskCallBack callBack;
    private Context mContext;
    private List<Todo> mTaskEntries;

    class TodoViewHolder extends RecyclerView.ViewHolder {
        private final TextView todoItemView;
        private final TextView todoDescView;
        private TextView priorityView;
        private ImageView update_image;
        private TextView todoDateView;
        private Button btn;

        private TodoViewHolder(View itemView) {
            super(itemView);
            todoItemView = itemView.findViewById(R.id.title_tv);
            todoDescView = itemView.findViewById(R.id.description_tv);
            priorityView = itemView.findViewById(R.id.priorityTextView);
            btn = itemView.findViewById(R.id.view_task);
            todoDateView = itemView.findViewById(R.id.date_tv);

            update_image = itemView.findViewById(R.id.update_img);

            update_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    callBack.onUpdate(mTodos.get(position));
                }
            });

        }

    }

    public List<Todo> mTodos; // Cached copy of todos

    TodoListAdapter(Context context, TaskCallBack callBack) {
        mContext = context;
        this.callBack = callBack;
    }

    @NonNull
    @Override
    public TodoViewHolder  onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item, parent, false);
        return new TodoViewHolder(itemView);
    }

    // --------------------------------------------- Display Tasks, Description, Priority and Date ------------------------------------------
    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {

        //if (mTodos != null) {
        Todo current = mTodos.get(position);
        //String todoDate = dateFormat.format(current.getCreatedDate().toString());
        int priority = current.getPriority();

        holder.todoItemView.setText(current.getTitle());
        holder.todoDescView.setText(current.getDescription());
        holder.todoDateView.setText(current.getDate());


        String priorityString = "Priority level: " + priority; // converts int to String
        holder.priorityView.setText(priorityString);

        GradientDrawable priorityCircle = (GradientDrawable) holder.priorityView.getBackground();
        // Get the appropriate background color based on the priority
        int priorityColor = getPriorityColor(priority);
        priorityCircle.setColor(priorityColor);

        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
                ViewFragment  viewFragment = new ViewFragment();
                Bundle args = new Bundle();
                args.putString("task_title", current.getTitle());
                args.putString("task_description", current.getDescription());
                args.putString("created_at" , current.getDate());
                args.putInt("task_id" , current.getId());
                viewFragment.setArguments(args);
                fragmentTransaction.replace(R.id.container, viewFragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

    }
    // Method to get color for priority
    private int getPriorityColor(int priority) {
        int priorityColor = 0;

        switch (priority) {
            case 1:
                priorityColor = ContextCompat.getColor(mContext, R.color.materialRed);
                break;
            case 2:
                priorityColor = ContextCompat.getColor(mContext, R.color.materialOrange);
                break;
            case 3:
                priorityColor = ContextCompat.getColor(mContext, R.color.materialYellow);
                break;
            default:
                break;
        }
        return priorityColor;
    }


    void setTodos(List<Todo> todos){
        mTodos = todos;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mTodos != null)
            return mTodos.size();
        else return 0;
    }

    //Interface for update
    public interface TaskCallBack {
        void onItemDeleted(int id);

        void onUpdate(Todo todo);
    }


}