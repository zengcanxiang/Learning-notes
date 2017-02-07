package com.zengcanxiang.learning_notes_m.permission;

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
import com.zengcanxiang.learning_notes_m.ListBean;
import com.zengcanxiang.learning_notes_m.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 危险权限列表展示界面
 */
public class DangerPermissionListActivity extends AppCompatActivity {
    ListView dangerPermissionList;
    List<ListBean> data = new ArrayList<>();
    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danger_permission);
        context = this;
        initData();
        dangerPermissionList = (ListView) findViewById(R.id.danger_permission_list);
        dangerPermissionList.setAdapter(new DangerPermissionListAdapter(data, context));
        dangerPermissionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(context, data.get(position).getToClz());
                startActivity(i);
            }
        });
    }

    private void initData() {
        ListBean bean1 = new ListBean("动态权限组申请", "Android 6.0 权限申请 通讯录 ", ContactsPermissionActivity.class);
        ListBean bean2 = new ListBean("动态权限组申请", "Android 6.0 权限申请 手机相关 ", CallPhonePermissionActivity.class);
        ListBean bean3 = new ListBean("动态权限组申请", "Android 6.0 权限申请 日程信息 ", CalendarPermissionActivity.class);
        ListBean bean4 = new ListBean("动态权限组申请", "Android 6.0 权限申请 相机 ", CameraPermissionActivity.class);
        ListBean bean5 = new ListBean("动态权限组申请", "Android 6.0 权限申请 传感器 ", SensorsPermissionActivity.class);
        ListBean bean6 = new ListBean("动态权限组申请", "Android 6.0 权限申请 位置信息 ", LocationPermissionActivity.class);
        ListBean bean7 = new ListBean("动态权限组申请", "Android 6.0 权限申请 文件存储 ", StoragePermissionActivity.class);
        ListBean bean8 = new ListBean("动态权限组申请", "Android 6.0 权限申请 录音 ", AudioPermissionActivity.class);
        ListBean bean9 = new ListBean("动态权限组申请", "Android 6.0 权限申请 短信 ", SMSPermissionActivity.class);
        data.add(bean1);
        data.add(bean2);
        data.add(bean3);
        data.add(bean4);
        data.add(bean5);
        data.add(bean6);
        data.add(bean7);
        data.add(bean8);
        data.add(bean9);
    }

    private class DangerPermissionListAdapter extends HelperAdapter<ListBean> {

        public DangerPermissionListAdapter(List<ListBean> mList, Context context) {
            super(mList, context, R.layout.main_list_item);
        }

        @Override
        public void HelperBindData(HelperViewHolder viewHolder, int position, ListBean s) {
            viewHolder.setText(R.id.list_item_name, s.getName());
            viewHolder.setText(R.id.list_item_brief_introduction, s.getBrief_introduction());
        }
    }
}
