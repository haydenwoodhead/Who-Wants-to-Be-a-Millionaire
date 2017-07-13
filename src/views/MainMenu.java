package views;

import javax.sound.sampled.*;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.io.IOException;

public class MainMenu extends JPanel{

    public static final String NAME = "main menu";

	private JButton btnPlay;
	private JButton btnHighScores;
	private JButton btnHelp;
	private JLabel lblBackground;
	private JButton btnExit;

    private Clip sound;

	public MainMenu() {
		setLayout(null);
		
		this.btnPlay = new JButton("");
		btnPlay.setBounds(440, 108, 251, 66);
		btnPlay.setOpaque(true);
		btnPlay.setContentAreaFilled(false);
		btnPlay.setBorderPainted(false);
		add(btnPlay);
		
		this.btnHighScores = new JButton("");
		btnHighScores.setBounds(440, 305, 251, 66);
		btnHighScores.setOpaque(true);
		btnHighScores.setContentAreaFilled(false);
		btnHighScores.setBorderPainted(false);
		add(btnHighScores);
		
		this.btnHelp = new JButton("");
		btnHelp.setBounds(440, 199, 251, 75);
		btnHelp.setOpaque(true);
		btnHelp.setContentAreaFilled(false);
		btnHelp.setBorderPainted(false);
		add(btnHelp);

		btnExit = new JButton("");
		btnExit.setBounds(563, 420, 145, 46);
		ViewHelper.setupJButton(btnExit);
		add(btnExit);
		
		lblBackground = new JLabel("");
		lblBackground.setBounds(0, 0, 745, 500);
		lblBackground.setIcon(new ImageIcon("images/main_menu.jpg"));
		add(lblBackground);

        // Allows us to start and stop the sound on card shown
        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {

            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {
//                playSound();
            }

            @Override
            public void componentHidden(ComponentEvent e) {
//                sound.stop();
            }
        });

//        playSound(); // play the sound
	}

//	public void playSound() {
//        String soundName = "sounds/intro.wav";
//        AudioInputStream audioInputStream = null;
//
//        try {
//            audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
//            sound = AudioSystem.getClip();
//            sound.open(audioInputStream);
//            sound.start();
//        } catch (UnsupportedAudioFileException e1) {
//            e1.printStackTrace();
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        } catch (LineUnavailableException e1) {
//            e1.printStackTrace();
//        }
//
//    }
//

	public JButton getBtnPlay() {
		return btnPlay;
	}

	public JButton getBtnHighScores() {
		return btnHighScores;
	}

	public JButton getBtnHelp() {
		return btnHelp;
	}

	public JButton getBtnExit() {
		return btnExit;
	}


	
}
