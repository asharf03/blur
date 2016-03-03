package blur.simulator;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.commons.csv.CSVRecord;

import blur.i2.ArProxy;
import blur.simulator.cmd.InitCommand;
import blur.simulator.cmd.ScenarioCommand;

/**
 * Hello world!
 *
 */
public class Simulator {
	private static String SCENARIO = "scenario/scenario1.scn";
	private static double ACCELERATION = 100.0;

	public static void main(String[] args) {
		Simulator s = new Simulator();
		// s.run();
		s.doSimulate();
	}

	public void run() {
		String cmd = "";
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		while (!cmd.equals("q") && !cmd.equals("quit")) {
			System.out.println("Blur Simulator: reset, init, simulate, quit");
			try {
				cmd = stdin.readLine();
				switch (cmd) {
				case "r":
				case "reset":
					doReset();
					break;

				case "i":	
				case "init":
					doInit();
					break;

				case "s":	
				case "simulate":
					doSimulate();
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
		}

		System.out.println("Done!");
	}

	private void doReset() {

	}

	private void doInit() {
		InitCommand cmd = new InitCommand();
		cmd.execute();
	}

	private void doSimulate() {
		ScenarioCommand cmd = new ScenarioCommand();
		cmd.setDebug(true);
		cmd.setScenario("/blur/resource/scenario/scenario1.scn");
		cmd.setAccelerationRatio(1);
		cmd.execute();
	}
}
