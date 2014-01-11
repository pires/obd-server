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
package pt.lighthouselabs.obd.server.dao;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import pt.lighthouselabs.obd.server.model.ObdReading;
import pt.lighthouselabs.obd.server.model.ObdReading_;

/**
 * Data access object for {@link ObdReading} entity.
 */
@Stateless
public class ObdReadingDao extends AbstractDao<ObdReading> {

  public ObdReadingDao() {
    super(ObdReading.class);
  }

  /**
   * Finds all {@link ObdReading} related to a certain vehicle.
   * 
   * @param vin
   *          the Vehicle Identification Number
   * @return a list of all {@link ObdReading} related to a certain vehicle.
   */
  public List<ObdReading> find_all_readings_by_vin(final String vin) {
    checkNotNull(vin, "No Vehicle ID was provided to search for.");
    CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
    CriteriaQuery<ObdReading> cq = cb.createQuery(ObdReading.class);
    Root<ObdReading> user = cq.from(ObdReading.class);
    // compare lowercase all the time
    Predicate vinEquals = cb.equal(cb.lower(user.get(ObdReading_.vin)),
        vin.toLowerCase());
    cq.where(vinEquals);
    return getEntityManager().createQuery(cq).getResultList();
  }

}