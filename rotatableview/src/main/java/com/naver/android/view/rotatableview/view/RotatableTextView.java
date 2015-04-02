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
import android.widget.TextView;

import com.naver.android.view.rotatableview.Rotatable;
import com.naver.android.view.rotatableview.helper.RotatableHelper;

public class RotatableTextView extends TextView implements Rotatable {
	public RotatableTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	RotatableHelper helper = new RotatableHelper(this);

	@Override
	protected void onDraw(Canvas canvas) {
		int currentDegree = helper.updateCurrentDegreeAndView();
		final float w = this.getMeasuredWidth();
		final float h = this.getMeasuredHeight();
		final float centerX = w / 2.0f;
		final float centerY = h / 2.0f;
		canvas.rotate(-currentDegree, centerX, centerY);
		super.onDraw(canvas);
	}

	@Override
	public void setOrientation(int orientation, boolean animation) {
		helper.setOrientation(orientation, animation);
	}
}
