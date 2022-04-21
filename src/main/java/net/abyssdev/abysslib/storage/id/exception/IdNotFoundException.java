package net.abyssdev.abysslib.storage.id.exception;

public class IdNotFoundException extends Exception {

    public IdNotFoundException() {
        super("Field annotated with @Id not found!");
    }
}
