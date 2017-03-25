package im.ene.toro.sample.feature.facebook.timeline;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import im.ene.toro.sample.R;
import im.ene.toro.sample.feature.facebook.AlbadiyaTimelineFragment;
import im.ene.toro.sample.feature.facebook.CompetitionTimlineFragment;
import im.ene.toro.sample.feature.facebook.PostsTimlineFragment;

/**
 * Created by T on 24-03-2017.
 */

public class CompetitionPhotoViewHolder extends PostsTimelineViewHolder {

    static final int LAYOUT_RES = R.layout.vh_fb_feed_post_photo;

    private TimelineItem.PhotoItem photoItem;
    TimelineAdapter adapter;
    private ImageView mThumbnail;
    private TextView mInfo;
    private CircleImageView mThumbnailUser;
    private TextView mInfoUser;
    private TextView user_desc;
    private TextView time;
    private ImageView delete_btn;
    private ImageView user_like;
    private ImageView download,like_image,view_image;
    private ImageView share_it;
    private TextView no_of_likes,like_text,view_text;
    private TextView no_of_views;
    private TextView seconds;
    AlbadiyaTimelineFragment albadiyaTimelineFragment;
    String post_id;
    String member_id;
    PostsTimlineFragment postsTimlineFragment;
    CompetitionTimlineFragment competitionTimlineFragment;




    public CompetitionPhotoViewHolder(View itemView) {
        super(itemView);
        mThumbnail = (ImageView) itemView.findViewById(R.id.photoviewimage);
        mInfo = (TextView) itemView.findViewById(R.id.info);
        mThumbnailUser = (CircleImageView) itemView.findViewById(R.id.tumbnailuser);
        mInfoUser = (TextView) itemView.findViewById(R.id.infouser);
        user_desc = (TextView) itemView.findViewById(R.id.user_desc);
        time  = (TextView) itemView.findViewById(R.id.time);
        user_like = (ImageView) itemView.findViewById(R.id.user_like);
        download = (ImageView) itemView.findViewById(R.id.download);
        // share_it = (ImageView) itemView.findViewById(R.id.share_it);
        no_of_likes = (TextView) itemView.findViewById(R.id.no_of_likes);
        no_of_views = (TextView) itemView.findViewById(R.id.no_of_views);
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
    public CompetitionPhotoViewHolder(View itemView,AlbadiyaTimelineFragment fragment,PostsTimlineFragment timeline,CompetitionTimlineFragment competiton) {
        super(itemView);
        mThumbnail = (ImageView) itemView.findViewById(R.id.photoviewimage);
        mInfo = (TextView) itemView.findViewById(R.id.info);
        mThumbnailUser = (CircleImageView) itemView.findViewById(R.id.tumbnailuser);
        mInfoUser = (TextView) itemView.findViewById(R.id.infouser);
        user_desc = (TextView) itemView.findViewById(R.id.user_desc);
        time  = (TextView) itemView.findViewById(R.id.time);
        user_like = (ImageView) itemView.findViewById(R.id.user_like);
        download = (ImageView) itemView.findViewById(R.id.download);
        // share_it = (ImageView) itemView.findViewById(R.id.share_it);
        no_of_likes = (TextView) itemView.findViewById(R.id.no_of_likes);
        no_of_views = (TextView) itemView.findViewById(R.id.no_of_views);
        delete_btn = (ImageView) itemView.findViewById(R.id.delete_btn);
        like_image = (ImageView) itemView.findViewById(R.id.like_image);
        view_image = (ImageView) itemView.findViewById(R.id.view_image);
        like_text = (TextView) itemView.findViewById(R.id.like_text);
        view_text = (TextView) itemView.findViewById(R.id.view_text);
        albadiyaTimelineFragment = fragment;
        postsTimlineFragment = timeline;
        competitionTimlineFragment = competiton;

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

    @Override public void bind(final RecyclerView.Adapter adapter, @Nullable final Object object) {
        if (!(object instanceof TimelineItem)
                || !(((TimelineItem) object).getEmbedItem() instanceof TimelineItem.PhotoItem)) {
            throw new IllegalArgumentException("Only PhotoItem is accepted");
        }

        this.photoItem = (TimelineItem.PhotoItem) ((TimelineItem) object).getEmbedItem();
        this.mInfoUser.setText((((TimelineItem) object).getAuthor().getUserName()));
        Picasso.with(itemView.getContext()).load((((TimelineItem) object).getAuthor().getUserUrl())).into(mThumbnailUser);
        this.user_desc.setText(((TimelineItem) object).getAuthor().getUserDescription());
        Picasso.with(itemView.getContext()).load(this.photoItem.getPhotoUrlstr()).into(mThumbnail);
        //this.time.setText(((TimelineItem) object).getAuthor().getUserTime());



        mThumbnailUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                competitionTimlineFragment.go_to_user_profile(((TimelineItem) object).getAuthor().getPersonId());
            }
        });

        mInfoUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                competitionTimlineFragment.go_to_user_profile(((TimelineItem) object).getAuthor().getPersonId());
            }
        });

        mThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                competitionTimlineFragment.go_to_vote_page(mThumbnail);
            }
        });



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
                                        competitionTimlineFragment.delete_post(post_id);
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
