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
package com.github.neio.filesystem;

import java.io.Serializable;

/**
 * @author developer.sid@gmail.com
 *
 * A simple abstraction for paths to files and directories on a filesystem.
 */
public interface Path extends Serializable, Comparable<Path>
{
   /**
    * @return String representing the path representation that was used to create this {@link Path} instance 
    */
   public String getPath();
   /**
    * Check if the {@link Path} instance actually points at a real object on the file system
    * @return true if path exists on filesystem false otherwise
    */
   public boolean exists();
   /**
    * All {@link Path} implementations should override {@link Object} toString method and return the same thing as getPath()
    * @return String representing the path representation that was used to create this {@link Path} instance
    */
   public String toString();
}
