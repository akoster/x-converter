package xcon.hotel.db;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Subclass of java.io.File which can be injected for testing purposes. No
 * actual files will be accessed, but the underlying functionality of the file
 * system will be mimicked as closely as possible.
 */
public class FileStub extends File {

    /**
     * Global store for pathnames of files physically created through the
     * FileStub API.
     */
    private static Set<String> existingFiles = new HashSet<String>();

    private boolean canExecute;
    private boolean canRead;
    private boolean canWrite;
    private boolean isHidden;
    private long lastModified;
    private long length;

    /**
     * The contents of this directory. If null, then this instance is a file.
     */
    private List<FileStub> directoryContents;

    /**
     * Callback interface for notifications about deletions. This is used to
     * enable a directory to know if it is empty (only an empty directory can be
     * deleted)
     */
    private interface DeletionListener {

        public void fileDeleted(FileStub file);
    }

    /**
     * Parent directory, to be notified when this instance is deleted
     */
    private DeletionListener parent;

    /**
     * Listener for deletions of any files contained in this directory
     */
    private DeletionListener directoryDeletionsListener =
        new DeletionListener() {

            public void fileDeleted(FileStub file) {
                if (directoryContents != null) {
                    directoryContents.remove(file);
                }
            }
        };

    public FileStub(String pathname) {
        super(pathname);
        canRead = true;
        canWrite = true;
        canExecute = true;
    }

    /**
     * Constructor for creating an instance contained in this directory
     */
    private FileStub(String pathname, DeletionListener deletionListener) {
        this(pathname);
        this.parent = deletionListener;
    }

    /**
     * Copy constructor
     */
    private FileStub(String pathname, FileStub source) {
        this(pathname);
        this.canExecute = source.canExecute;
        this.canRead = source.canRead;
        this.canWrite = source.canWrite;
        this.directoryContents = source.directoryContents;
        this.directoryDeletionsListener = source.directoryDeletionsListener;
        this.isHidden = source.isHidden;
        this.lastModified = source.lastModified;
        this.length = source.length;
        this.parent = source.parent;
    }

    @Override
    public File getCanonicalFile() throws IOException {
        return new FileStub(getCanonicalPath(), this);
    }

    @Override
    public String getCanonicalPath() throws IOException {
        // Canonicalization is filesystem specific, so we avoid it
        return getAbsolutePath();
    }

    @Override
    public boolean isDirectory() {
        return exists() && directoryContents != null;
    }

    @Override
    public boolean isFile() {
        return exists() && directoryContents == null;
    }

    @Override
    public boolean createNewFile() throws IOException {
        return createNewExistingFile();
    }

    private boolean createNewExistingFile() {
        if (exists()) {
            return false;
        }
        else {
            System.out.println("Creating " + getAbsolutePath());
            lastModified = System.currentTimeMillis();
            return existingFiles.add(getAbsolutePath());
        }
    }

    @Override
    public boolean exists() {
        return existingFiles.contains(getAbsolutePath());
    }

