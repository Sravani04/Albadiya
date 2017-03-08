package app.mamac.albadiya;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by T on 03-01-2017.
 */

public class FollowingFragmentAdapter  extends BaseAdapter{
    LayoutInflater inflater;
    Context context;
    //ArrayList<Posts> mimages;
    ArrayList<Integer> mimages;
    ArrayList<String> mnames;
    ArrayList<Notifications> notifications;


    protected FollowingFragmentAdapter(Context context,ArrayList<Notifications> notifications){
//        mimages = images;
//        mnames = names;
        this.context = context;
        this.notifications = notifications;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return notifications.size();
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
        View item_view = inflater.inflate(R.layout.following_list,null);
        CircleImageView user_image = (CircleImageView) item_view.findViewById(R.id.user_image);
        Picasso.with(context).load(notifications.get(position).member_image).into(user_image);
        TextView user_name = (TextView) item_view.findViewById(R.id.user_name);
        user_name.setText(notifications.get(position).member_name);
        TextView follow_time = (TextView) item_view.findViewById(R.id.follow_time);
        //follow_time.setText(notifications.get(position).time);
        ImageView ic_member_image = (ImageView) item_view.findViewById(R.id.ic_member_image);
        TextView changing_text = (TextView) item_view.findViewById(R.id.changing_text);
        if (notifications.get(position).type.equals("Follow")){
            changing_text.setText("Started Following You");
        }else if (notifications.get(position).type.equals("Like")){
            changing_text.setText("Liked Your Post");
        }
        //TextView follow = (TextView) item_view.findViewById(R.id.follow);
        if (notifications.get(position).type.equals("Follow")){
           // Picasso.with(context).load(notifications.get(position).member_image).into(ic_member_image);
            ic_member_image.setVisibility(View.GONE);
//            follow.setVisibility(View.VISIBLE);
//            follow.setText(notifications.get(position).type);
        }else if (notifications.get(position).type.equals("Like")){
            Log.e("response",notifications.get(position).post_image);
            //follow.setVisibility(View.GONE);
            Picasso.with(context).load(notifications.get(position).post_image).into(ic_member_image);
        }
        return item_view;
    }
}
