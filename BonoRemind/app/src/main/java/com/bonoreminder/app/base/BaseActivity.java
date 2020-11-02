package com.bonoreminder.app.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.bonoreminder.app.R;
import com.bonoreminder.app.utils.ClassUtil;
import com.bonoreminder.app.utils.StatusBarUtil;


public abstract class BaseActivity<VM extends ViewModel, DB extends ViewDataBinding> extends AppCompatActivity {

    protected abstract int getLayoutId();
    public abstract void click(int mode);
    protected VM viewModel;
    protected DB bindingView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingView = DataBindingUtil.setContentView(this, getLayoutId());
        bindingView.setLifecycleOwner(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        StatusBarUtil.setColor(this, getResources().getColor(R.color.activity_bg));

        initViewModel();
    }

    protected void initViewModel(){
        Class<VM> viewModelClass = ClassUtil.getViewModel(this);
        if (viewModelClass != null) {
            viewModel = new ViewModelProvider(this).get(viewModelClass);
        }
    }

    public VM getViewModel() {
        return viewModel;
    }

}
