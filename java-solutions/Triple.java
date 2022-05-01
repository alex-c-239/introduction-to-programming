public class Triple {
    private int mainCounter;
    private int lineCounter;
    public IntList list;

    public Triple(int mainCounter, int lineCounter, IntList list) {
        this.mainCounter = mainCounter;
        this.lineCounter = lineCounter;
        this.list = list;
    }

    public int getMainCounter() {
        return mainCounter;
    }

    public int getLineCounter() {
        return lineCounter;
    }

    public void setMainCounter(int newMainCounter) {
        mainCounter = newMainCounter;
    }

    public void setLineCounter(int newLineCounter) {
        lineCounter = newLineCounter;
    }

    public void addCounters() {
        ++mainCounter;
        ++lineCounter;
    }
}
