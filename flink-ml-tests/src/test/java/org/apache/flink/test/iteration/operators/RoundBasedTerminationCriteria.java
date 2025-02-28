/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.flink.test.iteration.operators;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.iteration.IterationListener;
import org.apache.flink.util.Collector;

/** An termination criteria function that asks to stop after the specialized round. */
public class RoundBasedTerminationCriteria
        implements FlatMapFunction<EpochRecord, Integer>, IterationListener<Integer> {

    private final int maxRound;

    public RoundBasedTerminationCriteria(int maxRound) {
        this.maxRound = maxRound;
    }

    @Override
    public void flatMap(EpochRecord integer, Collector<Integer> collector) throws Exception {}

    @Override
    public void onEpochWatermarkIncremented(
            int epochWatermark, Context context, Collector<Integer> collector) {
        if (epochWatermark < maxRound) {
            collector.collect(0);
        }
    }

    @Override
    public void onIterationTerminated(Context context, Collector<Integer> collector) {}
}
