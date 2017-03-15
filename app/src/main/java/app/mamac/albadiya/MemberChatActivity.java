package app.mamac.albadiya;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by T on 15-03-2017.
 */

public class MemberChatActivity extends Activity {
    ListView listView;
    MemberChatActivityAdapter memberChatActivityAdapter;
    ArrayList<Integer> images;
    ArrayList<String> names;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_chat_activity);
        listView = (ListView) findViewById(R.id.member_chats);
        images = new ArrayList<>();
        names  = new ArrayList<>();

        images.add(R.drawable.ic_profile);
        images.add(R.drawable.ic_profile);

        names.add("chaithu");
        names.add("samantha");

        memberChatActivityAdapter = new MemberChatActivityAdapter(this,images,names);
        listView.setAdapter(memberChatActivityAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }
}
