package net.xinhong.travel.conflict;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;

import net.xinhong.travel.R;

/**
 * Created by mac on 2017/2/13.
 */
public class ConflictActivity extends AppCompatActivity {


    ScrollView scrollView1;

    InnerScrollView innerScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conflict);
        scrollView1 = (ScrollView)findViewById(R.id.scroll_1);
        innerScrollView = (InnerScrollView)findViewById(R.id.scroll_2);
        innerScrollView.parentScrollView = scrollView1;
        final Button button = (Button)innerScrollView.findViewById(R.id.scroll_button2);
        final View content = findViewById(R.id.scroll_content);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (content.getVisibility() == View.VISIBLE) {
                    innerScrollView.resume();
                    content.setVisibility(View.GONE);
                } else {
                    innerScrollView.scrollTo(v);
                    content.setVisibility(View.VISIBLE);
                }
            }
        });

    }
}
