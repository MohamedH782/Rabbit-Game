public class GameScore {
    private int currentScore = 0;
    private int highScore = 0;

    public int getCurrentScore() {
        return currentScore;
    }

    public int getHighScore() {
        return highScore;
    }

    public void updateScore(int points) {
        currentScore += points;

        // Check for new high score
        if (currentScore > highScore) {
            highScore = currentScore;
        }
    }

    public void resetScore() {
        currentScore = 0;
    }
}