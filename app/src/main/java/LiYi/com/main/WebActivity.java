package LiYi.com.main;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import LiYi.com.R;

public class WebActivity extends AppCompatActivity {

    public static final String WEB_URL="webUrl";
    private ProgressBar mpgl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        mpgl=findViewById(R.id.pro_web);

//        头部返回
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        String webUrl=getIntent().getStringExtra(WEB_URL);
        WebView webView=findViewById(R.id.web_content);
        WebSettings settings=webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.loadUrl(webUrl);

//        设置进度条
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    mpgl.setVisibility(View.GONE);
                } else {
                    mpgl.setVisibility(View.VISIBLE);
                    mpgl.setProgress(newProgress);
                }
            }
        });

//      隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
