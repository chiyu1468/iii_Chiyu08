package tw.org.iii.chiyu08;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import java.util.Timer;
import java.util.TimerTask;

public class GameView extends View {
    private Resources res1;

    public GameView(Context context1, AttributeSet attrs) {
        super(context1, attrs);
        //setBackgroundColor(Color.YELLOW);

        res1 = context1.getResources();

        // 建構式 還沒有成像 所以抓不到View的寬高
        int VeiwH,ViewW;
        VeiwH = getHeight();ViewW = getWidth();
        Log.v("chiyu",VeiwH + "x" + ViewW + " : GameView");

        // 4.
        t1 = new Timer();


    }




    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Bitmap 是安卓處理圖像 最根源的物件
        // BitmapFactory 是類別方法 幫我從 res 抓出要的檔案 變成可以存取的物件
        // 影音檔 都是用編碼的方式在處理的 所以要 decode / encode
        Bitmap Bm = BitmapFactory.decodeResource(res1,R.drawable.aa);
        pokemon = BitmapFactory.decodeResource(res1,R.drawable.b1);
        // 畫背景
        canvas.drawBitmap(Bm,0,0,null);

        // onDraw 成像後才能抓到 背景的寬高
        int VeiwH,ViewW;
        VeiwH = getHeight();ViewW = getWidth();
        Log.v("chiyu",VeiwH + "x" + ViewW + " : onDraw");

        // 3. 用View的寬高 將要擺進去的東西 先進行縮放
        if (isInit == false) Init();
        // 畫快龍
        canvas.drawBitmap(pokemon,70,70,null);
//        canvas.drawBitmap(pokemon,PMX,PMY,null);

    }


    // 3. 計算比例 並 進行縮放
    private boolean isInit;
    private Matrix m1,m2;
    private Bitmap pokemon;
    void Init() {
        // 抓 View的寬高
        int VeiwH,ViewW;
        VeiwH = getHeight();ViewW = getWidth();


        // 好用的陣列處理工具
        m1 = new Matrix();m2 = new Matrix();

        //
        float pokemonH, pokemonW;
        pokemonH = VeiwH / 6f; pokemonW = ViewW / 8f;
        m1.postScale(pokemonW/pokemon.getWidth(),pokemonH/pokemon.getHeight());
        pokemon = Bitmap.createBitmap(pokemon,0,0,pokemon.getWidth(),pokemon.getHeight(),m1,false);
//        m1.reset();


        // 第一次執行這行敘述句時  會new出PMTask() 物件
        // timer 的 schedule 在第一秒後 會執行 PMTask() 的 run 方法
        // 之後每 60ms 執行 PMTask() 的 run 方法
        t1.schedule(new PMTask(),1000,60);

        isInit = true;
    }

    Bitmap resizeBmp(Bitmap src, float width, float height){
        m2.reset();
        m2.postScale(width/src.getWidth(), height/src.getHeight());
        return Bitmap.createBitmap(src,0,0,src.getWidth(),src.getHeight(),m2,false);
    }


    // 為了動畫 做一個 Timer
    // timer 是另外一個執行緒(thread) 所以要注意後續處理 (見MainActivity的finish)
    Timer t1;
    int PMX=0,PMY=0;
    private class PMTask extends TimerTask{
        @Override
        public void run() {
            PMX += 10; PMY += 10;
            postInvalidate();
        }
    }


}
