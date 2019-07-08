package util;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import bean.Hero;

public class XmlUtil {
    public static List<Hero> parseXMLWithPull(String str) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(str));
            int eventType = xmlPullParser.getEventType();
            String heroHeadSculptureUrl = "";
            String heroDesignNation = "";
            String heroName = "";
            String heroAttribute = "";
            String heroSkinsUrl = "";
            String backgroundStory = "";
            String skillImageUrl = "";
            String skillName = "";
            String detaildInfo = "";
            List<Hero> heroList = new ArrayList<>();

            while(eventType != XmlPullParser.END_DOCUMENT) {
                String nodeName = xmlPullParser.getName();
                switch(eventType) {
                    case XmlPullParser.START_TAG:
                        //Log.d("hero","Start Tag is " + nodeName);
                        if("heroHeadSculptureUrl".equals(nodeName)) {
                            heroHeadSculptureUrl = xmlPullParser.nextText();
                        }else if("designNation".equals(nodeName)) {
                            heroDesignNation = xmlPullParser.nextText();
                        }else if("heroName".equals(nodeName)) {
                            heroName = xmlPullParser.nextText();
                        }else if("heroAttribute".equals(nodeName)) {
                            while (true){
                                eventType = xmlPullParser.next();
                                nodeName = xmlPullParser.getName();
                                if("heroAttribute".equals(nodeName)){
                                    break;
                                }else if("string".equals(nodeName)){
                                    heroAttribute += xmlPullParser.nextText()+"/";
                                }
                            }
                            heroAttribute = heroAttribute.substring(0,heroAttribute.length()-1);
                        }else if("heroSkinsUrl".equals(nodeName)){
                            while (true){
                                eventType = xmlPullParser.next();
                                nodeName = xmlPullParser.getName();
                                if("heroSkinsUrl".equals(nodeName)){
                                    break;
                                }else if("string".equals(nodeName)){
                                    heroSkinsUrl += xmlPullParser.nextText() + "@";
                                }
                            }
                        }else if("backgroundStory".equals(nodeName)){
                            backgroundStory = xmlPullParser.nextText();
                        }else if("skillImageUrl".equals(nodeName)){
                            while (true){
                                eventType = xmlPullParser.next();
                                nodeName = xmlPullParser.getName();
                                if("skillImageUrl".equals(nodeName)){
                                    break;
                                }else if("string".equals(nodeName)){
                                    skillImageUrl += xmlPullParser.nextText() + "@";
                                }
                            }
                        }else if("skillName".equals(nodeName)){
                            while (true){
                                eventType = xmlPullParser.next();
                                nodeName = xmlPullParser.getName();
                                if("skillName".equals(nodeName)){
                                    break;
                                }else if("string".equals(nodeName)){
                                    skillName += xmlPullParser.nextText() + "@";
                                }
                            }
                        }else if ("detaildInfo".equals(nodeName)){
                            while (true){
                                eventType = xmlPullParser.next();
                                nodeName = xmlPullParser.getName();
                                if("detaildInfo".equals(nodeName)){
                                    break;
                                }else if("string".equals(nodeName)){
                                    detaildInfo += xmlPullParser.nextText() + "@";
                                }
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        //Log.d("hero","End Tag  is " + nodeName);
                        if("bean.HeroInfomation".equals(nodeName)) {
                            Hero hero = new Hero(heroHeadSculptureUrl,heroDesignNation,heroName,heroAttribute,heroSkinsUrl,backgroundStory,skillImageUrl,skillName,detaildInfo);
                            heroList.add(hero);
                            //Log.d("hero",hero.toString());
                            hero.save();
                            heroAttribute = "";
                            heroSkinsUrl = "";
                            skillImageUrl = "";
                            skillName = "";
                            detaildInfo = "";
                        }
                        break;
                }
                eventType = xmlPullParser.next();
            }
            return heroList;
        } catch (XmlPullParserException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
