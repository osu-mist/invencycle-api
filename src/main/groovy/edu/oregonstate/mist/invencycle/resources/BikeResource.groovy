package edu.oregonstate.mist.invencycle.resources

import edu.oregonstate.mist.invencycle.core.Bike
import edu.oregonstate.mist.invencycle.db.BikeDAO
import edu.oregonstate.mist.api.Resource
import io.dropwizard.jersey.params.IntParam
import com.google.common.base.Optional
import org.slf4j.LoggerFactory

import javax.ws.rs.DELETE
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.PUT
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.Response
import javax.ws.rs.core.MediaType
import javax.ws.rs.QueryParam
import javax.validation.Valid
import javax.ws.rs.Consumes
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 *Bike resource class
 */

@Path('/bikes/')
@Produces(MediaType.APPLICATION_JSON)
class BikeResource extends Resource {
    Logger logger = LoggerFactory.getLogger(BikeResource.class)

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

        try {
            addBike(newBike)
            returnResponse = Response.ok("Bike successfully created").build()
        } catch (Exception e) {
            String validationErrorMsg = getDataError(e.cause.toString())

            if (!(validationErrorMsg.isEmpty())) {
                returnResponse = badRequest(validationErrorMsg).build()
            } else {
                logger.error("Exception while calling postBike", e)
                returnResponse = internalServerError("Internal server error").build()
            }
        }
        returnResponse
    }

    /**
     * PUT bike with ID
     *
     * This method checks the bike attributes submitted and only updates the database
     * with the attributes that aren't null. Null submitted attributes do not affect
     * the attributes stored in the database.
     *
     */
    @PUT
    @Path('{id: \\d+}')
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response putByID (@PathParam('id') Integer id, @Valid Bike userBike) {
        Response returnResponse
        Bike currentBike = bikeDAO.getById(id)

        if (!currentBike) {
            returnResponse = badRequest("Bike ID does not exist. Use a POST request to create a new bike.").build()
            return(returnResponse)
        }
        try {
            copyProperties(userBike, currentBike)
            updateBike(currentBike)
            returnResponse = Response.ok("Bike successfully updated").build()
        } catch (Exception e) {
            String validationErrorMsg = getDataError(e.cause.toString())

            if (!(validationErrorMsg.isEmpty())) {
                returnResponse = badRequest(validationErrorMsg).build()
            } else {
                logger.error("Exception while calling putBike", e)
                returnResponse = internalServerError("Internal server error").build()
            }
        }
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
    /**
     * Checks an error for certain characteristics and returns a message based on the error.
     */
    public def getDataError(dbError) {
        def errorMessages = ["bike_make", "bike_model", "bike_year", "bike_msrp"]
        def friendlyMessage

        if (dbError.contains("cannot insert NULL into (\"MISTSTU3\".\"BIKE\".\"BIKE_BIKE_TYPE_ID\")")
                            || dbError.contains("cannot update (\"MISTSTU3\".\"BIKE\".\"BIKE_BIKE_TYPE_ID\") to NULL")) {
           friendlyMessage = "bike_type must be Mountain Trail, Mountain XC, " +
                    "Mountain Enduro, Road, Road Gravel, or Cyclocross"
        } else {
            errorMessages.each {
                if (dbError.contains("cannot insert NULL into (\"MISTSTU3\".\"BIKE\".\"${it.toUpperCase()}\")")) {
                    friendlyMessage = "${it} cannot be NULL"
                }
            }
        }
        friendlyMessage
    }
    /**
     * Makes a Bike ready to go to the DAO by updating attributes which are not null.
     */
    public def copyProperties(def source, def target) {
        target.metaClass.properties.each {
            if (source.metaClass.getProperty(source, it.name)) {
                if (source.metaClass.hasProperty(source, it.name)
                        && it.name != 'metaClass' && it.name != 'class') {
                    it.setProperty(target, source.metaClass.getProperty(source, it.name))
                }
            }
        }
    }
    /**
     * Passes a Bike to the DAO to update an existing bike in the database.
     */
    public def updateBike(Bike currentBike) {
        bikeDAO.updateBike(currentBike.id,
                currentBike.make,
                currentBike.model,
                currentBike.year,
                currentBike.msrp,
                currentBike.type)
        bikeDAO.updateBrakeFront(currentBike.id,
                currentBike.brake_make_front,
                currentBike.brake_model_front,
                currentBike.brake_rotor_size_front)
        bikeDAO.updateBrakeRear(currentBike.id,
                currentBike.brake_make_rear,
                currentBike.brake_model_rear,
                currentBike.brake_rotor_size_rear)
        bikeDAO.updateDerailuerFront(currentBike.id,
                currentBike.derailuer_make_front,
                currentBike.derailuer_model_front,
                currentBike.derailuer_speeds_front)
        bikeDAO.updateDerailuerRear(currentBike.id,
                currentBike.derailuer_make_rear,
                currentBike.derailuer_model_rear,
                currentBike.derailuer_speeds_rear)
        bikeDAO.updateFork(currentBike.id,
                currentBike.fork_make,
                currentBike.fork_model,
                currentBike.fork_travel)
        bikeDAO.updateShock(currentBike.id,
                currentBike.shock_make,
                currentBike.shock_model,
                currentBike.shock_travel)
        bikeDAO.updateWheelFront(currentBike.id,
                currentBike.wheel_size_front,
                currentBike.rim_make_front,
                currentBike.rim_model_front,
                currentBike.hub_make_front,
                currentBike.hub_model_front)
        bikeDAO.updateWheelRear(currentBike.id,
                currentBike.wheel_size_rear,
                currentBike.rim_make_rear,
                currentBike.rim_model_rear,
                currentBike.hub_make_rear,
                currentBike.hub_model_rear)
        bikeDAO.updateFrameSize(currentBike.id,
                currentBike.frame_size_name,
                currentBike.frame_size_cm)
    }
    /**
     * Passes a Bike to DAO to add it to the database.
     */
    public def addBike(Bike newBike) {
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
        bikeDAO.postFrameSize(newBike.frame_size_name,
                newBike.frame_size_cm)
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
                newBike.type,
                newBike.frame_size_name,
                newBike.frame_size_cm,
                newBike.derailuer_make_front,
                newBike.derailuer_model_front,
                newBike.derailuer_speeds_front,
                newBike.derailuer_make_rear,
                newBike.derailuer_model_rear,
                newBike.derailuer_speeds_rear,
                newBike.fork_make,
                newBike.fork_model,
                newBike.fork_travel,
                newBike.shock_make,
                newBike.shock_model,
                newBike.shock_travel,
                newBike.wheel_size_front,
                newBike.rim_make_front,
                newBike.rim_model_front,
                newBike.hub_make_front,
                newBike.hub_model_front,
                newBike.wheel_size_rear,
                newBike.rim_make_rear,
                newBike.rim_model_rear,
                newBike.hub_make_rear,
                newBike.hub_model_rear,
                newBike.brake_make_front,
                newBike.brake_model_front,
                newBike.brake_rotor_size_front,
                newBike.brake_make_rear,
                newBike.brake_model_rear,
                newBike.brake_rotor_size_rear)
    }
}
