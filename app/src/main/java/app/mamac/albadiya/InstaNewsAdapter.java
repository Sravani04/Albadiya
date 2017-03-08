package app.mamac.albadiya;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 * Created by T on 08-12-2016.
 */

public class InstaNewsAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    ArrayList<String> mitems;
    ArrayList<News> news;
    public InstaNewsAdapter(Context context,ArrayList<News> news){
        this.context = context;
        this.news = news;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return news.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item_view = inflater.inflate(R.layout.insta_news,null);
        TextView item_title = (TextView) item_view.findViewById(R.id.item_title);
        item_title.setText(news.get(position).title);
        ImageView item_image = (ImageView) item_view.findViewById(R.id.item_image);
        Ion.with(context)
                .load(news.get(position).image)
                .withBitmap()
                .placeholder(R.drawable.ic_profile)
                .intoImageView(item_image);
        TextView item_description = (TextView) item_view.findViewById(R.id.item_description);
        item_description.setText(news.get(position).description);

        return item_view;
    }
}
