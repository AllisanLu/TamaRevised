import javafx.scene.image.Image;

import java.io.Serializable;
import java.util.Arrays;

public class Tama implements Serializable {
    private int food, exp, poop, level, health, maxHealth;
    private boolean[] visiblePoop;
    private String fileName;

    public static int MAX_POOPS = 20;

    private int maxLevel;

    public Tama(String fileName) {
        level = 1;
        this.fileName = fileName;
        food = 5;
        health = 10;
        maxHealth = 10;
        visiblePoop = new boolean[MAX_POOPS];
    }

    public boolean[] getVisiblePoop() {
        return visiblePoop;
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
            poop++;

            int randomPoop = (int) (Math.random() * MAX_POOPS);
            while(visiblePoop[randomPoop] == true) {
                randomPoop = (int) (Math.random() * MAX_POOPS);
            }

            visiblePoop[randomPoop] = true;

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

    private boolean isDirty() {
        return poop >= 5;
    }

    private void die() {
        level = 0;
        health = 0;
        exp = 0;
    }

    public void cleanPoop() {
        poop = 0;
        Arrays.fill(visiblePoop, false);
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

        if(isStarving() && !isDead()) {
            health -= 4;
        }
        else if(!isHungry() && health != maxHealth) {
            health += 2;
        }

        if(isDead()) {
            die();
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
                "Health: " + health + "\n\t" +
                "Food: " + food + "\n\t" +
                "Exp: " + exp + "\n\t" +
                "Poop: " + poop + "\n\t" +
                "level: "+ level + "\n\t";
    }

    public void reset() {
        food = 8;
        exp = 0;
        health = maxHealth;
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
