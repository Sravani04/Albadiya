package im.ene.toro.sample.feature.facebook;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
import com.google.gson.JsonArray;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import im.ene.toro.Toro;
import im.ene.toro.ToroPlayer;
import im.ene.toro.ToroStrategy;
import im.ene.toro.sample.BaseToroFragment;
import im.ene.toro.sample.R;
import im.ene.toro.sample.feature.facebook.playlist.FacebookPlaylistFragment;
import im.ene.toro.sample.feature.facebook.timeline.Posts;
import im.ene.toro.sample.feature.facebook.timeline.PostsTimelineAdapter;
import im.ene.toro.sample.feature.facebook.timeline.Settings;
import im.ene.toro.sample.feature.facebook.timeline.TimelineItem;
import im.ene.toro.sample.util.Util;

/**
 * Created by T on 08-02-2017.
 */

public class PostsTimlineFragment extends BaseToroFragment implements FacebookPlaylistFragment.Callback,AbsListView.OnScrollListener{
    RecyclerView mRecyclerView;
    ImageView settings;
    ImageView chat_screen;
    private PostsTimelineAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<TimelineItem> itemsfrom_api;
    int pageno=1;
    private  int previouslast;
    Settingsinterface mCallback;
    ChatScreeninterface Callback;
    UserProfileSelectedListner uCallback;
    HashMap<String,Boolean> flags;
    HashMap<String,Integer> likes;
    LinearLayout header;
    TextView language;
    String post_id;
    String main_header;
    LinearLayout line;
    String horizontal_line;
    AlbadiyaTimelineFragment fragment;
    String member;


    public interface Settingsinterface{
        public void opensettings_page();
    }

    public interface ChatScreeninterface{
        public void openchatscreen_page();
    }

    public interface UserProfileSelectedListner {

        public void onUserSelected(String member_id);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) return;
        try {
            mCallback = (Settingsinterface) activity;
            Callback = (ChatScreeninterface) activity;
            uCallback = (UserProfileSelectedListner) activity;

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }



    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.generic_recycler_view, container, false);
        itemsfrom_api = new ArrayList<>();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(layoutManager);
        language = (TextView) view.findViewById(R.id.language);
        header = (LinearLayout) view.findViewById(R.id.header);
        line = (LinearLayout) view.findViewById(R.id.line);
        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        if(getArguments()!=null && getArguments().containsKey("header")) {
            main_header = getArguments().getString("header");
            horizontal_line = getArguments().getString("line");
            member  = getArguments().getString("member_id");
            header.setVisibility(View.GONE);
            line.setVisibility(View.GONE);
        } else {
            header.setVisibility(View.VISIBLE);
            line.setVisibility(View.VISIBLE);
        }

//        settings = (ImageView) view.findViewById(R.id.settings);
//        settings.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mCallback.opensettings_page();
//            }
//        });

