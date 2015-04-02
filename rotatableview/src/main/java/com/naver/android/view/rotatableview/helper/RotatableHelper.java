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

package com.naver.android.view.rotatableview.helper;

import android.view.View;
import android.view.animation.AnimationUtils;

import com.naver.android.view.rotatableview.OnRotateEndListener;
import com.naver.android.view.rotatableview.Rotatable;


public class RotatableHelper implements Rotatable {
	private static final int ANIMATION_SPEED = 270; // 270 deg/sec

    private OnRotateEndListener onRotateEndListener = new BaseOnRotateEndListener();

	private int currentDegree = 0; // [0, 359]
	private int startDegree = 0;
	private int targetDegree = 0;

	private boolean clockwise = false, enableAnimation = true;

	private long animationStartTime = 0;
	private long animationEndTime = 0;

	private View view;

	public RotatableHelper(View view) {
		this.view = view;
	}

	@Override
	public void setOrientation(int orientation, boolean animation) {
		enableAnimation = animation;
		// make sure in the range of [0, 359]
		orientation = orientation >= 0 ? orientation % 360 : orientation % 360 + 360;
		if (orientation == targetDegree) {
			return;
		}

		targetDegree = orientation;
		if (enableAnimation) {
			startDegree = currentDegree;
			animationStartTime = AnimationUtils.currentAnimationTimeMillis();

			int diff = targetDegree - currentDegree;
			diff = diff >= 0 ? diff : 360 + diff; // make it in range [0, 359]
			// Make it in range [-179, 180]. That's the shorted distance between the two angles
			diff = diff > 180 ? diff - 360 : diff;
			clockwise = diff >= 0;
			animationEndTime = animationStartTime
				+ Math.abs(diff) * 1000 / ANIMATION_SPEED;
		} else {
			currentDegree = targetDegree;
		}

		view.invalidate();
	}

	public int updateCurrentDegreeAndView() {
		if (currentDegree != targetDegree) {
			long time = AnimationUtils.currentAnimationTimeMillis();
			if (time < animationEndTime) {
				int deltaTime = (int)(time - animationStartTime);
				int degree = startDegree + ANIMATION_SPEED
					* (clockwise ? deltaTime : -deltaTime) / 1000;
				degree = degree >= 0 ? degree % 360 : degree % 360 + 360;
				currentDegree = degree;
				view.invalidate();
			} else {
				currentDegree = targetDegree;
                onRotateEndListener.onRotateEnd();
			}
		}

		return currentDegree;
	}

    public void setRotateEndListener(OnRotateEndListener onRotateEndListener){
        if(onRotateEndListener == null) {
            this.onRotateEndListener = new BaseOnRotateEndListener();
        } else {
            this.onRotateEndListener = onRotateEndListener;
        }
    }

    private class BaseOnRotateEndListener implements OnRotateEndListener{
        @Override
        public void onRotateEnd() {}
    }
}
