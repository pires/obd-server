/*
 * Copyright (C) the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.derquinse.common.jaxrs.gson;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;

import com.google.common.base.Charsets;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;

/**
 * Base class from Gson-based JSON JAX-RS providers.
 * 
 * @author Andres Rodriguez.
 */
public abstract class GenericGsonProvider<T> implements MessageBodyReader<T>,
        MessageBodyWriter<T> {
	/** Gson supplier. */
	private final Supplier<Gson> supplier;

	/**
	 * Constructor.
	 * 
	 * @param supplier
	 *            Gson supplier to use.
	 */
	protected GenericGsonProvider(Supplier<Gson> supplier) {
		this.supplier = checkNotNull(supplier,
		        "The Gson supplier must be provided");
	}

	/**
	 * Constructor.
	 * 
	 * @param gson
	 *            Gson instance to use.
	 */
	protected GenericGsonProvider(Gson gson) {
		this(Suppliers.ofInstance(checkNotNull(gson,
		        "The Gson instance must be provided")));
	}

	/** Default constructor. */
	protected GenericGsonProvider() {
		this(new Gson());
	}

	/** Returns the Gson object to use. */
	protected final Gson getGson() {
		return checkNotNull(supplier.get(), "The provided Gson object is null");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ws.rs.ext.MessageBodyReader#isReadable(java.lang.Class,
	 * java.lang.reflect.Type, java.lang.annotation.Annotation[],
	 * javax.ws.rs.core.MediaType)
	 */
	public boolean isReadable(Class<?> type, Type genericType,
	        Annotation[] annotations, MediaType mediaType) {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ws.rs.ext.MessageBodyReader#readFrom(java.lang.Class,
	 * java.lang.reflect.Type, java.lang.annotation.Annotation[],
	 * javax.ws.rs.core.MediaType, javax.ws.rs.core.MultivaluedMap,
	 * java.io.InputStream)
	 */
	public T readFrom(Class<T> type, Type genericType,
	        Annotation[] annotations, MediaType mediaType,
	        MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
	        throws IOException, WebApplicationException {
		return getGson().fromJson(
		        new InputStreamReader(entityStream, Charsets.UTF_8), type);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ws.rs.ext.MessageBodyWriter#isWriteable(java.lang.Class,
	 * java.lang.reflect.Type, java.lang.annotation.Annotation[],
	 * javax.ws.rs.core.MediaType)
	 */
	public boolean isWriteable(Class<?> type, Type genericType,
	        Annotation[] annotations, MediaType mediaType) {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ws.rs.ext.MessageBodyWriter#getSize(java.lang.Object,
	 * java.lang.Class, java.lang.reflect.Type,
	 * java.lang.annotation.Annotation[], javax.ws.rs.core.MediaType)
	 */
	public long getSize(T t, Class<?> type, Type genericType,
	        Annotation[] annotations, MediaType mediaType) {
		return -1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ws.rs.ext.MessageBodyWriter#writeTo(java.lang.Object,
	 * java.lang.Class, java.lang.reflect.Type,
	 * java.lang.annotation.Annotation[], javax.ws.rs.core.MediaType,
	 * javax.ws.rs.core.MultivaluedMap, java.io.OutputStream)
	 */
	public void writeTo(T t, Class<?> type, Type genericType,
	        Annotation[] annotations, MediaType mediaType,
	        MultivaluedMap<String, Object> httpHeaders,
	        OutputStream entityStream) throws IOException,
	        WebApplicationException {

		final Writer w = new OutputStreamWriter(entityStream, Charsets.UTF_8);
		final JsonWriter jsw = new JsonWriter(w);
		getGson().toJson(t, type, jsw);
		jsw.close();
	}
}