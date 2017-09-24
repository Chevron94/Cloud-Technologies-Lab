package cloud.monitoring.jobs.snmp;

import cloud.monitoring.api.entities.configs.snmp.SnmpConfig;
import cloud.monitoring.api.entities.configs.snmp.SnmpMetricConfig;
import cloud.monitoring.beans.MetricBean;
import cloud.monitoring.entities.Metric;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseListener;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import java.io.IOException;

/**
 * Created by Roman on 09.09.2017 12:46.
 */
public class SnmpJob implements Job {
    private Snmp snmp;
    private CommunityTarget communityTarget;
    private PDU pdu;
    private ResponseListener responseListener;
    private boolean configured = false;


    public SnmpJob() {
    }

    private void init(SnmpConfig snmpConfig, MetricBean metricBean) throws IOException {
        TransportMapping transportMapping = new DefaultUdpTransportMapping();
        transportMapping.listen();
        snmp = new Snmp(transportMapping);
        communityTarget = new CommunityTarget();
        communityTarget.setCommunity(new OctetString(snmpConfig.getCommunity()));
        communityTarget.setAddress(new UdpAddress(snmpConfig.getIp() + "/" + snmpConfig.getPort()));
        communityTarget.setRetries(3);
        communityTarget.setTimeout(snmpConfig.getTimeout());
        switch (snmpConfig.getVersion()){
            case 1: communityTarget.setVersion(SnmpConstants.version1); break;
            case 2: communityTarget.setVersion(SnmpConstants.version2c); break;
            case 3: communityTarget.setVersion(SnmpConstants.version3); break;
            default: throw new IllegalArgumentException("Incorrect version");
        }
        communityTarget.setVersion(SnmpConstants.version2c);

        pdu = new PDU();
        for (SnmpMetricConfig metric : snmpConfig.getMetrics()) {
            pdu.add(new VariableBinding(new OID(metric.getOid())));
        }
        pdu.setType(PDU.GET);
        pdu.setMaxRepetitions(50);
        responseListener = new SnmpResponseListener(snmpConfig, metricBean);
        configured = true;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            if (!configured) {
                JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
                init((SnmpConfig) jobDataMap.get("config"), (MetricBean)jobDataMap.get("metricBean"));
            }
            snmp.send(pdu, communityTarget, null, responseListener);
        } catch (IOException e) {
            //
        }
    }
}
