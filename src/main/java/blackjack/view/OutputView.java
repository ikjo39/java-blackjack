package blackjack.view;

import blackjack.model.blackjackgame.PlayersResults;
import blackjack.model.blackjackgame.Profit;
import blackjack.model.cards.Card;
import blackjack.model.cards.Cards;
import blackjack.model.participants.Dealer;
import blackjack.model.participants.Name;
import blackjack.model.participants.Participant;
import blackjack.model.participants.Player;
import blackjack.model.participants.PlayerInfo;
import blackjack.model.participants.Players;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OutputView {
    private static final String DEALER_NAME = "딜러";
    private static final String MULTIPLE_OUTPUTS_DELIMITER = ", ";
    private static final DecimalFormat DOUBLE_FORMAT = new DecimalFormat("#,###.#");

    public void printDistributedCardsInfo(final Players players, final Dealer dealer) {
        List<Player> allPlayers = players.getPlayers();
        System.out.println();
        System.out.printf(DEALER_NAME + "와 %s에게 2장을 나누었습니다.%n", getPlayersNames(allPlayers));

        System.out.print(getDealerCardFormat(getDealerCard(dealer)));
        allPlayers.forEach(player -> System.out.print(getParticipantCardsFormat(getPlayerName(player), player.getCards())));
        System.out.println();
    }

    public void printPlayerCardsInfo(final Players players, final int index) {
        Player player = players.getPlayer(index);
        System.out.print(getParticipantCardsFormat(getPlayerName(player), player.getCards()));
    }

    public void printDealerChange() {
        System.out.println();
        System.out.println(DEALER_NAME + "는 16이하라 한장의 카드를 더 받았습니다.");
    }

    public void printFinalScore(final Players players, final Dealer dealer) {
        System.out.println();
        System.out.print(getParticipantScoreFormat(DEALER_NAME, dealer.getCards(), getValue(dealer)));
        List<Player> allPlayers = players.getPlayers();
        allPlayers.forEach(player -> System.out.printf(
                getParticipantScoreFormat(getPlayerName(player), player.getCards(), getValue(player)))
        );
    }

    private int getValue(final Participant participant) {
        return participant.getCards().getCardsScore().getValue();
    }

    public void printGameResults(final Players players) {
        System.out.println();
        System.out.println("### 최종 수익");

        PlayersResults playersResults = players.getPlayersResults();
        System.out.printf("%s: %s%n", DEALER_NAME, convertFormat(playersResults.getDealerProfit()));
        printPlayerResultsFormat(playersResults.getResults());
    }

    public void printInvalidDrawCardState() {
        System.out.println("카드 합계가 21을 초과하였습니다. 턴을 종료합니다.");
    }

    private String getDealerCardFormat(final Card card) {
        return String.format("%s: %s%n", DEALER_NAME, convertCardText(card));
    }

    private String getParticipantCardsFormat(final String name, final Cards cards) {
        return String.format("%s: %s%n", name, getCardsText(cards));
    }

    private String getParticipantScoreFormat(final String name, final Cards cards, final int score) {
        return String.format("%s: %s - 결과: %d%n", name, getCardsText(cards), score);
    }

    private void printPlayerResultsFormat(final Map<Player, Profit> gameResult) {
        gameResult.entrySet()
                .stream()
                .map(entry -> getPlayerResultFormat(getPlayerName(entry.getKey()), convertFormat(entry.getValue())))
                .forEach(System.out::print);
    }

    private String convertFormat(final Profit profit) {
        return DOUBLE_FORMAT.format(profit.getValue());
    }

    private String getPlayerResultFormat(final String name, final String profit) {
        return String.format("%s: %s%n", name, profit);
    }

    private String getPlayerName(final Player player) {
        return player.getPlayerInfo().getName().getValue();
    }

    private String getPlayersNames(final List<Player> players) {
        return players.stream()
                .map(Player::getPlayerInfo)
                .map(PlayerInfo::getName)
                .map(Name::getValue)
                .collect(Collectors.joining(MULTIPLE_OUTPUTS_DELIMITER));
    }

    private Card getDealerCard(final Dealer dealer) {
        return dealer.getCards()
                .getCards()
                .get(0);
    }

    private String getCardsText(final Cards cards) {
        return cards.getCards()
                .stream()
                .map(this::convertCardText)
                .collect(Collectors.joining(MULTIPLE_OUTPUTS_DELIMITER));
    }

    private String convertCardText(final Card card) {
        CardNumberText cardNumberText = CardNumberText.from(card.getCardNumber());
        CardShapeText cardShapeText = CardShapeText.from(card.getCardShape());
        return cardNumberText.getText() + cardShapeText.getText();
    }
}
