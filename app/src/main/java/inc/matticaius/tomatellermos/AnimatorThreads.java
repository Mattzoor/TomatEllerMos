package inc.matticaius.tomatellermos;

import android.animation.ObjectAnimator;

final class AnimatorThreads {

    final Thread animator;

    public AnimatorThreads(final ObjectAnimator oa)
    {
        animator = new Thread(new Runnable() {
            @Override
            public void run() {
                oa.start();
            }
            // logic to make animation happen
        });

    }

    public void startAnimation()
    {
        animator.start();
    }

    public void awaitCompletion() throws InterruptedException
    {
        animator.join();
    }

}
