package com.example.gggab.rollthedice_laptop_v2;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class MainActivity extends AppCompatActivity {
    private ViewFlipper flip;
    private float initialX;

    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ctx = this;

        final Button startGame = (Button) findViewById(R.id.butStart);

        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText editText = (EditText) findViewById(R.id.nbtoursInput);
                int nbtour = Integer.parseInt(editText.getText().toString());
                if (nbtour > 10 || nbtour < 1) {
                    editText.setError("Choisir entre 1 et 10 tours");
                } else {
                    Game game = new Game((AppCompatActivity) ctx, nbtour);

//                    View view = (LayoutInflater.from(MainActivity.this)).inflate(R.layout.prompts, null);
//
//                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
//                    alertBuilder.setView(view);
//
//                    final EditText userInput = (EditText) view.findViewById(R.id.editTextDialogUserInput);
//
//                    alertBuilder.setCancelable(true)
//                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    userinputtext.setText(userInput.getText());
//                                }
//                            });
//                    Dialog dialog = alertBuilder.create();
//                    dialog.show();
                }
            }
        });
        ///// End Test


        flip = (ViewFlipper) findViewById(R.id.viewFlipper1);

        // Setting IN and OUT animation for view flipper
        flip.setInAnimation(this, R.anim.right_enter);
        flip.setOutAnimation(this, R.anim.left_out);

    }

    // Implementing touch event for view flipper
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                // Getting intitial by event action down
                initialX = event.getX();
                break;

            case MotionEvent.ACTION_UP:

                // On action up the flipper will start and showing next item
                float finalX = event.getX();
                System.out.println("initatX: " + initialX + "  FinalX" + finalX);
                if (initialX > finalX) {
                    System.out.println(flip.getDisplayedChild());
                    // Show items are 2
                    if (flip.getDisplayedChild() == 1)
                        break;

                    // Flip show next will show next item
                    flip.showNext();
                } else {
                    System.out.println(flip.getDisplayedChild());
                    // If flip has no items more then it will display previous item
                    if (flip.getDisplayedChild() == 0)
                        break;
                    flip.showPrevious();
                }
                break;
        }
        return false;
    }

//    public void StartGame(View view) {
//        EditText editText = (EditText) findViewById(R.id.nbtoursInput);
//        int nbtour = Integer.parseInt(editText.getText().toString());
//        if (nbtour > 10 || nbtour < 1) {
//            editText.setError("Choisir entre 1 et 10 tours");
//        }
//        else {
//            Toast.makeText(this, "La partie va commencer avec " + nbtour + " tours.", Toast.LENGTH_LONG).show();
//            editText.setEnabled(false);
//
//            Game game = new Game(nbtour);
//        }
//    }
}