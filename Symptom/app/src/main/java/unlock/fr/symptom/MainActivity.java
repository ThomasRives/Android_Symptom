package unlock.fr.symptom;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    /* Linked to the play button */
    private ImageButton playButton;
    /* Linked to the code button */
    private ImageButton codeButton;
    /* Linked to the hint button */
    private ImageButton hintButton;
    /* Linked to the review hint button */
    private ImageButton reviewHintButton;
    /* Linked to the machine button */
    private ImageButton machineButton;
    /* Linked to the penalty button */
    private ImageButton penaltyButton;
    /* Linked to the object button */
    private ImageButton objectButton;
    /* Linked to the object button */
    private ImageButton closeButton;
    /* Time left in seconds*/
    private int timeLeft = 3600;
    /* Given time to finish the game */
    private int givenTime = 3600000;
    /* Count down */
    private Timer timer;
    /* readable version of time left */
    private TextView displayedTime;
    /* Penalty that will be given if the penalty button is given (in seconds)*/
    private int penaltyTime = 180;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playButton = (ImageButton) findViewById(R.id.playButton);
        codeButton = (ImageButton) findViewById(R.id.codeButton);
        hintButton = (ImageButton) findViewById(R.id.hintButton);
        reviewHintButton = (ImageButton) findViewById(R.id.reviewHintButton);
        machineButton = (ImageButton) findViewById(R.id.machineButton);
        penaltyButton = (ImageButton) findViewById(R.id.penaltyButton);
        objectButton = (ImageButton) findViewById(R.id.objectButton);
        closeButton = (ImageButton) findViewById(R.id.closeButton);
        displayedTime = (TextView) findViewById(R.id.textTime);
        timer = new Timer(givenTime, 1, displayedTime);
        addButtonListener();
    }

    private void addButtonListener() {
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timer.isPaused()) {
                    timer.resume();
                    Toast.makeText(MainActivity.this, "Resume", Toast.LENGTH_LONG).show();
                }
                else {
                    timer.pause();
                    Toast.makeText(MainActivity.this, "Pause", Toast.LENGTH_LONG).show();
                }
            }
        });
        penaltyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.removeTime(penaltyTime * 1000);
            }
        });
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.app_name);
                builder.setIcon(R.drawable.logo_symptom);
                builder.setMessage("Êtes-vous sûr de vouloir quitter l'application ?")
                        .setCancelable(false)
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }
}
