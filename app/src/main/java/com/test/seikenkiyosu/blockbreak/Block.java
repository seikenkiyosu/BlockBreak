package com.test.seikenkiyosu.blockbreak;

//import java.util.Random;

import java.util.Random;

public class Block {
    //存在するかどうか(ボールと触れたらfalseとなる)
    boolean exist;

    //point
    int point;

    //position
    float x1, y1, x2, y2;

    int length;
    int width;

    //color
    int r, g, b;

    //item
    int item;

    public Block(int Point, int Length, int Width){
        this.exist = true;

        this.point = Point;
        this.length = Length;
        this.width = Width;

        switch (this.point) {
            case 1:
                ColorChange(1);
                break;
            case 2:
                ColorChange(2);
                break;
            case 3:
                ColorChange(3);
                break;
        }

        //item の初期化
        Random R = new Random();
        if (R.nextInt(10) == 0) {
            item = 3;
        }
        else {
            item = 0;
        }

//        if(this.item != 0){
//
//        }
    }

    public void ColorChange(int color){
        switch (color) {
            case 1:
                //黄色
                this.r = 255;
                this.g = 255;
                this.b = 0;
                break;
            case 2:
                //浅緑
                this.r = 162;
                this.g = 219;
                this.b = 169;
                break;
            case 3:
                //シルバーグレイ
                this.r = 165;
                this.g = 165;
                this.b = 16;
                break;
            default:
                this.r = 255;
                this.g = 0;
                this.b = 0;
                break;
        }
    }

    public void ItemAction(Item item) {
        switch (this.item) {
            case 3:

                break;
            default:
                break;
        }
    }
}
