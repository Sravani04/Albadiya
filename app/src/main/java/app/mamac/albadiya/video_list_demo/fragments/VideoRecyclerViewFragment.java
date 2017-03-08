package app.mamac.albadiya.video_list_demo.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.google.gson.JsonArray;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.volokh.danylo.video_player_manager.Config;
import com.volokh.danylo.video_player_manager.manager.PlayerItemChangeListener;
import com.volokh.danylo.video_player_manager.manager.SingleVideoPlayerManager;
import com.volokh.danylo.video_player_manager.manager.VideoPlayerManager;
import com.volokh.danylo.video_player_manager.meta.MetaData;

import app.mamac.albadiya.App;
import app.mamac.albadiya.HomeProfile;
import app.mamac.albadiya.HomeProfileAdapter;
import app.mamac.albadiya.Posts;
import app.mamac.albadiya.R;
import app.mamac.albadiya.Settings;
import app.mamac.albadiya.video_list_demo.adapter.VideoRecyclerViewAdapter;
import app.mamac.albadiya.video_list_demo.adapter.items.BaseVideoItem;
import app.mamac.albadiya.video_list_demo.adapter.items.ItemFactory;
import com.volokh.danylo.visibility_utils.calculator.DefaultSingleItemCalculatorCallback;
import com.volokh.danylo.visibility_utils.calculator.ListItemsVisibilityCalculator;
import com.volokh.danylo.visibility_utils.calculator.SingleListViewItemActiveCalculator;
import com.volokh.danylo.visibility_utils.scroll_utils.ItemsPositionGetter;
import com.volokh.danylo.visibility_utils.scroll_utils.RecyclerViewItemPositionGetter;

import java.io.IOException;
import java.util.ArrayList;

/**
 * This fragment shows of how to use {@link VideoPlayerManager} with a RecyclerView.
 */
public class VideoRecyclerViewFragment extends Fragment {

    VideoRecyclerViewAdapter videoRecyclerViewAdapter;
    private static final boolean SHOW_LOGS = Config.SHOW_LOGS;
    private static final String TAG = VideoRecyclerViewFragment.class.getSimpleName();

    private final ArrayList<BaseVideoItem> mList = new ArrayList<>();
    ArrayList<Posts> postsfrom_api = new ArrayList<>();
    /**
     * Only the one (most visible) view should be active (and playing).
     * To calculate visibility of views we use {@link SingleListViewItemActiveCalculator}
     */
    private final ListItemsVisibilityCalculator mVideoVisibilityCalculator =
            new SingleListViewItemActiveCalculator(new DefaultSingleItemCalculatorCallback(), mList);

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;

    /**
     * ItemsPositionGetter is used by {@link ListItemsVisibilityCalculator} for getting information about
     * items position in the RecyclerView and LayoutManager
     */
    private ItemsPositionGetter mItemsPositionGetter;

    /**
     * Here we use {@link SingleVideoPlayerManager}, which means that only one video playback is possible.
     */
    private final VideoPlayerManager<MetaData> mVideoPlayerManager = new SingleVideoPlayerManager(new PlayerItemChangeListener() {
        @Override
        public void onPlayerItemChanged(MetaData metaData) {

        }
    });

