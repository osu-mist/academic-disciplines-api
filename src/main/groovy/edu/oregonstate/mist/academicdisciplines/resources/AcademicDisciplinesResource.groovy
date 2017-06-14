package edu.oregonstate.mist.academicdisciplines.resources

import com.codahale.metrics.annotation.Timed
import edu.oregonstate.mist.academicdisciplines.db.AcademicDisciplinesDAO
import edu.oregonstate.mist.api.Resource
import edu.oregonstate.mist.api.jsonapi.ResourceObject
import edu.oregonstate.mist.api.jsonapi.ResultObject
import groovy.transform.TypeChecked
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.annotation.security.PermitAll
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("academic-disciplines")
@Produces(MediaType.APPLICATION_JSON)
@PermitAll
@TypeChecked
class AcademicDisciplinesResource extends Resource {
    Logger logger = LoggerFactory.getLogger(AcademicDisciplinesResource.class)

    private AcademicDisciplinesDAO academicDisciplinesDAO

    AcademicDisciplinesResource(AcademicDisciplinesDAO academicDisciplinesDAO) {
        this.academicDisciplinesDAO = academicDisciplinesDAO
    }

    @Timed
    @GET
    Response getDisciplines(@QueryParam('major') Boolean major,
                            @QueryParam('minor') Boolean minor,
                            @QueryParam('concentration') Boolean concentration,
                            @QueryParam('department') String department) {
        ok(new ResultObject(
                data: academicDisciplinesDAO.getDisciplines(
                        getDBBooleanFlag(major),
                        getDBBooleanFlag(minor),
                        getDBBooleanFlag(concentration),
                        department
                )
        )).build()
    }

    @Timed
    @Path('{id: [0-9a-zA-Z-]+}')
    @GET
    Response getDisciplineById(@PathParam('id') String id) {
        ResourceObject discipline = academicDisciplinesDAO.getDisciplineById(id)

        if (!discipline) {
            return notFound().build()
        }

        ok(new ResultObject(
                data: discipline
        )).build()
    }

    private String getDBBooleanFlag(Boolean aBoolean) {
        if (aBoolean) {
            return "Y"
        } else {
            return null
        }
    }
}
