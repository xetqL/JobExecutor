/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boulmier.machinelearning.jobexecutor;

import com.boulmier.machinelearning.jobexecutor.config.JobExecutorConfig;
import com.boulmier.machinelearning.jobexecutor.consumer.RequestConsumer;
import com.boulmier.machinelearning.jobexecutor.encrypted.AES;
import com.boulmier.machinelearning.jobexecutor.job.Job;
import com.boulmier.machinelearning.jobexecutor.logging.ILogger;
import com.boulmier.machinelearning.jobexecutor.logging.LoggerFactory;
import com.boulmier.machinelearning.request.ExecutableName;
import com.boulmier.machinelearning.request.Request;
import com.boulmier.machinelearning.request.RequestBuilder;
import com.boulmier.machinelearning.request.RequestProperty;
import com.jezhumble.javasysmon.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.MissingOptionException;
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
                decryptKeyOption = OptionBuilder.withArgName("key").withLongOpt("decrypt-key").isRequired(true).hasArg(true).create(),
                debugOption = OptionBuilder.withArgName(JobExecutorConfig.OPTIONS.CMD.SHORTDEBUGFIELD)
                .withLongOpt(JobExecutorConfig.OPTIONS.CMD.LONGDEBUGFIELD)
                .create();
        
        softwareOptions.addOption(ipOption);
        softwareOptions.addOption(portOption);
        softwareOptions.addOption(mongoIpOption);
        softwareOptions.addOption(debugOption);
        softwareOptions.addOption(mongoPortOption);
        softwareOptions.addOption(decryptKeyOption);
        return softwareOptions;
    }
    
    public static JavaSysMon sysMon;
    public static boolean debugState;
    public static ILogger logger;
    public static String decryptKey;

    public static void main(String[] args) throws ParseException, IOException, InterruptedException {
        Options options = defineOptions();
        sysMon = new JavaSysMon();
        InetAddress vmscheduler_ip, mongodb_ip = null;
        Integer vmscheduler_port = null, mongodb_port = null;
        CommandLineParser parser = new BasicParser();
        
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(JobExecutorConfig.OPTIONS.CMD.LONGPORTFIELD)) {
                vmscheduler_port = Integer.valueOf(cmd.getOptionValue(JobExecutorConfig.OPTIONS.CMD.LONGPORTFIELD));
            }
            mongodb_port = (int) (cmd.hasOption(JobExecutorConfig.OPTIONS.CMD.LONGMONGOPORTFIELD) ? cmd.hasOption(JobExecutorConfig.OPTIONS.CMD.LONGMONGOPORTFIELD) : JobExecutorConfig.OPTIONS.LOGGING.MONGO_DEFAULT_PORT);
            mongodb_ip = InetAddress.getByName(cmd.getOptionValue(JobExecutorConfig.OPTIONS.CMD.LONGMONGOIPFIELD));
            
            vmscheduler_ip = InetAddress.getByName(cmd.getOptionValue(JobExecutorConfig.OPTIONS.CMD.LONGIPFIELD));
            
            decryptKey = cmd.getOptionValue("decrypt-key");
            
            debugState = cmd.hasOption(JobExecutorConfig.OPTIONS.CMD.LONGDEBUGFIELD);
            
            logger = LoggerFactory.getLogger();
            logger.info("Attempt to connect on master @" + vmscheduler_ip + ":" + vmscheduler_port);
            
            new RequestConsumer().start();

        } catch (MissingOptionException moe) {
            logger.error(moe.getMissingOptions() + " are missing");
            HelpFormatter help = new HelpFormatter();
            help.printHelp(JobExecutor.class.getSimpleName(), options);
            
        } catch (UnknownHostException ex) {
            logger.error(ex.getMessage());
        } finally {
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    logger.info("JobExeutor is shutting down");
                }
            });
        }
    }
}
