/*
 * Copyright (c) 2015 Naver Corp.
 * @Author Ohkyun Kim, Byungwoong Kwon, Jonghun Kim
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

package com.naver.android.view.rotatableview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.naver.android.view.rotatableview.OnRotateEndListener;
import com.naver.android.view.rotatableview.Rotatable;
import com.naver.android.view.rotatableview.helper.RotatableImageViewHelper;


//-- https://groups.google.com/forum/?fromgroups=#!topic/android-developers/_JrJ8Wii7E8
//-- setFitToScreenFlag를 custom properties로 빼려고 했으나 위의 이유로 제거
//-- 라이브러리 프로젝트에서 custom properties를 넣으면 main에서 재정의가 필요

public class RotatableImageView extends ImageView implements Rotatable {
	RotatableImageViewHelper rotatableHelper = new RotatableImageViewHelper(this);

	public RotatableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RotatableImageView(Context context) {
        super(context);
    }

    @Override
	public void setOrientation(int orientation, boolean animation) {
		rotatableHelper.setOrientation(orientation, animation);
    }

    @Override
    protected void onDraw(Canvas canvas) {
		rotatableHelper.onDraw(canvas);
    }

	public void setFitToScreenFlag(boolean fitToScreenFlag) {
		rotatableHelper.setFitToScreenFlag(fitToScreenFlag);
	}

    public void setRotateEndListener(OnRotateEndListener onRotateEndListener){
        rotatableHelper.setRotateEndListener(onRotateEndListener);
    }
}
