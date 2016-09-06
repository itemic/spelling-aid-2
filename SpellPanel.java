//Assignment 2 Submission - Terran Kroft
//UPI: tkro003

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class SpellPanel extends JPanel{
	
	private JTextField textEntry = new JTextField(25);
	private JLabel display = new JLabel("");
	private JButton confirmBtn = new JButton("Check!");
	private JButton startBtn = new JButton("Start");
	private int wordCount = 10;
	private int wordCounter = 0;
	private SpellingTest test;
	private ArrayList<String> wordList;
	private JPanel panel;
	private JPanel levelPanel;
	private String spellingType;
	private ButtonGroup levelButtons;
	
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
				display.setText("Press start to play! (Deprecated soon...)");
			} else if (spellingType.equals("review")) {
				display.setText("Press start to review failed words.");
			}
			panel.add(startBtn, BorderLayout.SOUTH);
			levelPanel = new JPanel();
			levelButtons = new ButtonGroup();
			for (int i = 0; i < 10; i++) {
				JRadioButton levelButton = new JRadioButton(i+1 + "");
				if (i == 0) {
					levelButton.setSelected(true); //default to level 1
				}
				//this is sourced from 
				// http://stackoverflow.com/questions/201287/how-do-i-get-which-jradiobutton-is-selected-from-a-buttongroup
				levelButton.setActionCommand(i+1+"");
				levelPanel.add(levelButton);
				levelButtons.add(levelButton);
			}
			
			panel.add(levelPanel, BorderLayout.CENTER);
			
			startBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//the "start" button has been pressed
					//remove the start button and replace it with the text entry components
					int selectedLevel = Integer.parseInt(levelButtons.getSelection().getActionCommand());
					System.out.println("level: " + selectedLevel);
					// v TEMPORARY PlACEHOLDER code
					test = new SpellingTest(FName.WORDLIST.file(), wordCount, selectedLevel); 
					wordList = test.randomizedWords(); //update our wordlist
					wordCount = test.getWordCount(); //update wordcount if we have fewer words 
					//Temporary way
					// the ^ code must be changed because it is a duplicate of the Play/Review code.
					//think of a better way for this!
					panel.remove(startBtn);
					panel.remove(levelPanel);
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
