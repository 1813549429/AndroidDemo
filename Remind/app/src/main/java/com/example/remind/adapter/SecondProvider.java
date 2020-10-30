package com.example.remind.adapter;

import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.remind.R;
import com.example.remind.db.entity.CheckListEntity;
import com.example.remind.db.entity.Remind;
import com.example.remind.ui.MainActivity;
import com.example.remind.utils.DateUtil;


public class SecondProvider extends BaseNodeProvider {

    public static boolean isSelectAll = false;

    @Override
    public int getItemViewType() {
        return 2;
    }

    @Override
    public int getLayoutId() {
        return R.layout.main_item_second;
    }

    @Override
    public void convert(BaseViewHolder helper, BaseNode data) {
        SecondNode entity = (SecondNode) data;
        Remind remindData = entity.getRemindData();
        CheckListEntity checkListEntityData = entity.getCheckListEntityData();

        TextView tv_item_title = helper.findView(R.id.tv_item_title);
        TextView tv_item_date = helper.findView(R.id.tv_item_date);
        ImageView iv_hide = helper.findView(R.id.iv_hide);
        CheckedTextView checkedTextView = helper.findView(R.id.cb_item_complete);
        if(isSelectAll) {
            checkedTextView.setChecked(true);
        }else {
            checkedTextView.setChecked(false);
        }
        if(remindData.getTitle() != null) {
            tv_item_title.setText(remindData.getTitle());
        }
        if(remindData.getTime() != 0) {
            tv_item_date.setText(DateUtil.longToStr(remindData.getTime()));
        }
        ImageView iv_item_img = helper.findView(R.id.iv_item_img);
        tv_item_date.setTextColor(Color.parseColor("#000000"));
        tv_item_title.getPaint().setFlags(Paint.CURSOR_AFTER);
        iv_hide.setVisibility(View.INVISIBLE);

        switch (entity.getMainItemType()) {
            case MainActivity
                    .OVERDUE:
                iv_item_img.setBackgroundResource(R.mipmap.overdue);
                tv_item_date.setTextColor(Color.parseColor("#EB5757"));
                break;
            case MainActivity
                    .TODAY:
                iv_item_img.setBackgroundResource(R.mipmap.today);
                tv_item_date.setTextColor(Color.parseColor("#2F80ED"));

                break;
            case MainActivity
                    .NEXT_SEVEN_DAYS:
                iv_item_img.setBackgroundResource(R.mipmap.next_seven_days);
                tv_item_date.setTextColor(Color.parseColor("#50000000"));
                break;

            case MainActivity
                    .NOT_SCHEDULED:
                iv_item_img.setVisibility(View.GONE);
                tv_item_date.setVisibility(View.GONE);
                break;

            case MainActivity
                    .COMPLETED:
                iv_hide.setVisibility(View.VISIBLE);
                tv_item_date.setTextColor(Color.parseColor("#2F80ED"));
                tv_item_title.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                break;
        }
        if(remindData.isComplete()) {
            checkedTextView.setBackgroundResource(R.mipmap.finish);
        }else {
            checkedTextView.setBackgroundResource(R.drawable.item_second_checkbox);
        }

    }

    @Override
    public void onClick(BaseViewHolder helper, View view, BaseNode data, int position) {
        SecondNode entity = (SecondNode) data;

    }

    @Override
    public boolean onLongClick(BaseViewHolder helper, View view, BaseNode data, int position) {

        return super.onLongClick(helper, view, data, position);
    }


}
