package com.javen.widgets;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;


public class MainActivity extends FragmentActivity {
    boolean isAnimation = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAnimation)
                    return;
                final View view1 = findViewById(R.id.view);
                final ViewTreeObserver observer = view1.getViewTreeObserver();
                view1.requestLayout();
                observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        if (observer.isAlive())
                            observer.removeOnPreDrawListener(this);
                        ValueAnimator animator = ValueAnimator.ofInt(view1.getHeight(), view1.getHeight() - view1.getWidth() / 7 - 1);
                        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                Integer value = (Integer) valueAnimator.getAnimatedValue();
                                ViewGroup.LayoutParams lp = view1.getLayoutParams();
                                lp.height = value;
                                view1.requestLayout();
                            }
                        });
                        animator.setInterpolator(new AccelerateDecelerateInterpolator());
                        animator.setDuration(400);
                        animator.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animator) {
                                isAnimation = false;
                            }

                            @Override
                            public void onAnimationCancel(Animator animator) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animator) {

                            }
                        });
                        animator.start();
                        isAnimation = true;
                        return false;
                    }
                });
            }
        });
    }


}
