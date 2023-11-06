package lotto.service;

import java.util.List;
import lotto.constant.LottoConstant;
import lotto.domain.BonusNumber;
import lotto.domain.Lotto;
import lotto.domain.LottoResult;
import lotto.domain.PurchaseAmount;
import lotto.domain.WinningNumbers;

public class LottoService {
    public LottoResult getLottoResult(final List<Lotto> lottos,
                                    final WinningNumbers winningNumbers, final BonusNumber bonusNumber) {
        LottoResult lottoResult = new LottoResult();
        for (int i = 0; i < lottos.size(); i++) {
            Lotto lotto = lottos.get(i);
            int matchCount = calculateMatch(lotto, winningNumbers);
            boolean isBonus = isBonusMatch(lotto, bonusNumber);
            int rank = getRank(matchCount, isBonus);
            lottoResult.add(rank);
        }
        return lottoResult;
    }

    public float getRevenue(final LottoResult lottoResult) {
        float revenue = 0;
        for (int rank = LottoConstant.FIRST_RANK; rank <= LottoConstant.FIFTH_RANK; rank++) {
            if (lottoResult.contains(rank)) {
                int rankCount = lottoResult.get(rank);
                revenue += calculateRevenue(rank) * rankCount;
            }
        }
        return revenue;
    }

    public float getEarningRate(final PurchaseAmount purchaseAmount, float revenue) {
        if (revenue == 0) {
            return 0;
        }
        return (revenue * 100) / purchaseAmount.getAmount();
    }

    public int getLottoCount(final PurchaseAmount purchaseAmount) {
        return purchaseAmount.getAmount() / LottoConstant.LOTTO_PRICE;
    }

    private int calculateMatch(final Lotto lotto, final WinningNumbers winningNumbers) {
        List<Integer> lottoWinningNumbers = winningNumbers.getNumbers();
        List<Integer> lottoNumbers = lotto.getNumbers();
        int matchCount = 0;
        for (int i = 0; i < LottoConstant.LOTTO_LENGTH; i++) {
            int number = lottoWinningNumbers.get(i);
            if (lottoNumbers.contains(number)) {
                matchCount++;
            }
        }
        return matchCount;
    }

    private boolean isBonusMatch(final Lotto lotto, final BonusNumber bonusNumber) {
        List<Integer> lottoNumbers = lotto.getNumbers();
        if (lottoNumbers.contains(bonusNumber.get())) {
            return true;
        }
        return false;
    }

    private int getRank(int matchCount, boolean isBonus) {
        if (matchCount == 6) {
            return LottoConstant.FIRST_RANK;
        } else if (matchCount == 5 && isBonus) {
            return LottoConstant.SECOND_RANK;
        } else if (matchCount == 5) {
            return LottoConstant.THIRD_RANK;
        } else if (matchCount == 4) {
            return LottoConstant.FOURTH_RANK;
        } else if (matchCount == 3) {
            return LottoConstant.FIFTH_RANK;
        }
        return LottoConstant.NO_RANK;
    }

    private int calculateRevenue(int rank) {
        int revenue = 0;
        if (rank == LottoConstant.FIRST_RANK) {
            revenue += LottoConstant.FIRST_PRIZE;
        } else if (rank == LottoConstant.SECOND_RANK) {
            revenue += LottoConstant.SECOND_PRIZE;
        } else if (rank == LottoConstant.THIRD_RANK) {
            revenue += LottoConstant.THIRD_PRIZE;
        } else if (rank == LottoConstant.FOURTH_RANK) {
            revenue += LottoConstant.FOURTH_PRIZE;
        } else if (rank == LottoConstant.FIFTH_RANK) {
            revenue += LottoConstant.FIFTH_PRIZE;
        }
        return revenue;
    }
}
