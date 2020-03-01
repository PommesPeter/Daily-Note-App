package com.example.work2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import presenter.DBHelper;

public class RegisterActivity extends AppCompatActivity {

    static EditText ed_account;
    EditText ed_pwd;
    EditText ed_pwd2;
    View AccountDelete;
    View pwdDelete;
    View pwdDelete2;
    DBHelper dbHelper;
    int flag =1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);
        Button button_sub = findViewById(R.id.button_sub);
        Button button_cancle = findViewById(R.id.button_cancle);
        ed_account = findViewById(R.id.regAccount);
        ed_pwd = findViewById(R.id.EnterPwd1);
        ed_pwd2 = findViewById(R.id.EnterPwd2);
        AccountDelete = findViewById(R.id.accountDelete);
        pwdDelete = findViewById(R.id.pwdDeleter);
        pwdDelete2 = findViewById(R.id.pwdDelete2r);
        EditTextUtils.clearButtonListener(ed_account,AccountDelete);
        EditTextUtils.clearButtonListener(ed_pwd,pwdDelete);
        EditTextUtils.clearButtonListener(ed_pwd2,pwdDelete2);
        button_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUser();

//                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
//                startActivity(intent);
            }
        });
        button_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //注册用户
    private void setUser() {

        dbHelper = new DBHelper(RegisterActivity.this, "LoginInfo", null, 5);

        if (ed_account.getText().toString().length() <= 0 && ed_pwd.getText().toString().length() <= 0 && ed_pwd2.getText().toString().length() <= 0) {
            Toast.makeText(this, "用户名密码不能为空", Toast.LENGTH_SHORT).show();
            flag=0;

        } else if (ed_account.getText().toString().length() < 6 || ed_account.getText().toString().length() > 20) {
            if(ed_pwd.getText().toString().length() < 6 || ed_pwd.getText().toString().length() > 20){
                if(ed_pwd2.getText().toString().length() < 6 || ed_pwd2.getText().toString().length() > 20){

                    flag=0;
                    Toast.makeText(this, "请输入至少6位且不大于20位的用户或密码", Toast.LENGTH_SHORT).show();

                }
                flag=0;
                Toast.makeText(this, "请输入至少6位且不大于20位的用户或密码", Toast.LENGTH_SHORT).show();
            }

            flag=0;
            Toast.makeText(this, "请输入至少6位且不大于20位的用户或密码", Toast.LENGTH_SHORT).show();

        } else if (ed_account.getText().toString().length() > 0) {

            String sql = "select * from user where userid=?";
            Cursor cursor = dbHelper.getWritableDatabase().rawQuery(sql, new String[]{ed_account.getText().toString()});
            if (cursor.moveToFirst()) {

                Toast.makeText(this, "用户名已经存在", Toast.LENGTH_SHORT).show();
                flag=0;
            }
        } else if (!(ed_pwd.getText().toString().equals(ed_pwd2.getText().toString()))) {
            flag=0;
            ed_pwd.setText("");
            ed_pwd2.setText("");
            Toast.makeText(this, "两次输入密码错误，请重新输入", Toast.LENGTH_SHORT).show();
        }
        if(flag==1){
            dbHelper.AddUser(ed_account.getText().toString(), ed_pwd.getText().toString());
            Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
            Log.e("23","success");
            Intent intent = new Intent();
            intent.setClass(this, MainActivity.class);
            startActivity(intent);
            dbHelper.close();
        }



//        else {
//            Toast.makeText(this, "用户注册失败", Toast.LENGTH_SHORT).show();
//        }

    }
}
