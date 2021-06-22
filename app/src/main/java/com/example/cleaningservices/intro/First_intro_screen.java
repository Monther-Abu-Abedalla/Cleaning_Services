package com.example.cleaningservices.intro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.cleaningservices.R;
import com.example.cleaningservices.login_signup.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static android.content.Context.MODE_PRIVATE;

public class First_intro_screen extends Fragment {
    FloatingActionButton btn_move;
    Button go_to_login_screen_btn;
    ProgressBar progressBar;
    TextView textView;
    int count = 0;
    ImageView photoInrp;

    ImageView pointOne;
    Drawable pointTwo;

    ImageView phot_2;
    ImageView phot_3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_first_intro_screen, container, false);
        btn_move = root.findViewById(R.id.btn_move);
        progressBar = root.findViewById(R.id.progressBar2);
        photoInrp = root.findViewById(R.id.photoInrp);

        pointOne = root.findViewById(R.id.imageView6);


        phot_2 = root.findViewById(R.id.imageView7);
        phot_3 = root.findViewById(R.id.imageView8);
        BitmapDrawable drawable = (BitmapDrawable) phot_2.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        BitmapDrawable drawable2 = (BitmapDrawable) phot_3.getDrawable();
        Bitmap bitmap2 = drawable2.getBitmap();


        go_to_login_screen_btn = root.findViewById(R.id.go_to_login_screen_btn);
        textView = root.findViewById(R.id.textView4);
        ProgressBarAnimation anim = new ProgressBarAnimation(progressBar, 0, 50);
        anim.setDuration(2500);
        progressBar.startAnimation(anim);

        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                if (count == 1) {
                    ProgressBarAnimation anim = new ProgressBarAnimation(progressBar, 45, 90);
                    anim.setDuration(1000);
                    progressBar.startAnimation(anim);
                    ImageViewAnimatedChange(getContext(), photoInrp, bitmap);
                    pointOne.setImageResource(R.drawable.ic_points2);

                    textView.setText("خدمات تنظيف للمكاتب");

                } else if (count == 2) {
                    ProgressBarAnimation anim = new ProgressBarAnimation(progressBar, 90, 100);
                    anim.setDuration(1000);
                    progressBar.startAnimation(anim);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                            btn_move.setVisibility(View.GONE);
                            go_to_login_screen_btn.setVisibility(View.VISIBLE);
                        }
                    }, 1200);
                    textView.setText("خدمات تنظيف للمنازل");
                    pointOne.setImageResource(R.drawable.ic_points3);
                    ImageViewAnimatedChange(getContext(), photoInrp, bitmap2);
                    go_to_login_screen_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SharedPreferences isFirstTimeOpened = requireActivity().getSharedPreferences("isFirstTimeOpened", MODE_PRIVATE);
                            SharedPreferences.Editor isFirstTimeOpenedEditor = isFirstTimeOpened.edit();
                            isFirstTimeOpenedEditor.putBoolean("isFirstTimeOpened", false);
                            isFirstTimeOpenedEditor.apply();
                            startActivity(new Intent(requireActivity(), LoginActivity.class));
                            requireActivity().finish();
                        }
                    });

                }


            }
        });


        return root;

    }


    public static class ProgressBarAnimation extends Animation {
        private final ProgressBar progressBar;
        private final float from;
        private final float to;

        public ProgressBarAnimation(ProgressBar progressBar, float from, float to) {
            super();
            this.progressBar = progressBar;
            this.from = from;
            this.to = to;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            float value = from + (to - from) * interpolatedTime;
            progressBar.setProgress((int) value);
        }
    }


    public static void ImageViewAnimatedChange(Context c, final ImageView v, final Bitmap new_image) {
        final Animation anim_out = AnimationUtils.loadAnimation(c, android.R.anim.fade_out);
        final Animation anim_in = AnimationUtils.loadAnimation(c, android.R.anim.fade_in);
        anim_out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                v.setImageBitmap(new_image);
                anim_in.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                    }
                });
                v.startAnimation(anim_in);
            }
        });
        v.startAnimation(anim_out);
    }

}