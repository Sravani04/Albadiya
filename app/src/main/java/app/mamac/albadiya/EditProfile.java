package app.mamac.albadiya;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import im.ene.toro.sample.feature.facebook.AlbadiyaTimelineFragment;

/**
 * Created by T on 12-12-2016.
 */

public class EditProfile extends Fragment{
    TextView edit_btn;
    EditProfileAdapter editProfileAdapter;
    ArrayList<Integer> images;
    //GridView gridView;
    //TextView log_out;
    ImageView backbtn;
    TextView no_posts,no_of_followers,no_of_following;
    ImageView images_post;
    String member_id;
    ImageView settings;
    ImageView edit_image_btn;
    ImageView posts_list;
    FrameLayout frame_one;
    TextView logout;
    String header;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.activity_editprofile,container,false);



        if(getArguments()!=null && getArguments().containsKey("member_id"))
            member_id=getArguments().getString("member_id");
        else
            member_id=Settings.GetUserId(getActivity());


        edit_btn = (TextView) view.findViewById(R.id.edit_btn);
        edit_image_btn = (ImageView) view.findViewById(R.id.edit_image_btn);
        if(member_id.equals(Settings.GetUserId(getActivity())))
        {
            edit_btn.setText("Edit Profile");
//            edit_image_btn.setImageResource(R.drawable.ic_pencil);
            edit_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),UserEditProfile.class);
                    startActivity(intent);

                }
            });

            edit_image_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),MemberChatActivity.class);
                    startActivity(intent);
                }
            });
        }
        else{
            //edit_btn.setTag(1);
            if(!Settings.GetUserId(getActivity()).equals("-1"))
                follow_status();
            else{
                edit_btn.setText("follow");
                edit_btn.setText("follow");
            }
            edit_image_btn.setImageResource(R.drawable.ic_chats);
            edit_image_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),ChatScreen.class);
                    intent.putExtra("receiver_id",member_id);
                    startActivity(intent);
                }
            });
            edit_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    Ion.with(getContext())
                            .load(Settings.SERVER_URL + "follow.php")
                            .setBodyParameter("member_id",Settings.GetUserId(getContext()))
                            .setBodyParameter("follower_id",member_id)
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    if (result.get("status").getAsString().equals("Success")){
                                        Toast.makeText(getContext(),result.get("message").getAsString(),Toast.LENGTH_SHORT).show();
                                        follow_status();
//                                       final int status =(Integer) v.getTag();
//                                        if(status == 1) {
//                                            edit_btn.setText("Unfollow");
//
//                                            v.setTag(0);
//                                        } else {
//                                            edit_btn.setText("Follow");
//                                            v.setTag(1);
//                                        }

                                    }else{
                                        Toast.makeText(getContext(),result.get("message").getAsString(),Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

            });


        }


        logout = (TextView) view.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Settings.SetUserId(getActivity(),"-1");
                getActivity().onBackPressed();
            }
        });



