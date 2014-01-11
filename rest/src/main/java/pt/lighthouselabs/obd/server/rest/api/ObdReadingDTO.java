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
package pt.lighthouselabs.obd.server.rest.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pt.lighthouselabs.obd.server.model.ObdReading;

/**
 * Data transfer object for {@link ObdReading} entity.
 */
public class ObdReadingDTO {

  private double latitude, longitude;
  private long timestamp;
  private String vin;
  private Map<String, String> readings;

  private ObdReadingDTO() {
  }

  private ObdReadingDTO(double latitude, double longitude, long timestamp,
      String vin, Map<String, String> readings) {
    this.latitude = latitude;
    this.longitude = longitude;
    this.timestamp = timestamp;
    this.vin = vin;
    this.readings = readings;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  public String getVin() {
    return vin;
  }

  public void setVin(String vin) {
    this.vin = vin;
  }

  public Map<String, String> getReadings() {
    return readings;
  }

  public void setReadings(Map<String, String> readings) {
    this.readings = readings;
  }

  public static ObdReadingDTO fromEntity(final ObdReading entity) {
    ObdReadingDTO dto = new ObdReadingDTO();
    dto.setLatitude(entity.getLatitude());
    dto.setLongitude(entity.getLongitude());
    dto.setReadings(entity.getReadings());
    dto.setTimestamp(entity.getTimestamp());
    dto.setVin(entity.getVin());
    return dto;
  }

  public static List<ObdReadingDTO> fromEntities(final List<ObdReading> entities) {
    List<ObdReadingDTO> dtos = new ArrayList<>(entities.size());
    for (ObdReading entity : entities)
      dtos.add(fromEntity(entity));
    return dtos;
  }

  public static ObdReading toEntity(final ObdReadingDTO dto) {
    ObdReading entity = new ObdReading();
    entity.setLatitude(dto.getLatitude());
    entity.setLongitude(dto.getLongitude());
    entity.setReadings(dto.getReadings());
    entity.setTimestamp(dto.getTimestamp());
    entity.setVin(dto.getVin());
    return entity;
  }

}