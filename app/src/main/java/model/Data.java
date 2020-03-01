package model;

public class Data {
    private String name;
    private String age;
    private String sex;
    private String email;
    private String qnum;
    private String tel;

    private String rempwd;
    private String userid;
    private String Olduserpwd;
    private String Newuserpwd;



    private String title;   //标题
    private String content; //内容
    private int note_id;    //编号
    private String time;
    private int isLogin;
    private int isRem;

    public Data(String name, String age, String sex, String qnum, String tel, String email) {

        this.name = name;
        this.age = age;
        this.sex = sex;
        this.qnum = qnum;
        this.tel = tel;
        this.email = email;
    }

    public Data(int note_id, String ti, String con,String time) {
        this.note_id = note_id;
        this.title = ti;
        this.content = con;
        this.time = time;
    }
    public Data(String userid,int note_id, String ti, String con,String time) {
        this.userid = userid;
        this.note_id = note_id;
        this.title = ti;
        this.content = con;
        this.time = time;
    }

    public Data(int note_id, String ti, String con) {
        this.note_id = note_id;
        this.title = ti;
        this.content = con;

    }
//    public Data(int i, String ti,String content) {
//        this.note_id = i;
//        this.title = ti;
//        this.content=content;
//    }
//    public Data(String ti,String con,String time){
//        this.title = ti;
//        this.content = con;
//        this.time = time;
//    }
    public Data(String ti, String con) {

        this.title = ti;
        this.content = con;
    }
    public Data(String userid ,int isLogin){
        this.userid=userid;
        this.isLogin = isLogin;
    }
    public Data(String userid ,int isLogin, int isRem){
        this.userid= userid;
        this.isLogin= isLogin;
        this.isRem =isRem;
    }
    public Data(String userid){
        this.userid=userid;
    }
    public Data(String userid,String Olduserpwd,String Newuserpwd){
        this.userid=userid;
        this.Olduserpwd=Olduserpwd;
        this.Newuserpwd=Newuserpwd;

    }

    public int getNote_id() {
        return note_id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getTime() {
        return time;
    }

    public String getUserid(){
        return userid;
    }

    public String getUsername(){
        return name;
    }

    public String getUserage(){
        return age;
    }

    public String getUsersex(){
        return sex;
    }

    public String getUserqqnum(){
        return qnum;
    }

    public String getUseremail(){
        return email;
    }

    public String getUsertel(){
        return tel;
    }



}