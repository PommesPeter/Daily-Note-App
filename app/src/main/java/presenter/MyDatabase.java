package presenter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import model.Data;

public class MyDatabase {
    Context context;
    DBHelper DBHelper;
    SQLiteDatabase mydatabase;
    Cursor cursor;
    Data data;

    public MyDatabase(Context context) {
        this.context = context;
        DBHelper = new DBHelper(context, "LoginInfo", null, 5);
        mydatabase = DBHelper.getWritableDatabase();//初始化 遇到空指针就初始化一下对象


    }

    public ArrayList<Data> getarray() {
        //获取listview中要显示的数据
        //指定特定账户的便签内容
        //原始数据源
        ArrayList<Data> arr = new ArrayList<Data>();
        String userid = null;
        String sql = "select userid from user where isLogin=1";
        Cursor cursor_userid = DBHelper.getWritableDatabase().rawQuery(sql, null);
        if (cursor_userid.moveToFirst()) {
            do {
                userid = cursor_userid.getString(cursor_userid.getColumnIndex("userid"));
            } while (cursor_userid.moveToNext());
        }
        //获取用户名
        data = new Data(userid);
        //查询该用户名对应的便签内容
        cursor = mydatabase.rawQuery("select note_id,title,content,time from notes where userid='" + data.getUserid() + "' ORDER BY note_id DESC ", null);
        Log.e("userid_mydatabase1", data.getUserid());
        if (cursor.moveToFirst()) {
            do {
//                String userid1 = cursor.getString(cursor.getColumnIndex("userid"));
                int note_id = cursor.getInt(cursor.getColumnIndex("note_id"));
//            Log.e("note_id_mydatabase", String.valueOf(userid));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                Log.e("title", String.valueOf(title));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                String time = cursor.getString(cursor.getColumnIndex("time"));
                Data data = new Data(userid, note_id, title, content, time);
                arr.add(data);
            } while (cursor.moveToNext());
        }
        return arr;
    }

    //获取要修改数据（就是当选择listview子项想要修改数据时，获取数据显示在新建页面）
    public Data getTiandCon(int note_id) {
        mydatabase = DBHelper.getWritableDatabase();
        Cursor cursor = mydatabase.rawQuery("select note_id,title,content from notes where note_id='" + note_id + "'", null);//1
        cursor.moveToFirst();
        String title = cursor.getString(cursor.getColumnIndex("title"));
        String content = cursor.getString(cursor.getColumnIndex("content"));
        data = new Data(title, content);
        mydatabase.close();
        return data;
    }

    public void toUpdate(Data data) {           //修改表中数据
        mydatabase = DBHelper.getWritableDatabase();
        mydatabase.execSQL(
                "update notes set title='" + data.getTitle() +
                        "',content='" + data.getContent() +
                        "',time='" + data.getTime() +
                        "' where note_id='" + data.getNote_id() + "'");
        mydatabase.close();

    }

    public void toInsert(Data data) {           //在表中插入新建的便签的数据
        mydatabase = DBHelper.getWritableDatabase();
        mydatabase.execSQL("insert into notes(userid,title,content,time)values('"
                + data.getUserid() + "','"
                + data.getTitle() + "','"
                + data.getContent() + "','"
                + data.getTime() + "')");
        mydatabase.close();
    }

    public void toDelete(int note_id) {
        //在表中删除数据
        mydatabase = DBHelper.getWritableDatabase();
        mydatabase.execSQL("delete from notes where note_id=" + note_id);
        mydatabase.close();
    }

    public void UpdateLoginStatus_online(String userid, int isLogin) {
        data = new Data(userid, isLogin);
        mydatabase = DBHelper.getWritableDatabase();
        mydatabase.execSQL("update user set isLogin = 1 where userid = '" + data.getUserid() + "' ");

    }

    public void UpdateLoginStatus_offline(String userid, int isLogin) {
        data = new Data(userid, isLogin);
        mydatabase = DBHelper.getWritableDatabase();
        mydatabase.execSQL("update user set isLogin = 0 where userid = '" + data.getUserid() + "'");

    }

    public void UpdataUserInfo(String name, String age, String sex, String qnum, String tel, String email) {
        data = new Data(name, age, sex, qnum, tel, email);
        mydatabase = DBHelper.getWritableDatabase();
        mydatabase.execSQL(
                "update user set " + "username='" + data.getUsername() +
                        "',userage='" + data.getUserage() +
                        "',usersex='" + data.getUsersex() +
                        "',useremail='" + data.getUseremail() +
                        "',userqqnum='" + data.getUserqqnum() +
                        "',usertel='" + data.getUsertel() +
                        "' where isLogin=1");

    }

    public void init_isLogin() {
        mydatabase = DBHelper.getWritableDatabase();
        mydatabase.execSQL("update user set isLogin=0 where isLogin=1");

    }

    public void init_isRemb() {
        mydatabase = DBHelper.getWritableDatabase();
        mydatabase.execSQL("update user set isRemb=0");

    }

    public void RememberPwd(String userid, int isLogin, int isRem) {
        data = new Data(userid, isLogin, isRem);
        mydatabase.execSQL("update user set isRemb=1 where isLogin=1 AND userid='" + data.getUserid() + "'");

    }

    public void ModifyPwd(String userid, String Olduserpwd,String Newuserpwd) {
        data = new Data(userid, Olduserpwd,Newuserpwd);
        mydatabase.execSQL("update user set userpwd="+Newuserpwd+" where userid='" + data.getUserid() + "'");

    }

}


