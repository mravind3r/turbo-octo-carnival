package com.simpsons;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

public class HbasePutServlet extends HttpServlet {
    
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String tableName = "csdp_table";
        String fault = null;
        Table table = null;
        Connection conn = null;
        try {
        Configuration hConf = HBaseConfiguration.create(); //runs with zookeeper on default server:port localhost:2181
            hConf.set("hbase.zookeeper.quorum", "172.17.0.2");
            hConf.setInt("hbase.zookeeper.property.clientPort",2181 );
         conn = ConnectionFactory.createConnection(hConf);
    
        Admin admin = conn.getAdmin();
    
        //create a table descriptor
        HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(tableName));
    
        //create a column family
        HColumnDescriptor family = new HColumnDescriptor(Bytes.toBytes("i"));
    
        //adding coloumn family to HTable
        tableDescriptor.addFamily(family);
        
            if(!admin.tableExists(tableDescriptor.getTableName())) {
                admin.createTable(tableDescriptor);
            }
            TableName TABLE_NAME = TableName.valueOf(tableName);
             table = conn.getTable(TABLE_NAME);
            
        } catch (Exception e) {
            fault = e.getMessage();
        }finally{
            if (table != null) table.close();
            if (conn != null) conn.close();
        }
        out.println("<h1>" + "created table:" + table.getName().getNameAsString() + "</h1>");
    }
}
