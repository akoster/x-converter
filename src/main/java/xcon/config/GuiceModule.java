package xcon.config;

import xcon.datastore.Storage;
import xcon.datastore.impl.NullStorage;

import com.google.inject.AbstractModule;

public class GuiceModule extends AbstractModule {

    public void configure() {
        bind(Storage.class).to(NullStorage.class);        
    }
    
//    to inject:
//  Injector injector = Guice.createInjector(new GuiceModule());
//  injector.injectMembers(testObject);
}
