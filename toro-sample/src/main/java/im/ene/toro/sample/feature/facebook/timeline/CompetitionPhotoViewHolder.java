package im.ene.toro.sample.feature.facebook.timeline;

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

    static final int LAYOUT_RES = R.layout.competition_post_photo;

    private TimelineItem.PhotoItem photoItem;
    TimelineAdapter adapter;
    private ImageView mThumbnail;
    private TextView mInfo;
    private CircleImageView mThumbnailUser;
    private TextView mInfoUser;
    private TextView user_desc;
    private TextView time;
    private ImageView vote_btn;

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
        vote_btn = (ImageView) itemView.findViewById(R.id.vote_btn);
        time.setVisibility(View.GONE);

    }
    public CompetitionPhotoViewHolder(View itemView,AlbadiyaTimelineFragment fragment,PostsTimlineFragment timeline,CompetitionTimlineFragment competiton) {
        super(itemView);
        mThumbnail = (ImageView) itemView.findViewById(R.id.photoviewimage);
        mInfo = (TextView) itemView.findViewById(R.id.info);
        mThumbnailUser = (CircleImageView) itemView.findViewById(R.id.tumbnailuser);
        mInfoUser = (TextView) itemView.findViewById(R.id.infouser);
        user_desc = (TextView) itemView.findViewById(R.id.user_desc);
        time  = (TextView) itemView.findViewById(R.id.time);
        vote_btn = (ImageView) itemView.findViewById(R.id.vote_btn);
        albadiyaTimelineFragment = fragment;
        postsTimlineFragment = timeline;
        competitionTimlineFragment = competiton;

        time.setVisibility(View.GONE);
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

        post_id = ((TimelineItem) object).getAuthor().getUserId();

        if(!((TimelineItem) object).getAuthor().getPersonId().equals("-1"))
            vote_status();
        else{
            vote_btn.setImageResource(R.drawable.vote);
        }
        vote_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Ion.with(itemView.getContext())
                        .load(Settings.SERVER_URL + "vote.php")
                        .setBodyParameter("member_id",Settings.GetUserId(itemView.getContext()))
                        .setBodyParameter("image_id",((TimelineItem) object).getAuthor().getUserId())
                        .setBodyParameter("competition_id","2")
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
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


    }


    String cnt="0";
    public void vote_status(){
        Ion.with(itemView.getContext())
                .load(Settings.SERVER_URL + "vote-status.php")
                .setBodyParameter("member_id",Settings.GetUserId(itemView.getContext()))
                .setBodyParameter("competition_id","1")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result.get("status").getAsString().equals("Success")){
                            cnt = result.get("cnt").getAsString();
                            if (!cnt.equals("0")){
                                vote_btn.setImageResource(R.drawable.vote);
                            }else {
                                vote_btn.setImageResource(R.drawable.vote);
                            }
                        }

                    }
                });
    }



}
