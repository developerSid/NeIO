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
import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.neio.filesystem.exception.PathException;

/**
 * @author developer.sid@gmail.com
 *
 */
public class TestFilePath
{
   private static File testDir;
   
   @BeforeClass
   public static void beforeClass()
   {
      testDir=new File("./testTempDir/");
      testDir.mkdir();
   }
   @Test
   public void test_FileSuccessfullyInstantiated() throws IOException
   {
      FileUtils.writeStringToFile(new File(testDir, "testFile"), "Hello World");
      
      new FilePath("./testTempDir/testFile");
   }
   @Test(expected=PathException.class)
   public void test_FilePathWasDirectory()
   {
      new FilePath("./testTempDir");
   }
   @Test
   public void testTouch() throws InterruptedException, IOException
   {
      File testFile=new File(testDir, "testFile");
      FileUtils.writeStringToFile(testFile, "Hello World");
      long initialTime=testFile.lastModified();

      TimeUnit.SECONDS.sleep(1);
      new FilePath("./testTempDir/testFile").touch();
      
      Assert.assertTrue(initialTime < testFile.lastModified());
   }
   @Test
   public void testExists() throws IOException
   {
      FileUtils.writeStringToFile(new File(testDir, "testFile"), "Hello World");
      
      Assert.assertTrue(new FilePath("./testTempDir/testFile").exists());
   }
   @Test
   public void testSize() throws IOException
   {
      FileUtils.writeStringToFile(new File(testDir, "testFile"), "Hello World");
      
      Assert.assertEquals(11, new FilePath("./testTempDir/testFile").size());
   }
   @Test
   public void testToString()
   {
      FilePath path=new FilePath("./testTempDir/testFile");
      
      Assert.assertEquals("testTempDir/testFile", path.toString());
   }
   @Test
   public void testSha1Hash() throws IOException
   {
      FileUtils.writeStringToFile(new File(testDir, "testFile"), "Hello World");
      
      Assert.assertEquals("a4d55a8d778e5022fab701977c5d840bbc486d0", new FilePath("./testTempDir/testFile").sha1Hash().toString(16));
   }
   @Test
   public void testHashCode() throws IOException
   {
      FileUtils.writeStringToFile(new File(testDir, "testFile"), "Hello World");
      
      Assert.assertEquals(new BigInteger("a4d55a8d778e5022fab701977c5d840bbc486d0", 16).hashCode(), new FilePath("./testTempDir/testFile").hashCode());
   }
   @After
   public void after() throws IOException
   {
      FileUtils.cleanDirectory(testDir);
   }
   @AfterClass
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
