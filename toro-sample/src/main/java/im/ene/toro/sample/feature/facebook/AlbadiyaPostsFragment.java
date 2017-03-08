package im.ene.toro.sample.feature.facebook;

/**
 * Created by T on 11-02-2017.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
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
import im.ene.toro.sample.feature.facebook.timeline.Settings;
import im.ene.toro.sample.feature.facebook.timeline.TimelineAdapter;
import im.ene.toro.sample.feature.facebook.timeline.TimelineItem;
import im.ene.toro.sample.util.Util;

/**
 * Created by T on 08-02-2017.
 */

public class AlbadiyaPostsFragment extends BaseToroFragment implements FacebookPlaylistFragment.Callback,AbsListView.OnScrollListener{
    RecyclerView mRecyclerView;
    ImageView settings;
    ImageView chat_screen;
    private TimelineAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<TimelineItem> itemsfrom_api;
    int pageno=1;
    private  int previouslast;
    AlbadiyaTimelineFragment fragment;
    HashMap<String,Boolean> flags;
    HashMap<String,Integer> likes;




    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.posts_recycler_view, container, false);
        itemsfrom_api = new ArrayList<>();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(layoutManager);
        get_posts();
        return view;

    }

    private void make_page() {
        adapter = new TimelineAdapter(itemsfrom_api,fragment,this);
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

        adapter.setOnItemClickListener(new TimelineAdapter.ItemClickListener() {
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
//                ToroPlayer player = adapter.getPlayer();
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




    public void get_posts(){
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("please wait..");
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
                        if (progressDialog!=null)
                            progressDialog.dismiss();

                        if (e != null) {
                            e.printStackTrace();
                        } else {
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
                                TimelineItem timelineItem = new TimelineItem(getActivity(),posts.id,posts.user_name,
                                        posts.user_image, posts.description, type, type_url, posts.time,posts.total_likes,
                                        posts.total_views,posts.member_like,posts.user_id);
                                itemsfrom_api.add(timelineItem);
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
                get_posts();
                previouslast = lastitem;
            }
        }
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

