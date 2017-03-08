package app.mamac.albadiya;

import android.database.Cursor;
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
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by mac on 12/12/16.
 */

public class TakeGalleryFragment extends Fragment {
    GridView gridView;
    InstaSearchAdapter instaSearchAdapter;
    ArrayList<String> images;
    ArrayList<String> title;
    ProgressBar progressBar;
    PostFragment postFragment;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.take_gallery_layout, container, false);
        gridView  = (GridView) view.findViewById(R.id.insta_items);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        images = new ArrayList<>();
        title  = new ArrayList<>();
        instaSearchAdapter  = new InstaSearchAdapter(getActivity(),images,images);
        gridView.setAdapter(instaSearchAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                postFragment.onGallerySelected(images.get(position));

            }
        });
        postFragment = (PostFragment)getParentFragment();
        new getimagestask().execute();
        return view;
    }

    public ArrayList<String> getFilePaths()
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
                    if ( imagePath.getName().contains(".jpg")|| imagePath.getName().contains(".JPG")
                            || imagePath.getName().contains(".jpeg")|| imagePath.getName().contains(".JPEG")
                            || imagePath.getName().contains(".png") || imagePath.getName().contains(".PNG")
                            || imagePath.getName().contains(".gif") || imagePath.getName().contains(".GIF")
                            || imagePath.getName().contains(".bmp") || imagePath.getName().contains(".BMP")
                            )
                    {



                        String path= imagePath.getAbsolutePath();
                        resultIAV.add(path);
                        Log.e("path",path);
                        if(resultIAV.size()>20)
                            return resultIAV;

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
            images = getFilePaths();

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