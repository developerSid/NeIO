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
   private FilePath file;
   private FileChannel underlyingImplementation;
   
   public FileIOChannel(FilePath file) throws ChannelException
   {
      try
      {
         this.file=file;
         this.underlyingImplementation=new RandomAccessFile(file.getPath(), "rw").getChannel();
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
         buffer.flip();
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
         int bytesRead=underlyingImplementation.read(buffer);
         
         buffer.flip();
         
         return bytesRead;
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
   public long transferTo(long position, long count, WritableByteChannel target) throws ChannelException
   {
      try
      {
         if(target instanceof FileIOChannel) //the underlying implementation only seems to actually do a copy if the target is a FileChannel
         {
            return underlyingImplementation.transferTo(position, count, ((FileIOChannel)target).underlyingImplementation);
         }
         else //try just in case it will work
         {
            return underlyingImplementation.transferTo(position, count, target);
         }
      }
      catch(IOException e)
      {
         throw new ChannelException("Unable to transfer bytes from " + file.getPath(), e);
      }
   }
   @Override
   public long transferFrom(ReadableByteChannel src, long position, long count) throws ChannelException
   {
      try
      {
         if(src instanceof FileIOChannel) //the underlying implementation only seems to actually do a copy if the src is a FileChannel
         {
            return underlyingImplementation.transferFrom(((FileIOChannel)src).underlyingImplementation, position, count);
         }
         else  //try just in case it will work
         {
            return underlyingImplementation.transferFrom(src, position, count);
         }
      }
      catch(IOException e)
      {
         throw new ChannelException("Unable to transfer bytes from source to " + file.getPath());
      }
   }
}
