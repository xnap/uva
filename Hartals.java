import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class Hartals {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args) throws IOException {
        int t = Integer.parseInt(br.readLine());
        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine());
            int p = Integer.parseInt(br.readLine());
            int[] w = new int[p];
            for (int i = 0; i < p; i++) {
                w[i] = Integer.parseInt(br.readLine());
            }
            Set<Integer> s = new HashSet<>();
            for (int i = 0; i < p; i++) {
                for (int j = 1; j * w[i] <= n; j++) {
                    int ok = j * w[i];
                    if (ok % 7 != 6 && ok % 7 != 0) {
                        s.add(j * w[i]);
                    }
                }
            }
            System.out.println(s.size());
        }
    }
}
