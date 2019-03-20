import java.io.Serializable;

public class Tama implements Serializable {
    private int food, exp, poop, level;
    private String name, fileName;

    private int maxLevel;

    public Tama() {
        level = 1;
    }

    public Tama(String fileName){
        level = 1;
        this.fileName = fileName;
    }

    /**
     *
     * @return true if Tama leveled up; false otherwise.
     */
    public boolean levelUp() {
        if(exp == 100 && level != maxLevel) {
            exp = 0;
            level++;
            return true;
        }
        return false;
    }

    public boolean feed() {
        System.out.println("Hello");
        if(isHungry()) {
            food += 3;
            return true;
        }
        return false;
    }

    private boolean isHungry() {
        return food < 5;
    }

    public void poop() {
        poop++;
    }

    public void cleanPoop() {
        poop = 0;
    }

    public void update() {
        exp += 10;
        food -= 2;
        poop();
        levelUp();
    }

    @Override
    public String toString() {
        return "File: " + fileName + "\n\t" +
                "Food: " + food + "\n\t" +
                "Exp: " + exp + "\n\t" +
                "Poop: " + poop + "\n\t" +
                "level: "+ level + "\n\t";
    }

    public void reset() {
        food = 8;
        exp = 0;
        cleanPoop();
        level = 0;
    }
}
