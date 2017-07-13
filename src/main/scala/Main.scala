import java.util.Calendar

import data.TexiData
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession
import org.insightedge.spark.context.InsightEdgeConfig
import org.insightedge.spark.implicits.basic._

/**
  * Created by talm on 7/10/17.
  */
object Main {

  private val master = "spark://172.17.0.2:7077"
  private val space = "insightedge-space"
  private val groups = "insightedge"
  private val locators = "172.17.0.2:4174"

  private val appName = "nyc-i9e"

  private val accessKey= ???
  private val secretKey = ???

  private val s3Client = "s3n://"

//    private val s3Path = "xap-test/home/tamirs/nyc/tmp.txt" // works

//  private val s3Path = "xap-test/home/tamirs/nyc/fhv_tripdata_2015-01.csv"
  private val s3Path = "xap-test/home/tamirs/nyc/green_tripdata_2016-02.csv"
//  private val s3Path = "xap-test/home/tamirs/nyc/small.csv"

  private val s3Url = s"$s3Client$s3Path"

  def main(args: Array[String]): Unit = {

    val start = Calendar.getInstance()
    val startHour = start.get(Calendar.HOUR_OF_DAY)
    val startMinute = start.get(Calendar.MINUTE)

    println(s"##### STRATING AT [$startHour:$startMinute]")

    val i9eConfig: InsightEdgeConfig = InsightEdgeConfig(space, Some(groups), Some(locators))
    val spark: SparkSession = createSparkSession(i9eConfig)

    val sc = spark.sparkContext
    sc.hadoopConfiguration.set("fs.s3n.awsAccessKeyId", accessKey)
    sc.hadoopConfiguration.set("fs.s3n.awsSecretAccessKey", secretKey)

    println(s"url reading from [$s3Url]")
    val rdd: RDD[String] = sc.textFile(s3Url)
    rdd.filter(line => line.split(",").length == 20)

    val dataRdd: RDD[TexiData] = rdd.map(line => {
      println(s"PARSING ---- line [$line]")
      val d = createData(line)
      println(s"Created Data [$d]")
      d
    } )
    dataRdd.saveToGrid()

    val end = Calendar.getInstance()
    val endHour = end.get(Calendar.HOUR_OF_DAY)
    val endMinute = end.get(Calendar.MINUTE)

    println(s"##### FINISHED AT [$endHour:$endMinute]")

    println("FINISHED !!")
  }

  def createSparkSession(i9eConfig: InsightEdgeConfig): SparkSession ={
    SparkSession.builder
      .appName(appName)
      .master(master)
      .insightEdgeConfig(i9eConfig)
      .getOrCreate()
  }

  def createData(line : String): TexiData = {
    try {
      val fields: Array[String] = line.split(",")
      TexiData(
        null,
        fields.apply(6),
        fields.apply(7),
        fields.apply(8),
        fields.apply(9),
        fields.apply(10),
        fields.apply(11),
        fields.apply(17)
      )
    }
    catch {
      case t : Throwable => {
        println(s"Throwable is [$t]")
        println(s"problemtaic line is [$line]")
        TexiData(null, "-1","-1","-1","-1","-1","-1","-1")
      }
    }

  }

}
