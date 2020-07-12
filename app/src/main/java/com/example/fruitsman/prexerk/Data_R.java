package com.example.fruitsman.prexerk;

public class Data_R {
    public Data_R(String name,String[] exs){
        this.name=name;
        this.exs=exs;
    }
    private
            String name;
            String[] exs;

    public String getName() {
        return name;
    }
    public String[] getExs(){
        return exs;
    }

    public void setName(String name){
        this.name=name;
    }
    public void setExs(String[] exs){
        this.exs=exs;
    }

}
