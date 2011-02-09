package com.github.neio.filesystem;

import java.math.BigInteger;

import com.github.neio.exception.NeIOException;
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
    * All implementations of the {@link File} will return the hash their contents.  However due to the limitations of the Java 32 bit integer its uniqueness will not be as good
    * @return hash of the files contents
    */
   public int hashCode();
   /**
    * This will return the more unique hash of a file based off the sha-1 hash calculation of the files contents. Probably completely worthless.
    * <br/>
    * Caution this method on all implementations will probably have to recalculate the hash every time so performance of this could get really slow.
    * @return {@link BigInteger} that is the sha-1 hash of the contents of the file
    * @throws FilesystemException if there is an error during sha-1 calculation
    */
   public BigInteger sha1Hash() throws NeIOException, FilesystemException;
}
