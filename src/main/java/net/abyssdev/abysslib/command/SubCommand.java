package net.abyssdev.abysslib.command;


import lombok.Getter;
import net.abyssdev.abysslib.command.context.CommandContext;
import net.abyssdev.abysslib.text.message.Message;

import java.util.Set;

@Getter
public abstract class SubCommand {

    private final int required;
    private final Message invalid;

    public SubCommand(final int required) {
        this(required, null);
    }

    public SubCommand(final int required, final Message invalid) {
        this.required = required;
        this.invalid = invalid;
    }

    public abstract Set<String> aliases();
    public abstract void execute(final CommandContext<?> context);

}
