package edu.oregonstate.mist.academicdisciplines

import edu.oregonstate.mist.academicdisciplines.db.AcademicDisciplinesDAO
import edu.oregonstate.mist.academicdisciplines.health.AcademicDisciplinesHealthCheck
import edu.oregonstate.mist.academicdisciplines.resources.AcademicDisciplinesResource
import edu.oregonstate.mist.api.Application
import io.dropwizard.jdbi.DBIFactory
import io.dropwizard.setup.Environment
import org.skife.jdbi.v2.DBI

/**
 * Main application class.
 */
class AcademicDisciplinesApplication extends Application<AcademicDisciplinesConfiguration> {
    /**
     * Parses command-line arguments and runs the application.
     *
     * @param configuration
     * @param environment
     */
    @Override
    public void run(AcademicDisciplinesConfiguration configuration, Environment environment) {
        this.setup(configuration, environment)

        DBIFactory factory = new DBIFactory()
        DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "jdbi")
        AcademicDisciplinesDAO academicDisciplinesDAO = jdbi.onDemand(AcademicDisciplinesDAO.class)
        environment.jersey().register(new AcademicDisciplinesResource(academicDisciplinesDAO))

        AcademicDisciplinesHealthCheck healthCheck = new AcademicDisciplinesHealthCheck(
                academicDisciplinesDAO)
        environment.healthChecks().register("academicDisciplinesHealthCheck", healthCheck)
    }

    /**
     * Instantiates the application class with command-line arguments.
     *
     * @param arguments
     * @throws Exception
     */
    public static void main(String[] arguments) throws Exception {
        new AcademicDisciplinesApplication().run(arguments)
    }
}