//        settings = (ImageView) view.findViewById(R.id.settings);
//        settings.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SettingsFragment settingsFragment =  new SettingsFragment();
//                getFragmentManager().beginTransaction().replace(R.id.fragment,settingsFragment).commit();
//            }
//        });
        backbtn = (ImageView) view.findViewById(R.id.back_btn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        //gridView = (GridView) view.findViewById(R.id.gallery_images);
        item_name = (TextView) view.findViewById(R.id.item_name);
        item_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        item_image = (CircleImageView) view.findViewById(R.id.item_image);
        //log_out = (TextView) view.findViewById(R.id.log_out_btn);
//        log_out.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Settings.SetUserId(getActivity(),"-1");
//                getActivity().onBackPressed();
//            }
//        });
        postsfrom_api = new ArrayList<>();
        images = new ArrayList<>();



        images.add(R.drawable.banner3);
        images.add(R.drawable.timeline);
        images.add(R.drawable.amazon);
        images.add(R.drawable.banner1);
        images.add(R.drawable.banner);

        editProfileAdapter = new EditProfileAdapter(getActivity(),postsfrom_api);
//        gridView.setAdapter(editProfileAdapter);
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//           @Override
//           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//              // Toast.makeText(getActivity(),images.get(position),Toast.LENGTH_SHORT).show();
//           }
//        });
        no_posts = (TextView) view.findViewById(R.id.user_no_posts);
        no_of_followers = (TextView) view.findViewById(R.id.followers);
        no_of_following = (TextView) view.findViewById(R.id.following);
        images_post = (ImageView) view.findViewById(R.id.images_post);

        posts_list = (ImageView) view.findViewById(R.id.posts_list);

        frame_one = (FrameLayout) view.findViewById(R.id.frame_one);

        GalleryImageItems galleryImageItems = new GalleryImageItems();
        getFragmentManager().beginTransaction().replace(R.id.frame_one,galleryImageItems).commit();

        images_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GalleryImageItems galleryImageItems = new GalleryImageItems();
                getFragmentManager().beginTransaction().replace(R.id.frame_one,galleryImageItems).commit();
            }
        });

        if(getArguments()!=null && getArguments().containsKey("header"))
            header = getArguments().getString("header");

        posts_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(member_id.equals(Settings.GetUserId(getActivity()))) {
                    AlbadiyaTimelineFragment albadiyaTimelineFragment = new AlbadiyaTimelineFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("header","0");
                    bundle.putString("line","0");
                    albadiyaTimelineFragment.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.frame_one, albadiyaTimelineFragment).commit();
                }else {
                    AlbadiyaTimelineFragment albadiyaTimelineFragment = new AlbadiyaTimelineFragment();
                    getFragmentManager().beginTransaction().replace(R.id.frame_one, albadiyaTimelineFragment).commit();
                }

            }
        });



        get_member_details();
        //    get_posts();
        return view;

    }

    String cnt="0";
    public void follow_status(){
        Ion.with(getActivity())
                .load(Settings.SERVER_URL+"follow-status.php")
                .setBodyParameter("member_id",Settings.GetUserId(getActivity()))
                .setBodyParameter("follower_id",member_id)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result.get("status").getAsString().equals("Success")){
                            cnt = result.get("cnt").getAsString();
                            //Toast.makeText(getActivity(),result.get("message").getAsString(),Toast.LENGTH_SHORT).show();
                            if (!cnt.equals("0")){
                                edit_btn.setText("unfollow");
                                edit_btn.setText("unfollow");
                            }else{
                                edit_btn.setText("follow");
                                edit_btn.setText("follow");
                            }
                        }
                    }
                });
    }

    TextView item_name;
    CircleImageView item_image;



    ArrayList<Posts> postsfrom_api;
    public void get_member_details(){
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String url = Settings.SERVER_URL+"member-details.php";
        Ion.with(getActivity())
                .load(url)
                .setBodyParameter("member_id",member_id)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        if(progressDialog!=null)
                            progressDialog.dismiss();
                        try {
                            JsonObject jsonObject = result.get(0).getAsJsonObject();
                            item_name.setText(jsonObject.get("name").getAsString());
                            no_posts.setText(String.valueOf(jsonObject.get("posts").getAsJsonArray().size()));
                            no_of_followers.setText(String.valueOf(jsonObject.get("follows").getAsJsonArray().size()));
                            no_of_following.setText(String.valueOf(jsonObject.get("following").getAsJsonArray().size()));
                            JsonArray posts_aray = jsonObject.get("posts").getAsJsonArray();
                            for (int i = 0; i < posts_aray.size(); i++) {
                                Posts posts = new Posts(posts_aray.get(i).getAsJsonObject(), getActivity());
                                postsfrom_api.add(posts);
                            }
                            editProfileAdapter.notifyDataSetChanged();
                            Picasso.with(getActivity()).load(jsonObject.get("image").getAsString()).placeholder(R.drawable.ic_profile).into(item_image);
//                            Ion.with(getActivity())
//                                    .load(jsonObject.get("image").getAsString())
//                                    .withBitmap()
//                                    .placeholder(R.drawable.ic_prof   ile)
//                                    .intoImageView(item_image);

                        }catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }
                });
    }

    private void get_posts() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("plase wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Ion.with(this)
                .load(Settings.SERVER_URL+"posts.php")
                .setBodyParameter("member_id",member_id)
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
                            editProfileAdapter.notifyDataSetChanged();
                        }

                    }
                });
    }



}
