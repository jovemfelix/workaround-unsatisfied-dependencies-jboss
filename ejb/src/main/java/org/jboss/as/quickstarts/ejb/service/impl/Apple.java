package org.jboss.as.quickstarts.ejb.service.impl;

import org.jboss.as.quickstarts.ejb.qualifier.FruitQualifier;
import org.jboss.as.quickstarts.ejb.service.Food;

import javax.ejb.Stateless;

@Stateless
@FruitQualifier(kind = FruitQualifier.Kind.APPLE)
public class Apple implements Food {
    @Override
    public String name() {
        return "apple";
    }
}
