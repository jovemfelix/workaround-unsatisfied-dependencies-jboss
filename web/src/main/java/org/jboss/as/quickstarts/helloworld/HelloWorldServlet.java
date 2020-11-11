package org.jboss.as.quickstarts.helloworld;

import org.jboss.as.quickstarts.ejb.qualifier.FruitQualifier;
import org.jboss.as.quickstarts.ejb.service.Food;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@SuppressWarnings("serial")
@WebServlet("/hello")
public class HelloWorldServlet extends HttpServlet {

    @Inject
    @FruitQualifier(kind = FruitQualifier.Kind.APPLE)
    private Food apple;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        PrintWriter writer = resp.getWriter();
        writer.println("My fruit is: " + apple.name());
        writer.close();
    }

}
