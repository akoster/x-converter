package xcon.example.webcrawler;

import java.util.HashSet;
import java.util.Set;

/**
 * A Job is a unit of work defined by a String (the description). During
 * execution the job can obtain results and new job descriptions.
 * 
 * @author Adriaan
 */
public abstract class Job {

	private String description;
	private Set<String> results = new HashSet<String>();
	private Set<String> newDescriptions = new HashSet<String>();

	/**
	 * Sets the job description
	 * 
	 * @param description
	 * @return this for chaining
	 */
	public Job setDescription(String description) {
		this.description = description;
		return this;
	}

	/**
	 * Executes the job
	 */
	public abstract void execute();

	/**
	 * Gets the results obtained
	 * 
	 * @return
	 */
	public Set<String> getResults() {
		return results;
	}

	/**
	 * Gets the now job descriptions obtained
	 * 
	 * @return
	 */
	public Set<String> getNewDescriptions() {
		return newDescriptions;
	}

	/**
	 * Gets the job description
	 * 
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Allows the implementation to add an obtained result
	 * 
	 * @param result
	 */
	void addResult(String result) {
		results.add(result);
	}

	/**
	 * Allows the implementation to add an obtained description
	 * 
	 * @param result
	 */
	void addNewDescription(String newDescription) {
		newDescriptions.add(newDescription);
	}
}
