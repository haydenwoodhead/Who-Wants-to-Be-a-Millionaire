package controllers;

import models.*;
import views.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MainViewController extends JFrame implements ActionListener {

    /**
     * MVC implementation of Who Wants to Be a Millionaire UK by Hayden Woodhead
     *
     *
     * Controller class for the game
     * Responds to input, game actions and game state sending data and calling specific view elements when needed
     */

    private static final long serialVersionUID = 1L; // To appease eclipse
    private static final String FRAME_NAME = "Who Wants to Be a Millionaire";

    private RootPanel rootPanel;
    private MainMenu mainMenu;
    private GetUsername currentGetUsername;
    private QuestionView currentQuestionView;
    private PostQuestion currentPostQuestion;
    private HelpScreen helpScreen;

    private Game currentGame;
    private QuestionDatabase questionDatabase;
    private Leaderboard leaderboard;

    public MainViewController(String string, QuestionDatabase questionDatabase, Leaderboard leaderboard) {
		super(string);

        this.questionDatabase = questionDatabase;
        this.leaderboard = leaderboard;

        this.rootPanel = new RootPanel();

        this.mainMenu = new MainMenu();
        rootPanel.addCardToStack(mainMenu, MainMenu.NAME);

        this.helpScreen = new HelpScreen();
        rootPanel.addCardToStack(helpScreen, HelpScreen.NAME);

        getContentPane().add(this.rootPanel);
		setSize(745,525);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);

        // We can setup the actions listeners for main menu and help screen here because we never destroy it
        mainMenu.getBtnPlay().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initializeGame();
                rootPanel.switchToCard(currentGetUsername);
            }
        });

        mainMenu.getBtnExit().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(1); // Die , die, die
            }
        });

        mainMenu.getBtnHighScores().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rootPanel.switchToCard(new LeaderboardView(leaderboard, MainViewController.this));
            }
        });

        mainMenu.getBtnHelp().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rootPanel.showCardInStack(HelpScreen.NAME);
            }
        });

        helpScreen.getBtnExit().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rootPanel.showCardInStack(MainMenu.NAME);
            }
        });

	}

    /**
     * Acts as a router sending the action off to the correct handler
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Cast our event source into a Component, grab the components parent JPanel
        Component source = ((Component) e.getSource()).getParent();

        // Route the event to the correct handler
        if (source instanceof QuestionView) {
            questionViewEventHandler(e);
        } else if (source instanceof GetUsername) {
            usernameEventHandler();
        } else if (source instanceof PostQuestion) {
            postQuestionEventHandler(e);
        } else if (source instanceof LeaderboardView) {
            leaderboardViewEventHandler();
        }
    }

    /**
     * Event handler for actions performed in a Q		setSize(745,525);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);uestionView
     * @param e
     */
    private void questionViewEventHandler(ActionEvent e) {
        boolean correctAnswer = false;
        boolean questionAnswered = false;

        if (e.getSource() == currentQuestionView.getBtnA()) {
            correctAnswer = this.currentGame.getCurrentRound().checkAnswer(Round.CHOICE_A);
            questionAnswered = true;
        } else if (e.getSource() == currentQuestionView.getBtnB()) {
            correctAnswer = this.currentGame.getCurrentRound().checkAnswer(Round.CHOICE_B);
            questionAnswered = true;
        } else if (e.getSource() == currentQuestionView.getBtnC()) {
            correctAnswer = this.currentGame.getCurrentRound().checkAnswer(Round.CHOICE_C);
            questionAnswered = true;
        } else if (e.getSource() == currentQuestionView.getBtnD()) {
            correctAnswer = this.currentGame.getCurrentRound().checkAnswer(Round.CHOICE_D);
            questionAnswered = true;
        } else if (e.getSource() == this.currentQuestionView.getBtn5050()) {
            currentGame.useFiftyFifty();
            currentQuestionView = new QuestionView(currentGame, this);
            rootPanel.switchToCard(currentQuestionView);
//            SoundView.playSound(SoundView.FIFTY_SOUND);
        } else if (e.getSource() == currentQuestionView.getBtnSwitch()) {
            currentGame.useSwitchQuestion();
            currentQuestionView = new QuestionView(currentGame, this);
            rootPanel.switchToCard(currentQuestionView);
//            SoundView.playSound(SoundView.SWITCH_SOUND);
        }  else if (e.getSource() == currentQuestionView.getBtnAsk()) {
            AskTheAudience ask = currentGame.useAskTheAudience();
            currentQuestionView = new AskTheAudienceView(currentGame, ask, this);
            rootPanel.switchToCard(currentQuestionView);
//            SoundView.playSound(SoundView.ASK_SOUND);
        } else if (e.getSource() == currentQuestionView.getBtnExit()) {
            currentGame.finishGame();
            currentPostQuestion = new PostQuestion(currentGame, this);
            rootPanel.switchToCard(currentPostQuestion);
        }

        if (questionAnswered) {
            if (correctAnswer) {
                currentGame.correctAnswer();
            } else {
                currentGame.incorrectAnswer();
            }

            //No matter the outcome generate a new PostQuestion view from the current game and show it
            this.currentPostQuestion = new PostQuestion(currentGame, this);
            rootPanel.switchToCard(currentPostQuestion);
        }
    }

    /**
     * Event handler for when someone clicks next or enter in the
     * username view
     */
    private void usernameEventHandler() {
        String username = currentGetUsername.getTxtUsername().getText();

        try {
            if (username.equals("")) {
                currentGetUsername.showNoUsernameError();
            } else {
                currentGame.setUserName(username);
                rootPanel.switchToCard(currentQuestionView);
            }
        } catch (NullPointerException ex) {
            currentGetUsername.showNoUsernameError();
        }
    }

    /**
     * Event handlers for PostQuestion
     * @param e
     */
    private void postQuestionEventHandler(ActionEvent e) {
        if (e.getSource() == currentPostQuestion.getBtnExit()) {

            // If the game is not finished and the person clicks exit we finish the game
            // and show them a new PostQuestion view which the displays a different background
            if (currentGame.isNotFinished()) {
                currentGame.finishGame();
                currentPostQuestion = new PostQuestion(currentGame, this);
                rootPanel.switchToCard(currentPostQuestion);
            } else { // otherwise either reset the game and bring them back to the main menu or show the leaderboard

                int scorePosOnLeaderboard = leaderboard.addScore(currentGame.generateScore());

                // Write out the score to the database
                WriteOutScore ws = new WriteOutScore(currentGame.generateScore());
                ws.start();

                if (scorePosOnLeaderboard == Leaderboard.INSIGNIFICANT_SCORE_POS) {
                    rootPanel.showCardInStack(MainMenu.NAME);
                } else {
                    LeaderboardView lv = new LeaderboardView(leaderboard, this);
                    lv.highlightScore(scorePosOnLeaderboard);

                    rootPanel.switchToCard(lv);
                }
            }

        } else if (e.getSource() == currentPostQuestion.getBtnNext()) {
            currentGame.initRound();
            this.currentQuestionView = new QuestionView(currentGame, this);
            rootPanel.switchToCard(currentQuestionView);
        }
    }

    private void leaderboardViewEventHandler() {
        rootPanel.showCardInStack(MainMenu.NAME);
    }

    /**
     * Removes old cards from Root Panel and reinitialise a new game ready to play
     */
    private void initializeGame() {
        rootPanel.removeUnneededCards();

        currentGame = new Game(questionDatabase);
        currentGame.initRound();

        currentQuestionView = new QuestionView(currentGame, this);
        currentGetUsername = new GetUsername(this);
    }

    public static void main(String[] args) {
        QuestionDatabase questionDatabase = QuestionDatabase.fromDatabase();
        Leaderboard leaderboard = Leaderboard.fromDatabase();

    	MainViewController mv = new MainViewController(MainViewController.FRAME_NAME, questionDatabase, leaderboard);
    	mv.setVisible(true);
    }
}
