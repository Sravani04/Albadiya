package im.ene.toro.sample.feature.facebook.timeline;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import im.ene.toro.exoplayer2.ExoVideoView;
import im.ene.toro.exoplayer2.ExoVideoViewHolder;
import im.ene.toro.sample.R;
import im.ene.toro.sample.feature.facebook.AlbadiyaTimelineFragment;
import im.ene.toro.sample.feature.facebook.CompetitionTimlineFragment;
import im.ene.toro.sample.feature.facebook.PostsTimlineFragment;
import im.ene.toro.sample.util.Util;

/**
 * Created by T on 24-03-2017.
 */

public class CompetitionVideoViewHolder extends ExoVideoViewHolder {

    static final int LAYOUT_RES = R.layout.vh_fb_feed_post_video;

    private TimelineItem.VideoItem videoItem;
    private ImageView mThumbnail;
    private TextView mInfo;
    private CircleImageView mThumbnailUser;
    private TextView mInfoUser;
    private TextView description;
    private TextView time;
    private ImageView user_like;
    private ImageView download,like_image,view_image;
    private ImageView share_it;
    private TextView no_of_likes,like_text,view_text;
    private TextView no_of_views;
    private TextView seconds;
    private PostsTimlineFragment timeline;
    private ImageView delete_btn;
    String post_id;
    private CompetitionTimlineFragment competiton;


    public CompetitionVideoViewHolder(View itemView) {
        super(itemView);
        mThumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
        mInfo = (TextView) itemView.findViewById(R.id.info);
        mThumbnailUser = (CircleImageView) itemView.findViewById(R.id.tumbnailuser);
        mInfoUser = (TextView) itemView.findViewById(R.id.infouser);
        description = (TextView) itemView.findViewById(R.id.description);
        time = (TextView) itemView.findViewById(R.id.time);
        user_like = (ImageView) itemView.findViewById(R.id.user_like);
        download = (ImageView) itemView.findViewById(R.id.download);
        // share_it = (ImageView) itemView.findViewById(R.id.share_it);
        no_of_likes = (TextView) itemView.findViewById(R.id.no_of_likes);
        no_of_views = (TextView) itemView.findViewById(R.id.no_of_views);
        seconds = (TextView) itemView.findViewById(R.id.seconds);
        delete_btn = (ImageView) itemView.findViewById(R.id.delete_btn);
        like_image = (ImageView) itemView.findViewById(R.id.like_image);
        view_image = (ImageView) itemView.findViewById(R.id.view_image);
        like_text = (TextView) itemView.findViewById(R.id.like_text);
        view_text = (TextView) itemView.findViewById(R.id.view_text);

        time.setVisibility(View.GONE);
        delete_btn.setVisibility(View.GONE);
        user_like.setVisibility(View.GONE);
        download.setVisibility(View.GONE);
        no_of_likes.setVisibility(View.GONE);
        no_of_views.setVisibility(View.GONE);
        like_image.setVisibility(View.GONE);
        view_image.setVisibility(View.GONE);
        like_text.setVisibility(View.GONE);
        view_text.setVisibility(View.GONE);


    }
    public CompetitionVideoViewHolder(View itemView, AlbadiyaTimelineFragment fragment, PostsTimlineFragment timeline,CompetitionTimlineFragment competition) {
        super(itemView);
        mThumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
        mInfo = (TextView) itemView.findViewById(R.id.info);
        mThumbnailUser = (CircleImageView) itemView.findViewById(R.id.tumbnailuser);
        mInfoUser = (TextView) itemView.findViewById(R.id.infouser);
        description = (TextView) itemView.findViewById(R.id.description);
        time = (TextView) itemView.findViewById(R.id.time);
        user_like = (ImageView) itemView.findViewById(R.id.user_like);
        download = (ImageView) itemView.findViewById(R.id.download);
        // share_it = (ImageView) itemView.findViewById(R.id.share_it);
        no_of_likes = (TextView) itemView.findViewById(R.id.no_of_likes);
        no_of_views = (TextView) itemView.findViewById(R.id.no_of_views);
        seconds = (TextView) itemView.findViewById(R.id.seconds);
        delete_btn = (ImageView) itemView.findViewById(R.id.delete_btn);
        like_image = (ImageView) itemView.findViewById(R.id.like_image);
        view_image = (ImageView) itemView.findViewById(R.id.view_image);
        like_text = (TextView) itemView.findViewById(R.id.like_text);
        view_text = (TextView) itemView.findViewById(R.id.view_text);
        this.timeline = timeline;
        this.competiton = competition;

        time.setVisibility(View.GONE);
        delete_btn.setVisibility(View.GONE);
        user_like.setVisibility(View.GONE);
        download.setVisibility(View.GONE);
        no_of_likes.setVisibility(View.GONE);
        no_of_views.setVisibility(View.GONE);
        like_image.setVisibility(View.GONE);
        view_image.setVisibility(View.GONE);
        like_text.setVisibility(View.GONE);
        view_text.setVisibility(View.GONE);


    }



