package net.xinhong.travel.animator;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import net.xinhong.travel.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mac on 2017/5/11.
 */

public class AnimatorActivity extends Activity {


    private static final String TAG = "AnimatorActivity";
    @BindView(R.id.ll_back)
    ListView llBack;
    @BindView(R.id.rl_root)
    RelativeLayout rlRoot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator);
        ButterKnife.bind(this);

        rlRoot.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.e(TAG, "rlroot ------- onTouch: ");
                return false;
            }
        });

        llBack.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.e(TAG, "llback ----- onTouch: " );
                return false;
            }

        });
    }
}



