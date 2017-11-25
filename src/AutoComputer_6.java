public class AutoComputer_6 {
}

interface IBaseComputer {
    void tempCheck();

    void oilCheck();

    void zoomerCheck();
}

interface IEhnancedComputer {
    void ABS();
}

class BaseComputer implements IBaseComputer {
    int temp;
    final int tempThreshold = 120;
    boolean tempAlarm;

    boolean oil;
    final int oilThreshold = 5; // random number; unspecified in the requirements
    boolean oilAlarm;

    boolean doorOpened;
    boolean lightsOn;
    boolean zoomerAlarm;

    public BaseComputer(int temp) {
        this.temp = temp;
    }

    public void tempCheck() {
        this.tempAlarm = temp >= this.tempThreshold;
    }

    public void oilCheck() {
        this.oilAlarm = this.oil;
    }

    public void zoomerCheck() {
        this.zoomerAlarm = this.doorOpened && this.lightsOn;
    }
}


class EnhancedComputer extends BaseComputer implements IEhnancedComputer {
    int breaks;
    boolean slips;

    public EnhancedComputer(int temp) {
        super(temp);
    }

    public void ABS() {
        int originalBreaks = this.breaks;
        this.breaks = (int) (0.3 * this.breaks);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }

        this.slips = false;
        this.breaks = originalBreaks;
    }
}