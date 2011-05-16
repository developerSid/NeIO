package com.github.neio.filesystem.paths;

import java.io.File;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;

import com.github.neio.filesystem.Path;

class PathIterator implements Iterator<Path>
{
   private Iterator<File> platformFilesIterator;
   
   PathIterator(File dir)
   {
      this.platformFilesIterator=FileUtils.iterateFiles(dir, FileFilterUtils.trueFileFilter(), DirectoryFileFilter.DIRECTORY);
   }
   @Override
   public boolean hasNext()
   {
      return platformFilesIterator.hasNext();
   }
   @Override
   public Path next()
   {
      if(hasNext() == false)
      {
         throw new NoSuchElementException("Iterator has been exhausted.");
      }
      else
      {
         File currentPlatformFile=platformFilesIterator.next();
         
         if(currentPlatformFile.isDirectory() == true)
         {
            return new DirectoryPath(currentPlatformFile.getPath());
         }
         else
         {
            return new FilePath(currentPlatformFile.getPath());
         }
      }
   }
   @Override
   public void remove()
   {
      throw new UnsupportedOperationException("Unable to remove Path from iterator");
   }
}
