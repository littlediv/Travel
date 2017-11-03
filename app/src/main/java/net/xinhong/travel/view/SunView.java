package net.xinhong.travel.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import net.xinhong.travel.R;

import org.joda.time.DateTime;

/**
 * Created by mac on 2017/2/6.
 */
public class SunView extends View {

    private int roundColor;
    private int roundBackgroundColor;
    private String sunriseText;
    private String sunsetText;
    private int sunTextColor;
    private float sunTextSize;
    private float roundWidth;

    private Paint paint;
    private Bitmap bitmap;
    private RectF rectFArc;
    private RectF rectFBitmap;
    private Rect bounds;

    private int startAngle = 190;
    private int angleLength = 160;
    private float radius;

    int totalValue;
    int currentValue;
    private int animationLength = 3000;
    private int currentDegree;

    int widthFinal, heightFinal;
    int sunriseTimeY;
    int bitmapWidth = 100;
    int arcHeightOfHalf = 80;

    int leftOffset = 10;
    boolean isShowCircle = false;
    float ovalCenterX, ovalCenterY;

    public SunView(Context context) {
        this(context, null);
    }

    public SunView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SunView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SunView);
        roundColor = typedArray.getColor(R.styleable.SunView_roundcolor, Color.YELLOW);
        roundBackgroundColor = typedArray.getColor(R.styleable.SunView_roundbackgroundcolor, Color.GRAY);
        roundWidth = typedArray.getDimension(R.styleable.SunView_roundWidth, 5);
        radius = typedArray.getDimension(R.styleable.SunView_radius, 10);
        sunriseText = typedArray.getString(R.styleable.SunView_sunriseText);
        sunsetText = typedArray.getString(R.styleable.SunView_sunsetText);
        sunTextColor = typedArray.getColor(R.styleable.SunView_sunTextColor, Color.WHITE);
        sunTextSize = typedArray.getDimension(R.styleable.SunView_sunTextSize, 35);


        sunriseText = "7:37";
        sunsetText = "17:29";
        typedArray.recycle();

        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        rectFBitmap = new RectF();
        rectFArc = new RectF();

        bounds = new Rect();

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_name);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        int resultWidth = 400;
        int resultHeight = 200;
        if (widthMode == MeasureSpec.EXACTLY) {
            resultWidth = width;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            resultHeight = height;
        }

        setMeasuredDimension(resultWidth, resultHeight);

        widthFinal = getMeasuredWidth();
        heightFinal = getMeasuredHeight();

        arcHeightOfHalf = heightFinal /2;
    }



    private static final String TAG = "SunView";
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.BLUE);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(sunTextColor);
        paint.setTextSize(sunTextSize);

        paint.getTextBounds(sunriseText, 0, sunriseText.length(), bounds);
        sunriseTimeY = heightFinal *3/4;
        canvas.drawText(sunriseText, leftOffset + bounds.left, sunriseTimeY, paint);
        paint.getTextBounds(sunsetText, 0, sunsetText.length(), bounds);
        canvas.drawText(sunsetText, widthFinal - leftOffset - bounds.right, sunriseTimeY, paint);

        rectFArc.set(leftOffset + bounds.centerX(), sunriseTimeY + bounds.top - arcHeightOfHalf, widthFinal - leftOffset - bounds.centerX(), sunriseTimeY + bounds.top + arcHeightOfHalf);
        paint.setColor(roundBackgroundColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(roundWidth);
        canvas.drawArc(rectFArc, startAngle, angleLength, false, paint);

        paint.setColor(roundColor);
        canvas.drawArc(rectFArc, startAngle, currentDegree, false, paint);

        if (isShowCircle) {
            double d = (startAngle + currentDegree) *Math.PI/180;

            ovalCenterX = (rectFArc.right-rectFArc.left)/2;

            ovalCenterY = (rectFArc.bottom - rectFArc.top) / 2 ;

            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle((float)(ovalCenterX *Math.cos(d) + ovalCenterX + rectFArc.left), (float)(ovalCenterY * Math.sin(d) + ovalCenterY + rectFArc.top) , radius, paint);
        }


        bitmapWidth = (bounds.bottom - bounds.top) * 3;
        rectFBitmap.set(widthFinal / 2 - bitmapWidth, sunriseTimeY - bitmapWidth, widthFinal / 2 + bitmapWidth, sunriseTimeY);
        canvas.drawBitmap(bitmap, null, rectFBitmap, null);

    }




    public void setTime(String sunriseText, String sunsetText) {
        this.sunriseText = sunriseText;
        this.sunsetText = sunsetText;

        if (!sunriseText.contains(":") || !sunsetText.contains(":")) {
            return;
        }

        String[] sunrise = sunriseText.split(":");
        String[] sunset = sunsetText.split(":");
        int sunriseHour = Integer.parseInt(sunrise[0]);
        int sunriseMinute = Integer.parseInt(sunrise[1]);
        int sunsetHour = Integer.parseInt(sunset[0]);
        int sunsetMinute = Integer.parseInt(sunset[1]);

        DateTime dateTime = DateTime.now();
        if (dateTime.getMinuteOfDay() < (sunriseHour * 60 + sunriseMinute)) {
            isShowCircle = false;
            return;
        }
        isShowCircle = true;

        totalValue = (sunsetHour -sunriseHour) * 60 + sunsetMinute - sunriseMinute;


        if (dateTime.getMinuteOfDay() > (sunsetHour * 60 + sunsetMinute)) {
            currentValue = totalValue;
            isShowCircle = false;
        }else
        {
            currentValue = dateTime.getMinuteOfDay() - (sunriseHour * 60 + sunriseMinute);

        }

        currentDegree = (int)(currentValue / (float) totalValue * angleLength);

        setAnimation(0, currentDegree, animationLength);

    }

    private void setAnimation(int start, int end, int time) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(start, end);
        valueAnimator.setDuration(animationLength);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentDegree = (int) animation.getAnimatedValue();
                invalidate();
            }
        });

        valueAnimator.start();

    }

    public int getRoundColor() {
        return roundColor;
    }

    public void setRoundColor(int roundColor) {
        this.roundColor = roundColor;
    }

    public int getRoundBackgroundColor() {
        return roundBackgroundColor;
    }

    public void setRoundBackgroundColor(int roundBackgroundColor) {
        this.roundBackgroundColor = roundBackgroundColor;
    }

    public String getSunriseText() {
        return sunriseText;
    }

    public void setSunriseText(String sunriseText) {
        this.sunriseText = sunriseText;
    }

    public String getSunsetText() {
        return sunsetText;
    }

    public void setSunsetText(String sunsetText) {
        this.sunsetText = sunsetText;
    }

    public int getSunTextColor() {
        return sunTextColor;
    }

    public void setSunTextColor(int sunTextColor) {
        this.sunTextColor = sunTextColor;
    }


    public float getSunTextSize() {
        return sunTextSize;
    }

    public void setSunTextSize(float sunTextSize) {
        this.sunTextSize = sunTextSize;
    }

}
