package inc.matticaius.tomatellermos;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.graphics.drawable.Drawable;
import android.os.Handler;
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
import android.view.animation.LinearInterpolator;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements AccelerometerListener  {
    private static final String COIN_SIDE = "coin side";
    private static final int ANIM_DUR = 250;
    private static final int TOMAT = R.drawable.tomat_small;
    private static final int MOS = R.drawable.mos_small;
    private static boolean waitforit = false;

    private ImageView coinImage;

    private Random r;
    private int coinSide;
    private int curSide = TOMAT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        r = new Random();
        coinImage = findViewById(R.id.coin);

        // Restore all values and images after rotate

        if (savedInstanceState != null) {
            coinImage.setImageResource(Integer.parseInt(savedInstanceState.getCharSequence(COIN_SIDE).toString()));
        }

        coinImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flipCoin(coinImage);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence(COIN_SIDE, String.valueOf(curSide));
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    private long animateCoin(boolean stayTheSame) {

        Rotate3dAnimation animation;

        if (curSide == TOMAT) {
            animation = new Rotate3dAnimation(coinImage, TOMAT, MOS, 0, 180, 0, 0, 0, 0);
        } else {
            animation = new Rotate3dAnimation(coinImage, MOS, TOMAT, 0, 180, 0, 0, 0, 0);
        }
        if (stayTheSame) {
            animation.setRepeatCount(3); // must be odd (5+1 = 6 flips so the side will stay the same)
        } else {
            animation.setRepeatCount(4); // must be even (6+1 = 7 flips so the side will not stay the same)
        }

        animation.setDuration(ANIM_DUR);
        animation.setInterpolator(new LinearInterpolator());

        coinImage.startAnimation(animation);
        waitforit = true;

        return animation.getDuration() * (animation.getRepeatCount() + 1);
    }

    public void flipCoin(View v) {
        if (!waitforit){
            coinSide = r.nextInt(2);

            if (coinSide == 0) {  // We have Tails
                boolean stayTheSame = (curSide == MOS);
                long timeOfAnimation = animateCoin(stayTheSame);
                curSide = MOS;

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        waitforit = false;
                    }
                }, timeOfAnimation + 100);
            } else {  // We have Heads
                boolean stayTheSame = (curSide == TOMAT);
                long timeOfAnimation = animateCoin(stayTheSame);
                curSide = TOMAT;

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        waitforit = false;
                    }
                }, timeOfAnimation + 100);
            }
        }
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
        flipCoin(coinImage);
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
