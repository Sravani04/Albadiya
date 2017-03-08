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

public class InstaResultsAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    ArrayList<String> mname;
    ArrayList<Results> results;

    public InstaResultsAdapter(Context context,ArrayList<Results> results ){
        this.context = context;
        this.results = results;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return results.size();
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
        View item_view = inflater.inflate(R.layout.insta_results,null);
        TextView item_name = (TextView) item_view.findViewById(R.id.item_name);
        item_name.setText(results.get(position).title);
        CircleImageView item_image = (CircleImageView) item_view.findViewById(R.id.item_image);
         Picasso.with(context).load(results.get(position).image).placeholder(R.drawable.ic_profile).into(item_image);
//        Ion.with(context)
//                .load(results.get(position).image)
//                .withBitmap()
//                .placeholder(R.drawable.ic_profile)
//                .intoImageView(item_image);
        return item_view;
    }
}
