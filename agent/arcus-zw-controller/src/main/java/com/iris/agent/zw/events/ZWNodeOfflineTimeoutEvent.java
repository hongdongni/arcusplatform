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
package com.iris.agent.zw.events;

import com.iris.messages.address.ProtocolDeviceId;

/**
 * Event fired when the platform updates the offline
 * timeout for a specified device.
 * 
 * @author Erik Larson
 */
public class ZWNodeOfflineTimeoutEvent implements ZWEvent {
   private final ProtocolDeviceId pdi;
   private final int offlineTimeoutInSecs;
   
   public ZWNodeOfflineTimeoutEvent(ProtocolDeviceId pdi, int offlineTimeoutInSecs) {
      this.pdi = pdi;
      this.offlineTimeoutInSecs = offlineTimeoutInSecs;
   }
   
   @Override
   public ZWEventType getType() {
      return ZWEventType.OFFLINE_TIMEOUT;
   }
   
   public ProtocolDeviceId getProtocolDeviceId() {
      return pdi;
   }
   
   public int getOffineTimeoutInSecs() {
      return offlineTimeoutInSecs;            
   }
}
