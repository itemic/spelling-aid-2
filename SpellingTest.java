//Assignment 2 Submission - Terran Kroft
//UPI: tkro003

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class SpellingTest {
	private int wordCount; //default to 3
	private ArrayList<String> allWords = new ArrayList<>();
	private int currentWordIndex = 0;
	private boolean isNewWord = true;
	private boolean isCorrect = false;
	private boolean isFail = false;
//	private FileHandler fh = new FileHandler();

	
	public SpellingTest(File wordlist, int words) {
		wordCount = words;
		FileHandler fh = new FileHandler();
		allWords = fh.readWords(wordlist);
	}
	
	public void spellingTest(String userInput, ArrayList<String> words, int gameType) {
		//gameType is currently just a static int
		//1 denotes regular quiz/play, 2 denotes review game
		//later on it'll be changed into using enums
		
	
		
		isCorrect = isSpelledCorrectly(userInput, currentWordIndex);
		FileHandler fh = new FileHandler();
		String currentWord = words.get(currentWordIndex);

		if (isNewWord) {
			if (isCorrect) {
				//MASTERED
				Festival.speak("Correct!");
				isNewWord = true;
				isFail = false;
				fh.updateStatsList(currentWord, FName.MASTEREDWORDS.file());	
				if (gameType == 2) {
					fh.removeFromWordlist(currentWord, FName.FAILEDLIST.file());
				}
				currentWordIndex++;


			} else {
				//FAULTING
				Festival.speak("Incorrect! Try again!");
				sayWordFault(currentWord);
				isNewWord = false;
				isFail = false;
				
			}
		} else if (isFail) {
			//Festival is spelling this out so we won't change any statistics in this part
			//Also, this part won't be visited if we are in normal play mode.
			
			if (isCorrect) {
				Festival.speak("You got it right this time!");
				isFail = false;
				isNewWord = true;
				currentWordIndex++;
				
			} else {
				Festival.speak("Still incorrect. Better luck next time!");
				isFail = false;
				isNewWord = true;
				currentWordIndex++;
			}
			
		} else {
			if (isCorrect) {
				//FAULTED
				Festival.speak("Correct!");
				isNewWord = true;
				isFail = false;
				fh.updateStatsList(currentWord, FName.FAULTEDWORDS.file());
				fh.addToWordlist(currentWord, FName.FAULTEDLIST.file());
				if (gameType == 2) {
					fh.removeFromWordlist(currentWord, FName.FAILEDLIST.file());
				}
				currentWordIndex++;

			} else {
				//FAILED
				Festival.speak("Incorrect!");
				fh.updateStatsList(currentWord, FName.FAILEDWORDS.file());
				fh.addToWordlist(currentWord, FName.FAILEDLIST.file());

				if (gameType == 1) {
					isNewWord = true;
					currentWordIndex++;
				}
				else if (gameType == 2) {
					isNewWord = false;
					isFail = true;
					Festival.speak("This is how you spell " + currentWord);
					sayWordSpelling(words);
					Festival.speak("Please spell " + currentWord + " again!");
				}			
			}
		}
		if (isNewWord && currentWordIndex < wordCount) {
			currentWord = words.get(currentWordIndex); //update it because we've updated the current word
			Festival.speak("Spell " + currentWord);
		}
	}
	
	

	
	public boolean isExceed() {
		return currentWordIndex >= wordCount;
	}
	
	
	public String updateString() {
		String s = "Spelling word " + (currentWordIndex + 1) + " of " + wordCount + ".";
		if (isExceed()) {
			s = "Spelling test complete!";
		}
		return s;
	}
	
	public boolean isSpelledCorrectly(String userInput, int position) {
		return (userInput.equals(allWords.get(position)));
	}

	
	public void sayWordStart(String word) {
		Festival.speak("Spell " + word);
	}
	
	public void sayWordFault(String word) {
		//if word spelled wrong first time we say it twice.
		Festival.speak("Spell " + word + "... ... " + word);
	}
	
	public void sayWordSpelling(ArrayList<String> words) {
		String word = words.get(currentWordIndex);
		for (int i = 0; i < word.length(); i++) {
			Festival.speak(String.valueOf(word.charAt(i)));
		}
	}

	public boolean isEmptyList() {
		return (allWords == null || allWords.isEmpty());
	}
	
	public ArrayList<String> randomizedWords() {
		ArrayList<String> randomWords = new ArrayList<String>();

			Collections.shuffle(allWords);
			if (allWords.size() < wordCount) {
				//fewer words than we have :)
					randomWords.addAll(allWords);
					wordCount = allWords.size();
				
				
			} else {
				for (int i = 0; i < wordCount; i++) {
					randomWords.add(allWords.get(i));
				}
			}
		
		
		return randomWords;	
	}
	
	public int getWordCount() {
		return wordCount;
	}

}
