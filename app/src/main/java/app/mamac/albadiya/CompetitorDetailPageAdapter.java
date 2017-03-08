package app.mamac.albadiya;

import android.content.Context;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

import javax.crypto.AEADBadTagException;

/**
 * Created by T on 19-12-2016.
 */

public class CompetitorDetailPageAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    Competitors competitors;
    ArrayList<Integer>  mbaners;


    protected CompetitorDetailPageAdapter(Context context,Competitors competitors){
        this.context = context;
        this.competitors = competitors;
        inflater = LayoutInflater.from(context);

    }
    @Override
    public int getCount() {
        return competitors.images.size();

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
        final View item_view = inflater.inflate(R.layout.competitors_detail_page_items,null);
        ImageView item_image = (ImageView) item_view.findViewById(R.id.item_image);
        Ion.with(context)
                .load(competitors.images.get(position).image)
                .withBitmap()
                .placeholder(R.drawable.ic_profile)
                .intoImageView(item_image);
      // item_image.setImageResource(mbaners.get(position));
        return item_view;
    }






}
