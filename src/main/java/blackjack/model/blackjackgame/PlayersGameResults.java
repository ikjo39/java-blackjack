package blackjack.model.blackjackgame;

import blackjack.model.participants.Player;
import blackjack.view.DealerResultStatus;
import blackjack.view.PlayerResultStatus;
import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Collectors;

public class PlayersGameResults {

    private final Map<Player, PlayerResultStatus> result;

    public PlayersGameResults(Map<Player, PlayerResultStatus> result) {
        this.result = result;
    }

    public Map<DealerResultStatus, Long> getDealerResult() {
        return result.values()
                .stream()
                .collect(Collectors.groupingBy(DealerResultStatus::from,
                        () -> new EnumMap<>(DealerResultStatus.class),
                        Collectors.counting()));
    }

    public Map<Player, PlayerResultStatus> getResult() {
        return result;
    }
}