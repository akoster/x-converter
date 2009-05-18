package xcon.queens;

/**
 * Call back handler for SolutionFinders
 * @author loudiyimo
 */
public interface SolutionFinderCallbackHandler {

    /**
     * Called by a SolutionFinder when it has completed its work
     * @param finder
     */
    public void solutionFinderCompleted(SolutionFinder finder);
}
