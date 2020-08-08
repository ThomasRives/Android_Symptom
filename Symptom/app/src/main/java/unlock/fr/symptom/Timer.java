package unlock.fr.symptom;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.TextView;
// 99:99 = 6039
public class Timer {
    /* The view that will be updated */
    private TextView displayedTime;
    /* Indicate if the timer is paused */
    private boolean paused = true;
    /* The speed of the timer */
    private int speedTimer;
    /* The time left */
    private long timeLeft;
    /* The true countdown */
    private CountDownTimer countdownTimer = null;
    /* Handler to put text in white 1 sec after error */
    private Handler timeInWhite;

    /**
     * Constructor.
     *
     * @param givenTime: the time given to complete the game.
     * @param speedTimer: the speed of timer.
     * @param timeView: the view that will be modified.
     */
    public Timer(long givenTime, int speedTimer, TextView timeView) {
        this.displayedTime = timeView;
        this.timeLeft = givenTime;
        this.speedTimer = speedTimer;
        displayedTime.setText(displayableTime());
    }

    /**
     * Convert the seconds left in a string that will be displayed
     *
     * @return the displayable time.
     */
    private String displayableTime() {
        long timeLeftSeconds = timeLeft / 1000;
        long minutesLeft = timeLeftSeconds / 60;
        String minutes = (minutesLeft < 10) ? "0" + minutesLeft : "" + minutesLeft;
        long secondsLeft = (timeLeftSeconds % 60);
        String seconds = (secondsLeft < 10) ? "0" + secondsLeft : "" + secondsLeft;
        return minutes +":" + seconds;
    }

    /**
     * Create a new countdown.
     */
    private void createNewCountDown() {
        countdownTimer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                displayedTime.setText(displayableTime());
            }

            @Override
            public void onFinish() {
                displayedTime.setTextColor(Color.RED);
                displayedTime.setText("00:00");
            }
        };
        countdownTimer.start();
    }

    /**
     * Pause the timer.
     */
    public void pause() {
        if(paused)
            return;
        paused = true;
        countdownTimer.cancel();
    }

    /**
     * Resume the timer.
     */
    public void resume() {
        if(!paused)
            return;
        paused = false;
        createNewCountDown();
    }

    /**
     * Give more time to the players.
     *
     * @param additionalTime: the time given (in seconds).
     */
    public void addTime(long additionalTime) {
        countdownTimer.cancel();
        timeLeft += additionalTime;
        createNewCountDown();
    }

    /**
     * Withdraw time to the players. The time is displayed in red after a mistake.
     *
     * @param penalty: the time withdrawed (in seconds).
     */
    public void removeTime(long penalty) {
        countdownTimer.cancel();
        timeLeft -= penalty;
        displayedTime.setTextColor(Color.RED);
        timeInWhite = new Handler();

        timeInWhite.postDelayed(new Runnable() {
            @Override
            public void run() {
                displayedTime.setTextColor(Color.WHITE);
            }
        }, 1000);

        createNewCountDown();
    }

    /**
     * Getter for paused.
     *
     * @return a boolean that indicate if the timer is paused.
     */
    public boolean isPaused() {
        return paused;
    }
}
