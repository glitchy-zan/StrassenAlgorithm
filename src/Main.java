import mpi.MPI;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        /*

        // only for sequential and parallel

        HelpFunctions hf = new HelpFunctions();

        // getting starting matrices
        System.out.println("Enter size for matrices: ");
        int size = new Scanner(System.in).nextInt();

        Matrix a = new Matrix(size);
        a.fill_randomly();
        Matrix b = new Matrix(size);
        b.fill_randomly();

        hf.printMatrix(a);
        hf.printMatrix(b);

        Sequential seq = new Sequential(a, b);
        Parallel par = new Parallel(a, b);

        */

        /* // SEQUENTIAL

        // Algorithm start

        long str = System.currentTimeMillis();

        Matrix c_seq = seq.algorithm();

        long end = System.currentTimeMillis();

        // Algorithm end

        hf.printMatrix(c_seq);
        long time = end - str;
        System.out.println("Time needed: " + time + " ms");

        */

        /* // PARALLEL

        // Algorithm start

        long str = System.currentTimeMillis();

        Matrix c_par = par.algorithm();

        long end = System.currentTimeMillis();

        // Algorithm end

        hf.printMatrix(c_par);
        long time = end - str;
        System.out.println("Time needed: " + time + " ms");


         */


        // DISTRIBUTED
        // run configurations ---> VM options: -jar $MPJ_HOME$\lib\starter.jar -np 4
        //                         environmental variables: MPJ_HOME=C:\Users\friend\Desktop\mpj\mpj-v0_44

        MPI.Init(args);

        int rank = MPI.COMM_WORLD.Rank();
        int numOfRanks = MPI.COMM_WORLD.Size();

        HelpFunctions hf = new HelpFunctions();

        // size hardcoded since scanner is broken
        int size = 4;
        Matrix a = new Matrix(size);
        Matrix b = new Matrix(size);
        Matrix c = new Matrix(size);

        Matrix a11 = new Matrix(size / 2);
        Matrix a12 = new Matrix(size / 2);
        Matrix a21 = new Matrix(size / 2);
        Matrix a22 = new Matrix(size / 2);

        Matrix b11 = new Matrix(size / 2);
        Matrix b12 = new Matrix(size / 2);
        Matrix b21 = new Matrix(size / 2);
        Matrix b22 = new Matrix(size / 2);

        Matrix c11 = new Matrix(size / 2);
        Matrix c12 = new Matrix(size / 2);
        Matrix c21 = new Matrix(size / 2);
        Matrix c22 = new Matrix(size / 2);

        Matrix m1 = new Matrix(size / 2);
        Matrix m2 = new Matrix(size / 2);
        Matrix m3 = new Matrix(size / 2);
        Matrix m4 = new Matrix(size / 2);
        Matrix m5 = new Matrix(size / 2);
        Matrix m6 = new Matrix(size / 2);
        Matrix m7 = new Matrix(size / 2);

        // split matrices a and b
        if (rank == 0) {
            a.fill_randomly();
            b.fill_randomly();
            a11 = hf.getSplitedMatrix(a, 1);
            a12 = hf.getSplitedMatrix(a, 2);
            a21 = hf.getSplitedMatrix(a, 3);
            a22 = hf.getSplitedMatrix(a, 4);
            b11 = hf.getSplitedMatrix(b, 1);
            b12 = hf.getSplitedMatrix(b, 2);
            b21 = hf.getSplitedMatrix(b, 3);
            b22 = hf.getSplitedMatrix(b, 4);
        }

        // broadcast to everyone
        for (int i = 0; i < size / 2; i++) {
            MPI.COMM_WORLD.Bcast(a11.getArr()[i], 0, size / 2, MPI.INT, 0);
            MPI.COMM_WORLD.Bcast(a12.getArr()[i], 0, size / 2, MPI.INT, 0);
            MPI.COMM_WORLD.Bcast(a21.getArr()[i], 0, size / 2, MPI.INT, 0);
            MPI.COMM_WORLD.Bcast(a22.getArr()[i], 0, size / 2, MPI.INT, 0);
            MPI.COMM_WORLD.Bcast(b11.getArr()[i], 0, size / 2, MPI.INT, 0);
            MPI.COMM_WORLD.Bcast(b12.getArr()[i], 0, size / 2, MPI.INT, 0);
            MPI.COMM_WORLD.Bcast(b21.getArr()[i], 0, size / 2, MPI.INT, 0);
            MPI.COMM_WORLD.Bcast(b22.getArr()[i], 0, size / 2, MPI.INT, 0);
        }

        // M1 and M2
        if (rank == 0) {
            m1 = hf.multiplyMatrix(hf.addMatrix(a11, a22), hf.addMatrix(b11, b22));
            m2 = hf.multiplyMatrix(hf.addMatrix(a21, a22), b11);
        }

        // M3 and M4
        if (rank == 1) {
            m3 = hf.multiplyMatrix(a11, hf.substractMatrix(b12, b22));
            m4 = hf.multiplyMatrix(a22, hf.substractMatrix(b21, b11));
            for (int i = 0; i < size / 2; i++) {
                MPI.COMM_WORLD.Send(m3.getArr()[i], 0, size / 2, MPI.INT, 0, 0);
                MPI.COMM_WORLD.Send(m4.getArr()[i], 0, size / 2, MPI.INT, 0, 0);
            }
        }

        // M5 and M6
        if (rank == 2) {
            m5 = hf.multiplyMatrix(hf.addMatrix(a11, a12), b22);
            m6 = hf.multiplyMatrix(hf.substractMatrix(a21, a11), hf.addMatrix(b11, b12));
            for (int i = 0; i < size / 2; i++) {
                MPI.COMM_WORLD.Send(m5.getArr()[i], 0, size / 2, MPI.INT, 0, 0);
                MPI.COMM_WORLD.Send(m6.getArr()[i], 0, size / 2, MPI.INT, 0, 0);
            }
        }

        // M7
        if (rank == 3) {
            m7 = hf.multiplyMatrix(hf.substractMatrix(a12, a22), hf.addMatrix(b21, b22));
            for (int i = 0; i < size / 2; i++) {
                MPI.COMM_WORLD.Send(m7.getArr()[i], 0, size / 2, MPI.INT, 0, 0);
            }
        }

        // recv those bitches
        if (rank == 0) {
            for (int i = 0; i < size / 2; i++) {
                MPI.COMM_WORLD.Recv(m3.getArr()[i], 0, size / 2, MPI.INT, 1, 0);
                MPI.COMM_WORLD.Recv(m4.getArr()[i], 0, size / 2, MPI.INT, 1, 0);

                MPI.COMM_WORLD.Recv(m5.getArr()[i], 0, size / 2, MPI.INT, 2, 0);
                MPI.COMM_WORLD.Recv(m6.getArr()[i], 0, size / 2, MPI.INT, 2, 0);

                MPI.COMM_WORLD.Recv(m7.getArr()[i], 0, size / 2, MPI.INT, 3, 0);
            }

            c11 = hf.addMatrix(hf.substractMatrix(hf.addMatrix(m1, m4), m5), m7);
            c12 = hf.addMatrix(m3, m5);
            c21 = hf.addMatrix(m2, m4);
            c22 = hf.addMatrix(hf.addMatrix(hf.substractMatrix(m1, m2), m3), m6);

            c = hf.getJoinedMatrix(c11, c12, c21, c22);
        }

        for (int i = 0; i < size; i++) {
            MPI.COMM_WORLD.Bcast(c.getArr()[i], 0, size, MPI.INT, 0);
        }

        if (rank == 0) {
            System.out.println(rank + ": ");
            hf.printMatrix(c);
        }

        MPI.COMM_WORLD.Barrier();

        if (rank == 1) {
            System.out.println(rank + ": ");
            hf.printMatrix(c);
        }

        MPI.COMM_WORLD.Barrier();

        if (rank == 2) {
            System.out.println(rank + ": ");
            hf.printMatrix(c);
        }

        MPI.COMM_WORLD.Barrier();

        if (rank == 3) {
            System.out.println(rank + ": ");
            hf.printMatrix(c);
        }

        MPI.Finalize();
    }
}
