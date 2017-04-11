package app.mamac.albadiya;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 * Created by T on 08-12-2016.
 */

public class InstaContestants extends Fragment {
    ListView listView;
    InstaContestantsAdapter instaContestantsAdapter;
    ArrayList<Integer> images;
    ArrayList<String>  names;
    ArrayList<String>  comments;
    ArrayList<Competitors> competitorsfrom_api;


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.insta_contestants_items,container,false);
        listView = (ListView) view.findViewById(R.id.contestants_items);

        images   = new ArrayList<>();
        names    = new ArrayList<>();
        comments = new ArrayList<>();
        competitorsfrom_api = new ArrayList<>();


        images.add(R.drawable.yellowsoft);
        images.add(R.drawable.housejoy);
        images.add(R.drawable.yahoo);
        images.add(R.drawable.amazon);

        names.add("Yellowsoft");
        names.add("Housejoy");
        names.add("Yahoo");
        names.add("Amazon");

        comments.add("Yellowsoft");
        comments.add("Housejoy");
        comments.add("Yahoo");
        comments.add("Amazon");

        instaContestantsAdapter = new InstaContestantsAdapter(getActivity(),competitorsfrom_api);
        listView.setAdapter(instaContestantsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
//                CompetitorsDetailPage competitorsDetailPage = new CompetitorsDetailPage();
//                Bundle bundle =new Bundle();
//                bundle.putSerializable("competitors",competitorsfrom_api.get(position));
//                bundle.putString("participants",String.valueOf(competitorsfrom_api.get(position).images.size()));
////                bundle.putSerializable("title",competitorsfrom_api.get(position).title);
////                bundle.putSerializable("image",competitorsfrom_api.get(position).image);
////                bundle.putSerializable("end_date",competitorsfrom_api.get(position).end_date);
////                bundle.putSerializable("participants",String.valueOf(competitorsfrom_api.get(position).images.size()));
////                bundle.putSerializable("id",competitorsfrom_api.get(position).id);
//                competitorsDetailPage.setArguments(bundle);
//                getFragmentManager().beginTransaction().replace(R.id.fragment_contest,competitorsDetailPage).commit();
                //Toast.makeText(getActivity(),names.get(position), Toast.LENGTH_SHORT).show();

//                Intent intent = new Intent(getActivity(),CompetitorsDetailPage.class);
//                intent.putExtra("competitors",competitorsfrom_api.get(position));
//                intent.putExtra("title",competitorsfrom_api.get(position).title);
//                intent.putExtra("image",competitorsfrom_api.get(position).image);
//                intent.putExtra("end_date",competitorsfrom_api.get(position).end_date);
//                intent.putExtra("participants",String.valueOf(competitorsfrom_api.get(position).images.size()));
//                intent.putExtra("id",competitorsfrom_api.get(position).id);
//                startActivity(intent);

                Ion.with(getContext())
                        .load("http://naqshapp.com/albadiya/api/member-subscription.php")
                        .setBodyParameter("member_id",Settings.GetUserId(getContext()))
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                try {
                                    if (result.get("subscription").getAsString().equals("Yes")) {
                                        Log.e("sub_response", result.get("subscription").getAsString());
                                        CompetitorsDetailPage competitorsDetailPage = new CompetitorsDetailPage();
                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable("competitors", competitorsfrom_api.get(position));
                                        bundle.putString("participants", String.valueOf(competitorsfrom_api.get(position).images.size()));
                                        competitorsDetailPage.setArguments(bundle);
                                        getFragmentManager().beginTransaction().replace(R.id.fragment_contest, competitorsDetailPage).commit();
                                    } else if (result.get("subscription").getAsString().equals("No")) {
                                        Log.e("sub_response", result.get("subscription").toString());
                                        InstaSubscribe instaSubscribe = new InstaSubscribe();
                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable("result","FAILURE");
                                        instaSubscribe.setArguments(bundle);
                                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contest, instaSubscribe).commit();
                                    }
                                }catch (Exception e1){
                                    e1.printStackTrace();
                                }
                            }
                        });

            }
        });
        get_competitors();
        return view;
    }



    public void get_competitors(){
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Ion.with(this)
                .load(Settings.SERVER_URL+"competitions.php")
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        try {
                            if (progressDialog!=null)
                                progressDialog.dismiss();

                            if (e != null) {
                                e.printStackTrace();
                            } else {
                                Log.e("response", String.valueOf(result.size()));
                                for (int i = 0; i < result.size(); i++) {
                                    Competitors competitors = new Competitors(result.get(i).getAsJsonObject(), getActivity());
                                    competitorsfrom_api.add(competitors);
                                }

                                instaContestantsAdapter.notifyDataSetChanged();

                            }

                        }catch (Exception ex){
                            e.printStackTrace();
                        }

                    }
                });
    }

}
