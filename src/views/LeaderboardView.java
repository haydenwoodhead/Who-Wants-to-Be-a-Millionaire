package views;

import models.Leaderboard;
import models.Score;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by hayden on 5/14/17.
 */
public class LeaderboardView extends JPanel {
	
	private JButton btnExit;
    private JLabel lbl1;
    private JLabel lbl2;
    private JLabel lbl3;
    private JLabel lbl4;
    private JLabel lbl5;
    private JLabel lbl6;
    private JLabel lbl7;
    private JLabel lbl8;
    private JLabel lbl9;
    private JLabel lbl10;

    public LeaderboardView(Leaderboard leaderboard, ActionListener listener) {
		setForeground(Color.LIGHT_GRAY);
		setLayout(null);

        ArrayList<Score> scores = leaderboard.getScores();
		
		btnExit = new JButton("");
		btnExit.setBounds(636, 22, 75, 25);
        ViewHelper.setupJButton(btnExit, listener);
		add(btnExit);

        if (leaderboard.hasPosition(1)) {
            lbl1 = new JLabel(scores.get(0).getUserName() + " - $" + scores.get(0).getMoneyWon());
            lbl1.setHorizontalAlignment(SwingConstants.CENTER);
            lbl1.setFont(new Font("Dialog", Font.PLAIN, 13));
            lbl1.setBounds(35, 102, 325, 60);
            add(lbl1);
        }

        if (leaderboard.hasPosition(2)) {
            lbl2 = new JLabel(scores.get(1).getUserName() + " - $" + scores.get(1).getMoneyWon());
            lbl2.setHorizontalAlignment(SwingConstants.CENTER);
            lbl2.setFont(new Font("Dialog", Font.PLAIN, 13));
            lbl2.setBounds(386, 102, 325, 60);
            add(lbl2);
        }

        if (leaderboard.hasPosition(3)) {
            lbl3 = new JLabel(scores.get(2).getUserName() + " - $" + scores.get(2).getMoneyWon());
            lbl3.setHorizontalAlignment(SwingConstants.CENTER);
            lbl3.setFont(new Font("Dialog", Font.PLAIN, 13));
            lbl3.setBounds(35, 179, 325, 60);
            add(lbl3);
        }
		
        if (leaderboard.hasPosition(4)) {
            lbl4 = new JLabel(scores.get(3).getUserName() + " - $" + scores.get(3).getMoneyWon());
            lbl4.setHorizontalAlignment(SwingConstants.CENTER);
            lbl4.setFont(new Font("Dialog", Font.PLAIN, 13));
            lbl4.setBounds(386, 179, 325, 60);
            add(lbl4);
        }
		
        if (leaderboard.hasPosition(5)) {
            lbl5 = new JLabel(scores.get(4).getUserName() + " - $" + scores.get(4).getMoneyWon());
            lbl5.setHorizontalAlignment(SwingConstants.CENTER);
            lbl5.setFont(new Font("Dialog", Font.PLAIN, 13));
            lbl5.setBounds(35, 254, 325, 60);
            add(lbl5);
        }

        if (leaderboard.hasPosition(6)) {
            lbl6 = new JLabel(scores.get(5).getUserName() + " - $" + scores.get(5).getMoneyWon());
            lbl6.setHorizontalAlignment(SwingConstants.CENTER);
            lbl6.setFont(new Font("Dialog", Font.PLAIN, 13));
            lbl6.setBounds(386, 254, 325, 60);
            add(lbl6);
        }

        if (leaderboard.hasPosition(7)) {
            lbl7 = new JLabel(scores.get(6).getUserName() + " - $" + scores.get(6).getMoneyWon());
            lbl7.setHorizontalAlignment(SwingConstants.CENTER);
            lbl7.setFont(new Font("Dialog", Font.PLAIN, 13));
            lbl7.setBounds(35, 331, 325, 60);
            add(lbl7);
        }

        if (leaderboard.hasPosition(8)) {
            lbl8 = new JLabel(scores.get(7).getUserName() + " - $" + scores.get(7).getMoneyWon());
            lbl8.setHorizontalAlignment(SwingConstants.CENTER);
            lbl8.setFont(new Font("Dialog", Font.PLAIN, 13));
            lbl8.setBounds(386, 331, 325, 60);
            add(lbl8);
        }

        if (leaderboard.hasPosition(9)) {
            lbl9 = new JLabel(scores.get(8).getUserName() + " - $" + scores.get(8).getMoneyWon());
            lbl9.setHorizontalAlignment(SwingConstants.CENTER);
            lbl9.setFont(new Font("Dialog", Font.PLAIN, 13));
            lbl9.setBounds(35, 406, 325, 60);
            add(lbl9);
        }

        if (leaderboard.hasPosition(10)) {
            lbl10 = new JLabel(scores.get(9).getUserName() + " - $" + scores.get(9).getMoneyWon());
            lbl10.setHorizontalAlignment(SwingConstants.CENTER);
            lbl10.setFont(new Font("Dialog", Font.PLAIN, 13));
            lbl10.setBounds(386, 406, 325, 60);
            add(lbl10);
        }

		JLabel lblBackground = new JLabel("");
		lblBackground.setForeground(Color.LIGHT_GRAY);
		lblBackground.setIcon(new ImageIcon("images/leaderboard.jpg"));
		lblBackground.setBounds(0, 0, 745, 500);
		add(lblBackground);
	}

    /**
     * Highlight a particular score on the leaderboard
     * @param position
     */
	public void highlightScore(int position) {
        // + 1 to shift from index to normal
        switch (position+1) {
            case 1:
                lbl1.setForeground(Color.RED);
                lbl1.setFont(new Font("Dialog", Font.BOLD, 14));
                break;
            case 2:
                lbl2.setForeground(Color.RED);
                lbl2.setFont(new Font("Dialog", Font.BOLD, 14));
                break;
            case 3:
                lbl3.setForeground(Color.RED);
                lbl3.setFont(new Font("Dialog", Font.BOLD, 14));
                break;
            case 4:
                lbl4.setForeground(Color.RED);
                lbl4.setFont(new Font("Dialog", Font.BOLD, 14));
                break;
            case 5:
                lbl5.setForeground(Color.RED);
                lbl5.setFont(new Font("Dialog", Font.BOLD, 14));
                break;
            case 6:
                lbl6.setForeground(Color.RED);
                lbl6.setFont(new Font("Dialog", Font.BOLD, 14));
                break;
            case 7:
                lbl7.setForeground(Color.RED);
                lbl7.setFont(new Font("Dialog", Font.BOLD, 14));
                break;
            case 8:
                lbl8.setForeground(Color.RED);
                lbl8.setFont(new Font("Dialog", Font.BOLD, 14));
                break;
            case 9:
                lbl9.setForeground(Color.RED);
                lbl9.setFont(new Font("Dialog", Font.BOLD, 14));
                break;
            case 10:
                lbl10.setForeground(Color.RED);
                lbl10.setFont(new Font("Dialog", Font.BOLD, 14));
                break;
            default:
                break;
        }

    }
}

