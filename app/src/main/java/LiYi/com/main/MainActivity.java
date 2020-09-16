package LiYi.com.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import LiYi.com.R;

public class MainActivity extends AppCompatActivity {

    EditText name;
    SharedPreferences msp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //隐藏动作栏
        getSupportActionBar().hide();

        name=findViewById(R.id.edt_name);
        Button registerBtn=findViewById(R.id.btn_Login);
        Button signBtn=findViewById(R.id.btn_Sign_in);


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ListHeadActivity.class);

                String nameVal=name.getText().toString().trim();

                /*数据传递*/
                intent.putExtra("name",nameVal);

                /*跳转到主界面后，并将栈底的Activity全部都销毁*/
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);

                MainActivity.this.finish();
            }
        });

        signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("sign on","---------sign on");
                Toast.makeText(MainActivity.this,"敬请期待",Toast.LENGTH_SHORT).show();
            }
        });

        msp=getSharedPreferences("User",MODE_PRIVATE);
        String username=msp.getString("name","");
        if ("".equals(username)){
            name.setHint("your username");
        }else{
            name.setText(username);
        }
    }

    protected  void onPause() {
        super.onPause();
        String content=name.getText().toString();
        /*SharedPreferences保存*/
        msp=getSharedPreferences("User",MODE_PRIVATE);
        SharedPreferences.Editor editor=msp.edit();
        editor.putString("name",content);
        editor.apply();
    }
}
