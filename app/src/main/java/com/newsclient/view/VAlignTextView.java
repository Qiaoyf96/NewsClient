package com.newsclient.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.v7.widget.AppCompatTextView;

import com.newsclient.R;

import java.util.Vector;

/**
 * Created by lenovo on 2017/9/7.
 */

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public class VAlignTextView extends AppCompatTextView{

    private final String namespace = "rong.android.TextView";
    private String text;
    private float textSize;
    private float paddingLeft;
    private float paddingRight;
    private float marginLeft;
    private float marginRight;
    private int textColor;
    private int textHighlightedColor;
    private int maxLineCount;
    private Typeface textFont;
    private JSONArray colorIndex;
    private Paint paint1 = new Paint();
    private Paint paintColor = new Paint();
    private float textShowWidth;
    private float Spacing = 0;
    private float LineSpacing = 1f;//行与行的间距

    public VAlignTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.VAlignTextView);
        text = attrs.getAttributeValue(
                "http://schemas.android.com/apk/res/android", "text");

        // second argument is default setting

        textSize = typedArray.getDimension(R.styleable.VAlignTextView_textSize, 20);
        textColor = typedArray.getColor(R.styleable.VAlignTextView_textColor, Color.BLACK);
        textHighlightedColor = Color.BLUE;
        textFont = Typeface.DEFAULT;
        maxLineCount = typedArray.getInteger(R.styleable.VAlignTextView_maxDisplayLineCount, 10);

        paddingLeft = attrs.getAttributeIntValue(namespace, "paddingLeft", 0);
        paddingRight = attrs.getAttributeIntValue(namespace, "paddingRight", 0);
        marginLeft = attrs.getAttributeIntValue(namespace, "marginLeft", 0);
        marginRight = attrs.getAttributeIntValue(namespace, "marginRight", 0);
        paint1.setTextSize(textSize);
        paint1.setColor(textColor);
        paint1.setAntiAlias(true);
        paint1.setTypeface(textFont);
        paintColor.setAntiAlias(true);
        paintColor.setTextSize(textSize);
        paintColor.setColor(textHighlightedColor);
        paintColor.setTypeface(textFont);



        typedArray.recycle();
    }

    public VAlignTextView(Context context, float textSize, int textColor,
                      float paddingLeft, float paddingRight, float marginLeft, float marginRight){
        super(context);
        this.textSize = textSize;
        this.textColor = textColor;
        this.paddingLeft = paddingLeft;
        this.paddingRight = paddingRight;
        this.marginLeft = marginLeft;
        this.marginRight = marginRight;
        paint1.setTextSize(this.textSize);
        paint1.setColor(this.textColor);
        paint1.setAntiAlias(true);
        paintColor.setAntiAlias(true);
        paintColor.setTextSize(this.textSize);
        paintColor.setColor(this.textColor);
    }


    public JSONArray getColorIndex() {
        return colorIndex;
    }

    public void setColorIndex(JSONArray colorIndex) {
        this.colorIndex = colorIndex;
    }
    /**
     * 传入一个索引，判断当前字是否被高亮
     * @param index
     * @return
     * @throws JSONException
     */
    public boolean isColor(int index) throws JSONException{
        if(colorIndex == null){
            return false;
        }
        for(int i = 0 ; i < colorIndex.length() ; i ++){
            JSONArray array = colorIndex.getJSONArray(i);
            int start = array.getInt(0);
            int end = array.getInt(1)-1;
            if(index >= start && index <= end){
                return true;
            }
        }
        return false;
    }


    @Override
    protected void onDraw(Canvas canvas) {
//		super.onDraw(canvas);
        View view=(View)this.getParent();
        textShowWidth=view.getMeasuredWidth() - paddingLeft - paddingRight - marginLeft - marginRight;
        int lineCount = 0;

        text = this.getText().toString();//.replaceAll("\n", "\r\n");
        if(text==null)return;
        char[] textCharArray = text.toCharArray();
        // 已绘的宽度
        float drawedWidth = 0;
        float charWidth;
        for (int i = 0; i < textCharArray.length; i++) {
            charWidth = paint1.measureText(textCharArray, i, 1);

            if(textCharArray[i]=='\n'){
                lineCount++;
                drawedWidth = 0;
                continue;
            }
            if (textShowWidth - drawedWidth < 2*charWidth && lineCount + 1 == maxLineCount) {
                    char[] temp = "...".toCharArray();
                    canvas.drawText(temp, 0, 3, paddingLeft + drawedWidth,
                            (lineCount + 1) * textSize * LineSpacing, choosePaint(i));
                    break;
            }
            if (textShowWidth - drawedWidth < charWidth) {
                // try creating new line
//                if (lineCount + 1 == maxLineCount){
//                    char[] temp = "...".toCharArray();
//                    canvas.drawText(temp, 0, 3, paddingLeft + drawedWidth,
//                            (lineCount + 1) * textSize * LineSpacing, choosePaint(i));
//                    break;
//                }
                lineCount++;
                drawedWidth = 0;
            }

            canvas.drawText(textCharArray, i, 1, paddingLeft + drawedWidth,
                    (lineCount + 1) * textSize * LineSpacing, choosePaint(i));

            if(textCharArray[i] > 127 && textCharArray[i] != '、'
                    && textCharArray[i] != '，' && textCharArray[i] != '。'
                    && textCharArray[i] != '：' && textCharArray[i] != '！'){
                drawedWidth += charWidth + Spacing;

            }else{
                drawedWidth += charWidth;

            }
        }
        setHeight((int) ((lineCount + 1) * (int) textSize * LineSpacing + 10));
    }
    private Paint choosePaint(int i){
        boolean color = false;
        try {
            color = isColor(i);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return (color ? paintColor : paint1);
    }
    public float getSpacing() {
        return Spacing;
    }
    public void setSpacing(float spacing) {
        Spacing = spacing;
    }
    public float getLineSpacing() {
        return LineSpacing;
    }
    public void setLineSpacing(float lineSpacing) {
        LineSpacing = lineSpacing;
    }
    public float getTextSize() {
        return textSize;
    }
    public void setTextSize(float textSize) {
        this.textSize = textSize;
        paint1.setTextSize(textSize);
        paintColor.setTextSize(textSize);
    }


}


