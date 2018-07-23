package com.nodeprogress.nodeprogress;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.fish0.nodeview.BaseNodeProgressView;
import com.fish0.nodeview.LogisticsData;
import com.fish0.nodeview.NodeProgressAdapter;

import java.util.ArrayList;
import java.util.List;

public class BaseNodeActivity extends AppCompatActivity {



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        final ArrayList<LogisticsData> logisticsDatas = new ArrayList<>();
        logisticsDatas.add(new LogisticsData().setContext("填写审核资料并提交"));
        logisticsDatas.add(new LogisticsData().setContext("交付押金"));
        logisticsDatas.add(new LogisticsData().setContext("上传资料"));
        logisticsDatas.add(new LogisticsData().setContext("申请成功"));


        BaseNodeProgressView nodeProgressView = (BaseNodeProgressView) findViewById(R.id.npv_NodeProgressView);
        nodeProgressView.setNodeProgressAdapter(new NodeProgressAdapter() {

            @Override
            public int getCount() {
                return logisticsDatas.size();
            }

            @Override
            public List<LogisticsData> getData() {
                return logisticsDatas;
            }
        });

        nodeProgressView.setNodeSelectIndex(3);
        nodeProgressView.setNodeOricentalV();

    }
}
