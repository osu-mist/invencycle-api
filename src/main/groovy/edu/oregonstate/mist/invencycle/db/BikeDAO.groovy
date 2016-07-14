package edu.oregonstate.mist.invencycle.db

import edu.oregonstate.mist.invencycle.core.Bike
import edu.oregonstate.mist.invencycle.mapper.BikeMapper
import org.skife.jdbi.v2.sqlobject.SqlUpdate
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper
import org.skife.jdbi.v2.sqlobject.SqlQuery
import org.skife.jdbi.v2.sqlobject.Bind

@RegisterMapper(BikeMapper)
public interface BikeDAO extends Closeable {

    /**
     * POST a bike
     */
    @SqlUpdate("""
              INSERT INTO BRAKE (BRAKE_ID, BRAKE_MAKE, BRAKE_MODEL, BRAKE_ROTOR_SIZE, BRAKE_POSITION)
              VALUES (BIKE_BRAKE_ID_SEQ.nextval, :brake_make_front, :brake_model_front, :brake_rotor_size_front, 'Front')
               """)
    void postBrakeFront(@Bind("brake_make_front") String brake_make_front,
                        @Bind("brake_model_front") String brake_model_front,
                        @Bind("brake_rotor_size_front") Integer brake_rotor_size_front)

    @SqlUpdate("""
              INSERT INTO BRAKE (BRAKE_ID, BRAKE_MAKE, BRAKE_MODEL, BRAKE_ROTOR_SIZE, BRAKE_POSITION)
              VALUES (BIKE_BRAKE_ID_SEQ.nextval, :brake_make_rear, :brake_model_rear, :brake_rotor_size_rear, 'Rear')
               """)
    void postBrakeRear(@Bind("brake_make_rear") String brake_make_rear,
                        @Bind("brake_model_rear") String brake_model_rear,
                        @Bind("brake_rotor_size_rear") Integer brake_rotor_size_rear)

    @SqlUpdate("""
              INSERT INTO DERAILUER (DERAILUER_ID, DERAILUER_MAKE, DERAILUER_MODEL, DERAILUER_SPEEDS, DERAILUER_POSITION)
              VALUES (BIKE_DERAILUER_ID_SEQ.nextval, :derailuer_make_front, :derailuer_model_front, :derailuer_speeds_front, 'Front')
              """)
    void postDerailuerFront(@Bind("derailuer_make_front") String derailuer_make_front,
                            @Bind("derailuer_model_front") String derailuer_model_front,
                            @Bind("derailuer_speeds_front") Integer derailuer_speeds_front)

    @SqlUpdate("""
              INSERT INTO DERAILUER (DERAILUER_ID, DERAILUER_MAKE, DERAILUER_MODEL, DERAILUER_SPEEDS, DERAILUER_POSITION)
              VALUES (BIKE_DERAILUER_ID_SEQ.nextval, :derailuer_make_rear, :derailuer_model_rear, :derailuer_speeds_rear, 'Rear')
              """)
    void postDerailuerRear(@Bind("derailuer_make_rear") String derailuer_make_rear,
                            @Bind("derailuer_model_rear") String derailuer_model_rear,
                            @Bind("derailuer_speeds_rear") Integer derailuer_speeds_rear)
    
    @SqlUpdate("""
              INSERT INTO FRAME_SIZE (FRAME_SIZE_ID, FRAME_SIZE_NAME, FRAME_SIZE_CM)
              VALUES (BIKE_FRAME_SIZE_SEQ.nextval, :frame_size_name, :frame_size_cm)
               """)
    void postFrameSize(@Bind("frame_size_name") String frame_size_name,
                       @Bind("frame_size_cm") Integer frame_size_cm)
    
    @SqlUpdate("""
              INSERT INTO SUSPENSION (SUSPENSION_ID, SUSPENSION_MAKE, SUSPENSION_MODEL, SUSPENSION_TRAVEL, SUSPENSION_TYPE)
              VALUES (BIKE_SUSPENSION_ID_SEQ.nextval, :fork_make, :fork_model, :fork_travel, 'Fork')
               """)
    void postFork(@Bind("fork_make") String fork_make,
                  @Bind("fork_model") String fork_model,
                  @Bind("fork_travel") Integer fork_travel)

    @SqlUpdate("""
              INSERT INTO SUSPENSION (SUSPENSION_ID, SUSPENSION_MAKE, SUSPENSION_MODEL, SUSPENSION_TRAVEL, SUSPENSION_TYPE)
              VALUES (BIKE_SUSPENSION_ID_SEQ.nextval, :shock_make, :shock_model, :shock_travel, 'Shock')
               """)
    void postShock(@Bind("shock_make") String shock_make,
                  @Bind("shock_model") String shock_model,
                  @Bind("shock_travel") Integer shock_travel)
    
