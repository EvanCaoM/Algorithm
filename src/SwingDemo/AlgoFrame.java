package SwingDemo;

import javax.swing.*;
import java.awt.*;

public class AlgoFrame extends JFrame {

    private int canvasWidth;
    private int canvasHeight;

    public AlgoFrame(String title, int canvasWidth, int canvasHeight){
        super(title);

        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;

        AlgoCanvas canvas = new AlgoCanvas();
        //canvas.setPreferredSize(new Dimension(canvasWidth, canvasHeight));
        setContentPane(canvas);
        pack();//根据panel大小自动调整窗口大小
        //setSize(canvasWidth, canvasHeight);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public int getCanvasWidth() {
        return canvasWidth;
    }

    public int getCanvasHeight() {
        return canvasHeight;
    }

    private Circle[] circles;
    public void render(Circle[] circles){
        this.circles = circles;
        repaint();//重新刷新
    }

    private class AlgoCanvas extends JPanel{

        //打开双缓存
        public AlgoCanvas(){
            super(true);
        }

        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
//            g.drawOval(50, 50, 300, 300);

            Graphics2D g2D = (Graphics2D)g;

            // 抗锯齿
            RenderingHints hints = new RenderingHints(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2D.addRenderingHints(hints);


            // 具体绘制
            AlgoVisHelper.setStrokeWidth(g2D, 1);
            AlgoVisHelper.setColor(g2D, Color.RED);
            for(Circle circle : circles){
                if(!circle.isFilled)
                    AlgoVisHelper.strokeCircle(g2D, circle.x, circle.y, circle.getR());
                else
                    AlgoVisHelper.fillCircle(g2D, circle.x, circle.y, circle.getR());

            }

        }

        @Override
        public Dimension getPreferredSize(){
            return new Dimension(canvasWidth, canvasHeight);//返回画布大小
        }
    }
}
