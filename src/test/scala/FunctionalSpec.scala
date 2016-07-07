import de.flapdoodle.embed.mongo.{MongodExecutable, MongodProcess, MongodStarter}
import de.flapdoodle.embed.mongo.config.{MongodConfigBuilder, Net}
import de.flapdoodle.embed.mongo.distribution.Version
import de.flapdoodle.embed.process.runtime.Network
import org.scalatest.FlatSpec

class FunctionalSpec extends FlatSpec {

  "Functional spec" should "run embedded mongodb" in {
    val starter = MongodStarter.getDefaultInstance

    val port = 27017
    val mongodConfig = new MongodConfigBuilder()
      .version(Version.Main.PRODUCTION)
      .net(new Net(port, Network.localhostIsIPv6()))
      .build()

    var mongodExecutable: MongodExecutable = null
    try {
      mongodExecutable = starter.prepare(mongodConfig)
      val mongod: MongodProcess = mongodExecutable.start()

      Thread.sleep(60000)



    } finally {
      if (mongodExecutable != null)
        mongodExecutable.stop();
    }
  }
}
