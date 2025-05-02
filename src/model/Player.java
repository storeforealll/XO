package model;

public class Player {
    private char symbol;
    private String name;
    private int score;

    public Player(char symbol, String name) {
        this.symbol = symbol;
        this.name = name;
        this.score = 0;
    }

    public char getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void incrementScore() {
        score++;
    }

    public void resetScore() {
        score = 0;
    }
}