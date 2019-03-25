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

        getLooks();
    }

//    public void hunger(){
//        new Thread( () -> {
//            double begin = System.currentTimeMillis();
//            while(true){
//                double current = System.currentTimeMillis();
//                if((current - begin) >= 60000 && food > 0) {
//                    food--;
//                    if(food == 0)
//                        System.out.println("IM HUNGRYYY");
//                    begin = System.currentTimeMillis();
//                }
//            }
//        }
//        ).start();
//    }

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

    public Image getLooks() {
        switch(level) {
            default: return new Image("images/poo.png");
            case 0: return new Image(fileName + "dead.gif");
            case 1: return new Image(fileName + "First.gif");
            case 2: return new Image(fileName + "Second.gif");
        }
    }

}
