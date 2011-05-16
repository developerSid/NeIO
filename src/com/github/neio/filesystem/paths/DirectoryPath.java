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
import org.apache.commons.io.IOCase;

import com.github.neio.filesystem.Directory;
import com.github.neio.filesystem.Path;
import com.github.neio.filesystem.exception.FilesystemException;
import com.github.neio.filesystem.exception.PathException;

/**
 * @author developer.sid@gmail.com
 *
 */
public class DirectoryPath extends AbstractPath<Directory> implements Directory
{
   private static final long serialVersionUID=-4337087049736460729L;
   private File platformDirectory;
   
   /**
    * Construct a directory based off of a file system path
    * @param directory the directory to be constructed
    * @throws PathException if the path represented is not a directory
    */
   public DirectoryPath(String directory) throws PathException
   {
      super(FilenameUtils.normalizeNoEndSeparator(directory) + File.separator, Directory.class);
      this.platformDirectory=new File(super.path);
      
      if(platformDirectory.exists() == true)
      {
         if(platformDirectory.isDirectory() == false)
         {
            throw new PathException("[" + directory + "] is not a directory");
         }
      }
   }
   @Override
   protected int howDoICompare(Directory path)
   {
      return super.path.compareTo(FilenameUtils.normalizeNoEndSeparator(path.getPath())); 
   }
   @Override
   protected boolean amIEqual(Directory path)
   {
      return FilenameUtils.equals(super.path, path.getPath(), true, IOCase.SYSTEM);
   }
   @Override
   protected int hashMe()
   {
      return super.path.hashCode();
   }
   @Override
   protected String stringify()
   {
      return super.path;
   }
   
   @Override
   public void mkdir() throws FilesystemException
   {
      try
      {
         FileUtils.forceMkdir(platformDirectory);
      }
      catch(IOException e)
      {
         throw new FilesystemException("Unable to make directory [" + super.getPath() + "]", e);
      }
   }
   @Override
   public Iterator<Path> iterator()
   {
      return new PathIterator(platformDirectory);
   }
}
