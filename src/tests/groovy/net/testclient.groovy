package net

import org.vertx.groovy.core.net.NetClient
import org.vertx.groovy.newtests.TestUtils
import org.vertx.java.core.buffer.Buffer

tu = new TestUtils()
tu.checkContext()

client = null

tu.register("test1", {
  client = new NetClient().connect(8080, "localhost", { socket ->
    tu.checkContext()
    socket.dataHandler { buffer ->
      tu.checkContext()
      // println "Net client receiving: ${buffer.toString("UTF-8")}"
      tu.testComplete()
    }

    String str = "hello"
    socket << Buffer.create(str)
  })
})

tu.appReady()

void vertxStop() {
  if (client != null) client.close()
  tu.appStopped()
}
