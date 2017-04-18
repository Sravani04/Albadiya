package app.mamac.albadiya;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

import app.mamac.albadiya.life.knowledge4.videotrimmersample.TrimmerActivity;

/**
 * Created by mac on 12/12/16.
 */

public class TakeGalleryFragment extends Fragment{
    GridView gridView;
    InstaSearchAdapter instaSearchAdapter;
    ArrayList<String> images;
    ArrayList<String> title;
    ProgressBar progressBar;
    PostFragment postFragment;
    boolean isimages = true;
    TextView video_btn,image_btn;
    public static final String EXTRA_VIDEO_PATH = "EXTRA_VIDEO_PATH";
    private static final int REQUEST_VIDEO_TRIMMER = 0x01;
    private static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.take_gallery_layout, container, false);
        video_btn = (TextView) view.findViewById(R.id.video_btn);
        image_btn = (TextView) view.findViewById(R.id.image_btn);
        gridView  = (GridView) view.findViewById(R.id.insta_items);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        images = new ArrayList<>();
        title  = new ArrayList<>();
        instaSearchAdapter  = new InstaSearchAdapter(getActivity(),images,images);
        gridView.setAdapter(instaSearchAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (images.get(position).endsWith(".jpg")  || images.get(position).endsWith(".PNG")  || images.get(position).endsWith(".JPG")
                        || images.get(position).endsWith(".JPEG") || images.get(position).endsWith(".png") || images.get(position).endsWith(".jpeg")
                        || images.get(position).endsWith(".gif") || images.get(position).endsWith(".GIF") || images.get(position).endsWith(".BMP") || images.get(position).endsWith("bmp")) {
                    postFragment.onGallerySelected(images.get(position));
                }else if (images.get(position).endsWith(".mp4")) {
//                Intent intent = new Intent(getActivity(), app.mamac.albadiya.life.knowledge4.videotrimmersample.MainActivity.class);
//                startActivity(intent);
                    Intent intent = new Intent(getActivity(), TrimmerActivity.class);
                    Log.e("path1", images.toString());
                    intent.putExtra(EXTRA_VIDEO_PATH, images.get(position));
                    startActivity(intent);
                }
            }
        });

        image_btn.setBackgroundColor(Color.parseColor("#e6d82e"));
        isimages = true;
        new getimagestask().execute();

        video_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                video_btn.setBackgroundColor(Color.parseColor("#e6d82e"));
                image_btn.setBackgroundColor(Color.parseColor("#FEFCDD"));
                isimages = false;
                new getimagestask().execute();
            }
        });

        image_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_btn.setBackgroundColor(Color.parseColor("#e6d82e"));
                video_btn.setBackgroundColor(Color.parseColor("#FEFCDD"));
                isimages = true;
                new getimagestask().execute();
            }
        });

        postFragment = (PostFragment)getParentFragment();
        new getimagestask().execute();
        return view;
    }

    public ArrayList<String> getImagePaths()
    {
        Uri u = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Images.ImageColumns.DATA};
        Cursor c = null;
        SortedSet<String> dirList = new TreeSet<String>();
        ArrayList<String> resultIAV = new ArrayList<String>();

        String[] directories = null;
        if (u != null)
        {
            c = getActivity().managedQuery(u, projection, null, null, null);
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


        for(int i=0;i<dirList.size();i++) {
            File imageDir = new File(directories[i]);
            File[] imageList = imageDir.listFiles();
            if (imageList == null)
                continue;
            for (File imagePath : imageList) {

                    try {

                        if (imagePath.isDirectory()) {
                            imageList = imagePath.listFiles();

                        }

                            if (imagePath.getName().endsWith(".jpg") || imagePath.getName().endsWith(".JPG")
                                || imagePath.getName().endsWith(".jpeg") || imagePath.getName().endsWith(".JPEG")
                                || imagePath.getName().endsWith(".png") || imagePath.getName().endsWith(".PNG")
                                || imagePath.getName().endsWith(".gif") || imagePath.getName().endsWith(".GIF")
                                || imagePath.getName().endsWith(".bmp") || imagePath.getName().endsWith(".BMP")
                                )

                        {

                            String path = imagePath.getAbsolutePath();
                            resultIAV.add(path);
                            Log.e("path", path);
//                            if (resultIAV.size() > 10)
//                                return resultIAV;

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

    public ArrayList<String> getVideoPaths()
    {
        Uri u = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Images.ImageColumns.DATA};
        Cursor c = null;
        SortedSet<String> dirList = new TreeSet<String>();
        ArrayList<String> resultIAV = new ArrayList<String>();

        String[] directories = null;
        if (u != null)
        {
            c = getActivity().managedQuery(u, projection, null, null, null);
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


        for(int i=0;i<dirList.size();i++) {
            File imageDir = new File(directories[i]);
            File[] imageList = imageDir.listFiles();
            if (imageList == null)
                continue;
            for (File imagePath : imageList) {

                try {

                    if (imagePath.isDirectory()) {
                        imageList = imagePath.listFiles();

                    }

                    if (imagePath.getName().endsWith(".mp4") || imagePath.getName().endsWith(".MP4")
                            || imagePath.getName().endsWith(".mpeg4") || imagePath.getName().endsWith(".MPEG4")
                            )

                    {
                        String path = imagePath.getAbsolutePath();
                        resultIAV.add(path);
                        Log.e("path", path);
//                        if (resultIAV.size() > 10)
//                            return resultIAV;

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


    private class getimagestask extends AsyncTask {

        @Override
        protected Objects doInBackground(Object[] objects) {
            images.clear();

            if (isimages)
            images  = getImagePaths();
            else
            images = getVideoPaths();
            // title = getFilePaths();

            return null;
        }


        protected void onPostExecute(Object result) {
            progressBar.setVisibility(View.GONE);

            instaSearchAdapter  = new InstaSearchAdapter(getActivity(),images,images);
            gridView.setAdapter(instaSearchAdapter);

            instaSearchAdapter.notifyDataSetChanged();


        }


    }



}