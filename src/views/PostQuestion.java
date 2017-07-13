package views;

import javax.sound.sampled.*;
import javax.swing.*;

import models.Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.io.IOException;

/**
 * Created by hayden on 5/13/17.
 */
public class PostQuestion extends JPanel {
	
	private JButton btnExit;
	private JButton btnNext;



    public PostQuestion(Game game, ActionListener listener) {
		setLayout(null);
		
		JLabel lblMessage = new JLabel("You now have $" + game.getCurrentMoney());
		lblMessage.setVerticalAlignment(SwingConstants.TOP);
		lblMessage.setFont(new Font("Dialog", Font.BOLD, 25));
		lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
		lblMessage.setForeground(Color.WHITE);
		lblMessage.setBounds(64, 242, 615, 99);
		add(lblMessage);
		
		this.btnExit = new JButton("");
		btnExit.setBounds(634, 23, 79, 26);
        ViewHelper.setupJButton(btnExit, listener);
        add(btnExit);

        JLabel lblBackground = new JLabel("");

        if (game.isNotFinished()) {
            this.btnNext = new JButton("");
            btnNext.setBounds(634, 397, 79, 68);
            ViewHelper.setupJButton(btnNext, listener);
            add(btnNext);

            lblBackground.setIcon(new ImageIcon("images/correct.jpg"));
        } else {

            if (game.getExitReason().equals(Game.LOST_GAME)) {
                lblMessage.setText("Oh no... you walk away with $" + game.getCurrentMoney());
                lblBackground.setIcon(new ImageIcon("images/incorrect.jpg"));
            } else if (game.getExitReason().equals(Game.EXIT_GAME)) {
                lblMessage.setText("Well you walk away with $" + game.getCurrentMoney());
                lblBackground.setIcon(new ImageIcon("images/exit_screen.jpg"));
            } else if (game.getExitReason().equals(Game.WON_GAME)) {
                lblMessage.setText("<html>You just won, Who Wants to Be a Millionaire! <br> You leave here with $"
                        + game.getCurrentMoney() + "</html>");
                lblBackground.setIcon(new ImageIcon("images/won_game.jpg"));
            }
        }

        lblBackground.setBounds(0, 0, 745, 500);
        add(lblBackground);

        //Allows us to play the sound once the view is shown
        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {

            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {

//                //Play appropriate sound for what has happened
//                if (game.isNotFinished()) {
//                    SoundView.playSound(SoundView.CORRECT_SOUND);
//                } else if (!game.isNotFinished()){
//                    if (game.getExitReason().equals(Game.LOST_GAME)) {
//                        SoundView.playSound(SoundView.INCORRECT_SOUND);
//                    } else if (game.getExitReason().equals(Game.EXIT_GAME)) {
//                        SoundView.playSound(SoundView.LEAVING_SOUND);
//                    } else if (game.getExitReason().equals(Game.WON_GAME)) {
//                        SoundView.playSound(SoundView.WIN_SOUND);
//                    }
//                }
            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });
	}
    
    public JButton getBtnExit() {
        return btnExit;
    }

    public JButton getBtnNext() {
        return btnNext;
    }
}
