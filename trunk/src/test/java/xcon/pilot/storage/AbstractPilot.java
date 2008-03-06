package xcon.pilot.storage;

public class AbstractPilot {

    private Storage storage;

    // dependency injection, for example with Spring of Google Guice
    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public void doTest() {

        System.out.println("Contents before: " + storage.dumpContents());

        storage.setCapacity(6);
        storage.store("1", "Adriaan");
        storage.store("2", "Mohamed");
        storage.store("3", "Colette");
        storage.store("4", "Nadia");
        storage.store("5", "Ahmed");
        storage.store("6", "Tamar");
        storage.store("7", "Pipo");

        System.out.println("Contents after: " + storage.dumpContents());
    }

    public static void main(String[] args) {

        System.out.println("Testing file storage implementation");
        AbstractPilot testObject = new AbstractPilot();
        // manual 'injection'
        testObject.setStorage(new FileStorage());
        testObject.doTest();

        System.out.println("Testing HashMap storage implementation");
        // manual 'injection'
        testObject.setStorage(new HashMapStorage());
        testObject.doTest();
    }
}
