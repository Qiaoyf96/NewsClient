package com.java.group28.newsclient.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.View;

import com.java.group28.newsclient.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VAlignTextView extends AppCompatTextView{

    private final String namespace = "rong.android.TextView";
    private List<Integer> highlightIndexs;
    private String text;
    private String keyword;
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
        textHighlightedColor = typedArray.getColor(R.styleable.VAlignTextView_textHighlightedColor, Color.RED);
        textFont = Typeface.DEFAULT;
        maxLineCount = typedArray.getInteger(R.styleable.VAlignTextView_maxDisplayLineCount, 10);
        keyword = typedArray.getString(R.styleable.VAlignTextView_keyword);
        LineSpacing = typedArray.getFloat(R.styleable.VAlignTextView_lineSpacing, 1);

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

    public void setKeyword(String keyword){
        this.keyword = keyword;
    }

    public void setTextHighlightedColor(int id){
        this.textHighlightedColor = getResources().getColor(id);
        this.paintColor.setColor(this.textHighlightedColor);
    }

    public void keywordCheck(){
        highlightIndexs = new ArrayList<>();
        if (keyword == null || keyword == "" || text == null || text == ""){
            return;
        }
        Pattern p = Pattern.compile(this.keyword);
        Matcher m = p.matcher(this.text);
        while(m.find()){
            highlightIndexs.add(m.start());
        }
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

    public boolean isHighlighted(int index){
        if (highlightIndexs.size() == 0){
            return false;
        }
        for (int i : highlightIndexs){
            if (i <= index && index < i + keyword.length()){
                return true;
            }
        }
        return false;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        View view=(View)this.getParent();
        textShowWidth=view.getMeasuredWidth() - paddingLeft - paddingRight - marginLeft - marginRight;
        int lineCount = 0;

        text = this.getText().toString();

        keywordCheck();

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

        return (isHighlighted(i) ? paintColor : paint1);
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

    public void setTextColor(int color){
        this.textColor = color;
        paint1.setColor(color);
    }
}

