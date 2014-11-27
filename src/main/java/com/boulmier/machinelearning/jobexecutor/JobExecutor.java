/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boulmier.machinelearning.jobexecutor;

import com.boulmier.machinelearning.jobexecutor.config.JobExecutorConfig;
import com.boulmier.machinelearning.jobexecutor.logging.ILogger;
import com.jezhumble.javasysmon.*;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import java.io.IOException;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 *
 * @author Boulmier
 */
public class JobExecutor {

    @SuppressWarnings("static-access")
    public static Options defineOptions() {
        Options softwareOptions = new Options();
        Option ipOption = OptionBuilder.withArgName(JobExecutorConfig.OPTIONS.CMD.SHORTIPFIELD)
                .withLongOpt(JobExecutorConfig.OPTIONS.CMD.LONGIPFIELD)
                .isRequired(JobExecutorConfig.OPTIONS.CMD.ISIPREQUIERED)
                .withDescription(JobExecutorConfig.OPTIONS.CMD.IPDESCRIPTION)
                .hasArg()
                .create(),
                portOption = OptionBuilder.withArgName(JobExecutorConfig.OPTIONS.CMD.SHORTPORTFIELD)
                .withLongOpt(JobExecutorConfig.OPTIONS.CMD.LONGPORTFIELD)
                .withDescription(JobExecutorConfig.OPTIONS.CMD.PORTDESCRIPTION)
                .withType(JobExecutorConfig.OPTIONS.CMD.PORTTYPE)
                .hasArg()
                .create(),
                mongoIpOption = OptionBuilder.withArgName(JobExecutorConfig.OPTIONS.CMD.SHORTMONGOIPFIELD)
                .withLongOpt(JobExecutorConfig.OPTIONS.CMD.LONGMONGOIPFIELD)
                .withDescription(JobExecutorConfig.OPTIONS.CMD.MONGOIPDESCRIPTION)
                .isRequired(JobExecutorConfig.OPTIONS.CMD.ISMONGOIPREQUIERED)
                .hasArg()
                .create(),
                mongoPortOption = OptionBuilder.withArgName(JobExecutorConfig.OPTIONS.CMD.SHORTMONGOPORTFIELD)
                .withLongOpt(JobExecutorConfig.OPTIONS.CMD.LONGMONGOPORTFIELD)
                .withDescription(JobExecutorConfig.OPTIONS.CMD.MONGOPORTDESCRIPTION)
                .withType(int.class)
                .hasArg()
                .create(),
                debugOption = OptionBuilder.withArgName(JobExecutorConfig.OPTIONS.CMD.SHORTDEBUGFIELD)
                .withLongOpt(JobExecutorConfig.OPTIONS.CMD.LONGDEBUGFIELD)
                .create();

        softwareOptions.addOption(ipOption);
        softwareOptions.addOption(portOption);
        softwareOptions.addOption(mongoIpOption);
        softwareOptions.addOption(debugOption);
        softwareOptions.addOption(mongoPortOption);

        return softwareOptions;
    }

    public static JavaSysMon sysMon;
    public static boolean debugState;
    public static ILogger logger;

    public static void main(String[] args) throws ParseException, IOException, InterruptedException {
        final String QUEUE_NAME = "masters2";
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        channel.basicQos(1);

        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(QUEUE_NAME, false, consumer);

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());

            System.out.println(" [x] Received '" + message + "'");
            Thread.sleep(1000);//want to do some very very long stuff
            System.out.println(" [x] Done");

            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
    }



/*Options options = defineOptions();
 sysMon = new JavaSysMon();
 InetAddress vmscheduler_ip, mongodb_ip;
 Integer vmscheduler_port = null, mongodb_port;
 CommandLineParser parser = new BasicParser();
 try {
 CommandLine cmd = parser.parse(options, args);
 if (cmd.hasOption(JobExecutorConfig.OPTIONS.CMD.LONGPORTFIELD)) {
 vmscheduler_port = Integer.valueOf(cmd.getOptionValue(JobExecutorConfig.OPTIONS.CMD.LONGPORTFIELD));
 }
 mongodb_port = (int) (cmd.hasOption(JobExecutorConfig.OPTIONS.CMD.LONGMONGOPORTFIELD) ? cmd.hasOption(JobExecutorConfig.OPTIONS.CMD.LONGMONGOPORTFIELD) : JobExecutorConfig.OPTIONS.LOGGING.MONGO_DEFAULT_PORT);
 vmscheduler_ip = InetAddress.getByName(cmd.getOptionValue(JobExecutorConfig.OPTIONS.CMD.LONGIPFIELD));
 mongodb_ip = InetAddress.getByName(cmd.getOptionValue(JobExecutorConfig.OPTIONS.CMD.LONGMONGOIPFIELD));
 debugState = cmd.hasOption(JobExecutorConfig.OPTIONS.CMD.LONGDEBUGFIELD);
            
 logger = LoggerFactory.getLogger();
 logger.info("Attempt to connect on master @" + vmscheduler_ip + ":" + vmscheduler_port);
            
 new RequestConsumer().start();
            
 } catch (MissingOptionException moe) {
            
 HelpFormatter help = new HelpFormatter();
 help.printHelp(JobExecutor.class.getSimpleName(), options);
            
 } catch (UnknownHostException ex) {
            
 } finally {
 Runtime.getRuntime().addShutdownHook(new Thread() {
 @Override
 public void run() {
 logger.info("JobExeutor is shutting down");
 }
 });
 }
 }
 */
}
