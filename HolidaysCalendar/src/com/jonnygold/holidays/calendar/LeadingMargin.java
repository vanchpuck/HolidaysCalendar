package com.jonnygold.holidays.calendar;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.style.LeadingMarginSpan.LeadingMarginSpan2;

public class LeadingMargin implements LeadingMarginSpan2{

	private int margin;
    private int lines;
    
    public LeadingMargin(int margin, int lines){
    	this.margin = margin;
    	this.lines = lines;
    }
	
	@Override
	public void drawLeadingMargin(Canvas c, Paint p, int x, int dir, int top,
			int baseline, int bottom, CharSequence text, int start, int end,
			boolean first, Layout layout) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getLeadingMargin(boolean first) {
		if(first){
			return margin;
		}
		return 0;
	}

	@Override
	public int getLeadingMarginLineCount() {
		// TODO Auto-generated method stub
		return lines;
	}

}
