package cloud.monitoring.web;

import cloud.monitoring.beans.MetricBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.Date;

/**
 * Created by Roman on 24.09.2017 15:30.
 */
@RestController
public class MetricController {
    @Autowired
    private MetricBean metricBean;

    @RequestMapping(path = "/metrics", method = RequestMethod.GET)
    public ResponseEntity getLastMetrics(
            @RequestParam(value = "object-id")BigInteger objectID,
            @RequestParam(value = "metric-type-id")BigInteger metricTypeID,
            @RequestParam(value = "count", required = false) Integer count,
            @RequestParam(value = "from-date", required = false)Date date){
            if (count != null){
                return ResponseEntity.ok(metricBean.getMetrics(objectID, metricTypeID, count));
            } else {
                if (date != null) {
                    return ResponseEntity.ok(metricBean.getMetrics(objectID, metricTypeID, date));
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("\"count\" or \"from-date\" parameter should be passed");
                }
            }
    }
}
