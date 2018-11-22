package xcon.example.templatemethod;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FileDocumentService extends DocumentService {

    private static final FilenameFilter REGULAR_FILE_FILTER = (dir, name) -> Files.isRegularFile(new File(name).toPath());
    private static final FilenameFilter DIRECTORY_FILTER = (dir, name) -> Files.isDirectory(new File(name).toPath());

    @Override
    List<Document> listDocuments(String directoryPath) {
        List<Document> documents = new ArrayList<>();
        for (String filePath : Objects.requireNonNull(new File(directoryPath).list(REGULAR_FILE_FILTER))) {
            readDocument(filePath).ifPresent(documents::add);
        }
        return documents;
    }

    private Optional<Document> readDocument(String filePath) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            return Optional.of(new Document(filePath, content));
        } catch (IOException e) {
            System.err.println("Error reading file: " + filePath);
            return Optional.empty();
        }
    }

    @Override
    List<String> listChildren(String directoryPath) {
        return Arrays.asList(Objects.requireNonNull(new File(directoryPath).list(DIRECTORY_FILTER)));
    }
}
