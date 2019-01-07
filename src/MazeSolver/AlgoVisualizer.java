package MazeSolver;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;

public class AlgoVisualizer {

    private static int DELAY = 5;
    private static int blockSide = 8;
    private MazeData data;        // 数据
    private AlgoFrame frame;    // 视图

    private static final int d[][] = {{-1, 0},{0,1},{1,0},{0,-1}};

    public AlgoVisualizer(String mazeFile){

        // 初始化数据
        data = new MazeData(mazeFile);

        int sceneHeight = data.getN() * blockSide;
        int sceneWidth = data.getM() * blockSide;
        // 初始化视图
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Welcome", sceneWidth, sceneHeight);
            // TODO: 根据情况决定是否加入键盘鼠标事件监听器
            frame.addKeyListener(new AlgoKeyListener());
            frame.addMouseListener(new AlgoMouseListener());
            new Thread(() -> {
                run();
            }).start();
        });
    }

    // 动画逻辑
    private void run(){
        setData(-1, -1, true);
        if (!go(data.getEntranceX(), data.getEntranceY()))
            System.out.println("The maze has no solution!");
        setData(-1, -1, true);
    }

    private boolean go(int x, int y){
        if (!data.inArea(x,y))
            throw new IllegalArgumentException("x,y are illegal!");
        data.visited[x][y] = true;
        setData(x, y, true);
        if (x != data.getExitX() || y != data.getExitY()) {
            for (int i = 0; i < 4; i++) {
                int newX = x + d[i][0];
                int newY = y + d[i][1];
                if (data.inArea(newX, newY) && data.getMaze(newX, newY) == MazeData.ROAD && !data.visited[newX][newY])
                    if (go(newX, newY))
                        return true;
            }
            setData(x, y, false);
            return false;
        }
        else
            return true;
    }

    private void setData(int x, int y, boolean isPath){
        if (data.inArea(x, y)) {
            data.path[x][y] = isPath;
        }
        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    // TODO: 根据情况决定是否实现键盘鼠标等交互事件监听器类
    private class AlgoKeyListener extends KeyAdapter{ }

    private class AlgoMouseListener extends MouseAdapter{ }

    public static void main(String[] args) {
        String mazeFile = "Z:\\code_java1\\FirstProject\\src\\MazeSolver\\maze_101_101.txt";
        AlgoVisualizer vis = new AlgoVisualizer(mazeFile);
    }
}
