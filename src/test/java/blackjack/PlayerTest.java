package blackjack;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import blackjack.model.Card;
import blackjack.model.CardGenerator;
import blackjack.model.CardNumber;
import blackjack.model.CardShape;
import blackjack.model.Cards;
import blackjack.model.Player;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PlayerTest {
    @DisplayName("플레이어가 카드 한장을 지급받는다")
    @Test
    void addCard() {
        List<Card> given = List.of(
                new Card(CardNumber.SIX, CardShape.HEART),
                new Card(CardNumber.TEN, CardShape.CLOVER)
        );
        Cards cards = new Cards(given);
        Player player = new Player("daon", cards);
        CardGenerator cardGenerator = new CardGenerator(maxRange -> 3);

        player.addCard(cardGenerator);
        assertThat(player.getCards().getCards()).hasSize(3);
    }
}
