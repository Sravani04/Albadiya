package app.mamac.albadiya;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by T on 03-01-2017.
 */

public class LikeFragmentAdapter  extends BaseAdapter{
    LayoutInflater inflater;
    Context context;
    ArrayList<Posts> mimages;
    ArrayList<Notifications> notifications;

    protected LikeFragmentAdapter(Context context,ArrayList<Notifications> notifications){
        this.notifications = notifications;
        this.context = context;
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
        View item_view = inflater.inflate(R.layout.like_fragment_list,null);
        CircleImageView user_image = (CircleImageView) item_view.findViewById(R.id.user_image);
        Picasso.with(context).load(notifications.get(position).member_image).into(user_image);
        TextView  user_name = (TextView) item_view.findViewById(R.id.user_name);
        user_name.setText(notifications.get(position).member_name);
        TextView follow_time = (TextView) item_view.findViewById(R.id.follow_time);
        follow_time.setText(notifications.get(position).time);
        TextView changing_text = (TextView) item_view.findViewById(R.id.changing_text);
        if (notifications.get(position).type.equals("Follow")){
            changing_text.setText("started following you");
        }else if (notifications.get(position).type.equals("Like")){
            changing_text.setText("liked your photo");
        }

        ImageView ic_member_image = (ImageView) item_view.findViewById(R.id.ic_member_image);
        if (notifications.get(position).type.equals("Follow")){
            Picasso.with(context).load(notifications.get(position).member_image).into(ic_member_image);
        }else if (notifications.get(position).type.equals("Like")){
            Picasso.with(context).load(notifications.get(position).post_image).into(ic_member_image);
        }

        return item_view;
    }
}
