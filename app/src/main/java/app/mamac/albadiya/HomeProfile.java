package app.mamac.albadiya;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 * Created by T on 07-12-2016.
 */

public class HomeProfile extends Fragment implements AbsListView.OnScrollListener {
    ListView listView;
    HomeProfileAdapter homeProfileAdapter;
    ArrayList<String> title;
    ArrayList<Integer> image;

    ArrayList<Posts> postsfrom_api;
    ImageView settings;
    ImageView chat_item;
    int pageno=1;
    private  int previouslast;
    UserProfileSelectedListner mCallback;


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        int lastitem = firstVisibleItem + visibleItemCount;
        if (lastitem == totalItemCount){
            if (previouslast!=lastitem){
                Log.e("result","last");
                pageno++;
                get_posts();
                previouslast = lastitem;
            }
        }

    }


    public interface UserProfileSelectedListner {

        public void onUserSelected(String member_id);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (UserProfileSelectedListner) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.home_fragment_items,container,false);
        listView = (ListView) view.findViewById(R.id.home_items);
        listView.setOnScrollListener(this);


        settings = (ImageView) view.findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsFragment settingsFragment = new SettingsFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment,settingsFragment).commit();
            }
        });

        chat_item = (ImageView) view.findViewById(R.id.chat_item);
        chat_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chats = new Intent(getActivity(),ChatScreen.class);
                startActivity(chats);
            }
        });

        title   = new ArrayList<>();
        image = new ArrayList<>();
        postsfrom_api = new ArrayList<>();


        title.add("Amazon");
        title.add("Yellowsoft");
        title.add("Yahoo");
        title.add("Snapdeal");


        image.add(R.drawable.banner);
        image.add(R.drawable.banner1);
        image.add(R.drawable.banner3);
        image.add(R.drawable.timeline);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Ion.with(getActivity())
                        .load(Settings.SERVER_URL + "view.php")
                        .setBodyParameter("post_id",postsfrom_api.get(position).id)
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                if (result.get("status").getAsString().equals("Success"))
                                    Toast.makeText(getActivity(),result.get("message").getAsString(),Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(getActivity(),result.get("message").getAsString(),Toast.LENGTH_SHORT).show();

                            }
                        });
//                PostDetailPageFragment postDetailPageFragment = new PostDetailPageFragment();
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("post",postsfrom_api.get(position));
//                postDetailPageFragment.setArguments(bundle);
//                getFragmentManager().beginTransaction().replace(R.id.fragment,postDetailPageFragment).commit();
                //Toast.makeText(getActivity(),title.get(position),Toast.LENGTH_SHORT).show();
            }
        });

        get_posts();
        return view;
    }


    private void get_posts() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Ion.with(this)
                .load(Settings.SERVER_URL+"posts.php")
                .setBodyParameter("member_id",Settings.GetUserId(getActivity()))
                .setBodyParameter("page",String.valueOf(pageno))
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {

                        // do stuff with the result or error
                        //{"status":"Failure","message":"Please Enter Your Type"}
                        if (progressDialog != null)
                            progressDialog.dismiss();
                        if (e != null) {
                            e.printStackTrace();
                        } else {
                            Log.e("response", String.valueOf(result.size()));

                            for (int i = 0; i < result.size(); i++) {
                                Posts posts = new Posts(result.get(i).getAsJsonObject(), getActivity());
                                postsfrom_api.add(posts);
                            }

                            homeProfileAdapter = new HomeProfileAdapter(getActivity(),postsfrom_api,HomeProfile.this);
                            listView.setAdapter(homeProfileAdapter);

                           // homeProfileAdapter.notifyDataSetChanged();

                        }

                    }
                });
    }


    public void go_to_user_profile(String member_id){
        mCallback.onUserSelected(member_id);

    }

    }
