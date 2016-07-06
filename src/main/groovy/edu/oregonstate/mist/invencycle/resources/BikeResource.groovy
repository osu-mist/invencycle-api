package edu.oregonstate.mist.invencycle.resources

import edu.oregonstate.mist.invencycle.core.Bike
import edu.oregonstate.mist.invencycle.db.BikeDAO
import edu.oregonstate.mist.api.Resource
import io.dropwizard.jersey.params.IntParam
import com.google.common.base.Optional

import javax.ws.rs.DELETE
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.Response
import javax.ws.rs.core.MediaType
import javax.ws.rs.QueryParam

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

    /**
     *Get by ID
     */
    @GET
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

    /**
     * Get by query
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Bike> getAll (@QueryParam("make") Optional<String> make,
                              @QueryParam("model") Optional<String> model,
                              @QueryParam("type") Optional<String> type) {

        bikeDAO.getBikeByQuery(make.or(""), model.or(""), type.or(""))
    }

    /**
     * Delete by ID
     */
    @DELETE
    @Path('{id: \\d+}')
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteById (@PathParam('id') Integer id) {
        BikeDAO.deleteById(id)
        Reponse.ok().build()
    }
}
