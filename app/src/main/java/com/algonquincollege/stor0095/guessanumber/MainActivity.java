package com.algonquincollege.stor0095.guessanumber;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

/**
 * Guess A Number. This app generates a random number. The user is then allowed to input a number to try and guess that number.
 * The user has 10 attempts before the game is over. The user is allowed to reset whenever they wish.
 * Author: Geemakun Storey
 */
public class MainActivity extends AppCompatActivity {
    private static final String ABOUT_DIALOG_TAG;
    private static final String LOG_TAG;

    TextView displayMessage;
    EditText userInput;
    Button guessButton;
    Button resetButton;
    private int Answer = 0;
    private final int min_range = 0;
    private final int max_range = 1000;
    private int countAttempts = 0;
    private final int Attempts = 10;

    static {
        ABOUT_DIALOG_TAG = "About Dialog";
        LOG_TAG = "LOGIN EXAMPLE";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayMessage = (TextView) findViewById(R.id.textViewDisplayMessage);
        userInput = (EditText) findViewById(R.id.inputNumberUser);
        guessButton = (Button) findViewById(R.id.guessButton);
        resetButton = (Button) findViewById(R.id.resetButton);

        generateRandomNumber();

        guessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String numberInput = userInput.getText().toString();
                // If the textfield is empty display an error
                if (numberInput.isEmpty()) {
                    userInput.setError("Input a number");
                    userInput.requestFocus();
                    return;
                } else {
                    // The logic for the game
                    int number = Integer.parseInt(numberInput);
                    countAttempts--;
                    userInput.setText("");
                    if (number == Answer && countAttempts > 5){
                        guessButton.setText("Winner!");
                        displayMessage.setText("Congratulations! \nYou got the number after " + (Attempts - countAttempts) + " attempts!");
                        Toast.makeText(MainActivity.this, "Superior Win!", Toast.LENGTH_SHORT).show();
                        guessButton.setEnabled(false);

                    }
                    if (number == Answer && countAttempts < 5) {
                        displayMessage.setText("Congratulations! \nYou got the number after " + (Attempts - countAttempts) + " attempts!");
                        Toast.makeText(MainActivity.this, "Excellent Win!", Toast.LENGTH_SHORT).show();
                        guessButton.setEnabled(false);
                    }
                    else if (number > max_range) {
                        userInput.setError("Input a number lower than 1000.");
                        userInput.requestFocus();
                    }
                    else if (number < Answer) {
                        // High
                        Toast.makeText(MainActivity.this, "Guess a higher number", Toast.LENGTH_SHORT).show();
                        displayMessage.setText(countAttempts + " attempts left");
                    } else if (number > Answer) {
                        // Low
                        Toast.makeText(MainActivity.this, "Guess a lower number", Toast.LENGTH_SHORT).show();
                        displayMessage.setText(countAttempts + " attempts left");
                    }
                    if (countAttempts < 1) {
                        guessButton.setEnabled(false);
                        guessButton.setText("Game Over");
                        Toast.makeText(MainActivity.this, "Please reset!", Toast.LENGTH_SHORT).show();
                        displayMessage.setText("Game Over!\nThe answer was " + Answer);
                    }

                }
            }
        });
        // On long click display a toast message that shows the answer
        resetButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(MainActivity.this, "The answer is " + Answer, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        // Reset the game when the user taps the buttons
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInput.setText("");
                generateRandomNumber();
                guessButton.setEnabled(true);
                guessButton.setText("Guess");
                displayMessage.setText("Guess A Number!");
            }
        });

    }
    // Generate Random Number
    public void generateRandomNumber() {
        countAttempts = 10;
        Random randomGenerator = new Random();
        Answer = randomGenerator.nextInt(1000) + 1;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_about) {
            DialogFragment newFragment = new AvoutDialogFragment();
            newFragment.show(getFragmentManager(), ABOUT_DIALOG_TAG);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
