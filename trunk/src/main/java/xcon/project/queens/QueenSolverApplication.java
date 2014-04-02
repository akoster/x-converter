package xcon.project.queens;

public class QueenSolverApplication {

    private static final int BOARD_SIZE = 14;
    private static final int THREAD_POOL_SIZE = 2;
    private static final long serialVersionUID = 1L;

    // 1a. aantal solutions per thread in JLabel tonen
    // 1b. totaal aantal gevonden solutions bovenaan de lijst in JLabel tonen
    //
    // 2. Download Terracotta en experimenteer met een van hun hello world
    // voorbeelden
    //
    // 3. Applicatie clusteren met behulp van Terracotta

    public static void main(String[] args) {

        SolutionManager solutionManager =
            new SolutionManager(BOARD_SIZE, THREAD_POOL_SIZE);
        solutionManager.start();

    }
}
