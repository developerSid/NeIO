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

import com.github.neio.filesystem.Directory;
import com.github.neio.filesystem.exception.PathException;

/**
 * @author developer.sid@gmail.com
 *
 */
public class DirectoryPath extends AbstractPath<Directory> implements Directory
{
   private static final long serialVersionUID=-4337087049736460729L;

   /**
    * Construct a directory based off of a file system path
    * @param directory the directory to be constructed
    * @throws PathException if the path represented is not a directory
    */
   public DirectoryPath(String directory) throws PathException
   {
      super(directory);
   }
   @Override
   protected void checkType(File path) throws PathException
   {
      if(path.isDirectory() == false)
      {
         throw new PathException("[" + path.getPath() + "] was not a directory");
      }
   }
   @Override
   protected int howDoICompare(Directory path)
   {
      if(path instanceof DirectoryPath)
      {
         return super.file.compareTo(((DirectoryPath)path).file);
      }
      else
      {
         return super.file.compareTo(new File(path.getPath()));
      }
   }
   @Override
   protected boolean amIEqual(Directory path)
   {
      if(path instanceof DirectoryPath)
      {
         return super.file.equals(((DirectoryPath)path).file);
      }
      else
      {
         return super.file.equals(new File(path.getPath()));
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
