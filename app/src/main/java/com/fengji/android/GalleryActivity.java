package com.fengji.android;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GalleryActivity extends Activity {
    private static Integer[] IMAGES = new Integer[] {
            R.drawable.gallery_photo_1,
            R.drawable.gallery_photo_2,
            R.drawable.gallery_photo_3,
            R.drawable.gallery_photo_4,
            R.drawable.gallery_photo_5,
            R.drawable.gallery_photo_6,
            R.drawable.gallery_photo_7,
            R.drawable.gallery_photo_8
    };

    private GLCoverFlowView mCoverFlow;
    private Button mButton;
    private Toast mToast;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //Toast.makeText(GalleryActivity.this, (String) msg.obj, Toast.LENGTH_SHORT).show();
            mToast.setText((String) msg.obj);
            mToast.show();
        }
    };
    List<Integer> SAMPLE_IMAGES ;
    List<Bitmap> bitmaps = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mToast = Toast.makeText(GalleryActivity.this, "", Toast.LENGTH_SHORT);
        SAMPLE_IMAGES = new ArrayList<>(Arrays.asList(IMAGES));
        mButton = (Button) findViewById(R.id.add_more);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SAMPLE_IMAGES.add(1, R.drawable.poster2);
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.poster2);
                bitmaps.add(bitmap);
                mCoverFlow.requestRender();
            }
        });
        mCoverFlow = (GLCoverFlowView) findViewById(R.id.cover_flow);//new CoverFlowOpenGL(this);

        for (int i = 0; i < SAMPLE_IMAGES.size(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), SAMPLE_IMAGES.get(i));
            bitmaps.add(bitmap);
        }
        mCoverFlow.setList(bitmaps);
        mCoverFlow.setCoverFlowListener(new GLCoverFlowView.CoverFlowListener() {
            @Override
            public int getCount(GLCoverFlowView view) {
                return SAMPLE_IMAGES.size();
            }

            @Override
            public Bitmap getImage(GLCoverFlowView anotherCoverFlow, int position) {
                return BitmapFactory.decodeResource(getResources(), SAMPLE_IMAGES.get(position));
            }

            @Override
            public void tileOnTop(GLCoverFlowView view, int position) {
                // you can control what will happen when one image is in middle
                //mHandler.obtainMessage(0, String.format("Image %d is on top.", position)).sendToTarget();
            }

            @Override
            public void topTileClicked(GLCoverFlowView view, int position) {
                // you can control what will happen when the image in middle is clicked
                mHandler.obtainMessage(0, String.format("Image %d is clicked", position)).sendToTarget();
            }
        });

        mCoverFlow.setSelection(IMAGES.length/2);
        mCoverFlow.setBackgroundTexture(R.drawable.bg);

        //setContentView(mCoverFlow);
    }
}
