package cloud.monitoring.web;

import cloud.monitoring.api.entities.configs.snmp.SnmpConfig;
import cloud.monitoring.impl.beans.ConfigurationBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.ws.rs.core.Response;

/**
 * Created by Roman on 24.09.2017 15:30.
 */
@Component
public class ConfigurationController {

    @Autowired
    ConfigurationBean configurationBean;

    @RequestMapping(path = "/configurations/snmp", method = RequestMethod.POST)
    public Response applySNMPConfiguration(SnmpConfig snmpConfig){
        if (configurationBean.applyConfiguration(snmpConfig)){
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Configuration apply failed").build();
        }
    }
}
