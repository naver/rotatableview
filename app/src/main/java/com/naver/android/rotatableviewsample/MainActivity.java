/*
 * Copyright (c) 2015 Naver Corp.
 * @Author Oh kyun Kim, Byung Woong Kwon, Jong Hun Kim
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.naver.android.rotatableviewsample;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.OrientationEventListener;
import android.view.View;
import android.widget.Toast;

import com.naver.android.view.rotatableview.OnRotateEndListener;
import com.naver.android.view.rotatableview.view.RotatableButton;
import com.naver.android.view.rotatableview.view.RotatableImageButton;
import com.naver.android.view.rotatableview.view.RotatableImageView;
import com.naver.android.view.rotatableview.view.RotatableTextView;


/**
 * Created by helloyako on 15. 3. 20..
 *
 * NOTE : Activity screenOrientation must be portrait.
 */
public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";

    // Orientation hysteresis amount used in rounding, in degrees
    private static final int ORIENTATION_HYSTERESIS = 5;
    private int mLastRawOrientation = OrientationEventListener.ORIENTATION_UNKNOWN;
    private RotatableImageButton ratioButton;
    private RotatableImageView flash;
    private RotatableTextView helloWorld;
    private RotatableButton rotatableButton;

    private MyOrientationEventListener mOrientationListener;

    final private OnRotateEndListener to43ImageChange = new OnRotateEndListener(){
        @Override
        public void onRotateEnd() {
            ratioButton.setImageResource(R.drawable.camera_selector_rate43_button);
        }
    };

    final private OnRotateEndListener to34ImageChange = new OnRotateEndListener(){

        @Override
        public void onRotateEnd() {
            ratioButton.setImageResource(R.drawable.camera_selector_rate34_button);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ratioButton = (RotatableImageButton) findViewById(R.id.ratio_button);
        flash = (RotatableImageView) findViewById(R.id.flash_image_view);
        helloWorld = (RotatableTextView) findViewById(R.id.hello_text_view);
        rotatableButton = (RotatableButton) findViewById(R.id.rotatable_button_view);
        mOrientationListener = new MyOrientationEventListener(this);
        ratioButton.setRotateEndListener(new OnRotateEndListener() {
            @Override
            public void onRotateEnd() {
                ratioButton.setImageResource(R.drawable.camera_selector_rate43_button);
            }
        });

        ratioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Ratio Button Click",Toast.LENGTH_SHORT).show();
            }
        });

        rotatableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Button Click",Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        mOrientationListener.enable();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mOrientationListener.disable();
    }

    public int roundOrientation(int orientation, int orientationHistory) {
        boolean changeOrientation;
        if (orientationHistory == OrientationEventListener.ORIENTATION_UNKNOWN) {
            changeOrientation = true;
        } else {
            int dist = Math.abs(orientation - orientationHistory);
            dist = Math.min( dist, 360 - dist );
            changeOrientation = ( dist >= 45 + ORIENTATION_HYSTERESIS );
        }
        if (changeOrientation) {
            return ((orientation + 45) / 90 * 90) % 360;
        }
        return orientationHistory;
    }

    private class MyOrientationEventListener extends OrientationEventListener {
        public MyOrientationEventListener(Context context) {
            super(context);
        }

        @Override
        public void onOrientationChanged(int orientation) {
            // We keep the last known orientation. So if the user first orient
            // the camera then point the camera to floor or sky, we still have
            // the correct orientation.
            if (orientation == ORIENTATION_UNKNOWN) return;

            int lastRawOrientation = mLastRawOrientation;
            mLastRawOrientation = roundOrientation(orientation, mLastRawOrientation);
            if(lastRawOrientation == mLastRawOrientation) return;

            if(mLastRawOrientation == 90 || mLastRawOrientation == 270){
                ratioButton.setRotateEndListener(to43ImageChange);
            } else {
                ratioButton.setRotateEndListener(to34ImageChange);
            }

            ratioButton.setOrientation(mLastRawOrientation, true);
            flash.setOrientation(mLastRawOrientation, true);
            helloWorld.setOrientation(mLastRawOrientation, true);
            rotatableButton.setOrientation(mLastRawOrientation, true);
        }
    }
}
