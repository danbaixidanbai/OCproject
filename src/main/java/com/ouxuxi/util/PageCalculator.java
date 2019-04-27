package com.ouxuxi.util;

public class PageCalculator {
    public static int calculateRowIndex(int pageIndex,int pageSize){
        return (pageIndex>0)?(pageIndex-1)*pageSize:0;
    }
    public static int calculatePageCount(int count,int pageSize){
        int pageCount=count/pageSize;
        if(count%pageSize!=0){
            pageCount+=1;
        }
        return pageCount;
    }
}
