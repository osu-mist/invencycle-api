package edu.oregonstate.mist.invencycle.health

import com.codahale.metrics.health.HealthCheck
import com.codahale.metrics.health.HealthCheck.Result
import edu.oregonstate.mist.invencycle.db.BikeDAO

class InvencycleHealthCheck extends HealthCheck {

    private final BikeDAO bikeDAO

    public InvencycleHealthCheck(BikeDAO bikeDAO) {
        this.bikeDAO = bikeDAO
    }

    @Override
    protected Result check() throws Exception {
        try {
            bikeDAO.getById(0)
            Result.healthy()
        } catch (Exception e) {
            Result.unhealthy(e.message)
        }
    }
}
