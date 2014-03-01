/**
 * 
 */
package com.t3h.project.easygo.extras;

import com.t3h.project.easygo.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * @author My PC
 * 
 */
public class EdittextClass extends EditText {
	private Rect mRect;
	private Paint mPaint;

	public EdittextClass(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mRect = new Rect();
		mPaint = new Paint();
		mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		mPaint.setColor(R.drawable.yellow);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		int height = getHeight();
		int line_height = getLineHeight();

		int count = height / line_height;

		if (getLineCount() > count)
			count = getLineCount();

		Rect r = mRect;
		Paint paint = mPaint;
		int baseline = getLineBounds(0, r);

		for (int i = 0; i < count; i++) {

			canvas.drawLine(r.left, baseline + 1, r.right, baseline + 1, paint);
			baseline += getLineHeight();
			super.onDraw(canvas);
		}
	}

}
