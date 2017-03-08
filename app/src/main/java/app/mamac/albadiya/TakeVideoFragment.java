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
import android.widget.TextView;

/**
 * Created by mac on 12/12/16.
 */

public class TakeVideoFragment extends Fragment {
    private CameraPreview mPreview;
    private RelativeLayout mLayout;
    private Camera.PictureCallback mPicture;
    private MediaRecorder mediaRecorder;
    private boolean recording;
    ImageView camera_btn;
    PostFragment mTakeVideoListner;
    TextView recording_message;
    public interface TakeVideoListner
    {
        public void onVideobtn();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.take_video_layout, container, false);
        mLayout = (RelativeLayout)view.findViewById(R.id.camera_holder);
        camera_btn = (ImageView)view.findViewById(R.id.camera_btn);
        camera_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    mTakeVideoListner.onVideobtn();
            }
        });
        mTakeVideoListner = (PostFragment)getParentFragment();
        recording_message = (TextView) view.findViewById(R.id.recording_message);
        recording_message.setVisibility(View.GONE);
        return view;
    }

}