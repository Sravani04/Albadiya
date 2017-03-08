package app.mamac.albadiya;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;



import java.util.ArrayList;

/**
 * Created by T on 08-12-2016.
 */

public class InstaChatsAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    ArrayList<String> mmessages;

    public InstaChatsAdapter(Context context,ArrayList<String> messages){
        this.context = context;
        mmessages = messages;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return mmessages.size();
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
        View item_view = inflater.inflate(R.layout.insta_chats,null);
        TextView item_chats = (TextView)item_view.findViewById(R.id.item_chats);
        item_chats.setText(mmessages.get(position));
        return item_view;
    }
}
