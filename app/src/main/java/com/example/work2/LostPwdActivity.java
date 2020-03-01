package com.example.work2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import model.Data;
import presenter.MyDatabase;

public class LostPwdActivity extends AppCompatActivity {
    EditText EnterOldpwd;
    EditText EnterNewpwd2;
    EditText EnterNewpwd;
    EditText Account3;
    View AccountDelete3;
    View pwdDelete1;
    View pwdDelete2;
    View pwdDelete3;
    Button button_confirm;
    Button button_cancle;
    Data data;
    MyDatabase myDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findpwd);
        Account3 = findViewById(R.id.account3);
        EnterOldpwd = findViewById(R.id.oldpwd);
        EnterNewpwd = findViewById(R.id.EnterNewPwd1);
        EnterNewpwd2 = findViewById(R.id.EnterNewPwd2);
        button_confirm = findViewById(R.id.button_confirm);
        button_cancle = findViewById(R.id.button_cancle2);
        AccountDelete3 = findViewById(R.id.accountDelete3);
        pwdDelete1 = findViewById(R.id.pwdDelete1);
        pwdDelete2 = findViewById(R.id.pwdDelete2);
        pwdDelete3 = findViewById(R.id.pwdDelete3);
        EditTextUtils.clearButtonListener(Account3,AccountDelete3);
        EditTextUtils.clearButtonListener(EnterOldpwd,pwdDelete1);
        EditTextUtils.clearButtonListener(EnterNewpwd,pwdDelete2);
        EditTextUtils.clearButtonListener(EnterNewpwd2,pwdDelete3);


        button_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LostPwdActivity.this,MainActivity.class);
                startActivity(intent);

            }
        });
        button_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String userid =  Account3.getText().toString();
               String Olduserpwd = EnterOldpwd.getText().toString();
               String Newuserpwd = EnterNewpwd.getText().toString();
               String Newuserpwd2 = EnterNewpwd2.getText().toString();

                if(Newuserpwd.equals(Newuserpwd2)){
                   myDatabase = new MyDatabase(getApplicationContext());
                   data = new Data(userid,Olduserpwd,Newuserpwd);
                   myDatabase.ModifyPwd(userid,Olduserpwd,Newuserpwd);
                   Intent intent = new Intent(LostPwdActivity.this,MainActivity.class);
                   startActivity(intent);
                   Toast.makeText(LostPwdActivity.this,"修改密码成功",Toast.LENGTH_SHORT).show();

               }else{
                   Toast.makeText(LostPwdActivity.this,"两次输入密码不符，请再次输入",Toast.LENGTH_SHORT).show();
               }

            }
        });

    }
}