    @Override protected ExoVideoView findVideoView(View itemView) {
        return (ExoVideoView) itemView.findViewById(R.id.video);
    }


    @Override public void bind(RecyclerView.Adapter adapter, @Nullable final Object object) {
        if (!(object instanceof TimelineItem)
                || !(((TimelineItem) object).getEmbedItem() instanceof TimelineItem.VideoItem)) {
            throw new IllegalArgumentException("Only VideoItem is accepted");
        }

        this.videoItem = (TimelineItem.VideoItem) ((TimelineItem) object).getEmbedItem();
        this.videoView.setMedia(Uri.parse(videoItem.getVideoUrl()));
        this.mInfoUser.setText((((TimelineItem) object).getAuthor().getUserName()));
        this.description.setText(((TimelineItem) object).getAuthor().getUserDescription());
        Picasso.with(itemView.getContext()).load(((TimelineItem) object).getAuthor().getUserUrl()).into(mThumbnailUser);
        this.time.setText(((TimelineItem) object).getAuthor().getUserTime());


        if (((TimelineItem) object).getAuthor().getPersonId().equals(Settings.GetUserId(itemView.getContext()))){
            delete_btn.setVisibility(View.VISIBLE);
        }else {
            delete_btn.setVisibility(View.GONE);
        }

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete_popup();
            }
        });




        post_id = ((TimelineItem) object).getAuthor().getUserId();



        mThumbnailUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                competiton.go_to_user_profile(((TimelineItem) object).getAuthor().getPersonId());
            }
        });

        mInfoUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                competiton.go_to_user_profile(((TimelineItem) object).getAuthor().getPersonId());
            }
        });

        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                competiton.go_to_vote_page(mThumbnail);
            }
        });


    }

    @Override public void setOnItemClickListener(View.OnClickListener listener) {
        super.setOnItemClickListener(listener);
        mInfo.setOnClickListener(listener);
        this.videoView.setOnClickListener(listener);
    }


    @Nullable @Override public String getMediaId() {
        return Util.genVideoId(this.videoItem.getVideoUrl(), getAdapterPosition());
    }

    @Override public void onVideoPreparing() {
        super.onVideoPreparing();
        mInfo.setText("Preparing");
    }

//  @Override public void onVideoPrepared() {
//    super.onVideoPrepared();
//    mInfo.setText("Prepared");
//  }

    @Override public void onViewHolderBound() {
        super.onViewHolderBound();
        Picasso.with(itemView.getContext())
                .load(R.drawable.video_play_button)
                .resize(400, 300 )
                .into(mThumbnail);
        mInfo.setText("Bound");
    }

    @Override public void onPlaybackStarted() {
        mThumbnail.animate().alpha(0.f).setDuration(250).setListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationEnd(Animator animation) {
                CompetitionVideoViewHolder.super.onPlaybackStarted();
            }
        }).start();
        mInfo.setText("Started");
    }

    @Override public void onPlaybackPaused() {
        mThumbnail.animate().alpha(1.f).setDuration(250).setListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationEnd(Animator animation) {
                CompetitionVideoViewHolder.super.onPlaybackPaused();
            }
        }).start();
        mInfo.setText("Paused");
    }

    @Override public void onPlaybackCompleted() {
        mThumbnail.animate().alpha(1.f).setDuration(250).setListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationEnd(Animator animation) {
                CompetitionVideoViewHolder.super.onPlaybackCompleted();
            }
        }).start();
        mInfo.setText("Completed");
    }

    @Override public boolean onPlaybackError(Exception error) {
        mThumbnail.animate().alpha(1.f).setDuration(0).setListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationEnd(Animator animation) {
                // Immediately finish the animation.
            }
        }).start();
        mInfo.setText("Error: videoId = " + getMediaId());
        return super.onPlaybackError(error);
    }

    public void delete_popup() {
        AlertDialog.Builder builder2 = new AlertDialog.Builder(itemView.getContext());
        builder2.setMessage("Delete post?");
        builder2.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Ion.with(itemView.getContext())
                        .load("http://naqshapp.com/albadiya/api/post-delete.php")
                        .setBodyParameter("member_id", Settings.GetUserId(itemView.getContext()))
                        .setBodyParameter("post_id", post_id)
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                try {
                                    if (result.get("status").getAsString().equals("Success")) {
                                        Toast.makeText(itemView.getContext(), result.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                                        competiton.delete_post(post_id);
                                    } else {
                                        Toast.makeText(itemView.getContext(), result.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception ex) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }
        });
        builder2.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//        onViewHolderBound();
                Toast.makeText(itemView.getContext(), "U Clicked Cancel ", Toast.LENGTH_LONG).show();
            }

        });

        builder2.show();
    }
}
