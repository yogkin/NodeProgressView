package com.czm.nodeview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

import java.util.List;

/**
 * User: Daidingkang(ddk19941017@Gmail.com)
 * Date: 2016-06-28
 * Time: 09:43
 * FIXME
 */
public class BaseNodeProgressView extends View {

    private boolean mIsRotate;
    float width;
    float nodeRadius;

    Paint paint;

    Context context;

    /**
     * 节点间隔
     */
    int nodeInterval;

    /**
     * 边距
     */
    int left = 40;
    int top = 40;

    int dWidth;
    int dHeight;


    public BaseNodeProgressView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public BaseNodeProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BaseNodeProgressView);
        width = typedArray.getDimension(R.styleable.BaseNodeProgressView_bnpvWidth, 5);
        nodeRadius = typedArray.getDimension(R.styleable.BaseNodeProgressView_bnpvRodeRadius, 10);
        mIsRotate = typedArray.getBoolean(R.styleable.BaseNodeProgressView_bnpvRotate, true);
        typedArray.recycle();
        init();
    }

    public BaseNodeProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(getResources().getColor(R.color.nodeColor));
        paint.setAntiAlias(true);

        nodeInterval = dip2px(context, 50);

        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);

        dWidth = wm.getDefaultDisplay().getWidth();
        dHeight = wm.getDefaultDisplay().getHeight();
    }

    NodeProgressAdapter nodeProgressAdapter;

    /**
     * 设置适配数据
     */
    public void setNodeProgressAdapter(NodeProgressAdapter nodeProgressAdapter) {
        this.nodeProgressAdapter = nodeProgressAdapter;
        requestLayout();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (nodeProgressAdapter == null || nodeProgressAdapter.getCount() == 0)
            return;
        List data = nodeProgressAdapter.getData();


        if (mIsRotate) {
            drawRotateV(canvas,data);
        }else {
            drawRotateH(canvas,data);
        }




    }

    private void drawRotateH(Canvas canvas, List data) {
        Paint mPaint = new Paint();
        mPaint.setColor(getResources().getColor(R.color.nodeTextColor));
        canvas.drawRect(left,top,dWidth-left,width + top,mPaint);

        //计算出每一个点间距
        nodeInterval = (dWidth-left*2) / nodeProgressAdapter.getCount();

        for (int i = 0; i < nodeProgressAdapter.getCount(); i++) {

            canvas.drawCircle(i*nodeInterval+nodeInterval/2,top+width/2,nodeRadius,mPaint);

//            //文字换行
//            TextPaint textPaint = new TextPaint();
//            textPaint.setColor(getResources().getColor(R.color.nodeTextColor));
//            textPaint.setTextSize(35.0F);
//            textPaint.setAntiAlias(true);
//            StaticLayout layout = new StaticLayout(((LogisticsData)data.get(i)).getContext()+"", textPaint, (int) (dWidth * 0.8), Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
//            canvas.save();
//            canvas.translate(left * 2 + nodeRadius * 2, nodeRadius/2);
//            layout.draw(canvas);
//            canvas.restore();//重置
        }


    }

    private void drawRotateV(Canvas canvas, List data) {
        canvas.drawRect(left, top, width + left, nodeProgressAdapter.getCount() * nodeInterval + top, paint);
        for (int i = 0; i < nodeProgressAdapter.getCount(); i++) {
            if (i == 0) {
                Paint mPaint = new Paint();
                mPaint.setColor(getResources().getColor(R.color.nodeTextColor));
                //文字换行
                TextPaint textPaint = new TextPaint();
                textPaint.setColor(getResources().getColor(R.color.nodeTextColor));
                textPaint.setTextSize(35.0F);
                textPaint.setAntiAlias(true);
                StaticLayout layout = new StaticLayout(((LogisticsData)data.get(i)).getContext()+"", textPaint, (int) (dWidth * 0.8), Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
                canvas.save();
                canvas.translate(left * 2 + nodeRadius * 2, nodeRadius/2);
                layout.draw(canvas);
                canvas.restore();//重置

                //画圆
                canvas.drawCircle(width / 2 + left, i * nodeInterval + top, nodeRadius + 2, mPaint);
                mPaint.setStyle(Paint.Style.STROKE);//设置为空心
                mPaint.setStrokeWidth(8);//空心宽度
                mPaint.setAlpha(88);
                canvas.drawCircle(width / 2 + left, i * nodeInterval + top, nodeRadius + 4, mPaint);


            } else {
                paint.setColor(getResources().getColor(R.color.nodeColor));
                canvas.drawCircle(width / 2 + left, i * nodeInterval + top, nodeRadius, paint);

                //文字换行
                TextPaint textPaint = new TextPaint();
                textPaint.setColor(getResources().getColor(R.color.nodeColor));
                textPaint.setTextSize(35.0F);
                textPaint.setAntiAlias(true);
                StaticLayout layout = new StaticLayout(((LogisticsData)data.get(i)).getContext()+"", textPaint, (int) (dWidth * 0.8), Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
                canvas.save();//很重要，不然会样式出错
                canvas.translate(left * 2 + nodeRadius * 2, i * nodeInterval+(nodeRadius/2));
                layout.draw(canvas);
                canvas.restore();//重置
            }

        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (nodeProgressAdapter == null || nodeProgressAdapter.getCount() == 0)
            return;
        setMeasuredDimension(widthMeasureSpec, nodeProgressAdapter.getCount() * nodeInterval + top);
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
