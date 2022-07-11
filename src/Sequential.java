public class Sequential {
    private Matrix a;
    private Matrix b;
    private int size;

    HelpFunctions hf = new HelpFunctions();

    Sequential(Matrix a, Matrix b) {
        this.a = a;
        this.b = b;
        this.size = a.getSize();
    }

    public Matrix algorithm() {
        Matrix a11 = hf.getSplitedMatrix(a, 1);
        Matrix a12 = hf.getSplitedMatrix(a, 2);
        Matrix a21 = hf.getSplitedMatrix(a, 3);
        Matrix a22 = hf.getSplitedMatrix(a, 4);
        Matrix b11 = hf.getSplitedMatrix(b, 1);
        Matrix b12 = hf.getSplitedMatrix(b, 2);
        Matrix b21 = hf.getSplitedMatrix(b, 3);
        Matrix b22 = hf.getSplitedMatrix(b, 4);

        // initializing new matrices m1 - m7
        Matrix m1 = new Matrix(size / 2);
        Matrix m2 = new Matrix(size / 2);
        Matrix m3 = new Matrix(size / 2);
        Matrix m4 = new Matrix(size / 2);
        Matrix m5 = new Matrix(size / 2);
        Matrix m6 = new Matrix(size / 2);
        Matrix m7 = new Matrix(size / 2);

        // filling new matrices m1 - m7
        m1 = hf.multiplyMatrix(hf.addMatrix(a11, a22), hf.addMatrix(b11, b22));
        m2 = hf.multiplyMatrix(hf.addMatrix(a21, a22), b11);
        m3 = hf.multiplyMatrix(a11, hf.substractMatrix(b12, b22));
        m4 = hf.multiplyMatrix(a22, hf.substractMatrix(b21, b11));
        m5 = hf.multiplyMatrix(hf.addMatrix(a11, a12), b22);
        m6 = hf.multiplyMatrix(hf.substractMatrix(a21, a11), hf.addMatrix(b11, b12));
        m7 = hf.multiplyMatrix(hf.substractMatrix(a12, a22), hf.addMatrix(b21, b22));

        // initializing result matrices
        Matrix c11 = new Matrix(size / 2);
        Matrix c12 = new Matrix(size / 2);
        Matrix c21 = new Matrix(size / 2);
        Matrix c22 = new Matrix(size / 2);

        // filling result matrices
        c11 = hf.addMatrix(hf.substractMatrix(hf.addMatrix(m1, m4), m5), m7);
        c12 = hf.addMatrix(m3, m5);
        c21 = hf.addMatrix(m2, m4);
        c22 = hf.addMatrix(hf.addMatrix(hf.substractMatrix(m1, m2), m3), m6);

        // end matrix
        Matrix c = hf.getJoinedMatrix(c11, c12, c21, c22);

        return c;
    }

    public Matrix getA() {
        return a;
    }

    public Matrix getB() {
        return b;
    }
}
