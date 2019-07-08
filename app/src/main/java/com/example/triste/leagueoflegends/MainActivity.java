package com.example.triste.leagueoflegends;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import adapter.HeroAdapter;
import bean.Hero;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static util.XmlUtil.parseXMLWithPull;

public class MainActivity extends AppCompatActivity {

    private static List<Hero> heroList = new ArrayList<>();

    private static String str = "";

    private RecyclerView recyclerView;

    private HeroAdapter adapter;

    private long mExitTime;

    private Toolbar toolbar;

    private DrawerLayout drawerLayout;

    private SwipeRefreshLayout swipeRefresh;

    private EditText searchHero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshHeros();
            }
        });


        initHeroList();
        recyclerView = (RecyclerView) findViewById(R.id.Hero_List);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new HeroAdapter(heroList);
        recyclerView.setAdapter(adapter);

        searchHero = (EditText) findViewById(R.id.search_hero);
        searchHero.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if("".equals(s)){
                    searchHero.setCursorVisible(false);
                }else {
                    searchHero.setCursorVisible(true);
                }
                String condition = s.toString();
                searchHeroList(condition);
            }
        });
        searchHero.setCursorVisible(false);
    }

    private void refreshHeros() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        searchHero.setText("");
                        heroList.clear();
                        DataSupport.deleteAll(Hero.class);
                        initHeroList();
                        adapter.notifyDataSetChanged();         //此方法使用的list必须从头至尾都使用相同的堆内存，即不能使用赋值或new改变list指向的位置，
                        //否则此方法会失效
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(MainActivity.this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

    private void initHeroList(){
        Cursor cursor = DataSupport.findBySQL("select * from hero");
        if (cursor.moveToFirst()) {
            do {
                String heroHeadSculptureUrl = cursor.getString(cursor.getColumnIndex("heroheadsculptureurl"));
                String heroDesignNation = cursor.getString(cursor.getColumnIndex("herodesignnation"));
                String heroName = cursor.getString(cursor.getColumnIndex("heroname"));
                String heroAttribute = cursor.getString(cursor.getColumnIndex("heroattribute"));
                String heroSkinsUrl = cursor.getString(cursor.getColumnIndex("heroskinsurl"));
                String backgroundStory = cursor.getString(cursor.getColumnIndex("backgroundstory"));
                String skillImageUrl = cursor.getString(cursor.getColumnIndex("skillimageurl"));
                String skillName = cursor.getString(cursor.getColumnIndex("skillname"));
                String detaildInfo = cursor.getString(cursor.getColumnIndex("detaildinfo"));

                Hero hero = new Hero(heroHeadSculptureUrl, heroDesignNation, heroName, heroAttribute, heroSkinsUrl, backgroundStory, skillImageUrl, skillName, detaildInfo);
                heroList.add(hero);
            } while (cursor.moveToNext());
        }else {
            Log.d("hero", "需要从网络中读取");
            Thread getHeroInfo = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder().url("http://192.168.0.110:8080/hero.xml").build();
                        Response response = client.newCall(request).execute();
                        str = response.body().string();
                        List<Hero> heroes = parseXMLWithPull(str);
                        for(Hero h :heroes){
                            heroList.add(h);
                        }
                        Log.d("hero", heroList.size() + "");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            getHeroInfo.start();
            try {
                getHeroInfo.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void searchHeroList(String condition){
        heroList.clear();
        if("".equals(condition)){
            initHeroList();
            adapter.notifyDataSetChanged();
            return;
        }
        String sql = "select * from hero where herodesignnation = '" + condition + "' or herodesignnation like '%" +condition+ "%'" + "or heroname = '" + condition + "' or heroname like '%"+ condition +"%'";
        Log.d("hero","SQL :"+sql);
        Cursor cursor = DataSupport.findBySQL(sql);
        if (cursor.moveToFirst()) {
            do {
                String heroHeadSculptureUrl = cursor.getString(cursor.getColumnIndex("heroheadsculptureurl"));
                String heroDesignNation = cursor.getString(cursor.getColumnIndex("herodesignnation"));
                String heroName = cursor.getString(cursor.getColumnIndex("heroname"));
                String heroAttribute = cursor.getString(cursor.getColumnIndex("heroattribute"));
                String heroSkinsUrl = cursor.getString(cursor.getColumnIndex("heroskinsurl"));
                String backgroundStory = cursor.getString(cursor.getColumnIndex("backgroundstory"));
                String skillImageUrl = cursor.getString(cursor.getColumnIndex("skillimageurl"));
                String skillName = cursor.getString(cursor.getColumnIndex("skillname"));
                String detaildInfo = cursor.getString(cursor.getColumnIndex("detaildinfo"));

                Hero hero = new Hero(heroHeadSculptureUrl, heroDesignNation, heroName, heroAttribute, heroSkinsUrl, backgroundStory, skillImageUrl, skillName, detaildInfo);
                heroList.add(hero);
            } while (cursor.moveToNext());
            adapter.notifyDataSetChanged();
        }else{
            Toast.makeText(this,"没有查找到内容",Toast.LENGTH_SHORT).show();
        }
    }
}

