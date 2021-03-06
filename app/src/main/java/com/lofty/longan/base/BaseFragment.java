package com.lofty.longan.base;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * MVP Fragment基类
 */
public abstract class BaseFragment<T extends BasePresenter> extends Fragment implements BaseView {

    protected T presenter;
    protected View view;
    protected Activity activity;
    protected Context context;
    protected boolean isInited = false;

    @Override
    public void onAttach(Context context) {
        activity = (Activity) context;
        this.context = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(getLayoutId(), null);
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = (T) getPresenter();
        presenter.attachView(this);
        if (!isHidden()) {
            isInited = true;
            initEventAndData();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!isInited && !hidden) {
            isInited = true;
            initEventAndData();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) presenter.detachView();
    }

    protected abstract void initView(View view);

    protected abstract int getLayoutId();

    protected abstract void initEventAndData();

    protected abstract BasePresenter getPresenter();
}