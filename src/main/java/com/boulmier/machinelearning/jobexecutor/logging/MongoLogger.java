/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boulmier.machinelearning.jobexecutor.logging;

import com.boulmier.machinelearning.jobexecutor.config.JobExecutorConfig;
import com.boulmier.machinelearning.jobexecutor.mongodb.Mongo;
import com.boulmier.machinelearning.jobexecutor.mongodb.MongoConfig;
import java.net.UnknownHostException;
import org.log4mongo.MongoDbAppender;

/**
 *
 * @author Boulmier
 */
public class MongoLogger extends ILogger {

    private static final MongoDbAppender mApp = new MongoDbAppender();
    private static boolean available = true;

    static {
        try {
            mApp.setDatabaseName(JobExecutorConfig.OPTIONS.LOGGING.MONGO_DEFAULT_DATABASE);
            mApp.setCollectionName(JobExecutorConfig.OPTIONS.LOGGING.MONGO_DEFAULT_COLLECTION);
            mApp.setCollection(Mongo.getInstance(MongoConfig.build()).getLoggingCollection());
        } catch (UnknownHostException | NullPointerException ex) {
            available = false;
        }
    }

    private MongoLogger() {
        super(mApp);
    }

    public static MongoLogger getLoggerInstance() {
        if (available) {
            return new MongoLogger();
        } else {
            return null;
        }
    }

}
