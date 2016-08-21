//Assignment 2 Submission - Terran Kroft
//UPI: tkro003

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ClearPanel extends JPanel{
	private JPanel panel = new JPanel(new BorderLayout());
	private JLabel display = new JLabel("Are you sure you want to clear all statistics?");
	private JLabel warning = new JLabel("All data except for the word-list will be removed.");
	private JLabel allClear = new JLabel("Your stats have been cleared!");
	private JButton confirmBtn = new JButton("Clear all statistics");
	
	public ClearPanel() {
		panel.add(display, BorderLayout.NORTH);
		display.setForeground(Color.RED);
		panel.add(warning, BorderLayout.CENTER);
		panel.add(confirmBtn, BorderLayout.SOUTH);
		confirmBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//remove the warnings
				panel.remove(display);
				panel.remove(warning);
				panel.remove(confirmBtn);
				FileHandler fh = new FileHandler();
				//clear contents of all the files
				fh.clearContents(FName.FAILEDLIST.file());
				fh.clearContents(FName.FAILEDWORDS.file());
				fh.clearContents(FName.FAULTEDWORDS.file());
				fh.clearContents(FName.FAULTEDLIST.file());
				fh.clearContents(FName.MASTEREDWORDS.file());
				//let user know that everything has been cleared
				panel.add(allClear);
				revalidate();
				repaint();
				
			}
			
		});
		add(panel);
	}
}
