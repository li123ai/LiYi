package LiYi.com.lsit;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import LiYi.com.ImageLoader;
import LiYi.com.R;
import LiYi.com.bean.VideoInfo;
import LiYi.com.main.WebActivity;

public class VideoAdapter extends BaseAdapter {
    private List<VideoInfo> mDataList=new ArrayList<>();

    private Context mContext;
    private LayoutInflater mInflate;

    public VideoAdapter(List<VideoInfo> list, Context context){
        this.mDataList=list;
        this.mContext=context;
        mInflate=LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        int count=null==mDataList?0:mDataList.size();
        return count;
    }

    @Override
    public VideoInfo getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler holder;
        /*构建布局加载器*/
        if(convertView==null){
            convertView=mInflate.inflate(R.layout.item_movie,null);
            holder=new ViewHodler();
            holder.itemLayout=convertView.findViewById(R.id.layout_item);
            holder.iconTv = convertView.findViewById(R.id.iv_icon);
            holder.tltTv = convertView.findViewById(R.id.tv_title);
            holder.porfileTv=convertView.findViewById(R.id.tv_profile);

            convertView.setTag(holder);
        }else {
            holder=(ViewHodler) convertView.getTag();
        }

        final VideoInfo item=mDataList.get(position);
        holder.tltTv.setText(item.getTitle());
        holder.porfileTv.setText(item.getProfile());

        ImageLoader.getInstance().load(holder.iconTv,item.getThumbPath());

        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, WebActivity.class);
                intent.putExtra(WebActivity.WEB_URL,item.getFilePath());
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    public void setData(List<VideoInfo> list){
        mDataList.clear();
        if(null!=list){
            mDataList.addAll(list);
        }
    }

    private class ViewHodler {
        View itemLayout;
        ImageView iconTv;
        TextView tltTv;
        TextView porfileTv;

    }
}
