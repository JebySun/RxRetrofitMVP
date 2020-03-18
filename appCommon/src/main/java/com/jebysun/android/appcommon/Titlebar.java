package com.jebysun.android.appcommon;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jebysun.android.androidcommon.util.ToastUtil;

public class Titlebar extends Toolbar {
    String centerText;
    int textColor = 10;
    int mTitleSize = 16;

    private ImageView ivBack;
    private TextView tvCenter;

    private OnClickBackListener backListener;

    public Titlebar(Context context) {
        this(context, null);
    }

    public Titlebar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public Titlebar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    private void init(Context context, @Nullable AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.widget_titlebar, this);
        ivBack = findViewById(R.id.iv_toolbar_back);
        tvCenter = findViewById(R.id.tv_toolbar_title);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Titlebar, 0, 0);
        centerText = typedArray.getString(R.styleable.Titlebar_titleText);
        textColor = typedArray.getColor(R.styleable.Titlebar_titleColor, textColor);
        typedArray.recycle();

        setTitleText(centerText);
        setTitleColor(textColor);
        setTitleSize(mTitleSize);

        ivBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBack();
            }
        });
    }



    public void setOnClickBackListener(OnClickBackListener listener) {
        backListener = listener;
    }

    public void onClickBack() {
        if (backListener != null) {
            backListener.onBack();
            return;
        }
        if (getContext() instanceof Activity) {
            ((Activity) getContext()).finish();
            return;
        }
        ToastUtil.showToast("Titlebar目前只能存在于Activity的布局中");
    }


    public void setTitleText(String centerText) {
        this.centerText = centerText;
        tvCenter.setText(centerText);
    }


    public void setTitleColor(int color) {
        textColor = color;
        tvCenter.setTextColor(textColor);
    }

    public void setTitleSize(int titleSize) {
        mTitleSize = titleSize;
        tvCenter.setTextSize(mTitleSize);
    }


    public interface OnClickBackListener {
        void onBack();
    }
}
