package app.mamac.albadiya;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by T on 19-12-2016.
 */

public class CategoryFragment extends Fragment {
    TextView contestants;
    TextView subscribe;
    TextView results;
    TextView news;
    FrameLayout fragment_contest;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.category_items,container,false);
        contestants     = (TextView) view.findViewById(R.id.contestants);
        subscribe       = (TextView) view.findViewById(R.id.subscribe);
        results         = (TextView) view.findViewById(R.id.results);
        news            = (TextView) view.findViewById(R.id.news);
        fragment_contest = (FrameLayout) view.findViewById(R.id.fragment_contest);

        reset_color(1);
        InstaContestants instaContestants = new InstaContestants();
        getFragmentManager().beginTransaction().replace(R.id.fragment_contest,instaContestants).commit();

        contestants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset_color(1);
                InstaContestants instaContestants = new InstaContestants();
                getFragmentManager().beginTransaction().replace(R.id.fragment_contest,instaContestants).commit();
            }
        });

        subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset_color(1);
                InstaContestants instaContestants = new InstaContestants();
                getFragmentManager().beginTransaction().replace(R.id.fragment_contest,instaContestants).commit();
//                reset_color(2);
//                InstaSubscribe instaSubscribe = new InstaSubscribe();
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("result","FAILURE");
//                instaSubscribe.setArguments(bundle);
//                getFragmentManager().beginTransaction().replace(R.id.fragment_contest,instaSubscribe).commit();
            }
        });

        results.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset_color(3);
                InstaResults instaResults = new InstaResults();
                getFragmentManager().beginTransaction().replace(R.id.fragment_contest,instaResults).commit();
            }
        });

        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset_color(4);
                InstaNews instaNews = new InstaNews();
                getFragmentManager().beginTransaction().replace(R.id.fragment_contest,instaNews).commit();
            }
        });
        return view;
    }

    private void reset_color(int pos){
        contestants.setBackgroundColor(Color.parseColor("#f6ef98"));
        subscribe.setBackgroundColor(Color.parseColor("#f6ef98"));
        results.setBackgroundColor(Color.parseColor("#f6ef98"));
        news.setBackgroundColor(Color.parseColor("#f6ef98"));

        switch (pos){
            case 1:
                contestants.setBackgroundColor(Color.parseColor("#ffffff"));
                break;
            case 2:
                subscribe.setBackgroundColor(Color.parseColor("#ffffff"));
                break;
            case 3:
                results.setBackgroundColor(Color.parseColor("#ffffff"));
                break;
            case 4:
                news.setBackgroundColor(Color.parseColor("#ffffff"));
                break;
        }
    }



}
