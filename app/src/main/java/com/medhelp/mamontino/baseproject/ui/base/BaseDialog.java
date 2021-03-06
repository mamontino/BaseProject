package com.medhelp.mamontino.baseproject.ui.base;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;


public abstract class BaseDialog extends DialogFragment implements DialogMvpView
{
    private BaseActivity activity;

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof BaseActivity)
        {
            BaseActivity mActivity = (BaseActivity) context;
            this.activity = mActivity;
            mActivity.onFragmentAttached();
        }
    }

    @Override
    public void showLoading()
    {
        if (activity != null)
        {
            activity.showLoading();
        }
    }

    @Override
    public void showLineLoading()
    {
        if (activity != null)
        {
            activity.showLineLoading();
        }
    }

    @Override
    public void hideLoading()
    {
        if (activity != null)
        {
            activity.hideLoading();
        }
    }

    @Override
    public void showError(String message)
    {
        if (activity != null)
        {
            activity.showError(message);
        }
    }

    @Override
    public void showError(@StringRes int resId)
    {
        if (activity != null)
        {
            activity.showError(resId);
        }
    }

    @Override
    public void showMessage(String message)
    {
        if (activity != null)
        {
            activity.showMessage(message);
        }
    }

    @Override
    public void showMessage(@StringRes int resId)
    {
        if (activity != null)
        {
            activity.showMessage(resId);
        }
    }

    @Override
    public boolean isNetworkConnected()
    {
        if (activity != null)
        {
            return activity.isNetworkConnected();
        }
        return false;
    }

    @Override
    public void onDetach()
    {
        activity = null;
        super.onDetach();
    }

    @Override
    public void hideKeyboard()
    {
        if (activity != null)
        {
            activity.hideKeyboard();
        }
    }

    public BaseActivity getBaseActivity()
    {
        return activity;
    }

    protected abstract void setUp(View view);

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        final RelativeLayout root = new RelativeLayout(getActivity());
        root.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(root);
        if (dialog.getWindow() != null)
        {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        dialog.setCanceledOnTouchOutside(false);

        return dialog;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        setUp(view);
    }

    public void show(FragmentManager fragmentManager, String tag)
    {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment prevFragment = fragmentManager.findFragmentByTag(tag);
        if (prevFragment != null)
        {
            transaction.remove(prevFragment);
        }
        transaction.addToBackStack(null);
        show(transaction, tag);
    }

    @Override
    public void dismissDialog(String tag)
    {
        dismiss();
        getBaseActivity().onFragmentDetached(tag);
    }
}