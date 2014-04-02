package xcon.project.hotel.db;


import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;

/**
 * Application which tests FileStub vs File
 */
public class FileStubTester {

    /**
     * A wrapper for the test arguments
     */
    private static class TestArgs {

        String path;
        String[] directoryContents;
        long freeSpace;
        long totalSpace;
        long usableSpace;

        TestArgs(String path, String[] directoryContents, long freeSpace, long totalSpace, long usableSpace) {
            this.path = path;
            this.directoryContents = directoryContents;
            this.freeSpace = freeSpace;
            this.totalSpace = totalSpace;
            this.usableSpace = usableSpace;
        }
    }

    public static void main(String[] args) throws IOException {

        File newStubFile = new FileStub("/newfile.txt");
        File newRealFile = new File("/newfile.txt");

        TestArgs directoryArgs = createTestArgs("/testdir");
        directoryArgs.directoryContents = new String[] { "testfile1", "testfile2", "testfile3" };
        runTests(createStubFile(directoryArgs), newStubFile, directoryArgs);
        runTests(createRealFile(directoryArgs), newRealFile, directoryArgs);

        TestArgs fileArgs = createTestArgs("/testfile.txt");
        runTests(createStubFile(fileArgs), newStubFile, fileArgs);
        runTests(createRealFile(fileArgs), newRealFile, fileArgs);
    }

    private static File createStubFile(TestArgs args) {

        FileStub file = new FileStub(args.path);
        file.setDirectoryContents(args.directoryContents);
        file.setHidden(false);
        file.setLength(0L);
        return file;
    }

    private static File createRealFile(TestArgs args) throws IOException {

        File file = new File(args.path);
        file.delete();
        if (args.directoryContents != null) {
            file.mkdirs();
            for (String fileName : args.directoryContents) {
                File dirContent = new File(file, fileName);
                dirContent.createNewFile();
            }
        }
        return file;
    }

    private static TestArgs createTestArgs(String pathname) throws IOException {

        File[] rootFiles = File.listRoots();
        String[] roots = new String[rootFiles.length];
        int i = 0;
        for (File rootFile : rootFiles) {
            roots[i++] = rootFile.getCanonicalPath();
        }
        File partition = new File("/");
        long freeSpace = partition.getFreeSpace();
        long totalSpace = partition.getTotalSpace();
        long usableSpace = partition.getUsableSpace();
        return new TestArgs(pathname, null, freeSpace, totalSpace, usableSpace);
    }

    private static void runTests(File file, File newFile, TestArgs args) throws IOException {

        System.out.println("Testing" + ((file instanceof FileStub) ? " stub" : " real")
                + ((file.isDirectory()) ? " directory " : " file ") + file.getCanonicalPath());

        boolean isWindows = System.getProperty("os.name").toLowerCase().contains("windows");
        File upperCanonicalFile = new File(args.path.toUpperCase()).getCanonicalFile();
        File canonicalFile = file.getCanonicalFile();
        if (isWindows) {
            check(canonicalFile.compareTo(upperCanonicalFile) == 0, "On Windows case should not matter: "
                    + canonicalFile + " , " + upperCanonicalFile);
        } else {
            check(canonicalFile.compareTo(upperCanonicalFile) != 0, "On UNIX case should matter: " + canonicalFile
                    + " , " + upperCanonicalFile);
        }

        check(!file.isHidden(), "file should not be hidden");

        boolean isDirectory = args.directoryContents != null;
        if (isDirectory) {
            testDirectory(file, args);
        } else {
            testFile(file, newFile);
        }
    }

