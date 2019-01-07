package MazeSolver;

public class Main {
    public static void main(String[] args) {
        String mazeFile = "Z:\\code_java1\\FirstProject\\src\\MazeSolver\\maze_101_101.txt";
        MazeData data = new MazeData(mazeFile);
        data.print();
    }
}
