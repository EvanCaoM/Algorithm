package MazeGenerator;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.util.LinkedList;
import java.util.Stack;

public class AlgoVisualizer {

    private static int DELAY = 5;
    private static int blockSide = 8;
    private static final int d[][] = {{-1,0},{0,1},{1,0},{0,-1}};
    private MazeData data;        // 数据
    private AlgoFrame frame;    // 视图

    public AlgoVisualizer(int N, int M){

        // 初始化数据
        data = new MazeData(N, M);
        int sceneHeight = data.N() * blockSide;
        int sceneWidth = data.M() * blockSide;

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
        setData(-1, -1);

//        go(data.getEntranceX(), data.getEntranceY() + 1);
//        goWithStack();
//        goWithQueue();
//        goWithRandomQueue();
        goWithRandomInAndOut();
        setData(-1, -1);

    }

    private void goWithRandomInAndOut(){
        RandomInAndOut<Position> queue = new RandomInAndOut<Position>();
        Position first = new Position(data.getEntranceX(), data.getEntranceY() + 1);
        queue.add(first);
        data.visited[first.getX()][first.getY()] = true;
        data.openMist(first.getX(),first.getY());
        while (queue.size() > 0){
            Position curPos = queue.remove();
            for (int i = 0; i < 4; i++) {
                int newX = curPos.getX() + d[i][0] * 2;
                int newY = curPos.getY() + d[i][1] * 2;
                if (data.inArea(newX, newY) && !data.visited[newX][newY]) {
                    queue.add(new Position(newX, newY));
                    data.visited[newX][newY] = true;
                    data.openMist(newX, newY);
                    setData(curPos.getX() + d[i][0], curPos.getY() + d[i][1]);
                }
            }
        }
    }

    private void goWithRandomQueue(){
        RandomQueue<Position> queue = new RandomQueue<Position>();
        Position first = new Position(data.getEntranceX(), data.getEntranceY() + 1);
        queue.add(first);
        data.visited[first.getX()][first.getY()] = true;
        data.openMist(first.getX(),first.getY());
        while (queue.size() > 0){
            Position curPos = queue.remove();
            for (int i = 0; i < 4; i++) {
                int newX = curPos.getX() + d[i][0] * 2;
                int newY = curPos.getY() + d[i][1] * 2;
                if (data.inArea(newX, newY) && !data.visited[newX][newY]) {
                    queue.add(new Position(newX, newY));
                    data.visited[newX][newY] = true;
                    data.openMist(newX, newY);
                    setData(curPos.getX() + d[i][0], curPos.getY() + d[i][1]);
                }
            }
        }
    }

    private void goWithQueue(){
        LinkedList<Position> queue = new LinkedList<Position>();
        Position first = new Position(data.getEntranceX(), data.getEntranceY() + 1);
        queue.addLast(first);
        data.visited[first.getX()][first.getY()] = true;
        while (queue.size() > 0){
            Position curPos = queue.pop();
            for (int i = 0; i < 4; i++) {
                int newX = curPos.getX() + d[i][0] * 2;
                int newY = curPos.getY() + d[i][1] * 2;
                if (data.inArea(newX, newY) && !data.visited[newX][newY]) {
                    queue.addLast(new Position(newX, newY));
                    data.visited[newX][newY] = true;
                    setData(curPos.getX() + d[i][0], curPos.getY() + d[i][1]);
                }
            }
        }
    }

    private void goWithStack(){
        Stack<Position> stack = new Stack<Position>();
        stack.push(new Position(data.getEntranceX(), data.getEntranceY() + 1));
        data.visited[data.getEntranceX()][data.getEntranceY() + 1] = true;
        while (!stack.empty()) {
            Position curPos = stack.pop();
            for (int i = 0; i < 4; i++) {
                int newX = curPos.getX() + d[i][0] * 2;
                int newY = curPos.getY() + d[i][1] * 2;
                if (data.inArea(newX, newY) && !data.visited[newX][newY]) {
                    stack.push(new Position(newX, newY));
                    data.visited[newX][newY] = true;
                    setData(curPos.getX() + d[i][0], curPos.getY() + d[i][1]);
                }
            }
        }
    }

    private void go(int x, int y){
        if (!data.inArea(x, y))
            throw new IllegalArgumentException("x,y are out of index in go function!");
        data.visited[x][y] = true;
        for (int i = 0; i < 4; i++) {
            int newX = x + d[i][0] * 2;
            int newY = y + d[i][1] * 2;
            if (data.inArea(newX, newY) && !data.visited[newX][newY]){
                setData(x + d[i][0], y + d[i][1]);
                go(newX, newY);
            }
        }
    }

    private void setData(int x, int y){
        if (data.inArea(x, y))
            data.maze[x][y] = MazeData.ROAD;
        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    private class AlgoKeyListener extends KeyAdapter{
        @Override
        public void keyReleased(KeyEvent event){
            if (event.getKeyChar() == ' '){
                for (int i = 0; i < data.N(); i++) {
                    for (int j = 0; j < data.M(); j++) {
                        data.visited[i][j] = false;
                    }
                }
                new Thread(() -> {
                    goWithSolver(data.getEntranceX(), data.getEntranceY());
                }).start();
            }
        }
    }

    private boolean goWithSolver(int x, int y){
        if (!data.inArea(x,y))
            throw new IllegalArgumentException("x,y are illegal!");
        data.visited[x][y] = true;
        setDataWithSolver(x, y, true);
        if (x != data.getExitX() || y != data.getExitY()) {
            for (int i = 0; i < 4; i++) {
                int newX = x + d[i][0];
                int newY = y + d[i][1];
                if (data.inArea(newX, newY) && data.getMaze(newX, newY) == MazeData.ROAD && !data.visited[newX][newY])
                    if (goWithSolver(newX, newY))
                        return true;
            }
            setDataWithSolver(x, y, false);
            return false;
        }
        else
            return true;
    }

    private void setDataWithSolver(int x, int y, boolean isPath){
        if (data.inArea(x, y)) {
            data.path[x][y] = isPath;
        }
        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    private class AlgoMouseListener extends MouseAdapter{ }

    public static void main(String[] args) {

        int N = 101;
        int M = 101 ;

        AlgoVisualizer visualizer = new AlgoVisualizer(N, M);
    }
}