    @Override
    public boolean mkdirs() {
        if (!exists()) {
            setDirectoryContents(new String[] {});
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean mkdir() {
        return createNewExistingFile();
    }

    @Override
    public boolean delete() {
        if (!isDirectory() || isEmptyDirectory()) {
            if (parent != null) {
                parent.fileDeleted(this);
            }
            directoryContents = null;
            return existingFiles.remove(getAbsolutePath());
        }
        else {
            return false;
        }
    }

    private boolean isEmptyDirectory() {
        return isDirectory()
            && (directoryContents == null || directoryContents.size() == 0);
    }

    @Override
    public void deleteOnExit() {
    // ignore
    }

    @Override
    public boolean renameTo(File dest) {

        existingFiles.remove(getAbsolutePath());
        FileStub newFile = new FileStub(dest.getPath());
        newFile.createNewExistingFile();
        return true;
    }

    @Override
    public String[] list() {
        if (!isDirectory()) {
            return null;
        }
        List<String> fileNames = new ArrayList<String>();
        for (FileStub file : directoryContents) {
            fileNames.add(file.getPath());
        }
        return fileNames.toArray(new String[fileNames.size()]);
    }

    @Override
    public String[] list(FilenameFilter filter) {
        String[] fileNames = list();
        if (fileNames == null || filter == null) {
            return fileNames;
        }
        List<String> accepted = new ArrayList<String>();
        for (String fileName : fileNames) {
            if (filter.accept(this, fileName)) {
                accepted.add(fileName);
            }
        }
        return accepted.toArray(new String[accepted.size()]);
    }

    @Override
    public File[] listFiles() {
        if (!isDirectory()) {
            return null;
        }
        return getDirectoryContents();
    }

    private FileStub[] getDirectoryContents() {
        FileStub[] result = null;
        if (directoryContents != null) {
            result =
                directoryContents.toArray(new FileStub[directoryContents.size()]);
        }
        return result;
    }

    /**
     * Sets the directory contents
     * 
     * @param directoryContents
     *            the directory names residing in this directory
     */
    public void setDirectoryContents(String[] directoryContents) {

        if (directoryContents == null) {
            return;
        }
        List<FileStub> files = new ArrayList<FileStub>();
        for (String fileName : directoryContents) {
            FileStub file = new FileStub(fileName, directoryDeletionsListener);
            file.createNewExistingFile();
            files.add(file);
        }
        this.directoryContents = files;
        createNewExistingFile();
    }

    @Override
    public File[] listFiles(FileFilter filter) {

        if (!isDirectory()) {
            return null;
        }
        if (directoryContents == null || filter == null) {
            return getDirectoryContents();
        }
        List<File> accepted = new ArrayList<File>();
        for (File file : directoryContents) {
            if (filter.accept(file)) {
                accepted.add(file);
            }
        }
        return accepted.toArray(new File[accepted.size()]);
    }

    @Override
    public File[] listFiles(FilenameFilter filter) {

        if (!isDirectory()) {
            return null;
        }
        if (directoryContents == null || filter == null) {
            return getDirectoryContents();
        }
        List<File> accepted = new ArrayList<File>();
        for (File file : directoryContents) {
            if (filter.accept(this, file.getPath())) {
                accepted.add(file);
            }
        }
        return accepted.toArray(new File[accepted.size()]);
    }

    @Override
    public boolean setExecutable(boolean executable, boolean ownerOnly) {
        canExecute = executable;
        return exists();
    }

    @Override
    public boolean setExecutable(boolean executable) {
        return setExecutable(executable, true);
    }

    @Override
    public boolean canExecute() {
        return exists() && canExecute;
    }

    @Override
    public boolean setReadable(boolean readable, boolean ownerOnly) {
        canRead = readable;
        return true;
    }

    @Override
    public boolean setReadable(boolean readable) {
        return setReadable(readable, true);
    }

    @Override
    public boolean canRead() {
        return exists() && canRead;
    }

    @Override
    public boolean setWritable(boolean writable, boolean ownerOnly) {
        canWrite = writable;
        return exists();
    }

    @Override
    public boolean setWritable(boolean writable) {
        return setWritable(writable, true);
    }

    @Override
    public boolean canWrite() {
        return exists() && canWrite;
    }

    @Override
    public boolean setReadOnly() {
        if (exists()) {
            canRead = true;
            canWrite = false;
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Sets the hidden flag for this instance
     * 
     * @param isHidden
     */
    public void setHidden(boolean isHidden) {
        this.isHidden = isHidden;
    }

    @Override
    public boolean isHidden() {
        return isHidden;
    }

    @Override
    public boolean setLastModified(long time) {
        if (!exists()) {
            return false;
        }
        lastModified = time;
        return true;
    }

    @Override
    public long lastModified() {
        return lastModified;
    }

    /**
     * Sets the length in bytes
     * 
     * @param length
     */
    public void setLength(long length) {
        this.length = length;
    }

    @Override
    public long length() {
        return length;
    }
}
