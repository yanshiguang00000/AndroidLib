package com.bear.bear.activity;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bear.bear.R;
import com.bear.bear.util.ActManager;


/**
 * @author ysg
 * @date 2017/8/7 0005
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    public Dialog dialog;
    public Context mContext;
    protected ImageButton ib_back;
    protected TextView tv_base_title;
    protected TextView tv_base_title_right;
    protected ImageView iv_base_title_right;

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void loadData();

    protected abstract void initListener();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        ActManager.getAppManager().addActivity(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActManager.getAppManager().finishActivity(this);

    }

    /**
     * startActivity
     *
     * @param clazz 需要跳转的activity
     */
    protected void readyGo(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    /**
     * startActivity with bundle
     *
     * @param clazz  需要跳转的activity
     * @param bundle 携带的bundle
     */
    protected void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
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
        Intent intent = new Intent(this, clazz);
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
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * startActivity then finish
     *
     * @param clazz 需要跳转的activity
     */
    protected void readyGoThenKill(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        finish();
    }

    /**
     * startActivity with bundle then finish
     *
     * @param clazz  需要跳转的activity
     * @param bundle 携带的参数
     */
    protected void readyGoThenKill(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        finish();
    }

    /**
     * 用来跳转页面
     *
     * @param fragment 跳转到指定fragment，带BackStack
     */
    public void skip2Fragment(int container, Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(container, fragment).addToBackStack(null).commit();
    }

    protected void skip2FragmentWithoutBS(int container, Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(container, fragment).commit();
    }

    //dialog utils
    public void showDialog() {
        dialog = new Dialog(this);
//			dialog.setTitle("loading.......");
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(0x00ffffff));

        dialog.setCanceledOnTouchOutside(false);// 点击对话框周边不消失
        View v = View.inflate(mContext, R.layout.loading, null);
        dialog.setContentView(v);
        dialog.show();
    }

    public void showDialog(String text) {
        dialog = new Dialog(this);
//			dialog.setTitle("loading.......");
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(0x00ffffff));

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

    /**
     * 初始化顶部title栏(需要include title布局)
     *
     * @param title    中间的标题
     * @param right    右侧的文本
     * @param listener 右侧文本的点击事件
     */
    protected void initTitle(String title, String right, View.OnClickListener listener) {
        ib_back = (ImageButton) findViewById(R.id.ib_back);
        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tv_base_title = (TextView) findViewById(R.id.tv_base_title);
        tv_base_title.setText(title);
        tv_base_title_right = (TextView) findViewById(R.id.tv_base_title_right);
        if (TextUtils.isEmpty(right)) {
            tv_base_title_right.setVisibility(View.GONE);
        } else {
            tv_base_title_right.setVisibility(View.VISIBLE);
            tv_base_title_right.setText(right);
            tv_base_title_right.setOnClickListener(listener);
        }

    }

    /**
     * @param title        中间的标题
     * @param drawable     右边图片
     * @param listener     右侧图片的点击事件
     * @param listenerleft 左侧点击事件
     */
    public void initTitle(String title, int drawable, View.OnClickListener listener, View.OnClickListener listenerleft) {
        initTitle(title, null, null);
        if (drawable != 0) {
            iv_base_title_right = (ImageView) findViewById(R.id.iv_base_title_right);
            iv_base_title_right.setImageResource(drawable);
            iv_base_title_right.setVisibility(View.VISIBLE);
            if (listener != null) {
                iv_base_title_right.setOnClickListener(listener);
            }
            if (listenerleft != null)
                ib_back.setOnClickListener(listenerleft);
        }


    }

    /**
     * @param title        中间的标题
     * @param right        右侧的文本
     * @param listener     右侧文本的点击事件
     * @param listenerleft 左侧点击事件
     */
    public void initTitle(String title, String right, View.OnClickListener listener, View.OnClickListener listenerleft) {
        initTitle(title, right, listener);
        if (listenerleft != null)
            ib_back.setOnClickListener(listenerleft);
    }


    protected RelativeLayout rl_no_data;//无数据总页面
    private TextView tv_error;
    private LinearLayout ll_progress;

    /**
     * 加载、失败界面初始化
     *
     * @param listener 点击图标监听
     */
    protected void initErrorAndLoading(final OnListener listener) {
        rl_no_data = (RelativeLayout) findViewById(R.id.rl_no_data);
        ll_progress = (LinearLayout) findViewById(R.id.ll_progress);
        tv_error = (TextView) findViewById(R.id.tv_error);
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
