package info.istamendil.notebook;

import info.istamendil.notebook.data.Db;
import info.istamendil.notebook.data.DbException;
import info.istamendil.notebook.data.FileDb;
import info.istamendil.notebook.utils.*;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * Note Book. UserInteractor and DB modules.
 *
 * @author Alexander Ferenets (Istamendil) <ist.kazan@gmail.com>
 * <p>
 * Code for studying purposes. Programming course. Kazan Federal University, ITIS.
 * http://study.istamendil.info/
 */
public class App extends Application {

    private static final String DB = "tmp/db.txt";
    private static final String PUNCH_CARD = "tmp/card.txt";

    protected UserInteract terminal;
    protected Db db;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        App app = new App(args);
    }

    public App(String[] args) {
        super(args);
    }

    @Override
    public void init() {
        try {
            this.terminal = new PunchedCardUserInteract(Paths.get(App.PUNCH_CARD));
            this.db = new FileDb(App.DB);
        } catch (UserInteractException ex) {
            System.out.println("Couldn't start application due error:");
            System.err.println(ex.getMessage());
            System.exit(1);
        }
    }

    @Override
    public void start() {
        try {
            String command;
            while ((command = terminal.readCommand()) != null) {
                String[] currCommand = command.split(" ");
                switch (currCommand[0]) {
                    case "readAll":
                        terminal.print(Arrays.toString(db.findAll()));
                        break;
                    case "save":
                        if ((command = terminal.readCommand()) != null) {
                            db.save(command);
                        }
                        break;
                    case "removeAll":
                        db.removeAll();
                        break;
                    case "remove":
                        String index = currCommand[1];
                        db.remove(Integer.parseInt(index));
                        break;
                    default:
                        terminal.print("Unknown command");
                }
            }
        } catch (UserInteractReadException ex) {
            System.out.println("Can't read user input due error:");
            System.err.println(ex.getMessage());
            System.exit(1);
        } catch (UserInteractWriteException ex) {
            System.out.println("Can't print data to user due error:");
            System.err.println(ex.getMessage());
            System.exit(1);
        } catch (DbException ex) {
            System.err.println(ex.getMessage());
            System.exit(1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
