package xcon.project.queens.terracotta;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
    private static final int DEFAULT_BOARD_SIZE = 14;
    int totalSolutionCount = 0;
    int progressStatus;
    JFrame theFrame;
    Map<String, JProgressBar> progressHashmap;
    JProgressBar totalProgressBar = new JProgressBar();
    private static int boardSize;

    public static void main(String[] args) throws InterruptedException {

        boardSize = DEFAULT_BOARD_SIZE;
        if (args.length >= 1) {
            boardSize = Integer.parseInt(args[0]);

        }
        new Manager().run(boardSize);
    }

    private void run(int boardSize) throws InterruptedException {

        System.out.println("Solving n-queens for board size " + boardSize);
        progressHashmap = new HashMap<String, JProgressBar>();
        theFrame = new JFrame();
        theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentPane = theFrame.getContentPane();

        JPanel totalProgressPanel = new JPanel();
        totalProgressPanel.setLayout(new BorderLayout());
        totalProgressPanel.add(new JLabel("total progress"), BorderLayout.NORTH);
        totalProgressPanel.add(totalProgressBar, BorderLayout.CENTER);
        contentPane.add(totalProgressPanel, BorderLayout.NORTH);

        JPanel individualProgressPanel = new JPanel();
        individualProgressPanel.setLayout(new GridLayout(boardSize, 3));

        // put tasks in queue
        for (int firstColumn = 0; firstColumn < boardSize; firstColumn++) {
            QueenTask queenTask = new QueenTask(firstColumn, boardSize);
            queenWork.tasks.put(queenTask);

            JProgressBar indivedualProgressBar = new JProgressBar(0, 100);
            indivedualProgressBar.setValue(0);
            indivedualProgressBar.setStringPainted(true);
            JLabel statistics = new JLabel("column" + firstColumn);
            statistics.setVisible(true);
            String key = "column" + firstColumn;
            progressHashmap.put(key, indivedualProgressBar);

            individualProgressPanel.add(statistics);
            individualProgressPanel.add(new JLabel(""));
            individualProgressPanel.add(indivedualProgressBar);

        }
        contentPane.add(individualProgressPanel, BorderLayout.CENTER);
        theFrame.setSize(400, 400);
        theFrame.setVisible(true);
        // collect all the results
        int i = 0;
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
                totalSolutionCount = totalSolutionCount + result.value;

                String key = "column" + result.firstQueenColumn;
                JProgressBar progressBar = progressHashmap.get(key);
                progressBar.setValue(100);
                contentPane.repaint();
                System.out.println("result:" + i + " worker:"
                    + result.firstQueenColumn + " #solutions=" + result.value);
            }
            else {

                totalProgressBar.setValue(countTotalProgress());

                String key = "column" + result.firstQueenColumn;
                JProgressBar progressBar = progressHashmap.get(key);
                progressBar.setValue(result.value);
                contentPane.repaint();
            }
            
        }
        totalProgressBar.setValue(100);

        System.out.println("total solutions:" + totalSolutionCount);
        
        Thread.sleep(5000);
        
        System.exit(0);

    }

    private int countTotalProgress() {
        
        int totalProgress = 0;
        for (JProgressBar progressBar : progressHashmap.values()) {
            totalProgress += progressBar.getValue();
            
        }
        System.out.println("totalProgress:" +totalProgress);
        return totalProgress / boardSize;
    }
}
