package views;

import models.AskTheAudience;
import models.Game;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

/**
 * Created by hayden on 5/16/17.
 */
public class AskTheAudienceView extends QuestionView {

    public AskTheAudienceView(Game game, AskTheAudience askTheAudiences, ActionListener listener) {
        super(game, listener);

        ArrayList<String> percentages = askTheAudiences.getPercentages();

    	JLabel lblA = new JLabel("A: " + percentages.get(0) +"%");
    	lblA.setHorizontalAlignment(SwingConstants.CENTER);
    	lblA.setFont(new Font("Dialog", Font.BOLD, 18));
    	lblA.setForeground(Color.WHITE);
    	lblA.setBounds(62, 272, 287, 22);
    	getLblBackground().add(lblA);
    	
    	JLabel lblB = new JLabel("B: " + percentages.get(1) +"%");
    	lblB.setHorizontalAlignment(SwingConstants.CENTER);
    	lblB.setForeground(Color.WHITE);
    	lblB.setFont(new Font("Dialog", Font.BOLD, 18));
    	lblB.setBounds(396, 272, 287, 22);
    	getLblBackground().add(lblB);

        if (game.getCurrentRound().getChoices().size() > 2) {
            JLabel lblC = new JLabel("C: " + percentages.get(2) +"%");
            lblC.setHorizontalAlignment(SwingConstants.CENTER);
            lblC.setForeground(Color.WHITE);
            lblC.setFont(new Font("Dialog", Font.BOLD, 18));
            lblC.setBounds(62, 371, 287, 22);
            getLblBackground().add(lblC);

            JLabel lblD = new JLabel("D: " + percentages.get(3) +"%");
            lblD.setHorizontalAlignment(SwingConstants.CENTER);
            lblD.setForeground(Color.WHITE);
            lblD.setFont(new Font("Dialog", Font.BOLD, 18));
            lblD.setBounds(396, 371, 287, 22);
            getLblBackground().add(lblD);
        }
    }
}
