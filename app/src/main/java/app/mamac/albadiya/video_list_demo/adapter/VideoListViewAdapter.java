//package app.mamac.albadiya.video_list_demo.adapter;
//
//import android.content.Context;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//
//import com.volokh.danylo.video_player_manager.manager.VideoPlayerManager;
//
//import app.mamac.albadiya.Posts;
//import app.mamac.albadiya.video_list_demo.adapter.holders.VideoViewHolder;
//import app.mamac.albadiya.video_list_demo.adapter.items.BaseVideoItem;
//
//import java.util.List;
//
//public class VideoListViewAdapter extends BaseAdapter {
//
//    private final VideoPlayerManager mVideoPlayerManager;
//    private final List<Posts> mList;
//    private final Context mContext;
//
//    public VideoListViewAdapter(VideoPlayerManager videoPlayerManager, Context context, List<Posts> list){
//        mVideoPlayerManager = videoPlayerManager;
//        mContext = context;
//        mList = list;
//    }
//
//    @Override
//    public int getCount() {
//        return mList.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return mList.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return 0;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
////        BaseVideoItem videoItem = mList.get(position).videoItem;
//
//        View resultView;
//        if(convertView == null){
//
//            resultView = videoItem.createView(parent, mContext.getResources().getDisplayMetrics().widthPixels);
//        } else {
//            resultView = convertView;
//        }
//
//        videoItem.update(position, (VideoViewHolder) resultView.getTag(), mVideoPlayerManager);
//
//        return resultView;
//    }
//
//}
