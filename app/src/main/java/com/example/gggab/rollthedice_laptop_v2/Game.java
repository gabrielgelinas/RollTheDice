package com.example.gggab.rollthedice_laptop_v2;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import java.util.ArrayList;

/**
 * Created by gggab on 2018-01-10.
 */

public class Game extends MainActivity {
    private int tours;
    private int currentRoll = 0;
    private ArrayList<Player> players;
    private TextView TextViewsPlayers[];
    private Button rollDice;
    private Boolean player1Play = true;

    AppCompatActivity main;

    //Default constructor
    public Game() {
    }

    //Main constructor
    public Game(AppCompatActivity p_main, int nbtour) {
        tours = nbtour;
        players = new ArrayList<>(2);
        main = p_main;

        TextViewsPlayers = new TextView[2];
        TextViewsPlayers[0] = (TextView) main.findViewById(R.id.nameplayer1);
        TextViewsPlayers[1] = (TextView) main.findViewById(R.id.nameplayer2);

        rollDice = (Button) main.findViewById(R.id.rolldice);

        rollDice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RollTheDice();
            }
        });

        //Create player 1 and 2
        playernameInput();
//        for (int i = 0; i < 2; i++) {
//            playernameInput(2 - 1);
//            // For Testing Purpose  --> //
////            players.add(new Player("Joueur" + i));
//            // <-- For Testing Purpose //
//        }
        // For Testing Purpose  --> //
//        rollDice.setVisibility(View.VISIBLE);
//        ((ImageView) main.findViewById(R.id.imgDice)).setVisibility(View.VISIBLE);
        // <-- For Testing Purpose //
    }


