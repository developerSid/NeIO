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
package com.github.neio.streams.file;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.github.neio.exception.NeIOException;
import com.github.neio.filesystem.File;
import com.github.neio.filesystem.Path;
import com.github.neio.filesystem.exception.PathException;
import com.github.neio.streams.InputStream;
import com.github.neio.streams.execption.ReadStreamException;

/**
 * @author developer.sid@gmail.com
 *
 */
public class FileInputStream extends AbstractFileStream<java.io.FileInputStream> implements InputStream
{
   /**
    * Opens a {@link File} for reading
    * @param path the path to the {@link File} that is to be opened for reading
    * @throws PathException if the {@link File} was unable to be found
    */
   public FileInputStream(File path) throws PathException
   {
      super(path);
   }
   @Override
   protected java.io.FileInputStream createStream(Path path) throws PathException
   {
      try
      {
         return new java.io.FileInputStream(new java.io.File(path.getPath()));
      }
      catch(FileNotFoundException e)
      {
         throw new PathException("[" + path.getPath() + "] not found", e);
      }
   }
   @Override
   public int read() throws ReadStreamException
   {
      try
      {
         return super.closeable.read();
      }
      catch(IOException e)
      {
         throw new NeIOException(e);
      }
   }
   @Override
   public int read(byte[] buffer) throws ReadStreamException
   {
      try
      {
         return super.closeable.read(buffer);
      }
      catch(IOException e)
      {
         throw new ReadStreamException("Unable to read bytes into {buffer:[" + buffer.length +"]} from " + getPath().getPath(), e);
      }
   }
   @Override
   public int read(byte[] buffer, int off, int len) throws ReadStreamException
   {
      try
      {
         return super.closeable.read(buffer, off, len);
      }
      catch(IOException e)
      {
         throw new ReadStreamException("Unable to read bytes {offset:[" + off +"], len:[" + len +"]} from " + getPath().getPath() + " into {buffer:[" + buffer.length +"]}", e);
      }
   }
}
