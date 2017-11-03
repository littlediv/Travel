package net.xinhong.travel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.xinhong.travel.R;
import net.xinhong.travel.model.WeatherHourData;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mac on 2017/2/8.
 */
public class WeatherHourAdapter extends RecyclerView.Adapter<WeatherHourAdapter.ItemViewHoloder> {

    Context context;
    WeatherHourData data;
    int hourOfDay;

    private static final String TAG = "WeatherHourAdapter";
    private final LayoutInflater layoutInflater;

    public WeatherHourAdapter(Context context, WeatherHourData data, int hourOfDay) {
        this.context = context;
        this.data = data;
        this.hourOfDay = hourOfDay;

        Log.e(TAG, "WeatherHourAdapter: ");

        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public WeatherHourAdapter.ItemViewHoloder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = View.inflate(context, R.layout.rv_item_weather_hour, null);
        View view = layoutInflater.inflate(R.layout.rv_item_weather_hour, parent, false);
        return new ItemViewHoloder(view);
    }

    @Override
    public void onBindViewHolder(WeatherHourAdapter.ItemViewHoloder holder, int position) {
        TreeMap<Integer, String> ww = data.WDF;

        Set<Map.Entry<Integer, String>> entries = ww.entrySet();
        int j = 0;
        for (Map.Entry<Integer, String> entry : entries) {
            Log.e(TAG, "onBindViewHolder: " + + entry.getKey() + "  "+ entry.getValue() );
            if (j++ == position) {
                Integer key = entry.getKey();
                String value = entry.getValue();

                String hourTime;
                int hour = hourOfDay + key;
                if (hour > 24) {
                    hour = hour % 24;
                }

                if (hour < 10) {
                    hourTime = "0" + hour;
                }else {
                    hourTime = "" + hour;
                }

                holder.tvHourTime.setText(hourTime + "时");
                holder.tvHourWd.setText(value);

                Log.e(TAG, position + "  onBindViewHolder: " + holder.tvHourTime.getText() +"  " +holder.tvHourWd.getText());

                break;
            }
        }

    }

    @Override
    public int getItemCount() {
        return data.WDF.size();
    }

    class ItemViewHoloder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_hour_time)
        TextView tvHourTime;
        @BindView(R.id.iv_weather_icon)
        ImageView ivWeatherIcon;
        @BindView(R.id.tv_hour_tt)
        TextView tvHourTt;
        @BindView(R.id.tv_hour_vis)
        TextView tvHourVis;
        @BindView(R.id.tv_hour_wd)
        TextView tvHourWd;
        @BindView(R.id.tv_hour_ws)
        TextView tvHourWs;
        @BindView(R.id.tv_hour_cn)
        TextView tvHourCn;

        public ItemViewHoloder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            tvHourWd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, getAdapterPosition()+"", Toast.LENGTH_SHORT).show();
                }
            });

        }


    }
}
