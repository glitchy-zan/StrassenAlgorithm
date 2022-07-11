import mpi.MPI;

import java.util.Random;
import java.util.Scanner;

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
        int size = MPI.COMM_WORLD.Size();


        MPI.Finalize();
    }
}
