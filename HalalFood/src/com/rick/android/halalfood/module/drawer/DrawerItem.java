package com.rick.android.halalfood.module.drawer;

public class DrawerItem {

    private String title;
    private int imgResID;
    private int noticeNum;// 通知条数

    public DrawerItem() {
        this.noticeNum = 0;
    }

    public DrawerItem(String title, int imgResId) {
        this.title = title;
        this.imgResID = imgResId;
        this.noticeNum = 0;
    }

    public DrawerItem(String title, int imgResId, int noticeNum) {
        this.title = title;
        this.imgResID = imgResId;
        this.noticeNum = noticeNum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImgResID() {
        return imgResID;
    }

    public void setImgResID(int imgResID) {
        this.imgResID = imgResID;
    }

    public int getNoticeNum() {
        return noticeNum;
    }

    public void setNoticeNum(int noticeNum) {
        this.noticeNum = noticeNum;
    }

}
