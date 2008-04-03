package xcon.config;

import xcon.pilot.storage.HashMapStorage;
import xcon.pilot.storage.Storage;
import com.google.inject.AbstractModule;

public class GuiceModule extends AbstractModule {

    public void configure() {
        bind(Storage.class).to(HashMapStorage.class);        
    }
}
