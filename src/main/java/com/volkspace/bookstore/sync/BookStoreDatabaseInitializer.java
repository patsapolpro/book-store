package com.volkspace.bookstore.sync;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookStoreDatabaseInitializer implements InitializingBean {

    @Autowired
    private BookStoreSynchronizer bookStoreSynchronizer;

    @Override
    public void afterPropertiesSet() throws Exception {
        bookStoreSynchronizer.forceSync();
    }
}