    @SqlUpdate("""
               INSERT INTO WHEEL (WHEEL_ID, WHEEL_SIZE, WHEEL_RIM_MAKE, WHEEL_RIM_MODEL, WHEEL_HUB_MAKE, WHEEL_HUB_MODEL, WHEEL_POSITION)
               VALUES (BIKE_WHEEL_ID_SEQ.nextval, :wheel_size_front, :rim_make_front, :rim_model_front, :hub_make_front, :hub_model_front, 'Front')
              """)
    void postWheelFront(@Bind("wheel_size_front") Integer wheel_size_front,
                        @Bind("rim_make_front") String rim_make_front,
                        @Bind("rim_model_front") String rim_model_front,
                        @Bind("hub_make_front") String hub_make_front,
                        @Bind("hub_model_front") String hub_model_front)

    @SqlUpdate("""
               INSERT INTO WHEEL (WHEEL_ID, WHEEL_SIZE, WHEEL_RIM_MAKE, WHEEL_RIM_MODEL, WHEEL_HUB_MAKE, WHEEL_HUB_MODEL, WHEEL_POSITION)
               VALUES (BIKE_WHEEL_ID_SEQ.nextval, :wheel_size_rear, :rim_make_rear, :rim_model_rear, :hub_make_rear, :hub_model_rear, 'Rear')
              """)
    void postWheelRear(@Bind("wheel_size_rear") Integer wheel_size_rear,
                        @Bind("rim_make_rear") String rim_make_rear,
                        @Bind("rim_model_rear") String rim_model_rear,
                        @Bind("hub_make_rear") String hub_make_rear,
                        @Bind("hub_model_rear") String hub_model_rear)

    @SqlUpdate("""
              INSERT INTO BIKE (BIKE_ID, BIKE_MAKE, BIKE_MODEL, BIKE_YEAR, BIKE_BIKE_TYPE_ID, BIKE_FRAME_SIZE_ID, BIKE_MSRP, BIKE_DERAILUER_FRONT_ID, BIKE_DERAILUER_REAR_ID, BIKE_FORK_ID, BIKE_SHOCK_ID, BIKE_WHEEL_FRONT_ID, BIKE_WHEEL_REAR_ID, BIKE_BRAKE_FRONT_ID, BIKE_BRAKE_REAR_ID, BIKE_CREATE_DATE)
              VALUES (BIKE_ID_SEQ.nextval, :bike_make, :bike_model, :bike_year,

                (SELECT BIKE_TYPE_ID FROM BIKE_TYPE
                WHERE BIKE_TYPE_NAME LIKE :bike_type),

                (SELECT MAX(FRAME_SIZE_ID) FROM FRAME_SIZE),

              :bike_msrp,

                (SELECT MAX(DERAILUER_ID) FROM DERAILUER WHERE DERAILUER_POSITION LIKE 'Front'),

                (SELECT MAX(DERAILUER_ID) FROM DERAILUER WHERE DERAILUER_POSITION LIKE 'Rear'),

                (SELECT MAX(SUSPENSION_ID) FROM SUSPENSION WHERE SUSPENSION_TYPE LIKE 'Fork'),

                (SELECT MAX(SUSPENSION_ID) FROM SUSPENSION WHERE SUSPENSION_TYPE LIKE 'Shock'),

                (SELECT MAX(WHEEL_ID) FROM WHEEL WHERE WHEEL_POSITION LIKE 'Front'),

                (SELECT MAX(WHEEL_ID) FROM WHEEL WHERE WHEEL_POSITION LIKE 'Rear'),

                (SELECT MAX(BRAKE_ID) FROM BRAKE WHERE BRAKE_POSITION LIKE 'Front'),

                (SELECT MAX(BRAKE_ID) FROM BRAKE WHERE BRAKE_POSITION LIKE 'Rear'),

              SYSDATE)
              """)
    void postBike(@Bind("bike_make") String make,
                  @Bind("bike_model") String model,
                  @Bind("bike_msrp") Integer msrp,
                  @Bind("bike_year") Integer year,
                  @Bind("bike_type") String type)
    /**
     * Delete by ID
     */
    @SqlUpdate("""
        DELETE FROM BIKE
        WHERE BIKE.BIKE_ID = :id
        """)
    void deleteById(@Bind("id") Integer id)

