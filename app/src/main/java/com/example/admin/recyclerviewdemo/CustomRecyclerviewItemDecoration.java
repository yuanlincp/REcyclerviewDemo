package com.example.admin.recyclerviewdemo;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * recyclerview的装饰条
 */
public class CustomRecyclerviewItemDecoration extends RecyclerView.ItemDecoration {

    Paint paint;
    //设置分割线的高度为10px
    int dividerHeight = 10;
    //假如在中间底部绘制一个半径15的圆形
    int radius = 30;

    public CustomRecyclerviewItemDecoration() {
        paint = new Paint();
        paint.setColor(Color.RED);
    }

    /**
     * recyclerview 的item区域理解：
     * 每一个itemView其实是一个“回”字形布局，即是由两个矩形框构成，内层矩形显示itemwview内容，外层矩形限制
     * 显示范围。默认情况下两个矩形的宽高是一样的，两者重叠。 outRect就是内层view的显示区域，
     * 调用outRect.set（Rect rect）或者outRect.set(left, top, right, bottom)就是修改内层view的区域大小，
     * 也可以理解成给内层view设置margin值，其作用与在itemView的布局文件设置margin一样。
     *
     * 针对的是itemview本身
     * @param outRect 绘制itemview的区域
     * @param view    ==itemview
     * @param parent  即是指recyclerview
     * @param state   指recyclerview的状态
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        /**
         *
         *  outRect.set(); 该方法其实相当于给itemwview设置margin
         *
         */
        outRect.set(0, 0, 0, 10);
    }

    /**
     * 解释：
     * 该方法是指在itemview的区域内设置范围并绘制内容， 可绘制区域是itemView的（外层范围-内层范围）的矩形范围
     * 如果设置的绘制区域超出可绘制区域，那么将会给内层的view遮挡
     *
     *
     * 针对的是recyclerview本身
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        //获取绘制范围  或者范围点坐标
        //获取子view的数量
        int childCount = parent.getChildCount();
        //遍历每个itemview，分别获取他们的位置信息  然后绘制对应的分割线
        for (int i = 0; i < childCount; i++) {
            View childView = parent.getChildAt(i);
            //获取itemview的参数
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) childView.getLayoutParams();
            //获取分割线的上 顶  右  下的坐标点
            int left = parent.getPaddingLeft();
            int top = childView.getBottom() + layoutParams.bottomMargin;
            int right = parent.getWidth() - parent.getPaddingRight() - layoutParams.rightMargin;
            int bottom = top + dividerHeight;
            //绘制分隔线
            c.drawRect(left, top, right, bottom, paint);
        }
    }

    /**
     * 同样是绘制内容，但是层次上是在最顶层
     *
     *  针对的是recyclerview本身
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        paint.setColor(Color.BLUE);
        int childCound = parent.getChildCount();
        for (int i = 0; i < childCound; i++) {
            View childView = parent.getChildAt(i);

            //绘制圆形  计算圆形中心点坐标
            int centerX = parent.getWidth() / 2;
            int centerY = childView.getBottom() + dividerHeight - radius;
            c.drawCircle(centerX, centerY, radius, paint);
        }
    }

}
