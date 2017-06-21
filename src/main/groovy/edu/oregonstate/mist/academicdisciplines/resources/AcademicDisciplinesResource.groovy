package edu.oregonstate.mist.academicdisciplines.resources

import com.codahale.metrics.annotation.Timed
import edu.oregonstate.mist.academicdisciplines.db.AcademicDisciplinesDAO
import edu.oregonstate.mist.api.Resource
import edu.oregonstate.mist.api.jsonapi.ResourceObject
import edu.oregonstate.mist.api.jsonapi.ResultObject
import groovy.transform.TypeChecked
import javax.annotation.security.PermitAll
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.core.UriBuilder

@Path("academic-disciplines")
@Produces(MediaType.APPLICATION_JSON)
@PermitAll
@TypeChecked
class AcademicDisciplinesResource extends Resource {

    private AcademicDisciplinesDAO academicDisciplinesDAO
    private URI endpointUri

    AcademicDisciplinesResource(AcademicDisciplinesDAO academicDisciplinesDAO,
                                URI endpointUri) {
        this.academicDisciplinesDAO = academicDisciplinesDAO
        this.endpointUri = endpointUri
    }

    @Timed
    @GET
    Response getDisciplines(@QueryParam('major') Boolean major,
                            @QueryParam('minor') Boolean minor,
                            @QueryParam('concentration') Boolean concentration,
                            @QueryParam('department') String department) {
        ResultObject resultObject = new ResultObject(
                data: academicDisciplinesDAO.getDisciplines(
                        getDBBooleanFlag(major),
                        getDBBooleanFlag(minor),
                        getDBBooleanFlag(concentration),
                        department
                ))

        resultObject.data.each {
            it['links'] = addSelfLink(it)
        }

        ok(resultObject).build()
    }

    @Timed
    @Path('{id: [0-9a-zA-Z-]+}')
    @GET
    Response getDisciplineById(@PathParam('id') String id) {
        ResourceObject discipline = academicDisciplinesDAO.getDisciplineById(id)

        if (!discipline) {
            return notFound().build()
        }

        discipline['links'] = addSelfLink(discipline)

        ok(new ResultObject(
                data: discipline
        )).build()
    }

    private def addSelfLink(def resultObject) {
        UriBuilder builder = UriBuilder.fromUri(endpointUri).path(this.class).path("{id}")
        [
                'self': builder.build(resultObject['id'])
        ]
    }

    private String getDBBooleanFlag(Boolean aBoolean) {
        if (aBoolean) {
            return "Y"
        } else {
            return null
        }
    }
}
