package crudPackage;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public abstract class CRUD {
    public abstract void create(File file, Scanner scanner) throws IOException;

    public abstract void read(File file) throws IOException;

    public abstract void update(File file, Scanner scanner) throws IOException;

    public abstract void delete(File file, Scanner scanner) throws IOException;
}
