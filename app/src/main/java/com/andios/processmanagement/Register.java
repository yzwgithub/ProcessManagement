package com.andios.processmanagement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Register extends AppCompatActivity {
    private EditText register_username,register_password;
    private Button register_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        initView();
        register_username.setOnClickListener(new onClick());
    }
    private void initView(){
        register_username= (EditText) findViewById(R.id.register_enter_username);
        register_password= (EditText) findViewById(R.id.register_enter_password);
        register_button= (Button) findViewById(R.id.register_button);
    }
    class onClick implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.register_enter_username:
                case R.id.register_enter_password:
                case R.id.register_button:
            }
        }
    }
}
