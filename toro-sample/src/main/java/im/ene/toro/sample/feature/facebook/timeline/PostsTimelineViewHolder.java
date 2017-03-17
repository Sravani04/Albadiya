package im.ene.toro.sample.feature.facebook.timeline;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import im.ene.toro.ToroAdapter;
import im.ene.toro.sample.feature.facebook.AlbadiyaTimelineFragment;
import im.ene.toro.sample.feature.facebook.PostsTimlineFragment;

/**
 * Created by T on 17-03-2017.
 */

public class PostsTimelineViewHolder extends ToroAdapter.ViewHolder {

    private static LayoutInflater inflater;

    public PostsTimelineViewHolder(View itemView) {
        super(itemView);

    }

    @Override public void onAttachedToWindow() {

    }

    @Override public void onDetachedFromWindow() {

    }

    @Override
    public void bind(RecyclerView.Adapter adapter, @Nullable Object object) {

    }

    static ToroAdapter.ViewHolder createViewHolder(ViewGroup parent, int type, AlbadiyaTimelineFragment fragment, PostsTimlineFragment timeline) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }

        final ToroAdapter.ViewHolder viewHolder;
        final View view;
        switch (type) {
            case PostsTimelineAdapter.TYPE_OGP:
                view = inflater.inflate(OgpItemViewHolder.LAYOUT_RES, parent, false);
                viewHolder = new OgpItemViewHolder(view);
                break;
            case PostsTimelineAdapter.TYPE_VIDEO:
                view = inflater.inflate(PostsVideoViewHolder.LAYOUT_RES, parent, false);
                viewHolder = new PostsVideoViewHolder(view,fragment,timeline);
                break;
            case PostsTimelineAdapter.TYPE_PHOTO:
            default:
                view = inflater.inflate(PostsPhotoViewHolder.LAYOUT_RES, parent, false);
                viewHolder = new PostsPhotoViewHolder(view,fragment,timeline);
                break;
        }

        return viewHolder;
    }
}
