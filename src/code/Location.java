package code;

public class Location {
	
	String codename,person;//codename and people type for location
	boolean revealed;//whether location has been revealed or not 
	
	/*
	 * Location constructor, gives location a codename,person, and sets it to be not revealed
	 * @param string, string
	 * @return None
	 */
	public Location (String code, String pers) {
		codename = code;
		person = pers;
		revealed = false;
	}
	
	/*
	 * Return the codename of the Location object
	 * @param None
	 * @return the codename of this Location
	 */
	public String getCodename() {
		return codename;
	}
	
	/*
	 * Gets the person string
	 * @param None
	 * @return The String that is stored in person
	 */
	public String getPerson() {
		return person;
	}
	
	/*
	 * Get whether location has been revealed or not
	 * @param None
	 * @return The boolean that is stored in revealed
	 */
	public boolean getRevealed() {
		return revealed;
	}
	
	/*
	 * Reveals location 
	 * @param None
	 * @return void
	 */
	public void setRevealedTrue() {
		revealed = true;
	}

}
