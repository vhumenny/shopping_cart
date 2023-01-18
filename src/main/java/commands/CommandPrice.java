package commands;

import lombok.Getter;

import java.util.regex.Pattern;

@Getter
public class CommandPrice extends Command {

    //Example: price
    private final Pattern regex = (Pattern.compile("(^price$)"));

    public Boolean matches(String text) {
        return regex.matcher(text).find();
    }
}
