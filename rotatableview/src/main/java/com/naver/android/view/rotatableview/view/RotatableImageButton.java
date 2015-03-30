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

package com.naver.android.view.rotatableview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageButton;

import com.naver.android.view.rotatableview.OnRotateEndListener;
import com.naver.android.view.rotatableview.Rotatable;
import com.naver.android.view.rotatableview.helper.RotatableImageViewHelper;

public class RotatableImageButton extends ImageButton implements Rotatable {
	private RotatableImageViewHelper rotatableHelper = new RotatableImageViewHelper(this);

    public RotatableImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RotatableImageButton(Context context) {
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

    public void setRotateEndListener(OnRotateEndListener onRotateEndListener){
        rotatableHelper.setRotateEndListener(onRotateEndListener);
    }
}
