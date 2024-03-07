package blackjack.model;

public class Dealer {
    private final Cards cards;

    public Dealer(Cards cards) {
        this.cards = cards;
    }

    public void addCard(CardGenerator cardGenerator) {
        if (cards.calculateScore() <= 16) {
            cards.addCard(cardGenerator.drawCard());
        }
    }

    public void addCards(CardGenerator cardGenerator) {
        cards.addCard(cardGenerator.drawCards());
    }

    public Cards getCards() {
        return cards;
    }
}