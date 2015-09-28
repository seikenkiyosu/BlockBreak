package com.test.seikenkiyosu.blockbreak;

import android.app.*;
import android.graphics.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class MainActivity extends Activity implements Runnable {

    BlockBreakView bbv;
    Handler hn;
    LinearLayout ll;   //レイアウト初期化

    /* アプリ起動時に実行 */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //タイトルを消す
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //レイアウト初期化
        ll = new LinearLayout(this);
        setContentView(ll);

        //携帯の大きさを取得するための処理
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display dp = wm.getDefaultDisplay();
        Point point = new Point();
        dp.getSize(point);

        hn = new Handler();
        hn.postDelayed(this, 1);
        bbv = new BlockBreakView(this, point.x, point.y);
        ll.addView(bbv);
    }

    /* postDelayed によって実行 */
    public void run() {
        bbv.Move();  //for all ボールの反射を管理

        bbv.invalidate();  //再描画
        if(!bbv.IsComplete()) {
            hn.postDelayed(this, 1);   //繰り返しrun の呼び出し(右の数字は間隔)
        }
        else {
//            TextView tv = new TextView(this);
//            tv.setTextColor(Color.RED);
//            tv.setText("YOU DID IT!!");
//            ll.addView(tv);
        }
    }

    /* アプリ終了時に実行 */
    public void onDestroy() {
        super.onDestroy();
        hn.removeCallbacks(this);
    }
}
