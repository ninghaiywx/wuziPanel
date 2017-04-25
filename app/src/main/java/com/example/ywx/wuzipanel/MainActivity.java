package com.example.ywx.wuzipanel;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button noteButton;
    private LinearLayout dialog_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        noteButton=(Button)findViewById(R.id.note_button);
        dialog_layout=(LinearLayout)findViewById(R.id.dialog_layout);
        noteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                AlertDialog dialog=builder.create();
                dialog.show();
                Window window=dialog.getWindow();
                window.setContentView(R.layout.dialog_layout);
                final StringBuffer buffer=new StringBuffer();
                buffer.append("本程序为五子棋游戏\n");
                buffer.append("由两人在同一设备上对弈，白先黑后\n");
                buffer.append("作者: 岳炜翔\n");
                buffer.append("版本: V1.0");
                TextView title=(TextView)window.findViewById(R.id.dialog_title);
                TextView message=(TextView)window.findViewById(R.id.dialog_message);
                title.setBackgroundColor(Color.parseColor("#F0BF70"));
                message.setText(buffer);
                title.setText("说明");
            }
        });
    }
}