//public class VAlignTextView extends AppCompatTextView {
//    public  static  int m_iTextHeight; //文本的高度
//    public  static  int m_iTextWidth;//文本的宽度
//
//    private Paint mPaint = null;
//    private String string="";
//    private float LineSpace = 0;//行间距
//    private int left_Margin;
//    private int right_Margin;
//    private int bottom_Margin;
//
//    Vector m_String=new Vector();
//
//    public VAlignTextView(Context context, AttributeSet set)
//    {
//        super(context,set);
//        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
//        TypedArray typedArray = context.obtainStyledAttributes(set, R.styleable.VAlignTextView);
//        int width = displayMetrics.widthPixels;
//        left_Margin = 29;
//        right_Margin = 29;
//        bottom_Margin = 29;
//        width = width - left_Margin -right_Margin;
//        float textsize = typedArray.getDimension(R.styleable.VAlignTextView_textSize, 34);
//        int textcolor = typedArray.getColor(R.styleable.VAlignTextView_textColor, getResources().getColor(R.color.recentTitleTextColor));
//        float linespace = typedArray.getDimension(R.styleable.VAlignTextView_lineSpacingExtra, 15);
//        int typeface = typedArray.getColor(R.styleable.VAlignTextView_typeface, 0);
//
//        typedArray.recycle();
//
//        //设置TextView的宽度和行间距
//        m_iTextWidth=width;
//        LineSpace=linespace;
//
//        // 构建paint对象
//        mPaint = new Paint();
//        mPaint.setAntiAlias(true);
//        mPaint.setColor(textcolor);
//        mPaint.setTextSize(textsize);
//        switch(typeface){
//            case 0:
//                mPaint.setTypeface(Typeface.DEFAULT);
//                break;
//            case 1:
//                mPaint.setTypeface(Typeface.SANS_SERIF);
//                break;
//            case 2:
//                mPaint.setTypeface(Typeface.SERIF);
//                break;
//            case 3:
//                mPaint.setTypeface(Typeface.MONOSPACE);
//                break;
//            default:
//                mPaint.setTypeface(Typeface.DEFAULT);
//                break;
//        }
//
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas)
//    {
//        super.onDraw(canvas);
//
//        char ch;
//        int w = 0;
//        int istart = 0;
//        int m_iFontHeight;
//        int m_iRealLine=0;
//        int x=2;
//        int y=30;
//
//
//
//        Paint.FontMetrics fm = mPaint.getFontMetrics();
//        m_iFontHeight = (int) Math.ceil(fm.descent - fm.top) + (int)LineSpace;//计算字体高度（字体高度＋行间距）
//        for (int i = 0; i < string.length(); i++)
//        {
//            ch = string.charAt(i);
//            float[] widths = new float[1];
//            String srt = String.valueOf(ch);
//            mPaint.getTextWidths(srt, widths);
//            if (ch == '\n'){
//                m_iRealLine++;
//                m_String.addElement(string.substring(istart, i));
//                istart = i + 1;
//                w = 0;
//            }else{
//                w += (int) (Math.ceil(widths[0]));
//                if (w > m_iTextWidth){
//                    m_iRealLine++;
//                    m_String.addElement(string.substring(istart, i));
//                    istart = i;
//                    i--;
//                    w = 0;
//                }else{
//                    if (i == (string.length() - 1)){
//                        m_iRealLine++;
//                        m_String.addElement(string.substring(istart, string.length()));
//                    }
//                }
//            }
//        }
//        m_iTextHeight=m_iRealLine*m_iFontHeight+2;
//        //canvas.setViewport(m_iTextWidth, m_iTextWidth);
//        //canvas.se
//        for (int i = 0, j = 0; i < m_iRealLine; i++, j++)
//        {
//            canvas.drawText((String)(m_String.elementAt(i)), x,  y+m_iFontHeight * j, mPaint);
//        }
//    }
//
//
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
//    {
//        int measuredHeight = measureHeight(heightMeasureSpec);
//        int measuredWidth = measureWidth(widthMeasureSpec);
//        this.setMeasuredDimension(measuredWidth, measuredHeight);
//        LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(measuredWidth,measuredHeight);
//        layout.leftMargin= left_Margin;
//        layout.rightMargin= right_Margin;
//        layout.bottomMargin= bottom_Margin;
//        this.setLayoutParams(layout);
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//    }
//
//    private int measureHeight(int measureSpec)
//    {
//        int specMode = MeasureSpec.getMode(measureSpec);
//        int specSize = MeasureSpec.getSize(measureSpec);
//        // Default size if no limits are specified.
//        initHeight();
//        int result = m_iTextHeight;
//        if (specMode == MeasureSpec.AT_MOST){
//            // Calculate the ideal size of your
//            // control within this maximum size.
//            // If your control fills the available
//            // space return the outer bound.
//            result = specSize;
//        }else if (specMode == MeasureSpec.EXACTLY){
//            // If your control can fit within these bounds return that value.
////            result = specSize;
//        }
//        return result;
//    }
//
//    private void initHeight()
//    {
//        //设置 CY TextView的初始高度为0
//        m_iTextHeight=0;
//
//        //大概计算 CY TextView所需高度
//        Paint.FontMetrics fm = mPaint.getFontMetrics();
//        int m_iFontHeight = (int) Math.ceil(fm.descent - fm.top) + (int)LineSpace;
//        int line=0;
//        int istart=0;
//
//        int w=0;
//        for (int i = 0; i < string.length(); i++)
//        {
//            char ch = string.charAt(i);
//            float[] widths = new float[1];
//            String srt = String.valueOf(ch);
//            mPaint.getTextWidths(srt, widths);
//            if (ch == '\n'){
//                line++;
//                istart = i + 1;
//                w = 0;
//            }else{
//                w += (int) (Math.ceil(widths[0]));
//                if (w > m_iTextWidth){
//                    line++;
//                    istart = i;
//                    i--;
//                    w = 0;
//                }else{
//                    if (i == (string.length() - 1)){
//                        line++;
//                    }
//                }
//            }
//        }
//        m_iTextHeight=(line)*m_iFontHeight+2;
//    }
//
//    private int measureWidth(int measureSpec)
//    {
//        int specMode = MeasureSpec.getMode(measureSpec);
//        int specSize = MeasureSpec.getSize(measureSpec);
//        // Default size if no limits are specified.
//        int result = 500;
//        if (specMode == MeasureSpec.AT_MOST){
//            // Calculate the ideal size of your control
//            // within this maximum size.
//            // If your control fills the available space
//            // return the outer bound.
//            result = specSize;
//        }else if (specMode == MeasureSpec.EXACTLY){
//            // If your control can fit within these bounds return that value.
//            result = specSize;
//        }
//        return result;
//    }
//
//    public void SetText(String text)
//    {
//        string = text;
//        // requestLayout();
//        // invalidate();
//    }
//}

