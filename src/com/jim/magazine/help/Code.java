package com.jim.magazine.help;

import java.util.Random;

import android.R.color;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * 验证码
 * 
 * @author Administrator
 * 
 */
public class Code {
	// private static final char[] CHARS = { '2', '3', '4', '5', '6', '7', '8',
	// '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'l', 'm',
	// 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A',
	// 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
	// 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	private static final char[] CHARS = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9' };
	private static Code bmpCode;
	private static Context context;
	
	public void getContext(Context context){
		this.context=context;
	}
	
	public static Code getInstance() {
		if (bmpCode == null)bmpCode = new Code();
		return bmpCode;
	}

	// default settings
	// 验证码字数
	private static final int DEFAULT_CODE_LENGTH = 4;
	// 字体大小
	private static final int DEFAULT_FONT_SIZE = 29;
	// 线个数
	private static final int DEFAULT_LINE_NUMBER = 2;
	private static final int BASE_PADDING_LEFT = 10, RANGE_PADDING_LEFT = 20,
			BASE_PADDING_TOP = 25, RANGE_PADDING_TOP = 25;
	// 宽,高
	private static final int DEFAULT_WIDTH =  115, DEFAULT_HEIGHT =  45;

	// settings decided by the layout xml
	// canvas width and height
	//private int width = DensityUtil.px2dip(context, DEFAULT_WIDTH), 
	             // height =DensityUtil.px2dip(context, DEFAULT_HEIGHT);
	private int width = DEFAULT_WIDTH,height= DEFAULT_HEIGHT;
	
	// random word space and pading_top
	private int base_padding_left = BASE_PADDING_LEFT,
			range_padding_left = RANGE_PADDING_LEFT,
			base_padding_top = BASE_PADDING_TOP,
			range_padding_top = RANGE_PADDING_TOP;

	// number of chars, lines; font size
	private int codeLength = DEFAULT_CODE_LENGTH,
			line_number = DEFAULT_LINE_NUMBER, font_size = DEFAULT_FONT_SIZE;

	// variables
	private String code;
	private int padding_left, padding_top;
	private Random random = new Random();

	// 验证码图片
	public Bitmap createBitmap() {
		padding_left = 0;

		Bitmap bp = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas c = new Canvas(bp);

		code = createCode();
		// 背景 灰色
		c.drawColor(Color.LTGRAY);
		Paint paint = new Paint();
		paint.setTextSize(font_size);

		for (int i = 0; i < code.length(); i++) {
			randomTextStyle(paint);
			randomPadding();
			c.drawText(code.charAt(i) + "", padding_left, padding_top, paint);
		}

		for (int i = 0; i < line_number; i++) {
			drawLine(c, paint);
		}

		c.save(Canvas.ALL_SAVE_FLAG);// 保存
		c.restore();//
		return bp;
	}

	public String getCode() {
		return code;
	}

	// 验证码
	private String createCode() {
		StringBuilder buffer = new StringBuilder();
		for (int i = 0; i < codeLength; i++) {
			buffer.append(CHARS[random.nextInt(CHARS.length)]);
		}
		return buffer.toString();
	}

	private void drawLine(Canvas canvas, Paint paint) {
		int color = randomColor();
		int startX = random.nextInt(width);
		int startY = random.nextInt(height);
		int stopX = random.nextInt(width);
		int stopY = random.nextInt(height);
		paint.setStrokeWidth(1);
		paint.setColor(color);
		canvas.drawLine(startX, startY, stopX, stopY, paint);
	}

	private int randomColor() {
		return randomColor(1);
	}

	private int randomColor(int rate) {
		int red = random.nextInt(256) / rate;
		int green = random.nextInt(256) / rate;
		int blue = random.nextInt(256) / rate;

		// return Color.red(red);
		return Color.rgb(red, green, blue);
	}

	private void randomTextStyle(Paint paint) {
		// int color = randomColor();
		// 设置字体颜色
		// Color color = new Color();
		// color.red(R.color.green);
		// color.alpha(R.color.green);
		paint.setColor(Color.RED);
		paint.setFakeBoldText(random.nextBoolean()); // true为粗体，false为非粗体
		float skewX = random.nextInt(11) / 10;
		skewX = random.nextBoolean() ? skewX : -skewX;
		paint.setTextSkewX(skewX); // float类型参数，负数表示右斜，整数左斜
		// paint.setUnderlineText(true); //true为下划线，false为非下划线
		// paint.setStrikeThruText(true); //true为删除线，false为非删除线
	}

	private void randomPadding() {
		padding_left += base_padding_left + random.nextInt(range_padding_left);
		padding_top = base_padding_top + random.nextInt(range_padding_top);
	}

}
