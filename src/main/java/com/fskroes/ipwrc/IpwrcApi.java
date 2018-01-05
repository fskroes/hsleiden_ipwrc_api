package com.fskroes.ipwrc;

import com.fskroes.ipwrc.dao.EmployeeDAO;
import com.fskroes.ipwrc.handler.ApiUnauthorizedHandler;
import com.fskroes.ipwrc.model.EmployeeModel;
import com.fskroes.ipwrc.service.UserAuthenticationService;
import com.google.inject.Module;
import com.hubspot.dropwizard.guice.GuiceBundle;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.ScanningHibernateBundle;
import io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

public class IpwrcApi extends Application<RestApiConfig> {

    private RestApiGuiceModule module;
    private GuiceBundle guiceBundle;

    @Override
    public void initialize(Bootstrap<RestApiConfig> bootstrap) {
        super.initialize(bootstrap);

        module =  new RestApiGuiceModule();
        guiceBundle = createGuiceBundle(RestApiConfig.class, module);

        bootstrap.addBundle(guiceBundle);

    }

    private GuiceBundle createGuiceBundle(Class<RestApiConfig> configurationClass, Module module) {
        GuiceBundle.Builder guiceBuilder = GuiceBundle.<RestApiConfig>newBuilder()
                .addModule(module)
                .enableAutoConfig("com.fskroes.ipwrc")
                .setConfigClass(configurationClass);

        return guiceBuilder.build();
    }

    private void setupAuthentication(Environment environment) {
        EmployeeDAO employeeDAO = guiceBundle.getInjector().getInstance(EmployeeDAO.class);

        UserAuthenticationService authenticationService = new UnitOfWorkAwareProxyFactory(module.getHibernate()).create(UserAuthenticationService.class, EmployeeDAO.class, employeeDAO);

        ApiUnauthorizedHandler unauthorizedHandler = guiceBundle.getInjector().getInstance(ApiUnauthorizedHandler.class);

        environment.jersey().register(new AuthDynamicFeature(
                new BasicCredentialAuthFilter.Builder<EmployeeModel>()
                        .setAuthenticator(authenticationService)
                        .setAuthorizer(authenticationService)
                        .setRealm("SUPER SECRET STUFF")
                        .setUnauthorizedHandler(unauthorizedHandler)
                        .buildAuthFilter())
        );

        environment.jersey().register(RolesAllowedDynamicFeature.class);
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(EmployeeModel.class));
    }

    @Override
    public String getName() {
        return "IpwrcApi";
    }

    @Override
    public void run(RestApiConfig restApiConfig, Environment environment) throws Exception {
        final FilterRegistration.Dynamic cors = environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin, Authorization");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

        setupAuthentication(environment);
    }

    // ---

    public static void main(String[] args) throws Exception {
        new IpwrcApi().run(args);
    }
}
