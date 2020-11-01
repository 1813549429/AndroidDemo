package com.example.bonoremind.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.bonoremind.R;

import java.util.List;

public class RepeatAdapter extends RecyclerView.Adapter<RepeatAdapter.ViewHolder>{

    private Context context;
    private List<String> list;
    private String checkedStr;

    public RepeatAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    public String getCheckedStr() {
        return checkedStr;
    }

    public void setCheckedStr(String checkedStr) {
        this.checkedStr = checkedStr;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.layout_repeat_item, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(list.get(position));
        if (list.get(position).equals(checkedStr)) {
            holder.checkBox.setChecked(true);
        }else {
            holder.checkBox.setChecked(false);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CheckBox checkBox;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkbox);
            textView = itemView.findViewById(R.id.tv_text);
            itemView.findViewById(R.id.ll_repeat).setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            checkBox.setChecked(true);
            checkedStr = textView.getText().toString();
            Log.d("RepeatAdapter", checkedStr);
            notifyDataSetChanged();
        }
    }
}
