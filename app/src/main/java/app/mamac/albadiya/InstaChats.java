package app.mamac.albadiya;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;



import java.util.ArrayList;

/**
 * Created by T on 08-12-2016.
 */

public class InstaChats extends Fragment {
    ListView listView;
    InstaChatsAdapter instaChatsAdapter;
    ArrayList<String> messages;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.insta_chats_items,container,false);
        listView = (ListView) view.findViewById(R.id.chat_items);

        messages = new ArrayList<>();

        messages.add("");
        messages.add("");
        messages.add("");
        messages.add("");
        messages.add("");
        messages.add("");
        messages.add("");
        messages.add("");
        messages.add("");
        messages.add("");
        messages.add("");
        messages.add("");
        messages.add("");
        messages.add("");
        messages.add("");
        messages.add("");



        instaChatsAdapter = new InstaChatsAdapter(getActivity(),messages);
        listView.setAdapter(instaChatsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(),messages.get(position),Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
