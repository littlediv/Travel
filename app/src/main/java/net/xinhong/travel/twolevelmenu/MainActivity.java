package net.xinhong.travel.twolevelmenu;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import net.xinhong.travel.R;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button showPopBtn;

    /**
     * popupwindow
     *
     */
    private PopupWindow mPopupWindow;

    /**
     * 二级菜单的根目录
     */
    private ListView rootListView;

    /**
     * 根目录的节点
     */
    private String[] roots = new String[] { "附近", "排序", "筛选" };

    /**
     * 子目录节点
     */
    private String[][] sub_items = new String[][] {
            new String[] { "海淀区", "西城区", "石景山区", "东城区", "朝阳区" },
            new String[] { "离我最近", "人气最高", "评价最好", "人均最低", "人均最高" },
            new String[] { "全部类型", "优惠券商户", "闪惠商户", "预约服务", "上门服务" } };
    private ListView subListView;

    /**
     * 弹出的popupWindow布局
     */
    private LinearLayout popupLayout;

    /**
     * 子目录的布局
     */
    private FrameLayout subLayout;

    /**
     * 根目录被选中的节点
     */
    private int selectedPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_level);
        showPopBtn = (Button) findViewById(R.id.btn1);
        showPopBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btn1:
                showPopBtn(ScreenUtils.getScreenWidth(MainActivity.this),
                        ScreenUtils.getScreenHeight(MainActivity.this));
                break;
            default:
                break;
        }
    }

    private void showPopBtn(int screenWidth, int screenHeight) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        popupLayout = (LinearLayout) inflater.inflate(
                R.layout.popupwindow_layout, null, false);
        rootListView = (ListView) popupLayout.findViewById(R.id.root_listview);
        final RootListViewAdapter adapter = new RootListViewAdapter(
                MainActivity.this);
        adapter.setItems(roots);
        rootListView.setAdapter(adapter);

        /**
         * 子popupWindow
         */
        subLayout = (FrameLayout) popupLayout.findViewById(R.id.sub_popupwindow);

        /**
         * 初始化subListview
         */
        subListView = (ListView) popupLayout.findViewById(R.id.sub_listview);

        /**
         * 弹出popupwindow时，二级菜单默认隐藏，当点击某项时，二级菜单再弹出
         */
        subLayout.setVisibility(View.INVISIBLE);

        /**
         * 初始化mPopupWindow
         */
        mPopupWindow = new PopupWindow(popupLayout, screenWidth,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);

        /**
         * 有了mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
         * 这句可以使点击popupwindow以外的区域时popupwindow自动消失 但这句必须放在showAsDropDown之前
         */
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());

        /**
         * popupwindow的位置，第一个参数表示位于哪个控件之下 第二个参数表示向左右方向的偏移量，正数表示向左偏移，负数表示向右偏移
         * 第三个参数表示向上下方向的偏移量，正数表示向下偏移，负数表示向上偏移
         *
         */
        mPopupWindow.showAsDropDown(showPopBtn, -5, 5);// 在控件下方显示popwindow

        mPopupWindow.update();

        rootListView
                .setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        // TODO Auto-generated method stub

                        /**
                         * 选中root某项时改变该ListView item的背景色
                         */
                        adapter.setSelectedPosition(position);
                        adapter.notifyDataSetInvalidated();

                        selectedPosition = position;

                        SubListViewAdapter subAdapter = new SubListViewAdapter(
                                MainActivity.this, sub_items, position);
                        subListView.setAdapter(subAdapter);

                        /**
                         * 选中某个根节点时，使显示相应的子目录可见
                         */
                        subLayout.setVisibility(View.VISIBLE);
                        subListView
                                .setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                    @Override
                                    public void onItemClick(
                                            AdapterView<?> parent, View view,
                                            int position, long id) {
                                        // TODO Auto-generated method stub
                                        popupLayout.setVisibility(View.GONE);
                                        Toast.makeText(
                                                MainActivity.this,
                                                sub_items[selectedPosition][position],
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });

                    }
                });
    }

}
