import java.util.Arrays;
import java.util.Random;


public class Hash {
    //public StringBuilder x=new StringBuilder("");
    String x=new String("");
    String y=new String("");
    static int[] binar = new int[8];
    static int[][] matrice_mare = new int[100][100];
    static int linie = 1, coloana = 1;

    static int cautaPrim(int x, int semn) {
        while (x != 0) {
            if (x == 2) { return 2;
            } else {
                int d = 2;
                boolean estePrim = true;
                for (d = 2; d <= x / 2; d++) {
                    if (x % d == 0) { break; }
                }
                if (d > x / 2) { return x; }
            }
            x += semn;
        }
        return -1;
    }

    static void umple() {
        if (coloana == 9) {
            for (int i = 7; i >= 0; i--) {
                matrice_mare[linie][coloana] = binar[i];
                coloana++;
            }
        }
        int coloana_copie=0, linie_copie = ++linie;
        for (int i = 1; i <= linie; i++) {
            coloana_copie = 1;
            for (int j = 1; j <= coloana; j++) {
                matrice_mare[linie_copie][coloana_copie] = matrice_mare[j][i];
                coloana_copie++;
            }
            linie_copie++;
        }
        for (int i = 1; i <= linie_copie; i++) {
            for (int j = 1; j <= coloana_copie; j++) {
                System.out.print(matrice_mare[i][j] + " ");
            }
            System.out.println();
        }
    }

    void transformare_binar() {
        for (int i = 0; i < x.length(); i++) {
            Arrays.fill(binar, 0);
            int k = (int) x.charAt(i), lungime = 0;
            while (k > 0) { binar[lungime++] = k % 2; k /= 2; }
            int m = 0, n = 7;
            while (m < n) { int aux = binar[m]; binar[m] = binar[n]; binar[n] = aux; m++; n--; }
            for (int j = 0; j < 8; j++) {
                matrice_mare[linie][coloana] = binar[j];
                coloana++;
            }
            if (i % 2 == 1) {linie++; coloana = 1; }
            //System.out.println();
        }
        int semn = 1, new_line = 0;
        new_line = (x.length() % 2 == 1) ? 1 : 0;
        for (int i = 0; i < x.length() - 1; i++) {
            for (int j = i + 1; j < x.length(); j++) {
                if (semn == 1) {
                    int k = cautaPrim(Math.abs(x.charAt(i) - x.charAt(j)), semn), lungime = 0;
                    semn = -1;
                    Arrays.fill(binar, 0);
                    while (k > 0) { binar[lungime++] = k % 2; k /= 2; }
                    int permutare_start = binar[0];
                    for (int l = 0; l < 7; l++) {
                        binar[l] = binar[l + 1];
                        matrice_mare[linie][coloana] = binar[l];
                        coloana++;
                    }
                    binar[7] = permutare_start;
                    coloana++;
                    matrice_mare[linie][coloana] = binar[7];
                    new_line++;
                    if (new_line % 2 == 0) { linie++; coloana=1; }
                } else {
                    int k = cautaPrim(Math.abs(x.charAt(i) + x.charAt(j)), semn), lungime = 0;
                    semn = 1;
                    Arrays.fill(binar, 0);
                    while (k > 0) { binar[lungime++] = k % 2; k /= 2; }
                    int permutare_final = binar[7];
                    for (int l = 0; l < 7; l++) {
                        binar[l] = binar[l + 1];
                        matrice_mare[linie][coloana] = binar[l];
                        coloana++;
                    }
                    binar[0] = permutare_final;
                    coloana++;
                    matrice_mare[linie][coloana] = binar[0];
                    new_line++;
                    if (new_line % 2 == 0) {linie++; coloana = 1; }
                    //System.out.println()
                }
            }
        }
        umple();
    }
    public void transformareHexa() {
        for (int i = 1; i <= linie; i++) {
            for (int j = 1; j <= coloana; j += 4) {
                int Hex = matrice_mare[i][j] * (int) Math.pow(2, 3) + matrice_mare[i][j + 1] * (int) Math.pow(2, 2) + matrice_mare[i][j + 2] * (int) Math.pow(2, 1) + matrice_mare[i][j + 3];
                y=y+Integer.toHexString(Hex);
                //System.out.print(Integer.toHexString(Hex));
            }
        }
    }
    void citire_X(){
        for(int i=0;i<10;i++) {
            Random carctRandom=new Random(); carctRandom.nextInt(48,122);
            int RandomInt=carctRandom.nextInt(48,90); //x.append((char)RandomInt);
            x=x+(char)RandomInt; //cod ASCII
        }
    }
    Hash() {
        citire_X();
        transformare_binar();
        transformareHexa();
        System.out.print(y);
    }
}
