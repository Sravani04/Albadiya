package app.mamac.albadiya;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by T on 07-12-2016.
 */

public class InstaSearch extends Fragment {
    GridView gridView;
    InstaSearchAdapter instaSearchAdapter;
    ArrayList<String> images;
    ArrayList<String> title;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.instasearch_items,container,false);
        gridView  = (GridView) view.findViewById(R.id.insta_items);

        images = new ArrayList<>();
        title  = new ArrayList<>();

//        images.add(R.drawable.amazon);
//        images.add(R.drawable.flipkart);
//        images.add(R.drawable.timeline);
//        images.add(R.drawable.snapdeal);
//        images.add(R.drawable.yellowsoft);
//
//        title.add("amazon");
//        title.add("flipkart");
//        title.add("timeline");
//        title.add("snapdeal");
//        title.add("yellowsoft");

        images = getFilePaths();
        title = getFilePaths();

        instaSearchAdapter  = new InstaSearchAdapter(getActivity(),images,title);
        gridView.setAdapter(instaSearchAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(),title.get(position),Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    public ArrayList<String> getFilePaths()
    {


        Uri u = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Uri v = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Images.ImageColumns.DATA};
       String[] projection1 = {MediaStore.Video.VideoColumns.DATA};
        Cursor c = null;
        SortedSet<String> dirList = new TreeSet<String>();
        ArrayList<String> resultIAV = new ArrayList<String>();

        String[] directories = null;
        if (v != null)
        {
            c = getActivity().managedQuery(u, projection1, null, null, null);
        }

        if ((c != null) && (c.moveToFirst()))
        {
            do
            {
                String tempDir = c.getString(0);
                tempDir = tempDir.substring(0, tempDir.lastIndexOf("/"));
                try{
                    dirList.add(tempDir);
                }
                catch(Exception e)
                {

                }
            }
            while (c.moveToNext());
            directories = new String[dirList.size()];
            dirList.toArray(directories);

        }

        for(int i=0;i<dirList.size();i++)
        {
            File imageDir = new File(directories[i]);
            File[] imageList = imageDir.listFiles();
            if(imageList == null)
                continue;
            for (File imagePath : imageList) {
                try {

                    if(imagePath.isDirectory())
                    {
                        imageList = imagePath.listFiles();

                    }
//                    if ( imagePath.getName().contains(".jpg")|| imagePath.getName().contains(".JPG")
//                            || imagePath.getName().contains(".jpeg")|| imagePath.getName().contains(".JPEG")
//                            || imagePath.getName().contains(".png") || imagePath.getName().contains(".PNG")
//                            || imagePath.getName().contains(".gif") || imagePath.getName().contains(".GIF")
//                            || imagePath.getName().contains(".bmp") || imagePath.getName().contains(".BMP")
//                            )
//                    {
                    if ( imagePath.getName().contains(".mp4")|| imagePath.getName().contains(".MP4")
                            || imagePath.getName().contains(".mpe4")|| imagePath.getName().contains(".MPE4")
                            )
                    {



                        String path= imagePath.getAbsolutePath();
                        resultIAV.add(path);

                    }
                }
                //  }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return resultIAV;


    }





}
