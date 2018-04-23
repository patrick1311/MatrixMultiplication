
public class MatrixTest {
	
	
	public static void main(String args[]) {
		
		/*Demonstrating both the Classic and Strassen method
		* using small matrix size, in this case size = 4
		*/
		int n = 4;
		Matrix m1 = new Matrix(n);
		Matrix m2 = new Matrix(n);
		Matrix m3;
		Matrix m4;
		
		m1.genRand(n);	//generate random matrix values
		m2.genRand(n);
		m3 = m1.mul(m2);	//classic mult
		m4 = m1.strasMul(m2);	//Strassen mult
		
		System.out.println("Matrix A: ");
		m1.print();
		System.out.println("\nMatrix B: ");
		m2.print();
		System.out.println("\nMatrix C using Classic method: ");
		m3.print();
		System.out.println("\nMatrix C using Strassen method: ");
		m4.print();
		System.out.println();
		
		/*generate large size for analysis purposes
		* using size of power of 2 to calculate
		* the run time for each method
		*/
		long t1 = 0, t2 = 0;	//variable store run time
		int power = 9;		//test up to size of 2^power
		
		//set up table format
		System.out.format("%-8s | %-10s | %-10s", "size",
						  "Classic(ms)", "Strassen(ms)");
		System.out.println();
		System.out.println("-------------------------------------");
		
		//calculate run time for different matrix sizes
		for(int i = 1; i <= power; i++) {
			n = (int) Math.pow(2, i);
			m1 = new Matrix(n);
			m2 = new Matrix(n);
			m1.genRand(n);		
			m2.genRand(n);
			
			//classic method run time
			t1 = System.currentTimeMillis();
			m3 = m1.mul(m2);
			t1 = System.currentTimeMillis() - t1;
			
			//Strassen method run time
			t2 = System.currentTimeMillis();
			m4 = m1.strasMul(m2);
			t2 = System.currentTimeMillis() - t2;

			System.out.format("%-8d | %-12d | %-12d ", n, t1, t2);
			System.out.println();
			
		}
	}	
}
