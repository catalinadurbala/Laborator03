package ro.pub.cs.systems.eim.lab03.phonedialer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class PhoneDialerActivity extends AppCompatActivity {

    private EditText phoneNumberEditText;
    private ImageButton callImageButton;
    private ImageButton hangupImageButton;
    private ImageButton backspaceImageButton;
    private Button genericButton;

    private int[] buttonIds = {
            R.id.generic_button_0,
            R.id.generic_button_1,
            R.id.generic_button_2,
            R.id.generic_button_3,
            R.id.generic_button_4,
            R.id.generic_button_5,
            R.id.generic_button_6,
            R.id.generic_button_7,
            R.id.generic_button_8,
            R.id.generic_button_9,
            R.id.generic_button_A,
            R.id.generic_button_N,
    };

    final public static int PERMISSION_REQUEST_CALL_PHONE = 1;

    private CallButtonClickListener callButtonClickListener = new CallButtonClickListener();
    private class CallButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (ContextCompat.checkSelfPermission(PhoneDialerActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        PhoneDialerActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        PERMISSION_REQUEST_CALL_PHONE);
            } else {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phoneNumberEditText.getText().toString()));
                startActivity(intent);
            }
        }
    }

    private HangupCallButtonClickListener hangupCallButtonClickListener = new HangupCallButtonClickListener();
    private class HangupCallButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            finish();
        }
    }

    private BackspaceButtonClickListener backspaceButtonClickListener = new BackspaceButtonClickListener();
    private class BackspaceButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            int length = phoneNumberEditText.getText().length();
            if (length > 0) {
                phoneNumberEditText.setText(phoneNumberEditText.getText().delete(length - 1, length));
            }
        }
    }

    private GenericButtonClickListener genericButtonClickListener = new GenericButtonClickListener();
    private class GenericButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Button button = (Button)view;
            phoneNumberEditText.setText(phoneNumberEditText.getText().append(button.getText()).toString());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_phone_dialer);

        phoneNumberEditText = findViewById(R.id.phone_number_edit_text);

        callImageButton = findViewById(R.id.call_button);
        callImageButton.setOnClickListener(callButtonClickListener);

        hangupImageButton = findViewById(R.id.hangup_call_button);
        hangupImageButton.setOnClickListener(hangupCallButtonClickListener);

        backspaceImageButton = findViewById(R.id.backspace_button);
        backspaceImageButton.setOnClickListener(backspaceButtonClickListener);

        for (int id : buttonIds) {
            genericButton = findViewById(id);
            genericButton.setOnClickListener(genericButtonClickListener);
        }
    }
}
