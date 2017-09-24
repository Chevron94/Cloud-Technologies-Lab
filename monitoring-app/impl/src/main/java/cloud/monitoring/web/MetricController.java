package cloud.monitoring.web;

import cloud.monitoring.api.entities.rest.MetricResponse;
import cloud.monitoring.impl.beans.MetricBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ws.rs.BadRequestException;
import java.math.BigInteger;
import java.util.Date;

/**
 * Created by Roman on 24.09.2017 15:30.
 */
@Component
public class MetricController {
    @Autowired
    MetricBean metricBean;

    @RequestMapping(path = "/metrics", method = RequestMethod.GET)
    public @ResponseBody MetricResponse getLastMetrics(
            @RequestParam(value = "object-id")BigInteger objectID,
            @RequestParam(value = "metric-type-id")BigInteger metricTypeID,
            @RequestParam(value = "count", required = false) Integer count,
            @RequestParam(value = "from-date", required = false)Date date){
            if (count != null){
                return metricBean.getMetrics(objectID, metricTypeID, count);
            } else {
                if (date != null) {
                    return metricBean.getMetrics(objectID, metricTypeID, date);
                } else {
                    throw new BadRequestException("\"count\" or \"from-date\" parameter should be passed");
                }
            }
    }
}
