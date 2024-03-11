package blackjack.controller;

import blackjack.model.blackjackgame.BlackJackGame;
import blackjack.model.blackjackgame.PlayersGameResults;
import blackjack.model.generator.CardGenerator;
import blackjack.model.generator.RandomIndexGenerator;
import blackjack.model.participants.Dealer;
import blackjack.model.participants.Player;
import blackjack.view.Command;
import blackjack.view.InputView;
import blackjack.view.OutputView;
import java.util.List;

public class BlackJackController {

    private final InputView inputView;
    private final OutputView outputView;

    public BlackJackController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        Dealer dealer = new Dealer();
        List<Player> players = inputView.readPlayers();
        BlackJackGame blackJackGame = new BlackJackGame(dealer, players, new CardGenerator(new RandomIndexGenerator()));
        blackJackGame.distributeCards();
        outputView.printDistributedCardsInfo(blackJackGame);

        executeMultipleTurns(players, blackJackGame);
        outputView.printFinalScore(blackJackGame);
        PlayersGameResults playersGameResults = blackJackGame.calculatePlayersResults();
        outputView.printGameResults(playersGameResults);
    }

    private void executeMultipleTurns(List<Player> players, BlackJackGame blackJackGame) {
        for (int index = 0; index < players.size(); index++) {
            Player player = players.get(index);
            drawCardWithCommand(player, blackJackGame, index);
        }
        if (blackJackGame.checkDealerState()) {
            blackJackGame.updateDealer();
            outputView.printDealerChange();
        }
    }

    private void drawCardWithCommand(Player player, BlackJackGame blackJackGame, int index) {
        while (checkDrawCardState(player) && inputView.readCommand(player) == Command.YES) {
            blackJackGame.update(index);
            outputView.printPlayerCardsInfo(blackJackGame, index);
        }
    }

    private boolean checkDrawCardState(Player player) {
        if (!player.checkCanGetMoreCard()) {
            outputView.printInvalidDrawCardState();
        }
        return player.checkCanGetMoreCard();
    }
}
