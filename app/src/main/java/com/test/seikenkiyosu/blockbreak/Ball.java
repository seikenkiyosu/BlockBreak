package com.test.seikenkiyosu.blockbreak;

import java.util.Random;

public class Ball {
    //exist
    boolean exist;

    //position
    float x,y;

    //velocity
    float dx,dy;

    //color
    int r,g,b;

    //size
    int size;

    public Ball(int minv, int maxv, int msx, int msy, int blocksize, int barposition, int nodesize){
        Random R = new Random();
        this.exist = true;
        this.x = R.nextInt(msx);
        this.y = R.nextInt(msy*(barposition-1)/barposition-blocksize)+blocksize;
//        this.y = R.nextInt((int)msy*(7/8)-blocksize) + blocksize;
//        dx = dy = v;
        if(R.nextBoolean()) {
//            this.dx = minv + R.nextInt(maxv-minv-1) + R.nextFloat();
            this.dx = minv + R.nextInt(maxv-minv) + R.nextFloat();
        }
        else {
            this.dx = -(minv + R.nextInt(maxv-minv)+ R.nextFloat());
        }
        this.dy = -(minv + R.nextInt(maxv-minv)+ R.nextFloat());

        //紅梅色
        this.r = 235;
        this.g = 121;
        this.b = 136;

//        色ランダム
//        this.r = R.nextInt(256);
//        this.g = R.nextInt(256);
//        this.b = R.nextInt(256);

        size = nodesize;
    }
}
