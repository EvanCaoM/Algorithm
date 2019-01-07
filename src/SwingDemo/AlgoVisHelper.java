package SwingDemo;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class AlgoVisHelper {
    private AlgoVisHelper(){}

    public static void setStrokeWidth(Graphics2D g2D, int w){
        int strokeWidth = w;
        g2D.setStroke(new BasicStroke(strokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
    }

    /**
     * 绘制空心圆
     * @param g2D 2D图
     * @param x 圆心坐标
     * @param y
     * @param r 半径
     */
    public static void strokeCircle(Graphics2D g2D, int x, int y, int r){
        Ellipse2D circle = new Ellipse2D.Double(x-r, y-r, 2*r, 2*r);
        g2D.draw(circle);
    }

    public static void fillCircle(Graphics2D g2D, int x, int y, int r){
        Ellipse2D circle = new Ellipse2D.Double(x-r, y-r, 2*r, 2*r);
        g2D.fill(circle);
    }

    public static void setColor(Graphics2D g2D, Color color){
        g2D.setColor(color);
    }

    public static void pause(int t){
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
