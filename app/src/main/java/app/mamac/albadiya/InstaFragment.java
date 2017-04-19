package app.mamac.albadiya;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import im.ene.toro.sample.feature.facebook.AlbadiyaTimelineFragment;
import im.ene.toro.sample.feature.facebook.CompetitionTimlineFragment;
import im.ene.toro.sample.feature.facebook.PostsTimlineFragment;


/**
 * Created by T on 03-12-2016.
 */

public class InstaFragment extends FragmentActivity implements AlbadiyaTimelineFragment.UserProfileSelectedListner,AlbadiyaTimelineFragment.ChatScreeninterface,PostsTimlineFragment.UserProfileSelectedListner,PostsTimlineFragment.ChatScreeninterface,
        CompetitionTimlineFragment.ChatScreeninterface,CompetitionTimlineFragment.UserProfileSelectedListner{
    FrameLayout fragment;
    ImageView first_item,second_item,third_item,fourth_item,fifth_item;
    String main_header,ex_date;
    AlbadiyaTimelineFragment albadiyaTimelineFragment;
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
//        final AlbadiyaTimelineFragment albadiyaTimelineFragment = new AlbadiyaTimelineFragment();
//        getSupportFragmentManager().popBackStack();
//        getSupportFragmentManager().beginTransaction().replace(R.id.fragment,albadiyaTimelineFragment).commit();
//        reset_icons(1);

         albadiyaTimelineFragment = new AlbadiyaTimelineFragment();
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
        transaction.replace(R.id.fragment, albadiyaTimelineFragment);
        transaction.commit();
        reset_icons(1);

        first_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //HomeProfile homeProfile = new HomeProfile();
                //VideoRecyclerViewFragment videoRecyclerViewFragment = new VideoRecyclerViewFragment();
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment,homeProfile).commit();
                albadiyaTimelineFragment = new AlbadiyaTimelineFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                transaction.replace(R.id.fragment, albadiyaTimelineFragment);
                transaction.commit();
                reset_icons(1);

            }
        });


        second_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  InstaSearchAdapter.InstaSearch instaSearch = new InstaSearchAdapter.InstaSearch();
//                InstaSearchFragment instaSearchFragment = new InstaSearchFragment();
//                getSupportFragmentManager().popBackStack();
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment,instaSearchFragment).commit();
//                reset_icons(2);
                try{
                    albadiyaTimelineFragment.adapter.pausePlayback();
                    Log.e("message","created");
                }catch (Exception ex){
                    ex.printStackTrace();
                }

                InstaSearchFragment instaSearchFragment = new InstaSearchFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_left, R.anim.exit_to_left);
                transaction.replace(R.id.fragment, instaSearchFragment);
                transaction.commit();
                reset_icons(2);
//                albadiyaTimelineFragment.onPlaylistAttached();
//                albadiyaTimelineFragment.isResumed();


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
//                    PostFragment postFragment = new PostFragment();
//                    getSupportFragmentManager().popBackStackImmediate();
//                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment,postFragment).commit();
//                    reset_icons(3);

                    FragmentManager fm = getFragmentManager();
                    fm.popBackStackImmediate();

                    PostFragment postFragment = new PostFragment();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                    transaction.replace(R.id.fragment, postFragment);
                    transaction.commit();
                    reset_icons(3);
                    albadiyaTimelineFragment.onPlaylistAttached();
                    albadiyaTimelineFragment.isResumed();
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
//                    reset_icons(4);
//                    InstaCategories instaCategories = new InstaCategories();
//                    getSupportFragmentManager().popBackStackImmediate();
//                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment,instaCategories).commit();

//                    InstaCategories instaCategories = new InstaCategories();
//                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                    transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right);
//                    transaction.replace(R.id.fragment, instaCategories);
//                    transaction.commit();
//                    reset_icons(4);
                    InstaCategories instaCategories = new InstaCategories();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                    transaction.replace(R.id.fragment, instaCategories);
                    transaction.commit();
                    reset_icons(4);
                    albadiyaTimelineFragment.onPlaylistAttached();
                    albadiyaTimelineFragment.isResumed();

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
//                    reset_icons(5);
//                    EditProfile userProfile = new EditProfile();
//                    getSupportFragmentManager().popBackStackImmediate();
//                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment,userProfile).commit();

                    EditProfile userProfile = new EditProfile();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                    transaction.replace(R.id.fragment, userProfile);
                    transaction.commit();
                    reset_icons(5);
                    albadiyaTimelineFragment.onPlaylistAttached();
                    albadiyaTimelineFragment.isResumed();
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
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment,userProfile).commit();
    }


    @Override
    public void openchatscreen_page(String member_id) {
        Intent intent = new Intent(InstaFragment.this,MemberChatActivity.class);
        intent.putExtra("receiver_id",member_id);
        startActivity(intent);
    }


}
