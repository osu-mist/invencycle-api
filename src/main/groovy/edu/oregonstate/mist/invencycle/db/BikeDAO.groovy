package edu.oregonstate.mist.invencycle.db

import edu.oregonstate.mist.invencycle.core.Bike
import edu.oregonstate.mist.invencycle.mapper.bikemapper
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper
import org.skife.jdbi.v2.sqlobject.SqlQuery
import org.skife.jdbi.v2.sqlobject.SqlUpdate
import org.skife.jdbi.v2.sqlobject.Bind

/**
 * Created by jared on 4/20/16.
 */

@RegisterMapper(bikemapper)
public interface BikeDAO {

    @SqlQuery("""
        SELECT *
        FROM BIKE
        WHERE BIKE_ID = :id
        """)
    Bike getById(@Bind("id") Integer id)

}
