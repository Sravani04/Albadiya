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
 * Created by T on 17-11-2016.
 */

public class LawyerPeopleAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    ArrayList<String> mheading;
    ArrayList<Integer> mimages;


    public LawyerPeopleAdapter(Context context,ArrayList<String> heading,ArrayList<Integer> images){
        this.context = context;
        mimages = images;
        mheading = heading;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return mheading.size();
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
        View item_view = inflater.inflate(R.layout.lawyers_info,null);
        TextView item_heading = (TextView) item_view.findViewById(R.id.item_heading);
        ImageView item_image = (ImageView) item_view.findViewById(R.id.add_image);
        item_heading.setText(mheading.get(position));
        item_image.setImageResource(mimages.get(position));
        return item_view;
    }
}
