import javafx.scene.image.Image;

import java.io.Serializable;

public class Tama implements Serializable {
    private int food, exp, poop, level, health, maxHealth;
    private String fileName;

    private Image look;

    private int maxLevel;

    public Tama() {
        level = 1;
    }

    public Tama(String fileName) {
        level = 1;
        this.fileName = fileName;
        food = 5;
        health = 10;
        maxHealth = 10;
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

    public double getPercentHealth() {
        return (double) health /maxHealth;
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

    private boolean isStarving() {
        return food < 0;
    }

    private boolean isDead() { return health <= 0; }

    public void cleanPoop() {
        poop = 0;

        System.out.println(this);
    }

    private void updateLevel() {
        exp += 10;
        if(exp >= 100 && maxLevel != level) {
            exp = 0;
            level++;
        }
    }

    private void uptick() {
        food -= 2;
        exp += 10;
        poop++;

        if(isStarving()) {
            health -= 4;
        }
        else if(!isHungry() && health != maxHealth) {
            health += 2;
        }

        if(isDead()) {
            level = 0;
        }
    }

    public void update() {
        uptick();

        updateLevel();
        updateLooks();

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

    public Image updateLooks() {
        switch(level) {
            default: return new Image("images/poo.png");
            case 0: return new Image(fileName + "dead.gif");
            case 1: return new Image(fileName + "First.gif");
            case 2: return new Image(fileName + "Second.gif");
        }
    }
}
