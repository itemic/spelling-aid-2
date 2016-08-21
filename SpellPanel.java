//Assignment 2 Submission - Terran Kroft
//UPI: tkro003

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class SpellPanel extends JPanel{
	
	private JTextField textEntry = new JTextField(25);
	private JLabel display = new JLabel("");
	private JButton confirmBtn = new JButton("Check!");
	private JButton startBtn = new JButton("Start");
	private int wordCount = 3;
	private int wordCounter = 0;
	private SpellingTest test;
	private ArrayList<String> wordList;
	private JPanel panel;
	private String spellingType;
	
	private static final int PLAYGAME = 1;
	private static final int REVIEWGAME = 2;
	
	public SpellPanel(String type) {
		spellingType = type;
		if (type.equals("play")) {
			test = new SpellingTest(FName.WORDLIST.file(), wordCount);
		
		} else if (type.equals("review")) {
			test = new SpellingTest(FName.FAILEDLIST.file(), wordCount);		
		}
		panel = new JPanel(new BorderLayout());
		panel.add(display, BorderLayout.NORTH);
		
		if (test.isEmptyList()) {
			//If there are no words don't start a game!
			if (spellingType.equals("play")) {
				display.setText("No words available. Add some and come back!");
			} else if (spellingType.equals("review")) {
				display.setText("No words to review!");
			}
		} else {
			wordList = test.randomizedWords(); //update our wordlist
			wordCount = test.getWordCount(); //update wordcount if we have fewer words
			if (spellingType.equals("play")) {
				display.setText("Press start to play!");
			} else if (spellingType.equals("review")) {
				display.setText("Press start to review failed words.");
			}
			panel.add(startBtn, BorderLayout.CENTER);
			
			startBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//the "start" button has been pressed
					//remove the start button and replace it with the text entry components
					panel.remove(startBtn);
					panel.add(textEntry, BorderLayout.CENTER);
					panel.add(confirmBtn, BorderLayout.SOUTH);
					panel.revalidate();
					panel.repaint();
					//start the test by saying the first word
					display.setText("Spelling word " + 1 + " of " + wordCount + ".");
					test.sayWordStart(wordList.get(wordCounter));
				}
			});
			
			
			confirmBtn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
						String attempt = textEntry.getText();
						
						//Start spelling game based on test type.
						if (spellingType.equals("play")) {
							test.spellingTest(attempt, wordList, PLAYGAME);	
						} else if (spellingType.equals("review")) {
							test.spellingTest(attempt, wordList, REVIEWGAME);
						}
						
						display.setText(test.updateString());
						textEntry.setText("");
						if (test.isExceed()) {
							//done with spelling test so remove components
							panel.remove(confirmBtn);
							panel.remove(textEntry);
						}
						panel.revalidate();
						panel.repaint();	
				}
				
			});
		

		
		
		}			
		add(panel);
	}
	
}
