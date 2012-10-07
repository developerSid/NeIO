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
package com.github.neio.channel.file;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.neio.channel.IOChannel;
import com.github.neio.filesystem.paths.FilePath;

/**
 * @author developer.sid@gmail.com
 *
 */
public class TestFileIOChannel
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
   public void test_file_is_open()
   {
      IOChannel channel=new FileIOChannel(new FilePath("./testTempDir/file1"));
      
      Assert.assertTrue(channel.isOpen());
   }
   @Test
   public void test_file_is_closed()
   {
      IOChannel channel=new FileIOChannel(new FilePath("./testTempDir/file1"));
      
      channel.close();
      
      Assert.assertFalse(channel.isOpen());
   }
   @Test
   public void test_write() throws IOException
   {
      IOChannel channel=new FileIOChannel(new FilePath("./testTempDir/file1"));
      ByteBuffer buffer=ByteBuffer.allocate(1024);
      
      buffer.put("Hello World".getBytes());
      channel.write(buffer);
      channel.close();
      
      Assert.assertEquals("Hello World", FileUtils.readFileToString(new File("./testTempDir/file1")));
   }
   @Test
   public void test_read() throws IOException
   {
      FileUtils.writeStringToFile(new File("./testTempDir/file1"), "Hello World");
      
      IOChannel channel=new FileIOChannel(new FilePath("./testTempDir/file1"));
      ByteBuffer buffer=ByteBuffer.allocate(1024);
      
      channel.read(buffer);
      channel.close();
      
      Assert.assertEquals("Hello World", new String(buffer.array(), 0, buffer.limit()));
   }
   @Test
   public void test_reset() throws IOException
   {
      IOChannel channel=new FileIOChannel(new FilePath("./testTempDir/file1"));
      ByteBuffer buffer=ByteBuffer.allocate(1024);
      
      buffer.put("Hello World".getBytes());
      channel.write(buffer);
      channel.reset();
      
      channel.read(buffer);
      channel.close();
      
      Assert.assertEquals("Hello World", new String(buffer.array(), 0, buffer.limit()));
   }
   @Test
   public void test_size() throws IOException
   {
      IOChannel channel=new FileIOChannel(new FilePath("./testTempDir/file1"));
      ByteBuffer buffer=ByteBuffer.allocate(1024);
      
      buffer.put("Hello World".getBytes());
      channel.write(buffer);
      
      Assert.assertEquals(11, channel.size());
   }
   @Test
   public void test_clear()
   {
      IOChannel channel=new FileIOChannel(new FilePath("./testTempDir/file1"));
      ByteBuffer buffer=ByteBuffer.allocate(1024);
      
      buffer.put("Hello World".getBytes());
      channel.write(buffer);
      
      Assert.assertEquals(11, channel.size());
      
      channel.clear();
      
      Assert.assertEquals(0, channel.size());
   }
   @Test
   public void test_transferTo() throws IOException
   {
      FileUtils.writeStringToFile(new File("./testTempDir/file1"), "Hello World");
      IOChannel channelFrom=new FileIOChannel(new FilePath("./testTempDir/file1"));
      IOChannel channelTo=new FileIOChannel(new FilePath("./testTempDir/file2"));
      
      Assert.assertEquals(11, channelFrom.size());
      Assert.assertEquals(11, channelFrom.transferTo(0, channelFrom.size(), channelTo));
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
