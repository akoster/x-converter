package xcon.example.webcrawler;

/**
 * Manager interface for Workers
 * 
 * @author Adriaan
 */
public interface Manager {

	/**
	 * Gets a new job
	 * 
	 * @return
	 */
	public Job getNewJob();

	/**
	 * Indicates the job is completed
	 * 
	 * @param job
	 */
	public void jobCompleted(Job job);
}
