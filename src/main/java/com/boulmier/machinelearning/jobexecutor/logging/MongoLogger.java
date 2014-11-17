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
import org.apache.log4j.Level;
import org.log4mongo.MongoDbAppender;

/**
 *
 * @author Boulmier
 */
public class MongoLogger extends ILogger {

    private MongoLogger(MongoDbAppender mApp) {
        super(mApp);
    }

    public static MongoLogger getLoggerInstance() {
        MongoDbAppender mApp;
        try {
            mApp = new MongoDbAppender();
            mApp.setDatabaseName(JobExecutorConfig.OPTIONS.LOGGING.MONGO_DEFAULT_DATABASE);
            mApp.setCollectionName(JobExecutorConfig.OPTIONS.LOGGING.MONGO_DEFAULT_COLLECTION);
            mApp.setCollection(Mongo.getInstance(MongoConfig.build()).getLoggingCollection());
            mApp.setThreshold(Level.INFO);
        } catch (UnknownHostException ex) {
            return null;
        }
        return new MongoLogger(mApp);
    }

}
