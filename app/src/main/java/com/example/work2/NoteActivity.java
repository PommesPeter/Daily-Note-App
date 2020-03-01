package com.example.work2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import android.widget.ListView;

import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import ViewPackage.SlideCutListView;
import model.Data;
import presenter.DBHelper;
import presenter.MyAdapter;
import presenter.MyDatabase;


public class NoteActivity extends AppCompatActivity implements SlideCutListView.RemoveListener {
    //设置全局变量
    ListView listView;
    SlideCutListView slideCutListView;
    FloatingActionButton floatingActionButton;
    LayoutInflater layoutInflater;
    ArrayList<Data> arrayList;
    MyDatabase myDatabase;
    MyAdapter myAdapter;
    DBHelper dbHelper;
    Data data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoteActivity.this, New_Note.class);
                startActivity(intent);
            }
        });
        //点一下跳到编辑界面
        slideCutListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(NoteActivity.this, New_Note.class);
                int value = arrayList.get(position).getNote_id();
                intent.putExtra("note_id", value);
                Log.e("NoteActivity_note_id", String.valueOf(value));
                startActivity(intent);
//                NoteActivity.this.finish();
            }
        });
        //长按删除
        slideCutListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                //弹出一个对话框
                new AlertDialog.Builder(NoteActivity.this)
                        .setMessage("你确定要删除此便签吗？")
                        //.setTitle("确定要删除此便签？")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.e("debug", String.valueOf(arrayList.get(position).getNote_id()));
                                myDatabase.toDelete(arrayList.get(position).getNote_id());
                                init();

                            }
                        }).create()
                        .show();
                return false;

            }
        });

    }


        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_exit:
                Toast.makeText(this, "退出程序 ヾ(￣▽￣)Bye~Bye~", Toast.LENGTH_SHORT).show();
                Intent intent = getIntent();
                data = new Data(intent.getStringExtra("userid"), 0);
                myDatabase.UpdateLoginStatus_offline(data.getUserid(), 0);
                this.finish();
                break;
            case R.id.menu_backtologin:
                Toast.makeText(this, "已返回登录界面 ㄟ( ▔, ▔ )ㄏ", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                Intent intent5 = getIntent();
                data = new Data(intent5.getStringExtra("userid"), 0);
                myDatabase.UpdateLoginStatus_offline(data.getUserid(), 0);
                NoteActivity.this.finish();
                startActivity(intent2);
                break;
            case R.id.menu_edInfo:
                Intent intent3 = new Intent(getApplicationContext(), ed_info.class);
                startActivity(intent3);
                break;
            case R.id.menu_showInfo:
                Intent intent_Info = new Intent(NoteActivity.this,info_user.class);
                //将个人信息从数据库中提取出来
                String sql = "select username,userage,usersex,useremail,userqqnum,usertel from user where isLogin=1";
                dbHelper = new DBHelper(NoteActivity.this, "LoginInfo", null, 5);
                Cursor cursor =dbHelper.getWritableDatabase().rawQuery(sql,null);

                if(cursor.moveToFirst()){
                    do{
                        String name = cursor.getString(cursor.getColumnIndex("username"));
                        String age = cursor.getString(cursor.getColumnIndex("username"));
                        String sex = cursor.getString(cursor.getColumnIndex("usersex"));
                        String qqnum = cursor.getString(cursor.getColumnIndex("userqqnum"));
                        String email = cursor.getString(cursor.getColumnIndex("useremail"));
                        String tel = cursor.getString(cursor.getColumnIndex("usertel"));

                        intent_Info.putExtra("EXTRA_nameMESSAGE", name);
                        intent_Info.putExtra("EXTRA_sexMESSAGE", sex);
                        intent_Info.putExtra("EXTRA_ageMESSAGE", age);
                        intent_Info.putExtra("EXTRA_emailMESSAGE", email);
                        intent_Info.putExtra("EXTRA_qnumMESSAGE", qqnum);
                        intent_Info.putExtra("EXTRA_telMESSAGE", tel);

                    }while(cursor.moveToNext());
                    startActivity(intent_Info);
                }
                break;
            default:
        }
        return true;
    }




    private void init() {
        slideCutListView = findViewById(R.id.layout_listview);
//        listView = findViewById(R.id.layout_listview);
//        searchText = findViewById(R.id.layout_searchView);
//        searchText.setBackgroundColor(0x22ff00ff);
        floatingActionButton = findViewById(R.id.add_note);
        layoutInflater = getLayoutInflater();
        myDatabase = new MyDatabase(this);//改
        //listview数据源
        arrayList = myDatabase.getarray();//改
        //设置适配器
        myAdapter = new MyAdapter(this, arrayList);
        slideCutListView.setRemoveListener(this);
        slideCutListView.setAdapter(myAdapter);//改
        slideCutListView.setTextFilterEnabled(true);


    }


    @Override
    protected void onResume() {
        super.onResume();
        init();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    //滑动删除之后的回调方法
    public void removeItem(SlideCutListView.RemoveDirection direction, int position) {
        arrayList.remove(myAdapter.getItem(position));
        switch (direction) {
            case RIGHT:
                Toast.makeText(this, "向右删除 "+ position, Toast.LENGTH_SHORT).show();
                Log.e("slidetestR",arrayList.size()+"t");
                arrayList = myDatabase.getarray();
                myDatabase.toDelete(arrayList.get(position).getNote_id());
                init();
                break;
            case LEFT:
                Toast.makeText(this, "向左删除 "+ position, Toast.LENGTH_SHORT).show();
                arrayList = myDatabase.getarray();
                Log.e("slidetestL",arrayList.size()+"t");
                myDatabase.toDelete(arrayList.get(position).getNote_id());
                init();
                break;
            default:
                break;
        }

    }
}

