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
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.neio.filesystem.Directory;
import com.github.neio.filesystem.Path;
import com.github.neio.filesystem.exception.PathException;

/**
 * Unit tests for testing {@link DirectoryPath}
 * @author developer.sid@gmail.com
 *
 */
public class TestDirectoryPath
{
   private static File testDir;
   
   @BeforeClass
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
   public void test_directorySuccessfullyInstantiated()
   {
      new DirectoryPath("./testTempDir");
   }
   @Test(expected=PathException.class)
   public void test_directoryExists_ButWasFile() throws IOException
   {
      FileUtils.touch(new File(testDir, "tempFile"));
      
      new DirectoryPath("./testTempDir/tempFile");
   }
   @Test
   public void testPathEquals()
   {
      Directory directory=new DirectoryPath("./testTempDir/path/");
      
      Assert.assertTrue("Test path was not equal", FilenameUtils.equalsNormalizedOnSystem("./testTempDir/path/", directory.getPath()));
   }
   @Test
   public void test_getPath_returnsNormalized()
   {
      Directory directory=new DirectoryPath("./testTempDir/path/");
      
      Assert.assertEquals("testTempDir" + File.separator + "path" + File.separator, directory.getPath());
   }
   @Test
   public void test_mkDir()
   {
      Directory directory=new DirectoryPath("./testTempDir/path/");
      
      Assert.assertFalse(new File(testDir, "path/").exists());
      
      directory.mkdir();
      
      Assert.assertTrue(new File(testDir, "path/").exists());
   }
   @Test
   public void test_compareTo_lessThan()
   {
      Directory directory=new DirectoryPath("./testTempDir/path1");
      
      Assert.assertTrue(directory.compareTo(new DirectoryPath("./testTempDir/path2")) < 0);
   }
   @Test
   public void test_compareTo_greaterThan()
   {
      Directory directory=new DirectoryPath("./testTempDir/path2");
      
      Assert.assertTrue(directory.compareTo(new DirectoryPath("./testTempDir/path1")) > 0);
   }
   @Test
   public void test_CompareTo_equals()
   {
      Directory directory=new DirectoryPath("./testTempDir/path1");
      
      Assert.assertTrue(directory.compareTo(new DirectoryPath("./testTempDir/path1")) == 0);
   }
   @Test(expected=NullPointerException.class)
   public void test_CompareTo_CannotBeNull()
   {
      Directory directory=new DirectoryPath("./testTempDir/path1");
      
      directory.compareTo(null);
   }
   @Test(expected=ClassCastException.class)
   public void test_CompareTo_NotDirectory()
   {
      Directory directory=new DirectoryPath("./testTempDir/path1");
      
      directory.compareTo(new FilePath("./testTempDir/path1"));
   }
   @Test
   public void test_Equals_false()
   {
      Directory directory=new DirectoryPath("./testTempDir/path2");
      
      Assert.assertFalse(directory.equals(new DirectoryPath("./testTempDir/path1")));
   }
   @Test
   public void test_Equals_true()
   {
      Directory directory=new DirectoryPath("./testTempDir/path1");
      
      Assert.assertTrue(directory.equals(new DirectoryPath("./testTempDir/path1")));
   }
   @Test
   public void test_Equals_falseCausedByNull()
   {
      Directory directory=new DirectoryPath("./testTempDir/path2");
      
      Assert.assertFalse(directory.equals(null));
   }
   @Test
   public void test_Equals_falseCausedByWrongClass()
   {
      Directory directory=new DirectoryPath("./testTempDir/path2");
      
      Assert.assertFalse(directory.equals(new FilePath("./testTempDir/path1")));
   }
   @Test
   public void test_Path_Iterator() throws IOException
   {
      Directory directory=new DirectoryPath("./testTempDir");
      
      FileUtils.forceMkdir(new File("./testTempDir/path1"));
      FileUtils.touch(new File("./testTempDir/path1/file1"));
      
      Iterator<Path> iterator=directory.iterator();
      
      Assert.assertTrue(iterator.hasNext());
      Path path1=iterator.next();
      
      Assert.assertTrue(path1 instanceof FilePath);
      Assert.assertEquals("testTempDir/path1/file1", path1.getPath());
   }
   @Test
   public void test_Non_Existant_Directory()
   {
      Directory directory=new DirectoryPath("./dirDoesNotExist");
      
      Assert.assertFalse(directory.iterator().hasNext());
   }
   @Test
   public void test_toString()
   {
      Assert.assertEquals("testTempDir/", new DirectoryPath("./testTempDir").toString());
   }
   @Test
   public void test_hash_code()
   {
      Directory directory=new DirectoryPath("./testTempDir");
      
      Assert.assertEquals("testTempDir/".hashCode(), directory.hashCode());
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
