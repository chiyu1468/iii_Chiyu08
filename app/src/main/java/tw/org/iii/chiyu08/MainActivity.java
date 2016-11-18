package tw.org.iii.chiyu08;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private GameView gv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 1. 自己new出物件實體 塞到 ContentView
//        ChiyuView CV = new ChiyuView(this);
//        setContentView(CV);

        // 2. 在 setContentView 自動做出 layout 所定義的 物件實體
        setContentView(R.layout.activity_main);



        gv = (GameView)findViewById(R.id.gameView);
    }

    @Override
    public void finish() {
        // Timer 使用之後 要處理掉 避免卡記憶體
        if(gv.t1 != null)
        {
            gv.t1.purge();
            gv.t1.cancel();
            gv.t1 = null;
        }

        super.finish();
    }
}
