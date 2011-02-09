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

import com.github.neio.filesystem.File;
import com.github.neio.filesystem.Path;
import com.github.neio.filesystem.exception.PathException;
import com.github.neio.streams.OutputStream;
import com.github.neio.streams.execption.WriteStreamException;

/**
 * @author developer.sid@gmail.com
 *
 */
public class FileOutputStream extends AbstractFileStream<java.io.FileOutputStream> implements OutputStream
{
   /**
    * Opens a {@link File} for writing
    * @param path the path to the {@link File} that will be written to.
    * @throws PathException if the {@link File} was unable to be found
    */
   public FileOutputStream(File path) throws PathException
   {
      super(path);
   }
   @Override
   protected java.io.FileOutputStream createStream(Path path) throws PathException
   {
      try
      {
         return new java.io.FileOutputStream(new java.io.File(path.getPath()));
      }
      catch(FileNotFoundException e)
      {
         throw new PathException("[" + path.getPath() + "] not found", e);
      }
   }
   @Override
   public void write(int b) throws WriteStreamException
   {
      try
      {
         super.closeable.write(b);
      }
      catch(IOException e)
      {
         throw new WriteStreamException("Unable to write to [" + getPath().getPath() + "]", e);
      }
   }
   @Override
   public void write(byte[] b) throws WriteStreamException
   {
      
   }
   @Override
   public void write(byte[] b, int off, int len) throws WriteStreamException
   {
      
   }
}
