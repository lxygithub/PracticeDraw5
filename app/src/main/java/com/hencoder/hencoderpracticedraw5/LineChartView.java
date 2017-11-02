package com.hencoder.hencoderpracticedraw5;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 类描述：
 * 创建人：luoxingyuan
 * 创建时间：2017/8/17 19:29
 * 修改人：luoxingyuan
 * 修改时间：2017/8/17 19:29
 * 修改备注：
 */

public class LineChartView extends View
{

    private float titleHeight;
    private int viewWidth;
    private int viewHeight;
    private Paint paint;
    private Paint pathPaint;
    private Paint textPaint;
    private Point startPoint;
    private Point middlePoint;
    private Point endPoint;
    private final static int DEFAULT_MARGIN_VALUE = 40;
    private List<LinePoint> linePoints = new ArrayList<>();
    private Path path;
    private Random random;
    private int pointSpace;
    private int[] randomArrange = new int[2];
    private Rect rectTextBounds;
    private Rect rectPointTextBounds;
    private Rect rectVerticalTextBounds;

    public LineChartView(Context context)
    {
        this(context, null, 0);
    }

    public LineChartView(Context context, @Nullable AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public LineChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init()
    {
        titleHeight = dp2px(45);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        pathPaint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        startPoint = new Point();
        middlePoint = new Point();
        endPoint = new Point();

        random = new Random();
        path = new Path();

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(dp2px(10));

        rectTextBounds = new Rect();
        rectPointTextBounds = new Rect();
        rectVerticalTextBounds = new Rect();


    }


    private int dp2px(int dip)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        viewWidth = MeasureSpec.getSize(widthMeasureSpec);
        viewHeight = MeasureSpec.getSize(heightMeasureSpec);


        switch (widthMode)
        {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                //如果测量模式是最大取值为size
                //我们将大小取最大值,你也可以取其他值
                //如果没有指定大小，就设置为默认大小
                viewWidth = getResources().getDisplayMetrics().widthPixels;
            case MeasureSpec.EXACTLY:
                //如果是固定的大小，那就不要去改变它

                break;
            default:
                break;

        }
        switch (heightMode)
        {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                //如果测量模式是最大取值为size
                //我们将大小取最大值,你也可以取其他值
                //如果没有指定大小，就设置为默认大小
                viewHeight = (int) ((getResources().getDisplayMetrics().heightPixels - titleHeight) / 2);

            case MeasureSpec.EXACTLY:
                //如果是固定的大小，那就不要去改变它

                break;
            default:
                break;

        }


        setMeasuredDimension(viewWidth, viewHeight);

        startPoint.set(dp2px(DEFAULT_MARGIN_VALUE), dp2px(DEFAULT_MARGIN_VALUE));
        middlePoint.set(dp2px(DEFAULT_MARGIN_VALUE), viewHeight - dp2px(DEFAULT_MARGIN_VALUE));
        endPoint.set(viewWidth - dp2px(DEFAULT_MARGIN_VALUE), viewHeight - dp2px(DEFAULT_MARGIN_VALUE));

        pointSpace = (viewWidth - dp2px(DEFAULT_MARGIN_VALUE * 2)) / 10;
        randomArrange[0] = viewHeight - dp2px(DEFAULT_MARGIN_VALUE * 2);
        randomArrange[1] = dp2px(DEFAULT_MARGIN_VALUE);
    }


    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.drawColor(Color.WHITE);
        canvas.drawLine(startPoint.x, startPoint.y, middlePoint.x, middlePoint.y, paint);
        canvas.drawLine(middlePoint.x, middlePoint.y, endPoint.x, endPoint.y, paint);
        canvas.drawLine(startPoint.x, startPoint.y, startPoint.x - dp2px(5), startPoint.y + dp2px(10), paint);
        canvas.drawLine(startPoint.x, startPoint.y, startPoint.x + dp2px(5), startPoint.y + dp2px(10), paint);
        canvas.drawLine(endPoint.x, endPoint.y, endPoint.x - dp2px(10), endPoint.y - dp2px(5), paint);
        canvas.drawLine(endPoint.x, endPoint.y, endPoint.x - dp2px(10), endPoint.y + dp2px(5), paint);


        for (int i = 1; i <= 10; i++)
        {

            String text = i + "";
            textPaint.getTextBounds(i + "", 0, (i + "").length(), rectTextBounds);
            int x = pointSpace * i + dp2px(DEFAULT_MARGIN_VALUE);
            int y = random.nextInt(randomArrange[0]) + randomArrange[1];
            canvas.drawCircle(x, y, 5, paint);
            String pointText = viewHeight - dp2px(DEFAULT_MARGIN_VALUE) - y + "";
            textPaint.getTextBounds(pointText, 0, pointText.length(), rectPointTextBounds);
            canvas.drawText(pointText, x - (rectPointTextBounds.right - rectPointTextBounds.left) / 2, y - (rectPointTextBounds.bottom - rectPointTextBounds.top) / 2, textPaint);
            if (i == 1)
            {
                path.moveTo(x, y);
            }
            path.lineTo(x, y);
            canvas.drawLine(x, middlePoint.y, x, middlePoint.y - dp2px(5), paint);
            if (i != 10)
            {
                canvas.drawLine(startPoint.x, middlePoint.y, startPoint.x, middlePoint.y - dp2px(5), paint);
            }
            if (i < 10)
            {
                canvas.drawText(text, x - ((rectTextBounds.right - rectTextBounds.left) / 2), (float) (middlePoint.y + ((rectTextBounds.bottom - rectTextBounds.top) * 1.5)), textPaint);
            }
            if (i == 10)
            {
                canvas.drawText("X", x - ((rectTextBounds.right - rectTextBounds.left) / 2), (float) (middlePoint.y + ((rectTextBounds.bottom - rectTextBounds.top) * 1.5)), textPaint);
            }
        }
        canvas.drawText(0 + "", middlePoint.x - ((rectTextBounds.right - rectTextBounds.left) / 2), (float) (middlePoint.y + ((rectTextBounds.bottom - rectTextBounds.top) * 1.5)), textPaint);
        canvas.drawPath(path, pathPaint);

        for (int i = 0; i <= 10; i++)
        {
            String text = (viewHeight - dp2px(DEFAULT_MARGIN_VALUE) - (middlePoint.y - (middlePoint.y - startPoint.y) / 10 * i)) + "";
            textPaint.getTextBounds(text, 0, text.length(), rectVerticalTextBounds);
            if (i != 10)
            {
                canvas.drawLine(startPoint.x, middlePoint.y - (middlePoint.y - startPoint.y) / 10 * i,
                        endPoint.x, middlePoint.y - (middlePoint.y - startPoint.y) / 10 * i, paint);
            }
            if (i > 0 && i != 10)
            {
                canvas.drawText(text, (float) (startPoint.x - (rectVerticalTextBounds.right - rectVerticalTextBounds.left) - dp2px(5)),
                        middlePoint.y - (middlePoint.y - startPoint.y) / 10 * i + (rectVerticalTextBounds.bottom - rectVerticalTextBounds.top) / 2, textPaint);
            }
            if (i == 10)
            {
                String textAxis = "Y";
                canvas.drawText(textAxis, (float) (startPoint.x - ((rectTextBounds.right - rectTextBounds.left) * 2)),
                        middlePoint.y - (middlePoint.y - startPoint.y) / 10 * i + (rectTextBounds.bottom - rectTextBounds.top) / 2, textPaint);

            }
        }

    }


}
