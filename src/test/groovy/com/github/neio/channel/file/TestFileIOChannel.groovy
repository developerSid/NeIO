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

import java.nio.ByteBuffer

import org.apache.commons.io.FileUtils
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

import spock.lang.Specification

import com.github.neio.channel.IOChannel
import com.github.neio.filesystem.paths.FilePath

/**
 * @author developer.sid@gmail.com
 *
 */
public class TestFileIOChannel extends Specification
{
   @Rule TemporaryFolder testFolder=new TemporaryFolder()
   
   def "test file state" ()
   {
      when:
         File tempDir=testFolder.newFolder()
         IOChannel channel=new FileIOChannel(new FilePath(tempDir.getAbsolutePath() + "/temp1"))
      then:
         channel.isOpen()
         
      when:
         channel.close()
      then:
         !channel.isOpen()
         
      cleanup:
         channel.close()
   }
   def "test write" ()
   {
      setup:
         File tempDir=testFolder.newFolder()
         IOChannel channel=new FileIOChannel(new FilePath(tempDir.getAbsolutePath() + "/temp1"))
         ByteBuffer buffer=ByteBuffer.allocate(1024)
      when:
         buffer.put("Hello World".getBytes())
         channel.write(buffer)
         channel.close()
      then:
         FileUtils.readFileToString(new File(tempDir.getAbsolutePath() + "/temp1")) == "Hello World"
   }
   def "test read" ()
   {
      setup:
         File tempDir=testFolder.newFolder()
         FileUtils.writeStringToFile(new File(tempDir.getAbsolutePath() + "/file1"), "Hello World")
         IOChannel channel=new FileIOChannel(new FilePath(tempDir.getAbsolutePath() + "/file1"))
         ByteBuffer buffer=ByteBuffer.allocate(1024)
      when:
         channel.read(buffer)
      then:
         new String(buffer.array(), 0, buffer.limit()) == "Hello World"
      cleanup:
         channel.close()
   }
   def "test reset" ()
   {
      setup:
         File tempDir=testFolder.newFolder()
         IOChannel channel=new FileIOChannel(new FilePath(tempDir.getAbsolutePath() + "/file1"))
         ByteBuffer buffer=ByteBuffer.allocate(1024)
      when:
         buffer.put("Hello World".getBytes())
         channel.write(buffer)
         channel.reset()
         channel.read(buffer)
      then:
         new String(buffer.array(), 0, buffer.limit()) == "Hello World"
      cleanup:
         channel.close()
   }
   def "test size" ()
   {
      setup:
         File tempDir=testFolder.newFolder()
         IOChannel channel=new FileIOChannel(new FilePath(tempDir.getAbsolutePath() + "/file1"))
         ByteBuffer buffer=ByteBuffer.allocate(1024)
      when:
         buffer.put("Hello World".getBytes())
         channel.write(buffer)
      then:
         channel.size() == 11
      cleanup:
         channel.close();
   }
   def "test clear" ()
   {
      setup:
         File tempDir=testFolder.newFolder()
         IOChannel channel=new FileIOChannel(new FilePath(tempDir.getAbsolutePath() + "/file1"));
         ByteBuffer buffer=ByteBuffer.allocate(1024);
      when:
         buffer.put("Hello World".getBytes());
         channel.write(buffer);
      then:
         channel.size() == 11
      
      when:
         channel.clear();
      then:
         channel.size() == 0
      cleanup:
         channel.close();
   }
   def "test transfer to" ()
   {
      setup:
         def transfered=0
         File fromFile=testFolder.newFile()
         File toFile=testFolder.newFile()
         fromFile.text="Hello World"
         FileIOChannel from=new FileIOChannel(new FilePath(fromFile.getAbsolutePath()))
         FileIOChannel to=new FileIOChannel(new FilePath(toFile.getAbsolutePath()))
      when:
         transfered=from.transferTo(0, from.size(), to)
      then:
         transfered == 11
         toFile.text == "Hello World"
      cleanup:
         from.close()
         to.close()
   }
   def "test transfer from" ()
   {
      setup:
         def transfered=0
         File fromFile=testFolder.newFile()
         File toFile=testFolder.newFile()
         toFile.text="Hello World"
         FileIOChannel from=new FileIOChannel(new FilePath(fromFile.getAbsolutePath()))
         FileIOChannel to=new FileIOChannel(new FilePath(toFile.getAbsolutePath()))
      when:
         from.clear()
         transfered=from.transferFrom(to, 0, to.size())
      then:
         transfered == 11
         fromFile.text == "Hello World"
      cleanup:
         from.close()
         to.close()
   }
}
