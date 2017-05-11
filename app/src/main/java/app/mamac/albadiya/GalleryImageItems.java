package app.mamac.albadiya;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 * Created by T on 20-01-2017.
 */

public class GalleryImageItems extends Fragment {
    GridView gridView;
    GalleryImagesAdapter galleryImagesAdapter;
    ArrayList<Integer> images;
    ArrayList<Posts> postsfrom_api;
    String member;


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.gallery_images_items,container,false);
        gridView = (GridView) view.findViewById(R.id.gallery_images);
        postsfrom_api = new ArrayList<>();
        images = new ArrayList<>();

        if (getArguments()!=null){
            member  = getArguments().getString("member_id");
        }


        images.add(R.drawable.banner3);
        images.add(R.drawable.timeline);
        images.add(R.drawable.amazon);
        images.add(R.drawable.banner1);
        images.add(R.drawable.banner);

        galleryImagesAdapter = new GalleryImagesAdapter(getActivity(),postsfrom_api);
        gridView.setAdapter(galleryImagesAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Toast.makeText(getActivity(),images.get(position),Toast.LENGTH_SHORT).show();
            }
        });

       get_member_details();
        //get_posts();

        return view;
    }

    public void get_member_details(){
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String url = Settings.SERVER_URL+"member-details.php";
        Ion.with(getActivity())
                .load(url)
                .setBodyParameter("member_id",member)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        if(progressDialog!=null)
                            progressDialog.dismiss();
                        try {
                            JsonObject jsonObject = result.get(0).getAsJsonObject();
                            JsonArray posts_aray = jsonObject.get("posts").getAsJsonArray();
                            for (int i = 0; i < posts_aray.size(); i++) {
                                Posts posts = new Posts(posts_aray.get(i).getAsJsonObject(), getActivity());
                                postsfrom_api.add(posts);
                            }
                            galleryImagesAdapter.notifyDataSetChanged();

                        }catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }
                });
    }

    public void get_posts(){
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Ion.with(getContext())
                .load(Settings.SERVER_URL+"posts.php")
                .setBodyParameter("member_id",member)
                .setBodyParameter("uploader_id",Settings.GetUserId(getContext()))
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        if (progressDialog!=null)
                            progressDialog.dismiss();
                        try {
                            for (int i=0;i<result.size();i++) {
                                Posts posts = new Posts(result.get(i).getAsJsonObject(),getContext());
                                postsfrom_api.add(posts);
                            }
                            galleryImagesAdapter.notifyDataSetChanged();
                        }catch (Exception e1){
                            e1.printStackTrace();
                        }


                    }
                });
    }

}
