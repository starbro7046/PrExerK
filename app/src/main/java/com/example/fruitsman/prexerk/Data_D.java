package com.example.fruitsman.prexerk;

public class Data_D {
    public Data_D(String name,String[] names,int set1,int set2){
        this.name=name;
        this.names=names;
        this.set1=set1;
        this.set2=set2;
    }
    private     //가독성을 위해 name(%d)대신 배열 사용
        String name;
        String[] names;
        int set1;
        int set2;

    public String getName() {
        return name;
    }
    public String[] getNames(){
        return names;
    }
    public int getSet1(){
        return set1;
    }
    public int getSet2(){
        return set2;
    }
    public void setName(String name){
        this.name=name;
    }
    public void setNames(String[] names){
        this.names=names;
    }
    public void setSet1(int set1){
        this.set1=set1;
    }
    public void setSet2(int set2){
        this.set2=set2;
    }
}