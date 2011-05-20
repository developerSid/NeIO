package com.github.neio.filesystem.paths;

import java.util.Iterator;

import com.github.neio.filesystem.Path;

class EmptyPathIterator implements Iterator<Path>
{
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
      
   }
}
