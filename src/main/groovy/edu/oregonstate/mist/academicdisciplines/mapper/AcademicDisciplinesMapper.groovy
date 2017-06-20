package edu.oregonstate.mist.academicdisciplines.mapper

import edu.oregonstate.mist.academicdisciplines.core.Attributes
import edu.oregonstate.mist.api.jsonapi.ResourceObject
import org.skife.jdbi.v2.StatementContext
import org.skife.jdbi.v2.tweak.ResultSetMapper

import java.sql.ResultSet
import java.sql.SQLException

public class AcademicDisciplinesMapper  implements ResultSetMapper<ResourceObject> {
    public ResourceObject map(int i, ResultSet rs, StatementContext sc) throws SQLException {
        new ResourceObject(
                id: rs.getString("ID"),
                type: "discipline",
                attributes: new Attributes(
                        description: rs.getString("DESCRIPTION"),
                        descriptionLong: rs.getString("DESCRIPTION_LONG"),
                        major: getValidDiscipline(rs.getString("VALID_MAJOR")),
                        minor: getValidDiscipline(rs.getString("VALID_MINOR")),
                        concentration: getValidDiscipline(rs.getString("VALID_CONCENTRATION")),
                        department: rs.getString("DEPARTMENT"),
                        cipcCode: rs.getString("CIPC_CODE")
                )
        )
    }

    private Boolean getValidDiscipline(String data) {
        data == "Y"
    }
}
