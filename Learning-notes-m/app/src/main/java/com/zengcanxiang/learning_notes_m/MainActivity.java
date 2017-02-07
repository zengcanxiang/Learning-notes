package com.zengcanxiang.learning_notes_m;

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
import com.zengcanxiang.learning_notes_m.finger_mark.FingerMarkActivity;
import com.zengcanxiang.learning_notes_m.permission.DangerPermissionListActivity;

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
        ListBean one = new ListBean("指纹识别", "Android 6.0 指纹识别", FingerMarkActivity.class);
        ListBean two = new ListBean("动态权限", "Android 6.0 危险权限列表", DangerPermissionListActivity.class);
        data.add(one);
        data.add(two);
    }

    private class MainListAdapter extends HelperAdapter<ListBean> {

        public MainListAdapter(List<ListBean> mList, Context context) {
            super(mList, context, R.layout.main_list_item);
        }

        @Override
        public void HelperBindData(HelperViewHolder viewHolder, int position, ListBean s) {
            viewHolder.setText(R.id.list_item_name, s.getName());
            viewHolder.setText(R.id.list_item_brief_introduction, s.getBrief_introduction());
        }
    }
}
