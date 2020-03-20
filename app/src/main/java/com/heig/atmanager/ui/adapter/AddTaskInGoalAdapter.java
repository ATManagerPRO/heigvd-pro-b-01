package com.heig.atmanager.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.heig.atmanager.GoalTask;
import com.heig.atmanager.R;

import java.util.ArrayList;


public class AddTaskInGoalAdapter extends RecyclerView.Adapter<AddTaskInGoalAdapter.MyViewHolder>{

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView quantity ;
        private TextView unit;
        private TextView intervalNumber;
        private TextView interval;

        private Button editButton;
        private Button deleteButton;

        public MyViewHolder( View itemView) {
            super(itemView);

            quantity = itemView.findViewById(R.id.card_goal_display_quantity);
            unit = itemView.findViewById(R.id.card_goal_display_unit);
            intervalNumber = itemView.findViewById(R.id.card_goal_display_interval_number);
            interval = itemView.findViewById(R.id.card_goal_display_interval);

            editButton = itemView.findViewById(R.id.card_goal_display_edit_button);
            deleteButton = itemView.findViewById(R.id.card_goal_display_cancel_button);
        }
    }

    private ArrayList<GoalTask> goals;

    public AddTaskInGoalAdapter(ArrayList<GoalTask> goals) {
        this.goals = goals;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_goal_display, parent, false);

        MyViewHolder viewHolder = new MyViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        GoalTask currentGoalTodo = goals.get(position);
        holder.quantity.setText(String.valueOf(currentGoalTodo.getQuantity()));
        holder.unit.setText(currentGoalTodo.getUnit());
        holder.intervalNumber.setText(String.valueOf(currentGoalTodo.getIntervalNumber()));
        holder.interval.setText(currentGoalTodo.getInterval().toString());

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goals.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, goals.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return goals.size();
    }


}
