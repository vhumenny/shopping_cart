package runner;

import cart.Cart;
import cart.CommandService;
import cart.Commands;
import cart.ConsoleCommandParser;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class TextCommandExecutor {

    /**
     * Method description
     * Method parameters - string line which we get after reading file or getting it from console, instance of
     * class Cart;
     * This methode is made to execute commands which we get after parsing the string of command.
     * First we create instance of class ConsoleCommandParser;
     * next we get Command with the help of method parse()
     * after that we start method execute() which executes specified command.
     * method parse() - parses the received line (from file or from console);
     * method execute() - executes specified command.
     */
    public void executeCommand(String line, Cart cart) {
        ConsoleCommandParser consoleCommandParser = new ConsoleCommandParser(cart);
        Optional<CommandService> optionalCommandService = consoleCommandParser.parse(line);
        if (optionalCommandService.isEmpty()) {
            log.warn("Unknown command in line: {}. Try again, for example \"add beer 5\"", line);
        } else {
            CommandService commandService = optionalCommandService.get();
            Commands command = commandService.getCommand();
            switch (command) {
                case ADD -> commandService.addProductCommand(cart);
                case REMOVE -> commandService.removeProductCommand(cart);
                case DISCOUNT -> commandService.applyDiscountCommand(cart);
                case PRICE -> commandService.priceCommand(cart);
                case FINISH -> commandService.finishCommand(cart);
            }
        }
    }
}

