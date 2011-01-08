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

import com.github.neio.filesystem.File;
import com.github.neio.filesystem.exception.PathException;

/**
 * @author developer.sid@gmail.com
 *
 */
public class FilePath extends AbstractPath<File> implements File
{
   private static final long serialVersionUID=8774985562843285911L;
   
   /**
    * @param path
    * @throws PathException
    */
   public FilePath(String path) throws PathException
   {
      super(path);
   }
   @Override
   protected void checkType(java.io.File path) throws PathException
   {
      if(path.isFile() == false)
      {
         throw new PathException("[" + path.getPath() + "] is not a file");
      }
   }
   @Override
   protected int howDoICompare(File path)
   {
      if(path instanceof FilePath)
      {
         return super.file.compareTo(((FilePath)path).file);
      }
      else
      {
         return super.file.compareTo(new java.io.File(path.getPath()));
      }
   }
   @Override
   protected boolean amIEqual(File path)
   {
      if(path instanceof FilePath)
      {
         return super.file.equals(((FilePath)path).file);
      }
      else
      {
         return super.file.equals(new java.io.File(path.getPath()));
      }
   }
   @Override
   protected int hashMe()
   {
      return super.file.hashCode();
   }
   @Override
   protected String stringify()
   {
      return super.getPath();
   }
}
