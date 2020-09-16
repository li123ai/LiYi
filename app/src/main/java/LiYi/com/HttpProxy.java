package LiYi.com;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpProxy {

    private static final String TAG="HttpProxy";
    private static final HttpProxy instance=new HttpProxy();

    private HttpProxy(){

    }

    public static HttpProxy getInstance(){
        return instance;
    }

    public void load(final String urlStr, final NetInputCallback callback ){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url=new URL(urlStr);
                    HttpURLConnection urlConnection=(HttpURLConnection) url.openConnection();
                    /*设置请求参数*/
                    urlConnection.setRequestMethod("GET");
                    /*设置网络读取和连接时间的上线*/
                    urlConnection.setConnectTimeout(5000);
                    urlConnection.setReadTimeout(10000);
                    /*发起连接*/
                    urlConnection.connect();
                    /*获取响应*/
                    int responseCode=urlConnection.getResponseCode();
                    if(responseCode==200){
                        /*获取响应的输入流*/
                        InputStream inputStream=urlConnection.getInputStream();
                        callback.onSuccess(inputStream);
                        /*关闭输入流*/
                        inputStream.close();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public interface NetInputCallback {
        void onSuccess(InputStream inputStream);
    }
}
