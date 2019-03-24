import java.io.*;

public class LoaderAndSaver {

    private static final String SAVE_FILE = "saveData.txt";
    private File file;
    private Tama tama;


    public LoaderAndSaver() {
        file = new File(SAVE_FILE);
    }

    public Tama load() {
        try {
            if(file.createNewFile()) {
                tama = new Tama("images/jerry/");
            }
            else {
                FileInputStream fileInput = new FileInputStream(file);
                ObjectInputStream input = new ObjectInputStream(fileInput);
                tama = (Tama) input.readObject();
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

        return tama;
    }

    public void save(Tama tama) {
        try {
            FileOutputStream fileOutput = new FileOutputStream(file);
            ObjectOutputStream output = new ObjectOutputStream(fileOutput);
            output.writeObject(tama);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
