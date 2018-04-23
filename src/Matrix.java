import java.util.Random;

public class Matrix {

	Random rand = new Random();
	
	private int size;
	public int m[][];
	
	//constructor initialize matrix size n
	public Matrix(int n) {
		size = n;
		m = new int[size][size];
	}
	
	//overloading constructor
	public Matrix(Matrix A, int top, int bottom, int left, int right) {
		size = A.size / 2;	//size decreases by half
		m = new int[size][size];
		
		//copy to smaller matrix based on new size
		for(int i = 0, row = top; row < bottom; i++, row++) {
			for(int j = 0, col = left; col < right; j++, col++) {
				m[i][j] = A.m[row][col];
			}
		}
	}
	
	//Classic method
	public Matrix mul(Matrix x) {
		Matrix c = new Matrix(size);	//create new matrix object with same size
		
		//matrix multiply algorithm
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				c.m[i][j] = 0;
				for(int k = 0; k < size; k++) {
					c.m[i][j] = c.m[i][j] + m[i][k] * x.m[k][j];
				}
			}
		}
		return c;	//return new matrix object
	}
	
	//Strassen method
	public Matrix strasMul(Matrix x) {
		Matrix c = new Matrix(size);	//create new matrix object with same size
		Strassen(size, this, x, c);	//call Strassen multiply algorithm	
		return c;	//return new matrix object
	}
	
	//Strassen recursive call
	public void Strassen(int n, Matrix A, Matrix B, Matrix C) {
		//if size = 2 use classic method
		if(n == 2) {
			C.m[0][0] = A.m[0][0]*B.m[0][0] + A.m[0][1]*B.m[1][0];
			C.m[0][1] = A.m[0][0]*B.m[0][1] + A.m[0][1]*B.m[1][1];
			C.m[1][0] = A.m[1][0]*B.m[0][0] + A.m[1][1]*B.m[1][0];
			C.m[1][1] = A.m[1][0]*B.m[0][1] + A.m[1][1]*B.m[1][1];
		}
		else {
			
			//partition A into 4 submatrices
			Matrix A11 = new Matrix(A, 0, n/2, 0, n/2);
			Matrix A12 = new Matrix(A, 0, n/2, n/2, n);
			Matrix A21 = new Matrix(A, n/2, n, 0, n/2);
			Matrix A22 = new Matrix(A, n/2, n, n/2, n);
			
			//partition B into 4 submatrices
			Matrix B11 = new Matrix(B, 0, n/2, 0, n/2);
			Matrix B12 = new Matrix(B, 0, n/2, n/2, n);
			Matrix B21 = new Matrix(B, n/2, n, 0, n/2);
			Matrix B22 = new Matrix(B, n/2, n, n/2, n);
			
			//initialize Strassen matrices
			Matrix P = new Matrix(n/2);
			Matrix Q = new Matrix(n/2);
			Matrix R = new Matrix(n/2);
			Matrix S = new Matrix(n/2);
			Matrix T = new Matrix(n/2);
			Matrix U = new Matrix(n/2);
			Matrix V = new Matrix(n/2);
			
			//calculate each Strassen matrices
			Strassen(n/2, A11.add(A22), B11.add(B22), P);
			Strassen(n/2, A21.add(A22), B11, Q);
			Strassen(n/2, A11, B12.sub(B22), R);
			Strassen(n/2, A22, B21.sub(B11), S);
			Strassen(n/2, A11.add(A12), B22, T);
			Strassen(n/2, A21.sub(A11), B11.add(B12), U);
			Strassen(n/2, A12.sub(A22), B21.add(B22), V);

			//calculate the result submatrices
			Matrix C11 = P.add((S.sub(T)).add(V));
			Matrix C12 = R.add(T);
			Matrix C21 = Q.add(S);
			Matrix C22 = P.add((R.sub(Q)).add(U));
			
			//merge C11-22 back to C
			C.copy(C11, 0, n/2, 0, n/2);
			C.copy(C12, 0, n/2, n/2, n);
			C.copy(C21, n/2, n, 0, n/2);
			C.copy(C22, n/2, n, n/2, n);
		}	
	}
	
	//copy submatrix back to original matrix
	public void copy(Matrix M, int top, int bottom, int left, int right) {
		
		for(int i = 0, row = top; row < bottom; i++, row++) {
			for(int j = 0, col = left; col < right; j++, col++) {
				m[row][col] = M.m[i][j];
			}
		}
	}
	
	//add 2 matrices
	public Matrix add(Matrix x) {
		Matrix c = new Matrix(size);
		
		//every index add to each other
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				c.m[i][j] = this.m[i][j] + x.m[i][j];
			}
		}
		return c;	
	}
	
	//subtract 2 matrices
	public Matrix sub(Matrix x) {
		Matrix c = new Matrix(size);
		
		//every index subtract each other
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				c.m[i][j] = this.m[i][j] - x.m[i][j];
			}
		}
		return c;	
	}
	
	//generate random value for every position
	public void genRand(int n) {
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				m[i][j] = rand.nextInt(10);
			}
		}
	}
	
	//print for testing purposes
	public void print() {
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				System.out.print(m[i][j] + " ");
			}
			System.out.println();
		}	
	}
	
}