    private static void testFile(File file, File newFile) throws IOException {

        check(!file.isDirectory(), "should not be a directory");
        check(!file.isFile(), "should not be a file (does not exist yet)");
        check(!file.exists(), "should not exist");
        check(!file.setLastModified(1L), "should not be possible to change modification timestamp");
        check(!file.setReadOnly(), "should not be possible to set non existent file readonly");
        check(!file.canRead(), "file should not be readable");
        check(!file.canWrite(), "file should not be writeable");
        check(!file.canExecute(), "file should not be executable");

        check(file.mkdirs(), "mkdirs should succeed");
        equal(0, file.list().length, "directory should be empty");
        check(file.delete(), "deletion should succeed");

        equal(null, file.list(), "should return null");
        equal(null, file.listFiles(), "should return null");
        equal(null, file.list(null), "should return null");
        equal(null, file.listFiles((FilenameFilter) null), "should return null");
        equal(null, file.listFiles((FileFilter) null), "should return null");

        equal(file.getFreeSpace(), 0L, "file does not exist, free space should be zero");
        equal(file.getTotalSpace(), 0L, "file does not exist, total space should be zero");
        equal(file.getUsableSpace(), 0L, "file does not exist, usable space should be zero");

        check(!file.setWritable(true), "should not be possible to set non existent file writable");
        check(!file.canWrite(), "non existent file should not be writable");

        check(!file.delete(), "deletion should fail");
        check(file.createNewFile(), "creation should succeed");
        check(!file.createNewFile(), "repeated creation should fail");
        check(file.exists(), "file should exist");
        check(file.isFile(), "should be a file");

        check(file.lastModified() > 0, "file should be modified");
        long timeStamp = System.currentTimeMillis() - 100000;
        check(file.setLastModified(timeStamp), "should be possible to change modification timestmamp");
        equal(timeStamp, file.lastModified(), "modification timestamp should match");
        equal(0L, file.length(), "file length should be zero");

        check(file.canWrite(), "file should be writable");
        check(file.setWritable(false), "should be possible to set file non-writable");
        check(!file.canWrite(), "file should be non-writable");
        check(file.setWritable(true), "should be possible to set file writable");
        check(file.canWrite(), "file should be writable");

        check(file.canRead(), "file should be readable");
        file.setReadable(true); // succes depends on filesystem

        check(file.canExecute(), "file should be executable");
        file.setExecutable(true); // succes depends on filesystem

        check(file.setReadOnly(), "should be possible to set non existent file readonly");
        check(!file.canWrite(), "file should be non-writable");

        newFile.deleteOnExit();

        check(file.renameTo(newFile), "renaming should succeed");
        check(newFile.exists(), "new file should exist");
        check(!file.exists(), "old file should not exist");

        check(newFile.delete(), "deletion should succeed");
        check(!newFile.exists(), "file should not exist");
        check(!newFile.delete(), "repeated deletion should fail");
    }

    private static void testDirectory(File file, TestArgs args) {
        check(file.exists(), "directory should exist");
        check(file.isDirectory(), "should be directory");
        check(!file.delete(), "delete should fail");
        check(!file.mkdirs(), "mkdirs should fail");
        check(!file.mkdir(), "mkdir should fail");

        equal(file.getFreeSpace(), args.freeSpace, "directory exists, free space should match");
        equal(file.getTotalSpace(), args.totalSpace, "directory exists, total space should match");
        equal(file.getUsableSpace(), args.usableSpace, "directory exists, usable space should match");

        int fileCount = args.directoryContents.length;
        equal(fileCount, file.list().length, "number of files in directory should match");
        equal(fileCount, file.list(null).length, "null filenamefilter should return all files");
        equal(fileCount, file.listFiles((FilenameFilter) null).length, "null filenamefilter should return all files");
        equal(fileCount, file.listFiles((FileFilter) null).length, "null filefilter should return all files");

        final String lastFileNameInDirectory = args.directoryContents[fileCount - 1];
        FilenameFilter findLastFileName = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.equals(lastFileNameInDirectory);
            }
        };
        equal(lastFileNameInDirectory, file.list(findLastFileName)[0], "should return last fileName in directory");
        equal(lastFileNameInDirectory, file.listFiles(findLastFileName)[0].getName(),
                "should return last file in directory");

        FileFilter findLastFile = new FileFilter() {
            public boolean accept(File pathname) {
                return pathname.getName().equals(lastFileNameInDirectory);
            }
        };
        equal(lastFileNameInDirectory, file.listFiles(findLastFile)[0].getName(),
                "should return last file in directory");

        for (File dirFile : file.listFiles()) {
            check(dirFile.delete(), "deleting file from directory should succeed: " + dirFile);
            check(!dirFile.exists(), "file should no longer exist in directory");
        }

        equal(file.list().length, 0, "directory should be empty");
        check(file.delete(), "directory deletion should succeed");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    private static void equal(Object a, Object b, String message) {
        boolean bothNull = (a == null) && (b == null);
        if (!bothNull && !a.equals(b)) {
            throw new RuntimeException(message + " : " + a + " should equal " + b);
        }
    }
}
