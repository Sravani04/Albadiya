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
 * Created by T on 11-11-2016.
 */

public class GridviewAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    ArrayList<String> mnames;
    ArrayList<Integer> mimages;
    public GridviewAdapter(Context context,ArrayList<String> names,ArrayList<Integer> images){
        this.context = context;
        mnames = names;
        mimages = images;
        inflater = LayoutInflater.from(context);


    }
    @Override
    public int getCount() {
        return mnames.size();
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
        View item_view = inflater.inflate(R.layout.gridview_lists,null);
        TextView item_name = (TextView) item_view.findViewById(R.id.item_name);
        ImageView item_image = (ImageView) item_view.findViewById(R.id.add_image);
        item_name.setText(mnames.get(position));
        item_image.setImageResource(mimages.get(position));
        return item_view;


    }
}
