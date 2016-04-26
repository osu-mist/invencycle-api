package edu.oregonstate.mist.invencycle

import edu.oregonstate.mist.api.Configuration
import edu.oregonstate.mist.api.Resource
import edu.oregonstate.mist.api.InfoResource
import edu.oregonstate.mist.api.AuthenticatedUser
import edu.oregonstate.mist.api.BasicAuthenticator
import edu.oregonstate.mist.invencycle.db.BikeDAO
import edu.oregonstate.mist.invencycle.resources.BikeResource
import io.dropwizard.Application
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment
import org.skife.jdbi.v2.DBI
import io.dropwizard.jdbi.DBIFactory
import io.dropwizard.auth.AuthFactory
import io.dropwizard.auth.basic.BasicAuthFactory


/**
 * Main application class.
 */
class Invencycle extends Application<InvencycleConfiguration> {
    /**
     * Initializes application bootstrap.
     *
     * @param bootstrap
     */
    @Override
    public void initialize(Bootstrap<InvencycleConfiguration> bootstrap) {}

    /**
     * Parses command-line arguments and runs the application.
     *
     * @param configuration
     * @param environment
     */
    @Override
    public void run(InvencycleConfiguration configuration, Environment environment) {

        final DBIFactory factory = new DBIFactory()

        final DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "jdbi")

        final BikeDAO bikeDAO = jdbi.onDemand(BikeDAO.class)

        environment.jersey().register(new BikeResource(bikeDAO))
    }

    /**
     * Instantiates the application class with command-line arguments.
     *
     * @param arguments
     * @throws Exception
     */
    public static void main(String[] arguments) throws Exception {
        new Invencycle().run(arguments)
    }
}
