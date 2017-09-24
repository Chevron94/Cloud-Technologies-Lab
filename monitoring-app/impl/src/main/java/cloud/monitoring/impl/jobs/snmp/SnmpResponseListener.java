package cloud.monitoring.impl.jobs.snmp;

import cloud.monitoring.api.entities.configs.snmp.SnmpConfig;
import cloud.monitoring.api.entities.configs.snmp.SnmpMetricConfig;
import cloud.monitoring.impl.entities.Metric;
import cloud.monitoring.impl.repositories.MetricRepository;
import org.apache.log4j.Logger;
import org.snmp4j.PDU;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.event.ResponseListener;
import org.snmp4j.smi.VariableBinding;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Roman on 09.09.2017 13:03.
 */
public class SnmpResponseListener implements ResponseListener {

    private static final Logger LOGGER = Logger.getLogger(SnmpResponseListener.class);

    private Map<String, BigInteger> metricsMapping;

    public SnmpResponseListener(SnmpConfig snmpConfig) {
        metricsMapping = new HashMap<>();
        for (SnmpMetricConfig metricConfig:snmpConfig.getMetrics()){
            metricsMapping.put(metricConfig.getOid(), metricConfig.getMetricID());
        }
    }

    @Override
    public void onResponse(ResponseEvent responseEvent) {
        PDU pdu = responseEvent.getResponse();
        if (pdu != null && pdu.getVariableBindings() != null) {
            for (VariableBinding variableBinding : pdu.getVariableBindings()) {
                Metric metric = new Metric();
                metric.setDate(new Date());
                metric.setValue(new BigDecimal(variableBinding.getVariable().toString()));
                metric.setMetricTypeID(metricsMapping.get(variableBinding.getOid().toString()));
                LOGGER.debug("Collected metric: "+ metric);
                //todo: store metrics
            }
        }
    }
}
