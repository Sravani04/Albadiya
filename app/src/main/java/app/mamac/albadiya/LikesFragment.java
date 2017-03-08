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
 * Created by T on 20-01-2017.
 */

public class LikesFragment extends Fragment {
    TextView following,you;
    FrameLayout like_fragment;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.like_items,container,false);
        following = (TextView) view.findViewById(R.id.following);
        you       = (TextView) view.findViewById(R.id.you);
        like_fragment = (FrameLayout) view.findViewById(R.id.like_fragment);

        reset_icons(2);
        FollowingFragment followingFragment = new FollowingFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type","2");
        followingFragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.like_fragment,followingFragment).commit();

        following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset_icons(1);
                FollowingFragment followingFragment = new FollowingFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("type","1");
                followingFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.like_fragment,followingFragment).commit();
            }
        });

        you.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset_icons(2);
                FollowingFragment followingFragment = new FollowingFragment();
                Bundle bundle = new Bundle();
                bundle.putString("type","2");
                followingFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.like_fragment,followingFragment).commit();
            }
        });

        return view;

    }

    public void reset_icons(int pos){

        following.setBackgroundColor(Color.parseColor("#f6ef98"));
        you.setBackgroundColor(Color.parseColor("#f6ef98"));

        switch (pos){
            case 1:
                following.setBackgroundColor(Color.parseColor("#ffffff"));
                break;
            case 2:
                you.setBackgroundColor(Color.parseColor("#ffffff"));
                break;
        }
    }
}
