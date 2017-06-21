package com.simpsons;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ConsumerServlet extends HttpServlet {
    
    String topicName = "test-topic";
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        System.out.println("consumer servlet....");
        String message ;
        BasicKafkaConsumer basicKafkaConsumer = new BasicKafkaConsumer(topicName);
        message = basicKafkaConsumer.getMessage();
        out.println("<h1> consumed message:" + message + "</h1>");
    
    }
}
