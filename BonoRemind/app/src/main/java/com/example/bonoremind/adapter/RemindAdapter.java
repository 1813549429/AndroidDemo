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

import java.util.ArrayList;
import java.util.List;

public class RemindAdapter extends RecyclerView.Adapter<RemindAdapter.ViewHolder>{

    private Context context;
    private List<String> list;
    private List<String> checkedList;

    public RemindAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        checkedList = new ArrayList<>();
    }

    public List<String> getCheckedList() {
        return checkedList;
    }
    public void setCheckedList(List<String> checkedList) {
        this.checkedList = checkedList;
        Log.d("RemindAdapter", checkedList.size()+"");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.layout_remind_item, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(list.get(position));
        if (checkedList.contains(list.get(position))) {
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
            itemView.findViewById(R.id.ll_remind).setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.ll_remind) {
                String none = context.getString(R.string.none);
                String oneTime = context.getString(R.string.one_time);
                String checkedText = textView.getText().toString();
                if (checkedText.equals(none)) {
                    checkedList.clear();
                    checkedList.add(checkedText);
                }else if (checkedText.equals(oneTime)) {
                    checkedList.clear();
                    checkedList.add(checkedText);
                }else {
                    if(checkedList.contains(none)) {
                        checkedList.remove(none);
                    }
                    if(checkedList.contains(oneTime)) {
                        checkedList.remove(oneTime);
                    }

                    if(!checkBox.isChecked()) {
                        checkedList.add(textView.getText().toString());
                    }else {
                        checkedList.remove(textView.getText().toString());
                    }
                }

                if(checkedList.size() == 0) {
                    checkedList.add(none);
                }
                notifyDataSetChanged();

            }
        }
    }
}
