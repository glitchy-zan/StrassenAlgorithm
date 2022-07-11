import java.util.Random;

public class Matrix {
    private int size;
    private int[][] arr;

    Matrix(int size) {
        this.size = size;
        this.arr = new int[size][size];
    }

    public void fill_randomly() {
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                this.arr[i][j] = new Random().nextInt(9) + 1;
            }
        }
    }

    public int getSize() {
        return size;
    }

    public int[][] getArr() {
        return arr;
    }

    public void setArr(int[][] arr) {
        this.arr = arr;
    }

    public void setElement(int e, int row, int col) {
        this.arr[row][col] = e;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
