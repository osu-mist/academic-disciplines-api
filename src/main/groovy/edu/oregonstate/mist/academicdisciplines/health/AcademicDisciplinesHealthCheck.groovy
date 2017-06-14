package edu.oregonstate.mist.academicdisciplines.health

import com.codahale.metrics.health.HealthCheck
import com.codahale.metrics.health.HealthCheck.Result
import edu.oregonstate.mist.academicdisciplines.db.AcademicDisciplinesDAO

class AcademicDisciplinesHealthCheck extends HealthCheck {
    private AcademicDisciplinesDAO academicDisciplinesDAO

    AcademicDisciplinesHealthCheck(AcademicDisciplinesDAO academicDisciplinesDAO) {
        this.academicDisciplinesDAO = academicDisciplinesDAO
    }

    @Override
    protected Result check() {
        try {
            String status = academicDisciplinesDAO.checkHealth()

            if (status != null) {
                return Result.healthy()
            }
            Result.unhealthy("status: ${status}")
        } catch(Exception e) {
            Result.unhealthy(e.message)
        }
    }
}
