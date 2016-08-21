//Assignment 2 Submission - Terran Kroft
//UPI: tkro003

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class VOXSpelling extends JFrame implements ActionListener {

	
	private JButton play = new JButton("play");
	private JButton review = new JButton("review");
	private JButton stats = new JButton("stats");
	private JButton clear = new JButton("clear");
	
	private JPanel sidebar = new JPanel(new GridLayout(4,1));
	private JPanel mainBar = new JPanel(new BorderLayout());


	
	public VOXSpelling() {

		super("VOX Spelling");

		setSize(500, 400);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		add(sidebar, BorderLayout.EAST);
		add(mainBar, BorderLayout.CENTER);
		sidebar.add(play);
		sidebar.add(review);
		sidebar.add(stats);
		sidebar.add(clear);

		
		play.addActionListener(this);
		review.addActionListener(this);
		stats.addActionListener(this);
		clear.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		String btnType = ((JButton)e.getSource()).getText();

		switch (btnType) {
		case "play":
			mainBar.removeAll();
			mainBar.add(new SpellPanel("play"));
			break;
		case "review":
			mainBar.removeAll();
			mainBar.add(new SpellPanel("review"));
			break;
		case "stats":
			mainBar.removeAll();
			mainBar.add(new StatsPanel());
			break;
		case "clear":
			mainBar.removeAll();
			mainBar.add(new ClearPanel());
			break;
		default:
			break;
		}
		mainBar.revalidate();
		mainBar.repaint();
		
	}
	
	public static void main(String[] agrs){
		
		FileHandler fh = new FileHandler();
		fh.validateStats();
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				VOXSpelling frame = new VOXSpelling();
				frame.setVisible(true);
			}
		});
	}
}