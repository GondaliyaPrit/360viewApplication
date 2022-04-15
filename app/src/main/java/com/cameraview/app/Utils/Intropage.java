package com.cameraview.app.Utils;

public class Intropage {
    String title,intro;
    int pageimage;

    public Intropage(String title, String intro, int pageimage) {
        this.title = title;
        this.intro = intro;
        this.pageimage = pageimage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public int getPageimage() {
        return pageimage;
    }

    public void setPageimage(int pageimage) {
        this.pageimage = pageimage;
    }
}
