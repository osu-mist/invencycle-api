package edu.oregonstate.mist.invencycle.mapper

import edu.oregonstate.mist.invencycle.core.Bike
import org.skife.jdbi.v2.StatementContext
import org.skife.jdbi.v2.tweak.ResultSetMapper
import java.sql.ResultSet
import java.sql.SQLException

public class BikeMapper implements ResultSetMapper<Bike> {
    public Bike map(int i, ResultSet rs, StatementContext sc) throws SQLException {
        new Bike(
             id: rs.getInt('BIKE_ID'),
             make: rs.getString('BIKE_MAKE'),
             model: rs.getString('BIKE_MODEL'),
             year: rs.getInt('BIKE_YEAR'),
             type: rs.getString('BIKE_TYPE_NAME'),
             frame_size_name: rs.getString('FRAME_SIZE_NAME'),
             frame_size_cm: rs.getInt('FRAME_SIZE_CM'),
             msrp: rs.getInt('BIKE_MSRP'),
             derailuer_make_front: rs.getString('DERAILUER_MAKE_FRONT'),
             derailuer_model_front: rs.getString('DERAILUER_MODEL_FRONT'),
             derailuer_speeds_front: rs.getInt('DERAILUER_SPEEDS_FRONT'),
             derailuer_make_rear: rs.getString('DERAILUER_MAKE_REAR'),
             derailuer_model_rear: rs.getString('DERAILUER_MODEL_REAR'),
             derailuer_speeds_rear: rs.getInt('DERAILUER_SPEEDS_REAR'),
             fork_make: rs.getString('FORK_MAKE'),
             fork_model: rs.getString('FORK_MODEL'),
             fork_travel: rs.getInt('FORK_TRAVEL'),
             shock_make: rs.getString('SHOCK_MAKE'),
             shock_model: rs.getString('SHOCK_MODEL'),
             shock_travel: rs.getInt('SHOCK_TRAVEL'),
             rim_make_front: rs.getString('RIM_MAKE_FRONT'),
             rim_model_front: rs.getString('RIM_MODEL_FRONT'),
             wheel_size_front: rs.getInt('WHEEL_SIZE_FRONT'),
             hub_make_front: rs.getString('HUB_MAKE_FRONT'),
             hub_model_front: rs.getString('HUB_MODEL_FRONT'),
             rim_make_rear: rs.getString('RIM_MAKE_REAR'),
             rim_model_rear: rs.getString('RIM_MODEL_REAR'),
             wheel_size_rear: rs.getInt('WHEEL_SIZE_REAR'),
             hub_make_rear: rs.getString('HUB_MAKE_REAR'),
             hub_model_rear: rs.getString('HUB_MODEL_REAR'),
             brake_make_front: rs.getString('BRAKE_MAKE_FRONT'),
             brake_model_front: rs.getString('BRAKE_MODEL_FRONT'),
             brake_rotor_size_front: rs.getInt('BRAKE_ROTOR_SIZE_FRONT'),
             brake_make_rear: rs.getString('BRAKE_MAKE_REAR'),
             brake_model_rear: rs.getString('BRAKE_MODEL_REAR'),
             brake_rotor_size_rear: rs.getInt('BRAKE_ROTOR_SIZE_REAR')
        )
    }
}
