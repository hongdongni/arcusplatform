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
package com.iris.agent.zw.code.builders;

import com.iris.agent.zw.code.ZWSequence;
import com.iris.agent.zw.code.entity.CmdNetMgmtProxyNodeListGet;

public class NetMgmtProxyNodeListGetBuilder {
   private int seqNo = -1;
   
   // Should only be used for testing.
   public NetMgmtProxyNodeListGetBuilder withSeqNo(int seqNumber) {
      this.seqNo = seqNumber;
      return this;
   }
   
   public CmdNetMgmtProxyNodeListGet build() {
      if (seqNo < 0) {
         seqNo = ZWSequence.next();
      }
      
      return new CmdNetMgmtProxyNodeListGet(seqNo);
   }
}