    /**
     * Get by query
     */
    @SqlQuery("""
          SELECT
              BIKE.BIKE_ID,
              BIKE.BIKE_MAKE,
              BIKE.BIKE_MODEL,
              BIKE.BIKE_YEAR,
              BIKE_TYPE.BIKE_TYPE_NAME,
              FRAME_SIZE.FRAME_SIZE_NAME,
              FRAME_SIZE.FRAME_SIZE_CM,
              BIKE.BIKE_MSRP,
              D1.DERAILUER_MAKE AS DERAILUER_MAKE_FRONT,
              D1.DERAILUER_MODEL AS DERAILUER_MODEL_FRONT,
              D1.DERAILUER_SPEEDS AS DERAILUER_SPEEDS_FRONT,
              D2.DERAILUER_MAKE AS DERAILUER_MAKE_REAR,
              D2.DERAILUER_MODEL AS DERAILUER_MODEL_REAR,
              D2.DERAILUER_SPEEDS AS DERAILUER_SPEEDS_REAR,
              F.SUSPENSION_MAKE AS FORK_MAKE,
              F.SUSPENSION_MODEL AS FORK_MODEL,
              F.SUSPENSION_TRAVEL AS FORK_TRAVEL,
              S.SUSPENSION_MAKE AS SHOCK_MAKE,
              S.SUSPENSION_MODEL AS SHOCK_MODEL,
              S.SUSPENSION_TRAVEL AS SHOCK_TRAVEL,
              WF.WHEEL_SIZE AS WHEEL_SIZE_FRONT,
              WF.WHEEL_RIM_MAKE AS RIM_MAKE_FRONT,
              WF.WHEEL_RIM_MODEL AS RIM_MODEL_FRONT,
              WF.WHEEL_HUB_MAKE AS HUB_MAKE_FRONT,
              WF.WHEEL_HUB_MODEL AS HUB_MODEL_FRONT,
              WR.WHEEL_SIZE AS WHEEL_SIZE_REAR,
              WR.WHEEL_RIM_MAKE AS RIM_MAKE_REAR,
              WR.WHEEL_RIM_MODEL AS RIM_MODEL_REAR,
              WR.WHEEL_HUB_MAKE AS HUB_MAKE_REAR,
              WR.WHEEL_HUB_MODEL AS HUB_MODEL_REAR,
              BF.BRAKE_MAKE AS BRAKE_MAKE_FRONT,
              BF.BRAKE_MODEL AS BRAKE_MODEL_FRONT,
              BF.BRAKE_ROTOR_SIZE AS BRAKE_ROTOR_SIZE_FRONT,
              BR.BRAKE_MAKE AS BRAKE_MAKE_REAR,
              BR.BRAKE_MODEL AS BRAKE_MODEL_REAR,
              BR.BRAKE_ROTOR_SIZE AS BRAKE_ROTOR_SIZE_REAR,
              BIKE.BIKE_CREATE_DATE,
              BIKE.BIKE_DELETE_DATE

        FROM BIKE

        LEFT JOIN BIKE_TYPE
          ON BIKE.BIKE_BIKE_TYPE_ID = BIKE_TYPE.BIKE_TYPE_ID
        LEFT JOIN FRAME_SIZE
          ON BIKE.BIKE_FRAME_SIZE_ID = FRAME_SIZE.FRAME_SIZE_ID
        LEFT JOIN DERAILUER D1
          ON D1.DERAILUER_ID = BIKE.BIKE_DERAILUER_FRONT_ID
        LEFT JOIN DERAILUER D2
          ON D2.DERAILUER_ID = BIKE.BIKE_DERAILUER_REAR_ID
        LEFT JOIN SUSPENSION F
          ON F.SUSPENSION_ID = BIKE.BIKE_FORK_ID
        LEFT JOIN SUSPENSION S
          ON S.SUSPENSION_ID = BIKE.BIKE_SHOCK_ID
        LEFT JOIN WHEEL WF
          ON WF.WHEEL_ID = BIKE.BIKE_WHEEL_FRONT_ID
        LEFT JOIN WHEEL WR
          ON WR.WHEEL_ID = BIKE.BIKE_WHEEL_REAR_ID
        LEFT JOIN BRAKE BF
          ON BF.BRAKE_ID = BIKE.BIKE_BRAKE_FRONT_ID
        LEFT JOIN BRAKE BR
          ON BR.BRAKE_ID = BIKE.BIKE_BRAKE_REAR_ID

        WHERE BIKE.BIKE_MAKE LIKE '%' ||:make|| '%'
        AND BIKE.BIKE_MODEL LIKE '%' ||:model|| '%'
        AND BIKE_TYPE.BIKE_TYPE_NAME LIKE '%' ||:type|| '%'
        """)

