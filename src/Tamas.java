import java.io.Serializable;

public class Tamas implements Serializable {

    private Tama[] tamas;
    private Tama currentTama;

    public Tamas(Tama currentTama, Tama... tamas) {
        this.tamas = tamas;
        this.currentTama = currentTama;
    }

    public Tama getCurrentTama() {
        return currentTama;
    }

    public Tama switchTama(String name) {
        for(Tama tama : tamas) {
            if(tama.getFileName().equals(name)) {
                currentTama = tama;
                return currentTama;
            }
        }

        return currentTama;
    }

    public void updateTamas() {
        for(Tama tama : tamas) {
            tama.update();
        }
    }

    public void feedTamas() {
        for(Tama tama : tamas) {
            tama.feed();
        }
    }

    public void cleanTamas() {
        for(Tama tama : tamas) {
            tama.cleanPoop();
        }
    }
}
