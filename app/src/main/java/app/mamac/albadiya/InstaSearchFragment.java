package app.mamac.albadiya;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import com.google.gson.JsonArray;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

import im.ene.toro.sample.feature.facebook.AlbadiyaTimelineFragment;

/**
 * Created by T on 07-12-2016.
 */

public class InstaSearchFragment extends Fragment {
    GridView gridView;
    InstaMembersAdapter instaSearchAdapter;
    ArrayList<Integer> images;
    ArrayList<String> title;
    ArrayList<Members> membersfrom_api;
    AlbadiyaTimelineFragment.UserProfileSelectedListner mCallback;
    EditText item_search;
    public interface UserProfileSelectedListner {

        public void onUserSelected(String member_id);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (AlbadiyaTimelineFragment.UserProfileSelectedListner) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.instasearch_items,container,false);
        gridView  = (GridView) view.findViewById(R.id.insta_items);
        item_search = (EditText) view.findViewById(R.id.item_search);
        images = new ArrayList<>();
        title  = new ArrayList<>();
        membersfrom_api = new ArrayList<>();

        images.add(R.drawable.amazon);
        images.add(R.drawable.flipkart);
        images.add(R.drawable.timeline);
        images.add(R.drawable.snapdeal);
        images.add(R.drawable.yellowsoft);

        title.add("amazon");
        title.add("flipkart");
        title.add("timeline");
        title.add("snapdeal");
        title.add("yellowsoft");



        instaSearchAdapter  = new InstaMembersAdapter(getActivity(),membersfrom_api);
        gridView.setAdapter(instaSearchAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getActivity(),title.get(position),Toast.LENGTH_SHORT).show();
                mCallback.onUserSelected(membersfrom_api.get(position).id);

            }
        });

        item_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.e("search_word",charSequence.toString());
                instaSearchAdapter.getFilter().filter(charSequence);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
       get_members();
        return view;
    }

    public void get_members(){
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Ion.with(this)
                .load(Settings.SERVER_URL+"members.php")
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        if (progressDialog!=null)
                            progressDialog.dismiss();
                        if (e != null) {
                            e.printStackTrace();
                        } else {
                            Log.e("response", String.valueOf(result.size()));
                            for (int i = 0; i < result.size(); i++) {
                                Members members = new Members(result.get(i).getAsJsonObject(), getActivity());
                                membersfrom_api.add(members);
                            }

                            instaSearchAdapter.notifyDataSetChanged();

                        }

                    }
                });
    }



}
