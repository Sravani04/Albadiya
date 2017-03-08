package app.mamac.albadiya;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by T on 07-12-2016.
 */

public class InstaMembersAdapter extends BaseAdapter implements Filterable {
    LayoutInflater inflater;
    Context context;
    ArrayList<Members> members;
    ArrayList<Members> members_all;
//    ArrayList<Integer> mimages;
//    ArrayList<String> mtitle;
    public InstaMembersAdapter(Context context, ArrayList<Members> members){
        this.context = context;
        this.members_all = members;
        this.members = members;
//        mimages = images;
//        mtitle  = title;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return members.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override

    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item_view = inflater.inflate(R.layout.insta_search_members,null);
        CircleImageView image = (CircleImageView) item_view.findViewById(R.id.image);
        TextView name  = (TextView) item_view.findViewById(R.id.name);
        name.setText(members.get(position).name);
        //item_image.setImageResource(mimages.get(position));
//        Ion.with(context)
//                .load(members.get(position).image)
//                .withBitmap()
//                .placeholder(R.drawable.amazon)
//                .intoImageView(image);
//
        Picasso.with(context).load(members.get(position).image).into(image);

        return item_view;
    }


    @Override
    public Filter getFilter() {
        // return a filter that filters data based on a constraint

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    results.values = members_all;
                    results.count = members_all.size();
                }else{
                    ArrayList<Members> filteredContacts = new ArrayList<Members>();
                    for(int i=0;i<members_all.size();i++){
                        if(members_all.get(i).name.contains(constraint))
                            filteredContacts.add(members_all.get(i));
                    }
                    results.values = filteredContacts;
                    results.count = filteredContacts.size();


                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                members = (ArrayList<Members>) results.values;
                notifyDataSetChanged();

            }
        };
    }


}
