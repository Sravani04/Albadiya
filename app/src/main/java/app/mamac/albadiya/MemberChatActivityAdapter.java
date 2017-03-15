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
 * Created by T on 15-03-2017.
 */

public class MemberChatActivityAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    ArrayList<Integer> mimages;
    ArrayList<String> mnames;

    protected MemberChatActivityAdapter(Context context,ArrayList<Integer> images,ArrayList<String> names){
        this.context = context;
        mimages = images;
        mnames = names;
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
        View item_view = inflater.inflate(R.layout.member_chat_list,null);
        ImageView user_image = (ImageView) item_view.findViewById(R.id.user_image);
        TextView  user_name = (TextView) item_view.findViewById(R.id.user_name);
        user_image.setImageResource(mimages.get(position));
        user_name.setText(mnames.get(position));
        return item_view;
    }
}
