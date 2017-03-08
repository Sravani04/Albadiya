package app.mamac.albadiya.video_list_demo.adapter.items;

import android.app.Activity;

import com.squareup.picasso.Picasso;
import com.volokh.danylo.video_player_manager.manager.VideoPlayerManager;
import com.volokh.danylo.video_player_manager.meta.MetaData;

import java.io.IOException;

public class ItemFactory {

    public static BaseVideoItem createItemFromAsset(String assetName, String imageResource, Activity activity, VideoPlayerManager<MetaData> videoPlayerManager) throws IOException {
        return new DirectLinkVideoItem(assetName, assetName, videoPlayerManager, Picasso.with(activity), imageResource, activity);
    }
    public static BaseVideoItem createItemFromAsset(String assetName, int imageResource, Activity activity, VideoPlayerManager<MetaData> videoPlayerManager) throws IOException {
        return new DirectLinkVideoItem(assetName, assetName, videoPlayerManager, Picasso.with(activity), "no_image", activity);
    }


}
