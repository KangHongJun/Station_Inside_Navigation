/*
 *  Copyright 2016 Piruin Panichphol
 *  Copyright 2011 Lorensius W. L. T
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package piruincopy.quickaction;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

class PopupWindows {
    PopupWindow mWindow;
    private View mRootView;
    private Context mContext;

    PopupWindows(Context context) {
        mContext = context;
        mWindow = new PopupWindow(context);
        mWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    mWindow.dismiss();
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_BUTTON_PRESS) {
                    view.performClick();
                    return true;
                }
                return false;
            }
        });
    }

    Context getContext() {
        return mContext;
    }

    void preShow() {
        if (mRootView == null)
            throw new IllegalStateException("setContentView was not called with a view to display.");

        mWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        mWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        mWindow.setTouchable(true);
        mWindow.setFocusable(true);
        mWindow.setOutsideTouchable(true);
        mWindow.setContentView(mRootView);
        setShadows();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP) private void setShadows() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            mWindow.setElevation(10);
    }

    void setContentView(View root) {
        mRootView = root;
        mWindow.setContentView(root);
    }

    void setOnDismissListener(PopupWindow.OnDismissListener listener) {
        mWindow.setOnDismissListener(listener);
    }

    public void dismiss() {
        mWindow.dismiss();
    }
}
