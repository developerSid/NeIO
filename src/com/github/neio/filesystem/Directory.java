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

import com.github.neio.filesystem.exception.FilesystemException;

/**
 * This is just a marker interface for differentiating {@link Path} implementation as a directory on the file system.
 * @author developer.sid@gmail.com
 *
 */
public interface Directory extends Path
{
   /**
    * Will create the directory pointed at by {@link Directory} implementation as well as any parent directories that don't already exist
    * @throws FilesystemException if the directory(s) are unable to be made
    */
   public void mkdir() throws FilesystemException;
}
