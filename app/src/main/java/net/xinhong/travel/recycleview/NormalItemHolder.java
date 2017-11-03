package net.xinhong.travel.recycleview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.xinhong.travel.R;

/**
 * Created by mac on 2017/2/6.
 */
public class NormalItemHolder extends RecyclerView.ViewHolder {

    TextView newsTiele;
    ImageView newsIcon;

    public NormalItemHolder(View itemView) {
        super(itemView);

        newsTiele = (TextView) itemView.findViewById(R.id.base_swipe_item_title);
        newsIcon = (ImageView) itemView.findViewById(R.id.base_swipe_item_icon);

        itemView.findViewById(R.id.base_swipe_item_container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNewDeail(getAdapterPosition());
            }
        });




















    }

    private void showNewDeail(int adapterPosition) {


    }
}
