import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class PokerHands {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args) throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            String[] tokens = line.split(" ");
            Card[] black = new Card[5];
            for (int i = 0; i < 5; i++) {
                black[i] = new Card(tokens[i]);
            }
            CardValue x = getValue(black);
            Card[] white = new Card[5];
            for (int i = 0; i < 5; i++) {
                white[i] = new Card(tokens[i+5]);
            }
            CardValue y = getValue(white);
            if (x.compareTo(y) < 0) {
                System.out.println("White wins.");
            } else if (x.compareTo(y) > 0) {
                System.out.println("Black wins.");
            } else {
                System.out.println("Tie.");
            }
        }
    }

    static CardValue getValue(Card[] cards) {
        Arrays.sort(cards, new Comparator<Card>() {
            @Override
            public int compare(Card o1, Card o2) {
                return o1.suite - o2.suite;
            }
        });
        if (cards[0].suite == cards[4].suite) { // same suite
            Arrays.sort(cards, new Comparator<Card>() {
                @Override
                public int compare(Card o1, Card o2) {
                    return o1.number - o2.number;
                }
            });
            if (cards[0].number + 4 == cards[4].number) {
                return new CardValue(9, cards[4].number);
            }
            return new CardValue(6, cards[0].number + cards[1].number * 20 + cards[2].number * 400 + cards[3].number * 8000 + cards[4].number * 160000);
        }
        Arrays.sort(cards, new Comparator<Card>() {
            @Override
            public int compare(Card o1, Card o2) {
                return o1.number - o2.number;
            }
        });
        Map<Integer, Integer> map = new HashMap<>();
        for (Card c : cards) {
            if (map.containsKey(c.number)) {
                map.put(c.number, map.get(c.number) + 1);
            } else {
                map.put(c.number, 1);
            }
        }
        if (map.size() == 2 || map.size() == 4) {
            int maxKey = 0, maxValue = 0;
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                if (entry.getValue() > maxValue) {
                    maxValue = entry.getValue();
                    maxKey = entry.getKey();
                }
            }
            if (maxValue == 4 && map.size() == 2) {
                return new CardValue(8, maxKey);
            }
            if (maxValue == 3 && map.size() == 2) {
                return new CardValue(7, maxKey);
            }
            int q = 1;
            int v = 0;
            for (Card c : cards) {
                if (c.number != maxKey) {
                    v += c.number * q;
                }
                q *= 20;
            }
            return new CardValue(2, v + q * maxKey);
        }
        if (map.size() == 5) {
            if (cards[0].number + 4 == cards[4].number) {
                return new CardValue(5, cards[4].number);
            }
            return new CardValue(1, cards[0].number + cards[1].number * 20 + cards[2].number * 400 + cards[3].number * 8000 + cards[4].number * 160000);
        }
        int maxKey = 0, maxValue = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() > maxValue) {
                maxValue = entry.getValue();
                maxKey = entry.getKey();
            }
        }
        if (maxValue == 3) {
            return new CardValue(4, maxKey);
        }
        int secondKey = 0;
        int thirdKey = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 2 && entry.getKey() != maxKey) {
                secondKey = entry.getKey();
            }
            if (entry.getValue() == 1) {
                thirdKey = entry.getKey();
            }
        }
        return new CardValue(3, maxKey * 10000 + secondKey * 100 + thirdKey);
    }

    static class Card {
        int number;
        char suite;

        public Card(String s) {
            switch (s.charAt(0)) {
                case 'T':
                    this.number = 10;
                    break;
                case 'J':
                    this.number = 11;
                    break;
                case 'Q':
                    this.number = 12;
                    break;
                case 'K':
                    this.number = 13;
                    break;
                case 'A':
                    this.number = 14;
                    break;
                default:
                    this.number = s.charAt(0) - '0';
            }
            this.suite = s.charAt(1);
        }
    }


    static class CardValue implements Comparable<CardValue> {
        int type;
        int value;

        public CardValue(int type, int value) {
            this.type = type;
            this.value = value;
        }

        @Override
        public int compareTo(CardValue o) {
            if (this.type < o.type) return -1;
            else if (this.type > o.type) return 1;
            else return this.value - o.value;
        }
    }
}
