package commands;

import cart.Cart;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.regex.Pattern;
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommandFinish extends Command {

    //Example: finish
    private List<String> arguments;


    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }
    public void execute(Cart cart, List<String> arguments) {
        cart.finish();
    }
}
