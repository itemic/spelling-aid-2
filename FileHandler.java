//Assignment 2 Submission - Terran Kroft
//UPI: tkro003

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileHandler {
	//The logic behind some methods in this class have been sourced from
	//http://stackoverflow.com/questions/1377279/find-a-line-in-a-file-and-remove-it
	public void validateWordlist() {
		//Checks if the wordlist exists at the required location
		//UNUSED right now because Assignment 2 assumes it is available
		File f = FName.WORDLIST.file();
		if (f.exists()) {
			System.out.println("Wordlist exists!");
		} else {
			System.out.println("Wordlist does not exist.");
		}
	}
	
	public void validateStats() {
		File dir = FName.DIRECTORY.file();
		ArrayList<File> allfiles = new ArrayList<>();
		allfiles.add(FName.FAILEDWORDS.file());
		allfiles.add(FName.FAILEDLIST.file());
		allfiles.add(FName.FAULTEDWORDS.file());
		allfiles.add(FName.FAULTEDLIST.file());
		allfiles.add(FName.MASTEREDWORDS.file());
		
		//first check if the statistics/settings directory exists
		if (dir.exists()) {
			// yay we should go in and check if we have all the necessary files!
			for (File f: allfiles) {
				if (f.exists()) {
					//it exists so don't do anything
				} else {
					try {
						//it doesn't exist so make the file
						f.createNewFile(); 
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			// oh no we don't have the directory so we want to first make the directory
			dir.mkdir();
			for (File f: allfiles) {
				try {
					f.createNewFile(); //don't bother checking if it exists because if dir didn't exist so won't the files
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	
	}
	
	public void removeFromWordlist(String word, File file) {
		
		
		File temp = new File("temp");
		try {
			BufferedReader r = new BufferedReader(new FileReader(file));
			BufferedWriter w = new BufferedWriter(new FileWriter(temp));
			
			String line;
			while ((line = r.readLine()) != null) {
				if(line.trim().equals(word)) {
					//if it matches the word, don't write it (basically like a delete)
					continue; //it's like break but it doesn't stop the loop
				}
				//otherwise write the word
				w.write(line + System.lineSeparator());
			}
			r.close();
			w.close();
		} catch (IOException e) {
			e.printStackTrace(); //change this later
		}
		temp.renameTo(file);
	}
	
	public void clearContents(File file) {
		//erase everything by just overwriting it and closing it before adding text
		try {
			BufferedWriter pw = new BufferedWriter(new FileWriter(file));
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void addToWordlist(String word, File file) {
		
		File temp = new File("temp"); //do everything in a new file
		try {
			BufferedReader r = new BufferedReader(new FileReader(file));
			BufferedWriter w = new BufferedWriter(new FileWriter(temp));
			boolean isPresent = false;
			String line;
			while ((line = r.readLine()) != null) {
				if(line.trim().equals(word)) {
					w.write(line + System.lineSeparator());
					isPresent = true; //we have seen the word
				} else {
					w.write(line + System.lineSeparator());
				}
			}
			
			if (!isPresent) { //the word wasn't in the file at first, so add it now
				w.write(word + System.lineSeparator());
			}
			r.close();
			w.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		temp.renameTo(file); //overwrite the original file with the new file by renaming it
	} 
	
	public void updateStatsList(String word, File file) {
		
		File temp = new File("temp");
		boolean isPresent = false;
		try {
			BufferedReader r = new BufferedReader(new FileReader(file));
			BufferedWriter w = new BufferedWriter(new FileWriter(temp));
			
			String line;
			
			while ((line = r.readLine()) != null) {
				String firstWord = line.trim().split(" ")[0];
				if(firstWord.equals(word)) {
					//increment the count stored in the file by 1
					int count = Integer.parseInt(line.trim().split(" ")[1]);
					count++;
					w.write(firstWord + " " + count + System.lineSeparator());
					isPresent = true;
				} else {
					w.write(line + System.lineSeparator());					
				}

			}
			if (!isPresent) { //word wasn't in the file, so its first appearance is a 1
				w.write(word + " " + 1 + System.lineSeparator());
			}
			
			r.close();
			w.close();
		} catch (IOException e) {
			e.printStackTrace(); //change this later
		}
		temp.renameTo(file);
	}
	
	public ArrayList<String> readWords(File wordList) {
		//method for spelling test, but adds all the words in a file to an arraylist
		ArrayList<String> allWords = new ArrayList<>();
		BufferedReader br = null;
		String w;
		try {
			br = new BufferedReader(new FileReader(wordList));
			while ((w = br.readLine()) != null) {
				allWords.add(w);
			}
			br.close();
		} catch (IOException e) {
			
		}
		return allWords;
	}
	
	public ArrayList<String> readLevel(File wordList, int level) {
		ArrayList<String> levelWords = new ArrayList<>();
		BufferedReader br = null;
		String w;
		try {
			br = new BufferedReader(new FileReader(wordList));
			while ((w = br.readLine()) != null) {
				if (w.equals("%Level " + level)) {
					//we found the specified level!
					break;
				}
			}
			while ((w = br.readLine()) != null) {
				int nextLevel = level + 1;
				if (!w.equals("%Level " + nextLevel)) {
					//it's not the next level comment
					levelWords.add(w);
					System.out.println("word: " + w);
				} else {
					break;
				}
			}
		} catch (IOException e) {
			
		}
		return levelWords;
	}
	
}
