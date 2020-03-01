package com.example.work2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class info_user extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info);
        TextView display_name = findViewById(R.id.display_name);
        TextView display_age = findViewById(R.id.display_age);
        TextView display_sex = findViewById(R.id.display_sex);
        TextView display_email = findViewById(R.id.display_email);
        TextView display_qqnum = findViewById(R.id.display_qqnum);
        TextView display_tel = findViewById(R.id.display_telnum);

        Intent intent1 = getIntent();
        //把传送进来的String类型的xxxMessage的值赋给新的变量recXXXmsg
        String recNamemsg = intent1.getStringExtra("EXTRA_nameMESSAGE");
        String recAgemsg = intent1.getStringExtra("EXTRA_ageMESSAGE");
        String recSexmsg = intent1.getStringExtra("EXTRA_sexMESSAGE");
        String recEmailmsg = intent1.getStringExtra("EXTRA_emailMESSAGE");
        String recQnummsg = intent1.getStringExtra("EXTRA_qnumMESSAGE");
        String recTelmsg = intent1.getStringExtra("EXTRA_telMESSAGE");
        //显示
            display_name.setTextColor(0xff000000);//0xffff00ff是int类型的数据
            display_name.setText(recNamemsg);
            display_age.setTextColor(0xff000000);
            display_age.setText(recAgemsg);
            display_sex.setTextColor(0xff000000);
            display_sex.setText(recSexmsg);
            display_email.setTextColor(0xff000000);
            display_email.setText(recEmailmsg);
            display_qqnum.setTextColor(0xff000000);
            display_qqnum.setText(recQnummsg);
            display_tel.setTextColor(0xff000000);
            display_tel.setText(recTelmsg);

    }
}
