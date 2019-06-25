package com.example.chap11_2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7;
    TextView tv;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        tv = findViewById(R.id.tv);
        img=findViewById(R.id.img);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1://주소록 앱 연동
                Intent intent1 = new Intent(Intent.ACTION_PICK);
                intent1.setData(ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent1, 1001);//Context객체가 갖고 있는 메소드 - 시스템과 통신
                break;
            case R.id.btn2://카메라 앱 연동
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent2, 1002);//Context객체가 갖고 있는 메소드 - 시스템과 통신
                break;
            case R.id.btn3://갤러리 앱 연동
                Intent intent3 = new Intent(Intent.ACTION_PICK);
                intent3.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent3, 1003);//Context객체가 갖고 있는 메소드 - 시스템과 통신
                break;
            case R.id.btn4://음성인식 앱 연동
                Intent intent4 = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent4.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                startActivityForResult(intent4, 1004);//Context객체가 갖고 있는 메소드 - 시스템과 통신
                break;
            case R.id.btn5://지도 앱 연동
                Intent intent5 = new Intent(Intent.ACTION_VIEW,Uri.parse("geo:37.5662952, 126.9779451?q=37.5662952, 126.9779451"));
                startActivity(intent5);//Context객체가 갖고 있는 메소드 - 시스템과 통신
                break;
            case R.id.btn6://브라우저 앤 연동
                Intent intent6 = new Intent(Intent.ACTION_VIEW,Uri.parse("http://m.naver.com/"));
                startActivity(intent6);//Context객체가 갖고 있는 메소드 - 시스템과 통신
                break;
            case R.id.btn7://전화걸기 앱 연동
                if(ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED) {//권한 체크
                    Intent intent7 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:010-4691-9962"));
                    startActivity(intent7);//Context객체가 갖고 있는 메소드 - 시스템과 통신
                }else{//권한 없으면
                    //사용자에게 권한 요청
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},10);
                }
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            String result = data.getDataString();
            switch (requestCode) {
                case 1001:
                    tv.setText(result);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(result));
                    startActivity(intent);
                    break;
                case 1002:
                    Bitmap bitmap = (Bitmap)data.getExtras().get("data");
                    img.setImageBitmap(bitmap);
                    break;
                case 1003:
                    tv.setText(result);
                    Intent intent1 = new Intent(Intent.ACTION_VIEW);
                    intent1.setData(Uri.parse(result));
                    startActivity(intent1);
                    break;
                case 1004:
                    ArrayList<String> resultArray = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String tmp = resultArray.get(0);
                    tv.setText(tmp);
                    break;
            }
        }
    }
}
