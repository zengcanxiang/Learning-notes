package com.zengcanxiang.learning_notes_animation;

/**
 * 列表显示属性
 */
public class ListBean {

    private String name;
    private String brief_introduction;
    private Class toClz;

    public ListBean(String name, String brief_introduction, Class toClz) {
        this.name = name;
        this.brief_introduction = brief_introduction;
        this.toClz = toClz;
    }

    public ListBean() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrief_introduction() {
        return brief_introduction;
    }

    public void setBrief_introduction(String brief_introduction) {
        this.brief_introduction = brief_introduction;
    }

    public Class getToClz() {
        return toClz;
    }

    public void setToClz(Class toClz) {
        this.toClz = toClz;
    }
}
