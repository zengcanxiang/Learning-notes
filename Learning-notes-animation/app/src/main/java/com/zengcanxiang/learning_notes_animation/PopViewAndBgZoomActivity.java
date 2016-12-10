package com.zengcanxiang.learning_notes_animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * 弹出view，同时背景缩放
 */
public class PopViewAndBgZoomActivity extends AppCompatActivity {

    LinearLayout addPopupParent;
    View popupView;
    Button b;
    boolean isShow;
    View mask;
    View bg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popview_bgzoom);
        mask = findViewById(R.id.activity_mask);
        addPopupParent = (LinearLayout) findViewById(R.id.activity_popup_parent);
        popupView = getLayoutInflater().inflate(R.layout.pop_view, addPopupParent, false);
        b = (Button) findViewById(R.id.start);
        bg = findViewById(R.id.bg);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShow) {
                    isShow = false;
                    popupView.setVisibility(View.GONE);
                } else {
                    isShow = true;
                    if (addPopupParent.getChildAt(0) == null) {
                        addPopupParent.addView(popupView);
                    } else {
                        popupView.setVisibility(View.VISIBLE);
                    }
                    mask.setVisibility(View.VISIBLE);
                }
            }
        });
        initAnimator();


    }


    private void initAnimator() {
        LayoutTransition transition = new LayoutTransition();
        //设置定义动画
        //设置隐藏动画
        ObjectAnimator disAnimator = ObjectAnimator.ofFloat(this, "translateY", 1, 0);
        disAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                //放大动画
                ObjectAnimator anim = ObjectAnimator//
                        .ofFloat(bg, "zcx", 0.9F, 1.0F)//
                        .setDuration(300);//
                anim.start();
                anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float cVal = (Float) animation.getAnimatedValue();
                        bg.setScaleX(cVal);
                        bg.setScaleY(cVal);
                    }
                });
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mask.setVisibility(View.INVISIBLE);
            }
        });

        transition.setAnimator(LayoutTransition.DISAPPEARING, disAnimator);
        //设置显示动画
        ObjectAnimator startAnimator = ObjectAnimator.ofFloat(this, "translateY", 0, 1);
        startAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                //缩小动画
                ObjectAnimator anim = ObjectAnimator//
                        .ofFloat(bg, "zcx", 1.0F, 0.9F)//
                        .setDuration(200);//
                anim.start();
                anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float cVal = (Float) animation.getAnimatedValue();
                        bg.setScaleX(cVal);
                        bg.setScaleY(cVal);
                    }
                });
            }
        });
        transition.setAnimator(LayoutTransition.APPEARING, startAnimator);
        //分别设置动画持续时间
        transition.setDuration(LayoutTransition.APPEARING, 200);
        transition.setDuration(LayoutTransition.DISAPPEARING, 700);
        addPopupParent.setLayoutTransition(transition);
    }

}
