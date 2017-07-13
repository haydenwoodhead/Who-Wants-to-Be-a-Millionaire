package views;

import models.Game;
import models.Round;

import javax.swing.*;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.util.UUID;

public class QuestionView extends JPanel {

    private JButton btnA;
    private JButton btnB;
    private JButton btnC;
    private JButton btnD;
    private JLabel lblBackground;
    private JButton btnExit;
    private JLabel lblRound;
    private JLabel lblMoney;
    private JLabel lbl5050;
    private JLabel lblSwitch;
    private JLabel lblAsk;
    private JButton btn5050;
    private JButton btnSwitch;
    private JButton btnAsk;

    public QuestionView(Game game, ActionListener listener) {
		setLayout(null);

        Round round = game.getCurrentRound();

		// wrap in <html> tags to make the question line wrap
        JLabel lblQuestion = new JLabel("<html>" + round.getQuestion().getQuestionText() + "</html>");
        lblQuestion.setHorizontalAlignment(SwingConstants.CENTER);
		lblQuestion.setFont(new Font("Dialog", Font.BOLD, 20));
		lblQuestion.setBounds(82, 52, 226, 199);
		add(lblQuestion);

		btnA = new JButton("<html>" + round.getChoices().get(0) + "</html>");
        btnA.setName(Round.CHOICE_A);
		btnA.setFont(new Font("Dialog", Font.BOLD, 13));
        btnA.setBounds(62, 294, 290, 69);
        ViewHelper.setupJButton(btnA, listener);
		add(btnA);

        btnB = new JButton("<html>" + round.getChoices().get(1) + "</html>");
        btnB.setName(Round.CHOICE_B);
        btnB.setFont(new Font("Dialog", Font.BOLD, 13));
        btnB.setBounds(400, 294, 290, 69);
        ViewHelper.setupJButton(btnB, listener);
        add(btnB);

        //Don't add buttons c & d if we the round only has 2 possible answers
        if (round.getChoices().size() > 2) {
            btnC = new JButton("<html>" + round.getChoices().get(2) + "</html>");
            btnC.setName(Round.CHOICE_C);
            btnC.setFont(new Font("Dialog", Font.BOLD, 13));
            btnC.setBounds(62, 396, 290, 60);
            ViewHelper.setupJButton(btnC, listener);
            add(btnC);

            btnD = new JButton("<html>" + round.getChoices().get(3) + "</html>");
            btnD.setName(Round.CHOICE_D);
            btnD.setFont(new Font("Dialog", Font.BOLD, 13));
            btnD.setBounds(400, 396, 290, 60);
            ViewHelper.setupJButton(btnD, listener);
            add(btnD);
        }

        btnExit = new JButton("");
        btnExit.setBounds(637, 24, 73, 25);
        ViewHelper.setupJButton(btnExit, listener);
        add(btnExit);
        
        lblRound = new JLabel("Round: " + game.getCurrentRoundNumber());
        lblRound.setFont(new Font("Dialog", Font.BOLD, 32));
        lblRound.setForeground(Color.WHITE);
        lblRound.setBounds(400, 59, 177, 38);
        add(lblRound);
                
        lblMoney = new JLabel("Winnings: $" + game.getCurrentMoney());
        lblMoney.setForeground(Color.WHITE);
        lblMoney.setFont(new Font("Dialog", Font.PLAIN, 25));
        lblMoney.setBounds(400, 92, 206, 38);
        add(lblMoney);

        if (game.hasFiftyFifty()) {
            btn5050 = new JButton("");
            btn5050.setToolTipText("Remove Two Incorrect Options");
            btn5050.setBounds(389, 190, 73, 60);
            ViewHelper.setupJButton(btn5050, listener);
            add(btn5050);

            lbl5050 = new JLabel("");
            lbl5050.setIcon(new ImageIcon("images/50_50.png"));
            lbl5050.setBounds(386, 179, 80, 80);
            add(lbl5050);
        }

        if (game.hasSwitchQuestion()) {
            btnSwitch = new JButton("");
            btnSwitch.setToolTipText("Swap The Question For a New One");
            btnSwitch.setBounds(510, 190, 64, 60);
            ViewHelper.setupJButton(btnSwitch, listener);
            add(btnSwitch);

            lblSwitch = new JLabel("");
            lblSwitch.setIcon(new ImageIcon("images/switch_question.png"));
            lblSwitch.setBounds(501, 179, 80, 80);
            add(lblSwitch);
        }

        if (game.hasAskTheAudience()) {
            btnAsk = new JButton("");
            btnAsk.setToolTipText("Ask The Audience");
            btnAsk.setBounds(620, 190, 64, 60);
            ViewHelper.setupJButton(btnAsk, listener);
            add(btnAsk);

            lblAsk = new JLabel("");
            lblAsk.setIcon(new ImageIcon("images/ask_audience.png"));
            lblAsk.setBounds(610, 179, 80, 80);
            add(lblAsk);
        }

        lblBackground = new JLabel("");
        lblBackground.setForeground(Color.WHITE);
        lblBackground.setBounds(0, 0, 745, 500);

        // Use the correct background based on how many possible answers the round has
        if (round.getChoices().size() > 2) {
            lblBackground.setIcon(new ImageIcon("images/bg_4_questions.jpg"));
        } else {
            lblBackground.setIcon(new ImageIcon("images/bg_2_questions.jpg"));
        }

        add(lblBackground);

	}

    public JButton getBtnA() {
        return btnA;
    }

    public JButton getBtnB() {
        return btnB;
    }

    public JButton getBtnC() {
        return btnC;
    }

    public JButton getBtnD() {
        return btnD;
    }
    
    public JButton getBtnExit() {
    	return btnExit;
    }

    public JButton getBtn5050() {
        return btn5050;
    }

    public JButton getBtnSwitch() {
        return btnSwitch;
    }

    public JButton getBtnAsk() {
        return btnAsk;
    }

    public JLabel getLblBackground() {
        return lblBackground;
    }
}
