package app.mamac.albadiya;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

import im.ene.toro.sample.feature.facebook.CompetitionTimlineFragment;

/**
 * Created by T on 19-12-2016.
 */

public class CompetitorsDetailPage extends Fragment {
    GridView gridView;
    CompetitorDetailPageAdapter competitorDetailPageAdapter;
    ImageView back_btn,competition_post;
    TextView item_name;
    ImageView item_image;
    TextView end_date;
    TextView participants;
    TextView add_btn;
    ArrayList<Integer> baners;
    String comp_id;
    String title;
    String image;
    String date;
    String participant;
    String images;
    Competitors competitersfrom_api;
    FrameLayout frame_one;
    String competition_id;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.competitors_detail_page,container,false);
        frame_one = (FrameLayout) view.findViewById(R.id.frame_one);

        baners = new ArrayList<>();
        baners.add(R.drawable.banner3);
        baners.add(R.drawable.timeline);
        baners.add(R.drawable.amazon);
        baners.add(R.drawable.banner1);
        baners.add(R.drawable.banner);

//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                //Toast.makeText(CompetitorsDetailPage.this,baners.get(position),Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(CompetitorsDetailPage.this,CompetitorsVoteActivity.class);
//                intent.putExtra("images",competitersfrom_api.images.get(position).image);
//                intent.putExtra("id",competitersfrom_api.id);
//                intent.putExtra("image_id",competitersfrom_api.images.get(position).id);
//                startActivity(intent);
//            }
//        });

        CompetitionTimlineFragment competitionTimlineFragment = new CompetitionTimlineFragment();
        Bundle bundle = new Bundle();
        bundle.putString("header", "0");
        bundle.putString("line", "0");
        bundle.putSerializable("competitors",competitersfrom_api);
        bundle.putString("competition_id",competition_id);
        competitionTimlineFragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.frame_one,competitionTimlineFragment).commit();


        item_name = (TextView) view.findViewById(R.id.item_name);
        item_image = (ImageView) view.findViewById(R.id.item_image);
        end_date  = (TextView) view.findViewById(R.id.end_date);
        participants = (TextView) view.findViewById(R.id.participants);
        add_btn   = (TextView) view.findViewById(R.id.add_btn);
        back_btn = (ImageView) view.findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),CompetitorAddPost.class);
                intent.putExtra("id",competitersfrom_api.id);
                startActivity(intent);
            }
        });

       if (getArguments()!=null && getArguments().containsKey("competitors")){
           competitersfrom_api  = (Competitors) getArguments().getSerializable("competitors");
           participant = getArguments().getString("participants");
           comp_id = competitersfrom_api.id;
           item_name.setText(competitersfrom_api.title);
           end_date.setText(competitersfrom_api.end_date);
           Ion.with(this)
                   .load(competitersfrom_api.image)
                   .withBitmap()
                   .placeholder(R.drawable.ic_profile)
                   .intoImageView(item_image);
           participants.setText(participant);
           competitorDetailPageAdapter = new CompetitorDetailPageAdapter(getContext(),competitersfrom_api);
//           gridView.setAdapter(competitorDetailPageAdapter);



       }
        return view;
    }
}
