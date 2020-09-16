package LiYi.com.main;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import LiYi.com.R;

public class TagActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);

        //隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //隐藏动作栏
        getSupportActionBar().hide();

        final EditText tag=findViewById(R.id.tagEdit);
        Button submit=findViewById(R.id.btn_sub);

//        顶部添加返回
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

//        数据传递
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();

                String massage=tag.getText().toString();

                if(TextUtils.isEmpty(massage)){
                    Toast.makeText(TagActivity.this,"请输入签名",Toast.LENGTH_SHORT).show();
                    return;
                }
                intent.putExtra("massage",massage);

                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
    //    顶部添加返回
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
