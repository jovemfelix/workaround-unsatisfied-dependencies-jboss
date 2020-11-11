package org.jboss.as.quickstarts.ejb.service.impl;

import org.jboss.as.quickstarts.ejb.service.Food;
import org.jboss.as.quickstarts.ejb.service.Organic;
import org.jboss.as.quickstarts.ejb.qualifier.FruitQualifier;

import javax.ejb.Stateless;

@Stateless
@FruitQualifier(kind = FruitQualifier.Kind.APPLE)
public class Apple extends Organic implements Food {
    @Override
    public String name() {
        return "apple";
    }
}
