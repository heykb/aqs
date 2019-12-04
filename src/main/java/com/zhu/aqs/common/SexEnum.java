package com.zhu.aqs.common;

public enum SexEnum {

    MAN("男"),WOMEN("女");
    private String value;
    SexEnum(String value){
        this.value=value;
    }
    public String value(){
        return value;
    }
    public static SexEnum getInstance(int i){
        for(SexEnum sexEnum:SexEnum.values()){
            if(sexEnum.ordinal()==i){
                return sexEnum;
            }
        }
        return null;
    }
}
