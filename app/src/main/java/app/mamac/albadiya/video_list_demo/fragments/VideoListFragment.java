//package app.mamac.albadiya.video_list_demo.fragments;
//
//import android.app.Activity;
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AbsListView;
//import android.widget.ImageView;
//import android.widget.ListView;
//
//import com.google.gson.JsonArray;
//import com.koushikdutta.async.future.FutureCallback;
//import com.koushikdutta.ion.Ion;
//import com.volokh.danylo.video_player_manager.Config;
//import com.volokh.danylo.video_player_manager.manager.PlayerItemChangeListener;
//import com.volokh.danylo.video_player_manager.manager.SingleVideoPlayerManager;
//import com.volokh.danylo.video_player_manager.manager.VideoPlayerManager;
//import com.volokh.danylo.video_player_manager.meta.MetaData;
//import com.volokh.danylo.video_player_manager.utils.Logger;
//import com.volokh.danylo.visibility_utils.calculator.DefaultSingleItemCalculatorCallback;
//import com.volokh.danylo.visibility_utils.calculator.ListItemsVisibilityCalculator;
//import com.volokh.danylo.visibility_utils.calculator.SingleListViewItemActiveCalculator;
//import com.volokh.danylo.visibility_utils.scroll_utils.ItemsPositionGetter;
//import com.volokh.danylo.visibility_utils.scroll_utils.ListViewItemPositionGetter;
//
//import java.util.ArrayList;
//
//import app.mamac.albadiya.ChatScreen;
//import app.mamac.albadiya.Posts;
//import app.mamac.albadiya.R;
//import app.mamac.albadiya.Settings;
//import app.mamac.albadiya.SettingsFragment;
//import app.mamac.albadiya.video_list_demo.adapter.VideoListViewAdapter;
//import app.mamac.albadiya.video_list_demo.adapter.items.BaseVideoItem;
///**
// * This fragment shows of how to use {@link VideoPlayerManager} with a ListView.
// */
//public class VideoListFragment extends Fragment {
//
//    private static final boolean SHOW_LOGS = Config.SHOW_LOGS;
//    private static final String TAG = VideoListFragment.class.getSimpleName();
//
//    private final ArrayList<BaseVideoItem> mList = new ArrayList<>();
//    /**
//     * Only the one (most visible) view should be active (and playing).
//     * To calculate visibility of views we use {@link SingleListViewItemActiveCalculator}
//     */
//    private final ListItemsVisibilityCalculator mListItemVisibilityCalculator =
//            new SingleListViewItemActiveCalculator(new DefaultSingleItemCalculatorCallback(), mList);
//
//    /**
//     * ItemsPositionGetter is used by {@link ListItemsVisibilityCalculator} for getting information about
//     * items position in the ListView
//     */
//    private ItemsPositionGetter mItemsPositionGetter;
//
//    /**
//     * Here we use {@link SingleVideoPlayerManager}, which means that only one video playback is possible.
//     */
//    private final VideoPlayerManager<MetaData> mVideoPlayerManager = new SingleVideoPlayerManager(new PlayerItemChangeListener() {
//        @Override
//        public void onPlayerItemChanged(MetaData metaData) {
//            if(SHOW_LOGS) Logger.v(TAG, "onPlayerItemChanged " + metaData);
//
//        }
//    });
//
//    private int mScrollState = AbsListView.OnScrollListener.SCROLL_STATE_IDLE;
//
//    private ListView mListView;
//
//    ArrayList<Posts> postsfrom_api;
//    ImageView settings;
//    ImageView chat_item;
//    int pageno=2;
//    private  int previouslast;
//    UserProfileSelectedListner mCallback;
//    VideoListViewAdapter videoListViewAdapter;
//
//    public interface UserProfileSelectedListner {
//
//        public void onUserSelected(String member_id);
//    }
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//
//        // This makes sure that the container activity has implemented
//        // the callback interface. If not, it throws an exception
//        try {
//            mCallback = (UserProfileSelectedListner) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnHeadlineSelectedListener");
//        }
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        postsfrom_api = new ArrayList<>();
//        // if your files are in "assets" directory you can pass AssetFileDescriptor to the VideoPlayerView
//        // if they are url's or path values you can pass the String path to the VideoPlayerView
////        try {
//////            mList.add(ItemFactory.createItemFromAsset("video_sample_1.mp4", R.drawable.video_sample_1_pic, getActivity(), mVideoPlayerManager));
//////            mList.add(ItemFactory.createItemFromAsset("video_sample_2.mp4", R.drawable.video_sample_2_pic, getActivity(), mVideoPlayerManager));
//////            mList.add(ItemFactory.createItemFromAsset("video_sample_3.mp4", R.drawable.video_sample_3_pic, getActivity(), mVideoPlayerManager));
//////            mList.add(ItemFactory.createItemFromAsset("video_sample_4.mp4", R.drawable.video_sample_4_pic, getActivity(), mVideoPlayerManager));
//////
//////            mList.add(ItemFactory.createItemFromAsset("video_sample_1.mp4", R.drawable.video_sample_1_pic, getActivity(), mVideoPlayerManager));
//////            mList.add(ItemFactory.createItemFromAsset("video_sample_2.mp4", R.drawable.video_sample_2_pic, getActivity(), mVideoPlayerManager));
//////            mList.add(ItemFactory.createItemFromAsset("video_sample_3.mp4", R.drawable.video_sample_3_pic, getActivity(), mVideoPlayerManager));
//////            mList.add(ItemFactory.createItemFromAsset("video_sample_4.mp4", R.drawable.video_sample_4_pic, getActivity(), mVideoPlayerManager));
//////
//////            mList.add(ItemFactory.createItemFromAsset("video_sample_1.mp4", R.drawable.video_sample_1_pic, getActivity(), mVideoPlayerManager));
//////            mList.add(ItemFactory.createItemFromAsset("video_sample_2.mp4", R.drawable.video_sample_2_pic, getActivity(), mVideoPlayerManager));
//////            mList.add(ItemFactory.createItemFromAsset("video_sample_3.mp4", R.drawable.video_sample_3_pic, getActivity(), mVideoPlayerManager));
//////            mList.add(ItemFactory.createItemFromAsset("video_sample_4.mp4", R.drawable.video_sample_4_pic, getActivity(), mVideoPlayerManager));
////        } catch (IOException e) {
////            throw new RuntimeException(e);
////        }
//
//        View rootView = inflater.inflate(R.layout.fragment_video_list_view, container, false);
//
//        mListView = (ListView) rootView.findViewById(R.id.list_view);
//
//        videoListViewAdapter = new VideoListViewAdapter(mVideoPlayerManager, getActivity(), postsfrom_api);
//        mListView.setAdapter(videoListViewAdapter);
//
//        mItemsPositionGetter = new ListViewItemPositionGetter(mListView);
//        /**
//         * We need to set onScrollListener after we create {@link #mItemsPositionGetter}
//         * because {@link android.widget.AbsListView.OnScrollListener#onScroll(AbsListView, int, int, int)}
//         * is called immediately and we will get {@link NullPointerException}
//         */
//        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                mScrollState = scrollState;
//                if (scrollState == SCROLL_STATE_IDLE && !mList.isEmpty()) {
//                    mListItemVisibilityCalculator.onScrollStateIdle(mItemsPositionGetter, view.getFirstVisiblePosition(), view.getLastVisiblePosition());
//                }
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                if (!mList.isEmpty()) {
//                    // on each scroll event we need to call onScroll for mListItemVisibilityCalculator
//                    // in order to recalculate the items visibility
//                    mListItemVisibilityCalculator.onScroll(mItemsPositionGetter, firstVisibleItem, visibleItemCount, mScrollState);
//                }
//            }
//        });
//
//        settings = (ImageView) rootView.findViewById(R.id.settings);
//        settings.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SettingsFragment settingsFragment = new SettingsFragment();
//                getFragmentManager().beginTransaction().replace(R.id.fragment,settingsFragment).commit();
//            }
//        });
//
//        chat_item = (ImageView) rootView.findViewById(R.id.chat_item);
//        chat_item.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent chats = new Intent(getActivity(),ChatScreen.class);
//                startActivity(chats);
//            }
//        });
//
//
//
//        get_posts();
//        return rootView;
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        if(!mList.isEmpty()){
//            // need to call this method from list view handler in order to have list filled previously
//
//            mListView.post(new Runnable() {
//                @Override
//                public void run() {
//
//                    mListItemVisibilityCalculator.onScrollStateIdle(
//                            mItemsPositionGetter,
//                            mListView.getFirstVisiblePosition(),
//                            mListView.getLastVisiblePosition());
//
//                }
//            });
//        }
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        // we have to stop any playback in onStop
//        mVideoPlayerManager.resetMediaPlayer();
//    }
//
//    private void get_posts() {
//        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setMessage("loading..");
//        progressDialog.setCancelable(false);
//        progressDialog.show();
//        Ion.with(this)
//                .load(Settings.SERVER_URL+"posts.php")
//                .setBodyParameter("member_id",Settings.GetUserId(getActivity()))
//                .setBodyParameter("page",String.valueOf(pageno))
//                .asJsonArray()
//                .setCallback(new FutureCallback<JsonArray>() {
//                    @Override
//                    public void onCompleted(Exception e, JsonArray result) {
//
//                        // do stuff with the result or error
//                        //{"status":"Failure","message":"Please Enter Your Type"}
//                        if (progressDialog != null)
//                            progressDialog.dismiss();
//                        if (e != null) {
//                            e.printStackTrace();
//                        } else {
//                            Log.e("response", String.valueOf(result.size()));
//
//                            for (int i = 0; i < result.size(); i++) {
//                                Posts posts = new Posts(result.get(i).getAsJsonObject(),getActivity());
//                                postsfrom_api.add(posts);
//                            }
//                            videoListViewAdapter.notifyDataSetChanged();
//                        }
//
//                    }
//                });
//    }
//
//}