//Assignment 2 Submission - Terran Kroft
//UPI: tkro003

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class StatsHandler {
	StringBuilder sb = new StringBuilder();
	
	public int getWordFrequency(String word, File file) {
		//get the number of times the word has been spelled right/wrong
		try {
			@SuppressWarnings("resource")
			BufferedReader r = new BufferedReader(new FileReader(file));
			
			String line;
			while ((line = r.readLine()) != null) {
				if(line.trim().split(" ")[0].equals(word)) {
					return Integer.parseInt(line.trim().split(" ")[1]);
				}
			}
			r.close();
		} catch (IOException e) {
			e.printStackTrace(); //change this later
		}
		return 0; //it has not appeared!
	}
	
	public String generateStats(File wordlist) {
		BufferedReader br = null;
		ArrayList<String> allWords = new ArrayList<>();
		String w = "";
		try {
			br = new BufferedReader(new FileReader(wordlist));
			
			while ((w = br.readLine()) != null) {
				int mastered = getWordFrequency(w, FName.MASTEREDWORDS.file());
				int faulted = getWordFrequency(w, FName.FAULTEDWORDS.file());
				int failed = getWordFrequency(w, FName.FAILEDWORDS.file());
				
				if (mastered == 0 && faulted == 0 && failed == 0) {
					//don't add un-tested words
				} else {
					allWords.add(w + ":\t" + mastered + "/" + faulted + "/" + failed + System.lineSeparator());
				}
			}
			Collections.sort(allWords, new Comparator<Object>() {
				//sort the words alphabetically IGNORING case
				@Override
				public int compare(Object o1, Object o2) {
					String s1 = (String)o1;
					String s2 = (String)o2;
					return (s1.toLowerCase().compareTo(s2.toLowerCase()));
				}
				
			});

			for (String str: allWords) {
				sb.append(str);
			}
			w = sb.toString();
			
			br.close();
		} catch (IOException e) {
			e.getStackTrace();
		}
		
		return w;
	}
}
