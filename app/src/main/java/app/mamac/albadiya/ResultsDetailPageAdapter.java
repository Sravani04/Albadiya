package app.mamac.albadiya;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by T on 11-01-2017.
 */

public class ResultsDetailPageAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    Results results;
    ArrayList<Integer> mimages;
    ArrayList<String> mvotes;

    protected ResultsDetailPageAdapter (Context context,Results results){
//        mimages = images;
//        mvotes  = votes;
        this.results = results;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return results.posts.size();
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
        final View item_view = inflater.inflate(R.layout.results_detail_page_items,null);
        ImageView result_image = (ImageView) item_view.findViewById(R.id.result_image);
        //item_image.setImageResource(mimages.get(position));
        //votes_count.setText(mvotes.get(position));
//        Ion.with(context)
//                .load(results.posts.get(position).image)
//                .withBitmap()
//                .placeholder(R.drawable.placeholder)
//                .intoImageView(result_image);
        Picasso.with(context).load(results.posts.get(position).image).placeholder(R.drawable.placeholder).into(result_image);
        TextView result_title = (TextView) item_view.findViewById(R.id.result_title);
        result_title.setText(results.posts.get(position).title);
        TextView title = (TextView) item_view.findViewById(R.id.title);
        title.setText(results.posts.get(position).title);
        TextView item_description = (TextView) item_view.findViewById(R.id.item_description);
        item_description.setText(results.posts.get(position).description);
        return item_view;
    }
}
