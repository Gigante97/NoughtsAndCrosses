import java.util.Random;
import java.util.Scanner;

public class FirstGame {
    private static final int SIZE = 5;
    private static final char DOT_X = 'X';
    private static final char DOT_O = 'O';
    private static final char DOT_EMPTY = '.';
    private static final int TO_WIN = 4;

    private static char[][] map = new char[SIZE][SIZE];

    private static Scanner scanner = new Scanner(System.in);
    private static Random random = new Random();

    public static void initMap () {
        map = new char[SIZE][SIZE];
        for (int i=0; i<SIZE; i++) {
            for (int j=0; j<SIZE; j++) {
                map[i][j] = DOT_EMPTY;
            }
        }
    }

    public static void printMap () {
        for (int i =0; i<=SIZE; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i =0; i < SIZE; i++) {
            System.out.print((i+1) + " ");
            for (int j =0; j<SIZE; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void setSym(int y, int x, char sym) {
        map[y][x] = sym;
    }

    public static void humanTurn() {
        int x;
        int y;
        do {
            System.out.println("Введите координаты в формате X Y");
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;
        } while (!isCellValid(y, x));
        setSym(y, x, DOT_X);
    }


    public static void aiTurn() {

        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++) {
                if (isCellValid(i, j)) {
                    setSym(i, j, DOT_O);
                    if (checkWin(DOT_O)) return;
                    setSym(i, j, DOT_EMPTY);
                }
            }

        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++) {
                if (isCellValid(i, j)) {
                    setSym(i, j, DOT_X);
                    if (checkWin(DOT_X)) {
                        setSym(i, j, DOT_O);
                        return;
                    }
                    setSym(i, j, DOT_EMPTY);
                }
            }

        int x;
        int y;
        do {
            x = random.nextInt(SIZE);
            y = random.nextInt(SIZE);
        } while (!isCellValid(y, x));
        setSym(y, x, DOT_O );
    }

    public static boolean isCellValid(int y, int x) {
        if (x < 0 || y < 0 || x > SIZE - 1 || y > SIZE - 1) {
            return false;
        }
        return map[y][x] == DOT_EMPTY;
    }

    public static boolean isMapFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == DOT_EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }



    private static boolean checkWin(char sym) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (checkLine(i, j, 0, 1,  sym)) return true;
                if (checkLine(i, j, 1, 1,  sym)) return true;
                if (checkLine(i, j, 1, 0,  sym)) return true;
                if (checkLine(i, j, -1, 1, sym)) return true;
            }
        }
        return false;
    }

    private static boolean checkLine(int y, int x, int vy, int vx, char sym) {
        int wayX = x + (TO_WIN - 1) * vx;
        int wayY = y + (TO_WIN - 1) * vy;
        if (wayX < 0 || wayY < 0 || wayX > SIZE - 1 || wayY > SIZE - 1) return false;
        for (int i = 0; i < TO_WIN; i++) {
            int itemY = y + i * vy;
            int itemX = x + i * vx;
            if (map[itemY][itemX] != sym) return false;
        }
        return true;
    }


    public static void main(String[] args) {
        initMap();
        printMap();

        while (true) {
            humanTurn();
            printMap();
            if (checkWin(DOT_X)) {
                System.out.println("Человек умнее!");
                break;
            }
            if (isMapFull()) {
                System.out.println("Человек не уступает копьютеру!");
                break;
            }
            aiTurn();
            printMap();
            if (checkWin(DOT_O)) {
                System.out.println("Компюьтер оказался сильнее(");
                break;
            }
            if (isMapFull()) {
                System.out.println("Компюьтер не смог победить - Ничья!");
                break;
            }
        }

    }

}