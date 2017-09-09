package cloud.monitoring.impl.jobs.snmp;

import cloud.monitoring.api.entities.configs.snmp.SnmpConfig;
import org.snmp4j.PDU;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.event.ResponseListener;
import org.snmp4j.smi.VariableBinding;

/**
 * Created by Roman on 09.09.2017 13:03.
 */
public class SnmpResponseListener implements ResponseListener {

    SnmpConfig snmpConfig;

    public SnmpResponseListener(SnmpConfig snmpConfig) {
        this.snmpConfig = snmpConfig;
    }

    @Override
    public void onResponse(ResponseEvent responseEvent) {
        PDU pdu = responseEvent.getResponse();
        if (pdu != null && pdu.getVariableBindings() != null) {
            for (VariableBinding variableBinding : pdu.getVariableBindings()) {
                System.out.println("Object: " + snmpConfig.getObjectID() + " oid: " + variableBinding.getOid().toString() + " value: " + variableBinding.getVariable().toString());
            }
        }
    }
}
