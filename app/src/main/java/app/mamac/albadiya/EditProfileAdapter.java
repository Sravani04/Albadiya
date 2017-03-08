package app.mamac.albadiya;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by T on 12-12-2016.
 */

public class EditProfileAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    EditProfile editProfile;
    ArrayList<Posts> mimages;

    protected EditProfileAdapter(Context context,ArrayList<Posts> images){
        this.context = context;
        mimages = images;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return mimages.size();
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
        View item_view = inflater.inflate(R.layout.edit_profile_items,null);
        ImageView item_image = (ImageView) item_view.findViewById(R.id.item_image);
        //  item_image.setImageResource(mimages.get(position).image);
        Picasso.with(context).load(mimages.get(position).image).placeholder(R.drawable.ic_profile).into(item_image);
//        Ion.with(context)
//                .load(mimages.get(position).image)
//                .withBitmap()
//                .placeholder(R.drawable.ic_profile)
//                .intoImageView(item_image);

        return item_view;
    }


}
