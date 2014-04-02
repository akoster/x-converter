package xcon.project.queens.terracotta;

import java.util.concurrent.LinkedBlockingQueue;

public class QueenWork {

    public LinkedBlockingQueue<QueenTask> tasks =
        new LinkedBlockingQueue<QueenTask>();

    public LinkedBlockingQueue<QueenResult> results =
        new LinkedBlockingQueue<QueenResult>();
}
