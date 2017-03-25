package im.ene.toro.sample.feature.facebook.timeline;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import im.ene.toro.ToroAdapter;
import im.ene.toro.sample.feature.facebook.AlbadiyaTimelineFragment;
import im.ene.toro.sample.feature.facebook.CompetitionTimlineFragment;
import im.ene.toro.sample.feature.facebook.PostsTimlineFragment;

/**
 * Created by T on 24-03-2017.
 */

public class CompetitionTimelineViewHolder extends ToroAdapter.ViewHolder {

    private static LayoutInflater inflater;

    public CompetitionTimelineViewHolder(View itemView) {
        super(itemView);

    }

    @Override public void onAttachedToWindow() {

    }

    @Override public void onDetachedFromWindow() {

    }

    @Override
    public void bind(RecyclerView.Adapter adapter, @Nullable Object object) {

    }

    static ToroAdapter.ViewHolder createViewHolder(ViewGroup parent, int type, AlbadiyaTimelineFragment fragment, PostsTimlineFragment timeline, CompetitionTimlineFragment competition) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }

        final ToroAdapter.ViewHolder viewHolder;
        final View view;
        switch (type) {
            case CompetitionTimlineAdapter.TYPE_OGP:
                view = inflater.inflate(OgpItemViewHolder.LAYOUT_RES, parent, false);
                viewHolder = new OgpItemViewHolder(view);
                break;
            case CompetitionTimlineAdapter.TYPE_VIDEO:
                view = inflater.inflate(CompetitionVideoViewHolder.LAYOUT_RES, parent, false);
                viewHolder = new CompetitionVideoViewHolder(view,fragment,timeline,competition);
                break;
            case CompetitionTimlineAdapter.TYPE_PHOTO:
            default:
                view = inflater.inflate(CompetitionPhotoViewHolder.LAYOUT_RES, parent, false);
                viewHolder = new CompetitionPhotoViewHolder(view,fragment,timeline,competition);
                break;
        }

        return viewHolder;
    }
}
