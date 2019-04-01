import javafx.scene.image.Image;

import java.io.Serializable;

public class Tama implements Serializable {
    private int food, exp, poop, level;
    private String name, fileName;

    private Image look;

    private int maxLevel;

    public Tama() {
        level = 1;
    }

    public Tama(String fileName){
        level = 1;
        this.fileName = fileName;
        this.name = fileName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getPoop() {
        return poop;
    }

    public void setPoop(int poop) {
        this.poop = poop;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     *
     * @return true if Tama leveled up; false otherwise.
     */
    public boolean levelUp() {
        if(exp >= 100 && level != maxLevel) {
            exp = 0;
            level++;
            return true;
        }
        return false;
    }

    public boolean feed() {

        System.out.println(this);

        if(isHungry()) {
            exp +=  5;
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

        System.out.println(this);
    }

    public void update() {
        exp += 10;
        food -= 2;
        poop();
        levelUp();

        getLooks();

        System.out.println(this);
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
        level = 1;

        System.out.println(this);
    }

    public Image getLooks() {
        switch(level) {
            default: return new Image("images/poo.png");
            case 0: return new Image(fileName + "dead.gif");
            case 1: return new Image(fileName + "First.gif");
            case 2: return new Image(fileName + "Second.gif");
        }
    }
}
