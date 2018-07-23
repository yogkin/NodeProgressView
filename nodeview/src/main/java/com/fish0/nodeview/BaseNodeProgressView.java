package com.fish0.nodeview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

/**
 * User: Daidingkang(ddk19941017@Gmail.com)
 * Date: 2016-06-28
 * Time: 09:43
 * FIXME
 */
public class BaseNodeProgressView extends View {

    private  int mLineColor;
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

    //选中点位置
    private int mSelectIndex;
    private int mTextColor;

    public BaseNodeProgressView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public BaseNodeProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BaseNodeProgressView);
        width = typedArray.getDimension(R.styleable.BaseNodeProgressView_lineWidth, 5);
        nodeRadius = typedArray.getDimension(R.styleable.BaseNodeProgressView_radius, 10);
        mIsRotate = typedArray.getBoolean(R.styleable.BaseNodeProgressView_rotate, true);
        mLineColor = typedArray.getColor(R.styleable.BaseNodeProgressView_lineColor, Color.BLUE);
        mTextColor = typedArray.getColor(R.styleable.BaseNodeProgressView_textColor, Color.BLUE);
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

    /**
     * 设置选中点坐标
     */
    public void setNodeSelectIndex(int selectIndex) {
        this.mSelectIndex = selectIndex;
        requestLayout();
    }

    /**
     * 设置节点方向
     */

    public void setNodeOricentalV() {
        this.mIsRotate = true;
        requestLayout();
    }
   /**
     * 设置节点方向
     */

    public void setNodeOricentalH() {
        this.mIsRotate = false;
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

        mPaint.setAlpha(88);
        canvas.drawRect(left,top,dWidth-left,width + top,mPaint);

        //计算出每一个点间距
        nodeInterval = (dWidth-left*2) / nodeProgressAdapter.getCount();

        for (int i = 0; i < nodeProgressAdapter.getCount(); i++) {
            mPaint.reset();
            if (i<=mSelectIndex) {
                mPaint.setColor(mLineColor);
                //画圆
                canvas.drawRect(left,top,nodeInterval*(mSelectIndex+1)+nodeRadius,width + top,mPaint);

                canvas.drawCircle(i*nodeInterval+nodeInterval/2+left, top+width/2, nodeRadius + 2, mPaint);
                mPaint.setStyle(Paint.Style.STROKE);//设置为空心
                mPaint.setStrokeWidth(8);//空心宽度
                mPaint.setAlpha(88);
                canvas.drawCircle(i*nodeInterval+nodeInterval/2+left, top+width/2, nodeRadius + 4, mPaint);
            }else {
                mPaint.setColor(getResources().getColor(R.color.nodeColor));
                canvas.drawCircle(i*nodeInterval+nodeInterval/2+left,top+width/2,nodeRadius,mPaint);
            }




            //文字换行
            String str = ((LogisticsData) data.get(i)).getContext() + "";
            TextPaint textPaint = new TextPaint();
            textPaint.setColor(mTextColor);
            textPaint.setTextSize(35.0F);
            float measureText = textPaint.measureText(str);



            textPaint.setAntiAlias(true);
            StaticLayout layout = new StaticLayout(str, textPaint, (int) (nodeInterval*0.9), Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
            if (measureText>layout.getWidth()) {
                measureText=layout.getWidth();
            }
            canvas.save();
            canvas.translate(i*nodeInterval+left+(nodeInterval-measureText)/2, nodeRadius/2+top+40);
            layout.draw(canvas);
            canvas.restore();//重置
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
