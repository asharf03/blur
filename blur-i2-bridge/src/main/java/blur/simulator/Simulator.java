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
		s.doInit();
	}

	public void run() {
		String cmd = "";
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		while (!cmd.equals("quit")) {
			System.out.println("Blur Simulator: reset, init, start, quit");
			try {
				cmd = stdin.readLine();
				switch (cmd) {
				case "reset":
					doReset();
					break;

				case "init":
					doInit();
					break;

				case "start":
					doStart();
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

	private void doStart() {
		ScenarioCommand cmd = new ScenarioCommand();
		cmd.setScenario("scenario1.scn");
		cmd.setAccelerationRatio(1);
		cmd.execute();
	}
}
