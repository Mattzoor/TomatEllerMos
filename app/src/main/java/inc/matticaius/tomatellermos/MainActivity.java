package inc.matticaius.tomatellermos;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements AccelerometerListener  {

    public static final Random RANDOM = new Random();
    private ImageView coin;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coin = findViewById(R.id.coin);
        coin.setImageResource(R.drawable.tomat);
        coin.setTag(R.drawable.tomat);
        //btn = findViewById(R.id.btn);
        //btn.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //        //flipCoin();
        //    }
        //});
        coin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //flipAnimation();
                flipCoin2();
            }
        });
    }


    private void flipCoin2(){
        flipAnimation();
    }

    private void flipAnimation(){

        final int rand = RANDOM.nextInt(5);
        final ObjectAnimator oa1 = ObjectAnimator.ofFloat(coin, "scaleX", 1f, 0f);
        final ObjectAnimator oa2 = ObjectAnimator.ofFloat(coin, "scaleX", 0f, 1f);
        oa1.setInterpolator(new DecelerateInterpolator());
        oa2.setInterpolator(new AccelerateDecelerateInterpolator());
        oa1.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                int draw = (int)coin.getTag();
                switch (draw){
                    case R.drawable.tomat:
                        coin.setImageResource(R.drawable.mos);
                        coin.setTag(R.drawable.mos);
                        break;
                    case R.drawable.mos:
                        coin.setImageResource(R.drawable.tomat);
                        coin.setTag(R.drawable.tomat);
                        break;
                    default:
                        coin.setImageResource(R.drawable.error);
                        coin.setTag(R.drawable.error);
                        break;
                }
                oa2.start();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                int draw = (int)coin.getTag();
                switch (draw){
                    case R.drawable.tomat:
                        coin.setImageResource(R.drawable.mos);
                        coin.setTag(R.drawable.mos);
                        break;
                    case R.drawable.mos:
                        coin.setImageResource(R.drawable.tomat);
                        coin.setTag(R.drawable.tomat);
                        break;
                    default:
                        coin.setImageResource(R.drawable.error);
                        coin.setTag(R.drawable.error);
                        break;
                }
                oa2.start();
            }
        });
        oa1.setRepeatCount(rand);
        oa2.setDuration(500);
        oa1.start();
    }

    private void flipAnimation2(){
        final ObjectAnimator oa1 = ObjectAnimator.ofFloat(coin, "scaleX", 1f, 0f);
        final ObjectAnimator oa2 = ObjectAnimator.ofFloat(coin, "scaleX", 0f, 1f);
        oa2.setDuration(500);
        oa1.setDuration(500);
        oa1.setInterpolator(new DecelerateInterpolator());
        oa2.setInterpolator(new AccelerateDecelerateInterpolator());
        oa1.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                int draw = (int)coin.getTag();
                switch (draw){
                    case R.drawable.tomat:
                        coin.setImageResource(R.drawable.mos);
                        coin.setTag(R.drawable.mos);
                        break;
                    case R.drawable.mos:
                        coin.setImageResource(R.drawable.tomat);
                        coin.setTag(R.drawable.tomat);
                        break;
                    default:
                        coin.setImageResource(R.drawable.error);
                        coin.setTag(R.drawable.error);
                        break;
                }
                oa2.start();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //int draw = (int)coin.getTag();
                //switch (draw){
                //    case R.drawable.tomat:
                //        coin.setImageResource(R.drawable.mos);
                //        coin.setTag(R.drawable.mos);
                //        break;
                //    case R.drawable.mos:
                //        coin.setImageResource(R.drawable.tomat);
                //        coin.setTag(R.drawable.tomat);
                //        break;
                //    default:
                //        coin.setImageResource(R.drawable.error);
                //        coin.setTag(R.drawable.error);
                //        break;
                //}
                //oa2.start();
            }
        });
        oa1.setRepeatCount(2);
        //oa1.setRepeatMode(oa1.RESTART);
        oa1.start();
    }

    private void flipCoin() {
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setDuration(1000);
        fadeOut.setFillAfter(true);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                coin.setImageResource(RANDOM.nextFloat() > 0.5f ? R.drawable.tomat : R.drawable.mos);

                Animation fadeIn = new AlphaAnimation(0, 1);
                fadeIn.setInterpolator(new DecelerateInterpolator());
                fadeIn.setDuration(3000);
                fadeIn.setFillAfter(true);

                coin.startAnimation(fadeIn);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        coin.startAnimation(fadeOut);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AccelerometerManager.isSupported(this)) {
            AccelerometerManager.startListening(this);
        }
    }

    @Override
    public void onAccelerationChanged(float x, float y, float z) {

    }

    @Override
    public void onShake(float force) {
        flipCoin2();
    }

    @Override
    public void onStop() {
        super.onStop();

        //Check device supported Accelerometer senssor or not
        if (AccelerometerManager.isListening()) {

            //Start Accelerometer Listening
            AccelerometerManager.stopListening();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (AccelerometerManager.isListening()) {
            AccelerometerManager.stopListening();
        }
    }
}
