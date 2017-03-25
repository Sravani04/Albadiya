package im.ene.toro.sample.feature.facebook.timeline;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import im.ene.toro.MediaPlayerManager;
import im.ene.toro.MediaPlayerManagerImpl;
import im.ene.toro.ToroAdapter;
import im.ene.toro.ToroPlayer;
import im.ene.toro.sample.ToroApp;
import im.ene.toro.sample.feature.facebook.AlbadiyaTimelineFragment;
import im.ene.toro.sample.feature.facebook.CompetitionTimlineFragment;
import im.ene.toro.sample.feature.facebook.OrderedPlayList;
import im.ene.toro.sample.feature.facebook.PostsTimlineFragment;

/**
 * Created by T on 24-03-2017.
 */

public class CompetitionTimlineAdapter extends ToroAdapter<ToroAdapter.ViewHolder>
        implements OrderedPlayList, MediaPlayerManager {

    static final int TYPE_OGP = 1;
    static final int TYPE_PHOTO = 2;
    static final int TYPE_VIDEO = 3;



    private static final int ITEM_COUNT = 512;
    private final List<TimelineItem> items;
    private final MediaPlayerManager delegate;
    private AlbadiyaTimelineFragment fragment;
    private PostsTimlineFragment timeline;
    private CompetitionTimlineFragment competition;

    public CompetitionTimlineAdapter() {
        this.delegate = new MediaPlayerManagerImpl();
        this.items = new ArrayList<>();
        for (int i = 0; i < ITEM_COUNT; i++) {
            items.add(new TimelineItem(ToroApp.getApp()));
        }
    }

    public CompetitionTimlineAdapter(ArrayList<TimelineItem> items, AlbadiyaTimelineFragment fragment,PostsTimlineFragment timeline,CompetitionTimlineFragment competition) {
        this.delegate = new MediaPlayerManagerImpl();
        this.items = items;
        this.fragment = fragment;
        this.timeline = timeline;
        this.competition = competition;

    }

    @NonNull
    @Override protected TimelineItem getItem(int position) {
        return items.get(position);
    }

    private CompetitionTimlineAdapter.ItemClickListener onItemClickListener;

    public void setOnItemClickListener(CompetitionTimlineAdapter.ItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final ViewHolder viewHolder = CompetitionTimelineViewHolder.createViewHolder(parent, viewType,fragment,timeline,competition);
        if (viewHolder instanceof OgpItemViewHolder) {
            viewHolder.setOnItemClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = viewHolder.getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION
                            && onItemClickListener != null
                            && v == ((OgpItemViewHolder) viewHolder).ogpView) {
                        onItemClickListener.onOgpItemClick(viewHolder, v,
                                (TimelineItem.OgpItem) getItem(position).getEmbedItem());
                    }
                }
            });
        } else if (viewHolder instanceof CompetitionVideoViewHolder) {
            // TODO Click to Video
            viewHolder.setOnItemClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = viewHolder.getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION
                            && onItemClickListener != null
                            && v == ((CompetitionVideoViewHolder) viewHolder).getPlayerView()) {
                        onItemClickListener.onVideoClick(viewHolder, v,
                                (TimelineItem.VideoItem) getItem(position).getEmbedItem());
                    }
                }
            });
        } else if (viewHolder instanceof CompetitionPhotoViewHolder) {
            // TODO Click to Photo
        }

        return viewHolder;
    }

    @Override public int getItemCount() {
        return items.size();
    }

    @Override public int firstVideoPosition() {
        int firstVideo = -1;
        for (int i = 0; i < items.size(); i++) {
            if (TimelineItem.VideoItem.class.getSimpleName()
                    .equals(getItem(i).getEmbedItem().getClassName())) {
                firstVideo = i;
                break;
            }
        }

        return firstVideo;
    }

    @Override public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override public int getItemViewType(int position) {
        String itemClassName = getItem(position).getEmbedItem().getClassName();
        return TimelineItem.VideoItem.class.getSimpleName().equals(itemClassName) ? TYPE_VIDEO
                : (TimelineItem.PhotoItem.class.getSimpleName().equals(itemClassName) ? TYPE_PHOTO
                : TYPE_OGP);
    }

    public static abstract class ItemClickListener implements OnItemClickListener {

        @Override
        public void onItemClick(RecyclerView.Adapter adapter, RecyclerView.ViewHolder viewHolder,
                                View view, int adapterPosition, long itemId) {

        }

        protected abstract void onOgpItemClick(RecyclerView.ViewHolder viewHolder, View view,
                                               TimelineItem.OgpItem item);

        protected abstract void onPhotoClick(RecyclerView.ViewHolder viewHolder, View view,
                                             TimelineItem.PhotoItem item);

        protected abstract void onVideoClick(RecyclerView.ViewHolder viewHolder, View view,
                                             TimelineItem.VideoItem item);
    }

    // MediaPlayerManager implementation

    @Nullable
    @Override public ToroPlayer getPlayer() {
        return delegate.getPlayer();
    }

    @Override public void setPlayer(ToroPlayer player) {
        delegate.setPlayer(player);
    }

    @Override public void onRegistered() {
        delegate.onRegistered();
    }

    @Override public void onUnregistered() {
        delegate.onUnregistered();
    }

    @Override public void startPlayback() {
        delegate.startPlayback();
    }

    @Override public void pausePlayback() {
        delegate.pausePlayback();
    }

    @Override public void stopPlayback() {
        delegate.stopPlayback();
    }

    @Override public void saveVideoState(String videoId, @Nullable Long position, long duration) {
        delegate.saveVideoState(videoId, position, duration);
    }

    @Override public void restoreVideoState(String videoId) {
        delegate.restoreVideoState(videoId);
    }

    @Nullable @Override public Long getSavedPosition(String videoId) {
        return delegate.getSavedPosition(videoId);
    }
}
