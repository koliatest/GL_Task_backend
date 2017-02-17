package com.kolia.dao;

import com.kolia.model.Appliance;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ApplianceDaoImpl implements ApplianceDao {

    @Autowired
    SessionFactory sessionFactory;

    private Session openSession() {
        return sessionFactory.getCurrentSession();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Appliance> getAllAppliances() {

        List<Appliance> applianceList = new ArrayList<Appliance>();

        applianceList = openSession().createQuery("from Appliance").list();

        return applianceList.size() > 0? applianceList : null;
    }

    @Override
    public void addAppliance(final Appliance appliance) {
        openSession().save(appliance);
    }

    @Override
    public Appliance findById(Integer id) {

        return (Appliance)openSession().get(Appliance.class, id);
    }

    @Override
    public void deleteAppliance(Integer id) {
        openSession().delete(findById(id));
    }
}
