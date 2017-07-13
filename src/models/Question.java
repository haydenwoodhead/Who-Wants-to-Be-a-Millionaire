package models;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by hayden on 3/3/17.
 */
public class Question {

    private final UUID id;
    private final String questionText;
    private final String questionAnswer;
    private final ArrayList<String> incorrect;
    private final int difficulty;

    public static class Builder {

        /**
         * Builder class pattern from:
         * Effective Java by Joshua Bloch
         * ISBN-13: 978-0321356680
         */

        private UUID id;
        private String questionTitle;
        private String questionAnswer;
        private ArrayList<String> incorrect;
        private int difficulty;

        public Builder() {
            this.incorrect = new ArrayList<String>();
        }

        public Builder id(UUID id) {
            this.id = id;

            return this;
        }

        public Builder id(String id) {
            this.id = UUID.fromString(id);

            return this;
        }

        public Builder questionTitle(String questionTitle) {
            this.questionTitle = questionTitle;

            return this;
        }

        public Builder questionAnswer(String questionAnswer) {
            this.questionAnswer = questionAnswer;

            return this;
        }

        public Builder incorrect(String incorrect) {
            if (this.incorrect.size() < 3) {
                this.incorrect.add(incorrect);
            }

            return this;
        }

        public Builder difficulty(int difficulty) {
            this.difficulty = difficulty;

            return this;
        }

        public Builder difficulty(String difficulty) {
            this.difficulty = Integer.valueOf(difficulty);

            return this;
        }

        public Question build() {
            if (this.id == null) {
                this.id = UUID.randomUUID();
            }

            return new Question(this);
        }
    }

    private Question(Builder builder) {
        this.id = builder.id;
        this.questionText = builder.questionTitle;
        this.questionAnswer = builder.questionAnswer;
        this.incorrect = builder.incorrect;
        this.difficulty = builder.difficulty;
    }

    @Override
    public boolean equals(Object obj) {
        boolean toReturn;

        if (obj instanceof Question) {

            Question q = (Question) obj;

            if (q.getId().equals(this.getId())) {
                toReturn = true;
            } else {
                toReturn = false;
            }

        } else {
            toReturn = false;
        }

        return toReturn;
    }

    public UUID getId() {
        return id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getQuestionAnswer() {
        return questionAnswer;
    }

    public ArrayList<String> getIncorrect() {
        return incorrect;
    }

    public int getDifficulty() {
        return difficulty;
    }
}
