package com.example.ywx.wuzipanel;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ywx on 2017/4/13.
 */

public class WuziPanel extends View {
    private int panelWidth;
    private float lineHeight;
    private int MAX_LINE=10;
    private final static int MAX_IN_LINE=5;
    private Paint mPanit;
    private Bitmap white;
    private Bitmap black;
    private List<Point> whiteList=new ArrayList<>();
    private List<Point> blackList=new ArrayList<>();
    private boolean isGameover=false;
    private boolean isWhite=true;
    private boolean isWhiteWin=false;
    private boolean isFull=false;
    private float ratio=3*1.0f/4;
    public WuziPanel(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setBackgroundColor(Color.parseColor("#F0BF70"));
        mPanit=new Paint();
        init();
    }

    private void init() {
        mPanit.setColor(0x88000000);
        mPanit.setAntiAlias(true);
        mPanit.setDither(true);
        mPanit.setStyle(Paint.Style.STROKE);
        white= BitmapFactory.decodeResource(getResources(),R.drawable.stone_w2);
        black= BitmapFactory.decodeResource(getResources(),R.drawable.stone_b1);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action=event.getAction();
        if(action==MotionEvent.ACTION_UP)
        {
            int x= (int) event.getX();
            int y= (int) event.getY();
            Point p=getPoint(x,y);
            if(whiteList.contains(p)||blackList.contains(p))
            {
                return false;
            }
            if(isWhite)
            {
                whiteList.add(p);
                isWhite=false;
                if(checkWin((int)(x/lineHeight),(int)(y/lineHeight),whiteList))
                {
                    isGameover=true;
                    isWhiteWin=true;
                }
            }
            else
            {
                blackList.add(p);
                isWhite=true;
                if(checkWin((int)(x/lineHeight),(int)(y/lineHeight),blackList))
                {
                    isWhiteWin=false;
                    isGameover=true;
                }
            }
            if(whiteList.size()+blackList.size()==MAX_LINE*MAX_LINE)
                isFull=true;
            invalidate();
        }
        return true;
    }

    private boolean checkWin(int x, int y,List<Point> List) {
        if(verticalType(x,y,List))
        {
            Log.d("WuziPanel","vertical");
            return true;
        }
        if(horizitonalType(x,y,List))
        {
            Log.d("WuziPanel","horizitional");
            return true;
        }
        if(leftlineType(x,y,List))
        {
            Log.d("WuziPanel","leftline");
            return true;
        }
        if(rightlineType(x,y,List))
        {
            Log.d("WuziPanel","rightline");
            return true;
        }
        return false;
    }

    private boolean rightlineType(int x, int y, List<Point> list) {
        int count=1;
        for(int i=1;i<MAX_IN_LINE;i++)
        {
            if(list.contains(new Point(x+i,y-i)))
            {
                count++;
            }
            else
            {
                break;
            }
        }
        for(int i=1;i<MAX_IN_LINE;i++)
        {
            if(list.contains(new Point(x-i,y+i)))
            {
                count++;
            }
            else
            {
                break;
            }
        }
        if(count>=5)
            return true;
        return false;
    }

    private boolean leftlineType(int x, int y, List<Point> list) {
        int count=1;
        for(int i=1;i<MAX_IN_LINE;i++)
        {
            if(list.contains(new Point(x-i,y-i)))
            {
                count++;
            }
            else
            {
                break;
            }
        }
        for(int i=1;i<MAX_IN_LINE;i++)
        {
            if(list.contains(new Point(x+i,y+i)))
            {
                count++;
            }
            else
            {
                break;
            }
        }
        if(count>=5)
            return true;
        return false;
    }

    private boolean horizitonalType(int x, int y, List<Point> list) {
        int count=1;
        for(int i=1;i<MAX_IN_LINE;i++)
        {
            if(list.contains(new Point(x-i,y)))
            {
                count++;
            }
            else
            {
                break;
            }
        }
        for(int i=1;i<MAX_IN_LINE;i++)
        {
            if(list.contains(new Point(x+i,y)))
            {
                count++;
            }
            else
            {
                break;
            }
        }
        if(count>=5)
            return true;
        return false;
    }

    private boolean verticalType(int x, int y, List<Point> list) {
        int count=1;
        for(int i=1;i<MAX_IN_LINE;i++)
        {
            if(list.contains(new Point(x,y-i)))
            {
                count++;
            }
            else
            {
                break;
            }
        }
        for(int i=1;i<MAX_IN_LINE;i++)
        {
            if(list.contains(new Point(x,y+i)))
            {
                count++;
            }
            else
            {
                break;
            }
        }
        if(count>=5)
            return true;
        return false;
    }

    private Point getPoint(int x,int y) {
        Log.d("WuziPanel",(int)(x/lineHeight)+"   "+(int)(y/lineHeight));
        return new Point((int)(x/lineHeight),(int)(y/lineHeight));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int width=Math.min(widthSize,heightSize);
        if(widthMode==MeasureSpec.UNSPECIFIED) {
            width=heightSize;
        }else  if(heightMode==MeasureSpec.UNSPECIFIED)
        {
            width=widthSize;
        }
        setMeasuredDimension(width,width);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        panelWidth=w;
        lineHeight=(panelWidth*1.0f)/MAX_LINE;
        white=Bitmap.createScaledBitmap(white,(int)(lineHeight*ratio),(int)(lineHeight*ratio),false);
        black=Bitmap.createScaledBitmap(black,(int)(lineHeight*ratio),(int)(lineHeight*ratio),false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBoard(canvas);
        drawPieces(canvas);
        if(isFull)
        {
            AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
            builder.setTitle("旗鼓相当的对手!");
            builder.setMessage("棋盘已满");
            builder.setCancelable(false);
            builder.setPositiveButton("再来一局", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    whiteList.clear();
                    blackList.clear();
                    invalidate();
                    isGameover=false;
                    isFull=false;
                }
            });
            builder.create().show();
        }
        if(isGameover)
        {
            AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
            builder.setTitle("恭喜你");
            if(isWhiteWin)
            builder.setMessage("白子获胜");
            else
            builder.setMessage("黑子获胜");
            builder.setCancelable(false);
            builder.setPositiveButton("再来一局", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    whiteList.clear();
                    blackList.clear();
                    invalidate();
                    isGameover=false;
                    isFull=false;
                }
            });
          builder.create().show();
        }
    }

    private void drawPieces(Canvas canvas) {
        for(int i=0;i<whiteList.size();i++)
        {
            Point whitePoint=whiteList.get(i);
            canvas.drawBitmap(white,(whitePoint.x-(1-ratio)/2)*lineHeight+25,(whitePoint.y-(1-ratio)/2)*lineHeight+25,null);
        }
        for(int i=0;i<blackList.size();i++)
        {
            Point blackPoint=blackList.get(i);
            canvas.drawBitmap(black,(blackPoint.x-(1-ratio)/2)*lineHeight+25,(blackPoint.y-(1-ratio)/2)*lineHeight+25,null);
        }
    }

    private void drawBoard(Canvas canvas) {
        for(int i=0;i<MAX_LINE;i++)
        {
            int startX=(int)(lineHeight/2);
            int endX= (int) (panelWidth-lineHeight/2);
            int Y=(int)(lineHeight*(i+0.5));
            canvas.drawLine(startX,Y,endX,Y,mPanit);
            canvas.drawLine(Y,startX,Y,endX,mPanit);
        }
    }
}
