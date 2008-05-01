package xcon.config;

import xcon.pilot.storage.Storage;
import xcon.pilot.storage.impl.FileStorage;
import com.google.inject.AbstractModule;

public class GuiceModule extends AbstractModule {

    public void configure() {
        bind(Storage.class).to(FileStorage.class);        
    }
}
