package cloud.monitoring.beans.impl;

import cloud.monitoring.api.entities.configs.Config;
import cloud.monitoring.api.entities.configs.cli.CLIConfig;
import cloud.monitoring.api.entities.configs.cli.CLIMetricConfig;
import cloud.monitoring.api.entities.configs.snmp.SnmpConfig;
import cloud.monitoring.api.entities.configs.snmp.SnmpMetricConfig;
import cloud.monitoring.beans.ConfigurationBean;
import cloud.monitoring.entities.*;
import cloud.monitoring.jobs.JobPool;
import cloud.monitoring.repositories.*;
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
    private CliBasicConfigRepository cliBasicConfigRepository;
    @Autowired
    private CliMetricConfigRepository cliMetricConfigRepository;
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
                } else {
                    if (config instanceof CLIConfig) {
                        List<CliBasicConfigEntity> cliBasicConfigEntities = basicConfigEntity.getCliBasicConfigEntities();
                        if (cliBasicConfigEntities == null || cliBasicConfigEntities.isEmpty()) {
                            createBasicCliConfiguration(basicConfigEntity, (CLIConfig) config);
                        } else {
                            updateCliBasicConfiguration(cliBasicConfigEntities.get(0), (CLIConfig) config);
                        }
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
            if (basicConfigEntity.getSnmpBaseConfigs() != null && basicConfigEntity.getSnmpBaseConfigs().size() > 0) {
                jobPool.createJob(buildSnmpConfig(basicConfigEntity));
            }
            if (basicConfigEntity.getCliBasicConfigEntities() != null && basicConfigEntity.getCliBasicConfigEntities().size() > 0) {
                jobPool.createJob(buildCliConfig(basicConfigEntity));
            }
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

    @Override
    public CLIConfig getCliConfigs(BigInteger objectID) {
        BasicConfigEntity basicConfigEntity = configRepository.getBasicConfigsByObjectID(objectID);
        if (basicConfigEntity != null) {
            return buildCliConfig(basicConfigEntity);
        } else {
            return null;
        }
    }

    private BasicConfigEntity updateBasicConfiguration(BasicConfigEntity basicConfigEntity, Config config){
        basicConfigEntity.setObjectID(config.getObjectID());
        basicConfigEntity.setIp(config.getIp());
        basicConfigEntity = configRepository.save(basicConfigEntity);
        return basicConfigEntity;
    }

    private SnmpBasicConfigEntity updateSnmpBasicConfiguration(SnmpBasicConfigEntity entity, SnmpConfig config){
        entity.setCommunity(config.getCommunity());
        entity.setPort(config.getPort());
        entity.setCron(config.getCron());
        entity.setVersion(config.getVersion());
        entity.setTimeout(config.getTimeout());
        entity = snmpBasicConfigRepository.save(entity);

        List<SnmpMetricConfigEntity> metricConfigEntities = entity.getSnmpMetricConfigEntities();
        List<SnmpMetricConfig> metricConfigs = config.getMetrics();
        List<SnmpMetricConfigEntity> result = new ArrayList<>();
        if (metricConfigEntities != null && metricConfigEntities.size() == metricConfigs.size()) {
            for (int i= 0; i<metricConfigs.size(); i++){
                result.add(updateMetricConfigEntity(metricConfigEntities.get(i), metricConfigs.get(i), false));
            }
        } else {
            if (metricConfigEntities != null) {
                snmpMetricConfigRepository.delete(metricConfigEntities);
            }
            for (SnmpMetricConfig metricConfig : metricConfigs) {
                SnmpMetricConfigEntity snmpMetricConfigEntity = new SnmpMetricConfigEntity();
                snmpMetricConfigEntity.setSnmpBasicConfigEntity(entity);
                result.add(updateMetricConfigEntity(snmpMetricConfigEntity, metricConfig, false));
            }
        }
        snmpMetricConfigRepository.save(result);
        entity.setSnmpMetricConfigEntities(result);
        return entity;
    }

    private CliBasicConfigEntity updateCliBasicConfiguration(CliBasicConfigEntity entity, CLIConfig cliConfig){
        entity.setPort(cliConfig.getPort());
        entity.setTimeout(cliConfig.getTimeout());
        entity.setPassword(cliConfig.getPassword());
        entity.setLogin(cliConfig.getLogin());
        entity.setCron(cliConfig.getCron());
        entity = cliBasicConfigRepository.save(entity);

        List<CliMetricConfigEntity> metricConfigEntities = entity.getCliMetricConfigEntities();
        List<CLIMetricConfig> metricConfigs = cliConfig.getCliMetricConfigs();
        List<CliMetricConfigEntity> result = new ArrayList<>();
        if (metricConfigEntities != null && metricConfigEntities.size() == metricConfigs.size()) {
            for (int i= 0; i<metricConfigs.size(); i++){
                result.add(updateMetricConfigEntity(metricConfigEntities.get(i), metricConfigs.get(i), false));
            }
        } else {
            if (metricConfigEntities != null) {
                cliMetricConfigRepository.delete(metricConfigEntities);
            }
            for (CLIMetricConfig metricConfig : metricConfigs) {
                CliMetricConfigEntity cliMetricConfigEntity = new CliMetricConfigEntity();
                cliMetricConfigEntity.setCliBasicConfigEntity(entity);
                result.add(updateMetricConfigEntity(cliMetricConfigEntity, metricConfig, false));
            }
        }
        cliMetricConfigRepository.save(result);
        entity.setCliMetricConfigEntities(result);
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

    private CliMetricConfigEntity updateMetricConfigEntity(CliMetricConfigEntity entity, CLIMetricConfig metricConfig, Boolean commit){
        entity.setMetricTypeID(metricConfig.getMetricID());
        entity.setCommand(metricConfig.getCommand());
        entity.setRegexp(metricConfig.getRegexp());
        if (commit){
            entity = cliMetricConfigRepository.save(entity);
        }
        return entity;
    }

    private void createBasicConfiguration(Config config){
        BasicConfigEntity basicConfigEntity = updateBasicConfiguration(new BasicConfigEntity(), config);
        if (config instanceof SnmpConfig) {
            createBasicSnmpConfiguration(basicConfigEntity, (SnmpConfig) config);
        }
    }

    private void createBasicCliConfiguration(BasicConfigEntity basicConfigEntity, CLIConfig cliConfig) {
        CliBasicConfigEntity cliBasicConfigEntity = new CliBasicConfigEntity();
        cliBasicConfigEntity.setBasicConfigEntity(basicConfigEntity);
        cliBasicConfigEntity.setLogin(cliConfig.getLogin());
        cliBasicConfigEntity.setPassword(cliConfig.getPassword());
        cliBasicConfigEntity.setPort(cliConfig.getPort());
        cliBasicConfigEntity.setTimeout(cliConfig.getTimeout());
        cliBasicConfigEntity.setCron(cliConfig.getCron());
        updateCliBasicConfiguration(cliBasicConfigEntity, cliConfig);
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
        SnmpBasicConfigEntity snmpBasicConfigEntity = basicConfigEntity.getSnmpBaseConfigs().get(0);
        snmpConfig.setVersion(snmpBasicConfigEntity.getVersion());
        snmpConfig.setCron(snmpBasicConfigEntity.getCron());
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

    private CLIConfig buildCliConfig(BasicConfigEntity basicConfigEntity) {
        CLIConfig cliConfig = new CLIConfig();
        cliConfig.setObjectID(basicConfigEntity.getObjectID());
        cliConfig.setIp(basicConfigEntity.getIp());
        CliBasicConfigEntity cliBasicConfigEntity = basicConfigEntity.getCliBasicConfigEntities().get(0);
        cliConfig.setLogin(cliBasicConfigEntity.getLogin());
        cliConfig.setPassword(cliBasicConfigEntity.getPassword());
        cliConfig.setCron(cliBasicConfigEntity.getCron());
        cliConfig.setPort(cliBasicConfigEntity.getPort());
        cliConfig.setTimeout(cliBasicConfigEntity.getTimeout());

        List<CLIMetricConfig> cliMetricConfigs = new ArrayList<>();
        for (CliMetricConfigEntity metricConfigEntity: cliBasicConfigEntity.getCliMetricConfigEntities()){
            CLIMetricConfig cliMetricConfig = new CLIMetricConfig();
            cliMetricConfig.setCommand(metricConfigEntity.getCommand());
            cliMetricConfig.setRegexp(metricConfigEntity.getRegexp());
            cliMetricConfig.setMetricID(metricConfigEntity.getMetricTypeID());
            cliMetricConfigs.add(cliMetricConfig);
        }
        cliConfig.setCliMetricConfigs(cliMetricConfigs);
        return cliConfig;

    }
}
