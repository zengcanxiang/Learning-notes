package com.zengcanxiang.learning_notes_animation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zengcanxiang.baseAdapter.absListView.HelperAdapter;
import com.zengcanxiang.baseAdapter.absListView.HelperViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 动画学习模块主页
 */
public class MainActivity extends AppCompatActivity {
    ListView mainList;
    List<ListBean> data = new ArrayList<>();
    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        initData();
        mainList = (ListView) findViewById(R.id.mainList);
        mainList.setAdapter(new MainListAdapter(data, context));
        mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(context, data.get(position).getToClz());
                startActivity(i);
            }
        });
    }

    private void initData() {
        ListBean one = new ListBean("PopViewAndBgZoom", "弹出view，同时背景缩放", PopViewAndBgZoomActivity.class);
        data.add(one);

    }

    private class MainListAdapter extends HelperAdapter<ListBean> {

        public MainListAdapter(List<ListBean> mList, Context context) {
            super(mList, context, R.layout.main_list_item);
        }

        @Override
        public void HelpConvert(HelperViewHolder viewHolder, int position, ListBean s) {
            viewHolder.setText(R.id.list_item_name, s.getName());
            viewHolder.setText(R.id.list_item_brief_introduction, s.getBrief_introduction());
        }
    }
}
