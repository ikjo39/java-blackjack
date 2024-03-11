package blackjack.model.blackjackgame;

import blackjack.model.generator.CardGenerator;
import blackjack.model.participants.Dealer;
import blackjack.model.participants.Player;
import blackjack.view.PlayerResultStatus;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BlackJackGame {
    private final Dealer dealer;
    private final List<Player> players;
    private final CardGenerator cardGenerator;

    public BlackJackGame(Dealer dealer, List<Player> players, CardGenerator cardGenerator) {
        this.dealer = dealer;
        this.cardGenerator = cardGenerator;
        this.players = new ArrayList<>(players);
    }

    public void distributeCards() {
        dealer.addCards(cardGenerator.drawFirstCardsDealt());
        players.forEach(player -> player.addCards(cardGenerator.drawFirstCardsDealt()));
    }

    public void update(int index) {
        Player player = players.get(index);
        player.addCard(cardGenerator.drawCard());
    }

    public boolean checkDealerState() {
        return dealer.checkCanGetMoreCard();
    }

    public void updateDealer() {
        dealer.addCard(cardGenerator.drawCard());
    }

    public PlayersGameResults calculatePlayersResults() {
        Map<Player, PlayerResultStatus> result = new LinkedHashMap<>();
        players.forEach(player -> result.put(player, player.determineWinner(dealer)));
        return new PlayersGameResults(result);
    }

    public Dealer getDealer() {
        return dealer;
    }

    public List<Player> getPlayers() {
        return players;
    }
}
