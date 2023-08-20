package com.example.woodenfish2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";

    private Button strikeButton;
    private Button resetButton;
    private TextView numberTextView;

    private int number = 0;
    private LiveData<NumberEntity> liveDataNumber;
    private NumberDatabase numberDatabase;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        strikeButton = findViewById(R.id.StrikeButton);
        resetButton = findViewById(R.id.ResetButton);
        numberTextView = findViewById(R.id.numberTextView);

        numberDatabase = Room.databaseBuilder(getApplicationContext(), NumberDatabase.class, "number-db").build();
        liveDataNumber = numberDatabase.numberDao().getLastLiveDataNumber();
        mediaPlayer = MediaPlayer.create(this, R.raw.wooden_fish_sound_short);

        liveDataNumber.observe(this, new Observer<NumberEntity>() {
            @Override
            public void onChanged(NumberEntity numberEntity) {
                if(numberEntity == null){
                    initNumber();
                }else{
                    updateNumberTextView(numberEntity.number);
                }
            }
        });

        strikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                increaseNumberInDatabase();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetNumberInDatabase();
            }
        });
    }

    private void initNumber(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                NumberEntity numberEntity = new NumberEntity(0);
                numberDatabase.numberDao().insertNumber(numberEntity);
                updateNumberTextView(numberEntity.number);
            }
        }).start();
    }

    private void increaseNumberInDatabase(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                NumberEntity numberEntity = numberDatabase.numberDao().getLastNumberEntity();
                if(numberEntity != null){
                    numberEntity.number++;
                    numberDatabase.numberDao().updateNumber(numberEntity);
                }
            }
        }).start();
    }

    private void resetNumberInDatabase(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                NumberEntity numberEntity = numberDatabase.numberDao().getLastNumberEntity();
                if(numberEntity != null){
                    numberEntity.number = 0;
                    numberDatabase.numberDao().updateNumber(numberEntity);
                }
            }
        }).start();
    }

    private void updateNumberTextView(int number){
        numberTextView.setText(String.valueOf(number));
    }
}