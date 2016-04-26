package edu.oregonstate.mist.invencycle

import com.fasterxml.jackson.annotation.JsonProperty
import edu.oregonstate.mist.api.Configuration
import io.dropwizard.db.DataSourceFactory
import javax.validation.Valid
import javax.validation.constraints.NotNull

public class InvencycleConfiguration extends Configuration {

    //DataSourceFactory database = new DataSourceFactory()

    @Valid
    @NotNull
    @JsonProperty("database")
    DataSourceFactory database = new DataSourceFactory()

    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        database
    }

    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
        this.database = dataSourceFactory
    }
}

