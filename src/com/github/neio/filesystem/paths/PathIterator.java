package com.github.neio.filesystem.paths;

import java.io.File;
import java.util.Iterator;

import com.github.neio.filesystem.Path;

class PathIterator implements Iterator<Path>
{
   PathIterator(File dir)
   {
      
   }
   @Override
   public boolean hasNext()
   {
      return false;
   }
   @Override
   public Path next()
   {
      return null;
   }
   @Override
   public void remove()
   {
      throw new UnsupportedOperationException("Unable to remove Path from iterator");
   }
}
