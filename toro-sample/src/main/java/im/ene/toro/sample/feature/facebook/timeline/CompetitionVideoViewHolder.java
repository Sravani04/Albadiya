package im.ene.toro.sample.feature.facebook.timeline;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

    static final int LAYOUT_RES = R.layout.competition_post_video;

    private TimelineItem.VideoItem videoItem;
    private ImageView mThumbnail;
    private TextView mInfo;
    private CircleImageView mThumbnailUser;
    private TextView mInfoUser;
    private TextView description;
    private TextView time;
    private PostsTimlineFragment timeline;
    private TextView vote_btn;
    String post_id;
    private CompetitionTimlineFragment competiton;
    private ImageView sound_btn;
    boolean isvolume = true;



    public CompetitionVideoViewHolder(View itemView) {
        super(itemView);
        mThumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
        mInfo = (TextView) itemView.findViewById(R.id.info);
        mThumbnailUser = (CircleImageView) itemView.findViewById(R.id.tumbnailuser);
        mInfoUser = (TextView) itemView.findViewById(R.id.infouser);
        description = (TextView) itemView.findViewById(R.id.description);
        time = (TextView) itemView.findViewById(R.id.time);
        vote_btn = (TextView) itemView.findViewById(R.id.vote_btn);
        time.setVisibility(View.GONE);
        sound_btn = (ImageView) itemView.findViewById(R.id.sound_btn);


    }
    public CompetitionVideoViewHolder(View itemView, AlbadiyaTimelineFragment fragment, PostsTimlineFragment timeline,CompetitionTimlineFragment competition) {
        super(itemView);
        mThumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
        mInfo = (TextView) itemView.findViewById(R.id.info);
        mThumbnailUser = (CircleImageView) itemView.findViewById(R.id.tumbnailuser);
        mInfoUser = (TextView) itemView.findViewById(R.id.infouser);
        description = (TextView) itemView.findViewById(R.id.description);
        time = (TextView) itemView.findViewById(R.id.time);
        vote_btn = (TextView) itemView.findViewById(R.id.vote_btn);
        this.timeline = timeline;
        this.competiton = competition;
        time.setVisibility(View.GONE);
        sound_btn = (ImageView) itemView.findViewById(R.id.sound_btn);


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

       post_id =((TimelineItem) object).getAuthor().getCompetitionId();

        if(((TimelineItem) object).getAuthor().getPersonId().equals("-1"))
            vote_status();
        else{

            vote_btn.setText("Vote");
            vote_btn.setText("Vote");
        }
        vote_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Ion.with(itemView.getContext())
                        .load(Settings.SERVER_URL + "vote.php")
                        .setBodyParameter("member_id",((TimelineItem) object).getAuthor().getPersonId())
                        .setBodyParameter("image_id",((TimelineItem) object).getAuthor().getUserId())
                        .setBodyParameter("competition_id",post_id)
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                Log.e("voteresponse",result.toString());
                                if (result.get("status").getAsString().equals("Success")){
                                    Toast.makeText(itemView.getContext(),result.get("message").getAsString(),Toast.LENGTH_SHORT).show();
                                    vote_status();
                                }else{
                                    Toast.makeText(itemView.getContext(),result.get("message").getAsString(),Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        });

        videoView.setVolume(0f);
        sound_btn.setImageResource(R.drawable.mute_volume);
        Log.e("sound","sound muted");
        isvolume = true;

        sound_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isvolume) {
                    videoView.setVolume(0.9f);
                    sound_btn.setImageResource(R.drawable.voulme);
                    isvolume = false;
                    Log.e("sound", "sound on");
                }else {
                    videoView.setVolume(0f);
                    sound_btn.setImageResource(R.drawable.mute_volume);
                    Log.e("sound","sound muted");
                    isvolume = true;
                }
            }
        });




    }

    String cnt="0";
    public void vote_status(){
        Ion.with(itemView.getContext())
                .load(Settings.SERVER_URL + "vote-status.php")
                .setBodyParameter("member_id",Settings.GetUserId(itemView.getContext()))
                .setBodyParameter("competition_id",post_id)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        Log.e("votestatus",result.toString());
                        if (result.get("status").getAsString().equals("Success")){
                            cnt = result.get("cnt").getAsString();
                            if (!cnt.equals("0")){
                                vote_btn.setText("Voted");
                                vote_btn.setText("Voted");
                            }else {
                                vote_btn.setText("Vote");
                                vote_btn.setText("Vote");
                            }
                        }

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
        //mInfo.setText("Preparing");
    }

  @Override public void onVideoPrepared() {
    super.onVideoPrepared();
   // mInfo.setText("Prepared");
  }

    @Override public void onViewHolderBound() {
        super.onViewHolderBound();
        Picasso.with(itemView.getContext())
                .load(R.drawable.video_play_button)
                .resize(400, 300 )
                .into(mThumbnail);
        //mInfo.setText("Bound");
    }

    @Override public void onPlaybackStarted() {
        mThumbnail.animate().alpha(0.f).setDuration(250).setListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationEnd(Animator animation) {
                CompetitionVideoViewHolder.super.onPlaybackStarted();
            }
        }).start();
       // mInfo.setText("Started");
    }

    @Override public void onPlaybackPaused() {
        mThumbnail.animate().alpha(1.f).setDuration(250).setListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationEnd(Animator animation) {
                CompetitionVideoViewHolder.super.onPlaybackPaused();
            }
        }).start();
        //mInfo.setText("Paused");
    }

    @Override public void onPlaybackCompleted() {
        mThumbnail.animate().alpha(1.f).setDuration(250).setListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationEnd(Animator animation) {
                CompetitionVideoViewHolder.super.onPlaybackCompleted();
            }
        }).start();
        //mInfo.setText("Completed");
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






}
