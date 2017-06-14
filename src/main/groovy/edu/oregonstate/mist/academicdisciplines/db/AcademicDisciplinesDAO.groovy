package edu.oregonstate.mist.academicdisciplines.db

import edu.oregonstate.mist.academicdisciplines.mapper.AcademicDisciplinesMapper
import edu.oregonstate.mist.api.jsonapi.ResourceObject
import edu.oregonstate.mist.contrib.AbstractAcademicDisciplinesDAO
import org.skife.jdbi.v2.sqlobject.Bind
import org.skife.jdbi.v2.sqlobject.SqlQuery
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper

@RegisterMapper(AcademicDisciplinesMapper)
public interface AcademicDisciplinesDAO extends Closeable {

    @SqlQuery("SELECT 1 FROM dual")
    Integer checkHealth()

    @SqlQuery(AbstractAcademicDisciplinesDAO.getDisciplines)
    List<ResourceObject> getDisciplines(@Bind("major") String major,
                                  @Bind("minor") String minor,
                                  @Bind("concentration") String concentration,
                                  @Bind("department") String department)

    @SqlQuery(AbstractAcademicDisciplinesDAO.getDisciplineById)
    ResourceObject getDisciplineById(@Bind("id") String id)

    @Override
    void close()
}
