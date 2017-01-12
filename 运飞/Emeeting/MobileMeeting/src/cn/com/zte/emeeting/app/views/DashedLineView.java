package cn.com.zte.emeeting.app.views;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;
/**
 * 虚线
 * @author wangbolong
 * @Date   2010-09-15
 */
public class DashedLineView extends View {    
    
    public DashedLineView(Context context, AttributeSet attrs) {    
        super(context, attrs);              
            
    }    
    
    @Override
    protected void onDraw(Canvas canvas) {

    	super.onDraw(canvas);  
        Paint paint = new Paint();  
        paint.setStyle(Paint.Style.STROKE);  
//        paint.setColor(getResources().getColor(R.color.linegrey));  
        paint.setColor(Color.DKGRAY);
        Path path = new Path();  
        path.moveTo(0, 0);  
        path.lineTo(getWidth(), 0);  
        PathEffect effects = new DashPathEffect(new float[] {  
                2, 2, 2, 2  
        }, 1);
        path.moveTo(0, 0);  
        path.lineTo(0, getHeight());  
        effects = new DashPathEffect(new float[] {  
                2, 2, 2, 2  
        }, 1);        
        
        paint.setPathEffect(effects);  
        canvas.drawPath(path, paint);  
    }
}

