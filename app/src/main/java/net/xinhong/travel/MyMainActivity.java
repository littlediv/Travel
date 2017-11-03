package net.xinhong.travel;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import net.xinhong.travel.utils.PasswordHelp;

import java.util.ArrayList;
import java.util.List;


public class MyMainActivity extends AppCompatActivity {


    private List<Integer> lists = new ArrayList<>();
    private MyRecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private LinearLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swipe_activity_main);
        initData();
        initviews();
    }

    private void initviews() {
//        recyclerView = (MyRecyclerView) findViewById(R.id.my_recycler);
//        adapter = new RecyclerAdapter(getApplicationContext(), lists);
//        manager = new LinearLayoutManager(getApplicationContext());
//        recyclerView.setLayoutManager(manager);
//        recyclerView.setAdapter(adapter);

        final EditText etBefore = (EditText) findViewById(R.id.et_before);
        Button btnEn = (Button) findViewById(R.id.btn_en);
        final TextView tvAfter = (TextView) findViewById(R.id.tv_after);
        TextView tvBeforeOther = (TextView) findViewById(R.id.tv_before_other);

        btnEn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trim = etBefore.getText().toString().trim();
                PasswordHelp.savePassword(MyMainActivity.this, "aaa", trim, true);

                String[] strings = PasswordHelp.readPassword(MyMainActivity.this);

                tvAfter.setText(strings[1]);

            }
        });

    }

    private void initData() {
//        for (int i = 1; i < 20; i++) {
//            lists.add(i);
//        }
    }


}
