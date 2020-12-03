package com.eye.db.service;

import com.eye.db.dao.EyeSystemMapper;
import com.eye.db.domain.EyeSystem;
import com.eye.db.domain.EyeSystemExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EyeSystemConfigService {
    @Resource
    private EyeSystemMapper systemMapper;

    public Map<String, String> queryAll() {
        EyeSystemExample example = new EyeSystemExample();
        example.or().andDeletedEqualTo(false);

        List<EyeSystem> systemList = systemMapper.selectByExampleWithBLOBs(example);
        Map<String, String> systemConfigs = new HashMap<>();
        for (EyeSystem item : systemList) {
            systemConfigs.put(item.getKeyName(), item.getKeyValue());
        }

        return systemConfigs;
    }

    public Map<String, String> listMail() {
        EyeSystemExample example = new EyeSystemExample();
        example.or().andKeyNameLike("eye_mall_%").andDeletedEqualTo(false);
        List<EyeSystem> systemList = systemMapper.selectByExampleWithBLOBs(example);
        Map<String, String> data = new HashMap<>();
        for(EyeSystem system : systemList){
            data.put(system.getKeyName(), system.getKeyValue());
        }
        return data;
    }

    public Map<String, String> listWx() {
        EyeSystemExample example = new EyeSystemExample();
        example.or().andKeyNameLike("eye_wx_%").andDeletedEqualTo(false);
        List<EyeSystem> systemList = systemMapper.selectByExampleWithBLOBs(example);
        Map<String, String> data = new HashMap<>();
        for(EyeSystem system : systemList){
            data.put(system.getKeyName(), system.getKeyValue());
        }
        return data;
    }

    public Map<String, String> listOrder() {
        EyeSystemExample example = new EyeSystemExample();
        example.or().andKeyNameLike("eye_order_%").andDeletedEqualTo(false);
        List<EyeSystem> systemList = systemMapper.selectByExampleWithBLOBs(example);
        Map<String, String> data = new HashMap<>();
        for(EyeSystem system : systemList){
            data.put(system.getKeyName(), system.getKeyValue());
        }
        return data;
    }

    public Map<String, String> listExpress() {
        EyeSystemExample example = new EyeSystemExample();
        example.or().andKeyNameLike("eye_express_%").andDeletedEqualTo(false);
        List<EyeSystem> systemList = systemMapper.selectByExampleWithBLOBs(example);
        Map<String, String> data = new HashMap<>();
        for(EyeSystem system : systemList){
            data.put(system.getKeyName(), system.getKeyValue());
        }
        return data;
    }

    public void updateConfig(Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            EyeSystemExample example = new EyeSystemExample();
            example.or().andKeyNameEqualTo(entry.getKey()).andDeletedEqualTo(false);

            EyeSystem system = new EyeSystem();
            system.setKeyName(entry.getKey());
            system.setKeyValue(entry.getValue());
            system.setUpdateTime(LocalDateTime.now());
            systemMapper.updateByExampleSelective(system, example);
        }

    }

    public void addConfig(String key, String value) {
        EyeSystem system = new EyeSystem();
        system.setKeyName(key);
        system.setKeyValue(value);
        system.setAddTime(LocalDateTime.now());
        system.setUpdateTime(LocalDateTime.now());
        systemMapper.insertSelective(system);
    }
}
