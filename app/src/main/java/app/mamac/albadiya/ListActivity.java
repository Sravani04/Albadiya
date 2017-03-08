package app.mamac.albadiya;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.AdapterView;
import android.widget.ListView;
import android.view.View;
import android.widget.Toast;



import java.util.ArrayList;

/**
 * Created by T on 10-11-2016.
 */

public class ListActivity extends AppCompatActivity {
    ListView listView;
    ListviewAdapetrActivity listviewAdapetrActivity;
    ArrayList<String> names;
    ArrayList<Integer> images;
    ArrayList<String> links;
    ArrayList<String> description;
    ArrayList<String> foot;
    ArrayList<Integer> logos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_activity);
        listView = (ListView) findViewById(R.id.listview_activity);
        names = new ArrayList<>();
        images = new ArrayList<>();
        description = new ArrayList<>();
        foot = new ArrayList<>();
        logos = new ArrayList<>();
        links = new ArrayList<>();

        names.add("Welcome to Yellowsoft");
        names.add("Welcome to Housejoy");
        names.add("Welcome to Snapdeal");
        names.add("Welcome to Yahoo");
        names.add("Welcome to Flipkart");
        names.add("Welcome to Amazon");


        links.add("http://www.yellowsoft.in");
        links.add("http://www.housejoy.in");
        links.add("http://www.snapdeal.com");
        links.add("http://www.yellowsoft.in");
        links.add("http://www.yellowsoft.in");
        links.add("http://www.yellowsoft.in");


        description.add("Welcome to Yellow Soft, your number one source for services like  Web Designing Web Development Mobile Apps Development E Commerce Solutions Search Engine Optimization We’re dedicated to giving you the best, results, with a focus on three characteristics, i.e, dependability, customer service and uniqueness. We have recently initiated the services out, providing the best results and in turn gives you the  inspiration into a boom in your business.    We help the clients to transform and bloom in a changing world through operational leadership, and the co-creation of breakthrough solutions, which include the services in web designing, web development, apps applications and in SEO.");
        description.add("Housejoy is a pioneer in providing residential services related to day to day life in Bangalore, Mumbai, Hyderabad, Chennai, Pune and more cities. We are a dynamic team, having capable and trusted professionals who perform a wide variety of common home repairs and maintenance services. ");
        description.add("In February 2010, Kunal Bahl along with Rohit Bansal, started Snapdeal.com - India's largest online marketplace, with the widest assortment of 30 million plus products across 800 plus diverse categories from over 125,000 regional, national, and international brands and retailers.");
        description.add("Founded in 1994 by two Stanford PhD candidates, Jerry Yang and David Filo, Yahoo was the original guide to the Internet, connecting users with their passions and helping them to discover the mystery and promise of the World Wide Web. Rooted in that beginning we continue to be a guide to digital information, informing, connecting and entertaining our users with search, communications, and digital content products.");
        description.add("Flipkart is an online E-commerce portal that helps you in getting what you need be it a pen, Phone, T-shirts etc,. delivered at your door step or sometimes in your hand.");
        description.add("The Amazon.in marketplace is operated by Amazon Seller Services Private Ltd, an affiliate of Amazon.com, Inc. (NASDAQ: AMZN). Amazon.in seeks to build the most customer-centric online destination for customers to find and discover virtually anything they want to buy online by giving them more of what they want – vast selection, low prices, fast and reliable delivery, and a trusted and convenient experience; and provide sellers with a world-class e-commerce platform. ");


        images.add(R.drawable.yellowsoft);
        images.add(R.drawable.housejoy);
        images.add(R.drawable.snapdeal);
        images.add(R.drawable.yahoo);
        images.add(R.drawable.flipkart);
        images.add(R.drawable.amazon);

        foot.add("COPYRIGHT YELLOW SOFT 2014. ALL RIGHTS RESERVED.");
        foot.add("© Housejoy 2014 - 2016. All Rights Reserved");
        foot.add("Copyright © 2010, Jasper Infotech Private Limited. All Rights Reserved");
        foot.add("Yahoo 1995.All Rights Reserved");
        foot.add("Flipkart 2007.All Rights Reserved");
        foot.add("© 1996-2016, Amazon.com, Inc. or its affiliates");

        logos.add(R.drawable.yellowsoft);
        logos.add(R.drawable.housejoy);
        logos.add(R.drawable.snapdeal);
        logos.add(R.drawable.yahoo);
        logos.add(R.drawable.flipkart);
        logos.add(R.drawable.amazon);


       listviewAdapetrActivity = new ListviewAdapetrActivity(this,names,images);
        listView.setAdapter(listviewAdapetrActivity);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ListActivity.this,names.get(position),Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(ListActivity.this,ThirdActivity.class);
//                intent.putExtra("abc",names.get(position));
//                intent.putExtra("abcd",images.get(position));
//                intent.putExtra("abcde",description.get(position));
//                intent.putExtra("abcdef",foot.get(position));
//                intent.putExtra("abcdefg",logos.get(position));
//                startActivity(intent);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(links.get(position)));
                startActivity(i);

            }
        });
    }


}
