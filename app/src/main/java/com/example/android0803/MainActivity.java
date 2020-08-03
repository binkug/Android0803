package com.example.android0803;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView resultView;
    Button btn_contacts,btn_camera,btn_voice,btn_map,btn_browser,btn_call;
    ImageView resultImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultView = (TextView)findViewById(R.id.resultView);
        btn_contacts = (Button)findViewById(R.id.btn_contacts);
        btn_camera = (Button)findViewById(R.id.btn_camera);
        btn_voice = (Button)findViewById(R.id.btn_voice);
        btn_map = (Button)findViewById(R.id.btn_map);
        btn_browser = (Button)findViewById(R.id.btn_browser);
        btn_call = (Button) findViewById(R.id.btn_call);
        resultImageView = (ImageView) findViewById(R.id.resultImageView);

        //클릭 이벤트 처리 - 이벤트 라우팅
        View.OnClickListener listener = new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent;
                switch (view.getId()){
                    case R.id.btn_contacts:
                        intent = new Intent(Intent.ACTION_PICK);
                        intent.setData(ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                        //여러 개의 Activity를 실행하거나
                        //하위 Activity로 부터 데이터를 넘겨 받고자 하는 경우에는
                        //아래 메소드로 Activity를 호출
                        //번호는 구분을 하기 위한 것으로 임의로 배정해도 되지만 중복되면 안됩니다.
                        startActivityForResult(intent,10);
                        break;
                    case R.id.btn_camera:
                        intent = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, 20);
                        break;
                    case R.id.btn_browser:
                        intent = new Intent(
                                Intent.ACTION_VIEW, Uri.parse(
                                "http://seoul.go.kr"));
                        startActivityForResult(intent, 30);
                        break;
                    case R.id.btn_map:
                        intent = new Intent(
                                Intent.ACTION_VIEW, Uri.parse(
                                "geo:37.5666, 126.9779"));
                        startActivityForResult(intent, 40);
                        break;
                    case R.id.btn_call:
                        //권한 설정 여부를 확인
                        if(ContextCompat.checkSelfPermission(
                                MainActivity.this,
                                Manifest.permission.CALL_PHONE) ==
                                PackageManager.PERMISSION_GRANTED){
                            //권한이 있는 경우
                            intent = new Intent(
                                    Intent.ACTION_CALL,
                                    Uri.parse("tel:010-3790-1997"));
                            startActivityForResult(intent, 50);
                        }else{
                            //권한이 없는 경우  - 권한을 다시 요청
                            ActivityCompat.requestPermissions(
                                    MainActivity.this,
                                    new String[]{Manifest.permission.CALL_PHONE},
                                    100);
                        }
                        break;
                }
            }
        };

        //이벤트 핸들러 연결
        btn_contacts.setOnClickListener(listener);
        btn_camera.setOnClickListener(listener);
        btn_voice.setOnClickListener(listener);
        btn_map.setOnClickListener(listener);
        btn_browser.setOnClickListener(listener);
        btn_call.setOnClickListener(listener);

        //startActivityForResult로 하위 Activity를 출력한 경우
        //하위 Activity가 소멸되면 호출되는 메소드


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        //상위 클래스의 메소드 호출
        //파괴하는 메소드가 아니면 상위클래스의 메소드를 먼저 호출하는 것이 일반적입니다.
        super.onActivityResult(requestCode, resultCode, data);
        //requestCode가 20일때만 동작하도록 하는 if문
        if(requestCode == 20) {
            //카메라는 촬영한 사진을 data라는 이름으로 Bitmap 타입으로 리턴해줍니다.
            Bitmap bitmap = (Bitmap) getIntent().getExtras().get("data");
            resultImageView.setImageBitmap(bitmap);
        }
    }
}