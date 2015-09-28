package com.test.seikenkiyosu.blockbreak;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class BlockBreakView extends View {
    /* Ball info */
    public static final int BALLNUM = 3;
    public static final int BALLSIZE = 30;
    public static final int BALLMINV = 11;
    public static final int BALLMAXV = 12;

    /* Bar info */
    public static final int BARLENGTH = 250;
    public static final int BARWIDTH = 20;
    public static final int BARPOSITION = 12;    //(BARPOSITION-1)/BARPOSITOの位置(5以外が未完成)
    public static final int BARDIVISIONNUM = 10;

    /* Block info */
    public static final int BLOCKROW = 8;
    public static final int BLOCKCOLUMN = 3;
    public static final int BLOCKWIDTH = 80;


    int mobilesizex, mobilesizey;   //携帯のサイズ
    float barx, bary;     //Barの位置
    Ball ball[] = new Ball[BALLNUM];
    Bar bar;
    Block block[][] = new Block[BLOCKROW][BLOCKCOLUMN];
    Paint p = new Paint();

//    public BlockBreakView(Context cn){
//        super(cn);
//    }

    public BlockBreakView(Context cn, int msx, int msy) {
        super(cn);
        mobilesizex = msx;
        mobilesizey = msy;

        /* Ballの初期化 */
        for (int i = 0; i < BALLNUM; i++) {
            ball[i] = new Ball(BALLMINV, BALLMAXV, mobilesizex, mobilesizey, BARPOSITION, BLOCKWIDTH*BLOCKCOLUMN, BALLSIZE);
        }

        /* Barの初期化 */
        barx = mobilesizex / 2;
        bary = mobilesizey * (BARPOSITION-1)/BARPOSITION;
        bar = new Bar(cn, BARLENGTH, BARWIDTH);

        /* Blockの初期化 */
        for (int i = 0; i < BLOCKROW; i++)
            for (int j = 0; j < BLOCKCOLUMN; j++) {
                if (j<BLOCKCOLUMN/3) { block[i][j] = new Block(3, mobilesizex / BLOCKROW, BLOCKWIDTH); }
                else if (j<BLOCKCOLUMN*2/3) { block[i][j] = new Block(2, mobilesizex / BLOCKROW, BLOCKWIDTH); }
                else { block[i][j] = new Block(1, mobilesizex / BLOCKROW, BLOCKWIDTH); }
                block[i][j].x1 = mobilesizex / BLOCKROW * i;
                block[i][j].y1 = block[i][j].width * j;
                block[i][j].x2 = mobilesizex / BLOCKROW * i + block[i][j].length;
                block[i][j].y2 = block[i][j].width * (j + 1);
            }
    }

    /* 描画するときの処理 */
    protected void onDraw(Canvas cs) {
        super.onDraw(cs);

        //ボールを描画するための処理
        for (int i = 0; i < BALLNUM; i++) {
            if (ball[i].exist) {
                p.setColor(Color.rgb(ball[i].r, ball[i].g, ball[i].b));
                p.setStyle(Paint.Style.FILL);
                cs.drawCircle(ball[i].x, ball[i].y, ball[i].size, p); //10は大きさ
            }
        }

        //バーを描画するための処理
        p.setColor(Color.GRAY);
        p.setStyle(Paint.Style.FILL);
        cs.drawRect(barx, bary, barx + bar.length, bary + bar.width, p);

        //ブロックを描画するための処理
        for (int i = 0; i < BLOCKROW; i++) {
            for (int j = 0; j < BLOCKCOLUMN; j++) {
                if (block[i][j].exist) {
                    p.setColor(Color.rgb(block[i][j].r, block[i][j].g, block[i][j].b));
                    p.setStyle(Paint.Style.FILL);
                    cs.drawRect(block[i][j].x1, block[i][j].y1, block[i][j].x2, block[i][j].y2, p);
                }
            }
        }
    }

    /* タッチしたときの処理 */
    public boolean onTouchEvent(MotionEvent e) {
        barx = e.getX();  //Barを動かす
        this.invalidate();  //再描画(onDrawを再度呼び出す)
        return true;
    }

    /* ボールを動かすための処理 */
    public void Move() {
        for (int i = 0; i < BALLNUM; i++) {
            //反射のための処理(端に衝突)
            if (ball[i].x < 0 || ball[i].x > mobilesizex) {
                ball[i].dx = -ball[i].dx;
            }
            if (ball[i].y > mobilesizey) {
                ball[i].exist = false;
            }
            //天井
            if (ball[i].y < 0) {
                ball[i].dy = -ball[i].dy;
            }


            //反射のための処理(バーに衝突)
            if (barx <= ball[i].x && ball[i].x <= barx + bar.length
                    && bary <= ball[i].y && ball[i].y <= bary + bar.width/2) {
                for (int j=0; j<BARDIVISIONNUM; j++){
                    if (barx + j*bar.length/BARDIVISIONNUM <= ball[i].x && ball[i].x <= barx + (BARDIVISIONNUM-j-1)*bar.length/BARDIVISIONNUM) {
                        ball[i].dx *= (1.0-1.0/(float)BARDIVISIONNUM);
                    }
                    if ((j==0 && ball[i].dx>0)||(j==BLOCKCOLUMN-1 && ball[i].dx<0)){ ball[i].dx = -ball[i].dx; break; }
                    if(j < BARDIVISIONNUM/2) {
                        if (barx + j * bar.length / BARDIVISIONNUM <= ball[i].x && ball[i].x <= barx + (BARDIVISIONNUM - j - 1) * bar.length / BARDIVISIONNUM) {
                            ball[i].dx *= (1.0 - 1.0 / (float) BARDIVISIONNUM);
                            break;
                        }
                    }
                    else {
                        if (barx + j * bar.length / BARDIVISIONNUM <= ball[i].x && ball[i].x <= barx + (BARDIVISIONNUM - j - 1) * bar.length / BARDIVISIONNUM) {
                            ball[i].dx *= (1.0 - 1.0 / (float) BARDIVISIONNUM);
                            break;
                        }
                    }
                }
//                //左端に当たったとき
//                if (barx <= ball[i].x && ball[i].x <= barx + bar.length/BARDIVISIONNUM) {
//                    if(ball[i].dx==0){
//                        ball[i].dx += -ball[i].dy/2; }
//                    else if(
//                            ball[i].dx<0){ ball[i].dx *= 0.9; }
//                    else { ball[i].dx = -ball[i].dx; }
//                }
//                //左から二番目に当たったとき
//                if (barx + bar.length/BARDIVISIONNUM <= ball[i].x && ball[i].x <= barx + 2*bar.length/BARDIVISIONNUM){
//                    if(ball[i].dx==0){ ball[i].dx += -ball[i].dy/3; }
//                    else if(ball[i].dx<0){ ball[i].dx *= 0.7; }
//                    else { ball[i].dx *= 0.8; }
//                }
//                //右から二番目に当たったとき
//                if (barx + 3*bar.length/BARDIVISIONNUM <= ball[i].x && ball[i].x <= barx + 4*bar.length/BARDIVISIONNUM){
//                    if(ball[i].dx==0){ ball[i].dx += ball[i].dy/3; }
//                    else if(ball[i].dx>0){ ball[i].dx *= 0.7; }
//                    else { ball[i].dx *= 0.8; }
//                }
//                //右端に当たったとき
//                if (barx+bar.length*(BARDIVISIONNUM-1)/BARDIVISIONNUM <= ball[i].x) {
//                    if(ball[i].dx==0){ ball[i].dx += ball[i].dy/2; }
//                    else if(ball[i].dx>0){ ball[i].dx *= 0.9; }
//                    else { ball[i].dx = -ball[i].dx; }
//                }

                //縦の反射（反射のときは全部）
                ball[i].dy = -ball[i].dy;
            }

            //反射のための処理(ブロックに衝突)
            for (int j = 0; j < BLOCKROW; j++)
                for (int k = 0; k < BLOCKCOLUMN; k++){
                    if(block[j][k].exist) {
                        //左右から衝突
                        if (block[j][k].x1 <= ball[i].x + ball[i].dx && ball[i].x + ball[i].dx <= block[j][k].x2
                                && block[j][k].y1 <= ball[i].y && ball[i].y <= block[j][k].y2) {
                            ball[i].dx = -ball[i].dx;
                            block[j][k].point--; block[j][k].ColorChange(block[j][k].point);
                            if(block[j][k].point == 0) {
//                                block[i][j].ItemAction();
                                block[j][k].exist = false;
                            }
                        }
                        //左右から衝突
                        if (block[j][k].x1 <= ball[i].x && ball[i].x <= block[j][k].x2
                                && block[j][k].y1 <= ball[i].y + ball[i].dy && ball[i].y + ball[i].dy <= block[j][k].y2) {
                            ball[i].dy = -ball[i].dy;
                            block[j][k].point--; block[j][k].ColorChange(block[j][k].point);
                            if(block[j][k].point == 0) {
//                                block[i][j].ItemAction();
                                block[j][k].exist = false;
                            }
                        }
                    }
                }

            //ボールを動かす
            ball[i].x += ball[i].dx;
            ball[i].y += ball[i].dy;
        }
    }

    //ステージにクリアしたか否かを示す
    boolean IsComplete() {
       boolean iscomplete = true;
       for (int i = 0; i < BLOCKROW; i++)
           for (int j = 0; j < BLOCKCOLUMN; j++) {
                if(block[i][j].exist) iscomplete = false;
           }
       return iscomplete;
   }
}




