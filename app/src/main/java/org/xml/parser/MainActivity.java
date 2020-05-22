package org.xml.parser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import org.xml.parser.tools.XmlResolver;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<AppInfo> appInfos = XmlResolver.toBean(AppInfo.class, getResources().getXml(R.xml.app_whils_list));
        Log.e("TAG", "白名单: ----------------------------------");
        for (AppInfo appInfo : appInfos) {
            Log.e("TAG", "run: "+appInfo.toString() );
        }

        appInfos = XmlResolver.toBean(AppInfo.class, getResources().getXml(R.xml.app_black_list));
        Log.e("TAG", "黑名单: ----------------------------------");
        for (AppInfo appInfo : appInfos) {
            Log.e("TAG", "run: "+appInfo.toString() );
        }

    }
}
