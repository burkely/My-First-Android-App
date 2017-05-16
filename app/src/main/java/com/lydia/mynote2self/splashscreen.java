package com.lydia.mynote2self;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Lydia on 14/05/2017.
 */
public class SplashScreen extends Activity {

    //starting new activity from onAttachedToWindow improves
    //response time if comparing to starting it from onCreate()
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    /** Called when the activity is first created. */
    Thread splashTread;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        StartAnimations();
    }

    private void StartAnimations() {
        /*Animation anim = AnimationUtils.loadAnimation(this, R.anim.fade_text_splash);
        anim.reset();
        LinearLayout l =(LinearLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);
        */

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.scale_logo_splash);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.splash);
        iv.clearAnimation();
        iv.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.fade_text_splash);


        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(SplashScreen.this,
                        AddNoteActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                SplashScreen.this.finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        anim.reset();
        TextView tv = (TextView) findViewById(R.id.made_by);
        tv.clearAnimation();
        tv.startAnimation(anim);



/*
        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 7500) {
                        sleep(100);
                        waited += 100;
                    }
                    Intent intent = new Intent(SplashScreen.this,
                            AddNoteActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    SplashScreen.this.finish();
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    SplashScreen.this.finish();
                }

            }
        };

        splashTread.start();

*/
    }
}
