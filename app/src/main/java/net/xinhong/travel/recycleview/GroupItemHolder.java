package net.xinhong.travel.recycleview;

import android.view.View;
import android.widget.TextView;

import net.xinhong.travel.R;

/**
 * Created by mac on 2017/2/6.
 */
public class GroupItemHolder extends NormalItemHolder {
    TextView newsTime;

    public GroupItemHolder(View itemView) {
        super(itemView);

        newsTime = (TextView) itemView.findViewById(R.id.base_swipe_group_item_time);
    }
}
