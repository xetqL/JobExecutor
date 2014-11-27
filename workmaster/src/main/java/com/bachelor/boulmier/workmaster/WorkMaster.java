/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bachelor.boulmier.workmaster;

import com.bachelor.boulmier.workmaster.queuing.QueuingService;
import com.bachelor.boulmier.workmaster.config.MasterConfig;
import com.boulmier.machinelearning.jobexecutor.logging.ILogger;
import com.jezhumble.javasysmon.JavaSysMon;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.FlowListener;
import com.rabbitmq.client.MessageProperties;
import com.rabbitmq.client.ReturnListener;
import java.io.IOException;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;

/**
 *
 * @author antho
 */
public class WorkMaster {

    private static Options options;

    @SuppressWarnings("static-access")
    private static void defineOptions() {
        options = new Options();

        Option maxVMOption = OptionBuilder
                .withLongOpt(MasterConfig.CMD.MAXVMLONGOPT)
                .withArgName(MasterConfig.CMD.MAXVMARG)
                .withDescription(MasterConfig.CMD.MAXVMDESC)
                .withType(MasterConfig.CMD.MAXVMTYPE)
                .hasArg()
                .create(),
                remoteWebServer = OptionBuilder
                .withLongOpt(MasterConfig.CMD.REMOTEWSLONGOPT)
                .withArgName(MasterConfig.CMD.REMOTEWSARG)
                .withDescription(MasterConfig.CMD.REMOTEWSDESC)
                .hasArg()
                .create(),
                verboseOption = OptionBuilder
                .withLongOpt(MasterConfig.CMD.VERBOSELONGOPT)
                .withDescription(MasterConfig.CMD.VERBOSEDESC)
                .create(),
                debugOption = OptionBuilder
                .withDescription(MasterConfig.CMD.DEBUGDESC)
                .withLongOpt(MasterConfig.CMD.DEBUGLONGOPT)
                .create(),
                cli = OptionBuilder
                .withLongOpt(MasterConfig.CMD.CLILONGOPT)
                .withDescription(MasterConfig.CMD.CLIDESC)
                .create(),
                helpOption = OptionBuilder
                .withLongOpt(MasterConfig.CMD.HELPLONGOPT)
                .withDescription(MasterConfig.CMD.HELPDESC)
                .create();

        options.addOption(maxVMOption);
        options.addOption(remoteWebServer);
        options.addOption(cli);
        options.addOption(debugOption);
        options.addOption(verboseOption);
        options.addOption(helpOption);
    }

    private static int maxVM = 6;

    public static boolean cliEnabled = false,
            debug = false,
            verbose = false;
    private static String webServer = MasterConfig.DEFAULT.DEFAULTWS;

    public static JavaSysMon sysMon = new JavaSysMon();
    public static ILogger logger;
    public static QueuingService queuingService;

    public static void printHelp() {
        HelpFormatter help = new HelpFormatter();
        help.printHelp(WorkMaster.class.getSimpleName(), options);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final String QUEUE_NAME = "basicTest";
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        channel.addConfirmListener(new ConfirmListener() {

            @Override
            public void handleAck(long arg0, boolean arg1) throws IOException {
                System.err.println("A MESSAGE HAS BEEN  ACK <---");
            }

            @Override
            public void handleNack(long arg0, boolean arg1) throws IOException {
                System.err.println("A MESSAGE HAS BEEN NACK <---");

            }
        });
        String message = "hello world";

        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");
        for (int i = 0; i < 10000; i++) {
            Thread.sleep(1000);
        }
        channel.close();
        connection.close();
        /*defineOptions();
         CommandLineParser parser = new BasicParser();
         CommandLine cmd;
         try {
         cmd = parser.parse(options, args);

         if (cmd.hasOption(MasterConfig.CMD.CLILONGOPT)) {
         cliEnabled = true;
         }
         if (cmd.hasOption(MasterConfig.CMD.DEBUGLONGOPT)) {
         debug = true;
         }
         if (cmd.hasOption(MasterConfig.CMD.MAXVMLONGOPT)) {
         maxVM = Integer.valueOf(cmd.getOptionValue(MasterConfig.CMD.MAXVMLONGOPT));
         }
         if (cmd.hasOption(MasterConfig.CMD.VERBOSELONGOPT)) {
         verbose = true;
         }
         if (cmd.hasOption(MasterConfig.CMD.REMOTEWSLONGOPT)) {
         webServer = cmd.getOptionValue(MasterConfig.CMD.REMOTEWSLONGOPT);
         if(!webServer.matches("^"+MasterConfig.DEFAULT.IP_PATTERN+":"+MasterConfig.DEFAULT.PORT_PATTERN))
         throw new ParseException("The given web server IP was wrong");
         }

         if (cmd.hasOption(MasterConfig.CMD.HELPLONGOPT)) {
         printHelp();
         }
         logger = LoggerFactory.getLogger();
         queuingService = QueuingService.get();

         queuingService.send( RequestBuilder.builder().withExecutableName("DIMLP").create() );
         queuingService.send( RequestBuilder.builder().withExecutableName("ELM").create() );
         queuingService.send( RequestBuilder.builder().withExecutableName("HYBRID").create() );

         } catch (ParseException pe) {
         System.err.println(pe.getMessage());
         printHelp();
         }*/
    }
}
