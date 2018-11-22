package xcon.example.templatemethod;

import java.util.ArrayList;
import java.util.List;

public abstract class DocumentService {

    public List<Document> listDocumentsRecursively(String path) {
        List<Document> documents = new ArrayList<>(listDocuments(path));
        List<String> children = listChildren(path);
        for (String child : children) {
            documents.addAll(listDocumentsRecursively(child));
        }
        return documents;
    }

    abstract List<Document> listDocuments(String path);

    abstract List<String> listChildren(String path);
}

