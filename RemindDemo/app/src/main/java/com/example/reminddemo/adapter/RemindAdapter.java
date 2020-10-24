package com.example.reminddemo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckedTextView;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reminddemo.R;
import com.example.reminddemo.data.LiveDataBus;
import com.example.reminddemo.data.RemindBeforeListBean;
import com.example.reminddemo.db.RemindBefore;

import java.util.ArrayList;
import java.util.List;

public class RemindAdapter extends RecyclerView.Adapter<RemindAdapter.MyViewHolder> {

    private Context context;
    private List<RemindBefore> list;
    public List<RemindBefore> checkedList;
    private final MutableLiveData<RemindBeforeListBean> remindBeforeData;
    private final RemindBeforeListBean remindBeforeListBean;
    private static final String TAG = "RemindAdapter";

    public RemindAdapter(Context context, List<RemindBefore> list) {
        this.context = context;
        this.list = list;
        checkedList = new ArrayList<>();
        LiveDataBus liveDataBus = LiveDataBus.get();
        remindBeforeData = liveDataBus.getChannel("remindBefore", RemindBeforeListBean.class);
        remindBeforeListBean = new RemindBeforeListBean();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.layout_remind,parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tv_title.setText(list.get(position).getRemark());

        for (RemindBefore remindBefore: checkedList) {
            if(remindBefore.getRemark().equals(list.get(position).getRemark())) {
                holder.ib_check.setChecked(true);
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_title;
        AppCompatCheckedTextView ib_check;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            ib_check = itemView.findViewById(R.id.ib_check);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(ib_check.isChecked()) {
                ib_check.setChecked(false);
                RemindBefore remindBefore = list.get(getAdapterPosition());
                checkedList.remove(remindBefore);
            }else {
                ib_check.setChecked(true);
                checkedList.add(list.get(getAdapterPosition()));
            }
            Log.d(TAG, "checkedList长度: " + checkedList.size());
            remindBeforeListBean.setRemindBeforeList(checkedList);
            remindBeforeListBean.setRemindBeforeRemark(getRemark(checkedList));
            remindBeforeData.setValue(remindBeforeListBean);
        }
    }

    public String getRemark(List<RemindBefore> remindBeforeList) {
        StringBuilder buffer = new StringBuilder();
        for (RemindBefore remindBefore : remindBeforeList) {
            buffer.append(remindBefore.getRemark()+",");
        }
        if(buffer.length() > 0) {
            buffer.deleteCharAt(buffer.length()-1);
        }
        return buffer.toString();
    }


}