    List<Bike> getBikeByQuery(@Bind("make") String make,
                              @Bind("model") String model,
                              @Bind("type") String type)
    /**
     * Get by ID
     */
    @SqlQuery("""
        SELECT
          BIKE.BIKE_ID,
          BIKE.BIKE_MAKE,
          BIKE.BIKE_MODEL,
          BIKE.BIKE_YEAR,
          BIKE_TYPE.BIKE_TYPE_NAME,
          FRAME_SIZE.FRAME_SIZE_NAME,
          FRAME_SIZE.FRAME_SIZE_CM,
          BIKE.BIKE_MSRP,
          D1.DERAILUER_MAKE AS DERAILUER_MAKE_FRONT,
          D1.DERAILUER_MODEL AS DERAILUER_MODEL_FRONT,
          D1.DERAILUER_SPEEDS AS DERAILUER_SPEEDS_FRONT,
          D2.DERAILUER_MAKE AS DERAILUER_MAKE_REAR,
          D2.DERAILUER_MODEL AS DERAILUER_MODEL_REAR,
          D2.DERAILUER_SPEEDS AS DERAILUER_SPEEDS_REAR,
          F.SUSPENSION_MAKE AS FORK_MAKE,
          F.SUSPENSION_MODEL AS FORK_MODEL,
          F.SUSPENSION_TRAVEL AS FORK_TRAVEL,
          S.SUSPENSION_MAKE AS SHOCK_MAKE,
          S.SUSPENSION_MODEL AS SHOCK_MODEL,
          S.SUSPENSION_TRAVEL AS SHOCK_TRAVEL,
          WF.WHEEL_SIZE AS WHEEL_SIZE_FRONT,
          WF.WHEEL_RIM_MAKE AS RIM_MAKE_FRONT,
          WF.WHEEL_RIM_MODEL AS RIM_MODEL_FRONT,
          WF.WHEEL_HUB_MAKE AS HUB_MAKE_FRONT,
          WF.WHEEL_HUB_MODEL AS HUB_MODEL_FRONT,
          WR.WHEEL_SIZE AS WHEEL_SIZE_REAR,
          WR.WHEEL_RIM_MAKE AS RIM_MAKE_REAR,
          WR.WHEEL_RIM_MODEL AS RIM_MODEL_REAR,
          WR.WHEEL_HUB_MAKE AS HUB_MAKE_REAR,
          WR.WHEEL_HUB_MODEL AS HUB_MODEL_REAR,
          BF.BRAKE_MAKE AS BRAKE_MAKE_FRONT,
          BF.BRAKE_MODEL AS BRAKE_MODEL_FRONT,
          BF.BRAKE_ROTOR_SIZE AS BRAKE_ROTOR_SIZE_FRONT,
          BR.BRAKE_MAKE AS BRAKE_MAKE_REAR,
          BR.BRAKE_MODEL AS BRAKE_MODEL_REAR,
          BR.BRAKE_ROTOR_SIZE AS BRAKE_ROTOR_SIZE_REAR,
          BIKE.BIKE_CREATE_DATE,
          BIKE.BIKE_DELETE_DATE

        FROM BIKE

        LEFT JOIN BIKE_TYPE
          ON BIKE.BIKE_BIKE_TYPE_ID = BIKE_TYPE.BIKE_TYPE_ID
        LEFT JOIN FRAME_SIZE
          ON BIKE.BIKE_FRAME_SIZE_ID = FRAME_SIZE.FRAME_SIZE_ID
        LEFT JOIN DERAILUER D1
          ON D1.DERAILUER_ID = BIKE.BIKE_DERAILUER_FRONT_ID
        LEFT JOIN DERAILUER D2
          ON D2.DERAILUER_ID = BIKE.BIKE_DERAILUER_REAR_ID
        LEFT JOIN SUSPENSION F
          ON F.SUSPENSION_ID = BIKE.BIKE_FORK_ID
        LEFT JOIN SUSPENSION S
          ON S.SUSPENSION_ID = BIKE.BIKE_SHOCK_ID
        LEFT JOIN WHEEL WF
          ON WF.WHEEL_ID = BIKE.BIKE_WHEEL_FRONT_ID
        LEFT JOIN WHEEL WR
          ON WR.WHEEL_ID = BIKE.BIKE_WHEEL_REAR_ID
        LEFT JOIN BRAKE BF
          ON BF.BRAKE_ID = BIKE.BIKE_BRAKE_FRONT_ID
        LEFT JOIN BRAKE BR
          ON BR.BRAKE_ID = BIKE.BIKE_BRAKE_REAR_ID
        WHERE BIKE.BIKE_ID = :id
        """)
    Bike getById(@Bind("id") Integer id)

    @Override
    void close()

}
