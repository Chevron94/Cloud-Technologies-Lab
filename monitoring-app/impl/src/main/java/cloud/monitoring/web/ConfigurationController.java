package cloud.monitoring.web;

import cloud.monitoring.api.entities.configs.snmp.SnmpConfig;
import cloud.monitoring.beans.ConfigurationBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigInteger;

/**
 * Created by Roman on 24.09.2017 15:30.
 */
@Controller
public class ConfigurationController {

    @Autowired
    ConfigurationBean configurationBean;

    @RequestMapping(path = "/configurations/snmp", method = RequestMethod.POST)
    public ResponseEntity applySNMPConfiguration(@RequestBody SnmpConfig snmpConfig){
        if (configurationBean.applyConfiguration(snmpConfig)){
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("{\n\t\"message\":\"Configuration apply success\"\n}");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body("{\n\t\"message\":\"Configuration apply failed\"\n}");
        }
    }

    @RequestMapping(path = "/configurations/snmp", method = RequestMethod.GET)
    public ResponseEntity getSNMPConfiguration(@RequestParam("object-id")BigInteger objectID){
        SnmpConfig snmpConfig = configurationBean.getSnmpConfigs(objectID);
        if (snmpConfig != null) {
            return ResponseEntity.ok(snmpConfig);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body("{\n\t\"message\":\"No SNMP configurations for object with id "+objectID+"\"\n}");
        }
    }
}
