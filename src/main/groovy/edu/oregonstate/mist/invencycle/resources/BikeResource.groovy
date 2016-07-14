package edu.oregonstate.mist.invencycle.resources

import edu.oregonstate.mist.invencycle.core.Bike
import edu.oregonstate.mist.invencycle.db.BikeDAO
import edu.oregonstate.mist.api.Resource
import io.dropwizard.jersey.params.IntParam
import com.google.common.base.Optional

import javax.ws.rs.DELETE
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.Response
import javax.ws.rs.core.MediaType
import javax.ws.rs.QueryParam
import javax.validation.Valid
import javax.ws.rs.Consumes

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
     *GET by ID
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
     * GET by query
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Bike> getAll (@QueryParam("make") Optional<String> make,
                              @QueryParam("model") Optional<String> model,
                              @QueryParam("type") Optional<String> type) {

        bikeDAO.getBikeByQuery(make.or(""), model.or(""), type.or(""))
    }

    /**
     * POST bikes
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postBike (@Valid Bike newBike) {
        Response returnResponse
        //try {
            bikeDAO.postBrakeFront(newBike.brake_make_front,
                                   newBike.brake_model_front,
                                   newBike.brake_rotor_size_front)
            bikeDAO.postBrakeRear(newBike.brake_make_rear,
                                  newBike.brake_model_rear,
                                  newBike.brake_rotor_size_rear)
            bikeDAO.postDerailuerFront(newBike.derailuer_make_front,
                                       newBike.derailuer_model_front,
                                       newBike.derailuer_speeds_front)
            bikeDAO.postDerailuerRear(newBike.derailuer_make_rear,
                                      newBike.derailuer_model_rear,
                                      newBike.derailuer_speeds_rear)
            bikeDAO.postFrameSize(newBike.frame_size_cm,
                                  newBike.frame_size_name)
            bikeDAO.postFork(newBike.fork_make,
                             newBike.fork_model,
                             newBike.fork_travel)
            bikeDAO.postShock(newBike.shock_make,
                              newBike.shock_model,
                              newBike.shock_travel)
            bikeDAO.postWheelFront(newBike.wheel_size_front,
                                   newBike.rim_make_front,
                                   newBike.rim_model_front,
                                   newBike.hub_make_front,
                                   newBike.hub_model_front)
            bikeDAO.postWheelRear(newBike.wheel_size_rear,
                                  newBike.rim_make_rear,
                                  newBike.rim_model_rear,
                                  newBike.hub_make_rear,
                                  newBike.hub_model_rear)
            bikeDAO.postBike(newBike.make,
                             newBike.model,
                             newBike.msrp,
                             newBike.year,
                             newBike.type)
        //} catch (Exception e) {
         //   returnResponse = internalServerError("Internal server error").build()
       // }
        returnResponse
    }

    /**
     * DELETE by ID
     */
    @DELETE
    @Path('{id: \\d+}')
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteById (@PathParam('id') Integer id) {
        bikeDAO.deleteById(id)
        Response.ok().build()
    }
}
