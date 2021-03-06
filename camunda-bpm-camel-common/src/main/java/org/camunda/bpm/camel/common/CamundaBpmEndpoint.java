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

package org.camunda.bpm.camel.common;

import org.camunda.bpm.engine.RuntimeService;
import org.apache.camel.*;
import org.apache.camel.impl.DefaultEndpoint;

/**
 * This class has been modified to be consistent with the changes to CamelBehavior and its implementations. The set of changes
 * significantly increases the flexibility of our Camel integration, as you can either choose one of three "out-of-the-box" modes,
 * or you can choose to create your own. Please reference the comments for the "CamelBehavior" class for more information on the 
 * out-of-the-box implementation class options.  
 * 
 * @author Ryan Johnston (@rjfsu), Tijs Rademakers
 */
public class CamundaBpmEndpoint extends DefaultEndpoint {

  private RuntimeService runtimeService;

  private CamundaBpmConsumer camundaBpmConsumer;

  private boolean copyVariablesToProperties;

  private boolean copyVariablesToBodyAsMap;

  private boolean copyCamelBodyToBody;
  
  private boolean copyVariablesFromProperties;

  public CamundaBpmEndpoint(String uri, CamelContext camelContext, RuntimeService runtimeService) {
    super();
    setCamelContext(camelContext);
    setEndpointUri(uri);
    this.runtimeService = runtimeService;
  }

  void addConsumer(CamundaBpmConsumer consumer) {
    if (camundaBpmConsumer != null) {
      throw new RuntimeException("camunda BPM consumer already defined for " + getEndpointUri() + "!");
    }
    camundaBpmConsumer = consumer;
  }

  public void process(Exchange ex) throws Exception {
    if (camundaBpmConsumer == null) {
      throw new RuntimeException("camunda BPM consumer not defined for " + getEndpointUri());
    }
    camundaBpmConsumer.getProcessor().process(ex);
  }

  public Producer createProducer() throws Exception {
    return new CamundaBpmProducer(this, runtimeService);
  }

  public Consumer createConsumer(Processor processor) throws Exception {
    return new CamundaBpmConsumer(this, processor);
  }

  public boolean isSingleton() {
    return true;
  }

  public boolean isCopyVariablesToProperties() {
    return copyVariablesToProperties;
  }

  public void setCopyVariablesToProperties(boolean copyVariablesToProperties) {
    this.copyVariablesToProperties = copyVariablesToProperties;
  }

  public boolean isCopyCamelBodyToBody() {
    return copyCamelBodyToBody;
  }

  public void setCopyCamelBodyToBody(boolean copyCamelBodyToBody) {
    this.copyCamelBodyToBody = copyCamelBodyToBody;
  }

  public boolean isCopyVariablesToBodyAsMap() {
    return copyVariablesToBodyAsMap;
  }

  public void setCopyVariablesToBodyAsMap(boolean copyVariablesToBodyAsMap) {
    this.copyVariablesToBodyAsMap = copyVariablesToBodyAsMap;
  }
  
  public boolean isCopyVariablesFromProperties() {
    return copyVariablesFromProperties;
  }

  public void setCopyVariablesFromProperties(boolean copyVariablesFromProperties) {
    this.copyVariablesFromProperties = copyVariablesFromProperties;
  }

  @Override
  public boolean isLenientProperties() {
    return true;
  }
}
