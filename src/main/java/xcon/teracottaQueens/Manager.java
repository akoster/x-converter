package xcon.teracottaQueens;


public class Manager {

    /**
     * @param args
     */
    //private final static Logger LOG = Logger.getLogger(Manager.class);
    public QueenWork queenWork = new QueenWork();
    private static final int boardSize = 10;

    public static void main(String[] args) throws InterruptedException {
        new Manager().run();
    }

    private void run() throws InterruptedException {

        for (int firstColumn = 0; firstColumn < boardSize; firstColumn++) {
            QueenTask queenTask = new QueenTask(firstColumn,boardSize);
            System.out.println("executing Column " + firstColumn);
           System.out.println("coulums" + firstColumn);
           queenWork.tasks.put(queenTask);
        }
        
    }
}
