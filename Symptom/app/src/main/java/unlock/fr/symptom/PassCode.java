package unlock.fr.symptom;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PassCode extends AppCompatActivity {
    /* To display the code entered by the user */
    private TextView displayCodeZone = null;
    /* the exit Button */
    private ImageButton exitButton;
    /* The code entered by the user */
    private String enteredCode = "";
    /* Valid code list */
    private String validCodes[] = {"1234", "4321"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pass_code);

        displayCodeZone = (TextView) findViewById(R.id.displayedCode);
        exitButton = (ImageButton) findViewById(R.id.bExit);

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void numberPressed(View v) {
        Button b = (Button) v;
        if(enteredCode.length() < 4)
            enteredCode += b.getText().toString();
        displayCodeZone.setText(enteredCode);
    }

    public void clear(View v) {
        int codeLength = enteredCode.length();
        if(codeLength == 0)
            return;

        enteredCode = enteredCode.substring(0, codeLength - 1);
        displayCodeZone.setText(enteredCode);
    }

    public void valid(View v) {
        boolean valid = false;
        for(int i = 0; i < validCodes.length; i++)
            if(enteredCode.equals(validCodes[i])) {
                valid = true;
                break;
            }

        if(valid) {
            Toast.makeText(PassCode.this, "Code correct", Toast.LENGTH_LONG).show();
            finish();
        }
        else {
            Toast.makeText(PassCode.this, "Code incorrect", Toast.LENGTH_LONG).show();
            enteredCode = "";
            displayCodeZone.setText(enteredCode);
            MainActivity.getTimer().removeTime(180000);
        }
    }
}
