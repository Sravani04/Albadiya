package app.mamac.albadiya;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by T on 23-12-2016.
 */

public class ChatScreenAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    ArrayList<Chats> chats;


    public ChatScreenAdapter(Context context,ArrayList<Chats> chats){
        this.context = context;
        this.chats = chats;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return chats.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class Holder
    {
        TextView chating_right,chating_left,chat_date_time;
        LinearLayout chat_right,chat_left,chat_ll_left;
        ImageView item_image;
        RelativeLayout chat_ll_right;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View item_view = inflater.inflate(R.layout.chatlist_item,null);
        Holder holder=new Holder();
        holder.chat_right=(LinearLayout) item_view.findViewById(R.id.chat_right);
        holder.chat_ll_right=(RelativeLayout) item_view.findViewById(R.id.chat_ll_right);
        holder.chat_left=(LinearLayout) item_view.findViewById(R.id.chat_left);
        holder.chat_ll_left=(LinearLayout) item_view.findViewById(R.id.chat_ll_left);
        holder.chating_right=(TextView) item_view.findViewById(R.id.chating_right);
        holder.chating_left=(TextView) item_view.findViewById(R.id.chating_left);
        holder.item_image = (ImageView) item_view.findViewById(R.id.item_image);
        holder.chat_date_time = (TextView) item_view.findViewById(R.id.chat_date_time);

//        TextView description = (TextView) item_view.findViewById(R.id.description);
//        description.setText(chats.get(position).description);


        if(chats.get(position).member_id.equals(Settings.GetUserId(context))) {
            holder.chat_left.setVisibility(View.GONE);
            holder.chat_right.setVisibility(View.VISIBLE);
            holder.item_image.setVisibility(View.GONE);
            holder.chating_right.setText(chats.get(position).description);
            holder.chat_date_time.setText(chats.get(position).date);
            Picasso.with(context).load(chats.get(position).file).into(holder.item_image);
            holder.chat_ll_right.setBackgroundResource(R.drawable.linearlayout_bg);
        } else {
            holder.chat_left.setVisibility(View.VISIBLE);
            holder.chat_right.setVisibility(View.GONE);
            holder.item_image.setVisibility(View.GONE);
            holder.chating_left.setText(chats.get(position).description);
            holder.chat_date_time.setText(chats.get(position).date);
            Picasso.with(context).load(chats.get(position).file).into(holder.item_image);
            holder.chat_ll_left.setBackgroundResource(R.drawable.linearlayout_bg);
        }

        if (chats.get(position).msg_type.equals("text")){
            holder.chating_right.setVisibility(View.VISIBLE);
        }else {
            holder.chating_right.setVisibility(View.GONE);
        }

        if (chats.get(position).msg_type.equals("file")){
            holder.item_image.setVisibility(View.VISIBLE);
        }else {
            holder.item_image.setVisibility(View.GONE);
        }

        return item_view;
    }
}
