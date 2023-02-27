package runner;

import cart.Cart;
import config.PropertyUtils;
import storage.StorageDataBase;
import storage.StorageDataBaseByHibernate;
import storage.StorageDataBaseByJDBC;

import java.util.List;
import java.util.Scanner;

public class DataBaseModeRunner implements ModeRunner {
    private final List<StorageDataBase> dataBaseList =
            List.of(new StorageDataBaseByHibernate(), new StorageDataBaseByJDBC());

    /**
     * Method description
     * starts reading and executing commands line by line from console;
     * first we output message to console with method - showTooltipWithCommands().
     * next we create instance of Class TextCommandExecutor
     * after that we create instance of class Cart;
     * next we start endless cycle in which we receive read commands from console;
     * each line we pass to method executeCommand() to execute each command;
     * if line equals "finish" - we end this cycle while().
     * method executeCommand() - executes command in line from file;
     * method showTooltipWithCommands() - outputs message to console with tips.
     */
    @Override
    public void start() {
        System.out.println("Starting DataBase mode.");
        ModeRunner.showTooltipWithCommands();
        String connectionType = PropertyUtils.get("db.connection_type");
        Cart cart;
        for (StorageDataBase currentStorage : dataBaseList) {
            if (currentStorage.getConnectionType().equals(connectionType)) {
                cart = new Cart(currentStorage);
            } else {

            }
        }
        TextCommandExecutor textCommandExecutor = new TextCommandExecutor();
        while (true) {
            String line = new Scanner(System.in).nextLine();
            textCommandExecutor.executeCommand(line, cart);
            if (line.equals("finish")) return;
        }
    }
}
