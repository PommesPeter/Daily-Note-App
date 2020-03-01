package presenter;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {


    public static final String TB_NAME = "user";
    public static final String ID = "id";
    public static final String NAME = "userid";
    public static final String UserPwd = "userpwd";
    public static final String TIME = "time";
    public static final String isLogin = "isLogin";
    public static final String isRemb = "isRemb";

    public static final String TB_NAME_CT = "notes";
    public static final String Note_ID = "note_id";
    public static final String note_title = "title";
    public static final String note_contents = "content";


    public static final String Uname = "username";
    public static final String Age = "userage";
    public static final String Sex = "usersex";
    public static final String Email = "useremail";
    public static final String Qqnum = "userqqnum";
    public static final String Tel = "usertel";


    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    //建表(用户登录信息)
    @Override
    public void onCreate(SQLiteDatabase arg0) {
        arg0.execSQL("CREATE TABLE "
                + TB_NAME + "("
                + ID + "  INTEGER PRIMARY KEY AUTOINCREMENT,"
                + NAME + "  VARCHAR,"
                + UserPwd + "  VARCHAR,"
                + Uname + "  VARCHAR,"
                + Age + "  VARCHAR,"
                + Sex + "  VARCHAR,"
                + Email + "  VARCHAR,"
                + Qqnum + "  VARCHAR,"
                + Tel + "  VARCHAR,"
                + isLogin + "  INTEGER,"
                + isRemb + "  INTEGER )");

        arg0.execSQL("CREATE TABLE "
                + TB_NAME_CT + "("
                + NAME + "  VARCHAR,"
                + Note_ID+ "  INTEGER PRIMARY KEY AUTOINCREMENT,"
                + note_title +"  VARCHAR, "
                + note_contents + "  VARCHAR,"
                + TIME + "  TEXT)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    //关闭数据库
    public void close() {
        this.getWritableDatabase().close();
    }
    //添加用户方法
    public boolean AddUser(String userid, String userpwd) {
        try {
            ContentValues cv1 = new ContentValues();
            cv1.put(DBHelper.NAME, userid);//添加用户名
            cv1.put(DBHelper.UserPwd, userpwd);//添加密码
            this.getWritableDatabase().insert(DBHelper.TB_NAME, null, cv1);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

}
