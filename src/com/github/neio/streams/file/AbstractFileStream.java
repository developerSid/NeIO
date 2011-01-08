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

import java.io.Closeable;
import java.io.IOException;

import com.github.neio.exception.NeIOException;
import com.github.neio.filesystem.Path;
import com.github.neio.filesystem.exception.PathException;
import com.github.neio.streams.Stream;

/**
 * @author developer.sid@gmail.com
 *
 */
abstract class AbstractFileStream<S extends Closeable> implements Stream
{
   protected abstract S createStream(Path path) throws PathException;
   
   private Path path;
   protected S closeable;
   private boolean open;
   
   /**
    * Abstract class constructor for holding the pieces that will be used for doing IO
    * @param path
    * @param closeable
    * @throws if the stream is unable to be created
    */
   AbstractFileStream(Path path) throws PathException
   {
      this.path=path;
      this.closeable=createStream(path);
      this.open=true;
   }
   @Override
   public void close() throws NeIOException
   {
      try
      {
         if(open == true)
         {
            try
            {
               closeable.close();
            }
            finally
            {
               open=false;
            }
         }
      }
      catch(IOException e)
      {
         throw new NeIOException(e);
      }
   }
   @Override
   public Path getPath()
   {
      return path;
   }
   @Override
   public boolean open()
   {
      return open;
   }
   @Override
   public boolean closed()
   {
      return !open;
   }
}
