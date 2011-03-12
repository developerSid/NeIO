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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.DigestOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.AutoCloseInputStream;
import org.apache.commons.io.output.NullOutputStream;

import com.github.neio.exception.NeIOException;
import com.github.neio.filesystem.File;
import com.github.neio.filesystem.exception.FilesystemException;
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
      super(FilenameUtils.normalizeNoEndSeparator(path), File.class);
   }
   @Override
   protected int howDoICompare(File path)
   {
      return super.path.compareTo(FilenameUtils.normalizeNoEndSeparator(path.getPath())); 
   }
   @Override
   protected boolean amIEqual(File path)
   {
      return FilenameUtils.equals(super.path, path.getPath(), true, IOCase.SYSTEM);
   }
   @Override
   protected int hashMe()
   {
      return sha1Hash().hashCode();
   }
   @Override
   protected String stringify()
   {
      return super.getPath();
   }
   
   @Override
   public void touch() throws FilesystemException
   {
      try
      {
         FileUtils.touch(new java.io.File(super.getPath()));
      }
      catch(IOException e)
      {
         throw new FilesystemException("Unable to touch file [" + super.getPath() + "]", e);
      }
   }
   @Override
   public long size()
   {
      return new java.io.File(super.path).length();
   }
   @Override
   public BigInteger sha1Hash() throws NeIOException, FilesystemException
   {
      try
      {
         MessageDigest hash=MessageDigest.getInstance("SHA1");
         DigestOutputStream digestOutputStream=new DigestOutputStream(new NullOutputStream(), hash);
         
         IOUtils.copy(new AutoCloseInputStream(new FileInputStream(new java.io.File(super.path))), digestOutputStream);
         
         return new BigInteger(hash.digest());
      }
      catch(NoSuchAlgorithmException e)
      {
         throw new FilesystemException("Unable calculate hash due to SHA1 Algorithm not being found", e);
      }
      catch(FileNotFoundException e)
      {
         throw new FilesystemException("File pointed at by this path [" + super.path + "] does not exist", e);
      }
      catch(IOException e)
      {
         throw new NeIOException(e);
      }
   }
}
