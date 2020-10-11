package com.nayan.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView timerTextView;
    SeekBar timerSeekBar;

    Boolean counterIsActive = false;
    Button goButton;

    CountDownTimer countDownTimer;

    public void buttonClicked(View view){
        /**next what we have to do is when the timer starts and inbetween if we want to stop and get rid of the seekbar**/

        if (counterIsActive){ //if someone to start the timer

            timerTextView.setText("0:30");
            timerSeekBar.setProgress(30);
            timerSeekBar.setEnabled(true);

            //next is to stop the counter//
            countDownTimer.cancel();
            goButton.setText("go");
             // counter is active now we need to switch to false
            counterIsActive = false;

        }else {
            counterIsActive = true;
            timerSeekBar.setEnabled(false);
            goButton.setText("Stop!");

            //  Log.i("button presed","Nice!");

            /**next we have to create timer when user hits that button**/                                         //quick fix to run the code an back fast add more 100 sec//

             countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 100 , 10) {
                /**
                 * befor we put arbitary amount in reality we want to know how much time was aloted inorder to assighn the timeSeekBar
                 **/
                @Override
                public void onTick(long l) {
                    /**in onTick method we need to know about the seconds anmin which we have already implemented and inside this we can call updateTimer**/
                    //only kind of math we have to dear hear is we have to take this l ie; milisec  and tell all finished up and we have to divide by 1000 so it turn it to sec
                    updateTimer((int) l / 100);


                }

                @Override
                public void onFinish() {
                    //for seting alarm
                    MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.alarm);
                    mplayer.start();

                }
            }.start();
        }

    }

    public void updateTimer(int seccondsLeft){

        // 1st thing to figure out is minutes to sec
        int minutes = seccondsLeft/60;     //i is the seconds when number of second entered (supose 430) while div by 60 we will get 7.16 so since it is int it will round it to 7
        int seconds = seccondsLeft - (minutes * 60); //so inorder to get sec , if supose i is 122sec that means there will be 2mins , 2mins * 60 =120   122-120 =2 sec

        /**the way below issue is fixed is created a new string and assighning to if **/
        String secondString =Integer.toString(seconds);

        if (seconds <=9){
            secondString = "0" + secondString; //inorder to display 09 08 07 aafter reaching below 10
        }
        //next we have to update the textview
        timerTextView.setText(Integer.toString(minutes) + "  :  " +Integer.toString(seconds) ); //inside we gonna have minutes and seconds so inorder to convert we use Integer.toString

        /**while doing this we will run int to issue that is supose we type 120 ,120/60 =2 ,sec = 120 - (2*60) =0 it shows 2:0 it weird so we use if condition**/


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            timerSeekBar = findViewById(R.id.timerSeekBar);
            timerTextView = findViewById(R.id.countdownTextView);
            goButton = findViewById(R.id.goButton);

        // now we have acess both the seek bar And Textview

        //now we have to set initial values and progres to SeekBar

        timerSeekBar.setMax(600);  //10min
        timerSeekBar.setProgress(30);  // 30 sec

        //whenevre changes the seekBar we should run some code

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                updateTimer(i);

            }



            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}
