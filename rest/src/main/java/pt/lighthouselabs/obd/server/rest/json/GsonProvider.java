/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package pt.lighthouselabs.obd.server.rest.json;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

import net.derquinse.common.jaxrs.gson.GenericGsonProvider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapterFactory;

/**
 * Gson-based JSON JAX-RS Provider.
 * 
 * We need this not only for using Gson instead of Jackson, but also to be able
 * to register {@link TypeAdapterFactory} instances.
 */
@Provider
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GsonProvider extends GenericGsonProvider<Object> {

	public GsonProvider() {
		super(registerGsonTypeAdapterFactories());
	}

	/**
	 * Hereby one will register every subtypes to be supported by Gson.
	 */
	private static final Gson registerGsonTypeAdapterFactories() {
		GsonBuilder builder = new GsonBuilder();

		// handle double NaN
		builder.serializeSpecialFloatingPointValues();

		// register
		// builder.registerTypeAdapterFactory(RuntimeTypeAdapterFactory
		// .of(Result.class).registerSubtype(LatencyResult.class)
		// .registerSubtype(DownloadUploadResult.class)
		// .registerSubtype(WebPageAccessResult.class));

		return builder.create();
	}

}