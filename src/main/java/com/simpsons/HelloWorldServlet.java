package com.simpsons;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloWorldServlet extends HttpServlet {
    
    String topicName = "test-topic";
    
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        TopicCreator.createTopic(topicName);
    }
    
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String message ;
        try {
            response.setContentType("text/html");
            BasicProducer basicProducer = new BasicProducer(topicName);
            message = "tomcat:" + new Date().toString();
            basicProducer.postSynchMessage(message);
        } catch (Exception ex) {
           message = ex.toString();
        }
        
        out.println("<h1>" + "message produced:" + message + "</h1>");
    }
}