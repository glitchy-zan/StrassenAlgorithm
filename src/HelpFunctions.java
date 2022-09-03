public class HelpFunctions {

    public Matrix getSplitedMatrix(Matrix m, int quadrant) {
        Matrix splitedMatrix = new Matrix(m.getSize() / 2);

        if (quadrant == 1) {
            for (int i = 0; i < m.getSize() / 2; i++) {
                for (int j = 0; j < m.getSize() / 2; j++) {
                    splitedMatrix.setElement(m.getArr()[i][j], i, j);
                }
            }
        } else if (quadrant == 2) {
            for (int i = 0; i < m.getSize() / 2; i++) {
                for (int j = m.getSize() / 2; j < m.getSize(); j++) {
                    splitedMatrix.setElement(m.getArr()[i][j], i, j - m.getSize() / 2);
                }
            }
        } else if (quadrant == 3) {
            for (int i = m.getSize() / 2; i < m.getSize(); i++) {
                for (int j = 0; j < m.getSize() / 2; j++) {
                    splitedMatrix.setElement(m.getArr()[i][j], i - m.getSize() / 2, j);
                }
            }
        } else {
            for (int i = m.getSize() / 2; i < m.getSize(); i++) {
                for (int j = m.getSize() / 2; j < m.getSize(); j++) {
                    splitedMatrix.setElement(m.getArr()[i][j], i - m.getSize() / 2, j - m.getSize() / 2);
                }
            }
        }
        return splitedMatrix;
    }

    public Matrix getJoinedMatrix(Matrix m11, Matrix m12, Matrix m21, Matrix m22) {
        Matrix joinedMatrix = new Matrix(m11.getSize() * 2);

        for (int i = 0; i < joinedMatrix.getSize(); i++) {
            for (int j = 0; j < joinedMatrix.getSize(); j++) {
                if (i < m11.getSize() && j < m11.getSize()) {
                    joinedMatrix.setElement(m11.getArr()[i][j], i, j);
                } else if (i < m11.getSize()) {
                    joinedMatrix.setElement(m12.getArr()[i][j - m12.getSize()], i, j);
                } else if (j < m11.getSize()) {
                    joinedMatrix.setElement(m21.getArr()[i - m21.getSize()][j], i, j);
                } else {
                    joinedMatrix.setElement(m22.getArr()[i - m22.getSize()][j - m22.getSize()], i, j);
                }
            }
        }

        return joinedMatrix;
    }

    public Matrix multiplyMatrix(Matrix m1, Matrix m2) {
        Matrix multipliedMatrix = new Matrix(m1.getSize());

        for (int i = 0; i < m1.getSize(); i++) {
            for (int j = 0; j < m1.getSize(); j++) {
                for (int k = 0; k < m1.getSize(); k++) {
                    multipliedMatrix.setElement(multipliedMatrix.getArr()[i][j] + (m1.getArr()[i][k] * m2.getArr()[k][j]), i, j);
                }
            }
        }

        return multipliedMatrix;
    }

    public Matrix addMatrix(Matrix m1, Matrix m2) {
        Matrix addedMatrix = new Matrix(m1.getSize());

        for (int i = 0; i < m1.getSize(); i++) {
            for (int j = 0; j < m2.getSize(); j++) {
                addedMatrix.setElement(m1.getArr()[i][j] + m2.getArr()[i][j], i, j);
            }
        }

        return addedMatrix;
    }

    public Matrix substractMatrix(Matrix m1, Matrix m2) {
        Matrix substractedMatrix = new Matrix(m1.getSize());

        for (int i = 0; i < m1.getSize(); i++) {
            for (int j = 0; j < m1.getSize(); j++) {
                substractedMatrix.setElement(m1.getArr()[i][j] - m2.getArr()[i][j], i, j);
            }
        }

        return substractedMatrix;
    }

    public void printMatrix(Matrix m) {
        for (int i = 0; i < m.getSize(); i++) {
            for (int j = 0; j < m.getSize(); j++) {
                System.out.print(m.getArr()[i][j]);
            }
            System.out.println();
        }
    }

    public int[] matrixToArray(Matrix m) {
        int[] arr = new int[m.getSize() * m.getSize()];
        for (int i = 0; i < m.getSize(); i++) {
            for (int j = 0; j < m.getSize(); j++) {
                arr[i * m.getSize() + j] = m.getArr()[i][j];
            }
        }
        return arr;
    }
}
