package LiYi.com;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import java.io.InputStream;

public class ImageLoader {
    private static final ImageLoader instance=new ImageLoader();
    private Handler mHandler;

    private ImageLoader(){
        mHandler=new Handler(Looper.getMainLooper());
    }
    public static ImageLoader getInstance(){
        return instance;
    }

    public void load(final ImageView iconTV,final String thumbPath){
        iconTV.setTag(thumbPath);
        iconTV.setBackgroundColor(Color.GRAY);
        HttpProxy.getInstance().load(thumbPath, new HttpProxy.NetInputCallback() {
            @Override
            public void onSuccess(InputStream inputStream) {
                /*读取图片*/
                final Bitmap bitmap=NetInputUtils.readImg(inputStream);
                /*子线程不能更新UI，子线程切换到主线程*/
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        String tag=(String) iconTV.getTag();
                        if(tag.equals(thumbPath)){
                            iconTV.setImageBitmap(bitmap);
                        }
                    }
                });
            }
        });
    }
}
