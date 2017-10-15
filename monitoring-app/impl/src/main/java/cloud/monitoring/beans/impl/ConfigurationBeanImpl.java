package cloud.monitoring.beans.impl;

import cloud.monitoring.api.entities.configs.Config;
import cloud.monitoring.api.entities.configs.snmp.SnmpConfig;
import cloud.monitoring.api.entities.configs.snmp.SnmpMetricConfig;
import cloud.monitoring.beans.ConfigurationBean;
import cloud.monitoring.entities.BasicConfigEntity;
import cloud.monitoring.entities.SnmpBasicConfigEntity;
import cloud.monitoring.entities.SnmpMetricConfigEntity;
import cloud.monitoring.jobs.JobPool;
import cloud.monitoring.repositories.BasicConfigRepository;
import cloud.monitoring.repositories.SnmpBasicConfigRepository;
import cloud.monitoring.repositories.SnmpMetricConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roman on 24.09.2017 15:51.
 */
@Component
public class ConfigurationBeanImpl implements ConfigurationBean {
    @Autowired
    private BasicConfigRepository configRepository;
    @Autowired
    private SnmpMetricConfigRepository snmpMetricConfigRepository;
    @Autowired
    private SnmpBasicConfigRepository snmpBasicConfigRepository;
    @Autowired
    private JobPool jobPool;

    @Override
    @Transactional
    public Boolean applyConfiguration(Config config) {
        try {
            jobPool.createJob(config);
            BasicConfigEntity basicConfigEntity = configRepository.getBasicConfigsByObjectID(config.getObjectID());
            if (basicConfigEntity == null) {
                createBasicConfiguration(config);
            } else {
                basicConfigEntity = updateBasicConfiguration(basicConfigEntity, config);
                if (config instanceof SnmpConfig) {
                    List<SnmpBasicConfigEntity> snmpBasicConfigEntities = basicConfigEntity.getSnmpBaseConfigs();
                    if (snmpBasicConfigEntities == null || snmpBasicConfigEntities.isEmpty()) {
                        createBasicSnmpConfiguration(basicConfigEntity, (SnmpConfig) config);
                    } else {
                        updateSnmpBasicConfiguration(snmpBasicConfigEntities.get(0), (SnmpConfig) config);
                    }
                }
            }
            return true;
        } catch (IllegalStateException ex) {
            return false;
        }
    }

    @Override
    public void reloadConfigurations() {
        jobPool.clear();
        List<BasicConfigEntity> basicConfigEntities = configRepository.findAll();
        for (BasicConfigEntity basicConfigEntity: basicConfigEntities){
            jobPool.createJob(buildSnmpConfig(basicConfigEntity));
        }
    }

    @Override
    public SnmpConfig getSnmpConfigs(BigInteger objectID) {
        BasicConfigEntity basicConfigEntity = configRepository.getBasicConfigsByObjectID(objectID);
        if (basicConfigEntity != null) {
            return buildSnmpConfig(basicConfigEntity);
        } else {
            return null;
        }
    }

    private BasicConfigEntity updateBasicConfiguration(BasicConfigEntity basicConfigEntity, Config config){
        basicConfigEntity.setObjectID(config.getObjectID());
        basicConfigEntity.setIp(config.getIp());
        basicConfigEntity.setCron(config.getCron());
        basicConfigEntity = configRepository.save(basicConfigEntity);
        return basicConfigEntity;
    }

    private SnmpBasicConfigEntity updateSnmpBasicConfiguration(SnmpBasicConfigEntity entity, SnmpConfig config){
        entity.setCommunity(config.getCommunity());
        entity.setPort(config.getPort());
        entity.setVersion(config.getVersion());
        entity.setTimeout(config.getTimeout());
        entity = snmpBasicConfigRepository.save(entity);

        List<SnmpMetricConfigEntity> metricConfigEntities = entity.getSnmpMetricConfigEntities();
        List<SnmpMetricConfig> metricConfigs = config.getMetrics();
        List<SnmpMetricConfigEntity> result = new ArrayList<>();
        if (metricConfigEntities.size() == metricConfigs.size()) {
            for (int i= 0; i<metricConfigs.size(); i++){
                result.add(updateMetricConfigEntity(metricConfigEntities.get(i), metricConfigs.get(i), false));
            }
        } else {
            snmpMetricConfigRepository.delete(metricConfigEntities);
            for (int i= 0; i<metricConfigs.size(); i++){
                SnmpMetricConfigEntity snmpMetricConfigEntity = new SnmpMetricConfigEntity();
                snmpMetricConfigEntity.setSnmpBasicConfigEntity(entity);
                result.add(updateMetricConfigEntity(snmpMetricConfigEntity, metricConfigs.get(i), false));
            }
        }
        snmpMetricConfigRepository.save(result);
        entity.setSnmpMetricConfigEntities(result);
        return entity;
    }

    private SnmpMetricConfigEntity updateMetricConfigEntity(SnmpMetricConfigEntity entity, SnmpMetricConfig metricConfig, Boolean commit){
        entity.setMetricTypeID(metricConfig.getMetricID());
        entity.setOid(metricConfig.getOid());
        if (commit){
            entity = snmpMetricConfigRepository.save(entity);
        }
        return entity;
    }

    private void createBasicConfiguration(Config config){
        BasicConfigEntity basicConfigEntity = updateBasicConfiguration(new BasicConfigEntity(), config);
        if (config instanceof SnmpConfig) {
            createBasicSnmpConfiguration(basicConfigEntity, (SnmpConfig) config);
        }
    }

    private void createBasicSnmpConfiguration(BasicConfigEntity basicConfigEntity, SnmpConfig config){
        SnmpBasicConfigEntity snmpBasicConfigEntity = new SnmpBasicConfigEntity();
        snmpBasicConfigEntity.setBasicConfigEntity(basicConfigEntity);
        updateSnmpBasicConfiguration(snmpBasicConfigEntity, config);
    }

    private SnmpConfig buildSnmpConfig(BasicConfigEntity basicConfigEntity){
        SnmpConfig snmpConfig = new SnmpConfig();
        snmpConfig.setObjectID(basicConfigEntity.getObjectID());
        snmpConfig.setIp(basicConfigEntity.getIp());
        snmpConfig.setCron(basicConfigEntity.getCron());
        SnmpBasicConfigEntity snmpBasicConfigEntity = basicConfigEntity.getSnmpBaseConfigs().get(0);
        snmpConfig.setVersion(snmpBasicConfigEntity.getVersion());
        snmpConfig.setTimeout(snmpBasicConfigEntity.getTimeout());
        snmpConfig.setPort(snmpBasicConfigEntity.getPort());
        snmpConfig.setCommunity(snmpBasicConfigEntity.getCommunity());

        List<SnmpMetricConfig> metricConfigs = new ArrayList<>();
        for (SnmpMetricConfigEntity metricConfigEntity: snmpBasicConfigEntity.getSnmpMetricConfigEntities()){
            SnmpMetricConfig metricConfig = new SnmpMetricConfig();
            metricConfig.setOid(metricConfigEntity.getOid());
            metricConfig.setMetricID(metricConfigEntity.getMetricTypeID());
            metricConfigs.add(metricConfig);
        }
        snmpConfig.setMetrics(metricConfigs);
        return snmpConfig;
    }
}
