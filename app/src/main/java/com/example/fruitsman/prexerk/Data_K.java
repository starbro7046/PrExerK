package com.example.fruitsman.prexerk;

public class Data_K {
    public Data_K(String name,int left1,int left2,int right1,int right2,int left3,int left4,int right3,int right4,int time,int accuracy){
        this.name=name;
        this.left1=left1;
        this.left2=left2;
        this.right1=right1;
        this.right2=right2;
        this.left3=left3;
        this.left4=left4;
        this.right3=right3;
        this.right4=right4;
        this.time=time;
        this.accuracy=accuracy;
    }
    private
        String name;
        int right1;
        int right2;
        int left1;
        int left2;
        int right3;
        int right4;
        int left3;
        int left4;
        int pose;
        int time;
        int accuracy;

    public String getName() {
        return name;
    }
    public int getLeft1() {
        return left1;
    }
    public int getLeft2() {
        return left2;
    }
    public int getLeft3() {
        return left3;
    }
    public int getLeft4() {
        return left4;
    }
    public int getRight1() {
        return right1;
    }

    public int getRight2() {
        return right2;
    }

    public int getRight3() {
        return right3;
    }

    public int getRight4() {
        return right4;
    }

    public int getPose() {
        return pose;
    }
    public int getTime() {
        return time;
    }
    public int getAccuracy() {
        return accuracy;
    }
    public void setRight1(int right1) {
        this.right1 = right1;
    }
    public void setRight2(int right2) {
        this.right2 = right2;
    }
    public void setRight3(int right3) {
        this.right3 = right3;
    }
    public void setRight4(int right4) {
        this.right4 = right4;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPose(int pose) {
        this.pose = pose;
    }
    public void setLeft1(int left1) {
        this.left1 = left1;
    }
    public void setLeft2(int left2) {
        this.left2 = left2;
    }
    public void setLeft3(int left3) {
        this.left3 = left3;
    }
    public void setLeft4(int left4) {
        this.left4 = left4;
    }
    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }
    public void setTime(int time) {
        this.time = time;
    }
}

