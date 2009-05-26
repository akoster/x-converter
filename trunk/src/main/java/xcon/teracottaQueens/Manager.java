package xcon.teracottaQueens;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

public class Manager {

    // huiswerk
    // 1 JProgressbar invoeren
    // 2 met meerdere machines configureren
    // - .jar file maken van QueenWorker (en QueenTask, QueenResult, QueenWork)
    // - .bat file om de .jar te runnen (met java -jar Worker.jar bijvoorbeeld)
    // - makkelijk te installeren maken
    // - evt met NSIS?
    // 3 eigen ESB bouwen met Terracotta
    // -

    public QueenWork queenWork = new QueenWork();
    private static final int DEFAULT_BOARD_SIZE = 13;
    int total = 0;
    JFrame theFrame;
    Map<String, JProgressBar> progressHashmap ;


    public static void main(String[] args) throws InterruptedException {

        int boardSize = DEFAULT_BOARD_SIZE;
        if (args.length >= 1) {
            boardSize = Integer.parseInt(args[0]);

        }
        new Manager().run(boardSize);
    }

    private void run(int boardSize) throws InterruptedException {

        System.out.println("Solving n-queens for board size " + boardSize);
        theFrame = new JFrame();
        theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentPane = theFrame.getContentPane();
        progressHashmap = new HashMap<String,JProgressBar >();
        theFrame.setSize(400, 400);
        theFrame.setVisible(true);
        
        // put tasks in queue
        for (int firstColumn = 0; firstColumn < boardSize; firstColumn++) {
            QueenTask queenTask = new QueenTask(firstColumn, boardSize);
            queenWork.tasks.put(queenTask);

            JProgressBar progressBar = new JProgressBar(0, 100);
            progressBar.setValue(0);
            progressBar.setStringPainted(true);
            JLabel statistics = new JLabel("column" + firstColumn);
            statistics.setVisible(true);
            String key = "column"+firstColumn ; 
            progressHashmap.put(key, progressBar); 
            contentPane.setLayout(new GridLayout(boardSize,2)); 
            contentPane.add(statistics);
            contentPane.add(progressBar);
        }
        // collect all the results
        int i = 0;
        int[] progress = new int[boardSize];
        // JProgressbar[] progress = new JProgressbar[boardSize];
        // for (n=0; n<boardSize; n++)
        // progress[n] = new JProgressbar();
        while (i < boardSize) {

            // haal de queenResult uit de queue
            // bepaal of het een result of progress type is
            // if result ==>
            // * i++
            // * update total
            // if progress
            // * array[firstqueen] updaten met result
            //  
            QueenResult result = (QueenResult) queenWork.results.take();
            if (result.type == QueenResult.Type.RESULT) {

                i++;
                total = total + result.value;
                //progress[result.firstQueenColumn] = 100;
                String key = "column"+result.firstQueenColumn ; 
                JProgressBar progressBar = progressHashmap.get(key);
                progressBar.setValue(100);
                contentPane.repaint();
                // progressBars[result.firstQueenColumn].setValue(100);
                System.out.println("result:" + i + " worker:"
                    + result.firstQueenColumn + " #solutions=" + result.value);
            }
            else {
                String key = "column"+result.firstQueenColumn ;
                JProgressBar progressBar = progressHashmap.get(key);
                progressBar.setValue(result.value);
                contentPane.repaint();
                //progress[result.firstQueenColumn] = result.value;
                // progressBars[result.firstQueenColumn].setValue(result.value);
                //System.out.println("progress: " + Arrays.toString(progress));
            }

        }
       // System.out.println("progress: " + Arrays.toString(progress));
        //System.out.println("total solutions:" + total);

    }
}
