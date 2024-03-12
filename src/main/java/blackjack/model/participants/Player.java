package blackjack.model.participants;

import blackjack.model.blackjackgame.PlayerProfitCalculator;
import blackjack.model.blackjackgame.Profit;

public class Player extends Participant {
    private final String name;
    private final Betting betting;

    public Player(final String name, final Betting betting) {
        this.name = name;
        this.betting = betting;
    }

    @Override
    public boolean checkCanGetMoreCard() {
        return !cards.isBusted();
    }

    public Profit getProfit(final Participant participant) {
        return betting.getProfit(getScoreStatus(participant));
    }

    private PlayerProfitCalculator getScoreStatus(final Participant participant) {
        return cards.getPlayerResultStatus(participant.cards);
    }

    public String getName() {
        return name;
    }
}
