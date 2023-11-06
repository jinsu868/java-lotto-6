package lotto.domain;

import java.util.HashMap;
import java.util.Map;

public class LottoResult {
    private Map<Integer, Integer> ranks;

    public LottoResult() {
        ranks = new HashMap<>();
    }

    public int get(int value) {
        return ranks.get(value);
    }

    public void add(int rank) {
        if (rank != 0) {
            ranks.put(rank, ranks.get(rank) + 1);
        }
    }
}
