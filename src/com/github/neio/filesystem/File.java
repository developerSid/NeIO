package com.github.neio.filesystem;

import com.github.neio.filesystem.exception.FilesystemException;

/**
 * This is just a marker interface for differentiating a {@link Path} implementation as a file on the file system.
 * @author developer.sid@gmail.com
 *
 */
public interface File extends Path
{
   /**
    * Implements the same behavior as the "touch" utility on Unix. It creates a new file with size 0 or, if the file exists already, it is opened and closed without modifying it, but updating the file date and time.
    * <br />
    * <br />
    * All directories up to the parent directory of the file that is pointed at by the {@link File} implementation will be created if they don't exist
    * @throws FilesystemException if for some reason the file is unable to be handled
    */
   public void touch() throws FilesystemException;
   /**
    * returns the size of the file
    * @return the number of bytes in the file
    */
   public long size();
   /**
    * All implementations of the {@link File} interface should return the SHA1 hash of their contents.
    * @return SHA1 hash of the files contents
    */
   public int hashCode();
}
