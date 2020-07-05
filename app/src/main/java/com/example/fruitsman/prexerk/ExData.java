package com.example.fruitsman.prexerk;

import android.util.Log;

class ExData {
    int [] stanAngle=new int[8];
    int [][] dAngle=new int[10][9];
    int angleRange;
    int exCode;
    int dyLength;
    int set1;
    int set2;
    boolean useVoiceRecognition;
    final String[] poseName={"오른쪽 팔꿈치를","오른쪽 손목을","왼쪽 팔꿈치를","왼쪽 손목을","오른쪽 허벅지를","오른쪽 종아리를","왼쪽 허벅지를","왼쪽 종아리를"};
    //exCode: 0->static Exercise
    //1-> dynamic Exercise
    public String getKFeedBack(int userAngle,int index){
        int diff=(Math.abs(stanAngle[index]-userAngle));
        if(diff>180){
            diff-=180;
        }
        if(diff<=angleRange){
            //no feedback
            return "";
        }else{

         return (poseName[index]+" "+diff+"도 "+second(stanAngle[index],userAngle));
        }
    }public boolean isAccurate(int userAngle,int index){
        int diff=(Math.abs(stanAngle[index]-userAngle));
        if(diff>180){
            diff-=180;
        }
        if(diff<=angleRange){
            return true;
        }else{
            return false;
        }
    }
    /*
    private String first(int index,boolean stanIsBigger){
        if(exCode==0){
            switch (index){
                case
            }
        }else if(exCode==1){

            return exCode[index]
        }else if(exCode==2){

        }else{
            //error
            return "";
        }
    }
    */
    private String second(int stan,int angle){
        /*
            switch(index){
                case 1://reversed
                case 2:
                case 5:
                case 6:

                    break;
                case 3://normal
                case 4:
                case 7:
                case 8:
                    if(stanIsBigger){
                        return "올";
                    }else{
                        return "내";
                    }
                    break;
            }
            */
        if(0<=angle&&angle<=180){//왼쪽, 오른쪽 추가
            int stanLine=angle-180;//90,-90 코드추가
            if(90<=angle){
                if(stanLine<stan&&stan<angle){
                    //up
                    return "올";
                }else{
                    //down
                    return "내";
                }
            }else {
                if(stanLine<stan&&stan<angle){
                    return "내";//up
                }else{
                    return "올";//down
                }
            }
        }else if(-180<=angle&&angle<0){
            int stanLine=angle+180;
            if(angle<=-90){
                if(angle<stan&&stan<stanLine){
                    return "내";
                }else{
                    return "올";
                }
            }else{
                if(angle<stan&&stan<stanLine){
                    return "올";
                }else{
                    return "내";
                }
            }
        }else{
            //error
            return "error";
        }
    }
    public boolean isAccurateD(int userAngle,int i1,int i2){
        int diff=(Math.abs(dAngle[i1][i2]-userAngle));
        int angleRangeD=dAngle[i1][8];
        if(diff>180){
            diff-=180;
        }
        if(diff<=angleRangeD){
            return true;
        }else{
            return false;
        }
    }
    public String getDFeedBack(int i1,int i2,int userAngle){
        int diff=(Math.abs(dAngle[i1][i2]-userAngle));
        if(diff>180){
            diff-=180;
        }
        if(diff<=dAngle[i1][8]){
            //no feedback
            return "";
        }else {
            return (poseName[i2] + " " + diff + "도 " + second(dAngle[i1][i2], userAngle));
        }
    }
    public void setData(int []angle,int range){ //static
        DataHolder.dataInstance.angleRange=range;
        DataHolder.dataInstance.exCode=0;
        DataHolder.dataInstance.stanAngle=angle;
    }
    public void setDataD(int []angle,int index){

        DataHolder.dataInstance.exCode=1;
        DataHolder.dataInstance.dAngle[index]=angle;
    }
    static ExData getInstance() {
        return DataHolder.dataInstance;
    }
    public static class DataHolder{
        public static final ExData dataInstance=new ExData();

    }
}