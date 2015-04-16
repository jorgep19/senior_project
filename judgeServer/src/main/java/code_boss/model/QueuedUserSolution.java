package code_boss.model;

public class QueuedUserSolution {
    private UserSolution solution;
    private int timeWaited;

    public QueuedUserSolution(UserSolution solution) {
        this.solution = solution;
        this.timeWaited = 0;
    }

    public UserSolution getSolution() {
        return solution;
    }

    public int getTimeWaited() {
        return timeWaited;
    }

    public void incWaitedTime() {
        ++timeWaited;
    }
}
