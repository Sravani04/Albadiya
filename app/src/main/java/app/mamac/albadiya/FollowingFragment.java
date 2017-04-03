package app.mamac.albadiya;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.JsonArray;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 * Created by T on 12-12-2016.
 */

public class FollowingFragment extends Fragment {
    FollowingFragmentAdapter followingFragmentAdapter;
    ArrayList<Integer> images;
    ArrayList<String> names;
    ListView listView;
    ArrayList<Notifications> notificationsfrom_api;
    String type;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.following_listview,container,false);
        listView = (ListView) view.findViewById(R.id.following_items);

        images = new ArrayList<>();
        names = new ArrayList<>();
        notificationsfrom_api = new ArrayList<>();

        if(getArguments()!=null && getArguments().containsKey("type"))
           type = getArguments().getSerializable("type").toString();
        else
           type = getArguments().getSerializable("type").toString();

        images.add(R.drawable.ic_profile);
        images.add(R.drawable.ic_profile);
        images.add(R.drawable.ic_profile);

        names.add("banner");
        names.add("timeline");
        names.add("yellowsoft");

        followingFragmentAdapter = new FollowingFragmentAdapter(getActivity(),notificationsfrom_api);
        listView.setAdapter(followingFragmentAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });


        get_notifications();
        return view;

    }


    public void get_notifications(){
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Ion.with(getContext())
                .load(Settings.SERVER_URL+"notifications.php")
                .setBodyParameter("member_id",Settings.GetUserId(getContext()))
                .setBodyParameter("type",type)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        try {
                            if (progressDialog!=null)
                                progressDialog.dismiss();
                            Log.e("likeresponse",result.toString());
                            for (int i = 0; i < result.size(); i++) {
                                Notifications notifications = new Notifications(result.get(i).getAsJsonObject(), getActivity());
                                notificationsfrom_api.add(notifications);
                            }
                            followingFragmentAdapter.notifyDataSetChanged();
                        }catch (Exception e1){
                            e1.printStackTrace();
                        }

                    }
                });
    }
}

