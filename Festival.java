//Assignment 2 Submission - Terran Kroft
//UPI: tkro003

import java.io.IOException;

public class Festival {
	private static Festival instance = null;
	private Festival() {
		
	}
	
	public static Festival getInstance() {
		if (instance == null) {
			instance = new Festival();
		}
		return instance;
	}
	
	protected static void speak(String speak) {
		ProcessBuilder pb = null;
		//Make it work on Macs as well using the "say" command
		//Because my main working computer is a Mac.
		String os = System.getProperty("os.name");
		String cmd = null;
		if (os.equals("Mac OS X")) {
			//Mac uses the say command
			cmd = "say " + speak;
		} else {
			//Linux uses festial. This doesn't work on Windows just yet.
			cmd = "echo " + speak + " | festival --tts";
		}
		pb = new ProcessBuilder("/bin/bash", "-c", cmd);
		try {
			Process p = pb.start();
			//wait until it's done saying.
			p.waitFor();
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
