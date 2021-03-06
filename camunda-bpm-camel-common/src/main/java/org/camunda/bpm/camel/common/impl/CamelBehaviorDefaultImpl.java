/* Licensed under the Apache License, Version 2.0 (the "License");
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

package org.camunda.bpm.camel.common.impl;

import java.util.Map;

import org.camunda.bpm.camel.common.CamundaBpmComponent;
import org.camunda.bpm.camel.common.CamundaBpmEndpoint;
import org.camunda.bpm.camel.common.CamelBehavior;
import org.apache.camel.Exchange;

/**
 * This implementation of the CamelBehavior abstract class works just like CamelBehaviour does; it copies variables 
 * into Camel as properties.
 * 
 * @author Ryan Johnston (@rjfsu), Tijs Rademakers
 */
public class CamelBehaviorDefaultImpl extends CamelBehavior {
	
	private static final long serialVersionUID = 003L;
	
	@Override
	protected void modifyActivitiComponent(CamundaBpmComponent component) {
		//Set the copy method for new endpoints created using this component.
		component.setCopyVariablesToProperties(true);
		component.setCopyVariablesToBodyAsMap(false);
		component.setCopyCamelBodyToBody(false);
	}
	
	@Override
  protected void copyVariables(Map<String, Object> variables, Exchange exchange, CamundaBpmEndpoint endpoint) {
	  if (endpoint.isCopyVariablesToBodyAsMap()) {
	    copyVariablesToBodyAsMap(variables, exchange);
	  } else if (endpoint.isCopyCamelBodyToBody()) {
	    copyVariablesToBody(variables, exchange);
	  } else {
	    copyVariablesToProperties(variables, exchange);
	  }
	}
}
