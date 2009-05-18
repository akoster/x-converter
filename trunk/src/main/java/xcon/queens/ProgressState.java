package xcon.queens;

import java.awt.BorderLayout;
import java.awt.Container;
import java.util.Iterator;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import org.apache.log4j.Logger;

public class ProgressState implements Runnable {

    private static final Logger LOG = Logger.getLogger(ProgressState.class);

    private List<SolutionFinder> solutionFinderList;
    private JFrame theFrame;
    private Container contentPane;
    private JLabel statistics;
    private JProgressBar progressBar;

    public ProgressState(List<SolutionFinder> solutionFinders, int boardSize) {
        
        this.solutionFinderList = solutionFinders;
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        statistics = new JLabel();
        statistics.setVisible(true);
        theFrame = new JFrame();
        theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane = theFrame.getContentPane();
        contentPane.add(progressBar, BorderLayout.NORTH);
        contentPane.add(statistics, BorderLayout.CENTER);
        theFrame.setSize(400, 400);
        theFrame.setVisible(true);
    }

    @Override
    public void run() {

        StringBuilder text = new StringBuilder("<html>");
        int progressTotal = 0;
        for (SolutionFinder solutionFinder : solutionFinderList) {
            
            int progress = solutionFinder.getProgress();
            progressTotal += progress;
            text.append(solutionFinder.getQueenBoard().getColumn(0) + ": "
                + progress + " " + solutionFinder.getState() + "<br>");
        }
        int completedAverage = progressTotal / solutionFinderList.size();
        progressBar.setValue(completedAverage);
        statistics.setText(text.toString());
    }

}
