package com.kolia.service;

import com.kolia.dao.ApplianceDao;
import com.kolia.model.Appliance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class ApplianceServiceImpl implements ApplianceService {

    @Autowired
    ApplianceDao applianceDao;

    @Override
    public List<Appliance> getAllAppliances() {
        return applianceDao.getAllAppliances();
    }

    @Override
    public void addAppliance(final Appliance appliance) {
        applianceDao.addAppliance(appliance);
    }

    @Override
    public Appliance findById(Integer id) {
        return applianceDao.findById(id);
    }

    @Override
    public void deleteAppliance(Integer id) {
        applianceDao.deleteAppliance(id);
    }
}
