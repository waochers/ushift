package com.example.android.ushift;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class login extends AppCompatActivity {
    EditText usernameET, passwordET;
    RadioButton customerRB, packerRB;
    Button loginBT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameET= (EditText) findViewById(R.id.username);
        passwordET= (EditText) findViewById(R.id.password);
        customerRB= (RadioButton) findViewById(R.id.customer);
        packerRB= (RadioButton) findViewById(R.id.packer);
        loginBT=(Button)findViewById(R.id.loginBt);
        loginBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin(v);
            }
        });
    }

    public void doLogin(View view) {
        String username = usernameET.getText().toString();
        String password = passwordET.getText().toString();
        boolean checkedCust = customerRB.isChecked();
        boolean checkedPack = packerRB.isChecked();
        String type = null;
        if (checkedCust) {
            type = "c";
        } else if (checkedPack) {
            type = "p";
        }
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type, username, password);
    }

}
