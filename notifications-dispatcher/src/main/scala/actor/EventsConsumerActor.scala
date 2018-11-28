package actor

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import cakesolutions.kafka.KafkaConsumer
import cakesolutions.kafka.akka.KafkaConsumerActor.{Confirm, Subscribe}
import cakesolutions.kafka.akka.{ConsumerRecords, KafkaConsumerActor}
import com.typesafe.config.{Config, ConfigFactory}
import model.Notification
import org.apache.kafka.clients.consumer.OffsetResetStrategy
import org.apache.kafka.common.serialization.StringDeserializer

import scala.concurrent.duration._

object EventsConsumerActor {
  def props() = Props(new EventsConsumerActor())
}

/**
  * This actor consumes events from kafka then parse them them distribute them to [[SessionActor]]
  *
  * @author jaharzli
  */
class EventsConsumerActor extends Actor with ActorLogging {

  // Configurations
  private val config: Config = ConfigFactory.load().getConfig("kafka-consumer")
  private val topic: String = config.getString("topic")
  private val servers: String = config.getString("servers")
  private val groupId: String = config.getString("group-id")
  private val scheduleInterval = 1.seconds
  private val maxRedeliveries = 3.seconds

  private val recordsExtractor = ConsumerRecords.extractor[String, String]

  createConsumerAndSubscribeToTopic()

  override def receive: Receive = {
    case recordsExtractor(records) => processRecords(records)
  }

  private def createConsumerAndSubscribeToTopic(): Unit = {

    log.debug(
      "Request to create consumer and subscribe to topic {} in kafka {} with group id {}", topic, servers, groupId)

    val conf = consumerConfiguration()
    val consumer = buildConsumer(conf)
    consumer ! Subscribe.AutoPartition(List(topic))

  }

  private def processRecords(records: ConsumerRecords[String, String]): Unit = {

    records.pairs.map { case (_, data) => data }
      .foreach(data => {
        val strings = data.split("#")
        val userId = strings(0)
        val notificationId = strings(1)
        val notificationContent = strings(2)
        context.actorSelection("../*") ! SendToClient(Notification(notificationId, notificationContent), userId)
      })

    sender() ! Confirm(records.offsets, commit = true)

  }

  private def consumerConfiguration(): KafkaConsumer.Conf[String, String] = {

    KafkaConsumer.Conf(
      new StringDeserializer,
      new StringDeserializer,
      groupId = groupId,
      enableAutoCommit = false,
      autoOffsetReset = OffsetResetStrategy.EARLIEST,
      bootstrapServers = servers
    )

  }

  private def buildConsumer(consumerConfig: KafkaConsumer.Conf[String, String]): ActorRef = {

    val actorConfig = KafkaConsumerActor.Conf(scheduleInterval, maxRedeliveries)
    context.actorOf(KafkaConsumerActor.props(consumerConfig, actorConfig, self))

  }

}
