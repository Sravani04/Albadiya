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
 * Created by T on 15-11-2016.
 */

public class LawyerAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    ArrayList<Packages> packages;

    public LawyerAdapter(Context context,ArrayList<Packages> packages){
      this.context=context;
        this.packages = packages;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return packages.size();
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
        View item_view = inflater.inflate(R.layout.lawyer_items,null);
        ImageView item_image = (ImageView) item_view.findViewById(R.id.add_image);
        TextView item_dec = (TextView) item_view.findViewById(R.id.item_dec);
        TextView item_title = (TextView) item_view.findViewById(R.id.item_title);
      //  item_image.setImageResource(mimages.get(position));
        item_dec.setText(packages.get(position).description);
        item_title.setText(packages.get(position).title +"  "+packages.get(position).price + "KD");
        return item_view;
    }
}
