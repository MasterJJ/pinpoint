/*
 * Copyright 2017 NAVER Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.navercorp.pinpoint.web.vo.stat.chart;

import com.navercorp.pinpoint.web.util.TimeWindow;
import com.navercorp.pinpoint.web.vo.stat.AggreJoinTransactionBo;
import com.navercorp.pinpoint.web.vo.stat.chart.TransactionPoint.UncollectedTransactionPointCreater;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author minwoo.jung
 */
public class ApplicationTransactionChartGroup implements ApplicationStatChartGroup {

    public static final UncollectedTransactionPointCreater UNCOLLECTED_TRANSACTION_POINT = new UncollectedTransactionPointCreater();
    private final Map<ChartType, Chart> transactionChartMap;

    public enum TransactionChartType implements ChartType {
        TRANSACTION_COUNT
    }

    public ApplicationTransactionChartGroup(TimeWindow timeWindow, List<AggreJoinTransactionBo> aggreJoinTransactionBoList) {
        transactionChartMap = new HashMap<>();
        List<Point> transactionList = new ArrayList<>(aggreJoinTransactionBoList.size());

        for (AggreJoinTransactionBo aggreJoinTransactionBo : aggreJoinTransactionBoList) {
            transactionList.add(new TransactionPoint(aggreJoinTransactionBo.getTimestamp(), aggreJoinTransactionBo.getMinTotalCount(), aggreJoinTransactionBo.getMinTotalCountAgentId(), aggreJoinTransactionBo.getMaxTotalCount(), aggreJoinTransactionBo.getMaxTotalCountAgentId(), aggreJoinTransactionBo.getTotalCount()));
        }

        transactionChartMap.put(TransactionChartType.TRANSACTION_COUNT,  new TimeSeriesChartBuilder(timeWindow, UNCOLLECTED_TRANSACTION_POINT).build(transactionList));
    }

    @Override
    public Map<ChartType, Chart> getCharts() {
        return this.transactionChartMap;
    }
}
