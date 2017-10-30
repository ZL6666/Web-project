import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestUnit {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("hello world!");
	}

	@Test
	public void TestUnit() {
		System.out.println("hello unit!");
	}

	@Before
	public void testBefore() {
		System.out.println("hello before!");
	}

	@After
	public void testAfter() {
		System.out.println("hello after!");
	}

}
