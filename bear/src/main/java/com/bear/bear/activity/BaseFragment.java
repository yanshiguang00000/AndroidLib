package com.bear.bear.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bear.bear.R;


/**
 *
 *
 * @author ysg
 * @date 2017/8/7 0005
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener{

    protected Dialog dialog;
    protected Context mContext;
    protected abstract void initView(LayoutInflater inflater);
    protected abstract void initData();
    protected abstract void loadData();
    protected abstract void initListener();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    /**
     * startActivity
     *
     * @param clazz 需要跳转的activity
     */
    protected void readyGo(Class<?> clazz) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivity(intent);
    }

    /**
     * startActivity with bundle
     *
     * @param clazz  需要跳转的activity
     * @param bundle 携带的bundle
     */
    protected void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * startActivityForResult
     *
     * @param clazz       需要跳转的activity
     * @param requestCode 请求码
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivityForResult(intent, requestCode);
    }

    /**
     * startActivityForResult with bundle
     *
     * @param clazz       需要跳转的activity
     * @param requestCode 请求码
     * @param bundle      携带的参数
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 用来跳转页面
     *
     * @param fragment 跳转到指定fragment，带BackStack
     */
    protected void skip2Fragment(int container, Fragment fragment) {
        getFragmentManager().beginTransaction().replace(container, fragment).addToBackStack(null).commit();
    }

    protected void skip2FragmentWithoutBS(int container, Fragment fragment) {
        getFragmentManager().beginTransaction().replace(container, fragment).commit();
    }

    //dialog utils
    public void showDialog() {
        dialog = new Dialog(getActivity());
//			dialog.setTitle("loading.......");
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(0x00ffffff));

        dialog.setCanceledOnTouchOutside(false);// 点击对话框周边不消失
        dialog.setCanceledOnTouchOutside(false);// 点击对话框周边不消失
        View v = View.inflate(mContext, R.layout.loading, null);
        TextView tv = (TextView) v.findViewById(R.id.textView5);
        tv.setText("正在加载...");
        dialog.setContentView(v);
        dialog.show();
    }

    public void showDialog(String text) {
        dialog = new Dialog(getActivity());
//			dialog.setTitle("loading.......");
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(0x00ffffff));

        dialog.setCanceledOnTouchOutside(false);// 点击对话框周边不消失
        dialog.setCanceledOnTouchOutside(false);// 点击对话框周边不消失
        View v = View.inflate(mContext, R.layout.loading, null);
        TextView tv = (TextView) v.findViewById(R.id.textView5);
        tv.setText(text);
        dialog.setContentView(v);
        dialog.show();
    }

    public void hideDialog() {

        try {
            dialog.dismiss();
        } catch (Exception e) {
        }
    }

    public RelativeLayout rl_no_data;
    private TextView tv_error;
    private LinearLayout ll_progress;

    /**
     * 加载、失败界面初始化
     *
     * @param listener 点击图标监听
     */
    protected void initErrorAndLoading(View view, final OnListener listener) {

        ll_progress = (LinearLayout) view.findViewById(R.id.ll_progress);
        rl_no_data = (RelativeLayout) view.findViewById(R.id.rl_no_data);
        tv_error = (TextView) view.findViewById(R.id.tv_error);
        tv_error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_error.setVisibility(View.GONE);
                ll_progress.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listener.onClick();
                    }
                }, 1000);

            }
        });
    }

    protected void showError() {
        rl_no_data.setVisibility(View.VISIBLE);
        tv_error.setVisibility(View.VISIBLE);
        tv_error.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(mContext, R.drawable.no_internet), null, null);
        tv_error.setText("网络不好，点击重新加载");
        ll_progress.setVisibility(View.GONE);
    }

    protected void showNoData() {
        rl_no_data.setVisibility(View.VISIBLE);
        tv_error.setVisibility(View.VISIBLE);
        tv_error.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(mContext, R.drawable.blank), null, null);
        tv_error.setText("暂时没有相关信息，先看看别的吧");
        ll_progress.setVisibility(View.GONE);
    }

    protected void showLoading() {
        rl_no_data.setVisibility(View.VISIBLE);
        tv_error.setVisibility(View.GONE);
        ll_progress.setVisibility(View.VISIBLE);
    }

    protected void hideErrorAndLoading() {
        rl_no_data.setVisibility(View.GONE);
        tv_error.setVisibility(View.GONE);
        ll_progress.setVisibility(View.GONE);
    }

    public interface OnListener {
        void onClick();
    }


}
