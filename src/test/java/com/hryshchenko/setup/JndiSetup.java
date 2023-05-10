package com.hryshchenko.setup;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class JndiSetup {
    /**
     * Setup the Data Source
     */
    public static void setUpClass() {
        // rcarver - setup the jndi context and the datasource
        try {
            // Create initial context
            System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                    "org.apache.naming.java.javaURLContextFactory");
            System.setProperty(Context.URL_PKG_PREFIXES,
                    "org.apache.naming");
            InitialContext ic = new InitialContext();

            ic.createSubcontext("java:");
            ic.createSubcontext("java:/comp");
            ic.createSubcontext("java:/comp/env");
            ic.createSubcontext("java:/comp/env/jdbc");

            // Construct DataSource
            MysqlConnectionPoolDataSource ds = new MysqlConnectionPoolDataSource();
            Properties properties = new Properties();
            properties.load(new FileInputStream("src/test/resources/test.properties"));
            ds.setURL(properties.getProperty("ds.url"));
            ds.setUser(properties.getProperty("ds.user"));
            ds.setPassword(properties.getProperty("ds.password"));
            ic.bind("java:/comp/env/jdbc/FacultativeDB", ds);
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}