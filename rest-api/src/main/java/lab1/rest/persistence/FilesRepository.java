package lab1.rest.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;


public class FilesRepository {

    private static final String storageFolder = "/Users/dlarralde/personal/files";

    public static void  store(String name, InputStream input) throws IOException {
        Files.copy(input, filePath(name), StandardCopyOption.REPLACE_EXISTING);
    }

    public static byte[] load(String name) throws IOException {
        return Files.readAllBytes(filePath(name));
    }

    private static Path filePath(String name) {
        return Path.of(storageFolder, name);
    }

}
