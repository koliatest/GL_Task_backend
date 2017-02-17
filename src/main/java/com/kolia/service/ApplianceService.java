package com.kolia.service;

import com.kolia.model.Appliance;

import java.util.List;

public interface ApplianceService {
    List<Appliance> getAllAppliances();
    void addAppliance(final Appliance appliance);
    Appliance findById(final Integer id);
    void deleteAppliance(final Integer id);


}
