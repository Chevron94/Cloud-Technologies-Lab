package cloud.monitoring.web;

import cloud.monitoring.api.entities.configs.snmp.SnmpConfig;
import cloud.monitoring.beans.ConfigurationBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Configuration apply failed");
        }
    }
}