    private int mScrollState = AbsListView.OnScrollListener.SCROLL_STATE_IDLE;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {





        View rootView = inflater.inflate(R.layout.fragment_video_recycler_view, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        //get_posts();

        update_view();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(!mList.isEmpty()){
            // need to call this method from list view handler in order to have filled list

            mRecyclerView.post(new Runnable() {
                @Override
                public void run() {

                    mVideoVisibilityCalculator.onScrollStateIdle(
                            mItemsPositionGetter,
                            mLayoutManager.findFirstVisibleItemPosition(),
                            mLayoutManager.findLastVisibleItemPosition());

                }
            });
        }
    }



    @Override
    public void onStop() {
        super.onStop();
        // we have to stop any playback in onStop
        mVideoPlayerManager.resetMediaPlayer();
    }

    private void get_posts() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("plase wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Ion.with(this)
                .load(Settings.SERVER_URL+"posts.php")
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
                                try {
                                    mList.add(ItemFactory.createItemFromAsset("http://html5demos.com/assets/dizzy.mp4", posts.image, getActivity(), mVideoPlayerManager));
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }

                            }

                            update_view();




                            //homeProfileAdapter = new HomeProfileAdapter(getActivity(),postsfrom_api,HomeProfile.this);
                            //listView.setAdapter(homeProfileAdapter);
                            // homeProfileAdapter.notifyDataSetChanged();
                        }

                    }
                });
    }


    private void update_view(){
        try {
            // App.getProxy(getActivity()).getProxyUrl("http://mamacgroup.com/albadiya/uploads/posts/14829206571.mp4")
            mList.add(ItemFactory.createItemFromAsset("https://video.twimg.com/ext_tw_video/814398061153779712/pu/vid/320x180/5-V4ltzKaWNM4B3X.mp4", R.drawable.albadiya_logo, getActivity(), mVideoPlayerManager));
            mList.add(ItemFactory.createItemFromAsset("https://video.twimg.com/ext_tw_video/814398061153779712/pu/vid/320x180/5-V4ltzKaWNM4B3X.mp4", R.drawable.albadiya_logo, getActivity(), mVideoPlayerManager));
            mList.add(ItemFactory.createItemFromAsset("https://video.twimg.com/ext_tw_video/814398061153779712/pu/vid/320x180/5-V4ltzKaWNM4B3X.mp4", R.drawable.albadiya_logo, getActivity(), mVideoPlayerManager));
            mList.add(ItemFactory.createItemFromAsset("https://video.twimg.com/ext_tw_video/814398061153779712/pu/vid/320x180/5-V4ltzKaWNM4B3X.mp4", R.drawable.albadiya_logo, getActivity(), mVideoPlayerManager));

            mList.add(ItemFactory.createItemFromAsset("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4", R.drawable.albadiya_logo, getActivity(), mVideoPlayerManager));
            mList.add(ItemFactory.createItemFromAsset("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4", R.drawable.albadiya_logo, getActivity(), mVideoPlayerManager));
            mList.add(ItemFactory.createItemFromAsset("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4", R.drawable.albadiya_logo, getActivity(), mVideoPlayerManager));
            mList.add(ItemFactory.createItemFromAsset("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4", R.drawable.albadiya_logo, getActivity(), mVideoPlayerManager));

            mList.add(ItemFactory.createItemFromAsset("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4", R.drawable.albadiya_logo, getActivity(), mVideoPlayerManager));
            mList.add(ItemFactory.createItemFromAsset("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4", R.drawable.albadiya_logo, getActivity(), mVideoPlayerManager));
            mList.add(ItemFactory.createItemFromAsset("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4", R.drawable.albadiya_logo, getActivity(), mVideoPlayerManager));
            mList.add(ItemFactory.createItemFromAsset("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4", R.drawable.albadiya_logo, getActivity(), mVideoPlayerManager));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        videoRecyclerViewAdapter = new VideoRecyclerViewAdapter(mVideoPlayerManager, getActivity(), mList);

        mRecyclerView.setAdapter(videoRecyclerViewAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {
                mScrollState = scrollState;
                if(scrollState == RecyclerView.SCROLL_STATE_IDLE && !mList.isEmpty()){

                    mVideoVisibilityCalculator.onScrollStateIdle(
                            mItemsPositionGetter,
                            mLayoutManager.findFirstVisibleItemPosition(),
                            mLayoutManager.findLastVisibleItemPosition());
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(!mList.isEmpty()){
                    mVideoVisibilityCalculator.onScroll(
                            mItemsPositionGetter,
                            mLayoutManager.findFirstVisibleItemPosition(),
                            mLayoutManager.findLastVisibleItemPosition() - mLayoutManager.findFirstVisibleItemPosition() + 1,
                            mScrollState);
                }
            }
        });
        if(!mList.isEmpty()){
            // need to call this method from list view handler in order to have filled list

            mRecyclerView.post(new Runnable() {
                @Override
                public void run() {

                    mVideoVisibilityCalculator.onScrollStateIdle(
                            mItemsPositionGetter,
                            mLayoutManager.findFirstVisibleItemPosition(),
                            mLayoutManager.findLastVisibleItemPosition());

                }
            });
        }

        mItemsPositionGetter = new RecyclerViewItemPositionGetter(mLayoutManager, mRecyclerView);
    }


}