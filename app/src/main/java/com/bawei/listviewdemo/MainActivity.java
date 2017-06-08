package com.bawei.listviewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    List<CheckBean> list = new ArrayList<CheckBean>() ;
    private ListView listView;


    boolean checked ;
    private CheckBoxAdapter adapter;
    private CheckBean checkBean;

    int total = 0;
    int startIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        listView = (ListView) findViewById(R.id.listView);

        getData();


        //条目点击
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();

            }
        });


        //全选的按钮
        findViewById(R.id.quanXuan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                for(int i=0;i<list.size();i++){
                    if(!checked){
                        list.get(i).setIscheck(true);
                    }else {
                        list.get(i).setIscheck(false);
                    }
                }
                adapter.notifyDataSetChanged();

                if(!checked){
                    checked = true;
                }else {
                    checked = false;
                }

            }
        });



        //listview滑动监听
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {



                if (scrollState ==  AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {

                    if (listView.getLastVisiblePosition() == list.size()-1) {
                        //加载数据

                        startIndex = startIndex+20;
                        if (list.size() == 100){

                            Toast.makeText(MainActivity.this, "全部加载完毕", Toast.LENGTH_SHORT).show();

                        }else {

                            getData();

                        }
                    }
                }


            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });


    }


    /**
     * 加载数据 并设置适配器
     */
    private void getData() {

        //添加数据
        for(int i = startIndex; i < startIndex+20; i++){
            checkBean = new CheckBean();
            checkBean.setContent("ListView"+i);
            list.add(checkBean);
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        /**
         * 如果没有适配器 创建出来 设置
         * 如果有 在数据改变的时候 刷新适配器
         */
        if (adapter == null) {
            adapter = new CheckBoxAdapter(list, MainActivity.this);
            listView.setAdapter(adapter);
        }else {
            adapter.notifyDataSetChanged();
        }


    }


}
