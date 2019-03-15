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
package com.iris.alexa.shs.handlers.v2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.iris.alexa.AlexaInterfaces;
import com.iris.alexa.message.AlexaMessage;
import com.iris.alexa.message.Header;
import com.iris.alexa.message.v2.Appliance;
import com.iris.alexa.message.v2.IntValue;
import com.iris.alexa.message.v2.error.DriverInternalError;
import com.iris.alexa.message.v2.error.ErrorPayloadException;
import com.iris.alexa.message.v2.request.IncrementColorTemperatureRequest;
import com.iris.alexa.message.v2.response.IncrementColorTemperatureConfirmation;
import com.iris.alexa.shs.ShsAssertions;
import com.iris.messages.MessageBody;
import com.iris.messages.PlatformMessage;
import com.iris.messages.address.Address;
import com.iris.messages.service.AlexaService;
import com.iris.messages.type.AlexaPropertyReport;
import com.iris.util.IrisUUID;

public class TestTxfmIncrementColorTemperature {

   private AlexaMessage incColorTemp;
   private Appliance app;

   @Before
   public void setup() {
      Header h = Header.v2(IrisUUID.randomUUID().toString(), "IncrementColorTemperatureRequest", "Alexa.ConnectedHome.Control");

      app = new Appliance();
      app.setApplianceId(Address.platformDriverAddress(IrisUUID.randomUUID()).getRepresentation());

      IncrementColorTemperatureRequest payload = new IncrementColorTemperatureRequest();
      payload.setAccessToken("token");
      payload.setAppliance(app);

      incColorTemp = new AlexaMessage(h, payload);
   }

   @Test
   public void testIncrementColorTemperature() {
      PlatformMessage platMsg = TxfmTestUtil.txfmReq(incColorTemp);

      ShsAssertions.assertExecuteRequest(
         platMsg,
         app.getApplianceId(),
         AlexaInterfaces.ColorTemperatureController.REQUEST_INCREASECOLORTEMPERATURE,
         ImmutableMap.of(),
         null,
         false
      );
   }

   @Test
   public void testSuccessResponse() {
      AlexaPropertyReport colorTempRpt = new AlexaPropertyReport();
      colorTempRpt.setValue(2700);
      colorTempRpt.setUncertaintyInMilliseconds(0L);
      colorTempRpt.setTimeOfSample(ZonedDateTime.now(ZoneOffset.UTC).toString());
      colorTempRpt.setName(AlexaInterfaces.ColorTemperatureController.PROP_COLORTEMPERATUREINKELVIN);
      colorTempRpt.setNamespace(AlexaInterfaces.ColorTemperatureController.NAMESPACE);

      MessageBody resp = AlexaService.ExecuteResponse.builder()
         .withProperties(ImmutableList.of(colorTempRpt.toMap()))
         .build();

      AlexaMessage response = TxfmTestUtil.txfmResponse(incColorTemp, resp);
      ShsAssertions.assertCommonResponseHeader(incColorTemp, response, "IncrementColorTemperatureConfirmation", "2");
      assertTrue(response.getPayload() instanceof IncrementColorTemperatureConfirmation);
      IncrementColorTemperatureConfirmation confirmation = (IncrementColorTemperatureConfirmation) response.getPayload();
      IntValue confirmedColorTemp = (IntValue) confirmation.getAchievedState().get("colorTemperature");
      assertEquals(2700, confirmedColorTemp.getValue());
   }

   @Test
   public void testNullPropertiesResponse() {
      try {
         TxfmTestUtil.txfmResponse(incColorTemp, AlexaService.ExecuteResponse.builder().build());
      } catch(ErrorPayloadException epe) {
         assertTrue(epe.getPayload() instanceof DriverInternalError);
         DriverInternalError die = (DriverInternalError) epe.getPayload();
         assertEquals("Alexa.ConnectedHome.Control", die.getNamespace());
      }
   }

   @Test
   public void testEmptyPropertiesResponse() {
      MessageBody resp = AlexaService.ExecuteResponse.builder()
         .withProperties(ImmutableList.of())
         .build();
      try {
         TxfmTestUtil.txfmResponse(incColorTemp, resp);
      } catch(ErrorPayloadException epe) {
         assertTrue(epe.getPayload() instanceof DriverInternalError);
         DriverInternalError die = (DriverInternalError) epe.getPayload();
         assertEquals("Alexa.ConnectedHome.Control", die.getNamespace());
      }
   }
}

