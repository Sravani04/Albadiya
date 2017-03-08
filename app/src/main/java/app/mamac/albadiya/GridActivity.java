package app.mamac.albadiya;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;



import java.util.ArrayList;

/**
 * Created by T on 11-11-2016.
 */

public class GridActivity extends AppCompatActivity {
    GridView gridView;
    GridAdapter gridAdapter;
    ArrayList<String> names;
    ArrayList<String> links;
    ArrayList<Integer> images;
    ArrayList<Integer> logos;
    ArrayList<String> description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gridview_example);
        gridView = (GridView) findViewById(R.id.gridview);

        names = new ArrayList<>();
        links = new ArrayList<>();
        images = new ArrayList<>();
        logos  = new ArrayList<>();
        description = new ArrayList<>();

        names.add("Tamrind");
        names.add("Rasoie");
        names.add("Sweet Magic");
        names.add("Dolphins");
        names.add("Dominos");
        names.add("Choclate Room");
        names.add("KFC");

        links.add("http://www.tamarindrestaurant.in/");
        links.add("http://storendoor.com/order/rasoie-restaurant/");
        links.add("http://www.sweetmagic.co.in/");
        links.add("http://storendoor.com/order/dolphins/");
        links.add("http://www.dominos.co.in/");
        links.add("http://www.thechocolateroomindia.com/");
        links.add("https://online.kfc.co.in/");

        images.add(R.drawable.tamarind);
        images.add(R.drawable.rasoi);
        images.add(R.drawable.sweetmagic);
        images.add(R.drawable.dolphins);
        images.add(R.drawable.dominos);
        images.add(R.drawable.choclateroom);
        images.add(R.drawable.kfc);

        logos.add(R.drawable.tamrindlogo);
        logos.add(R.drawable.rasoilogo);
        logos.add(R.drawable.sweetmagiclogo);
        logos.add(R.drawable.dolphins);
        logos.add(R.drawable.dominoslogo);
        logos.add(R.drawable.choclateroomlogo);
        logos.add(R.drawable.kfclogo);

        description.add("A Spacious Privacy Dining Restaurant In Vijayawada Serving Chinese And Indian Cuisine. The Taste Of Food Leaves You Yearning For More!");
        description.add("A Spacious food in vijayawada");
        description.add("The History of Sweet Magic is a pursuit of excellence began 14 years back As pioneers in the segment of Sweets, Namkings, Bakery and Restaurants.. The group earned an eaviable reputation in the market. Recently we started Food Proceesing Industry in Auto Nagar, Vijayawada with International Standards, infact first in South India.");
        description.add("Dolphin Caters South Indian, North Indian, Chinese And Tandoor Cuisines. Food Is Prepared In Its See Through Hygienic Kitchen With Modern Amenities");
        description.add("Jubilant FoodWorks Limited (the Company) is a Jubilant Bhartia Group Company, The Company was incorporated in 1995 and initiated operations in 1996, The Company got listed on the Indian bourses in February 2010, Mr, Shyam S, Bhartia, Mr, Hari S, Bhartia and Jubilant Enpro Private Ltd, are the Promoters of the Company. The Company & its subsidiary operates Domino's Pizza brand with the exclusive rights for India, Nepal, Bangladesh and Sri Lanka, The Company is India's largest and fastest growing food service company, with a network of 1004 Domino’s Pizza restaurants across 230 cities (as of February 11, 2016).");
        description.add("");
        description.add("");




        gridAdapter = new GridAdapter(this,names,images);
        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(GridActivity.this,names.get(position),Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(GridActivity.this,ThirdActivity.class);
//                intent.putExtra("abc",names.get(position));
//                intent.putExtra("abcd",images.get(position));
//                intent.putExtra("mainlogo",logos.get(position));
//                intent.putExtra("abcdefgh",description.get(position));
//                startActivity(intent);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(links.get(position)));
                startActivity(intent);
            }
        });

    }
}
