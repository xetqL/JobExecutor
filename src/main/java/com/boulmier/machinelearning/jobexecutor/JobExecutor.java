/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boulmier.machinelearning.jobexecutor;

import com.boulmier.machinelearning.jobexecutor.config.JobExecutorConfig;
import com.boulmier.machinelearning.jobexecutor.logging.ILogger;
import com.boulmier.machinelearning.jobexecutor.logging.LoggerFactory;
import com.jezhumble.javasysmon.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
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
        Option 
            ipOption = OptionBuilder.withArgName(JobExecutorConfig.OPTIONS.CMD.SHORTIPFIELD)
                .withLongOpt(JobExecutorConfig.OPTIONS.CMD.LONGIPFIELD)
                .isRequired(JobExecutorConfig.OPTIONS.CMD.ISIPREQUIERED)
                .withDescription(JobExecutorConfig.OPTIONS.CMD.IPDESCRIPTION)
                .hasArg()
                .create(),
            portOption = OptionBuilder.withArgName(JobExecutorConfig.OPTIONS.CMD.SHORTPORTFIELD)
                .withLongOpt(JobExecutorConfig.OPTIONS.CMD.LONGPORTFIELD)
                .isRequired(JobExecutorConfig.OPTIONS.CMD.ISPORTREQUIERED)
                .withDescription(JobExecutorConfig.OPTIONS.CMD.PORTDESCRIPTION)
                .withType(JobExecutorConfig.OPTIONS.CMD.PORTTYPE)
                .hasArg()
                .create(),
            mongoOption = OptionBuilder.withArgName(JobExecutorConfig.OPTIONS.CMD.SHORTMONGOFIELD)
                .withLongOpt(JobExecutorConfig.OPTIONS.CMD.LONGMONGOFIELD)
                .isRequired(JobExecutorConfig.OPTIONS.CMD.ISMONGOREQUIERED)
                .hasArg()
                .create(),
            debugOption = OptionBuilder.withArgName(JobExecutorConfig.OPTIONS.CMD.SHORTDEBUGFIELD)
                .withLongOpt(JobExecutorConfig.OPTIONS.CMD.LONGDEBUGFIELD)
                .create();
        softwareOptions.addOption(ipOption);
        softwareOptions.addOption(portOption);
        softwareOptions.addOption(mongoOption);
        softwareOptions.addOption(debugOption);
        
        return softwareOptions;
    }
    
    public static JavaSysMon sysMon;
    public static boolean debugState;
    public static ILogger logger;
    public static void main(String[] args) throws ParseException, IOException {
        Options options = defineOptions();
        sysMon = new JavaSysMon();
        CommandLineParser parser = new BasicParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            int vmscheduler_port       = Integer.valueOf(cmd.getOptionValue(JobExecutorConfig.OPTIONS.CMD.LONGPORTFIELD));
            InetAddress vmscheduler_ip = InetAddress.getByName(cmd.getOptionValue(JobExecutorConfig.OPTIONS.CMD.LONGIPFIELD));
            debugState = cmd.hasOption(JobExecutorConfig.OPTIONS.CMD.LONGDEBUGFIELD);
            logger = LoggerFactory.getLogger();
            logger.info("Attempt to connect on master @"+vmscheduler_ip+":"+vmscheduler_port);
        } catch (MissingOptionException moe) {
            HelpFormatter help = new HelpFormatter();
            help.printHelp(JobExecutor.class.getSimpleName(), options);
        } catch (UnknownHostException ex) {}
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                logger.info("Leaving system");
            }
        });
    }
    
}
