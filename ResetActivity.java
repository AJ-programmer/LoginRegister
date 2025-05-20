package com.example.loginregister;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ResetActivity extends AppCompatActivity {

    EditText password, repassword;
    Button confirm, backLogin;
    DbHelper DB;
    TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        username = (TextView) findViewById(R.id.username_reset_text);
        password = (EditText) findViewById(R.id.password_reset);
        repassword = (EditText) findViewById(R.id.repassword_reset);
        confirm = (Button) findViewById(R.id.confirm_reset);
        backLogin = (Button) findViewById(R.id.backToLogin);
        DB = new DbHelper(this);

        Intent intent = getIntent();
        String usernameValue = intent.getStringExtra("username");
        if (usernameValue != null) {
            username.setText(usernameValue);
        } else {
            Toast.makeText(ResetActivity.this, "Error: Username not received", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();

                if (pass.equals("") || repass.equals("")) {
                    Toast.makeText(ResetActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pass.equals(repass)) {
                    Boolean checkpasswordupdate = DB.updatePassword(user, pass);

                    if (checkpasswordupdate) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(ResetActivity.this, "Password Updated Successfully", Toast.LENGTH_SHORT).show();
                        finish(); // Close this activity
                    } else {
                        Toast.makeText(ResetActivity.this, "Password Not Updated Successfully", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ResetActivity.this, "Passwords Do Not Match", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
