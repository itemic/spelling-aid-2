//Assignment 2 Submission - Terran Kroft
//UPI: tkro003

import java.io.File;

public enum FName {
		WORDLIST ("./wordlist"),
		DIRECTORY ("./.statistics"),
		FAILEDWORDS ("./.statistics/failedstats"),
		FAILEDLIST ("./.statistics/failwordlist"),
		FAULTEDWORDS ("./.statistics/faultedstats"),
		FAULTEDLIST ("./.statistics/faultwordlist"),
		MASTEREDWORDS ("./.statistics/masteredstats");
		private final String location;
		FName(String location) {
			this.location = location;
		}
		
		File file() {
			return new File(location);
		}
	}