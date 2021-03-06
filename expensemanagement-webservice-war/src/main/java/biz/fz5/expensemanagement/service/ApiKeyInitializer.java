/*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package biz.fz5.expensemanagement.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;


/**
 * Context initializer that loads the API key from a
 * {@value #PATH} file located in the classpath (typically under
 * {@code WEB-INF/classes}).
 */
public class ApiKeyInitializer implements ServletContextListener {

  static final String ATTRIBUTE_ACCESS_KEY = "apiKey";

  public static final String PATH = "/api.key";

  protected static Logger log = Logger.getLogger(ApiKeyInitializer.class
			.getName());

  public void contextInitialized(ServletContextEvent event) {
	  log.info("Reading " + PATH + " from resources (probably from " +
        "WEB-INF/classes");
    String key = getKey();
    
    event.getServletContext().setAttribute(ATTRIBUTE_ACCESS_KEY, key);
  }

  /**
   * Gets the access key.
   */
  protected String getKey() {
    InputStream stream = Thread.currentThread().getContextClassLoader()
        .getResourceAsStream(PATH);
    if (stream == null) {
      throw new IllegalStateException("Could not find file " + PATH +
          " on web resources)");
    }
    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
    try {
      String key = reader.readLine();
      return key;
    } catch (IOException e) {
      throw new RuntimeException("Could not read file " + PATH, e);
    } finally {
      try {
        reader.close();
      } catch (IOException e) {
    	  log.warn("Exception closing " + PATH, e);
      }
    }
  }

  public void contextDestroyed(ServletContextEvent event) {
  }

}
