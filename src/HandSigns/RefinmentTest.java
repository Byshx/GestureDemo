package HandSigns;

public class RefinmentTest {
	int[][]vector = 
		{{1,1,1,1,1,1,1,1,1,1},
		 {1,1,1,1,1,1,1,1,1,1},
		 {1,1,1,1,1,1,1,1,1,1}, 
		 {1,1,1,0,0,0,0,1,1,1}, 
		 {1,1,1,0,0,0,0,1,1,1}, 
		 {1,1,1,0,0,0,0,1,1,1},
		 {1,1,1,0,0,0,0,1,1,1},
		 {1,1,1,1,1,1,1,1,1,1},
		 {1,1,1,1,1,1,1,1,1,1},
		 {1,1,1,1,1,1,1,1,1,1}};
	public RefinmentTest() {
		// TODO Auto-generated constructor stub
		int[][]v = new Refinment().Calculate(vector).clone();
		for(int i=0;i<10;i++){
			for(int j=0;j<10;j++){
				System.out.print(v[i][j]);
			}
			System.out.println();
		}
	}
	public static void main(String[] args) {
		new RefinmentTest();
	}
}
