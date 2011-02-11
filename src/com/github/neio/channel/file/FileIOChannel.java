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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

import com.github.neio.channel.IOChannel;
import com.github.neio.channel.exception.ChannelException;
import com.github.neio.filesystem.paths.FilePath;

/**
 * Implements the {@link IOChannel} using a file on the file system
 * @author developer.sid@gmail.com
 *
 */
public class FileIOChannel implements IOChannel
{
   private FileChannel underlyingImplementation;
   private FilePath file;
   
   public FileIOChannel(FilePath file) throws ChannelException
   {
      try
      {
         this.underlyingImplementation=new RandomAccessFile(file.getPath(), "rw").getChannel();
         this.file=file;
      }
      catch(FileNotFoundException e)
      {
         throw new ChannelException("File " + file.getPath() + " does not exist", e);
      }
   }
   @Override
   public void close() throws ChannelException
   {
      try
      {
         underlyingImplementation.close();
      }
      catch(IOException e)
      {
         throw new ChannelException("Unable to close " + file.getPath(), e);
      }
   }
   @Override
   public boolean isOpen()
   {
      return underlyingImplementation.isOpen();
   }
   @Override
   public int write(ByteBuffer buffer) throws ChannelException
   {
      try
      {
         return underlyingImplementation.write(buffer);
      }
      catch(IOException e)
      {
         throw new ChannelException("Unable to write the provided bytes to " + file.getPath(), e);
      }
   }
   @Override
   public int read(ByteBuffer buffer) throws ChannelException
   {
      try
      {
         return underlyingImplementation.read(buffer);
      }
      catch(IOException e)
      {
         throw new ChannelException("Unalbe to read the requested bytes from " + file.getPath(), e);
      }
   }
   @Override
   public void reset() throws ChannelException
   {
      try
      {
         underlyingImplementation.force(true);
         underlyingImplementation.position(0);
      }
      catch(IOException e)
      {
         throw new ChannelException("Unable to reset " + file.getPath(), e);
      }
   }
   @Override
   public void clear() throws ChannelException
   {
      try
      {
         underlyingImplementation.truncate(0);
      }
      catch(IOException e)
      {
         throw new ChannelException("Unable to clear " + file.getPath(), e);
      }
   }
   @Override
   public long size() throws ChannelException
   {
      try
      {
         return underlyingImplementation.size();
      }
      catch(IOException e)
      {
         throw new ChannelException("Unable to read the size of " + file.getPath(), e);
      }
   }
   @Override
   public long transferTo(WritableByteChannel target) throws ChannelException
   {
      try
      {
         return underlyingImplementation.transferTo(underlyingImplementation.position(), underlyingImplementation.size()-underlyingImplementation.position(), target);
      }
      catch(IOException e)
      {
         throw new ChannelException("Unable to transfer bytes from " + file.getPath(), e);
      }
   }

   @Override
   public long transferTo(long position, long count, WritableByteChannel target) throws ChannelException
   {
      try
      {
         return underlyingImplementation.transferTo(position, count, target);
      }
      catch(IOException e)
      {
         throw new ChannelException("Unable to transfer bytes from " + file.getPath(), e);
      }
   }
   @Override
   public long transferFrom(ReadableByteChannel src) throws ChannelException
   {
      return 0;
   }
   @Override
   public long transferFrom(ReadableByteChannel src, long position, long count) throws ChannelException
   {
      return 0;
   }
}
