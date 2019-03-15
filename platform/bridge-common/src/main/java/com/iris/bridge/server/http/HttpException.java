/*
 * Copyright 2019 Arcus Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.iris.bridge.server.http;

import io.netty.handler.codec.http.HttpResponseStatus;

@SuppressWarnings("serial")
public class HttpException extends Exception {
   private final int statusCode;
   
   public HttpException(int statusCode) {
      super("Http Error: " + statusCode);
      this.statusCode = statusCode;
   }
   
   public HttpException(HttpResponseStatus status) {
   	this(status, "Http Error " + status.code() + ": " + status.reasonPhrase(), null);
   }
   
   public HttpException(HttpResponseStatus status, Throwable cause) {
   	this(status, "Http Error " + status.code() + ": " + status.reasonPhrase(), cause);
   }
   
   public HttpException(HttpResponseStatus status, String message) {
		this(status, message, null);
	}

   public HttpException(HttpResponseStatus status, String message, Throwable cause) {
   	super(message, cause);
   	this.statusCode = status.code();
   }
   
	public int getStatusCode() {
      return statusCode;
   }
}

