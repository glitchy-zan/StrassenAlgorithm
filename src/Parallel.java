public class Parallel {
    private Matrix a;
    private Matrix b;
    private int size;

    HelpFunctions hf = new HelpFunctions();

    Parallel(Matrix a, Matrix b) {
        this.a = a;
        this.b = b;
        this.size = a.getSize();
    }

    public Matrix algorithm() throws InterruptedException {

        Matrix a11 = hf.getSplitedMatrix(a, 1);
        Matrix a12 = hf.getSplitedMatrix(a, 2);
        Matrix a21 = hf.getSplitedMatrix(a, 3);
        Matrix a22 = hf.getSplitedMatrix(a, 4);
        Matrix b11 = hf.getSplitedMatrix(b, 1);
        Matrix b12 = hf.getSplitedMatrix(b, 2);
        Matrix b21 = hf.getSplitedMatrix(b, 3);
        Matrix b22 = hf.getSplitedMatrix(b, 4);

        // initializing new matrices m1 - m7
        final Matrix[] m1 = {new Matrix(size / 2)};
        final Matrix[] m2 = {new Matrix(size / 2)};
        final Matrix[] m3 = {new Matrix(size / 2)};
        final Matrix[] m4 = {new Matrix(size / 2)};
        final Matrix[] m5 = {new Matrix(size / 2)};
        final Matrix[] m6 = {new Matrix(size / 2)};
        final Matrix[] m7 = {new Matrix(size / 2)};

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                m1[0] = hf.multiplyMatrix(hf.addMatrix(a11, a22), hf.addMatrix(b11, b22));
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                m2[0] = hf.multiplyMatrix(hf.addMatrix(a21, a22), b11);
            }
        });

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                m3[0] = hf.multiplyMatrix(a11, hf.substractMatrix(b12, b22));
            }
        });

        Thread t4 = new Thread(new Runnable() {
            @Override
            public void run() {
                m4[0] = hf.multiplyMatrix(a22, hf.substractMatrix(b21, b11));
            }
        });

        Thread t5 = new Thread(new Runnable() {
            @Override
            public void run() {
                m5[0] = hf.multiplyMatrix(hf.addMatrix(a11, a12), b22);
            }
        });

        Thread t6 = new Thread(new Runnable() {
            @Override
            public void run() {
                m6[0] = hf.multiplyMatrix(hf.substractMatrix(a21, a11), hf.addMatrix(b11, b12));
            }
        });

        Thread t7 = new Thread(new Runnable() {
            @Override
            public void run() {
                m7[0] = hf.multiplyMatrix(hf.substractMatrix(a12, a22), hf.addMatrix(b21, b22));
            }
        });

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
        t7.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();
        t5.join();
        t6.join();
        t7.join();

        // initializing result matrices
        final Matrix[] c11 = {new Matrix(size / 2)};
        final Matrix[] c12 = {new Matrix(size / 2)};
        final Matrix[] c21 = {new Matrix(size / 2)};
        final Matrix[] c22 = {new Matrix(size / 2)};

        Thread t11 = new Thread(new Runnable() {
            @Override
            public void run() {
                c11[0] = hf.addMatrix(hf.substractMatrix(hf.addMatrix(m1[0], m4[0]), m5[0]), m7[0]);
            }
        });

        Thread t12 = new Thread(new Runnable() {
            @Override
            public void run() {
                c12[0] = hf.addMatrix(m3[0], m5[0]);
            }
        });

        Thread t13 = new Thread(new Runnable() {
            @Override
            public void run() {
                c21[0] = hf.addMatrix(m2[0], m4[0]);
            }
        });

        Thread t14 = new Thread(new Runnable() {
            @Override
            public void run() {
                c22[0] = hf.addMatrix(hf.addMatrix(hf.substractMatrix(m1[0], m2[0]), m3[0]), m6[0]);
            }
        });

        t11.start();
        t12.start();
        t13.start();
        t14.start();

        t11.join();
        t12.join();
        t13.join();
        t14.join();

        // end matrix
        Matrix c = hf.getJoinedMatrix(c11[0], c12[0], c21[0], c22[0]);

        return c;
    }

    public Matrix getA() {
        return a;
    }

    public Matrix getB() {
        return b;
    }

    public int getSize() {
        return size;
    }
}
