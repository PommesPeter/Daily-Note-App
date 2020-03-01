package com.example.work2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import model.Data;
import presenter.DBHelper;
import presenter.MyDatabase;


public class ed_info extends AppCompatActivity {
    EditText ed_name;
    EditText ed_age;
    EditText ed_sex;
    EditText ed_qnum;
    EditText ed_tel;
    EditText ed_email;
    Button saveInfo;
    DBHelper dbHelper;
    Data data;
    private EditText ettext;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ed_userinfo);
        //声明一个编辑框和布局文件中id为info_xxx的编辑框链接起来
        ed_name = findViewById(R.id.info_name);
        ed_age = findViewById(R.id.info_age);
        ed_sex = findViewById(R.id.info_sex);
        ed_qnum = findViewById(R.id.info_qnumber);
        ed_tel = findViewById(R.id.info_tel);
        ed_email = findViewById(R.id.info_email);
        saveInfo = findViewById(R.id.saveInfo);
        saveInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper = new DBHelper(ed_info.this, "LoginInfo", null, 5);
                sendInfo();
                Toast.makeText(ed_info.this, "已保存用户信息 ♪(^∇^*)~~~~", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void sendInfo() {

        Intent intent_Info = new Intent(ed_info.this, info_user.class);
        // 把编辑框获取的文本赋值给String类型的nameMessage。
        String nameMessage = ed_name.getText().toString();
        String ageMessage = ed_age.getText().toString();
        String sexMessage = ed_sex.getText().toString();
        String emailMessage = ed_email.getText().toString();
        String qnumMessage = ed_qnum.getText().toString();
        String telMessage = ed_tel.getText().toString();

        data = new Data(nameMessage,ageMessage,sexMessage,emailMessage,qnumMessage,telMessage);
        MyDatabase myDatabase = new MyDatabase(this);
        myDatabase.UpdataUserInfo(data.getUsername(),data.getUserage(),data.getUsersex(),data.getUseremail(),data.getUserqqnum(),data.getUsertel());

        // 给message起一个名字，并传给另一个activity
        intent_Info.putExtra("EXTRA_nameMESSAGE", nameMessage);
        intent_Info.putExtra("EXTRA_sexMESSAGE", sexMessage);
        intent_Info.putExtra("EXTRA_ageMESSAGE", ageMessage);
        intent_Info.putExtra("EXTRA_emailMESSAGE", emailMessage);
        intent_Info.putExtra("EXTRA_qnumMESSAGE", qnumMessage);
        intent_Info.putExtra("EXTRA_telMESSAGE", telMessage);
        // 启动意图
        startActivity(intent_Info);


    }
    //添加用户信息方法
//    public boolean AddInfo() {
//
//        try {
//            ContentValues cv1 = new ContentValues();
//            cv1.put(DBHelper.Uname, ed_name.getText().toString());//添加用户名字
//            cv1.put(DBHelper.Age, ed_age.getText().toString());//添加用户年龄
//            cv1.put(DBHelper.Sex, ed_sex.getText().toString());//添加用户性别
//            cv1.put(DBHelper.Email, ed_email.getText().toString());//添加用户电子邮件
//            cv1.put(DBHelper.Qqnum, ed_qnum.getText().toString());//添加用户qq号码
//            cv1.put(DBHelper.Tel, ed_tel.getText().toString());//添加用户电话
//            dbHelper.getWritableDatabase().insert(DBHelper.TB_NAME, null, cv1);
//            return true;
//        } catch (Exception ex) {
//            Log.e("EX",String.valueOf(ex));
//            return false;
//        }
//    }

}
