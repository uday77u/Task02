package baseTest;

import org.apache.commons.lang3.RandomStringUtils;

public class GeneralScript {

	//-------------------------------Random data generator---------------------------
	public String getRandomNumericString(int length) {
		return RandomStringUtils.randomNumeric(length);
	}
	 
	public String getRandomAlphabetic(int length) {
		return RandomStringUtils.randomAlphabetic(length);
	}
	
	public String getRandomAlphanumeric(int length) {
		return RandomStringUtils.randomAlphanumeric(length);
	}
	 public String getRandomEmail() {
	        return getRandomAlphabetic(6) + "@gmail.com";
	 }

	    public String getRandomPassword() {
	        return getRandomAlphanumeric(8);
	 }
	
}
