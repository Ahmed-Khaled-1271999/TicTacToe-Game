package com.example.xowithballs;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
//import android.widget.GridLayout;
import androidx.gridlayout.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    // 0=player1(red)  , 1=player2(blue) , why int not bool , enable multiple players
    //roles
    int activePlayer=0;
    //Game State
    final int boxEmpty= 2;
    boolean gameActive=true;
    //  final int boxFilled=3;
    //                 tag=0    tag=1    tag=2    tag=3     tag=4    tag=5    tag=6    tag=7    tag=8
    int [] gameState={boxEmpty,boxEmpty,boxEmpty,boxEmpty,boxEmpty,boxEmpty,boxEmpty,boxEmpty,boxEmpty};
    int [][] winingPositions = {{0,1,2}, {3,4,5}, {6,7,8},  {0,3,6},{1,4,7},{2,5,8},  {0,4,8}, {2,4,6}};

    public void dropIn (View view){
        ImageView certainImageView = (ImageView) view;

        //Global(not Quit) find Views
        TextView winnerDeclaration = (TextView) findViewById(R.id.winnerDeclarationTextView);
        Button playAgain= (Button) findViewById(R.id.againButton);
        int tappedImageView=Integer.parseInt(certainImageView.getTag().toString());

        if(gameState[tappedImageView]==boxEmpty && gameActive) {

            gameState[tappedImageView] = activePlayer; //why not final int box_filled , so this let as indicate to what the color fill the box too ,, awesome!
            certainImageView.setTranslationY(-1500); //out of the screen at first
            if (activePlayer == 0) {
                certainImageView.setImageResource(R.drawable.red);
                activePlayer = 1;
            } else {
                certainImageView.setImageResource(R.drawable.blue);
                activePlayer = 0;
            }
            certainImageView.animate().translationYBy(1500).rotation(360).setDuration(300);

            for (int[] winingPosition : winingPositions) {
                if (gameState[winingPosition[0]] == gameState[winingPosition[1]] && gameState[winingPosition[0]] == gameState[winingPosition[2]] && gameState[winingPosition[0]] != boxEmpty) {
                    //some one has won!
                    gameActive=false;
                    String someone = "";
                    if (activePlayer == 0) { //reverse here on player who win!
                        //blue has won!
                        someone = "Blue";
                    } else {
                        //red has won!
                        someone = "Red";
                    }
                    winnerDeclaration.setVisibility(View.VISIBLE);
                    winnerDeclaration.setText(someone + " has won !!!");
                    if(someone.equals("Red"))
                        winnerDeclaration.setTextColor(Color.RED); // -65536 Red from Android Docs
                    else
                        winnerDeclaration.setTextColor(Color.BLUE); // -65536 Red from Android Docs
                        playAgain.setVisibility(View.VISIBLE);
                }
                else {
                    // the case which both are winners or losers!
                    boolean bothLose=true;
                    for (int looper: gameState){
                        if(looper==boxEmpty)
                            bothLose=false;
                    }
                    if(bothLose){
                        winnerDeclaration.setVisibility(View.VISIBLE);
                        winnerDeclaration.setText("Smarter Guys, but you both loses!!!");
                        winnerDeclaration.setTextSize(10);
                        winnerDeclaration.setTextColor(Color.BLACK);
                        playAgain.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }

    public void again(View view){
        //RESET Layout
        TextView winnerDeclaration = (TextView) findViewById(R.id.winnerDeclarationTextView);
        GridLayout theBoardLayout =(GridLayout)findViewById(R.id.myGridLayout);
        Button againButton = (Button)findViewById(R.id.againButton);

        winnerDeclaration.setVisibility(View.INVISIBLE);
        for(int i=0; i<theBoardLayout.getChildCount(); i++){
            ImageView imageViewCounter = (ImageView) theBoardLayout.getChildAt(i);
            imageViewCounter.setImageDrawable(null);
        }
        againButton.setVisibility(View.INVISIBLE);

        //RESET Settings
        activePlayer=0;
        gameActive =true;
        for (int i=0; i<gameState.length; i++){
            gameState[i]=boxEmpty;//the short hand written only on creation!!
        }
    }
}
