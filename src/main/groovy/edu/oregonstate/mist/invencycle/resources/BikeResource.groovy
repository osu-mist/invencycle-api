package edu.oregonstate.mist.invencycle.resources

import edu.oregonstate.mist.api.Resource
import edu.oregonstate.mist.invencycle.core.Bike
import edu.oregonstate.mist.invencycle.core.Sample
import edu.oregonstate.mist.api.AuthenticatedUser
import edu.oregonstate.mist.invencycle.db.BikeDAO
import edu.oregonstate.mist.api.Resource
import edu.oregonstate.mist.api.Error
import edu.oregonstate.mist.invencycle.core.ErrorPOJO
import io.dropwizard.auth.Auth
import io.dropwizard.jersey.params.IntParam
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.Consumes
import javax.ws.rs.core.Response
import javax.ws.rs.core.Response.ResponseBuilder
import javax.ws.rs.core.MediaType

/**
 *Bike resource class
 */

@Path('/bikes/')
@Produces(MediaType.APPLICATION_JSON)
class BikeResource extends Resource {

    private final BikeDAO bikeDAO

    BikeResource(BikeDAO bikeDAO) {
        this.bikeDAO = bikeDAO
    }

    @GET

    /**
     *Get by ID
     */
    @Path ('{id: \\d+}')
    @Produces(MediaType.APPLICATION_JSON)

    public Response getByID(@PathParam('id') IntParam id) {

        Response returnResponse

        Bike bikes = bikeDAO.getById(id.get())

        if (bikes == null) {

            returnResponse = notFound().build()
            
        } else {

            returnResponse = ok(bikes).build()
        }

        returnResponse
    }
}
