package com.bachelor.boulmier.workmaster.config;

import java.util.regex.Pattern;

/**
 *
 * @author antho
 */
public interface MasterConfig {

    public interface CMD {

        public final String DEBUGLONGOPT = "debug",
                DEBUGDESC = "Enable debugging mode";

        public final String MAXVMLONGOPT = "max-vm",
                MAXVMDESC = "Maximum number of worker available",
                MAXVMARG = "max number";
        public final Class MAXVMTYPE = Short.class;

        public final String VERBOSELONGOPT = "verbose",
                VERBOSEDESC = "Set if the software is verbose";
        public final String CLILONGOPT = "with-cli",
                CLIDESC = "Enable the command line interface in addition to the web interface";

        public final String REMOTEWSLONGOPT = "remote",
                REMOTEWSDESC = "specify the ip:port of the remote web server",
                REMOTEWSARG = "ip:port";
        public final String HELPLONGOPT = "help",
                HELPDESC = "display help";
    }

    public interface DEFAULT {

        public final String DEFAULTWS = "127.0.0.1:5000";
        public final Pattern IP_PORT_PATTERN
                = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5]):(102[5-9]|10[3-9][0-9]|1[1-9][0-9]{2}|[2-9][0-9]{3}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5])$"); //from mkyong.com
    }
}
