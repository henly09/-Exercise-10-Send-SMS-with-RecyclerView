package com.hcdc.exercise10sendsmswithrecyclerview_montera;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class sendpage extends AppCompatActivity {

    EditText numbernotexistmain, messagemain;
    Button sendmain;
    RecyclerView rviewmain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendpage);

        numbernotexistmain = findViewById(R.id.numbernotexist);
        messagemain = findViewById(R.id.message);
        sendmain = findViewById(R.id.send);
        rviewmain = findViewById(R.id.rview);

        sendmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(sendpage.this, new String[]{Manifest.permission.SEND_SMS}, 2);
                }
                else {
                    sendMessage();
                }
            }
        });

        if (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(sendpage.this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
        }
        else {
            getNumbers();
        }

        Intent intent = getIntent();
        numbernotexistmain.setText(intent.getStringExtra("phonenumber"));


    }

    private void sendMessage() {
        String num = numbernotexistmain.getText().toString();
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(num, null, messagemain.getText().toString(), null, null);
        Toast.makeText(this, "Successfully Send the Message", Toast.LENGTH_SHORT).show();
    }

    private void getNumbers() {
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        String names[] = new String[cursor.getCount()];
        String emails[] = new String[cursor.getCount()];
        String phonenumber[] = new String[cursor.getCount()];
        while (cursor.moveToNext()) {
            int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            int emailIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS);
            int numIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            if (nameIndex >= 0 && numIndex >= 0 && emailIndex >= 0) {
                if (!cursor.getString(nameIndex).equals(null) && !cursor.getString(emailIndex).equals(null) && !cursor.getString(numIndex).equals(null)) {
                names[cursor.getPosition()] = cursor.getString(nameIndex);
                emails[cursor.getPosition()] = cursor.getString(emailIndex);
                phonenumber[cursor.getPosition()] = cursor.getString(numIndex);}
            }
            else{
                Toast.makeText(this, "Error Occured..", Toast.LENGTH_SHORT).show();
            }
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        rviewmain.setLayoutManager(layoutManager);
        rviewmain.setAdapter(new ContactAdapter(this, names,emails,phonenumber));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getNumbers();
        }
        else if (requestCode == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            sendMessage();
        }
    }


}