/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bachelor.boulmier.workmaster;

import com.bachelor.boulmier.workmaster.queuing.QueuingService;
import com.bachelor.boulmier.workmaster.config.MasterConfig;
import com.boulmier.machinelearning.jobexecutor.logging.ConsoleLogger;
import com.boulmier.machinelearning.jobexecutor.logging.ILogger;
import com.boulmier.machinelearning.jobexecutor.logging.LoggerFactory;
import com.jezhumble.javasysmon.JavaSysMon;
import java.io.IOException;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

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
    public static boolean 
            cliEnabled = false,
            debug = false,
            verbose = false;
    private static String webServer = MasterConfig.DEFAULT.DEFAULTWS;
    
    
    public static JavaSysMon sysMon = new JavaSysMon();
    public static ILogger logger;
    public static QueuingService queuingService = QueuingService.get();
    
    public static void printHelp() {
        HelpFormatter help = new HelpFormatter();
        help.printHelp(WorkMaster.class.getSimpleName(), options);
    }

    public static void main(String[] args) throws IOException {
        defineOptions();
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
            queuingService.send(new Request("DIMLP", "Discretized Interpretable Multi Layer Perceptron"));
        } catch (ParseException pe) {
            System.err.println(pe.getMessage());
            printHelp();
        }
    }
}
