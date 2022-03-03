package net.abyssdev.abysslib.command;

import net.abyssdev.abysslib.command.context.CommandContext;
import net.abyssdev.abysslib.command.registration.CommandRegistration;
import net.abyssdev.abysslib.logger.AbyssLogger;
import net.abyssdev.abysslib.text.message.Message;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.logging.Level;

/**
 * @author sysdm-del
 * @param <T>
 */
public abstract class Command<T extends CommandSender> extends org.bukkit.command.Command {

    private int required;
    private Message invalid;
    private final Set<SubCommand> subCommands = new HashSet<>();
    private final Class<T> sender;

    public Command(final String name, final Class<T> sender) {
        super(name);
        this.sender = sender;
    }

    public Command(final String name, final String description, final Class<T> sender) {
        super(name, description, "", Collections.emptyList());
        this.sender = sender;
    }

    public Command(final String name, final String description, final List<String> aliases, final Class<T> sender) {
        super(name, description, "", aliases);
        this.sender = sender;
    }

    public void require(final int required, final Message invalid) {
        this.required = required;
        this.invalid = invalid;
    }

    public void register(final SubCommand... commands) {
        this.subCommands.addAll(Arrays.asList(commands));
    }

    public void require(final int required) {
        this.require(required, null);
    }

    @Override
    public boolean execute(final @NotNull CommandSender commandSender, final @NotNull String s, final @NotNull String[] args) {

        //gotta check to return
        if (!subCommands.isEmpty() && args.length != 0) {
            for (final SubCommand subCommand : this.subCommands) {

                if (!subCommand.aliases().contains(args[0])) {
                    continue;
                }

                final String[] copied = Arrays.copyOfRange(args, 1, args.length);

                if (!this.sender.isInstance(commandSender)) {
                    AbyssLogger.log(Level.INFO, "You cannot do this!");
                    return true;
                }

                if (subCommand.getRequired() != 0 && copied.length < subCommand.getRequired() && subCommand.getInvalid() != null) {
                    subCommand.getInvalid().send(commandSender);
                    return true;
                }

                if (subCommand.getRequired() != 0 && copied.length < subCommand.getRequired()) {
                    return true;
                }

                subCommand.execute(new CommandContext<>((T) commandSender, this.sender, copied));
                return true;
            }

            if (this.invalid != null) {
                this.invalid.send(commandSender);
            }

            return true;
        }

        if (this.required != 0 && args.length < this.required && this.invalid != null) {
            this.invalid.send(commandSender);
            return true;
        }

        if (this.required != 0 && args.length < this.required) {
            return true;
        }

        if (!this.sender.isInstance(commandSender)) {
            AbyssLogger.log(Level.INFO, "You cannot do this!");
            return true;
        }

        this.execute(new CommandContext<>((T) commandSender, this.sender, args));

        return true;
    }

    public abstract void execute(final CommandContext<T> arguments);

    public void register() {
        CommandRegistration.register(this);
    }

    public void unregister() {
        CommandRegistration.unregister(this);
    }
}
