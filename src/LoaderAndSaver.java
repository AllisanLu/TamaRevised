import java.io.*;

public class LoaderAndSaver {

    private static final String SAVE_FILE = "saveData.txt";
    private File file;


    public LoaderAndSaver() {
        file = new File(SAVE_FILE);
    }

    public Tamas load() {
        Tamas tamas = null;
        try {
            if(file.createNewFile()) {
                Tama terry = new Tama("images/terry/");
                Tama jerry = new Tama("images/jerry/");
                tamas = new Tamas(terry, jerry, terry);
            }
            else {
                FileInputStream fileInput = new FileInputStream(file);
                ObjectInputStream input = new ObjectInputStream(fileInput);
                tamas = (Tamas) input.readObject();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error processing File");
            System.exit(0);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Could not read Object");
            System.exit(0);
        }

        return tamas;
    }

    public void save(Tamas tamas) {
        try {
            FileOutputStream fileOutput = new FileOutputStream(file);
            ObjectOutputStream output = new ObjectOutputStream(fileOutput);
            output.writeObject(tamas);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
