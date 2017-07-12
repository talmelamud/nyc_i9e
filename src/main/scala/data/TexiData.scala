package data

import com.gigaspaces.annotation.pojo.SpaceIndex
import com.gigaspaces.metadata.index.SpaceIndexType
import org.insightedge.scala.annotation._

import scala.beans.BeanProperty
/**
  * Created by tamirs
  * on 7/12/17.
  */
case class TexiData(
                    @BeanProperty
                    @SpaceId(autoGenerate = true)
                    var  id : String,
                    //                @BeanProperty VendorID : String,
                    //                @BeanProperty lpep_pickup_datetime : String,
                    //                @BeanProperty Lpep_dropoff_datetime : String,
                    //                @BeanProperty Store_and_fwd_flag : String,
                    //                @BeanProperty RateCodeID : String,
                    @BeanProperty
                    var pickupLongitude : String,
                    @BeanProperty
                    var pickupLatitude : String,
                    @BeanProperty
                    var dropoffLongitude : String,
                    @BeanProperty
                    var dropoffLatitude : String,
                    @BeanProperty
                    @SpaceIndex(`type` = SpaceIndexType.BASIC, unique = true)
                    var passengerCount : String,
                    @BeanProperty
                    var tripDistance : String,
                    //                @BeanProperty Fare_amount : String,
                    //                @BeanProperty Extra : String,
                    //                @BeanProperty MTA_tax : String,
                    //                @BeanProperty Tip_amount : String,
                    //                @BeanProperty Tolls_amount : String,
                    //                @BeanProperty Ehail_fee : String,
                    //                @BeanProperty improvement_surcharge : String,
                    @BeanProperty
                    var totalAmount : String
                    //                @BeanProperty Payment_type : String,
                    //                @BeanProperty Trip_type : String
                  ) {

  def this() = this(null,"","","","","","", "")
}




