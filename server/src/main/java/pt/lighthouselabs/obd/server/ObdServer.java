/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package pt.lighthouselabs.obd.server;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.glassfish.embeddable.GlassFish;
import org.glassfish.embeddable.GlassFishException;
import org.glassfish.embeddable.GlassFishProperties;
import org.glassfish.embeddable.GlassFishRuntime;
import org.glassfish.embeddable.archive.ScatteredArchive;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

/**
 * Embedded service.
 */
public class ObdServer {

  private static final Logger logger = Logger.getLogger(ObdServer.class
      .getName());

  @Option(name = "--port", usage = "system port to bind", metaVar = "PORT")
  private int port = 8080;

  @Option(name = "--adminPort", usage = "admin port to bind", metaVar = "PORT_ADMIN")
  private int portAdmin = 4848;

  @Option(name = "--help", usage = "show help")
  private boolean help = false;

  @Argument
  private List<String> arguments = new ArrayList<String>();

  public static void main(String[] args) throws IOException, GlassFishException {
    new ObdServer().execute(args);
  }

  public void execute(String... args) throws GlassFishException, IOException {
    CmdLineParser parser = new CmdLineParser(this);
    parser.setUsageWidth(80);
    try {
      parser.parseArgument(args);
    } catch (CmdLineException e) {
      // ignore
    }

    // show help, if requested
    if (help) {
      System.err.println("java -jar [jarfile] [options...] arguments...");
      parser.printUsage(System.err);
      System.err.println();
      parser.printUsage(System.err);
    } else {
      logger.info("Initiliazing OBD server..");
      // set-up embedded Glassfish instance
      GlassFishProperties gfProperties = new GlassFishProperties();
      final String domainXmlPath = new File("server/target/classes/domain.xml")
          .toURI().toString();
      logger.info("domain.xml -> " + domainXmlPath);
      gfProperties.setConfigFileURI(domainXmlPath);
      gfProperties.setPort("admin-listener", portAdmin);
      gfProperties.setPort("http-listener-1", port);
      final GlassFish server = GlassFishRuntime.bootstrap().newGlassFish(
          gfProperties);

      // register shutdown hook
      Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
        @Override
        public void run() {
          logger.info("Stopping server..");
          try {
            server.stop();
          } catch (GlassFishException e) {
            logger.severe(e.getMessage());
          }
        }
      }, "shutdownHook"));

      // run
      try {
        server.start();

        // for some unknown reason, this doesn't work when in domain.xml
        server.getCommandRunner().run("create-jdbc-resource",
            "--connectionpoolid", "PgPool", "jdbc/OBDDS");

        // create WAR
        ScatteredArchive archive = new ScatteredArchive("obdserver",
            ScatteredArchive.Type.WAR);
        archive.addClassPath(new File("model/target", "classes"));
        archive.addMetadata(new File("model/src/main/resources/META-INF",
            "persistence.xml"));
        archive.addClassPath(new File("rest/target", "classes"));
        archive
            .addMetadata(new File("rest/src/main/webapp/WEB-INF", "web.xml"));

        // deploy the scattered web archive.
        server.getDeployer().deploy(archive.toURI(), "--contextroot=/obd");

        logger.info("Press CTRL^C to exit..");
        Thread.currentThread().join();
      } catch (Exception e) {
        logger.severe(e.getMessage());
      }
    }
  }

}