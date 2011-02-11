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
package com.github.neio.channel;

import java.nio.channels.Channel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

import com.github.neio.channel.exception.ChannelException;

/**
 * {@link IOChannel} is a unifying interface for NIO Channel based input/output used by the NeIO library. <br />
 * Additional methods of reset, clear, size and transferTo, and transferFrom have been added
 * @author developer.sid@gmail.com
 *
 */
public interface IOChannel extends Channel, WritableByteChannel, ReadableByteChannel
{
   /**
    * Reset the pointer for reads and writes to the channel implementation to the beginning of the channel
    */
	public void reset() throws ChannelException;
	/**
	 * Clear the contents of the channel implementation and resets the pointer to the beginning of the channel
	 */
	public void clear() throws ChannelException;
	/**
	 * Load the size of the file in bytes.  This has the potential to be system dependent and may yield unexpected results
	 * @return the number of bytes that are contained within this channel
	 */
	public long size() throws ChannelException;
	/**
	 * Transfer all the bytes from the underlying implementation to the target {@link WritableByteChannel} from the current position of the underlying channel implementation
	 * @param target the {@link WritableByteChannel} that the bytes will be written to.
	 * @return
	 */
	public long transferTo(WritableByteChannel target) throws ChannelException;
	/**
	 * Transfers the specified number of bytes of the underlying channel implementation to the provided {@link WritableByteChannel}
	 * @param position the starting point of the bytes from the underlying channel implementation that will be transfered to the provided {@link WritableByteChannel}
	 * @param count the number of bytes that will be transfered from the underlying channel implementation to the {@link WritableByteChannel}
	 * @param target the writable channel that the bytes will from the underlying channel implementation will be transfered to.  This cannot be null.
	 * @return the number of bytes that were transfered
	 */
	public long transferTo(long position, long count, WritableByteChannel target) throws ChannelException;
	/**
	 * Transfer all the bytes form the provided {@link ReadableByteChannel} to the underlying channel implementation starting from the current position of the src {@link ReadableByteChannel}
	 * @param src the {@link ReadableByteChannel} that the bytes will be read from.
	 * @return the number of bytes transfered.
	 */
	public long transferFrom(ReadableByteChannel src) throws ChannelException;
	/**
	 * Transfers from the {@link ReadableByteChannel} to the underlying channel implementation the specified bytes
	 * @param src the {@link ReadableByteChannel} that the bytes will be read from.
	 * @param position the starting point of the bytes from within the src {@link ReadableByteChannel} parameter
	 * @param count the number of bytes that will be transfered
	 * @return the number of bytes that were transfered.
	 */
	public long transferFrom(ReadableByteChannel src, long position, long count) throws ChannelException;

}
