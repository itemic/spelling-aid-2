//Assignment 2 Submission - Terran Kroft
//UPI: tkro003

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class StatsPanel extends JPanel{
	
	private JPanel panel = new JPanel(new BorderLayout());
	private JLabel display = new JLabel("word: mastered/faulted/failed");
	private JTextArea textArea = new JTextArea(20, 20);
	private JScrollPane scroll = new JScrollPane(textArea);

	
	public StatsPanel() {
		panel.add(display, BorderLayout.NORTH);
		panel.add(scroll, BorderLayout.CENTER);
		textArea.setEditable(false);
		textArea.setOpaque(false);
		textArea.setLineWrap(true);
		StatsHandler sf = new StatsHandler();
		String stats = sf.generateStats(FName.WORDLIST.file());
		if (stats.isEmpty()) {
			stats = "No stats available.\nPlay a few games and come back!";
		}
		textArea.setText(stats);
			
		add(panel);
	}
}
