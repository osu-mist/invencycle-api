package edu.oregonstate.mist.invencycle.mapper

import edu.oregonstate.mist.invencycle.core.Bike
import org.skife.jdbi.v2.StatementContext
import org.skife.jdbi.v2.tweak.ResultSetMapper
import java.sql.ResultSet
import java.sql.SQLException

/**
 * Created by jared on 4/19/16.
 */
public class bikemapper implements ResultSetMapper<Bike> {
    public Bike map(int i, ResultSet rs, StatementContext sc) throws SQLException {
        new Bike(
             id: rs.getInt('BIKE_ID'),
             make: rs.getString('BIKE_MAKE'),
             model: rs.getString('BIKE_MODEL'),
             year: rs.getInt('BIKE_YEAR')
        )
    }

}
