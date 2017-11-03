package net.xinhong.travel.recycleview;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.xinhong.travel.R;

import java.util.List;

/**
 * Created by mac on 2017/2/6.
 */
public class NewsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int NORMAL_ITEM = 0;
    private static final int GROUP_ITEM = 1;

    private Activity mContext;
    private List<String> mDataList;
    private LayoutInflater mLaayoutInflater;

    private NewsListAdapter(Activity mContext, List<String> mDataList) {
        this.mContext = mContext;
        this.mDataList = mDataList;

        mLaayoutInflater = LayoutInflater.from(mContext);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == NORMAL_ITEM) {
            return new NormalItemHolder(mLaayoutInflater.inflate(R.layout.card_item_notitle, parent, false));
        }else {
            return new GroupItemHolder(mLaayoutInflater.inflate(R.layout.card_item_title, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String value = mDataList.get(position);

        if (value == null) {
            return;
        }

        if (holder instanceof GroupItemHolder) {
            bindGroupItem(value, holder);
        }else {
            NormalItemHolder normalItemHolder = (NormalItemHolder) holder;
            bindNormalItem(value, normalItemHolder.newsTiele, normalItemHolder.newsIcon);
        }
    }

    private void bindNormalItem(String value, TextView newsTiele, ImageView newsIcon) {
    }

    private void bindGroupItem(String value, RecyclerView.ViewHolder holder) {
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return GROUP_ITEM;
        }


        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }
}
