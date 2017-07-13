package views;

import javax.swing.*;
import java.awt.*;
import java.util.UUID;

/**
 * Created by hayden on 5/12/17.
 */
public class RootPanel extends JPanel {

    /**
     * Class stores other JPanels as a stack of cards this allows us to change between JPanels without doing anything
     * to the JFrame itself.
     */

    private CardLayout cardLayout;

    public RootPanel() {
        this.cardLayout = new CardLayout();
        setLayout(cardLayout);
    }

    /**
     * Add the panel to the stack of cards for use later
     * @param panel
     * @param name
     */
    public void addCardToStack(JPanel panel, String name) {
        this.add(panel, name);
        panel.setName(name);
    }

    /**
     * Make the passed in panel instantly visible
     * @param panel
     */
    public void switchToCard(JPanel panel) {
        String panelID = UUID.randomUUID().toString();
        addCardToStack(panel, panelID);
        showCardInStack(panelID);
    }

    /**
     * Switched to named card in stack
     * @param name
     */
    public void showCardInStack(String name) {
        cardLayout.show(this, name);
    }

    /**
     * Get rid of every card other than our main menu card
     */
    public void removeUnneededCards() {
        for (Component component: this.getComponents()) {
            if (component instanceof JPanel) {
                JPanel card = (JPanel) component;
                if (!card.getName().equals(MainMenu.NAME) && !card.getName().equals(HelpScreen.NAME)) {
                    this.remove(card);
                }
            }
        }

    }
}
