package app.mamac.albadiya;

import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by mac on 12/12/16.
 */

public class TakePictureFragment extends Fragment {
    private CameraPreview mPreview;
    private RelativeLayout mLayout;
    private Camera.PictureCallback mPicture;
    private MediaRecorder mediaRecorder;
    private boolean recording;
    ImageView camera_btn;
    PostFragment mTakePictureListner;

    public interface TakePictureListner
    {
        public void onPicturebtn();
    }



    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.take_picture_layout, container, false);
        mLayout = (RelativeLayout)view.findViewById(R.id.camera_holder);
        camera_btn = (ImageView)view.findViewById(R.id.camera_btn);
        camera_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTakePictureListner.onPicturebtn();
            }
        });
        mTakePictureListner = (PostFragment) getParentFragment();
        return view;
    }


}