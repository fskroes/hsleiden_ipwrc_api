package com.fskroes.ipwrc;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.ScanningHibernateBundle;
import io.dropwizard.setup.Environment;
import org.hibernate.SessionFactory;

public class RestApiGuiceModule extends AbstractModule {

    @Override
    protected void configure() {

    }

    private final HibernateBundle<RestApiConfig> hibernate = new ScanningHibernateBundle<RestApiConfig>("com.fskroes.ipwrc") {
        @Override
        public DataSourceFactory getDataSourceFactory(RestApiConfig configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    @Provides
    public SessionFactory provideSessionFactory(RestApiConfig configuration, Environment environment) {
        SessionFactory sf = hibernate.getSessionFactory();
        if (sf == null) {
            try {
                hibernate.run(configuration, environment);
                return hibernate.getSessionFactory();
            } catch (Exception e) {
                System.out.println("Unable to run hibernatebundle");
                return null;
            }
        } else {
            return sf;
        }
    }

    public HibernateBundle<RestApiConfig> getHibernate() {
        return hibernate;
    }
}
