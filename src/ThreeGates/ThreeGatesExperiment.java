package ThreeGates;

public class ThreeGatesExperiment {

    private int N;

    public ThreeGatesExperiment(int N){
        if (N <= 0)
            throw new IllegalArgumentException("N must be larger than zero!");
        this.N = N;

    }

    public void run(boolean changeDoor){
        int wins = 0;
        for (int i = 0; i < N; i++) {
            if (play(changeDoor))
                wins ++;
        }
        System.out.println(changeDoor ? "change" : "Not change");
        System.out.println("wining rate:" + (double)wins/N);
    }

    private boolean play(boolean changeDoor){
        int prizeDoor = (int)(Math.random() * 3);
        int playerChoice = (int)(Math.random() * 3);

        if (playerChoice == prizeDoor)
            return changeDoor ? false : true;
        else
            return changeDoor ? true : false;
    }

    public static void main(String[] args) {
        ThreeGatesExperiment threeGatesExperiment = new ThreeGatesExperiment(1000000000);
        threeGatesExperiment.run(true);
        threeGatesExperiment.run(false);
    }
}
