package commands;

import cart.Cart;
import cart.CartCommandParser;
import lombok.Getter;

import java.util.List;
import java.util.regex.Pattern;

import static cart.CartCommandParser.createRegExValues;

@Getter
public class CommandAdd extends Command {

    // Example: add bear 5, add cola 1, add soap 2 -> structure add [product name][product quantity]
    //pattern of regular expression we divide in three groups (group(1))(group(2))(group(3))
    //     * (group(1)) - command name: add
    //     * (group(2)) - it is a product name
    //     * (group(3)) - it is product quantity
    private final Pattern regex = (Pattern.compile("^(add) (" +
            createRegExValues(CartCommandParser.getProducts()) + ") ([0-9]+)"));

    //method which finds out if Pattern matches string
    public Boolean matches(String text) {
        return regex.matcher(text).find();
    }

    public void execute(Cart cart, List<String> arguments) {
        cart.add(arguments.get(1), Integer.parseInt(arguments.get(2)));
    }
}