//        chat_screen = (ImageView) view.findViewById(R.id.chat_screen);
//        chat_screen.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Callback.openchatscreen_page();
//            }
//        });
        get_posts(true);
        return view;

    }

    private void make_page() {
        adapter = new PostsTimelineAdapter(itemsfrom_api,fragment,this);
        mRecyclerView.setAdapter(adapter);

        final ToroStrategy oldStrategy = Toro.getStrategy();
        final int firstVideoPosition = adapter.firstVideoPosition();

        Toro.setStrategy(new ToroStrategy() {
            boolean isFirstPlayerDone = firstVideoPosition != -1; // Valid first position only

            @Override
            public String getDescription() {
                return "First video plays first";
            }

            @Override
            public ToroPlayer findBestPlayer(List<ToroPlayer> candidates) {
                return oldStrategy.findBestPlayer(candidates);
            }

            @Override
            public boolean allowsToPlay(ToroPlayer player, ViewParent parent) {
                boolean allowToPlay = (isFirstPlayerDone || player.getPlayOrder() == firstVideoPosition)  //
                        && oldStrategy.allowsToPlay(player, parent);

                // A work-around to keep track of first video on top.
                if (player.getPlayOrder() == firstVideoPosition) {
                    isFirstPlayerDone = true;
                }
                return allowToPlay;

            }

        });


        adapter.setOnItemClickListener(new PostsTimelineAdapter.ItemClickListener() {

            @Override
            protected void onOgpItemClick(RecyclerView.ViewHolder viewHolder, View view,
                                          TimelineItem.OgpItem item) {
                final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getItemUrl()));
                startActivity(intent);
            }

            @Override
            protected void onPhotoClick(RecyclerView.ViewHolder viewHolder, View view,
                                        TimelineItem.PhotoItem item) {
            }

            @Override
            protected void onVideoClick(RecyclerView.ViewHolder viewHolder, View view,
                                        TimelineItem.VideoItem item) {
                long duration = C.LENGTH_UNSET;
                long position = C.POSITION_UNSET;
                int order = viewHolder.getAdapterPosition();

//               ToroPlayer player = adapter.getPlayer();
//                if (player != null) {
//                    duration = player.getDuration();
//                    position = player.isPlaying() ? player.getCurrentPosition()
//                            : adapter.getSavedPosition(Util.genVideoId(item.getVideoUrl(), order)); // safe
//                }
//
//                if (item != null) {
//                    FacebookPlaylistFragment playlistFragment =
//                            FacebookPlaylistFragment.newInstance(item, position, duration, order);
//                    playlistFragment.show(getFragmentManager(),
//                            FacebookPlaylistFragment.class.getSimpleName());
//                }
            }


        });

    }

    boolean isActive = false;

    @Override
    protected void dispatchFragmentActivated() {
        super.dispatchFragmentActivated();
        Toro.register(mRecyclerView);
        isActive = true;

    }

    @Override
    public void dispatchFragmentDeActivated() {
        super.dispatchFragmentDeActivated();
        Toro.register(mRecyclerView);
        isActive = false;

    }


    private static final String TAG = "Toro:FB:TL";

    @Override
    public void onPlaylistAttached() {
        Log.i(TAG, "onPlaylistAttached() called");
        Toro.unregister(mRecyclerView);

    }

    @Override
    public void onPlaylistDetached(TimelineItem.VideoItem baseItem, Long position, int order) {
        Log.i(TAG,
                "onPlaylistDetached() called with: position = [" + position + "], order = [" + order + "]");
        if (adapter.getPlayer() != null) {
            adapter.saveVideoState(Util.genVideoId(baseItem.getVideoUrl(), order), position,
                    adapter.getPlayer().getDuration());
        }

        if (isActive) {
            Toro.register(mRecyclerView);
        }
    }


    public void delete_post(String post_id){
        get_posts(true);

    }
    public void get_posts(boolean clear_all){

        if(clear_all)
            itemsfrom_api.clear();
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("please wait..");
            progressDialog.setCancelable(false);
            progressDialog.show();
            Ion.with(this)
                    .load(Settings.SERVER_URL + "posts.php")
                    .setBodyParameter("member_id", Settings.GetUserId(getContext()))
                    .setBodyParameter("uploader_id",member)
                    .asJsonArray()
                    .setCallback(new FutureCallback<JsonArray>() {
                        @Override
                        public void onCompleted(Exception e, JsonArray result) {
                            if (progressDialog != null)
                                progressDialog.dismiss();

                            if (e != null) {
                                e.printStackTrace();
                            } else {
                                flags = new HashMap<>();
                                likes = new HashMap<>();
                                Log.e("response",result.toString());

                                for (int i = 0; i < result.size(); i++) {
                                    Posts posts = new Posts(result.get(i).getAsJsonObject(), getActivity());
                                    String type, type_url;
                                    if (posts.video.equals("")) {
                                        type = "image";
                                        type_url = posts.image;
                                    } else {
                                        type = "video";
                                        type_url = posts.video;
                                    }

                                    post_id = posts.id;
                                    TimelineItem timelineItem = new TimelineItem(getActivity(), posts.id, posts.user_name,
                                            posts.user_image, posts.description, type, type_url, posts.time, posts.total_likes,
                                            posts.total_views, posts.member_like, posts.user_id);
//                                    if (posts.user_id.equals(member)) {
                                        itemsfrom_api.add(timelineItem);

                                        likes.put(posts.id, Integer.parseInt(itemsfrom_api.get(i).getAuthor().getUserLikes()));

                                        if (itemsfrom_api.get(i).getAuthor().getMemberLike().equals("0")) {
                                            flags.put(posts.id, Boolean.FALSE);
                                        } else {
                                            flags.put(posts.id, Boolean.TRUE);
                                        }
//                                    }
                                }

                                make_page();
                                //adapter.notifyDataSetChanged();
                            }
                        }
                    });
        }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {
        int lastitem = i + i1;
        if (lastitem == i2){
            if (previouslast!=lastitem){
                Log.e("result","last");
                pageno++;
                get_posts(false);
                previouslast = lastitem;
            }
        }
    }


    public void go_to_user_profile(String member_id){
        uCallback.onUserSelected(member_id);
    }




    public boolean get_like_id(String id){


        if(flags.get(id))
            return true;
        else
            return false;
    }


    public void set_like_id(String id) {
        flags.put(id,!flags.get(id));

    }


    public void set_like_count(String id) {
        if(flags.get(id))
            likes.put(id,likes.get(id)+1);
        else
            likes.put(id,likes.get(id)-1);
    }

    public String get_like_count(String id) {


        return String.valueOf(likes.get(id));
    }

}
