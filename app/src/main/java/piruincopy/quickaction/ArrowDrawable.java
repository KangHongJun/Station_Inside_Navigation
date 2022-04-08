/*
 *  The MIT License (MIT)
 *
 *  Copyright (c) 2016. Vi
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 *
 *  ----------------------------------------------------
 *
 *  Copyright 2016 Piruin Panichphol
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package quickaction;

import android.graphics.*;
import android.graphics.drawable.ColorDrawable;
import androidx.annotation.ColorInt;

final class ArrowDrawable extends ColorDrawable {
    static final int ARROW_UP = 1;
    static final int ARROW_DOWN = 2;

    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mShadowPaint = new Paint(mPaint);
    private final int gravity;
    private final int stroke;

    private Path mPath;
    private Path mStrokePath;

    ArrowDrawable(int gravity, @ColorInt int foregroundColor, int strokeWidth, int strokeColor)
    {
        this.gravity = gravity;
        this.stroke = strokeWidth;
        mPaint.setColor(foregroundColor);
        mShadowPaint.setColor(strokeColor);
    }

    @Override protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        updatePath(bounds);
        updateShadowPath(bounds);
    }

    private synchronized void updatePath(Rect bounds) {
        mPath = new Path();
        switch (gravity) {
            case ARROW_UP:
                mPath.moveTo(stroke, bounds.height());
                mPath.lineTo(bounds.width()/2, stroke);
                mPath.lineTo(bounds.width()-stroke, bounds.height());
                break;
            case ARROW_DOWN:
                mPath.moveTo(stroke, 0);
                mPath.lineTo(bounds.width()/2, bounds.height()-stroke);
                mPath.lineTo(bounds.width()-stroke, 0);
                break;
        }
        mPath.close();
    }

    private synchronized void updateShadowPath(Rect bounds) {
        mStrokePath = new Path();
        switch (gravity) {
            case ARROW_UP:
                mStrokePath.moveTo(0, bounds.height());
                mStrokePath.lineTo(bounds.width()/2, 0);
                mStrokePath.lineTo(bounds.width(), bounds.height());
                break;
            case ARROW_DOWN:
                mStrokePath.moveTo(0, 0);
                mStrokePath.lineTo(bounds.width()/2, bounds.height());
                mStrokePath.lineTo(bounds.width(), 0);
                break;
        }
        mStrokePath.close();
    }

    @Override public void draw(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT);
        if (mPath == null) {
            updatePath(getBounds());
            updateShadowPath(getBounds());
        }
        canvas.drawPath(mStrokePath, mShadowPaint);
        canvas.drawPath(mPath, mPaint);
    }

    public void setColor(@ColorInt int color) {
        mPaint.setColor(color);
    }

    @Override public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override public int getOpacity() {
        if (mPaint.getColorFilter() != null) {
            return PixelFormat.TRANSLUCENT;
        }

        switch (mPaint.getColor() >>> 24) {
            case 255:
                return PixelFormat.OPAQUE;
            case 0:
                return PixelFormat.TRANSPARENT;
        }
        return PixelFormat.TRANSLUCENT;
    }
}