//    private void playernameInput(int player_number) {
//        final String[] name = {null};
//
//        View view = (LayoutInflater.from(main)).inflate(R.layout.prompts, null);
//
//        TextView pop_text = (TextView) view.findViewById(R.id.prompt_text);
//
////        String prompt = "Nom du joueur " + player_number;
//        String prompt = "Nom du joueur " + (players.size() + 1);
//        pop_text.setText(prompt);
//
//        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(main);
//        alertBuilder.setView(view);
//
//        final EditText userInput = (EditText) view.findViewById(R.id.editTextDialogUserInput);
//
//        alertBuilder.setCancelable(true)
//                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
//                    @Override
//                    public synchronized void onClick(DialogInterface dialogInterface, int i) {
//                        name[0] = String.valueOf(userInput.getText());
//                        players.add(new Player(name[0]));
//                        checkPlayers();
//                    }
//                });
//        Dialog dialog = alertBuilder.create();
//        dialog.show();
//    }

    private void playernameInput() {
        final String[] name = {null};

        View view = (LayoutInflater.from(main)).inflate(R.layout.prompts, null);

        TextView pop_text = (TextView) view.findViewById(R.id.prompt_text);

//        String prompt = "Nom du joueur " + player_number;
        String prompt = "Nom du joueur " + (players.size() + 1);
        pop_text.setText(prompt);

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(main);
        alertBuilder.setView(view);

        final EditText userInput = (EditText) view.findViewById(R.id.editTextDialogUserInput);

        alertBuilder.setCancelable(true)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public synchronized void onClick(DialogInterface dialogInterface, int i) {
                        name[0] = String.valueOf(userInput.getText());
                        players.add(new Player(name[0]));
                        if (players.size() < 2) {
                            playernameInput();
                        }
                        checkPlayers();
                    }
                });
        Dialog dialog = alertBuilder.create();
        dialog.show();
    }

    private void checkPlayers() {
        for (int i = 0; i < players.size(); i++) {
//            System.out.println("Player " + i + " :" + players.get(i).getName());
            TextViewsPlayers[i].setText(players.get(i).getName());
            if (players.size() == 2) {
                EditText editText = (EditText) main.findViewById(R.id.nbtoursInput);
                Button startGame = (Button) main.findViewById(R.id.butStart);
                editText.setEnabled(false);
                startGame.setEnabled(false);
                startGame.setText("Partie en Cours");

                Toast.makeText(main, "La partie va commencer avec " + tours + " tours.", Toast.LENGTH_LONG).show();


                rollDice.setVisibility(View.VISIBLE);
                rollDice.setEnabled(true);
                rollDice.setText("Lancer le Dé");
                ((ImageView) main.findViewById(R.id.imgDice)).setVisibility(View.VISIBLE);

                TextView turn_player = (TextView) main.findViewById(R.id.txt_turn);
                turn_player.setVisibility(View.VISIBLE);
                String msg = "À " + players.get(0).getName() + " de lancer le dé.";
                turn_player.setText(msg);

                for (int j = 0; j < 10; j++) {
                    TextView currentTextView_p1 = (TextView) main.findViewById(R.id.p1_roll_01 + j);
                    TextView currentTextView_p2 = (TextView) main.findViewById(R.id.p2_roll_01 + j);

                    currentTextView_p1.setText("");
                    currentTextView_p2.setText("");
                }
            }
        }
//        if (players.size() == 0) {
//            playernameInput();
//        }
    }

    public void RollTheDice() {
        ViewFlipper flip = (ViewFlipper) main.findViewById(R.id.viewFlipper1);
        TextView turn_player = (TextView) main.findViewById(R.id.txt_turn);

        int roll;
        if (tours > currentRoll) {
            if (player1Play) {
                roll = players.get(0).newRoll();
                Toast.makeText(main.getBaseContext(), players.get(0).getName() + " a roulé un " + roll, Toast.LENGTH_SHORT).show();
                String rollInfo = "Lancé: " + (currentRoll + 1) + "   Roll: " + roll + "   Total: " + players.get(0).getTotal();

                TextView currentTextView = (TextView) main.findViewById(R.id.p1_roll_01 + currentRoll);
//                String newText = (currentTextView).getText().toString() + rollInfo;
                currentTextView.setText(rollInfo);
                currentTextView.setVisibility(View.VISIBLE);
                player1Play = false;

                diceAnimation(roll);
                String msg = "À " + players.get(1).getName() + " de lancer le dé.";
                turn_player.setText(msg);
                flip.setDisplayedChild(0);
            } else {
                roll = players.get(1).newRoll();
                Toast.makeText(main.getBaseContext(), players.get(1).getName() + " a roulé un " + roll, Toast.LENGTH_SHORT).show();
                String rollInfo = "Lancé: " + (currentRoll + 1) + "   Roll: " + roll + "   Total: " + players.get(1).getTotal();


                TextView currentTextView = (TextView) main.findViewById(R.id.p2_roll_01 + currentRoll);
                String newText = (currentTextView).getText().toString() + rollInfo;
                currentTextView.setText(newText);
                currentTextView.setVisibility(View.VISIBLE);
                player1Play = true;
                currentRoll++;

                diceAnimation(roll);

                String msg = "À " + players.get(0).getName() + " de lancer le dé.";
                turn_player.setText(msg);

                flip.setDisplayedChild(1);
            }
        }
        synchronized (players.get(1)) {
            if (players.get(1).rolls == tours) {
                Button but_roll = (Button) main.findViewById(R.id.rolldice);
                but_roll.setEnabled(false);
                but_roll.setText("Partie Terminée");
                String winner = "";

                TextView p1 = (TextView) main.findViewById(R.id.nameplayer1);
                TextView p2 = (TextView) main.findViewById(R.id.nameplayer2);

                if (players.get(0).getTotal() > players.get(1).getTotal()) {
                    winner = "Le gagnant est " + players.get(0).getName();
                    p1.setTypeface(null, Typeface.BOLD);
                    flip.setDisplayedChild(0);
                } else {
                    winner = "Le gagnant est " + players.get(1).getName();
                    p2.setTypeface(null, Typeface.BOLD);
                    flip.setDisplayedChild(1);
                }

                if (players.get(0).getTotal() == players.get(1).getTotal()) {
                    winner = "Aucun gagnant... Égalité!";

                    p1.setTypeface(null, Typeface.NORMAL);
                    p2.setTypeface(null, Typeface.NORMAL);
                }
                turn_player.setText(winner);

                GameReset();
            }
        }
//        View popupView = (LayoutInflater.from(main)).inflate(R.layout.showdice, null);
//
//        final PopupWindow popupWindow = new PopupWindow(
//                popupView,
//                LinearLayout.LayoutParams.WRAP_CONTENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT);
//        Button btnDismiss = (Button) popupView.findViewById(R.id.dismiss);
//        btnDismiss.setOnClickListener(new Button.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                popupWindow.dismiss();
//            }
//        });
    }

    public void diceAnimation(int numb) {
        final TypedArray imgs_dice;
        imgs_dice = main.getResources().obtainTypedArray(R.array.images_dice);

        final ImageView dice = (ImageView) main.findViewById(R.id.imgDice);

//        ObjectAnimator rotate_anim = (ObjectAnimator) AnimatorInflater.loadAnimator(main, R.ani);
//        AnimationUtils.loadAnimation(main,R.anim.rotate_dice);


        //////////////////////////////////////////

//        Matrix matrix = new Matrix();
//        dice.setScaleType(ImageView.ScaleType.MATRIX);   //required
//        matrix.postRotate( 180f, dice.getDrawable().getBounds().width()/2, dice.getDrawable().getBounds().height()/2);
//        dice.setImageMatrix(matrix);

        //////////////////////////////////////////

//        Animation animation = AnimationUtils.loadAnimation(main, R.anim.rotate_dice);
//
//        animation.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                dice.setImageResource(R.drawable.roll_04);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
//
//        //////////////////////////////////////////
//
//        dice.startAnimation(animation);
//
//        ValueAnimator xmlAnimator = (ValueAnimator) AnimatorInflater.loadAnimator(this,
//                R.animator.animator);
//        xmlAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator updatedAnimation) {
//                float animatedValue = (float) updatedAnimation.getAnimatedValue();
//                textView.setTranslationX(animatedValue);
//            }
//        });
//
//        xmlAnimator.start();
//

//        Animation rotateDice = new RotateAnimation(60f * i_int, 60f * (i_int + 1f),Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
//
//        AnimatorSet bouncer = new AnimatorSet();
//        bouncer.play(rotateDice).before(squashAnim1);
//        bouncer.play(squashAnim1).with(squashAnim2);
//        bouncer.play(squashAnim1).with(stretchAnim1);
//        bouncer.play(squashAnim1).with(stretchAnim2);
//        bouncer.play(bounceBackAnim).after(stretchAnim2);
//        ValueAnimator fadeAnim = ObjectAnimator.ofFloat(newBall, "alpha", 1f, 0f);
//        fadeAnim.setDuration(250);
//        AnimatorSet animatorSet = new AnimatorSet();
//        animatorSet.play(bouncer).before(fadeAnim);
//        animatorSet.start();
//
//
//////////////////////////////////////////////
//
//        rotate_anim.setDuration(1500);
//        rotate_anim.setAnimationListener(new Animation.AnimationListener() {
//
//            @Override
//            public void onAnimationStart(Animation animation) {
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                viewToAnimate.setVisibility(View.VISIBLE);
//            }
//        });
//
//
//        Animation fadeout = new AlphaAnimation(1, 0);
//        fadeout.setDuration(1500);
//        fadeout.setAnimationListener(new Animation.AnimationListener() {
//
//            @Override
//            public void onAnimationStart(Animation animation) {
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                viewToAnimate.setVisibility(View.VISIBLE);
//            }
//        });

//        AnimatorSet set = new AnimatorSet();
//        ObjectAnimator in = ObjectAnimator.ofFloat(dice, "rotation", 0f, 360f);
//        in.setDuration(2500);
//        in.setInterpolator(new AccelerateInterpolator());
//        ObjectAnimator out = ObjectAnimator.ofFloat(dice, "alpha", 1f, 0f);
//        out.setDuration(2500);
//        out.setStartDelay(1000);
//        out.setInterpolator(new AccelerateInterpolator());
//
//        set.playSequentially(in, out);


//        for (int i = 0; i < 6; i++) {
//            final int i_int = i;
//            synchronized (dice) {
//                runOnUiThread(new Runnable() {
//                    public void run() {
//                        Animation rotateDice = new RotateAnimation(60f * i_int, 60f * (i_int + 1f),Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
//                        rotateDice.setDuration(500);
//                        rotateDice.setAnimationListener(new Animation.AnimationListener() {
//
//                            @Override
//                            public void onAnimationStart(Animation animation) {
//                            }
//
//                            @Override
//                            public void onAnimationRepeat(Animation animation) {
//                            }
//
//                            @Override
//                            public void onAnimationEnd(Animation animation) {
////                    viewToAnimate.setVisibility(View.VISIBLE);
//                            }
//                        });
//                        dice.startAnimation(rotateDice);
//
//                        dice.setImageResource(imgs_dice.getResourceId(i_int, -1));
//
//                    }
//                });
//            }
//        }


        dice.setImageResource(imgs_dice.getResourceId(numb - 1, -1));
//        imgs_dice.recycle();
//
//        dice.startAnimation(fadeout);

        //////////////////////////////////////

//        dice.setImageResource(R.drawable.roll_01);
//        dice.animate().rotation(60f).setDuration(500);
//        dice.setImageResource(R.drawable.roll_02);
//        dice.animate().rotation(60f).setDuration(500);
//        dice.setImageResource(R.drawable.roll_03);
//        dice.animate().rotation(60f).setDuration(500);
//        dice.setImageResource(R.drawable.roll_04);
//        dice.animate().rotation(60f).setDuration(500);
//        dice.setImageResource(R.drawable.roll_05);
//        dice.animate().rotation(60f).setDuration(500);
//        dice.setImageResource(R.drawable.roll_06);
//        dice.animate().rotation(60f).setDuration(500);
//
//        switch (numb){
//            case 1:
//                dice.setImageResource(R.drawable.roll_01);
//                break;
//            case 2:
//                dice.setImageResource(R.drawable.roll_02);
//                break;
//            case 3:
//                dice.setImageResource(R.drawable.roll_03);
//                break;
//            case 4:
//                dice.setImageResource(R.drawable.roll_04);
//                break;
//            case 5:
//                dice.setImageResource(R.drawable.roll_05);
//                break;
//            case 6:
//                dice.setImageResource(R.drawable.roll_06);
//                break;
//            default:
//                break;
//        }

        imgs_dice.recycle();
    }


    public void GameReset() {
        Button startGame = (Button) main.findViewById(R.id.butStart);
        EditText editText = (EditText) main.findViewById(R.id.nbtoursInput);

        editText.setEnabled(true);
        startGame.setEnabled(true);
        startGame.setText("Nouvelle Partie");

    }
}
