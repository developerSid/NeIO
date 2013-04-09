package com.github.neio.filesystem.paths;

import java.io.File;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import com.github.neio.filesystem.Path;

/**
 * Used to iterate over all the files in a directory tree.  Right now it will only return files due to it using Apache Commons IO {@link FileUtils} iterateFile method.
 * At some point depending upon need it may be necessary to also return directories instead of just files. 
 * @author developer.sid@gmail.com
 *
 */
class PathIterator implements Iterator<Path>
{
   private Iterator<File> proxied;
   
   PathIterator(File dir)
   {
      this.proxied=FileUtils.iterateFiles(dir, TrueFileFilter.TRUE, TrueFileFilter.TRUE);
   }
   @Override
   public boolean hasNext()
   {
      return proxied.hasNext();
   }
   @Override
   public Path next()
   {
      File file=proxied.next();
      
      return new FilePath(file.toString());
   }
   @Override
   public void remove()
   {
      throw new UnsupportedOperationException("Unable to remove Path from iterator");
   }
}
