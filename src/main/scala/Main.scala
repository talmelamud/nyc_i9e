import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession
import org.insightedge.spark.context.InsightEdgeConfig
import org.insightedge.spark.implicits.basic._

/**
  * Created by talm on 7/10/17.
  */
object Main {

  private val master = "spark://127.0.0.1:7077"
  private val space = "insightedge-space"
  private val groups = "insightedge"
  private val locators = "127.0.0.1:4174"

  private val appName = "nyc-i9e"

  private val accessKey= "***"
  private val secretKey = "***"

  //https://s3.amazonaws.com/nyc-tlc/trip+data/fhv_tripdata_2015-01.csv

  private val s3Client = "s3n://"

//    private val s3Path = "xap-test/home/tamirs/nyc/tmp.txt" // works

    private val s3Path = "xap-test/home/tamirs/nyc/fhv_tripdata_2015-01.csv"
//  private val s3Path = "nyc-tlc/trip+data/fhv_tripdata_2015-01.csv"
  private val s3Url = s"$s3Client$s3Path"

  def main(args: Array[String]): Unit = {
    val i9eConfig: InsightEdgeConfig = InsightEdgeConfig(space, Some(groups), Some(locators))
    val spark: SparkSession = createSparkSession(i9eConfig)

    val sc = spark.sparkContext
    sc.hadoopConfiguration.set("fs.s3n.awsAccessKeyId", accessKey)
    sc.hadoopConfiguration.set("fs.s3n.awsSecretAccessKey", secretKey)

    println(s"url reading from [$s3Url]")
    val rdd: RDD[String] = sc.textFile(s3Url)

    val arr: Array[String] = rdd.collect()

    arr foreach println
  }

  def createSparkSession(i9eConfig: InsightEdgeConfig): SparkSession ={
    SparkSession.builder
      .appName(appName)
      .master(master)
      .insightEdgeConfig(i9eConfig)
      .getOrCreate()
  }

}
