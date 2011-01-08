/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.neio.filesystem.paths;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.neio.filesystem.Directory;

/**
 * Unit tests for testing {@link DirectoryPath}
 * @author developer.sid@gmail.com
 *
 */
public class TestDirectoryPath
{
   private static File testDir;
   
   public static void beforeClass()
   {
      testDir=new File("./testTempDir/");
   }
   @Before
   public void before() throws IOException
   {
      FileUtils.forceMkdir(testDir);
   }
   @Test
   public void testPath()
   {
      Directory directory=new DirectoryPath("./testTempDir/");
      
      
   }
   @After
   public void after() throws IOException
   {
      FileUtils.cleanDirectory(testDir);
   }
   public static void afterClass()
   {
      try
      {
         FileUtils.deleteDirectory(testDir);
      }
      catch(IOException e)
      {
         System.err.println("Unable to delete test directory " + e.getMessage());
      }
   }
}
