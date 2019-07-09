package com.example.triste.leagueoflegends;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import adapter.GlideImageLoader;
import bean.Hero;

public class HeroInformation extends AppCompatActivity implements View.OnClickListener{

    private List<String> skillImageUrls = new ArrayList<>();

    private List<String> skillNames = new ArrayList<>();

    private List<String> skillInfos = new ArrayList<>();

    private List<String> skinUrls = new ArrayList<>();

    private List<ImageButton> imageButtons = new ArrayList<>();

    private TextView heroSkillName;

    private TextView heroSkillInfo;

    private TextView herobackgroundStory;

    private static String[] shortcut_keys = {"(被动)","(Q)","(W)","(E)","(R)"};

    private Banner banner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_information);

        Intent intent = getIntent();
        Hero hero = (Hero)intent.getSerializableExtra("hero");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        heroSkillName = (TextView) findViewById(R.id.skill_name);
        heroSkillInfo = (TextView) findViewById(R.id.skill_info);
        herobackgroundStory = (TextView) findViewById(R.id.hero_backgroundStroy);
        banner = (Banner) findViewById(R.id.hero_image_banner);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbar.setTitle(hero.getHeroDesignNation()+" "+hero.getHeroName());
        collapsingToolbar.setExpandedTitleColor(Color.argb(0, 0, 255, 0));

        String[] skinUrl = hero.getHeroSkinsUrl().split("@");
        for(String s : skinUrl){
            skinUrls.add(s);
        }

        String[] skillImageUrl = hero.getSkillImageUrl().split("@");
        for(String s : skillImageUrl){
            skillImageUrls.add(s);
            Log.d("hero","SkillImageUrl : " + s);
        }
        String[] skillName = hero.getSkillName().split("@");
        for(String s : skillName){
            skillNames.add(s);
            Log.d("hero","skillName : " + s);
        }
        String[] skillInfo = hero.getDetaildInfo().split("@");
        for(String s : skillInfo){
            skillInfos.add(s);
            Log.d("hero","skillInfo : " + s);
        }

        ImageButton btn1 = (ImageButton) findViewById(R.id.skill_image_1);
        ImageButton btn2 = (ImageButton) findViewById(R.id.skill_image_2);
        ImageButton btn3 = (ImageButton) findViewById(R.id.skill_image_3);
        ImageButton btn4 = (ImageButton) findViewById(R.id.skill_image_4);
        ImageButton btn5 = (ImageButton) findViewById(R.id.skill_image_5);

        imageButtons.add(btn1);
        imageButtons.add(btn2);
        imageButtons.add(btn3);
        imageButtons.add(btn4);
        imageButtons.add(btn5);

        for(int i = 0; i < imageButtons.size(); i++){
            Glide.with(this).load(skillImageUrls.get(i)).override(90,90).into(imageButtons.get(i));
            imageButtons.get(i).setOnClickListener(this);
        }
        heroSkillName.setText(skillNames.get(0) + shortcut_keys[0]);
        heroSkillInfo.setText(skillInfos.get(0));

        herobackgroundStory.setText(hero.getBackgroundStory());

        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(skinUrls);
        //banner.isAutoPlay(false);
        banner.start();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {
        for(ImageButton imageButton : imageButtons){
            imageButton.setBackgroundColor(R.color.noBackground);
        }
        switch (v.getId()){
            case R.id.skill_image_1:
                imageButtons.get(0).setBackgroundColor(R.color.background);
                heroSkillName.setText(skillNames.get(0) + shortcut_keys[0]);
                heroSkillInfo.setText(skillInfos.get(0));
                break;
            case R.id.skill_image_2:
                imageButtons.get(1).setBackgroundColor(R.color.background);
                heroSkillName.setText(skillNames.get(1) + shortcut_keys[1]);
                heroSkillInfo.setText(skillInfos.get(1));
                break;
            case R.id.skill_image_3:
                imageButtons.get(2).setBackgroundColor(R.color.background);
                heroSkillName.setText(skillNames.get(2) + shortcut_keys[2]);
                heroSkillInfo.setText(skillInfos.get(2));
                break;
            case R.id.skill_image_4:
                imageButtons.get(3).setBackgroundColor(R.color.background);
                heroSkillName.setText(skillNames.get(3) + shortcut_keys[3]);
                heroSkillInfo.setText(skillInfos.get(3));
                break;
            case R.id.skill_image_5:
                imageButtons.get(4).setBackgroundColor(R.color.background);
                heroSkillName.setText(skillNames.get(4) + shortcut_keys[4]);
                heroSkillInfo.setText(skillInfos.get(4));
                break;
        }
    }
}
