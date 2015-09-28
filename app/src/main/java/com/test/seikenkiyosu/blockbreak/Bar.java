package com.test.seikenkiyosu.blockbreak;

import android.content.Context;
import android.view.View;

public class Bar extends View{
    int length;
    int width;

//    public  Bar(Context cn){
//        super(cn);
//    }

    public Bar(Context cn, int l, int w){
        super(cn);
        length = l;
        width = w;
    }
}