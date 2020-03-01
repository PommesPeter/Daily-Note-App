package com.example.work2;

import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;

import model.Data;
import presenter.DBHelper;
import presenter.MyDatabase;


public class New_Note extends AppCompatActivity {
    EditText ed_title;
    EditText ed_content;
    FloatingActionButton floatingActionButton;
    MyDatabase myDatabase;
    DBHelper dbHelper;
    String userid;
    Data data;
    int note_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_note);
        ed_title = findViewById(R.id.title);
        ed_content = findViewById(R.id.content);
        floatingActionButton = findViewById(R.id.finish);
        myDatabase = new MyDatabase(this);
        Intent intent = getIntent();
        note_id = intent.getIntExtra("note_id",0);
        Log.e("New_note_note_id", String.valueOf(note_id));
        if (note_id != 0) {
            data = myDatabase.getTiandCon(note_id);
            ed_title.setText(data.getTitle());
            ed_content.setText(data.getContent());
        }
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isSave();
            }
        });
    }
    private void isSave() {

         //写一个方法进行调用，如果是属于新建则插入数据表并返回主页面，如果是修改，修改表中数据并返回主页面
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日  HH：mm");
        Date date = new Date(System.currentTimeMillis());
        String time = simpleDateFormat.format(date);
        Log.d("new_note", "isSave: "+time);
        //获取要修改数据（就是当选择listview子项想要修改数据时，获取数据显示在新建页面）
        String title = ed_title.getText().toString();
        String content = ed_content.getText().toString();
        String sql = "select userid from user where isLogin=1";
        dbHelper = new DBHelper(New_Note.this, "LoginInfo", null, 5);
        Cursor cursor = dbHelper.getWritableDatabase().rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do {
                userid = cursor.getString(cursor.getColumnIndex("userid"));
            } while (cursor.moveToNext());
        }
        if (note_id != 0) {
            data = new Data(userid,note_id,title,content,time);
            myDatabase.toUpdate(data);
            Intent intent = new Intent(New_Note.this,NoteActivity.class);
            startActivity(intent);
            New_Note.this.finish();
        }
        //新建日记
        else {
            data = new Data(userid,note_id,title, content,time);
            myDatabase.toInsert(data);
            Intent intent = new Intent(New_Note.this, NoteActivity.class);
            startActivity(intent);
            New_Note.this.finish();

        }

    }
    //重写返回建方法，如果是属于新建则插入数据表并返回主页面，如果是修改，修改表中数据并返回主页面
    @Override
    public void onBackPressed() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日  HH：mm");
        Date date = new Date(System.currentTimeMillis());
        String time = simpleDateFormat.format(date);
        String title = ed_title.getText().toString();
        String content = ed_content.getText().toString();

        if (note_id != 0) {
            data = new Data(userid,note_id,title,content,time);
            myDatabase.toUpdate(data);
//            Intent intent = new Intent(New_Note.this, MainActivity.class);
//            startActivity(intent);
            New_Note.this.finish();
        }
        //新建日记
        else {
            myDatabase.toInsert(data);
            Intent intent = new Intent(New_Note.this, MainActivity.class);
            startActivity(intent);

            New_Note.this.finish();
        }

    }

}
