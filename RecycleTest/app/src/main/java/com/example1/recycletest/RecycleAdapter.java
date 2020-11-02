package com.example1.recycletest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private static final int VIEW_TYPE_HEADER = 1;
    private static final int VIEW_TYPE_FIRST = 2;
    private static final int VIEW_TYPE_SECOND = 3;

    private int clickPosition;
    private RecyclerView.ViewHolder clickHolder;

    String header;
    List<AdapterBean> adapterBeanList;
    MainActivity activity;

    public RecycleAdapter(MainActivity activity, List<AdapterBean> adapterBeanList, String header) {
        this.activity = activity;
        this.header = header;
        this.adapterBeanList = adapterBeanList;
    }

    public void setHeaderStr(String header) {
        this.header = header;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_HEADER) {
            return new HeaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header, parent, false));
        } else if (viewType == VIEW_TYPE_FIRST) {
            return new FirstViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item_first, parent, false));
        }else {
            return new SecondViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item_second, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == VIEW_TYPE_HEADER) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            headerViewHolder.textView.setText(header);
        } else if (getItemViewType(position) == VIEW_TYPE_FIRST) {

            final FirstViewHolder firstViewHolder = (FirstViewHolder) holder;
            AdapterBean adapterBean = adapterBeanList.get(getFirstPosition(position));

            firstViewHolder.title.setText(adapterBean.getTitle());
            firstViewHolder.item_count.setText(adapterBean.getChildNodes().size()+"");

            if(adapterBean.isExpend()) {
                firstViewHolder.iv.setRotation(0);
            } else {
                firstViewHolder.iv.setRotation(-90);
            }

            firstViewHolder.ll_item.setBackgroundColor(adapterBean.getBgColor());
            firstViewHolder.ll_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickHolder = firstViewHolder;
                    clickPosition = position;
                    setArrowSpin(firstViewHolder.iv, clickPosition);
                }
            });
        }else {
            SecondViewHolder secondViewHolder = (SecondViewHolder) holder;
            int firstPosition = getFirstPosition(position)-1;
            AdapterBean adapterBean = adapterBeanList.get(firstPosition);
            int secondPosition = getSecondPosition(position, firstPosition);
            Remind remind = adapterBean.getChildNodes().get(secondPosition);
            secondViewHolder.cb_item_complete.setChecked(remind.isCompleted());
            secondViewHolder.tv_item_title.setText(remind.getTitle());
            secondViewHolder.tv_item_date.setText(remind.getTime()+"");
            if (!adapterBean.isExpend()) {
                secondViewHolder.itemView.setVisibility(View.GONE);
                ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(1,1);
                secondViewHolder.itemView.setLayoutParams(layoutParams);
            } else {
                secondViewHolder.itemView.setVisibility(View.VISIBLE);
                ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,ConstraintLayout.LayoutParams.WRAP_CONTENT);
                secondViewHolder.itemView.setLayoutParams(layoutParams);
            }
        }
    }

    public void setArrowSpin(View view, int position) {
        int firstPosition = getFirstPosition(position);
        AdapterBean adapterBean = adapterBeanList.get(firstPosition);
        if (!adapterBean.isExpend()) {

            ViewCompat.animate(view).setDuration(200)
                    .setInterpolator(new DecelerateInterpolator())
                    .rotation(0f)
                    .start();

        }else {

            ViewCompat.animate(view).setDuration(200)
                    .setInterpolator(new DecelerateInterpolator())
                    .rotation(-90f)
                    .start();

        }
        adapterBean.setExpend(!adapterBean.isExpend());
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        int itemCount = 1;

        for (AdapterBean adapterBean : adapterBeanList) {
            itemCount += adapterBean.getChildNodes().size();
        }

        itemCount += adapterBeanList.size();
        return itemCount;
    }


    @Override
    public int getItemViewType(int position) {


        if (position == 0) {
            return VIEW_TYPE_HEADER;
        }

        int maxLength = 1;
        for (int i=0; i<adapterBeanList.size(); i++) {
            List<Remind> reminds = adapterBeanList.get(i).getChildNodes();
            if (maxLength == position) {
                return VIEW_TYPE_FIRST;
            }
            maxLength += reminds.size();
            maxLength++;
        }
        return VIEW_TYPE_SECOND;

    }

    public int getFirstPosition(int position) {
        int maxLength = 1;
        int firstPosition = 0;
        for (int i=0; i<adapterBeanList.size(); i++) {
            List<Remind> reminds = adapterBeanList.get(i).getChildNodes();
            if (maxLength >= position) {
                return firstPosition;
            }
            maxLength += reminds.size();
            maxLength++;
            firstPosition++;
        }
        return firstPosition;
    }

    public int getSecondPosition(int position, int firstPosition) {
        int maxLength = 1;
        int secondPosition = 0;
        for (int i=0; i< firstPosition; i++) {
            maxLength += adapterBeanList.get(i).getChildNodes().size();
            maxLength++;
        }
        secondPosition = position - maxLength - 1;
        return secondPosition;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_item:

                break;
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_title_center);
        }
    }

    class FirstViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout ll_item;
        public ImageView iv;
        public TextView title;
        public TextView item_count;

        public FirstViewHolder(@NonNull View itemView) {
            super(itemView);
            ll_item = itemView.findViewById(R.id.ll_item);
            iv = itemView.findViewById(R.id.iv);
            title= itemView.findViewById(R.id.title);
            item_count = itemView.findViewById(R.id.item_count);

        }
    }

    class SecondViewHolder extends RecyclerView.ViewHolder {

        public CheckedTextView cb_item_complete;
        public TextView tv_item_title;
        public ImageView iv_item_img;
        public TextView tv_item_date;
        public ConstraintLayout cl_second_item;

        public SecondViewHolder(@NonNull View itemView) {
            super(itemView);
            cb_item_complete = itemView.findViewById(R.id.cb_item_complete);
            tv_item_title = itemView.findViewById(R.id.tv_item_title);
            iv_item_img = itemView.findViewById(R.id.iv_item_img);
            tv_item_date = itemView.findViewById(R.id.tv_item_date);
            cl_second_item = itemView.findViewById(R.id.cl_second_item);
        }
    }
}
