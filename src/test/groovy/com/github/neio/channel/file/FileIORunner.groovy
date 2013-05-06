package com.github.neio.channel.file

import com.github.neio.filesystem.paths.FilePath


class FileIORunner
{
   static void main(args)
   {
      File file=new File("/Users/myersgw/testFile.txt")
      FileIOChannel inFile=new FileIOChannel(new FilePath("/Users/myersgw/testFile.txt"))
      FileIOChannel outFile=new FileIOChannel(new FilePath("/Users/myersgw/testFileOut.txt"))
      
      file.text="Hello World"
      println inFile.transferTo(0, inFile.size(), outFile.underlyingImplementation)
   }
}
