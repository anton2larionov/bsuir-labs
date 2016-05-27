package calculus;

/**
 * Используется для хранения результатов численного дифференцирования
 */
public class DiffResult {
    /**
     * первая производная
     */
    private double firstDer;

    /**
     * вторая производная
     */
    private double secondDer;

    public double getFirstDer() {
        return firstDer;
    }

    public void setFirstDer(double firstDer) {
        this.firstDer = firstDer;
    }

    public double getSecondDer() {
        return secondDer;
    }

    public void setSecondDer(double secondDer) {
        this.secondDer = secondDer;
    }

}