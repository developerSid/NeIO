package com.github.neio.channel.exception;

import com.github.neio.exception.NeIOException;

public class ChannelException extends NeIOException
{
   private static final long serialVersionUID = 4508724545351881303L;

   public ChannelException()
   {
      super();
   }
   public ChannelException(String message, Throwable cause)
   {
      super(message, cause);
   }
   public ChannelException(String message)
   {
      super(message);
   }
   public ChannelException(Throwable cause)
   {
      super(cause);
   }
}
