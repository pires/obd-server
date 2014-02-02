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
/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package pt.lighthouselabs.obd.server.rest;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.lighthouselabs.obd.server.dao.ObdReadingDao;
import pt.lighthouselabs.obd.server.model.ObdReading;
import pt.lighthouselabs.obd.server.rest.api.ObdReadingDTO;

@Path("/")
public class ObdReaderService {

	private static final Logger logger = LoggerFactory
			.getLogger(ObdReaderService.class);

	@EJB
	private ObdReadingDao orDao;

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response handleNewObdReading(final ObdReadingDTO dto) {
		logger.info("Received new reading {}", dto);
		checkNotNull(dto);
		// persist entity
		orDao.create(ObdReadingDTO.toEntity(dto));
		return Response.ok().build();
	}

	@GET
	@Path("/vin")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> retrieveVINs() {
		return orDao.find_all_vins();
	}

	/**
	 * Retrieves all {@link ObdReading} related to a parameterized vehicle.
	 * 
	 * @param vin
	 *            the Vehicle Identification Number
	 * @return all {@link ObdReading} related to a parameterized vehicle.
	 */
	@GET
	@Path("/vin/{vin}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ObdReadingDTO> retrieveObdReadingsByVIN(
			@PathParam("vin") final String vin) {
		checkNotNull(vin);
		return ObdReadingDTO.fromEntities(orDao.find_all_readings_by_vin(vin));

	}

}