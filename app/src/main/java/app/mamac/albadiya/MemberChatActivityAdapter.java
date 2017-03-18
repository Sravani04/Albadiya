package app.mamac.albadiya;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by T on 15-03-2017.
 */

public class MemberChatActivityAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    ArrayList<Integer> mimages;
    ArrayList<String> mnames;
    ArrayList<ChatMember> chatMembers;

    protected MemberChatActivityAdapter(Context context,ArrayList<ChatMember> chatMembers){
        this.context = context;
//        mimages = images;
//        mnames = names;
        this.chatMembers = chatMembers;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return chatMembers.size();
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
        View item_view = inflater.inflate(R.layout.member_chat_list,null);
        CircleImageView user_image = (CircleImageView) item_view.findViewById(R.id.user_image);
        LinearLayout count = (LinearLayout) item_view.findViewById(R.id.count);
        TextView  user_name = (TextView) item_view.findViewById(R.id.user_name);
        TextView  description  = (TextView) item_view.findViewById(R.id.description);
        TextView msg_count = (TextView) item_view.findViewById(R.id.msg_count);
        TextView date = (TextView) item_view.findViewById(R.id.date);
        //user_image.setImageResource(mimages.get(position));
        //user_name.setText(mnames.get(position));
        Picasso.with(context).load(chatMembers.get(position).image).placeholder(R.drawable.ic_profile).into(user_image);
        user_name.setText(chatMembers.get(position).name);
        description.setText(chatMembers.get(position).message);
        msg_count.setText(chatMembers.get(position).count);

        if(chatMembers.get(position).count.equals("0"))
            count.setVisibility(View.GONE);
        else
           count.setVisibility(View.VISIBLE);

        return item_view;
    }
}
