package com.example.proj.zhaohuo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by Administrator on 2016/12/9.
 */

public class circlelistActivity extends AppCompatActivity {
    Toolbar toolbar;
    private void initialize(){
        setContentView(R.layout.circlelist);

    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }
}
