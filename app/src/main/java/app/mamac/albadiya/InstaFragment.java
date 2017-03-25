package app.mamac.albadiya;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import im.ene.toro.sample.feature.facebook.AlbadiyaTimelineFragment;
import im.ene.toro.sample.feature.facebook.CompetitionTimlineFragment;
import im.ene.toro.sample.feature.facebook.PostsTimlineFragment;


/**
 * Created by T on 03-12-2016.
 */

public class InstaFragment extends FragmentActivity implements AlbadiyaTimelineFragment.UserProfileSelectedListner,
        AlbadiyaTimelineFragment.Settingsinterface,AlbadiyaTimelineFragment.ChatScreeninterface,PostsTimlineFragment.UserProfileSelectedListner,PostsTimlineFragment.ChatScreeninterface,PostsTimlineFragment.Settingsinterface,CompetitionTimlineFragment.Settingsinterface,CompetitionTimlineFragment.ChatScreeninterface,CompetitionTimlineFragment.UserProfileSelectedListner,CompetitionTimlineFragment.VoteScreeninterface{
    FrameLayout fragment;
    ImageView first_item,second_item,third_item,fourth_item,fifth_item;
    String main_header;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instafragement_activity);
        fragment = (FrameLayout) findViewById(R.id.fragment);
        first_item = (ImageView) findViewById(R.id.first_item);
        second_item = (ImageView) findViewById(R.id.second_item);
        third_item = (ImageView) findViewById(R.id.third_item);
        fourth_item = (ImageView) findViewById(R.id.fourth_item);
        fifth_item  = (ImageView) findViewById(R.id.fifth_item);




        //HomeProfile homeProfile = new HomeProfile();
       // VideoRecyclerViewFragment videoRecyclerViewFragment = new VideoRecyclerViewFragment();
       //VideoListFragment videoListFragment = new VideoListFragment();
       // getSupportFragmentManager().beginTransaction().replace(R.id.fragment,videoListFragment).commit();

        AlbadiyaTimelineFragment albadiyaTimelineFragment = new AlbadiyaTimelineFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment,albadiyaTimelineFragment).commit();
        reset_icons(1);

        first_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //HomeProfile homeProfile = new HomeProfile();
                //VideoRecyclerViewFragment videoRecyclerViewFragment = new VideoRecyclerViewFragment();
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment,homeProfile).commit();

                AlbadiyaTimelineFragment albadiyaTimelineFragment = new AlbadiyaTimelineFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment,albadiyaTimelineFragment).commit();
                reset_icons(1);
            }
        });

        second_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  InstaSearchAdapter.InstaSearch instaSearch = new InstaSearchAdapter.InstaSearch();
                InstaSearchFragment instaSearchFragment = new InstaSearchFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment,instaSearchFragment).commit();
                reset_icons(2);
            }
        });

        third_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Settings.GetUserId(InstaFragment.this).equals("-1")){
                    Intent intent = new Intent(InstaFragment.this,HomeActivityScreen.class);
                    startActivity(intent);
                }else{
                    //HomeProfile homeProfile = new HomeProfile();
                    //getSupportFragmentManager().beginTransaction().replace(R.id.fragment,homeProfile).commit();
                    PostFragment postFragment = new PostFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment,postFragment).commit();
                    reset_icons(3);
                }



            }
        });

        fourth_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Settings.GetUserId(InstaFragment.this).equals("-1")){
                    Intent intent = new Intent(InstaFragment.this,HomeActivityScreen.class);
                    startActivity(intent);
                }else{
                    reset_icons(4);
                    InstaCategories instaCategories = new InstaCategories();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment,instaCategories).commit();
                }

            }
        });


        fifth_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Settings.GetUserId(InstaFragment.this).equals("-1"))
                {
                    Intent intent = new Intent(InstaFragment.this, HomeActivityScreen.class);
                    startActivity(intent);


                }else{
                    reset_icons(5);
                    EditProfile userProfile = new EditProfile();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment,userProfile).commit();

                }

            }
        });



    }




    private void reset_icons(int pos){

        first_item.setImageResource(R.drawable.ic_home_in);
        second_item.setImageResource(R.drawable.ic_search_in);
        third_item.setImageResource(R.drawable.ic_camera_in);
        fourth_item.setImageResource(R.drawable.ic_contest_in);
        fifth_item.setImageResource(R.drawable.ic_account_in);

        switch (pos){
            case  1:
                first_item.setImageResource(R.drawable.ic_home);
                break;
            case  2:
                second_item.setImageResource(R.drawable.ic_search);
                break;
            case  3:
                third_item.setImageResource(R.drawable.ic_camera);
                break;
            case  4:
                fourth_item.setImageResource(R.drawable.ic_contests);
                break;
            case  5:
                fifth_item.setImageResource(R.drawable.ic_account);
                break;

        }


    }

    @Override
    public void onUserSelected(String member_id) {
        EditProfile userProfile = new EditProfile();
        Bundle bundle = new Bundle();
        bundle.putString("member_id",member_id);
        userProfile.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment,userProfile).addToBackStack(null).commit();
    }

    @Override
    public void opensettings_page() {
        SettingsFragment settingsFragment = new SettingsFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment,settingsFragment).addToBackStack(null).commit();
    }

    @Override
    public void openchatscreen_page() {
       Intent intent = new Intent(InstaFragment.this,ChatScreen.class);
        startActivity(intent);
    }


    @Override
    public void votescreen(ImageView image) {
        Intent intent = new Intent(InstaFragment.this,CompetitorsVoteActivity.class);
        intent.putExtra("image",true);
        startActivity(intent);
    }
}
