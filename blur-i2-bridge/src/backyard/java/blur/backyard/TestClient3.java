package blur.backyard;

import blur.i2.ArProxy;

public class TestClient3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TestClient3 tc = new TestClient3();
		tc.run();
	}

	public void run() {
		ArProxy arc = new ArProxy();
		arc.createPerson("3", "3", "", "");
		arc.createPerson("4", "4", "", "");
		arc.createAssociate("L1", "Friend", "3", "4");
		arc.execute();
	}
}
