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

import org.apache.commons.lang.ClassUtils;

import com.github.neio.filesystem.Path;
import com.github.neio.filesystem.exception.PathException;

/**
 * @author developer.sid@gmail.com
 *
 */
abstract class AbstractPath<P extends Path> implements Path
{
   private static final long serialVersionUID=-5023729396774232536L;
   protected File file;
   private Class<P> clazz;
   
   private String stringifyResult=null;
   private Integer hashMeResult=null;
   
   protected abstract void checkType(File path) throws PathException;
   protected abstract String stringify();
   protected abstract int hashMe();
   protected abstract int howDoICompare(P path);
   protected abstract boolean amIEqual(P path);
   
   AbstractPath(String path) throws PathException
   {
      checkType(file);
   }
   AbstractPath(String parent, String path)
   {
      this(parent.charAt(parent.length()-1) == File.pathSeparatorChar ? parent + path : parent + File.pathSeparator + path);
   }
   @Override
   public String getPath()
   {
      return file.getPath();
   }
   @Override
   public boolean exists()
   {
      return file.exists();
   }
   @SuppressWarnings("unchecked")
   @Override
   public int compareTo(Path o)
   {
      if(o != null)
      {
         if(ClassUtils.isAssignable(o.getClass(), clazz))
         {
            return howDoICompare((P)o);
         }
         else
         {
            throw new ClassCastException(o.getClass() + " was not comparable with " + clazz.getName());
         }
      }
      else
      {
         throw new NullPointerException("Path being compared was null");
      }
   }
   @SuppressWarnings("unchecked")
   @Override
   public boolean equals(Object obj)
   {
      if(obj != null)
      {
         if(ClassUtils.isAssignable(obj.getClass(), clazz) == true)
         {
            return amIEqual((P)obj);
         }
         else
         {
            return false;
         }
      }
      else
      {
         return false;
      }
   }
   @Override
   public int hashCode()
   {
      if(hashMeResult == null)
      {
         hashMeResult=Integer.valueOf(hashMe());
      }
      
      return hashMeResult.intValue();
   }
   public String toString()
   {
      if(stringifyResult == null)
      {
         stringifyResult=stringify();
      }
      
      return stringifyResult;
   }
}
