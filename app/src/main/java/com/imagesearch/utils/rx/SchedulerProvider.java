package com.imagesearch.utils.rx;

import io.reactivex.Scheduler;

/**
 * Created by Varun on 28,July,2018
 */

public interface SchedulerProvider {

    Scheduler ui();

    Scheduler computation();

    Scheduler io();

}
