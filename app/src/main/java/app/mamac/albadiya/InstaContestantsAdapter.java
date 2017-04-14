package app.mamac.albadiya;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by T on 08-12-2016.
 */

public class InstaContestantsAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
//    ArrayList<Integer> mimages;
//    ArrayList<String>  mnames;
//    ArrayList<String>  mcomments;
    ArrayList<Competitors> competitors;
    ArrayList<Subscription> subscriptions;

    public   InstaContestantsAdapter(Context context,ArrayList<Competitors> competitors,ArrayList<Subscription> subscriptions){
        this.context = context;
        this.competitors =  competitors;
//        mimages = images;
//        mnames  = names;
//        mcomments = comments;
        this.subscriptions = subscriptions;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return competitors.size();
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
        View item_view = inflater.inflate(R.layout.insta_contestants,null);
        CircleImageView item_image = (CircleImageView) item_view.findViewById(R.id.item_image);
        TextView  item_name  = (TextView) item_view.findViewById(R.id.item_name);
        item_name.setText(competitors.get(position).title);
        Picasso.with(context).load(competitors.get(position).image).placeholder(R.drawable.ic_profile).into(item_image);
        TextView date = (TextView) item_view.findViewById(R.id.date);
        date.setText(competitors.get(position).end_date);
//        Ion.with(context)
//                .load(competitors.get(position).image)
//                .withBitmap()
//                .placeholder(R.drawable.ic_profile)
//                .intoImageView(item_image);
        return item_view;
    }
}
