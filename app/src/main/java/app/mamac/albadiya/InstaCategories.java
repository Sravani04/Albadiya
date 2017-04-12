package app.mamac.albadiya;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by T on 08-12-2016.
 */

public class InstaCategories extends Fragment {
    ImageView contestants_icon,likes_icon;
    FrameLayout fragment_category;
    TextView en_lang,ar_lang;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.insta_categories,container,false);
        contestants_icon = (ImageView) view.findViewById(R.id.contestants_icon);
        likes_icon    = (ImageView) view.findViewById(R.id.likes_icon);
        fragment_category = (FrameLayout) view.findViewById(R.id.fragment_category);
        en_lang = (TextView) view.findViewById(R.id.en_lang);
        ar_lang = (TextView) view.findViewById(R.id.ar_lang);
        ar_lang.setText("Ar");
        ar_lang.setVisibility(View.VISIBLE);
        en_lang.setVisibility(View.GONE);

        ar_lang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                en_lang.setText("En");
                en_lang.setVisibility(View.VISIBLE);
                ar_lang.setVisibility(View.GONE);
                view.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            }
        });

        en_lang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ar_lang.setText("Ar");
                ar_lang.setVisibility(View.VISIBLE);
                en_lang.setVisibility(View.GONE);
                view.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            }
        });

        reset_icons(1);
        final CategoryFragment categoryFragment = new CategoryFragment();
        getFragmentManager().beginTransaction().replace(R.id.fragment_category,categoryFragment).commit();

        contestants_icon.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               reset_icons(1);
               CategoryFragment categoryFragment = new CategoryFragment();
               getFragmentManager().beginTransaction().replace(R.id.fragment_category,categoryFragment).commit();
           }
       });

        likes_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset_icons(2);
                LikesFragment likesFragment = new LikesFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_category,likesFragment).commit();
            }
        });
        return view;
    }

    private void reset_icons(int pos){
        contestants_icon.setImageResource(R.drawable.ic_contest_in);
        likes_icon.setImageResource(R.drawable.without);

        switch (pos){
            case 1:
                contestants_icon.setImageResource(R.drawable.ic_contests);
                break;
            case 2:
                likes_icon.setImageResource(R.drawable.with);
                break;
        }
    }


}
