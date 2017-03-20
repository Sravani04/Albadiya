package app.mamac.albadiya;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.JsonArray;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 * Created by T on 15-03-2017.
 */

public class MemberChatActivity extends Activity {
    ListView listView;
    MemberChatActivityAdapter memberChatActivityAdapter;
    ArrayList<Integer> images;
    ArrayList<String> names;
    ArrayList<ChatMember> chatmembersfrom_api;
    ImageView back_btn;
    String receiver_id;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_chat_activity);
        listView = (ListView) findViewById(R.id.member_chats);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MemberChatActivity.this.onBackPressed();
            }
        });

        if(getIntent()!=null && getIntent().hasExtra("receiver_id")){
            receiver_id=getIntent().getStringExtra("receiver_id");
            Log.e("messafe",receiver_id);
        }else{
            finish();
        }

        images = new ArrayList<>();
        names  = new ArrayList<>();
        chatmembersfrom_api = new ArrayList<>();

        images.add(R.drawable.ic_profile);
        images.add(R.drawable.ic_profile);

        names.add("chaithu");
        names.add("samantha");

        memberChatActivityAdapter = new MemberChatActivityAdapter(this,chatmembersfrom_api);
        listView.setAdapter(memberChatActivityAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MemberChatActivity.this,MessagesActivity.class);
                intent.putExtra("id",chatmembersfrom_api.get(position).id);
                intent.putExtra("name",chatmembersfrom_api.get(position).name);
                intent.putExtra("image",chatmembersfrom_api.get(position).image);
                startActivity(intent);
            }
        });

        get_chats_member();

    }

    public void get_chats_member(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Ion.with(this)
                .load("http://naqshapp.com/albadiya/api/chats-member.php")
                .setBodyParameter("member_id",Settings.GetUserId(this))
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        if (progressDialog!=null)
                            progressDialog.dismiss();
                        Log.e("chats",result.toString());
                        for (int i = 0; i < result.size(); i++) {
                            ChatMember chatMember = new ChatMember(result.get(i).getAsJsonObject(), MemberChatActivity.this);
                            chatmembersfrom_api.add(chatMember);
                        }
                        memberChatActivityAdapter.notifyDataSetChanged();

                    }
                });
    }
}
