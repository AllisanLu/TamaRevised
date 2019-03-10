public class Tama {
    private int food, exp, health, poop, level;
    private String name, fileName;

    private int maxLevel;

    public Tama() {
        level = 1;
    }
    public Tama(String fileName){
        level = 1;
        name = fileName;
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


}
