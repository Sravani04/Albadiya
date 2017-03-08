package app.mamac.albadiya;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by T on 13-02-2017.
 */

public class GalleryImagesAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    GalleryImageItems galleryImageItems;
    ArrayList<Posts> mimages;

    protected GalleryImagesAdapter(Context context,ArrayList<Posts> images){
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View item_view = inflater.inflate(R.layout.edit_profile_items,null);
        ImageView item_image = (ImageView) item_view.findViewById(R.id.item_image);
        ImageView  play_btn = (ImageView) item_view.findViewById(R.id.play_btn);
        //  item_image.setImageResource(mimages.get(position).image);
        Picasso.with(context).load(mimages.get(position).image).placeholder(R.drawable.ic_profile).into(item_image);
        if(mimages.get(position).video.equals("")){
            play_btn.setVisibility(View.GONE);
        }else{
            play_btn.setVisibility(View.VISIBLE);
        }
        play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VideoPlayerActivity.class);
                intent.putExtra("video", mimages.get(position).video);
                context.startActivity(intent);

            }
        });
        return item_view;
    }
}
