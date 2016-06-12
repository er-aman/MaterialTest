package com.example.aman.materialtest.Services;

import com.example.aman.materialtest.logging.L;

import me.tatarka.support.job.JobParameters;
import me.tatarka.support.job.JobService;

/**
 * Created by aman on 9/6/16.
 */
public class MyService extends JobService {
    // Register the service in manifest file
    @Override
    public boolean onStartJob(JobParameters params) {
       L.t(this,"On start job");
        jobFinished(params,false);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
