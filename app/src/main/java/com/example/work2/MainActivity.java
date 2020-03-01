package com.example.work2;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import model.Data;
import presenter.DBHelper;
import presenter.MyDatabase;


public class MainActivity extends AppCompatActivity {

    EditText ed_id;
    EditText ed_pwd;
    TextView forget_pwd;
    DBHelper dbHelper;
    CheckBox rememberPwd;
    View AccountDelete;
    View pwdDelete;
    Data data;
    MyDatabase myDatabase;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        forget_pwd = findViewById(R.id.find_pwd);
        rememberPwd = findViewById(R.id.rememberPwd);
        ed_id = findViewById(R.id.Account2);
        ed_pwd = findViewById(R.id.EnterPwd2);
        AccountDelete = findViewById(R.id.accountDelete);
        pwdDelete = findViewById(R.id.pwdDelete);
        EditTextUtils.clearButtonListener(ed_id,AccountDelete);
        EditTextUtils.clearButtonListener(ed_pwd,pwdDelete);
        //获取用户是否是登录状态
        dbHelper = new DBHelper(MainActivity.this, "LoginInfo", null, 5);
        //每次启动登录界面都会查看用户是否有勾上记住密码
        String sql = "Select isRemb from user where isLogin=1";
        Cursor cursor = dbHelper.getWritableDatabase().rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do{
                int flag = cursor.getInt(cursor.getColumnIndex("isRemb"));

                //只要isRemb一直为1 那么就可以一直记住密码
                if(flag==1){
                    rememberPwd.setChecked(true);
                    String sql1 = "select userid,userpwd from user where isRemb=1";
                    Cursor cursor1 = dbHelper.getWritableDatabase().rawQuery(sql1,null);
                    if(cursor1.moveToFirst()) {

                        String userid = cursor1.getString(cursor1.getColumnIndex("userid"));
                        String userpwd = cursor1.getString(cursor1.getColumnIndex("userpwd"));
                        Log.e("remb",userid);
                        ed_id.setText(userid);
                        ed_pwd.setText(userpwd);
                    }
                    break;
                }
            }while(cursor.moveToNext());
        }

        Intent intent5 = getIntent();
        //每次启动登录界面就把isLogin置0
        data = new Data(intent5.getStringExtra("userid"), 0);
        myDatabase = new MyDatabase(getApplicationContext());
//        Log.e("MA",intent5.getStringExtra("userid"));
        //初始化标志
        myDatabase.init_isRemb();
        myDatabase.init_isLogin();

        Button button_register = findViewById(R.id.button_register);
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent1);
            }
        });


        Button button_login = findViewById(R.id.button_login);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUser();
            }
            //判断用户账号密码是否正确
            public void getUser() {
                String sql = "select * from user where userid=?";
                Cursor cursor = dbHelper.getWritableDatabase().rawQuery(sql, new String[]{ed_id.getText().toString()});

                    if (cursor.moveToFirst()) {
                        if (ed_id.getText().toString().length() <= 0 || ed_pwd.getText().toString().length() <= 0) {

                            Toast.makeText(MainActivity.this, "用户名密码不能为空 (￣_,￣ )~~~", Toast.LENGTH_SHORT).show();
                            Log.e("MainActivity", "empty");
                            return;
                        } else if(!(ed_pwd.getText().toString().equals(cursor.getString(cursor.getColumnIndex("userpwd"))))) {
                            Toast.makeText(MainActivity.this, "用户名或者密码错误 w(ﾟДﾟ)w~~~", Toast.LENGTH_SHORT).show();
                            Log.e("MainActivity", "Error");
                        } else if(ed_id.getText().toString().length()<6 || ed_pwd.getText().toString().length()>20
                        || ed_pwd.getText().toString().length()<6 || ed_pwd.getText().toString().length()>20){
                                Toast.makeText(MainActivity.this, "请输入至少6位且不超过20位的用户名和密码",Toast.LENGTH_SHORT).show();
                        } else if (ed_pwd.getText().toString().equals(cursor.getString(cursor.getColumnIndex("userpwd")))) {

                            Toast.makeText(MainActivity.this, "登录成功 φ(≧ω≦*)♪~~~~", Toast.LENGTH_SHORT).show();
                            Intent intent2 = new Intent(MainActivity.this, NoteActivity.class);
                            intent2.putExtra("userid",ed_id.getText().toString());
                            myDatabase = new MyDatabase(getApplicationContext());
                            myDatabase.UpdateLoginStatus_online(ed_id.getText().toString(),1);
                            startActivity(intent2);
                            //登录成功才记住密码
                            RemPwdStatus();
                            cursor.close();
                            MainActivity.this.finish();

                            Log.e("MainActivity", "it work.");

                        }
                    }
                    dbHelper.close();
                }
        });

        forget_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LostPwdActivity.class);
                startActivity(intent);
            }
        });
    }


    //记住密码
    //在数据设置多一个数据列，isRemember 当isRemember=1时为用户勾上记住密码，然后再设置一个数据列为isLogin 如果登录了，还勾上了记住密码，就在返回登录界面的同时把对应的密码设置上。
    private void RemPwdStatus() {
        if (rememberPwd.isChecked()) {
                data = new Data(ed_id.getText().toString(),1,1);
                myDatabase = new MyDatabase(getApplicationContext());
                myDatabase.RememberPwd(data.getUserid(),1,1);
//                rememberPwd.setChecked(true); 设置checkbox状态
            } else{
            myDatabase.init_isRemb();
        }
    }

}
    //用于实现点击叉叉时  ,  清空输入框的内容
class EditTextUtils {
    public static void clearButtonListener(final EditText editText, final View view) {

        // 取得account2 and Enterpwd2中的文字
        String etInputString = editText.getText().toString();
        // 根据et中是否有文字进行X可见或不可见的判断
        if (TextUtils.isEmpty(etInputString)) {
            view.setVisibility(View.INVISIBLE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
        //点击X时使et中的内容为空

        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                editText.setText("");
                editText.requestFocusFromTouch();
            }
        });
        //对editText的输入状态进行监听
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() == 0) {
                    view.setVisibility(View.INVISIBLE);
                } else {
                    view.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
