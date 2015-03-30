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
package com.naver.android.view.rotatableview.helper;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.naver.android.view.rotatableview.OnRotateEndListener;
import com.naver.android.view.rotatableview.Rotatable;

public class RotatableImageViewHelper implements Rotatable {

	private final ImageView imageView;

	public RotatableImageViewHelper(ImageView imageView) {
		this.imageView = imageView;
		this.helper = new RotatableHelper(imageView);
	}

	RotatableHelper helper;

	private boolean fitToScreenFlag = false;

	@Override
	public void setOrientation(int orientation, boolean animation) {
		helper.setOrientation(orientation, animation);
	}

	static final float SQRT_2 = 1.4142f;

	public void onDraw(Canvas canvas) {
		Drawable drawable = imageView.getDrawable();
		if (drawable == null) {
			return;
		}

		Rect bounds = drawable.getBounds();
		int w = bounds.right - bounds.left;
		int h = bounds.bottom - bounds.top;

		if (w == 0 || h == 0) {
			return;
		}

		int currentDegree = helper.updateCurrentDegreeAndView();
		int left = imageView.getPaddingLeft();
		int top = imageView.getPaddingTop();
		int right = imageView.getPaddingRight();
		int bottom = imageView.getPaddingBottom();
		int width = imageView.getWidth() - left - right;
		int height = imageView.getHeight() - top - bottom;

		int saveCount = canvas.getSaveCount();

		if ((imageView.getScaleType() == ImageView.ScaleType.FIT_CENTER)) {
			float scale = Math.min((float)width / w, (float)height / h);

			//-- 화면 크기에 맞춰서 scale down할 필요가 있을때 아래처럼 한다.
			//-- 이유는 그리는 영역을 이미지가 넘어가면서 잘리기 때문
			if (fitToScreenFlag && (currentDegree % 90) != 0) {
				float x = Math.abs(currentDegree % 90);
				x = (x > 45) ? 90 - x : x;
				float scaleFactor = (1.f / SQRT_2 - 1f) / 45f * x + 1f;
				scale = scaleFactor * scale;
			}
			canvas.scale(scale, scale, width / 2.0f, height / 2.0f);
		}
		canvas.translate(left + width / 2, top + height / 2);
		canvas.rotate(-currentDegree);
		canvas.translate(-w / 2, -h / 2);
		drawable.draw(canvas);
		canvas.restoreToCount(saveCount);
	}

	public void setFitToScreenFlag(boolean fitToScreenFlag) {
		this.fitToScreenFlag = fitToScreenFlag;
	}

    public void setRotateEndListener(OnRotateEndListener onRotateEndListener){
        helper.setRotateEndListener(onRotateEndListener);
    }
}
