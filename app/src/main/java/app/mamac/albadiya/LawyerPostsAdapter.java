package app.mamac.albadiya;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;



import java.util.ArrayList;

/**
 * Created by T on 26-11-2016.
 */

public class LawyerPostsAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    ArrayList<Posts> posts;



    public LawyerPostsAdapter(Context context, ArrayList<Posts> posts){
        this.context =context;
        this.posts = posts;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return posts.size();
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
        View item_view = inflater.inflate(R.layout.lawyerposts_listview,null);
        TextView item_title = (TextView) item_view.findViewById(R.id.item_title);
        TextView item_message = (TextView) item_view.findViewById(R.id.item_message);
        ImageView item_image = (ImageView) item_view.findViewById(R.id.add_image);
        TextView  item_date  = (TextView) item_view.findViewById(R.id.item_date);
        TextView add_post  = (TextView) item_view.findViewById(R.id.add_post);
        item_title.setText(posts.get(position).title);
       // item_message.setText(posts.get(position).message);
      //  item_date.setText(posts.get(position).date);
        //item_image.setImageResource(posts.get(position).image);

        return item_view;
    }
}
