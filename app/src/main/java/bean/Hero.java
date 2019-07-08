package bean;

import org.litepal.crud.DataSupport;

import java.io.Serializable;


public class Hero extends DataSupport implements Serializable {

    private int id;

    private String heroHeadSculptureUrl;

    private String heroDesignNation;

    private String heroName;

    private String heroAttribute;

    private String heroSkinsUrl;

    private String backgroundStory;

    private String skillImageUrl;

    private String skillName;

    private String detaildInfo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeroHeadSculptureUrl() {
        return heroHeadSculptureUrl;
    }

    public void setHeroHeadSculptureUrl(String heroHeadSculptureUrl) {
        this.heroHeadSculptureUrl = heroHeadSculptureUrl;
    }

    public String getHeroDesignNation() {
        return heroDesignNation;
    }

    public void setHeroDesignNation(String heroDesignNation) {
        this.heroDesignNation = heroDesignNation;
    }

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }

    public String getHeroAttribute() {
        return heroAttribute;
    }

    public void setHeroAttribute(String heroAttribute) {
        this.heroAttribute = heroAttribute;
    }

    public String getHeroSkinsUrl() {
        return heroSkinsUrl;
    }

    public void setHeroSkinsUrl(String heroSkinsUrl) {
        this.heroSkinsUrl = heroSkinsUrl;
    }

    public String getBackgroundStory() {
        return backgroundStory;
    }

    public void setBackgroundStory(String backgroundStory) {
        this.backgroundStory = backgroundStory;
    }

    public String getSkillImageUrl() {
        return skillImageUrl;
    }

    public void setSkillImageUrl(String skillImageUrl) {
        this.skillImageUrl = skillImageUrl;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getDetaildInfo() {
        return detaildInfo;
    }

    public void setDetaildInfo(String detaildInfo) {
        this.detaildInfo = detaildInfo;
    }

    public Hero(String heroHeadSculptureUrl, String heroDesignNation, String heroName, String heroAttribute, String heroSkinsUrl, String backgroundStory, String skillImageUrl, String skillName, String detaildInfo) {
        this.heroHeadSculptureUrl = heroHeadSculptureUrl;
        this.heroDesignNation = heroDesignNation;
        this.heroName = heroName;
        this.heroAttribute = heroAttribute;
        this.heroSkinsUrl = heroSkinsUrl;
        this.backgroundStory = backgroundStory;
        this.skillImageUrl = skillImageUrl;
        this.skillName = skillName;
        this.detaildInfo = detaildInfo;
    }

    @Override
    public String toString() {
        return "Hero{" +
                "id=" + id +
                ", heroHeadSculptureUrl='" + heroHeadSculptureUrl + '\'' +
                ", heroDesignNation='" + heroDesignNation + '\'' +
                ", heroName='" + heroName + '\'' +
                ", heroAttribute='" + heroAttribute + '\'' +
                ", heroSkinsUrl=" + heroSkinsUrl +
                ", backgroundStory='" + backgroundStory + '\'' +
                ", skillImageUrl=" + skillImageUrl +
                ", skillName=" + skillName +
                ", detaildInfo=" + detaildInfo +
                '}';
    }
}
